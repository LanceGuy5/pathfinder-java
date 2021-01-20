import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Entire purpose is to display the instructions on how to run the program
 * @author Lance Hartman
 */
public class InstructionScreen extends MouseAdapter {

    Pathfinder p;

    public InstructionScreen(Pathfinder p){
        this.p = p;
    }

    /**
     * Mouse input method
     * @see MouseAdapter
     * @param e MouseEvent being checked
     * @see MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e){
        if(Pathfinder.s.equals(Pathfinder.state.instructions)){
            int x = e.getX();
            int y = e.getY();
            if(clickInLimit(330, 680, 330 + 230, 680 + 70, x, y)){
                Thread t = new Thread(p::init);
                t.start();
                while(true){
                    if(!t.isAlive()){
                        Pathfinder.s = Pathfinder.state.program;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Tick method for loading screen
     * @deprecated
     */
    public void tick(){

    }

    /**
     * Renders all of the text on the loading screen
     * @param g Graphics object being used to render the text
     */
    public void render(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, Pathfinder.rlWidth, Pathfinder.rlHeight);
        Font f = new Font("Helvetica", Font.BOLD, 64);
        Font f2 = new Font("Helvetica", Font.PLAIN, 32);
        Font f3 = new Font("Helvetica", Font.BOLD, 45);

        //TEXT:
        g.setFont(f);
        g.setColor(Color.white);
        g.drawString("Path Finder", 285, 80);
        g.setFont(f2);
        g.drawString("Press and hold 'S' and click to place a start.", 155, 160);
        g.drawString("Press and hold 'G' and click to place a goal.", 155, 220);
        g.drawString("Press 'L' to start the program once there is a start and goal.", 30, 280);
        g.drawString("Hold Left-click and drag to place walls.", 180, 340);
        g.drawString("Hold Right-click and drag to remove walls.", 160, 400);
        g.drawString("Press '\\' to toggle the control panel.", 210, 460);
        g.drawString("Increase/decrease the grid size by scrolling the mouse wheel.", 20, 520);
        g.drawString("Pressing 'reset' on the control panel will clear the grid.", 75, 580);

        g.drawString("Developed by Lance Hartman", 240, 870);

        //BUTTON:
        g.setFont(f3);
        g.setColor(Color.white);
        g.drawRect(330, 680, 230, 70);
        g.drawString("Continue", 350, 730);

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
}
