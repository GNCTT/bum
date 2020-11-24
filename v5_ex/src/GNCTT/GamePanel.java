package GNCTT;
import GNCTT.states.GameStateManager;
import GNCTT.until.KeyHandler;
import GNCTT.until.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {
    public static int width;
    public static int height;
    public static int oldFrameCount;

    private Thread thread;
    private Boolean running = false;

    private BufferedImage img;
    private Graphics2D g;

    private MouseHandler mouse;
    private KeyHandler key;

    private GameStateManager gsm;

    public GamePanel (int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify (){
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    public void init() {
        running = true;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();

        mouse = new MouseHandler(this);
        key = new KeyHandler(this);

        gsm = new GameStateManager();
    }

    public void run () {
        init();

        final double GAME_HERT = 60.0;
        final double TBU = 1000000000/GAME_HERT;

        final int MUBR = 5;
        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double Target_FPS = 60;
        final double TTBR = 1000000000/Target_FPS;

        int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime/1000000000);
        oldFrameCount = 0;

        while (running) {
            double now = System.nanoTime();
            int updateCount = 0;
            while ( ((now - lastUpdateTime) > TBU) && (updateCount < MUBR) ) {
                update();
                input(mouse, key);
                lastUpdateTime+= TBU;
                updateCount++;
            }

            if ( now - lastUpdateTime > TBU) {
                lastUpdateTime = now - TBU;
            }

            input(mouse, key);
            render();
            draw();
            lastRenderTime = now;
            frameCount++;
            int thisSecond = (int) (lastUpdateTime/1000000000);
            if (thisSecond > lastSecondTime) {
                if (frameCount != oldFrameCount) {
                    System.out.println("newSecond " + thisSecond + " " + frameCount);
                    oldFrameCount = frameCount;
                }
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            while (now - lastRenderTime < TTBR && now - lastUpdateTime < TBU) {
                Thread.yield();
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.out.println("ERROR: yield Thread");
                }
                now = System.nanoTime();
            }
        }
    }

    public void update() {
        gsm.update();
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        gsm.input(mouse, key);
    }

    public void render () {
        if (g != null) {
            g.setColor(new Color(66, 134, 244));
            g.fillRect(0, 0, width, height);
            gsm.render(g);
        }
    }

    public void draw () {
        Graphics g2 = (Graphics) this.getGraphics();
        g2.drawImage(img, 0, 0 , width, height, null);
        g2.dispose();
    }


}
