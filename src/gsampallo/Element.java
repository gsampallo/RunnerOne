package gsampallo;

import java.awt.image.*;

public interface Element {
    public int getX();
    public int getY();
    public int getWidth();
    public int getHeight();

    public BufferedImage getImage();
}