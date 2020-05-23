package gsampallo;


import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class RunnerOne extends JFrame implements ActionListener {

    public static int FRAME_WIDTH = 640;
    public static int FRAME_HEIGHT = 480;

    public final int DELAY = 50;

    private Background background;

    private Player player;

    private ArrayList<Fruit> listFruit;
    private ArrayList<Box> listBox;

    private Timer timer;

    private boolean pause = false;

    public RunnerOne() {
        setTitle("RunnerOne");

        this.setSize(FRAME_WIDTH,FRAME_HEIGHT);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width,dim.height/2-this.getSize().height/2);

        addKeyListener(new GameKeys());

        /*
         * Background
         */
        background = new Background("image/bgd1.png");

        /*
         * Fruit
         */
        Fruit fruit = new Fruit(Fruit.APPLE,new Point(150,410));

        listFruit = new ArrayList<Fruit>();
        listFruit.add(fruit);


        /*
         * BOX
         */
        listBox = new ArrayList<Box>();

        Box box1 = new Box(Box.BOX1,new Point(180,410));
        Box box2 = new Box(Box.BOX2,new Point(210,410));
        Box box3 = new Box(Box.BOX3,new Point(240,410));

        listBox.add(box1);
        listBox.add(box2);
        listBox.add(box3);

        /*
         * Player
         */
        player = new Player(Player.MASK_DUDE,new Point(50,410));

        //Timer
        timer = new Timer(DELAY, this);
        timer.start();

        setVisible(true);

    }

    public void paint(Graphics g) {
        g.drawImage(background.getImageBackground(),0,0,null);


        /*
         * Fruit
         */
        if(!listFruit.isEmpty()) {
            Iterator it = listFruit.iterator();
            while(it.hasNext()) {
                Fruit fruit = (Fruit)it.next();
                g.drawImage(fruit.getImageFruit(),fruit.getX(),fruit.getY(),null);
            }
        }   

        /*
         * Box
         */
        if(!listBox.isEmpty()) {
            Iterator it = listBox.iterator();
            while(it.hasNext()) {
                Box box = (Box)it.next();
                g.drawImage(box.getBoxImage(),box.getX(),box.getY(),null);
            }
        }        

        /*
         * Player
         */
        g.drawImage(player.getImagePlayer(),player.getX(),player.getY(),null);
    }



    private boolean isHorizontalColision(Element elA,Element elB,int tolerance) {
        boolean isColision = false;

        if((elA.getX()+elA.getWidth()+tolerance) > elB.getX()) {
            isColision = true;
        }

        return isColision;
    }

    public void updateGame() {
        if(pause) {
            return;
        }

        if(player.getState() != Player.STATE_IDLE) {
            background.updateBackground();
        }


        boolean move = (player.getState() != Player.STATE_IDLE);

        /*
         * Fruit
         */
        if(!listFruit.isEmpty()) {
            Iterator it = listFruit.iterator();
            while(it.hasNext()) {
                Fruit fruit = (Fruit)it.next();
                

                if(isHorizontalColision(player,fruit,-12)) {
                    if(!fruit.isCollected()) {
                        fruit.setCollected(true);
                    }
                }
                
                fruit.updateFruit(move);

                if(!fruit.isVisible()) {
                    it.remove();
                }

            }
        }


        if(!listBox.isEmpty()) {

            Iterator it = listBox.iterator();
            while(it.hasNext()) {
                Box box = (Box)it.next();

                box.updateBox(move);

                if(!box.isVisible()) {
                    it.remove();
                }
            }


        }


        /*
         * Player
         */
        player.updatePlayer();
    }

    public static void main(String[] args) throws Exception {
        RunnerOne runnerOne = new RunnerOne();
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {

        
        updateGame();

        repaint();

    }


    public class GameKeys extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
            
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            } else if(e.getKeyCode() ==  KeyEvent.VK_P) {
                pause = !pause;

            /*
             * RIGHT
             */
            } else if(e.getKeyCode() ==  KeyEvent.VK_RIGHT) {
                player.run();
            } else if(e.getKeyCode() ==  KeyEvent.VK_D) {
                player.run();
            

            /*
             * JUMP
             */
            } else if(e.getKeyCode() ==  KeyEvent.VK_UP) {
                player.jump();
            }
            
        }


        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() ==  KeyEvent.VK_RIGHT) {
                player.idle();
            } else if(e.getKeyCode() ==  KeyEvent.VK_D) {
                player.run();                
            }
        }
    }
}
