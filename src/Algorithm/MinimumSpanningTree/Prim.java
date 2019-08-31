
package Algorithm.MinimumSpanningTree;

import Controller.PrimController;
import Model.Node;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javafx.util.Pair;

public class Prim {
    
    private ArrayList<Pair<Node, Node>> tree;
    
    private Node node1, node2;
    
    public void constructMST(ArrayList<Node> nodes) {
        
        tree = new ArrayList<>();
        
        if(!nodes.isEmpty()) {
            
            ArrayList<Node> reachedNodes = new ArrayList<>();
            ArrayList<Node> unreachedNodes = new ArrayList<>(nodes);

            reachedNodes.add(unreachedNodes.get(0));
            unreachedNodes.remove(0);

            while(!unreachedNodes.isEmpty()) {
                double shortestDistance = Double.MAX_VALUE;

                Node reachedNode = null, unreachedNode = null;

                for(Node reached : reachedNodes) {
                    for(Node unreached : unreachedNodes) {

                        double currentDistance = reached.getDistnace(unreached);

                        if(currentDistance < shortestDistance) {
                            shortestDistance = currentDistance;
                            reachedNode = reached;
                            unreachedNode = unreached;
                        }          
                    }
                }

                tree.add(new Pair(reachedNode, unreachedNode));
                reachedNodes.add(unreachedNode);
                unreachedNodes.remove(unreachedNode);
            }
        }
        
    }
    
    public void visualizeMSTConstruction(PrimController controller) {
        
        ArrayList<Node> nodes = controller.getNodes();
        tree = new ArrayList<>();
        
        if(!nodes.isEmpty()) {
            
            ArrayList<Node> reachedNodes = new ArrayList<>();
            ArrayList<Node> unreachedNodes = new ArrayList<>(nodes);

            reachedNodes.add(unreachedNodes.get(0));
            unreachedNodes.remove(0);

            while(!unreachedNodes.isEmpty()) {
                double shortestDistance = Double.MAX_VALUE;

                Node reachedNode = null, unreachedNode = null;

                for(Node reached : reachedNodes) {
                    for(Node unreached : unreachedNodes) {
                        
                        node1 = reached;
                        controller.getController().animateShort();
                        node2 = unreached;
                        controller.getController().animateShort();
                        
                        double currentDistance = reached.getDistnace(unreached);

                        if(currentDistance < shortestDistance) {
                            shortestDistance = currentDistance;
                            reachedNode = reached;
                            unreachedNode = unreached;
                        }          
                        
                        node1 = node2 = null;
                        
                    }
                    controller.getController().animateShort();
                }
                
                controller.getController().animateShort();
                
                tree.add(new Pair(reachedNode, unreachedNode));
                reachedNodes.add(unreachedNode);
                unreachedNodes.remove(unreachedNode);
                
                controller.getController().animateShort();
                
            }
        }        
    }
    
    public void resetTree() {
        tree = null;
    }
    
    public void render(Graphics2D g) {
                
        if(tree != null) {
            g.setColor(Color.red);
            
            for(int i = 0; i < tree.size(); i++) {
                Pair<Node, Node> pair = tree.get(i);
                if(pair != null) pair.getKey().connect(g, pair.getValue());
            }          
        }
        
        if(node1 != null) node1.renderSelected(g);
        if(node2 != null) node2.renderSelected(g);
        
        g.setColor(Color.blue);
        if(node1 != null && node2 != null && node1 != node2){
            node1.connect(g, node2);
        }
    }
    
}
