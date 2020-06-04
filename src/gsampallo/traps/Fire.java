package gsampallo.traps;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Fire extends Trap {

    public Fire(Point initialPoint) {
        super(Trap.FIRE,initialPoint);

        this.width = 16;
        this.height = 32;

        loadImages();
    }

    private BufferedImage imageOff;
    private BufferedImage imageOn;

    private void loadImages() {
        try {

            imageOff = ImageIO.read(new File("image/Traps/Fire/Off.png"));
            imageOn = ImageIO.read(new File("image/Traps/Fire/On (16x32).png"));

        } catch (final Exception e) {
            System.err.println("No se pudieron cargar imagenes de Fire");
            System.err.println(e.getMessage());
        }        
    }

    private int imageNumber = 0;
    private int firePeriodOn = 6;
    private int firePeriodOff = 9;
    private int firePeriodNumber = 0;

    public void updateTrap(boolean move) {
        if(on) {

            if(firePeriodNumber < firePeriodOn) {
                if(imageNumber < (imageOn.getWidth()/width)-1) {
                    imageNumber++;
                } else {
                    imageNumber = 0;
                    firePeriodNumber++;
                }
            } else {
                on = false;
                imageNumber = 0;
                firePeriodNumber = 0;
            }

        } else {

            if(imageNumber < firePeriodOff) {
                imageNumber++;
            } else {
                on = true;
                imageNumber = 0;
                firePeriodNumber = 0;
            }


        }

        if(move) {
            position.x--;
            visible = (position.x > 0);
        }
    }


    public BufferedImage getImage() {
        if(on) {
            int x = imageNumber*width;
            return imageOn.getSubimage(x,0,width,height);
        } else {
            return imageOff;
        }
    }

    public void setPeriodFireOn(int period) {
        this.firePeriodOn = period;
    }

    public void setPeriodFireOff(int period) {
        this.firePeriodOff = period;
    }

}