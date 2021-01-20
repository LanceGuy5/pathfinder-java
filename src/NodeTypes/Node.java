package NodeTypes;

import java.awt.*;

public abstract class Node{

    /**
     * Positions of the Node in the overall graph
     */
    protected int posX, posY;

    /**
     * Constructor
     */
    public Node(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Types of Nodes
     */
    public enum NODETYPE{
        NORMAL,
        START,
        GOAL,
        WALL
    }

    /**
     * G-Score of the Node - How far it is from the goal
     * @return The G-Score of the Node
     */
    public abstract double getGScore();

    /**
     * Sets the G-Score of the Node
     * @param g The number that the G-Score is being set to
     */
    public abstract void setGScore(double g);

    /**
     * S-Score of the Node - How far it is from the starting node
     * @return The S-Score of the Node
     */
    public abstract double getSScore();

    /**
     * Sets the S-Score of the Node
     * @param s The number that the S-Score is being set to
     */
    public abstract void setSScore(double s);

    /**
     * X-Position - Its x position relative to the Node graph in terms of (1, 1) (Origin of the graph)
     * @return The X-Position of the Node
     */
    public abstract int getPosX();

    /**
     * Sets the X-Position of the Node
     * @param x The number that the X-Position is being set to
     */
    public abstract void setPosX(int x);

    /**
     * Y-Position = Its y position relative to the Node graph in terms of (1, 1) (Origin of the graph)
     * @return The Y-Position of the Node
     */
    public abstract int getPosY();

    /**
     * Sets the Y-Position of the Node
     * @param y The number that the Y-Position is being set to
     */
    public abstract void setPosY(int y);

    /**
     * NODETYPE = The type of Node that the Object is
     * @return The NODETYPE
     */
    public abstract NODETYPE getNodeType();

    /**
     * Sets the NODETYPE of the specific Node object
     * @param n The NODETYPE that the object is adopting
     */
    public abstract void setNodeType(NODETYPE n);

    /**
     * The g-score and the s-score of the Node added together
     * @return The final score of the node
     */
    public abstract double getFinalScore();

    /**
     * Sets the final score of the node to n
     * @param n The integer that the final score is being set to
     */
    public abstract void setFinalScore(double n);

    /**
     * Sets the color of the Node to c
     * @param c The new color of the Node
     */
    public abstract void setColor(Color c);

    /**
     * Gets the current color of the Node
     * @return The Node's color
     */
    public abstract Color getColor();

    /**
     * Sets the parant of the Node
     * @param n The Node that is to be the parent of the current Node
     */
    public abstract void setParent(Node n);

    /**
     * Gets the parent of the current Node
     * @return The parent of the current Node
     */
    public abstract Node getParent();

}
