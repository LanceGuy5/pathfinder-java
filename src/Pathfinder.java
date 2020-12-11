import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

/**
 * Finds shortest path from one point to another on a grid using A* Algorithm
 * @author Lance Hartman
 *
 */
public class Pathfinder extends Canvas implements Runnable{

    public static final int multiplier = 9;
    public static final int rlWidth = 100 * multiplier, rlHeight = 100 * multiplier;
    public static final int WIDTH = rlWidth + 16, HEIGHT = rlHeight + 39;
    public static final String name = "Path Finder";

    //Objects
    Panel p;
    DrawNodes d;
    KeyHandler k;
    MouseHandler m;
    InstructionScreen i;

    //Thread stuff
    Thread t;
    boolean isRunning;

    public enum state{
        instructions,
        program;
    }

    public static state s = state.instructions;

    /**
     * Constructor
     */
    public Pathfinder(){
        new Window(WIDTH, HEIGHT, name, this);
        t = new Thread(this);
        start();
    }

    /**
     * Starts program Thread
     * @see java.lang.Thread
     */
    public synchronized void start(){
        if(isRunning) return;
        t.start();
        isRunning = true;
    }

    /**
     * Stops program Thread (not used).
     * @see java.lang.Thread
     */
    public synchronized void stop(){
        if(!isRunning) return;
        try {
            t.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        isRunning = false;
    }

    /**
     * Initializes instructions screen
     */
    public void preInit(){
        i = new InstructionScreen(this);
        this.addMouseListener(i);
    }

    /**
     * Initializes program objects
     */
    public void init(){
        d = new DrawNodes();
        this.addMouseWheelListener(d);
        p = new Panel(this, d);
        this.addMouseListener(p);
        k = new KeyHandler(d, p);
        this.addKeyListener(k);
        m = new MouseHandler(d, k, p);
        this.addMouseListener(m);
        this.addMouseMotionListener(m);
        p.setActivated(true);
    }

    /**
     * Hosts loop that updates/displays all objects in program
     * @see java.lang.Runnable
     */
    @Override
    public void run() {
        Thread preInit = new Thread(this::preInit);
        preInit.start();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                Panel.setFPS(frames);
                Panel.setTicks(updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    /**
     * Updates objects in program
     */
    private void tick(){
        if(s.equals(state.program)) {
            d.tick();
            k.tick();
            m.tick();
            p.tick();
        }else{
            i.tick();
        }
    }

    /**
     * Renders objects in program using java.awt.Graphics
     * @see java.awt.Graphics
     */
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        //RENDER IN BETWEEN HERE

        if(s.equals(state.program)) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            try {
                d.render(g);
                p.render(g);
            } catch (NullPointerException ignored) {
            }
        }else{
            i.render(g);
        }

        //AND HERE

        bs.show();
        g.dispose();
    }
}
