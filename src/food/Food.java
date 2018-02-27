package food;

import Config.Config;
import java.awt.*;

/**Класс описывающий параметры создания еды и его отрисовку*/
public class Food {
    /**Свойство- координата х еды */  
    private final double x;
    /**Свойство- координата y еды */  
    private final double y;
    /**Свойство-радиус еды */  
    private final int rad;
    /**Свойство-цвет еды */  
    Color color;
    /**Связь с классом конфигурации,для получения значений  */ 
    Config con = new Config();
    
    /**Задает цвет, координаты х и у и радиус еды.*/
    public Food(){
        color =Color.decode("#9f8170");
        x = Math.random()*con.getHeigth()-200;
        y = Math.random()*con.getWeigth();
        rad = 15;
    }

    /**Возвращает значение координаты х еды
     * @return*/
    public double getX(){return x;}

    /**Возвращает значение координаты у еды
     * @return*/
    public double getY(){return y;}

    /**Возвращает значение радиуса еды
     * @return */
    public int getR() {return rad;}
    
    /**Отрисовывает еду
     * @param g*/
    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect((int)x,(int) y, rad, rad);
        g.setStroke(new BasicStroke(3));
    }
}
