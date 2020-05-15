package gsampallo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Player {


    public static int MASK_DUDE = 0;
    public static int NINJA_FROG = 1;
    public static int PINK_MAN = 2;
    public static int VIRTUAL_GUY = 3;

    public static int STATE_IDLE = 0;
    public static int STATE_RUN = 1;
    public static int STATE_JUMP = 2;
    public static int STATE_FALL = 3;

    private Point position;
    private int type;
    private int state = STATE_IDLE;

    private int width = 32;
    private int height = 32;

    public Player(int type,Point initialPoint) {
        this.type = type;
        this.position = initialPoint;

        loadImage();

    }

    private BufferedImage imageIdle;
    private BufferedImage imageRun;
    private BufferedImage imageJump;
    private BufferedImage imageFall;
    private BufferedImage imageHit;
    
    private void loadImage() {
        String[] pathImages = {
            "image/characters/Mask Dude/",
            "image/characters/Ninja Frog/",
            "image/characters/Pink Man/",
            "image/characters/Virtual Guy/"
        };      
        
        String pathIdle = pathImages[type]+"Idle.png";
        String pathRun = pathImages[type]+"Run.png";
        String pathJump = pathImages[type]+"Jump (32x32).png";
        String pathFall = pathImages[type]+"Fall (32x32).png";
        String pathHit = pathImages[type]+"Hit (32x32).png";         

        try {

            imageIdle = ImageIO.read(new File(pathIdle));
            imageRun = ImageIO.read(new File(pathRun));
            imageJump = ImageIO.read(new File(pathJump));
            imageFall = ImageIO.read(new File(pathFall));
            imageHit = ImageIO.read(new File(pathHit));
            
        } catch (Exception e) {
            System.err.println("No se pudieron cargar imagenes");
            System.err.println(e.getMessage());
            System.exit(0);
        }

    }

    private int imageReference = 0;
    private int jumpDistance = height;

    public void updatePlayer() {
        if(state == STATE_IDLE) {

            if(imageReference < (imageIdle.getWidth()/width)-1) {
                imageReference++;
            } else {
                imageReference = 0;
            }


        } else if(state == STATE_JUMP) {

            if(position.y > (baseLine-jumpDistance)) {
                position.y = position.y - 2;
            } else {
                fall();
            }

        } else if(state == STATE_FALL) {

            if(position.y < baseLine) {
                position.y = position.y + 2;
            } else {
                state = previousState;
            }

        } else if(state == STATE_RUN) {
            if(imageReference < (imageRun.getWidth()/width)-1) {
                imageReference++;
            } else {
                imageReference = 0;
            }
        
        }
    }

    public BufferedImage getImagePlayer() {
        int x = imageReference*width;
        if(state == STATE_RUN) {
            return imageRun.getSubimage(x,0,width,height);
        } else if(state == STATE_JUMP) {   
            return imageJump;
        } else if(state == STATE_FALL) {   
            return imageFall;            
        } else {
            return imageIdle.getSubimage(x,0,width,height);
        }
    }


    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }


    public void idle() {
        if(state == Player.STATE_RUN) {
            state = STATE_IDLE;
        }
    }

    public void run() {
        if(state == Player.STATE_IDLE) {
            state = STATE_RUN;
        }
    }


    private int previousState = 0;
    private int baseLine;

    public void jump() {
        if((state == Player.STATE_RUN) || (state == Player.STATE_IDLE)) {
            previousState = state;
            state = Player.STATE_JUMP;
            baseLine = position.y;
        }
    }

    public void fall() {
        state = Player.STATE_FALL;
    }

    public int getState() {
        return state;
    }

}
