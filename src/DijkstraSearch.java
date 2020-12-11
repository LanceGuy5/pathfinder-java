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

    DrawNodes d;
    KeyHandler k;

    public LinkedList<Node> finalPath = new LinkedList<>();

    public DijkstraSearch(DrawNodes d, KeyHandler k){
        this.d = d;
        this.k = k;
    }

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
            //TODO state that there needs to be a start and a goal
        }
    }

    /*********      ALGORITHMS     **************/

    public void algorithm(Node start, Node goal){
        List<Node> open = new ArrayList<>();
        List<Node> closed = new ArrayList<Node>();
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

    public void search(Node n){
        if(!n.getParent().getNodeType().equals(Node.NODETYPE.START)) {
            finalPath.add(n.getParent());
            search(n.getParent());
        }
    }


    /*********      UTILITIES     **************/

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

    public double getDistance(Node a, Node b){
        int distanceX = Math.abs(a.getPosX() - b.getPosX());
        int distanceY = Math.abs(a.getPosY() - b.getPosY());
        if(distanceX > distanceY){
            return 14.0 * (distanceY) + 10.0 * (distanceX - distanceY);
        }else{
            return 14.0 * (distanceX) + 10.0 * (distanceY - distanceX);
        }
    }

    public double calculateScores(Node n1, Node n2){
        int add;
        if(n1.getPosX() == n2.getPosX() || n1.getPosY() == n2.getPosY()){
            add = 10;
        }else{
            add = 14;
        }
        return add;
    }

    public boolean checkCorner(Node s1, Node s2) {
        if(s1 == null || s2 == null) return false;
        return !(s1.getNodeType().equals(Node.NODETYPE.WALL) && s2.getNodeType().equals(Node.NODETYPE.WALL));
    }

}
