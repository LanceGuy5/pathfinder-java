import NodeTypes.Goal;
import NodeTypes.Node;
import NodeTypes.Normal;
import NodeTypes.Start;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.time.Clock;

public class DijkstraSearch implements Runnable{

    /**
     * DrawNodes object used in the program
     */
    DrawNodes d;

    /**
     * KeyHandler to be used in the program
     */
    KeyHandler k;

    /**
     * List of final path of Nodes
     */
    public LinkedList<Node> finalPath = new LinkedList<>();

    /**
     * Constructor
     * @param d DrawNodes object
     * @see DrawNodes
     * @param k KeyHandler object
     * @see KeyHandler
     */
    public DijkstraSearch(DrawNodes d, KeyHandler k){
        this.d = d;
        this.k = k;
    }

    /**
     * Run method - made as a new thread.
     * Used for timing and data analysis of the program.
     */
    @Override
    public void run(){
        if(d.hasStart() && d.hasGoal()){
            Node start = d.getStart();
            Node goal = d.getGoal();
            Thread runAlg = new Thread(() -> algorithm(start, goal));
            runAlg.start();
            long nano1 = System.currentTimeMillis();
            LocalDateTime now =  LocalDateTime.now();
            LocalDateTime roundFloor =  now.truncatedTo(ChronoUnit.SECONDS);
            String s = "" + roundFloor;
            for(int i = 0; i < s.length(); i++)
                if(s.charAt(i) == 'T') s = s.substring(i + 1);
            Panel.setTimeStarted(s);
            while(true) {
                if (!runAlg.isAlive()) {
                    long nano2 = System.currentTimeMillis();
                    Panel.setTimeStamp(nano2 - nano1);
                    break;
                }
            }
        }else{
            // TODO state that there needs to be a start and a goal
        }
    }

    /*********      ALGORITHMS     **************/

    /**
     * Main algorithm - Dijkstra Search
     * @param start The starting Node
     * @param goal The goal Node
     */
    public void algorithm(Node start, Node goal){
        List<Node> open = new ArrayList<>();
        List<Node> closed = new ArrayList<>();
        open.add(start);
        while(true){
            try {
                Node current = open.get(0);
                for (Node n : open) {
                    if (n.getFinalScore() < current.getFinalScore()) {
                        current = n;
                    }
                }
                open.remove(current);
                closed.add(current);
                if (current.equals(goal)) {
                    search(current);
                    break;
                }
                List<Node> neighbors = calcCloseNodes(current);
                for (Node n : neighbors) {
                    if (n != null) {
                        if (!n.getNodeType().equals(Node.NODETYPE.WALL) && !closed.contains(n)) {
                            double newPath = n.getGScore() + calculateScores(n, current);
                            if (newPath < n.getGScore() || !open.contains(n)) {
                                n.setFinalScore(newPath + getDistance(start, n));
                                n.setParent(current);
                                if (!open.contains(n)) {
                                    open.add(n);
                                }
                            }
                        }
                    }
                }
                for (Node n : open){
                    if(!n.getNodeType().equals(Node.NODETYPE.START) && !n.getNodeType().equals(Node.NODETYPE.GOAL)) {
                        n.setColor(Color.DARK_GRAY);
                    }
                }
                for (Node n : closed){
                    if(!n.getNodeType().equals(Node.NODETYPE.START) && !n.getNodeType().equals(Node.NODETYPE.GOAL)) {
                        n.setColor(Color.LIGHT_GRAY);
                    }
                }
            }catch(IndexOutOfBoundsException ignored){
                JOptionPane.showMessageDialog(null, "No path exists.",
                        "Alert", JOptionPane.ERROR_MESSAGE);
                d.clearNodes();
                KeyHandler.setProgramStarted(false);
                break;
            }
        }

        List<Node> tempFinalPath = new LinkedList<>();
        for(int i = finalPath.size() - 1; i >= 0; i--){
            tempFinalPath.add(finalPath.get(i));
        }
        for (Node n : tempFinalPath){
            n.setColor(Color.blue);
        }
        Panel.setPathLength(finalPath.size());
    }

    /**
     * A method that searches for the final path.
     * Uses parent Nodes to trace the path back from the final Node to the starting Node
     * @param n The Node being searched
     */
    public void search(Node n){
        if(!n.getParent().getNodeType().equals(Node.NODETYPE.START)) {
            finalPath.add(n.getParent());
            search(n.getParent());
        }
    }


