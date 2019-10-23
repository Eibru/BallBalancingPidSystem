import cv2
#import imutils
#import socket
import math
import numpy as np

#UDP communication
UDP_IP = '127.0.0.1'
UDP_PORT = 5555
BUFFER_SIZE = 20 
#s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

#Setup upper and lower bounds of the color space
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

#Start video capture
vs = cv2.VideoCapture(0)

platformCenter = (200, 150)
platformDiameter = 80

lower1 = np.array([lower1_h, lower1_s, lower1_v])
upper1 = np.array([upper1_h, upper1_s, upper1_v])

while(True):
    #Read frame from video capture, resize and draw circle
    _, frame = vs.read()
    frame = cv2.resize(frame,(200,150))
    #roi = frame[101: 367, 200: 461]
    #frame = cv2.bitwise_and(roi, roi)
    #cv2.circle(frame, platformCenter, platformDiameter, (0, 0, 255), 1)

    #Blur and convert to hsv color spectrum
    #blur = cv2.GaussianBlur(frame, (11,11),0)
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    #Create two masks using lower and upper bounds and combine them
    m1 = cv2.inRange(hsv, lower1, upper1)
    m2 = cv2.inRange(hsv, (lower2_h, lower2_s, lower2_v), (upper2_h, upper2_s, upper2_v))
    mask = m1 | m2

    #Filter out noise
    mask = cv2.erode(mask, None, iterations=2)
    mask = cv2.dilate(mask, None, iterations=2)

    #Find the contours of the mask
    cnts, h = cv2.findContours(mask, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    #cnts = imutils.grab_contours(cnts)

    if len(cnts) > 0:
        #Find the contour with the biggest area
        c = max(cnts, key=cv2.contourArea)

        #Find x and y position and the radius of the contour
        #((x,y),radius) = cv2.minEnclosingCircle(c)

        #Send position to queue
        #self.out_q_pos.put(str(x) + "," + str(y))

        #Get the moments of the contour and find its center
        M = cv2.moments(c)
        center = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))

        #Distance from object to the platform center
        plat_x = platformCenter[0]
        plat_y = platformCenter[1]
        dist_x = plat_x - center[0]
        dist_y = plat_y - center[1]
        distance = math.sqrt(dist_x*dist_x + dist_y*dist_y)
        
        #If the radius is grater than ..., draw a circle
        if distance < platformDiameter:
            cv2.circle(frame, center, int(radius), (0, 255, 0), 1)
            cv2.circle(frame, center, 2, (0,255,0),-1)
            s.sendto((str(center[0]) + "," + str(center[1]) + "\n").encode(), (UDP_IP,UDP_PORT))
    
    cv2.imshow('image',frame)
