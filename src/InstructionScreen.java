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

    public void tick(){

    }

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
        g.drawString("Press and hold 's' and click to place a start.", 155, 160);
        g.drawString("Press and hold 'g' and click to place a goal.", 155, 240);
        g.drawString("Hold Left-click and drag to place walls.", 180, 320);
        g.drawString("Hold Right-click and drag to remove walls.", 160, 400);
        g.drawString("Press '\\' to toggle the control panel.", 210, 480);
        g.drawString("Pressing 'reset' on the control panel will clear the grid", 75, 580);

        g.drawString("Developed by Lance Hartman", 240, 870);

        //BUTTON:
        g.setFont(f3);
        g.setColor(Color.white);
        g.drawRect(330, 680, 230, 70);
        g.drawString("Continue", 350, 730);

    }

    public boolean clickInLimit(int minX, int minY, int maxX, int maxY, int x, int y){
        if(x > minX && x < maxX){
            return y > minY && y < maxY;
        }
        return false;
    }
}
