
package Menus;

import Controller.Algorithm;
import Controller.Controller;
import GameEngine.GameState.State;
import GameMenu.AbstractMenu;
import GameMenu.MenuItem;

public class PauseMenu extends AbstractMenu{
    
    Controller controller;
    
    public PauseMenu(Controller controller) {
        super(controller);
        this.controller = controller;
    }

    @Override
    public void initiate() {
        
        addItem(new MenuItem("Restart") {
            @Override
            public void function() {
                controller.setAlgorithm(controller.getAlgorithm());
                controller.setState(State.inGame);
            }
        });
        
        addItem(new MenuItem("Main Menu") {
            @Override
            public void function() {
                controller.setWindowTitle("Mode");
                controller.setState(State.startMenu);
            }
        });
        
        addItem(new MenuItem("Exit") {
            @Override
            public void function() {
                System.exit(0);
            }
        });
        
    }

}
