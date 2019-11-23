# BallBalancingPidSystem
## CVTuning.py
Used to tune the lower and upper bounds of the hsv filter. 

## Com.py
Contains the main python application and all the python threads/processes. 
* servoCom
  * Reads values from UDP and writes the values to the servos using the adafruit_servokit library
* cvCom
  * Finds the ball position relative to the platform using the openCV library
  * Sends the ball position over UDP to the java application
  * Puts the generated frame into the storagebox
* webServer
  * Uses flask library
  * Sends html file to client on connect
  * Streams the content of the storagebox which contains the frames
  * Can receive POST requests from the client to change the setpoint and the PID varables

## Templates 
Contains the html files for the GUI

## Static
Contains the css files for the GUI

## src
Contains the java application
