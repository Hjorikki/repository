package Panel;

import Config.Config;
import javax.swing.*;
import java.awt.*;

/**Задает параметры отрисовки окон начала и конца игры */
public class Panel extends JPanel{
    /**Связь с классом конфигурации,для получения значений  */ 
    Config con = new Config();
    /**Картика, которая будет отображаться на панели  */ 
    private Image image;
    
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    /**Отрисовывает фон панели начала и конца игры 
     *@param g*/
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(image != null){
            g.drawImage(image, 0, 0, con.getWeigth(), con.getHeigth(),null);
        }
    }    
    
}
