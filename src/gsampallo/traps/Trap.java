package gsampallo.traps;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import gsampallo.Element;

public class Trap implements Element {

    public static int FIRE = 0;

    protected int width;
    protected int height;

    protected int trapType;
    protected Point position;
    protected boolean visible = true;

    protected boolean on = true;

    public Trap(int type,Point initialPoint) {
        this.trapType = type;
        this.position = initialPoint;
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

  
    @Override
    public BufferedImage getImage() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getTrapType() {
        return this.trapType;
    }

    public boolean isVisible() {
        return visible;
    }

    public void updateTrap(boolean move) {

    }

    public void setOn(boolean isOn) {
        this.on = isOn;
    }

    public boolean isOn() {
        return this.on;
    }

}