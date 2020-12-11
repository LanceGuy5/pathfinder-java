package NodeTypes;

import java.awt.*;

public class Start extends Node{

    double gScore;
    double sScore;

    double finalScore = -1;

    Node parent = null;

    Color c = Color.RED;

    public Start(int posX, int posY){
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
        return NODETYPE.START;
    }

    @Override
    public void setNodeType(NODETYPE n) {

    }

    @Override
    public double getFinalScore(){
        return finalScore;
    }

    @Override
    public void setFinalScore(double n){
        this.finalScore = n;
    }

    @Override
    public void setColor(Color c) {
        this.c = c;
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public void setParent(Node n) {
        this.parent = n;
    }

    @Override
    public Node getParent() {
        return parent;
    }

}
