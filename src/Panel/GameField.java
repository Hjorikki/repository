package Panel;

import Config.Config;
import java.awt.*;
import javax.swing.*;

/**Задает параметры отрисовки фона игры */
public class GameField extends JFrame {
    /**Связь с классом конфигурации,для получения значений  */ 
    Config con = new Config();
    /**Свойство-цвет поля игры с агентами и едой */  
    private final Color color1;
    /**Свойство-цвет поля с отображением информации об агентах */  
    private final Color color;
    /**Назначает цвет фона панели */
    public GameField(){
        color1 =Color.decode("#bbb2a4");
        color =Color.decode("#d1cbc2");
    }
    /**Отрисовывает фон панели игры
     * @param g*/
    public void draw(Graphics2D g){
        g.setColor(color1);
        g.fillRect( 0, 0, con.getWeigth()-200, con.getHeigth()+10);
        g.setColor(color);
        g.fillRect( con.getWeigth()-200, 0, con.getWeigth()+10, con.getHeigth()+10);
    }    
}
