public class SB_servoPos {
    private ServoPos servoPos;

    public SB_servoPos(ServoPos servoPos){
        this.servoPos = servoPos;
    }

    public void setServoPositions(ServoPos servoPos){
        this.servoPos = servoPos;
    }

    public void setServoPositions(double a1, double a2, double a3){
        this.servoPos.setAngle1(a1);
        this.servoPos.setAngle2(a2);
        this.servoPos.setAngle3(a3);
    }

    public ServoPos getServoPos(){
        return this.servoPos;
    }
}
