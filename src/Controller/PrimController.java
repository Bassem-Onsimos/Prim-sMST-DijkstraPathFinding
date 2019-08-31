
package Controller;

import Algorithm.MinimumSpanningTree.Prim;
import Model.Node;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PrimController {
    
    private ArrayList<Node> nodes;
    private Prim prim;
    //
    private Controller controller;
    //
    private boolean moving = false;
    private int mouseX = 0, mouseY = 0;
    //
    private Node selectedNode = null;
    //
    private int numberOfNodes = 0;
    
    public PrimController(Controller controller) {
        this.controller = controller;
        nodes = new ArrayList<>();
        prim = new Prim();        
    }

    public void update() {
        
        if(controller.getInput().isButtonDown(MouseEvent.BUTTON1)) {
            
            if(controller.getMode() == Mode.debug) prim.resetTree();
            
            int x = controller.getInput().getMouseX();
            int y = controller.getInput().getMouseY();
            boolean overlap = false;
            
            for(Node node : nodes) {
                if(node.getBounds().contains(x, y) && !moving) {
                    moving = true;
                    mouseX = x;
                    mouseY = y;
                    selectedNode = node;
                    overlap = true;
                }
            }
            
            if(!overlap) {
                nodes.add(new Node(x, y, numberOfNodes++));
                if(controller.getMode() == Mode.live) prim.constructMST(nodes);
            }
        }
        
        if(controller.getInput().isButtonUp(MouseEvent.BUTTON1)) {
            moving = false;
        }
        
        if(controller.getInput().isButton(MouseEvent.BUTTON1) && moving) {
            
            if(controller.getMode() == Mode.debug) prim.resetTree();
            
            int x = controller.getInput().getMouseX();
            int y = controller.getInput().getMouseY();
            
            if(selectedNode != null && mouseX != x && mouseY != y) {
                if(x < selectedNode.getRadius()) x = (int)selectedNode.getRadius();
                if(x > controller.getWidth() - selectedNode.getRadius()) x = controller.getWidth() - (int)selectedNode.getRadius();                   
                selectedNode.setX(x);
                
                if(y < selectedNode.getRadius()) y = (int)selectedNode.getRadius();
                if(y > controller.getHeight() - selectedNode.getRadius()) y = controller.getHeight() - (int)selectedNode.getRadius();
                selectedNode.setY(y);
                    
                if(controller.getMode() == Mode.live) prim.constructMST(nodes);
                
            }
        }
        
        if(controller.getInput().isButtonDown(MouseEvent.BUTTON3)) {
            
            if(controller.getMode() == Mode.debug) prim.resetTree();
            
            int x = controller.getInput().getMouseX();
            int y = controller.getInput().getMouseY();
            
            for(Node node : nodes) {
                if(node.getBounds().contains(x, y)) {
                    nodes.remove(node);
                    if(controller.getMode() == Mode.live) prim.constructMST(nodes);
                    break;
                }
            }          
        }
        
        if(controller.getMode() == Mode.debug) {
            if(controller.getInput().isKeyDown(KeyEvent.VK_SPACE))
                prim.visualizeMSTConstruction(this);
        } 
  
    }

    public void render(Graphics2D g) {
        
        for(int i = 0; i < nodes.size(); i++) {
            nodes.get(i).renderNormal(g);
        }
        
        prim.render(g);
                
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Controller getController() {
        return controller;
    }
    
    
}
