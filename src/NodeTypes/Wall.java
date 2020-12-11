package NodeTypes;

import java.awt.*;

public class Wall extends Node{

    double gScore = -2;
    double sScore = -2;

    Color c = Color.black;

    Node parent = null;

    public Wall(int posX, int posY){
        super(posX, posY);
    }

    @Override
    public double getGScore() {
        return gScore;
    }

    @Override
    public void setGScore(double g) {
        this.gScore = g;
    }

    @Override
    public double getSScore() {
        return sScore;
    }

    @Override
    public void setSScore(double s) {
        this.sScore = s;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public void setPosX(int x) {
        this.posX = x;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public void setPosY(int y) {
        this.posY = y;
    }

    @Override
    public NODETYPE getNodeType() {
        return NODETYPE.WALL;
    }

    @Override
    public void setNodeType(NODETYPE n) {

    }

    @Override
    public double getFinalScore() {
        return -2;
    }

    /**
     * @deprecated Unnecessary method
     */
    @Override
    public void setFinalScore(double n) {

    }

    /**
     * @deprecated Unnecessary method
     */
    @Override
    public void setColor(Color c) {

    }

    @Override
    public Color getColor() {
        return c;
    }

    /**
     * @deprecated Unnecessary method
     */
    @Override
    public void setParent(Node n) {
        this.parent = n;
    }

    /**
     * @deprecated Unnecessary method
     */
    @Override
    public Node getParent() {
        return parent;
    }
}
