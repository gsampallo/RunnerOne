package gsampallo;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Weapon implements Element {

    private int width = 19;
    private int height = 19;

    private Point position;

    private boolean visible = true;

    public Weapon(Point initialPosition) {
        this.position = initialPosition;

        loadImages();
    }


    private BufferedImage imageWeapon;

    private void loadImages() {
        try {

            imageWeapon = ImageIO.read(new File("image/Traps/Saw/On (19x19).png"));

        } catch (Exception e) {
            System.err.println("No se pudieron cargar imagenes de Box");
            System.err.println(e.getMessage());
        }        
    }

    private int imageNumber = 0;

    public void updateWeapon(boolean move) {
        if(imageNumber < (imageWeapon.getWidth()/width)-1) {
            imageNumber++;
        } else {
            imageNumber = 0;
        }

        position.x = position.x + 3;
        visible = (position.x < RunnerOne.FRAME_WIDTH) & visible;
    }


    public BufferedImage getImage() {
        int x = imageNumber*width;
        return imageWeapon.getSubimage(x, 0, this.width,this.height);
    }


    @Override
    public int getX() {
        return position.x;
    }

    @Override
    public int getY() {
        return position.y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean isVisible) {
        this.visible = isVisible;
    }

}