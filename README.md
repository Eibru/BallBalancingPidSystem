# BallBalancingPidSystem
## CVTuning.py
Used to tune the lower and upper bounds of the hsv filter. 

## Com.py
Contains the main python application and all the python threads/processes. 
### servoCom
  * Reads values from UDP and writes the values to the servos using the adafruit_servokit library
### cvCom
  * Finds the ball position relative to the platform using the openCV library
  * Sends the ball position over UDP to the java application
  * Puts the generated frame into the storagebox
### webServer
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

### CvCom
Gets the ball position from UDP communication. Puts the value into the ballpos storagebox.

### WebCom 
Gets pid values and setpoints from UDP communication. Puts the values into the pidvalues and setpoint storageboxes.

### PIDController
Reads the ball position, pid values and setpoints from the storageboxes. Uses the values to calculate the pitch and roll angles of the platform, puts the generated values into the platform storagebox.

### InverseKinematics
Reads the values from the platform storagebox and calculates the servo angles to achieve the given pitch and roll of the platform. Puts the calculated values into the servoPos storagebox.

### ServoCom
Sends the content of the servoPos storagebox over UDP


