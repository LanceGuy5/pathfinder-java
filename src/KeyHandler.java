import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {

    public boolean[] keysDown = new boolean[2]; //0=S, 1=G

    DrawNodes d;
    Panel p;

    static boolean programStarted = false;

    public KeyHandler(DrawNodes d, Panel p){
        this.d = d;
        this.p = p;
    }

    /**
     * For debugging
     */
    public void tick(){
//        System.out.println(Arrays.toString(keysDown));
    }

    /**
     * Checks to see if either the 's' or 'g' key is pressed, if yes, make true in array
     * @param e KeyEvent used to check which key is actually pressed
     * @see KeyEvent
     * @see KeyAdapter
     */
    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_S){
            keysDown[0] = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_G){
            keysDown[1] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_L){
            if(!programStarted) {
                Thread search = new Thread(new DijkstraSearch(d, this));
                search.start();
                programStarted = true;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_BACK_SLASH){
            p.setActivated(!p.getActivated());
        }
    }

    /**
     * Checks to see if either the 's' or 'g' key is released, if yes, make false in array
     * @param e KeyEvent used to check which key is actually released
     * @see KeyEvent
     * @see KeyAdapter
     */
    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_S){
            keysDown[0] = false;
        }
        else if(e.getKeyCode() == KeyEvent.VK_G){
            keysDown[1] = false;
        }
    }

    public static boolean getProgramStarted(){
        return programStarted;
    }

    public static void setProgramStarted(boolean started){
        programStarted = started;
    }

}
