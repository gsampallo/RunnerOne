package gsampallo;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Fruit implements Element {

    public static int APPLE = 0;
    public static int BANANAS = 1;
    public static int CHERRYS = 2;
    public static int KIWI = 3;
    public static int MELON = 4;
    public static int ORANGE = 5;
    public static int PINEAPPLE = 6;
    public static int STRAWBERRY = 7;

    private Point position;
    private int width = 32;
    private int height = 32;

    private int fruitNumber;

    private boolean visible = true;
    private boolean collected = false;


    public Fruit(int type, Point initialPoint) {
        fruitNumber = type;
        position = initialPoint;

        loadImage();
    }

    public boolean isVisible() {
        return visible;
    }


    private BufferedImage imageFruit;
    private BufferedImage imageCollected;

    public void loadImage() {
        String[] imagesPath = {
            "image/Items/Fruits/Apple.png",
            "image/Items/Fruits/Bananas.png",
            "image/Items/Fruits/Cherries.png",
            "image/Items/Fruits/Kiwi.png",
            "image/Items/Fruits/Melon.png",
            "image/Items/Fruits/Orange.png",
            "image/Items/Fruits/Pineapple.png",
            "image/Items/Fruits/Strawberry.png"
    
        };
        
        try {

            imageFruit = ImageIO.read(new File(imagesPath[this.fruitNumber]));
            imageCollected = ImageIO.read(new File("image/Items/Fruits/Collected.png"));

        } catch (Exception e) {
            System.err.println("No se pudieron cargar imagenes de Fruit");
            System.err.println(e.getMessage());
            System.exit(0);
        }
        
    }


    private int imageNumber = 0;
    public void updateFruit(boolean move) {

        if(collected) {
            if(imageNumber < (imageCollected.getWidth()/width)-1) {
                imageNumber++;
            } else {
                visible = false;
            }
        } else {
            if(imageNumber < (imageFruit.getWidth()/width)-1) {
                imageNumber++;
            } else {
                imageNumber = 0;
            }
        }


        if(move) {
            position.x--;
            visible = (position.x > 0) && visible;
        }

    }


    public BufferedImage getImage() {
        int x = imageNumber*width;
        if(collected) {
            return imageCollected.getSubimage(x, 0, width,height);
        } else {
            return imageFruit.getSubimage(x, 0, width,height);
        }
    }

    @Override
    public int getX() {
        // TODO Auto-generated method stub
        return position.x;
    }

    @Override
    public int getY() {
        // TODO Auto-generated method stub
        return position.y;
    }

    @Override
    public int getWidth() {
        // TODO Auto-generated method stub
        return width;
    }

    @Override
    public int getHeight() {
        // TODO Auto-generated method stub
        return height;
    }


    public void setCollected(boolean collected) {
        this.collected = collected;
        this.imageNumber = 0;
    }

    public boolean isCollected() {
        return collected;
    }

}