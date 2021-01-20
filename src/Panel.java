import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Panel extends MouseAdapter {

    Pathfinder p;
    DrawNodes d;

    private boolean activated = false;

    private static double timeStamp = 0;
    private static String timeStarted = "--:--:--";
    private static int pathLength = 0;
    private static int FPS;
    private static int ticks;
    private static boolean running = false;

    /**
     * Creates a new panel for specific controls, directions, etc
     * @param p The pathfinder main program
     * @param d The DrawNodes object of the program's instance
     */
    public Panel(Pathfinder p, DrawNodes d){
        this.p = p;
        this.d = d;
    }

    /**
     * Checks if mouse has been released
     * @param e MouseEvent checking status of the click
     */
    @Override
    public void mouseReleased(MouseEvent e){
        if(activated){
            int x = e.getX();
            int y = e.getY();
            if(clickInLimit(40, 730, 40 + 60, 730 + 30, x, y)){
                reset();
            }
        }
    }

    /**
     * Tick method for the Panel
     */
    public void tick(){
        if(activated){
            running = KeyHandler.getProgramStarted();
        }
    }

    /**
     * Render method for the Panel
     * @param g Graphics object being rendered
     */
    public void render(Graphics g){
        if(activated) {
            //Panel:
            g.setColor(Color.RED);
            g.fillRect(30, 720, 250, 150);
            g.setColor(Color.black);
            g.drawRect(30, 720, 250, 150);

            //Button:
            g.setColor(Color.BLACK);
            g.drawRect(40, 730, 60, 30);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(40, 730, 60, 30);
            Font f = new Font("Helvetica", Font.PLAIN, 20);
            g.setFont(f);
            g.setColor(Color.black);
            g.drawString("Reset", 43, 750);

            //Timestamp/other information
            Font f2 = new Font("Helvetica", Font.BOLD, 15);
            g.setFont(f2);
            g.drawString("Time to exec.: " + timeStamp + " ms", 40, 790);
            g.drawString("Run at: " + timeStarted, 40, 808);
            g.drawString("Length of path: " + pathLength, 40, 826);
            g.drawString("FPS: " + FPS + " Ticks: " + ticks, 40, 844);
            if(running){
                g.drawString("Status: Running", 110, 750);
            }else{
                g.drawString("Status: Not running", 110, 750);
            }
        }
    }

    /**
     * A method used to see if a click is within a specific set of parameters
     * @param minX X Negative limit
     * @param minY Y Negative limit
     * @param maxX X Positive Limit
     * @param maxY Y Positive Limit
     * @param x X value of click
     * @param y Y value of click
     * @return If the clock is within the limitations
     */
    public boolean clickInLimit(int minX, int minY, int maxX, int maxY, int x, int y){
        if(x > minX && x < maxX){
            return y > minY && y < maxY;
        }
        return false;
    }

    /**
     * Resets the panel and the graph
     */
    public void reset(){
        d.clearNodes();
        KeyHandler.setProgramStarted(false);
        timeStamp = 0;
        timeStarted = "--:--:--";
        pathLength = 0;
    }

    /**
     * Gets if the panel has been activated
     * @return If the panel is activated
     */
    public boolean getActivated(){
        return activated;
    }

    /**
     * Sets if the panel is activated
     * @param activated If the panel should be active or not
     */
    public void setActivated(boolean activated){
        this.activated = activated;
    }

    /**
     * @param d Sets the time stamp to d
     */
    public static void setTimeStamp(double d){
        timeStamp = d;
    }

    /**
     * @param d Sets the time started to d
     */
    public static void setTimeStarted(String d){
        timeStarted = d;
    }

    /**
     * @param i Sets the path length to i
     */
    public static void setPathLength(int i){
        pathLength = i;
    }

    /**
     * @param i Sets the FPS to i
     */
    public static void setFPS(int i){
        FPS = i;
    }

    /**
     * @param i Sets the ticks to i
     */
    public static void setTicks(int i){
        ticks = i;
    }

}
