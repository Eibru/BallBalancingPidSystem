import cv2
import imutils
import socket

#UDP communication
UDP_IP = '127.0.0.1'
UDP_PORT = 5556
BUFFER_SIZE = 20 
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

#Setup upper and lower bounds of the color space
lower1_h = 0; 
lower1_s = 166; 
lower1_v = 174; 

lower2_h = 131; 
lower2_s = 88; 
lower2_v = 55; 

upper1_h = 29; 
upper1_s = 255; 
upper1_v = 255; 

upper2_h = 180; 
upper2_s = 255; 
upper2_v = 255; 

#Start video capture
vs = cv2.VideoCapture(0)

while(True):
    #Read frame from video capture
    _, frame = vs.read()

    #Resize, blur and convert to hsv color spectrum
    frame = cv2.resize(frame,(200, 100))
    blur = cv2.GaussianBlur(frame, (11,11),0)
    hsv = cv2.cvtColor(blur, cv2.COLOR_BGR2HSV)

    #Create two masks using lower and upper bounds and combine them
    m1 = cv2.inRange(hsv, (lower1_h, lower1_s, lower1_v), (upper1_h, upper1_s, upper1_v))
    m2 = cv2.inRange(hsv, (lower2_h, lower2_s, lower2_v), (upper2_h, upper2_s, upper2_v))
    mask = m1 | m2

    #Filter out noise
    mask = cv2.erode(mask, None, iterations=2)
    mask = cv2.dilate(mask, None, iterations=2)

    #Find the contours of the mask
    cnts = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    cnts = imutils.grab_contours(cnts)

    if len(cnts) > 0:
        #Find the contour with the biggest area
        c = max(cnts, key=cv2.contourArea)

        #Find x and y position and the radius of the contour
        ((x,y),radius) = cv2.minEnclosingCircle(c)

        #Send position to queue
        #self.out_q_pos.put(str(x) + "," + str(y))

        #Get the moments of the contour and find its center
        M = cv2.moments(c)
        center = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))
        
        #If the radius is grater than ..., draw a circle
        if radius > 2:
            cv2.circle(frame, center, int(radius), (0, 255, 0), 1)
            cv2.circle(frame, center, 2, (0,255,0),-1)

        s.sendto(str(x) + "," + str(y) + "\n", (UDP_IP,UDP_PORT))
