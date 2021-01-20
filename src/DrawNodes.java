import NodeTypes.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;

/**
 * The class that is used to draw the nodes onto the screen as well as calculate the number of nodes being run in the program
 * @author Lance Hartman
 */
public class DrawNodes extends MouseAdapter{

    private boolean enabled = true;

    //Nodes
    public LinkedList<Node> Nodes;
    private int[][] coordinate;

    int nodeZoom = 8;
    private static int minZoom;
    int numOfNodes = 8; //DEFAULT NUM OF NODES
    private static final int MAX_ZOOM = 100;

    KeyHandler k;

    public int NodeLength = Pathfinder.rlWidth / scaling()[nodeZoom];

    public Thread loadNodes = new Thread(){
        public void run(){
            translateZoomIntoNodesArray(Nodes);
        }
    };

    /**
     * Constructor
     */
    public DrawNodes(){
//        this.k = k;
        if(Pathfinder.multiplier == 9){
            minZoom = 3;
        }else{
            minZoom = 2;
        }
        Nodes = new LinkedList<>();
        loadNodes.start();
    }

    /**
     * Gets information about the Mouse Wheel
     * @param e Requested parameter from parent class - can be used to access information about the Mouse's Wheel
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        if(Math.signum(e.getWheelRotation()) == 1){
            for(int i = 0; i < e.getScrollAmount(); i++){
                if(enabled) {
                    if (nodeZoom > minZoom) {
                        nodeZoom--;
                    }
                }
            }
        }
        if(Math.signum(e.getWheelRotation()) == -1){
            for(int i = 0; i < e.getScrollAmount(); i++){
                if(enabled) {
                    if (numOfNodes < scaling().length) {
                        nodeZoom++;
                    }
                }
            }
        }
    }

    /**
     * Updates Object
     */
    public void tick() {
        try {
            NodeLength = Pathfinder.rlWidth / scaling()[nodeZoom];
        }catch (ArrayIndexOutOfBoundsException e){
            if(nodeZoom >= scaling().length - 1) {
                while (nodeZoom > scaling().length - 1) {
                    nodeZoom--;
                }
            }
            NodeLength = Pathfinder.rlWidth / scaling()[nodeZoom];
        }
    }

    /**
     * Renders object
     * @param g The Graphics object in the Pathfinder class that will be used to render this Object
     * @see Pathfinder
     */
    public void render(Graphics g){
        try {
            for (Node node : Nodes)
                drawNode(node, g);
        }catch(ConcurrentModificationException ignored){}
        if(nodeZoom >= scaling().length - 1) {
            while (nodeZoom > scaling().length - 1) {
                nodeZoom--;
            }
        }
        drawNodes(scaling()[nodeZoom], g);
    }

    /**
     * Method in which zooming in/out is scaled
     * @return An integer array of all available zoom configurations
     */
    public int[] scaling(){
        LinkedList<Integer> l = new LinkedList<>();
        for(int i = 1; i < MAX_ZOOM; i++) {
            if (Pathfinder.rlWidth % i == 0 && Pathfinder.rlHeight % i == 0) {
                l.add(i);
            }
        }
        int[] retAr = new int[l.size()];
        for(int i = 0; i < l.size(); i++){
            retAr[i] = l.get(i);
        }
        return retAr;
    }

    /**
     * Draws the Node using java.awt.Graphics
     * White for normal, Red for start, Black for wall, Green for goal, Gray for error
     * @param n The Node that is being drawn
     * @param g The Graphics object being used to draw the object
     * @see java.awt.Graphics
     * @see Node
     */
    public void drawNode(Node n, Graphics g){
        g.setColor(n.getColor());
        g.fillRect((n.getPosX() - 1) * NodeLength, (n.getPosY() - 1) * NodeLength, NodeLength, NodeLength);
    }

    /**
     * Turns zoom into an array of nodes for the purpose of drawing, calculations, etc
     * @param Nodes The LinkedList that stores the data for every Node
     */
    public void translateZoomIntoNodesArray(LinkedList<Node> Nodes){
        for(int i = 1; i <= Pathfinder.multiplier * 10; i++){
            for(int j = 1; j <= Pathfinder.multiplier * 10; j++){
                Normal tempNode = new Normal(i, j);
                Nodes.add(tempNode);
            }
        }
    }

    /**
     * The method in which the lines determining the nodes are drawn
     * @param nodeNum The number of nodes per row
     * @param g The Graphics object in the Pathfinder class that will be used to render the lines
     * @see Pathfinder
     */
    public void drawNodes(int nodeNum, Graphics g) {
        g.setColor(Color.BLACK);
        int distance = Pathfinder.rlWidth / nodeNum;
        for(int i = 0; i < nodeNum; i++)
            g.drawLine(i * distance, 0, i * distance, Pathfinder.rlHeight);
        for(int i = 0; i < nodeNum; i++)
            g.drawLine(0, i * distance, Pathfinder.rlWidth, i * distance);
    }

    /**
     * Used to disable scaling while running program
     * @param enabled If scaling is enabled or not
     */
    public void setScalingEnabled(boolean enabled){
        this.enabled = enabled;
    }

    /**
     * Method to access list of Nodes
     * @return The list of Nodes
     */
    public LinkedList<Node> getNodes(){
        return Nodes;
    }

    /**
     * Pulls the data of a specific node from the LinkedList of Nodes
     * @param x The X-Coordinate of the Node being accessed
     * @param y The Y-Coordinate of the Node being accessed
     * @return The Node attempting to be accessed
     */
    public Node getNodeFromList(int x, int y){
        for(Node node : Nodes) {
            if (node.getPosX() == x) {
                if (node.getPosY() == y) {
                    return node;
                }
            }
        }
        return null;
    }

    /**
     * Replaces Node in list with a new Node
     * @param newNode Node that is replacing the old Node
     */
    public void replaceNode(Node newNode){
        for(int i = 0; i < Nodes.size(); i++){
            if(Nodes.get(i).getPosX() == newNode.getPosX() && Nodes.get(i).getPosY() == newNode.getPosY()){
                Nodes.set(i, newNode);
            }
        }
    }

    /**
     * Gets the zoom of the scale
     * @return The zoom of the program
     */
    public int getZoom(){
        return nodeZoom;
    }

    /**
     * Checks if there is a Start Node in the program
     * @return If there is a Start Node in the program
     */
    public boolean hasStart(){
        for (Node node : Nodes) {
            if (node.getNodeType().equals(Node.NODETYPE.START)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the start in the list of nodes
     * @return The start node
     */
    public Node getStart(){
        for (Node node : Nodes) {
            if (node.getNodeType().equals(Node.NODETYPE.START)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Checks if there is a Goal Node in the program
     * @return If there is a Goal Node in the program
     */
    public boolean hasGoal(){
        for (Node node : Nodes) {
            if (node.getNodeType().equals(Node.NODETYPE.GOAL)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the goal in the list of nodes
     * @return The goal node
     */
    public Node getGoal(){
        for (Node node : Nodes) {
            if (node.getNodeType().equals(Node.NODETYPE.GOAL)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Clears the entire Node list
     */
    public void clearNodes(){
        for (Node node : Nodes) {
            if (!node.getNodeType().equals(Node.NODETYPE.NORMAL)) {
                replaceNode(new Normal(node.getPosX(), node.getPosY()));
            }
        }
        for (Node node : Nodes) {
            if (node.getNodeType().equals(Node.NODETYPE.NORMAL)) {
                node.setColor(Color.white);
            }
        }
//        k.setProgramStarted(false);
    }

}
