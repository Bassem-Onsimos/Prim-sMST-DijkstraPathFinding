
package Algorithm.ShortestPathFinding;

import Controller.DijkstraController;
import Model.Node;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Dijkstra {
    
    private DijkstraController controller;
    private ArrayList<Node> nodes;
    //
    private Node node1;
    private Node node2;
    //
    PriorityQueue<QueueNode> nodesQueue;
    
    public Dijkstra(DijkstraController controller) {
        this.controller = controller;
        this.nodes = controller.getNodes();
    }
    
    public void constructPath() {
        
        Node source = controller.getSourceNode();
                
        nodesQueue = new PriorityQueue<>();
        
        for(Node node : controller.getNodes()) {
            
            if(node == source) node.resetSource();
            else node.reset();
            
            nodesQueue.add(new QueueNode(node, node.getDistanceFromSource()));
        }
        
        while(!nodesQueue.isEmpty()) {
            
            Node node = nodesQueue.poll().getNode();
            
            for(Node neighbour : node.getNeighbours()) {
                double distance = node.getDistanceFromSource() + node.getDistnace(neighbour);
                if(distance < neighbour.getDistanceFromSource()) {
                    neighbour.setDistanceFromSource(distance);
                    neighbour.setPredecessor(node);
                    
                    nodesQueue.removeIf((QueueNode qn) -> (qn.getNode() == neighbour && qn.getDistance() >= distance));
                    
                    nodesQueue.add(new QueueNode(neighbour, distance));
                }
            }            
        }        
    }
    
    public void visualizePathConstruction() {
        
        Node source = controller.getSourceNode();
                
        nodesQueue = new PriorityQueue<>();
        
        for(Node node : controller.getNodes()) {
            
            if(node == source) node.resetSource();
            else node.reset();
            
            nodesQueue.add(new QueueNode(node, node.getDistanceFromSource()));
        }
        
        while(!nodesQueue.isEmpty()) {
            
            /*
            for(QueueNode node : nodesQueue) {
                System.out.println("Node: " + node.getNode().getNumber() + ", Distance From Source = " + node.distance);
            }
            System.out.println("--------------");
            */
            Node node = nodesQueue.poll().getNode();
            node1 = node;
            controller.getController().animate();
            
            for(Node neighbour : node.getNeighbours()) {
                node2 = neighbour;
                controller.getController().animate();
                
                double distance = node.getDistanceFromSource() + neighbour.getDistnace(node);
                if(distance < neighbour.getDistanceFromSource()) {
                    neighbour.setDistanceFromSource(distance);
                    neighbour.setPredecessor(node);
                    
                    ArrayList<QueueNode> list = new ArrayList<>();
                    
                    nodesQueue.removeIf((QueueNode qn) -> (qn.getNode() == neighbour && qn.getDistance() >= distance));
                    
                    nodesQueue.add(new QueueNode(neighbour, distance));
                    
                    node2 = null;
                    controller.getController().animate();
                }
            }    
            node1 = node2 = null;
        }  
        
    }
    
    public void resetPath() {
        for(Node node : nodes) {
            node.reset();
        }
    }
    
    private class QueueNode implements Comparable<QueueNode>{
        private Node node;
        private double distance;
        
        public QueueNode(Node node, double distance) {
            this.node = node;
            this.distance = distance;
        }
        
        @Override
        public int compareTo(QueueNode node) {
            return Double.compare(this.distance, node.distance);
        }   

        public Node getNode() {
            return node;
        }

        public double getDistance() {
            return distance;
        }
        
    }
    
    public void render(Graphics2D g) {
        
        for(int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if(node != null) node.renderConnections(g);
        }
        
        for(int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if(node != null) node.renderPath(g);
        }
        
        for(int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if(node != null) node.renderArrows(g);
        }
        
        for(int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if(node != null) node.renderPathArrows(g);
        }
        
        if(node1 != null) node1.renderSelected(g);
        if(node2 != null) node2.renderSelected(g);
        
        g.setColor(Color.blue);
        if(node1 != null && node2 != null && node1 != node2){
            node1.connect(g, node2);
            node1.drawArrow(g, node2);
        }
        
        if(nodesQueue != null) {
            
        }
        
    }
    
}
