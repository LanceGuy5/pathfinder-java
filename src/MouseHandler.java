import NodeTypes.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.LinkedList;

public class MouseHandler extends MouseAdapter {

    DrawNodes d;
    KeyHandler k;
    Panel p;

    int x, y;
    int[] clickCoord = new int[2];

    /**
     * Constructor
     * @param d DrawNodes object used
     * @see DrawNodes
     * @param k KeyHandler object used
     * @see KeyHandler
     * @param p Panel object used
     * @see Panel
     */
    public MouseHandler(DrawNodes d, KeyHandler k, Panel p){
        this.d = d;
        this.k = k;
        this.p = p;
    }

    /**
     * Test method
     */
    public void tick(){

    }

    /**
     * Does stuff based on the mouse's release
     * @param e Information about the mouse when it is realsed
     */
    @Override
    public void mouseReleased(MouseEvent e){
        x = e.getX();
        y = e.getY();
        clickCoord = turnClickLocationToCoordinate(x, y);
        if(p.getActivated()){
            if(!inLimit(30, 720, 30 + 250, 720 + 150, x, y)){
                if (k.keysDown[0]) {
                    if (!d.hasStart()) {
                        d.replaceNode(new Start(clickCoord[0], clickCoord[1]));
                    } else {
                        //TODO get working
//                JOptionPane.showMessageDialog(null, "You already have a Start. \n" +
//                        "Delete your old one to make a new one.",
//                        "Alert", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (k.keysDown[1]) {
                    if (!d.hasGoal()) {
                        d.replaceNode(new Goal(clickCoord[0], clickCoord[1]));
                    } else {
                        //TODO get working
//                JOptionPane.showMessageDialog(null, "You already have a Goal. \n" +
//                                "Delete your old one to make a new one.",
//                        "Alert", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }else {
            if (k.keysDown[0]) {
                if (!d.hasStart()) {
                    d.replaceNode(new Start(clickCoord[0], clickCoord[1]));
                } else {
                    //TODO get working
//                JOptionPane.showMessageDialog(null, "You already have a Start. \n" +
//                        "Delete your old one to make a new one.",
//                        "Alert", JOptionPane.ERROR_MESSAGE);
                }
            } else if (k.keysDown[1]) {
                if (!d.hasGoal()) {
                    d.replaceNode(new Goal(clickCoord[0], clickCoord[1]));
                } else {
                    //TODO get working
//                JOptionPane.showMessageDialog(null, "You already have a Goal. \n" +
//                                "Delete your old one to make a new one.",
//                        "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Does certain stuff based on the mouse being dragged
     * @param e Information about the mouse while dragging
     */
    @Override
    public void mouseDragged(MouseEvent e){
        try {
            if (!k.keysDown[0] && !k.keysDown[1]) {
                if(!KeyHandler.getProgramStarted()) {
                    x = e.getX();
                    y = e.getY();
                    clickCoord = turnClickLocationToCoordinate(x, y);
                    if (inLimit(0, 0, Pathfinder.rlWidth, Pathfinder.rlHeight, x, y)){
                        if (p.getActivated()) {
                            if (!inLimit(30, 720, 30 + 250, 720 + 150, x, y)) {
                                if (SwingUtilities.isLeftMouseButton(e)) {
                                    if (d.getNodeFromList(clickCoord[0], clickCoord[1]).getNodeType().equals(Node.NODETYPE.NORMAL)) {
                                        d.replaceNode(new Wall(clickCoord[0], clickCoord[1]));
                                    }
                                } else if (SwingUtilities.isRightMouseButton(e)) {
                                    if (d.getNodeFromList(clickCoord[0], clickCoord[1]).getNodeType().equals(Node.NODETYPE.WALL)) {
                                        d.replaceNode(new Normal(clickCoord[0], clickCoord[1]));
                                    } else if (d.getNodeFromList(clickCoord[0], clickCoord[1]).getNodeType().equals(Node.NODETYPE.START)) {
                                        int reply = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete your start?", "Warning",
                                                JOptionPane.YES_NO_OPTION);
                                        if (reply == JOptionPane.YES_OPTION) {
                                            d.replaceNode(new Normal(clickCoord[0], clickCoord[1]));
                                        }
                                    } else if (d.getNodeFromList(clickCoord[0], clickCoord[1]).getNodeType().equals(Node.NODETYPE.GOAL)) {
                                        int reply = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete your goal?", "Warning",
                                                JOptionPane.YES_NO_OPTION);
                                        if (reply == JOptionPane.YES_OPTION) {
                                            d.replaceNode(new Normal(clickCoord[0], clickCoord[1]));
                                        }
                                    }
                                }
                            }
                        } else {
                            if (SwingUtilities.isLeftMouseButton(e)) {
                                if (d.getNodeFromList(clickCoord[0], clickCoord[1]).getNodeType().equals(Node.NODETYPE.NORMAL)) {
                                    d.replaceNode(new Wall(clickCoord[0], clickCoord[1]));
                                }
                            } else if (SwingUtilities.isRightMouseButton(e)) {
                                if (d.getNodeFromList(clickCoord[0], clickCoord[1]).getNodeType().equals(Node.NODETYPE.WALL)) {
                                    d.replaceNode(new Normal(clickCoord[0], clickCoord[1]));
                                } else if (d.getNodeFromList(clickCoord[0], clickCoord[1]).getNodeType().equals(Node.NODETYPE.START)) {
                                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete your start?", "Warning",
                                            JOptionPane.YES_NO_OPTION);
                                    if (reply == JOptionPane.YES_OPTION) {
                                        d.replaceNode(new Normal(clickCoord[0], clickCoord[1]));
                                    }
                                } else if (d.getNodeFromList(clickCoord[0], clickCoord[1]).getNodeType().equals(Node.NODETYPE.GOAL)) {
                                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete your goal?", "Warning",
                                            JOptionPane.YES_NO_OPTION);
                                    if (reply == JOptionPane.YES_OPTION) {
                                        d.replaceNode(new Normal(clickCoord[0], clickCoord[1]));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch(NullPointerException ignored){}
    }

    /**
     * Turns a click into a readable coordinate (int array)
     * @param x X coordinate of the click
     * @param y Y coordinate of the click
     * @return An array readable by the code regarding the location of the click
     */
    public int[] turnClickLocationToCoordinate(int x, int y){
        int zoom = d.getZoom();
        int[] scale = d.scaling();

        int NodeLength = Pathfinder.rlWidth / scale[zoom];

        int nodeX = (x / NodeLength) + 1;
        int nodeY = (y / NodeLength) + 1;

        return new int[]{nodeX, nodeY};
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
    public boolean inLimit(int minX, int minY, int maxX, int maxY, int x, int y){
        if(x > minX && x < maxX){
            return y > minY && y < maxY;
        }
        return false;
    }

}
