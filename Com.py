from multiprocessing import Process, Queue, Manager
import cv2
import socket
import math
import numpy as np
from adafruit_servokit import ServoKit
import time
from flask import Flask, render_template, Response, request
import sys

class SB_frame():
    def __init__(self):
        self.frame = 0

    def setFrame(self,frame):
        self.frame = frame

    def getFrame(self):
        return self.frame

def servoCom():
    kit = ServoKit(channels=16)

    #UDP communication
    UDP_IP = '127.0.0.1'
    UDP_PORT = 5556
    BUFFER_SIZE = 20
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.bind((UDP_IP,UDP_PORT))

    while True:
        data, addr = s.recvfrom(1024)
        data = data.decode()
        data = data.split('\n')[0].split(',')

        if(len(data) == 3):
            #print(data[0])
            #print(data[1])
            #print(data[2])

            angle1 = float(data[0])
            angle2 = float(data[1])
            angle3 = float(data[2])

            kit.servo[0].angle = angle1
            kit.servo[1].angle = angle2
            kit.servo[2].angle = angle3

def cvCom(sb_frame):
    #UDP communication
    UDP_IP = '127.0.0.1'
    UDP_PORT = 5555
    BUFFER_SIZE = 20
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    #Define lower and upper bound of the color
    lower1_h = 0
    lower1_s = 166
    lower1_v = 174

    lower2_h = 131
    lower2_s = 88
    lower2_v = 55

    upper1_h = 29
    upper1_s = 255
    upper1_v = 255

    upper2_h = 180
    upper2_s = 255
    upper2_v = 255

    #Define the portion of the image the program should look at
    xMin = 185
    xMax = 473
    yMin = 90
    yMax = 377

    #Define platform center and diameter
    platformCenter = (145, 142)
    platformRadius = 143

    #Start video capture
    vs = cv2.VideoCapture(0)

    #Tells the program to display the image or not
    showImage = True

    while True:
        #Read frame from video capture
        _,frame = vs.read()

        #Resize frame
        #frame = cv2.resize(frame,(400,300))

        #Crop frame
        roi = frame[yMin:yMax,xMin:xMax]
        frame = cv2.bitwise_and(roi,roi)

        #Draw circle around platform
        cv2.circle(frame, platformCenter, platformRadius, (0, 0, 255), 1)

        #Convert to hsv color space
        hsv = cv2.cvtColor(frame,cv2.COLOR_BGR2HSV)

        #Apply circular mask
        m = np.zeros(shape = hsv.shape, dtype = "uint8")
        cv2.circle(m, (platformCenter[0], platformCenter[1]), platformRadius+5, color=(255, 255, 255), thickness=-1)
        hsv = cv2.bitwise_and(hsv,m)

        #Create mask
        m1 = cv2.inRange(hsv, (lower1_h, lower1_s, lower1_v), (upper1_h, upper1_s, upper1_v))
        m2 = cv2.inRange(hsv, (lower2_h, lower2_s, lower2_v), (upper2_h, upper2_s, upper2_v))
        mask = m1 | m2

        #Filter out noise
        mask = cv2.erode(mask, np.ones((5, 5), np.uint8))
        mask = cv2.dilate(mask, np.ones((5, 5), np.uint8))

        #Find contours
        cnts,h = cv2.findContours(mask, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

        if len(cnts) > 0:
            #Find the contour with the biggest area
            c = max(cnts, key=cv2.contourArea)

            #Find position and radius of the object
            ((x,y),radius) = cv2.minEnclosingCircle(c)

            #Get the moments of the contour and find its center
            M = cv2.moments(c)
            if M["m00"] > 0:
                center = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))

                #Calculates the position of the ball relative to the platform
                dist_x = center[0] - platformCenter[0]
                dist_y = platformCenter[1] - center[1]

                if radius > 1:
                    #Draw circles around the object
                    cv2.circle(frame, center, int(radius), (0, 255, 0), 1)
                    cv2.circle(frame, center, 2, (0,255,0),-1)

                    #Label to display position of the ball relative to the center of the platform
                    cv2.putText(frame, "x: "+str(dist_x)+", y: "+str(dist_y), (10,30),cv2.FONT_HERSHEY_SIMPLEX, 1.0, (255,255,255))

                    #Send position of the ball over UDP
                    s.sendto((str(dist_x) + "," + str(dist_y) + "\n").encode(), (UDP_IP,UDP_PORT))

        _,jpeg = cv2.imencode('.jpg',frame.copy())
        try:
            #sb_frame.put(frame, False)
            sb_frame.put(jpeg.tobytes(), False)
        except:
            pass

setpointX = 0
setpointY = 0

def webServer(sb_frame):
    app = Flask(__name__)

    #UDP communication
    UDP_IP = '127.0.0.1'
    UDP_PORT = 5557
    BUFFER_SIZE = 20
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    @app.route('/')
    def index():
        return render_template('index.html')

    def gen(frames):
        while True:
            frame = frames.get()
            if frame is not None:
                #_,jpeg = cv2.imencode('.jpg',frame.copy())
                yield (b'--frame\r\n'
                       b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')

    @app.route('/video_feed')
    def video_feed():
        return Response(gen(sb_frame),mimetype='multipart/x-mixed-replace; boundary=frame')

    @app.route('/setpoint', methods = ['POST', 'GET'])
    def setpoint():
        global setpointX
        global setpointY
        if request.method == 'POST':
            data = request.get_json()
            setpointX = data['setpointX']
            setpointY = data['setpointY']
            s.sendto(("setpoint:"+str(setpointX) + "," + str(setpointY) + "\n").encode(), (UDP_IP,UDP_PORT))
            return 'OK', 200
        else:
            return '{"setpointX": "'+str(setpointX)+'","setpointY": "'+str(setpointY)+'"}'

    @app.route('/pidValues', methods = ['POST', 'GET'])
    def pidValues():
        if request.method == 'POST':
            data = request.get_json()
            Kp = data['Kp']
            Ki = data['Ki']
            Kd = data['Kd']
            DT = data['DT']
            filter_alpha = data['filter_alpha']
            filter_iterations = data['filter_iterations']

            s.sendto(("pid:"+str(Kp) + "," + str(Ki) + "," + str(Kd) + "," + str(DT) + "," + str(filter_alpha) + "," + str(filter_iterations) + "\n").encode(), (UDP_IP,UDP_PORT))
            return 'OK', 200
        else:
            return render_template('pidValues.html')

    if __name__ == '__main__':
        app.run(host='0.0.0.0',debug=False)

if __name__ == '__main__':
    q = Queue()
    p1 = Process(target=cvCom, args=(q,))
    p2 = Process(target=servoCom)
    p3 = Process(target=webServer, args=(q,))
    p1.start()
    p2.start()
    p3.start()
    if len(sys.argv) > 1:
        if sys.argv[1] == 'i':
            while True:
                cv2.imshow('image',q.get())
                k = cv2.waitKey(20)
                if k == 27:
                    cv2.destroyAllWindows()
                    p1.kill()
                    p2.kill()
                    p3.kill()
                    break
