package gsampallo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Background {

    private Point position;

    public Background(String imageFile) {
        loadImage(imageFile);

        position = new Point(0,background.getHeight()-RunnerOne.FRAME_HEIGHT);

    }

    private BufferedImage background;
    private BufferedImage background1;
    private BufferedImage background2;
    private BufferedImage backgroundTotal;

    public void loadImage(String imageFile) {

        try {
            background = ImageIO.read(new File(imageFile));
        } catch(Exception e) {
            System.err.println(e.getMessage());

        }

    }
 
    private boolean isTwoImage = false;

    public void updateBackground() {
        if((position.x+RunnerOne.FRAME_WIDTH)+1 < background.getWidth()) {
            position.x++;
            isTwoImage = false;
        } else {

            if(position.x < background.getWidth()) {
                position.x++;
                isTwoImage = true;
            } else if(position.x >= background.getWidth()) {
                position.x = 0;
                isTwoImage = false;
            }


        }
    }

    public BufferedImage getImageBackground()  {

        if(!isTwoImage) {
            return background.getSubimage(position.x,position.y,RunnerOne.FRAME_WIDTH,RunnerOne.FRAME_HEIGHT);
        } else {
            int xMax1 = background.getWidth() - position.x;
            int xMax2 = (position.x + RunnerOne.FRAME_WIDTH) - background.getWidth();

            background2 = background.getSubimage(0, position.y,xMax2,RunnerOne.FRAME_HEIGHT);

            if(xMax1 > 0) {
                background1 = background.getSubimage(position.x,position.y,xMax1,RunnerOne.FRAME_HEIGHT);

                return joinImages(background1,background2, xMax1);


            } else {
                return background2;
            }


        }

    }

    private BufferedImage joinImages(BufferedImage image1,BufferedImage image2,int xMin) {
        backgroundTotal = new BufferedImage(RunnerOne.FRAME_WIDTH,RunnerOne.FRAME_HEIGHT,background.getType());

        Graphics g = backgroundTotal.getGraphics();

        g.drawImage(image1,0,0,null);
        g.drawImage(image2,xMin,0,null);
        
        return backgroundTotal;
    }

}