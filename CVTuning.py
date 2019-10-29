import cv2
import math
import numpy as np

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
xMin = 0
xMax = 400 
yMin = 0
yMax = 300

#Define platform center and diameter
platformCenter = [200, 150]
platformRadius = 80

def nothing(value):
    pass

#Create window
cv2.namedWindow('Image')
cv2.namedWindow('Options')

#Define size of image
imgX = 400
imgY = 300

#Create trackbars
cv2.createTrackbar('lower1_h', 'Options', 0, 180, nothing)
cv2.createTrackbar('lower1_s', 'Options', 0, 255, nothing)
cv2.createTrackbar('lower1_v', 'Options', 0, 255, nothing)
cv2.createTrackbar('lower2_h', 'Options', 0, 180, nothing)
cv2.createTrackbar('lower2_s', 'Options', 0, 255, nothing)
cv2.createTrackbar('lower2_v', 'Options', 0, 255, nothing)
cv2.createTrackbar('upper1_h', 'Options', 0, 180, nothing)
cv2.createTrackbar('upper1_s', 'Options', 0, 255, nothing)
cv2.createTrackbar('upper1_v', 'Options', 0, 255, nothing)
cv2.createTrackbar('upper2_h', 'Options', 0, 180, nothing)
cv2.createTrackbar('upper2_s', 'Options', 0, 255, nothing)
cv2.createTrackbar('upper2_v', 'Options', 0, 255, nothing)
cv2.createTrackbar('xMin', 'Options', 0, imgX, nothing)
cv2.createTrackbar('xMax', 'Options', 0, imgX, nothing)
cv2.createTrackbar('yMin', 'Options', 0, imgY, nothing)
cv2.createTrackbar('yMax', 'Options', 0, imgY, nothing)
cv2.createTrackbar('Diameter', 'Options', 0, 200, nothing)
cv2.createTrackbar('PlatformX', 'Options', 0, imgX, nothing)
cv2.createTrackbar('PlatformY', 'Options', 0, imgY, nothing)
cv2.createTrackbar('x', 'Image', 0, imgX, nothing)
cv2.createTrackbar('y', 'Image', 0, imgY, nothing)

#Set trackbar values
cv2.setTrackbarPos('lower1_h', 'Options', lower1_h)
cv2.setTrackbarPos('lower1_s', 'Options', lower1_s)
cv2.setTrackbarPos('lower1_v', 'Options', lower1_v)
cv2.setTrackbarPos('lower2_h', 'Options', lower2_h)
cv2.setTrackbarPos('lower2_s', 'Options', lower2_s)
cv2.setTrackbarPos('lower2_v', 'Options', lower2_v)
cv2.setTrackbarPos('upper1_h', 'Options', upper1_h)
cv2.setTrackbarPos('upper1_s', 'Options', upper1_s)
cv2.setTrackbarPos('upper1_v', 'Options', upper1_v)
cv2.setTrackbarPos('upper2_h', 'Options', upper2_h)
cv2.setTrackbarPos('upper2_s', 'Options', upper2_s)
cv2.setTrackbarPos('upper2_v', 'Options', upper2_v)
cv2.setTrackbarPos('xMin', 'Options', xMin)
cv2.setTrackbarPos('xMax', 'Options', xMax)
cv2.setTrackbarPos('yMin', 'Options', yMin)
cv2.setTrackbarPos('yMax', 'Options', yMax)
cv2.setTrackbarPos('Diameter', 'Options', platformRadius)
cv2.setTrackbarPos('PlatformX', 'Options', platformCenter[0])
cv2.setTrackbarPos('PlatformY', 'Options', platformCenter[1])

#Start video capture
vs = cv2.VideoCapture(0)

while(True):
    xMin = cv2.getTrackbarPos('xMin','Options')
    xMax = cv2.getTrackbarPos('xMax','Options')
    yMin = cv2.getTrackbarPos('yMin','Options')
    yMax = cv2.getTrackbarPos('yMax','Options')
    platformRadius = cv2.getTrackbarPos('Diameter','Options')
    platformCenter[0] = cv2.getTrackbarPos('PlatformX','Options')
    platformCenter[1] = cv2.getTrackbarPos('PlatformY','Options')

    #Read frame from video capture
    _,frame = vs.read()

    #Resize frame
    frame = cv2.resize(frame,(imgX,imgY))

    #Crop frame
    roi = frame[yMin:yMax,xMin:xMax]
    frame = cv2.bitwise_and(roi,roi)

    #Draw circle around platform
    cv2.circle(frame, (platformCenter[0], platformCenter[1]), platformRadius, (0, 0, 255), 1)

    #Convert to hsv color space
    hsv = cv2.cvtColor(frame,cv2.COLOR_BGR2HSV)

    #Apply circular mask
    m = np.zeros(shape = hsv.shape, dtype = "uint8")
    cv2.circle(m, (platformCenter[0], platformCenter[1]), platformRadius+5, color=(255, 255, 255), thickness=-1)
    hsv = cv2.bitwise_and(hsv,m)

    #Create mask
    m1 = cv2.inRange(hsv, (cv2.getTrackbarPos('lower1_h','Options'), cv2.getTrackbarPos('lower1_s','Options'), cv2.getTrackbarPos('lower1_v','Options')), 
    (cv2.getTrackbarPos('upper1_h','Options'),cv2.getTrackbarPos('upper1_s','Options'),cv2.getTrackbarPos('upper1_v','Options')))
    m2 = cv2.inRange(hsv, (cv2.getTrackbarPos('lower2_h','Options'),cv2.getTrackbarPos('lower2_s','Options'),cv2.getTrackbarPos('lower2_v','Options')), 
    (cv2.getTrackbarPos('upper2_h','Options'),cv2.getTrackbarPos('upper2_s','Options'),cv2.getTrackbarPos('upper2_v','Options')))
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

                #Update the position trackbars which displays the position of the ball rellative to the frame
                cv2.setTrackbarPos('x','Image',center[0])
                cv2.setTrackbarPos('y','Image',center[1])

    #Show image
    cv2.imshow('Image', frame)
    cv2.imshow('Options',mask)

    #Break if esc key is pressed
    if(cv2.waitKey(1) == 27):
        break

cv2.destroyAllWindows()