    /*********      UTILITIES     **************/

    /**
     * Calculates all Nodes surrounding the target Node
     * @param n Node being searched
     * @return A LinkedList of the surrounding Nodes
     */
    public LinkedList<Node> calcCloseNodes(Node n){
        LinkedList<Node> closeNodes = new LinkedList<>();
        if(d.getNodeFromList(n.getPosX() - 1, n.getPosY()) != null) {
            closeNodes.add(d.getNodeFromList(n.getPosX() - 1, n.getPosY()));
        }
        if(d.getNodeFromList(n.getPosX() + 1, n.getPosY()) != null) {
            closeNodes.add(d.getNodeFromList(n.getPosX() + 1, n.getPosY()));
        }
        if (checkCorner(d.getNodeFromList(n.getPosX() + 1, n.getPosY()), d.getNodeFromList(n.getPosX(), n.getPosY() - 1))) {
            if(d.getNodeFromList(n.getPosX() + 1, n.getPosY() - 1) != null) {
                closeNodes.add(d.getNodeFromList(n.getPosX() + 1, n.getPosY() - 1));
            }
        }
        if (checkCorner(d.getNodeFromList(n.getPosX() - 1, n.getPosY()), d.getNodeFromList(n.getPosX(), n.getPosY() + 1))) {
            if(d.getNodeFromList(n.getPosX() - 1, n.getPosY() + 1) != null) {
                closeNodes.add(d.getNodeFromList(n.getPosX() - 1, n.getPosY() + 1));
            }
        }
        if(d.getNodeFromList(n.getPosX(), n.getPosY() - 1) != null) {
            closeNodes.add(d.getNodeFromList(n.getPosX(), n.getPosY() - 1));
        }
        if(d.getNodeFromList(n.getPosX(), n.getPosY() + 1) != null) {
            closeNodes.add(d.getNodeFromList(n.getPosX(), n.getPosY() + 1));
        }
        if (checkCorner(d.getNodeFromList(n.getPosX() - 1, n.getPosY()), d.getNodeFromList(n.getPosX(), n.getPosY() - 1))) {
            if(d.getNodeFromList(n.getPosX() - 1, n.getPosY() - 1) != null) {
                closeNodes.add(d.getNodeFromList(n.getPosX() - 1, n.getPosY() - 1));
            }
        }
        if (checkCorner(d.getNodeFromList(n.getPosX() + 1, n.getPosY()), d.getNodeFromList(n.getPosX(), n.getPosY() + 1))) {
            if(d.getNodeFromList(n.getPosX() + 1, n.getPosY() + 1) != null) {
                closeNodes.add(d.getNodeFromList(n.getPosX() + 1, n.getPosY() + 1));
            }
        }
        return closeNodes;
    }

    /**
     * Method to get the algorithmic distance between Nodes
     * @param a The first Node
     * @param b The second Node
     * @return The distance between Nodes a and b
     */
    public double getDistance(Node a, Node b){
        int distanceX = Math.abs(a.getPosX() - b.getPosX());
        int distanceY = Math.abs(a.getPosY() - b.getPosY());
        if(distanceX > distanceY){
            return 14.0 * (distanceY) + 10.0 * (distanceX - distanceY);
        }else{
            return 14.0 * (distanceX) + 10.0 * (distanceY - distanceX);
        }
    }

    /**
     * Calculates the score algorithmically of a Node (n1) based off of another Node (n2)
     * @param n1 Node one
     * @param n2 Node two
     * @return The score of Node one
     */
    public double calculateScores(Node n1, Node n2){
        int add;
        if(n1.getPosX() == n2.getPosX() || n1.getPosY() == n2.getPosY()){
            add = 10;
        }else{
            add = 14;
        }
        return add;
    }

    /**
     * Checks if a Node's corners are blocked
     * @param s1 The one of the sides touching the corner
     * @param s2 The other side touching the corner
     * @return If the sides touching the corner are both not walls
     */
    public boolean checkCorner(Node s1, Node s2) {
        if(s1 == null || s2 == null) return false;
        return !(s1.getNodeType().equals(Node.NODETYPE.WALL) && s2.getNodeType().equals(Node.NODETYPE.WALL));
    }

}
