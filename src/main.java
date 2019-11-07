/**
 * Main class of the application
 * Creates and starts the threads
 */
public class main {
    public static void main(String args[]){
        //Setup event handlers
        Event eventBallStorageBox = new Event();
        Event eventPlatformAngleStorageBox = new Event();
        Event eventServoStorageBox = new Event();

        //Setup storage boxes
        SB_ballPos ballStorageBox = new SB_ballPos();
        SB_platformAngle platformAngleStorageBox = new SB_platformAngle();
        SB_servoPos servoStorageBox = new SB_servoPos();
        SB_pidValues pidValuesStorageBox = new SB_pidValues();
        SB_setpoint setpointStorageBox = new SB_setpoint();

        //Setup threads
        CamCom camCom = new CamCom(eventBallStorageBox, ballStorageBox);
        PidController pidController = new PidController(eventBallStorageBox, ballStorageBox, eventPlatformAngleStorageBox, platformAngleStorageBox, pidValuesStorageBox, setpointStorageBox);
        InverseKinematics inverseKinematics = new InverseKinematics(eventPlatformAngleStorageBox, platformAngleStorageBox, eventServoStorageBox, servoStorageBox);
        ServoCom servoCom = new ServoCom(eventServoStorageBox, servoStorageBox);
        WebCom webCom = new WebCom(setpointStorageBox, pidValuesStorageBox);

        //Set start angle
        platformAngleStorageBox.setAngle(0,0);
        eventPlatformAngleStorageBox.toggle();

        //Set default pid values
        pidValuesStorageBox.setPidValues(new PidValues(0.13,0.0005,0.0022,0.001));

        //Set default setpoint
        setpointStorageBox.setSetpoint(new Setpoint(0,0));

        //Start threads
        camCom.start();
        pidController.start();
        inverseKinematics.start();
        servoCom.start();
        webCom.start();
    }
}
