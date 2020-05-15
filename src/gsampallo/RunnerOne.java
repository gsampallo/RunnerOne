package gsampallo;


import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.*;
import java.io.File;
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
         * Player
         */
        g.drawImage(player.getImagePlayer(),player.getX(),player.getY(),null);
    }

    public void updateGame() {
        if(pause) {
            return;
        }

        if(player.getState() != Player.STATE_IDLE) {
            background.updateBackground();
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
