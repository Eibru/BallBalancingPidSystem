/**
 * Reads the data from the ballPos storagebox and generates the new servo angles and puts the values into the servoPos storagebox
 */
import java.lang.Math;
public class PidController extends Thread{
    SB_ballPos storageBoxBall;
    SB_servoPos storageBoxServo;

    /**
     * Constructor
     * @param storageBoxBall Storagebox for ball position
     * @param storageBoxServo Storagebox for servo angles
     */
    public PidController(SB_ballPos storageBoxBall, SB_servoPos storageBoxServo){
        this.storageBoxBall = storageBoxBall;
        this.storageBoxServo = storageBoxServo;
    }

    public void run(){
        double l = 14.5;
        double pitch = 1;
        double roll = 1;
        double z0 = 0;
        double d = 0;


        double z1 = ((Math.sqrt(3*l)/6)+d)*Math.sin(pitch)*Math.cos(roll)+(l/2)*Math.sin(roll) + z0;
        double z2 = ((Math.sqrt(3*l)/6)+d)*Math.sin(pitch)*Math.cos(roll)-(l/2)*Math.sin(roll) + z0;
        double z3 = ((-Math.sqrt(3*l)/3)+d)*Math.sin(pitch)*Math.cos(roll) + z0;


    }

    double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }

        return result;
    }

    double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    String matrixToString(double[][] matrix){
        String s = "[";
        for(int i = 0; i < matrix.length; i++){
            s += "[";
            for(int j =0; j < matrix[i].length; j++){
                s += matrix[i][j] + ", ";
            }
            s+="]\n";
        }
        s += "]";
        return s;
    }
}
