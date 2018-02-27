package game;

import Config.Config;
import java.io.IOException;
import javax.swing.*;
/**Класс, в отором создается JFrame и находится main*/
public class Game {
    /**Окно игры JFramе.*/
    public static JFrame start = new JFrame("Agent game(Course Game)");
    public static void main(String[] args) throws IOException {
                
        Config.Config();
       
        AgentGame game = new AgentGame();
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setResizable(false);   
        start.setContentPane(game);
        start.pack();
        start.setLocationRelativeTo(null);
        start.setVisible(true);
        game.button();        
    }
}  