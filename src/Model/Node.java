
package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Node {
    
    //for drawing the nodes
    private double x, y;
    private final double radius = 13;
    private String number;
    //
    //for constructing a directed graph
    private ArrayList<Node> neighbours; 
    private ArrayList<Node> parents;
    //
    //variables for dijkstra's algorithm
    private double distanceFromSource;
    private Node predecessor; //in the path from source
    
    public Node(int x, int y, int number) {
        this.x = x;
        this.y = y;
        this.number = Integer.toString(number);
        
        this.neighbours = new ArrayList<>();
        this.parents = new ArrayList<>();
        
        reset();
    }
    
    public void reset() {
        this.distanceFromSource = Double.MAX_VALUE;
        this.predecessor = null;
    }
    
    public void resetSource() {
        this.distanceFromSource = 0;
        this.predecessor = null;
    }
    
    public void renderConnections(Graphics2D g) {
        
        g.setColor(Color.white);
        
        for(Node neighbour : neighbours) {
            if(neighbour.getPredecessor() != this && predecessor != neighbour) {
                connect(g, neighbour);
            }
            else if(neighbour.getPredecessor() != this) {
                drawDistance(g, neighbour);
            }
        }
    }
    
    public void renderArrows(Graphics2D g) {
        
        g.setColor(Color.white);
        
        for(Node neighbour : neighbours) {
            if(neighbour.getPredecessor() != this)
                drawArrow(g, neighbour);      
        }
        renderNormal(g);
    }
    
    public void renderPath(Graphics2D g) {
        
        g.setColor(Color.red);
        
        for(Node neighbour : neighbours) {
            if(neighbour.getPredecessor() == this) {
                connect(g, neighbour);
            }
        }      
    }
    
    public void renderPathArrows(Graphics2D g) {
        
        for(Node neighbour : neighbours) {
            if(neighbour.getPredecessor() == this) {
                g.setColor(Color.red);
                drawArrow(g, neighbour);
                neighbour.renderReached(g);
            }
        }      
    }
    
    
    public void drawArrow(Graphics2D g, Node tip) {
                
        double tipX = tip.getX(), tipY = tip.getY();
        double tailX = x, tailY = y;
       
        double theta = Math.atan2(tipY - tailY, tipX - tailX);        
        
        tailX += (radius + 2) * Math.cos(theta);
        tailY += (radius + 2) * Math.sin(theta);
        
        tipX -= (radius + 2) * Math.cos(theta);
        tipY -= (radius + 2) * Math.sin(theta);
        
        double headAngle = theta + Math.toRadians(22);
        
        double x1 = tipX - 14 * Math.cos(headAngle);
        double y1 = tipY - 14 * Math.sin(headAngle);
        
        headAngle = theta - Math.toRadians(22);
        
        double x2 = tipX - 14 * Math.cos(headAngle);
        double y2 = tipY - 14 * Math.sin(headAngle);
        
        g.draw(new Line2D.Double(tipX, tipY, x1, y1));
        g.draw(new Line2D.Double(tipX, tipY, x2, y2));
        
        /*
        double[] pointsX = {tipX, x1, x2};
        double[] pointsY = {tipY, y1, y2};
        
        Path2D path = new Path2D.Double();

        path.moveTo(pointsX[0], pointsY[0]);
        for(int i = 1; i < pointsX.length; ++i) {
           path.lineTo(pointsX[i], pointsY[i]);
        }
        path.closePath();
        
        g.fill(path);
        */
    }
    
    public void connect(Graphics2D g, Node tip) {
        double tipX = tip.getX(), tipY = tip.getY();
        double tailX = x, tailY = y;
        
        double theta = Math.atan2(tipY - tailY, tipX - tailX);        
        double distance = getDistnace(tip);
        
        
        tailX += (radius + 2) * Math.cos(theta);
        tailY += (radius + 2) * Math.sin(theta);
        
        tipX -= (radius + 2) * Math.cos(theta);
        tipY -= (radius + 2) * Math.sin(theta);
        
        double midX = (tipX + tailX) / 2;
        double midY = (tipY + tailY) / 2;
        
        //
        AffineTransform transform = g.getTransform();
        
        String dist = Integer.toString((int)distance);
        
        g.rotate(theta, midX, midY);
        g.drawString(dist, (float)midX - g.getFontMetrics().stringWidth(dist) / 2, (float)midY - 8);
        
        g.setTransform(transform);
        //
        
        g.draw(new Line2D.Double(tipX, tipY, tailX, tailY));
    }
    
    public void drawDistance(Graphics2D g, Node tip) {
        double tipX = tip.getX(), tipY = tip.getY();
        double tailX = x, tailY = y;
        
        double theta = Math.atan2(tipY - tailY, tipX - tailX);        
        double distance = getDistnace(tip);
        
        
        tailX += (radius + 2) * Math.cos(theta);
        tailY += (radius + 2) * Math.sin(theta);
        
        tipX -= (radius + 2) * Math.cos(theta);
        tipY -= (radius + 2) * Math.sin(theta);
        
        double midX = (tipX + tailX) / 2;
        double midY = (tipY + tailY) / 2;
        
        //
        AffineTransform transform = g.getTransform();
        
        String dist = Integer.toString((int)distance);
        
        g.rotate(theta, midX, midY);
        g.drawString(dist, (float)midX - g.getFontMetrics().stringWidth(dist) / 2, (float)midY - 8);
        
        g.setTransform(transform);

    }
    
    public void connectPrim(Graphics2D g, Node tip) {
        g.draw(new Line2D.Double(tip.getX(), tip.getY(), x, y));
    }
    
    public void renderSelected(Graphics2D g) {
        g.setColor(Color.blue);
        g.fill(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2)); 
        g.setColor(Color.black);
        g.drawString(number, (float)x - g.getFontMetrics().stringWidth(number) / 2 - 1, (float)y + g.getFontMetrics().getAscent() / 2 - 2 + 1);
        g.setColor(Color.white);
        g.drawString(number, (float)x - g.getFontMetrics().stringWidth(number) / 2, (float)y + g.getFontMetrics().getAscent() / 2 - 2);
    }
    
    public void renderSource(Graphics2D g) {
        g.setColor(new Color(85, 107, 47));
        g.fill(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
        g.setColor(Color.black);
        g.drawString(number, (float)x - g.getFontMetrics().stringWidth(number) / 2 - 1, (float)y + g.getFontMetrics().getAscent() / 2 - 2 + 1);
        g.setColor(Color.white);
        g.drawString(number, (float)x - g.getFontMetrics().stringWidth(number) / 2, (float)y + g.getFontMetrics().getAscent() / 2 - 2);
    }
    
    public void renderReached(Graphics2D g) {
        g.setColor(Color.red);
        g.fill(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2)); 
        g.setColor(Color.black);
        g.drawString(number, (float)x - g.getFontMetrics().stringWidth(number) / 2 - 1, (float)y + g.getFontMetrics().getAscent() / 2 - 2 + 1);
        g.setColor(Color.white);
        g.drawString(number, (float)x - g.getFontMetrics().stringWidth(number) / 2, (float)y + g.getFontMetrics().getAscent() / 2 - 2);
    }
    
    public void renderNormal(Graphics2D g) {
        g.setColor(Color.gray);
        g.fill(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
        g.setColor(Color.black);
        g.drawString(number, (float)x - g.getFontMetrics().stringWidth(number) / 2 - 1, (float)y + g.getFontMetrics().getAscent() / 2 - 2 + 1);
        g.setColor(Color.white);
        g.drawString(number, (float)x - g.getFontMetrics().stringWidth(number) / 2, (float)y + g.getFontMetrics().getAscent() / 2 - 2);
    }
    
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x - radius, y - radius, radius * 2, radius * 2);
    }
    
    public double getDistnace(Node node) {
        return Math.sqrt(Math.pow(this.x - node.getX(), 2) + Math.pow(this.y - node.getY(), 2));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }
    
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }
    
    public void addNeighbour(Node node) {
        if(!neighbours.contains(node)) {
            neighbours.add(node);
            node.addParent(this);
        }
    }
    
    public void removeNeighbour(Node node) {
        if(neighbours.contains(node)) {
            neighbours.remove(node);
        }
    }
    
    public void addParent(Node node) {
        if(!parents.contains(node)) {
            parents.add(node);
        }
    }
    
    public void removeParent(Node node) {
        if(parents.contains(node)) {
            parents.remove(node);
        }
    }
    
    public void disconnect() {
       for(Node parent : parents) {
            parent.removeNeighbour(this);
        }
       parents.clear();
    }

    public double getDistanceFromSource() {
        return distanceFromSource;
    }

    public void setDistanceFromSource(double distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    public String getNumber() {
        return number;
    }
    
}
