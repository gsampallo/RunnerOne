package gsampallo;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Box implements Element {

    public static int BOX1 = 0;
    public static int BOX2 = 1;
    public static int BOX3 = 2;

    private int type;
    private Point position;

    private int width = 28;
    private int height = 24;

    private boolean isBreak = false;

    private boolean visible = true;

    public Box(int type,Point initialPoint) {
        this.type = type;
        this.position = initialPoint;

        loadImage();
    }

    private BufferedImage imageBoxIdle;
    private BufferedImage imageBoxBreak;

    public void loadImage() {
        String[] imagesPath = {
            "image/Items/Boxes/Box1/",
            "image/Items/Boxes/Box2/",
            "image/Items/Boxes/Box3/"
        };

        String pathIdle = imagesPath[this.type]+"Idle.png";
        String pathBreak = imagesPath[this.type]+"Break.png";

        try {

            imageBoxIdle = ImageIO.read(new File(pathIdle));
            imageBoxBreak = ImageIO.read(new File(pathBreak));

        } catch (Exception e) {
            System.err.println("No se pudieron cargar imagenes de Box");
            System.err.println(e.getMessage());
        }
        
    }


    private int numberImageBreak = 0;
    public void updateBox(boolean move) {
        if(isBreak) {
            if(numberImageBreak <= (imageBoxBreak.getWidth()/width)) {
                numberImageBreak++;
            } else {
                visible = false;
            }

        } 

        if(move) {
            position.x--;
            visible = (position.x > 0);
        }
    }


    public BufferedImage getBoxImage() {
        if(isBreak) {
            int x = numberImageBreak*width;
            return imageBoxBreak.getSubimage(x, 0, width,height);  
        } else {
            return imageBoxIdle;
        }
    }

   
    public boolean isVisible() {
        return visible;
    }

    public int getX() {
        return this.position.x;
    }

    public int getY() {
        return this.position.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isBreak() {
        return isBreak;
    }

    public void setBreak() {
        isBreak = true;
    }

}