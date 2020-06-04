package gsampallo;

import java.awt.Dimension;
import java.awt.Point;
import javax.imageio.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

public class Credits {

    public int width = 8;
    public int height = 10;

    public Credits() {
        loadImages();
        updateScore(0);
    }

    private BufferedImage imageCredits;

    private void loadImages() {
        try {

            imageCredits = ImageIO.read(new File("image/Menu/Text/Text (White) (8x10).png"));

        } catch (Exception e) {
            System.err.println("No se pudieron cargar imagenes de Box");
            System.err.println(e.getMessage());
        }
    } 
    
    private BufferedImage imageScore;

    public BufferedImage getCreditNumber(int nro) {
        return imageCredits.getSubimage(nro*width,30, width,height);
    }

    public BufferedImage getPlusOne() {
        return imageCredits.getSubimage(7*width,40, width,height);
    }

    public void updateScore(int score) {
        String strCredits = ""+score;
        char[] listN = strCredits.toCharArray();

        imageScore = new BufferedImage(listN.length*width,height,imageCredits.getType());
        Graphics g = imageScore.createGraphics();

        for(int i=0;i<listN.length;i++) {
            int n = ((int)listN[i]) - 48;
            g.drawImage(getCreditNumber(n), i*width,0,null);
        }
    }

    public BufferedImage getScore() {
        return imageScore;
    }

    public int getWidth() {
        return width;
    }

}