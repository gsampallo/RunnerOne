package gsampallo;


import java.awt.*;


import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.*;


public class RunnerOne extends JFrame implements ActionListener {

    public static int FRAME_WIDTH = 640;
    public static int FRAME_HEIGHT = 480;

    public final int DELAY = 50;

    private Background background;

    private Player player;

    private Timer timer;

    public RunnerOne() {
        setTitle("RunnerOne");

        this.setSize(FRAME_WIDTH,FRAME_HEIGHT);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width,dim.height/2-this.getSize().height/2);

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
        background.updateBackground();

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
}
