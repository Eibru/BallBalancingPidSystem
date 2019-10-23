import cv2
import numpy as np
import imutils
import math

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

#Define lower bounds
redLower_1 = (0, 140, 140)
redLower_2 = (165, 140, 115)

#Define upper bounds
redUpper_1 = (15, 255,255)
redUpper_2 = (180, 255,255)

#Start video capture
vs = cv2.VideoCapture(0)

#Temp
def nothing(value):
    pass
cv2.namedWindow('TT')
cv2.createTrackbar('lower1_h', 'TT', 0, 180, nothing)
cv2.createTrackbar('lower1_s', 'TT', 0, 255, nothing)
cv2.createTrackbar('lower1_v', 'TT', 0, 255, nothing)
cv2.createTrackbar('lower2_h', 'TT', 0, 180, nothing)
cv2.createTrackbar('lower2_s', 'TT', 0, 255, nothing)
cv2.createTrackbar('lower2_v', 'TT', 0, 255, nothing)
cv2.createTrackbar('upper1_h', 'TT', 0, 180, nothing)
cv2.createTrackbar('upper1_s', 'TT', 0, 255, nothing)
cv2.createTrackbar('upper1_v', 'TT', 0, 255, nothing)
cv2.createTrackbar('upper2_h', 'TT', 0, 180, nothing)
cv2.createTrackbar('upper2_s', 'TT', 0, 255, nothing)
cv2.createTrackbar('upper2_v', 'TT', 0, 255, nothing)
cv2.createTrackbar('x', 'TT', 0, 300, nothing)
cv2.createTrackbar('y', 'TT', 0, 150, nothing)
cv2.setTrackbarPos('lower1_h', 'TT', lower1_h)
cv2.setTrackbarPos('lower1_s', 'TT', lower1_s)
cv2.setTrackbarPos('lower1_v', 'TT', lower1_v)
cv2.setTrackbarPos('lower2_h', 'TT', lower2_h)
cv2.setTrackbarPos('lower2_s', 'TT', lower2_s)
cv2.setTrackbarPos('lower2_v', 'TT', lower2_v)
cv2.setTrackbarPos('upper1_h', 'TT', upper1_h)
cv2.setTrackbarPos('upper1_s', 'TT', upper1_s)
cv2.setTrackbarPos('upper1_v', 'TT', upper1_v)
cv2.setTrackbarPos('upper2_h', 'TT', upper2_h)
cv2.setTrackbarPos('upper2_s', 'TT', upper2_s)
cv2.setTrackbarPos('upper2_v', 'TT', upper2_v)

platformCenter = (200, 150)
platformDiameter = 100

while(True):
    #Read frame from video capture
    _, frame = vs.read()
    frame = cv2.resize(frame,(400,300))
    cv2.circle(frame, platformCenter, platformDiameter, (0, 0, 255), 1)

    #Resize, blur and convert to hsv color spectrum
    #frame = cv2.resize(frame,(300, 150))
    blur = cv2.GaussianBlur(frame, (11,11),0)
    hsv = cv2.cvtColor(blur, cv2.COLOR_BGR2HSV)

    #Create two masks using lower and upper bounds and combine them
    m1 = cv2.inRange(hsv, (cv2.getTrackbarPos('lower1_h','TT'), cv2.getTrackbarPos('lower1_s','TT'), cv2.getTrackbarPos('lower1_v','TT')), 
    (cv2.getTrackbarPos('upper1_h','TT'),cv2.getTrackbarPos('upper1_s','TT'),cv2.getTrackbarPos('upper1_v','TT')))
    m2 = cv2.inRange(hsv, (cv2.getTrackbarPos('lower2_h','TT'),cv2.getTrackbarPos('lower2_s','TT'),cv2.getTrackbarPos('lower2_v','TT')), 
    (cv2.getTrackbarPos('upper2_h','TT'),cv2.getTrackbarPos('upper2_s','TT'),cv2.getTrackbarPos('upper2_v','TT')))
    mask = m1 | m2

    #Temp
    cv2.imshow('TT',mask)

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

        #Get the moments of the contour and find its center
        M = cv2.moments(c)
        center = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))
        
        #Distance from object to the platform center
        plat_x = platformCenter[0]
        plat_y = platformCenter[1]
        dist_x = plat_x - x
        dist_y = plat_y - y
        distance = math.sqrt(dist_x*dist_x + dist_y*dist_y)

        #If the radius is grater than 15, draw a circle
        if radius > 2 and distance < platformDiameter:
            cv2.circle(frame, center, int(radius), (0, 255, 0), 1)
            cv2.circle(frame, center, 2, (0,255,0),-1)

            #Temp 
            cv2.setTrackbarPos('x','TT',int(x))
            cv2.setTrackbarPos('y','TT',int(y))
    
    #Show image
    #cv2.imshow('eeey',cv2.resize(mask,(900, 450)))
    cv2.imshow('Image', frame)

    #Break if esc key is pressed
    if(cv2.waitKey(1) == 27):
        break; 

cv2.destroyAllWindows()
