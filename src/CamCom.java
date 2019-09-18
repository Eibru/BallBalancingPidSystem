/**
 * Gets the ballposition from the python CV program and puts it in a storagebox
 */
public class CamCom extends Thread {
    private SB_ballPos storageBox;

    /**
     *
     * @param storageBox
     */
    public CamCom(SB_ballPos storageBox){
        this.storageBox = storageBox;
    }

    public void run(){

    }
}
