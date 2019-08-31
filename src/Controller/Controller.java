
package Controller;

import GameEngine.AbstractGame;
import Menus.PauseMenu;
import Menus.StartMenu;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

public class Controller extends AbstractGame{
    
    private Algorithm algorithm;
    private Mode mode;
    //
    private PrimController primController;
    private DijkstraController dijkstraController;

    public Controller(int width, int height, float scale) {
        super(width, height, scale);
    }

    @Override
    public void initiate() {
        setPausable(true);
        setFPSlimited(true, 60);
        setResizable(true);
        setDebugInfoDisplayed(false);
        
        setStartMenu(new StartMenu(this));
        setPauseMenu(new PauseMenu(this));
        
    }

    @Override
    public void update() {
        
        switch(algorithm) {
            case prim:{
                primController.update();
                break;
            }
            case dijkstra:{
                dijkstraController.update();
                break;
            }
        }
        
    }

    @Override
    public void render(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Stroke stroke = g.getStroke();
        g.setStroke(new BasicStroke(3));
        
        Font font = g.getFont();  
        g.setFont(new Font("Arial", Font.BOLD, 15));
        
        switch(algorithm) {
            case prim:{
                if(primController != null) primController.render(g);
                break;
            }
            case dijkstra:{
                if(dijkstraController != null) dijkstraController.render(g);
                break;
            }
        }
        
        g.setStroke(stroke);
        g.setFont(font);

    }
    
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        switch(algorithm) {
            case prim:{
                primController = new PrimController(this);
                break;
            }
            case dijkstra:{
                dijkstraController = new DijkstraController(this);
                break;
            }
        }
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }
    
    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
    
    public void animate() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException ex) {
        }
    }
    
    public void animateShort() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
        }
    }

}
