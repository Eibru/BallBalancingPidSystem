import java.awt.image.BufferedImage;

public class SB_frames {
    int width = 0;    //width of the image
    int height = 0;   //height of the image
    BufferedImage image = null;


    public SB_frames(int width, int height, BufferedImage image) {
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
