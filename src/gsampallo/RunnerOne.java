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

import gsampallo.traps.*;

public class RunnerOne extends JFrame implements ActionListener {

    public static int FRAME_WIDTH = 640;
    public static int FRAME_HEIGHT = 480;

    public final int DELAY = 50;

    private Background background;

    private Player player;

    private ArrayList<Fruit> listFruit;
    private ArrayList<Box> listBox;
    private ArrayList<Weapon> listWeapon;
    private ArrayList<Trap> listTrap;

    private Credits showCredits;
    private boolean winCredits = false;
    private int numberWinCredits = 0;
    private int credits = 0;
    private int showCreditNumber = 0;

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
        Fruit fruit = new Fruit(Fruit.APPLE,new Point(250,410));
        Fruit fruit1 = new Fruit(Fruit.APPLE,new Point(280,410));
        Fruit fruit2 = new Fruit(Fruit.APPLE,new Point(310,410));

        listFruit = new ArrayList<Fruit>();
        // listFruit.add(fruit);
        // listFruit.add(fruit1);
        // listFruit.add(fruit2);

        /*
         * BOX
         */
        //Box box = new Box(Box.BOX1,new Point(200,410));

        listBox = new ArrayList<Box>();
        //listBox.add(box);

        /*
         * TRAP
         */
        listTrap = new ArrayList<Trap>();

        Fire fire = new Fire(new Point(200,410));
        listTrap.add(fire);

        Fire fire1 = new Fire(new Point(220,410));
        fire1.setPeriodFireOn(18);
        listTrap.add(fire1);

        /*
         * Weapon
         */
        listWeapon = new ArrayList<Weapon>();

        /*
         * Player
         */
        player = new Player(Player.MASK_DUDE,new Point(50,410));

        /*
         * Credits
         */
        showCredits = new Credits();

        //Timer
        timer = new Timer(DELAY, this);
        timer.start();

        setVisible(true);

    }

    private void drawList(ArrayList itemList,Graphics g) {

        if(!itemList.isEmpty()) {
            Iterator it = itemList.iterator();
            while(it.hasNext()) {
                Element el = (Element)it.next();
                g.drawImage(el.getImage(),el.getX(),el.getY(),null);
            }
        }

    }

    public void paint(Graphics g) {
        g.drawImage(background.getImageBackground(),0,0,null);

        /*
         * Credits
         */
        g.drawImage(showCredits.getScore(),20,50,null);

        /*
         * Fruit
         */
        drawList(listFruit,g);        

        /*
         * Box
         */
        drawList(listBox, g);        

        /*
         * TRAPS
         */
        drawList(listTrap, g);   

        /*
         * WEAPON
         */
        drawList(listWeapon, g);         

        /*
         * Player
         */
        g.drawImage(player.getImage(),player.getX(),player.getY(),null);

        /*
         * Win Credits
         */
        if(winCredits) {

            if(showCreditNumber < 3) {
                
                g.drawImage(showCredits.getPlusOne(),player.getX()+player.getWidth(),player.getY()-3,null);
                g.drawImage(showCredits.getCreditNumber(numberWinCredits),player.getX()+player.getWidth()+showCredits.getWidth(),player.getY()-3,null);

                showCreditNumber++;
            } else {
                winCredits = false;
                showCreditNumber = 0;
            }

        }
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
                        winCredits = true;
                        numberWinCredits = fruit.getCreditValue();
                        credits = credits + fruit.getCreditValue();
                        showCredits.updateScore(credits);
                        fruit.setCollected(true);

                    }
                }
                
                fruit.updateFruit(move);

                if(!fruit.isVisible()) {
                    it.remove();
                }

            }
        }


        /*
         * Box
         */
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
         * Traps
         */
        if(!listTrap.isEmpty()) {
            Iterator it = listTrap.iterator();
            while(it.hasNext()) {
                Trap trap = (Trap)it.next();
                trap.updateTrap(move);
                if(!trap.isVisible()) {
                    it.remove();
                }
            }
        }

        /*
         * WEAPON
         */
        if(!listWeapon.isEmpty()) {
            Iterator it = listWeapon.iterator();
            while(it.hasNext()) {
                Weapon weapon = (Weapon)it.next();

                    if(!listBox.isEmpty()) {
                        Iterator it1 = listBox.iterator();
                        while(it1.hasNext()) {
                            Box box = (Box)it1.next();
                            
                            if(isHorizontalColision(weapon,box,0)) {
                                box.setBreak();
                                weapon.setVisible(false);

                                break;
                            }
                        }
                    }                

                weapon.updateWeapon(move);

                if(!weapon.isVisible()) {
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


    public void shootWeapon() {
        Weapon weapon = new Weapon(new Point(player.getX(),player.getY()+6));
        listWeapon.add(weapon);
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

            /*
             * FIRE
             */
            } else if(e.getKeyCode() ==  KeyEvent.VK_SPACE) {
                shootWeapon();
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
