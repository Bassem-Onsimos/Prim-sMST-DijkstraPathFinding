
package Controller;

import Algorithm.ShortestPathFinding.Dijkstra;
import Model.Node;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javafx.util.Pair;

public class DijkstraController {
    
    private ArrayList<Node> nodes;
    private Node selectedNode;
    private Node sourceNode;
    //
    private Dijkstra dijkstra;
    //
    private Controller controller;
    //
    private boolean moving = false;
    private int mouseX = 0, mouseY = 0;
    //
    private int numberOfNodes = 0;
    //
    private Mode mode;
    
    public DijkstraController(Controller controller) {
        this.controller = controller;
        this.mode = controller.getMode();
        
        nodes = new ArrayList<>();
        dijkstra = new Dijkstra(this);
    }
    
    public void update() {
        if(controller.getInput().isButtonDown(MouseEvent.BUTTON1)) {
            
            if(controller.getMode() == Mode.debug) {
                if(sourceNode != null) dijkstra.resetPath();
                sourceNode = null;
            }
            
            int x = controller.getInput().getMouseX();
            int y = controller.getInput().getMouseY();
            boolean overlap = false;
            
            for(Node node : nodes) {
                if(node.getBounds().contains(x, y)) {
                    
                    if(controller.getInput().isKey(KeyEvent.VK_SPACE)) {
                        sourceNode = node;
                        selectedNode = null;
                    }
                    else {
                        if(controller.getInput().isKey(KeyEvent.VK_CONTROL) && selectedNode != null) {
                            if(selectedNode != node) {
                                selectedNode.addNeighbour(node);
                            }
                        }

                        selectedNode = node;
                        moving = true;
                        mouseX = x;
                        mouseY = y;
                    }
                    
                    overlap = true;
                    break;
                }
            }

            if(!overlap) {
                Node newNode = new Node(x, y, numberOfNodes++);
                nodes.add(newNode);
                
                if(controller.getInput().isKey(KeyEvent.VK_SPACE)) {
                    sourceNode = newNode;
                    selectedNode = null;
                    
                }
                else {
                    if(controller.getInput().isKey(KeyEvent.VK_CONTROL) && selectedNode != null) {
                        selectedNode.addNeighbour(newNode);
                    }

                    selectedNode = newNode;
                    moving = true;
                    mouseX = x;
                    mouseY = y;
                }
            }
            
            if(sourceNode != null) { 
                if(controller.getMode() == Mode.debug)
                    dijkstra.visualizePathConstruction();
                else
                    dijkstra.constructPath();
            }
        }
        
        if(controller.getInput().isButtonUp(MouseEvent.BUTTON1)) {
            moving = false;
        }
        
        if(controller.getInput().isButton(MouseEvent.BUTTON1) && moving) {
            int x = controller.getInput().getMouseX();
            int y = controller.getInput().getMouseY();
            
            if(selectedNode != null && mouseX != x && mouseY != y) {
                if(x < selectedNode.getRadius()) x = (int)selectedNode.getRadius();
                if(x > controller.getWidth() - selectedNode.getRadius()) x = controller.getWidth() - (int)selectedNode.getRadius();                   
                selectedNode.setX(x);
                
                if(y < selectedNode.getRadius()) y = (int)selectedNode.getRadius();
                if(y > controller.getHeight() - selectedNode.getRadius()) y = controller.getHeight() - (int)selectedNode.getRadius();
                selectedNode.setY(y);
                
                if(mode == Mode.live && sourceNode != null) dijkstra.constructPath();
            }
        }
        
        if(controller.getInput().isButtonDown(MouseEvent.BUTTON3)) {
            
            int x = controller.getInput().getMouseX();
            int y = controller.getInput().getMouseY();
            
            for(Node node : nodes) {
                if(node.getBounds().contains(x, y)) {
                    nodes.remove(node);
                    node.disconnect();
                    if(selectedNode != null) if(selectedNode == node) selectedNode = null;
                    if(sourceNode != null) if(sourceNode == node) sourceNode = null;
                    break;
                }
            } 
            
            if(mode == Mode.live && sourceNode != null) dijkstra.constructPath();
        }
        
        if(controller.getInput().isKeyDown(KeyEvent.VK_D)) selectedNode = null;
    }
    
    public void render(Graphics2D g) {
        
        dijkstra.render(g);
        
        if(sourceNode != null) sourceNode.renderSource(g);
        if(selectedNode != null) selectedNode.renderSelected(g);
    }
    
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public Controller getController() {
        return controller;
    }
    
}
