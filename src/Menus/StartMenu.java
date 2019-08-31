
package Menus;

import Controller.Algorithm;
import Controller.Controller;
import Controller.Mode;
import GameEngine.GameState.State;
import GameMenu.AbstractMenu;
import GameMenu.MenuItem;
import GameMenu.SubMenuInitializer;

public class StartMenu extends AbstractMenu{

    private Controller controller;
    
    public StartMenu(Controller controller) {
        super(controller);
        this.controller = controller;
    }

    @Override
    public void initiate() {
        
        setTitle("Mode");
        
        addItem(new SubMenuInitializer("Live Update") {
            
            @Override
            public void initiate() {
                                
                addSubMenuItem(new MenuItem("Prim's Algorithm") {
                    @Override
                    public void function() {
                        controller.setWindowTitle("Prim's Algorithm");
                        controller.setMode(Mode.live);
                        controller.setAlgorithm(Algorithm.prim);
                        controller.setState(State.inGame);
                    }
                });

                addSubMenuItem(new MenuItem("Dijkstra's Algorithm") {
                    @Override
                    public void function() {
                        controller.setWindowTitle("Dijkstra's Algorithm");
                        controller.setMode(Mode.live);
                        controller.setAlgorithm(Algorithm.dijkstra);
                        controller.setState(State.inGame);
                    }
                });

                addSubMenuItem(new MenuItem("Main Menu") {
                    @Override
                    public void function() {
                        controller.setWindowTitle("Mode");
                        controller.setState(State.startMenu);
                    }
                });


            }
        });
        
        addItem(new SubMenuInitializer("Debug Algorithm") {
            
            @Override
            public void initiate() {
                                
                addSubMenuItem(new MenuItem("Prim's Algorithm") {
                    @Override
                    public void function() {
                        controller.setWindowTitle("Prim's Algorithm");
                        controller.setMode(Mode.debug);
                        controller.setAlgorithm(Algorithm.prim);
                        controller.setState(State.inGame);
                    }
                });

                addSubMenuItem(new MenuItem("Dijkstra's Algorithm") {
                    @Override
                    public void function() {
                        controller.setWindowTitle("Dijkstra's Algorithm");
                        controller.setMode(Mode.debug);
                        controller.setAlgorithm(Algorithm.dijkstra);
                        controller.setState(State.inGame);
                    }
                });

                addSubMenuItem(new MenuItem("Main Menu") {
                    @Override
                    public void function() {
                        controller.setWindowTitle("Mode");
                        controller.setState(State.startMenu);
                    }
                });


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
