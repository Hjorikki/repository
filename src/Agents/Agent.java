
package Agents;

import javafx.scene.image.Image;
import java.awt.*;
import static game.AgentGame.agents;
import Config.Config;
import java.util.*;

/**Класс описывающий параметры создания агентов, их движение, взаимодействия с другими обЪектами и отрисовку.*/
public class Agent {
    
    Config c = new Config();
    /**Свойство- ширина поля игры  */  
    private int weigth = c.getWeigth()-200;
    /**Свойство- высота поля игры  */  
    private int height = c.getHeigth();
    /**Свойство- энергия агента */   
    double Energy =c.getEnergy();
    /**Свойство- скорость агента */   
    private double Speed ;
    /**Свойство- скорость агента 2 типа*/   
    private double Speed2 = c.getSpeed_2();
    /**Свойство- скорость агента 1 типа*/   
    private double Speed1 = c.getSpeed_1();
   /**Свойство- минимальный уровень энергии агента */   
    private final double Min_energy = c.getMinEnergy();
    /**Свойство- увеличения уровня энергии при столкновении с едой агента */   
    private final double Feed = c.getFeed();
    /**Свойство- уменьшения уровня энеригии агента при движении*/   
    private final double Energy_Lost = c.getEnergyLost();
    /**Свойство- тип агента */    
    private int type;
    /**Свойство- интедефикатор агента */    
    private int id;
    /**Свойство- потеря энергии агента типа 1, при столкновении с агентов 2 типа */        
    private double CollisionA = c.getCollisionA();
    /**Свойство- потеря энергии агента типа 2, при столкновении с агентов 1 типа */   
    private double CollisionB = c.getCollisionB();
    /**Свойство- координата х агента */       
    private double x;
     /**Свойство- координата y агента */     
    private double y;
     /**Свойство- изменение координаты х агента */     
    private double dx;
    /**Свойство- изменение координаты y агента */  
    private double dy;
    /**Связь с классом конфигурации,для получения значений  */   
    Config con = new Config();
    /**Свойство-радиус агента */  
    private int rad;
    /**Свойство-цвет агента */  
    Color color;
    Image image; 

    /**Создание агента 
     * @param tip
     * @param i
     * Задает параметры агента, предаваемые ему tip и id определяю тип(1 или 2) 
     * и интедефикаторю так же в соответствии с типом задаются цвет,начальные координаты
     * скрорость и радиус агента
     */
    public Agent(int tip,int i){
        
        this.type=tip;
        switch(type){
            case(1):
                id=i;
                color = Color.decode("#5e4032");
                x = Math.random()*weigth;
                y = Math.random()*height;
                rad = 13;
                
                Speed = Speed1;
                
                dx = Math.random()*Speed;
                dy = Math.random()*Speed;
                return;
            case(2):
                id=i;
                color = Color.decode("#ebded8");
                x = Math.random()*weigth;
                y = Math.random()*height;
                rad = 10;
                
                Speed = Speed2;
                
                dx = Math.random()*Speed;
                dy = Math.random()*Speed; 
        }
    }
    
    /**Возвращает значение координаты х
     * @return*/
    public double getX(){return x;}

    /**Возвращает значение координаты у
     * @return*/
    public double getY(){return y;}

    /**Возвращает значение энергии агента 
     * @return*/
    public double getE() {return Energy;}

    /**Возвращает значение радиуса агента
     * @return*/
    public int getR() {return rad;}    

    /**Возвращает значение типа агента
    * @return */
    public int getT() {return type;}

    /**Возвращает значение интедефикатор агента
     * @return*/
    public int getID() {return id;}
    
    /**Увеличивает энергию агенту, при вызове данного метода*/
    public void eat(){
        Energy += Feed;
    }
     
    /**Метод возвращает значение true, если энергия агента меньше минимального уровня
    * @return*/
    public boolean remove(){
        if(Energy <Min_energy )
            return true;
        else return false;
    }
    
    /**Изменяет для агентов их координаты х и у, в соответствии с координатами изменения dx и dy,
     * если новые координаты находятся вне поля игры, dx и dy меняют значения на противоположное,
     * что исключает выход за границы поля.
     * Так же вызывается метод Collision(), который обрабатывает столкановения агентов
     **/
    public void update(){
        x +=dx;
        y += dy;
        if(x<0 && dx<0)dx = -dx;
        if(y<0 && dy<0)dy = -dy;
        if(x>weigth-rad*2 && dx>0)dx = -dx;
        if(y>height-rad*2 && dy>0)dy = -dy;
        Energy -=Energy_Lost;
        
        Collision();          
    }

    /**Отрисовывание агента на поле
     * @param g*/
    public void draw(Graphics2D g){
        g.setColor(color);
        if(type==1)
            g.fillOval((int)(x),(int)(y), rad*2, rad*2);
        else if(type==2)
            g.fillOval((int)(x),(int)(y), rad*2, rad*2);
    }
    /**Потеря агентом энергии при столкновении с агентом другого типа.
     * @param t
     *  тип агента, в зависимости от него агент теряет некоторое кол-во энергии и 
     * изменяет направдение, при столкновении с агентом другой группы 
     */
    public void collisionEnergy(int t) {
        if(t==1){
            Energy -=CollisionA;
            dx =-dx;
            dy =-dy;
        }
        else if(t==2){
            Energy -=CollisionB;
            dx =-dx;
            dy =-dy;
        }
    }
    /**обработывает столкноваения агентов.Если они разных типов, то они меняют направления
     * и терют часть энергии(в зависимости от типа)
     */
    private void Collision() {
        ArrayList<Agent> agents1 = new ArrayList<>();
        ArrayList<Agent> agents2 = new ArrayList<>();
        for(int i=0; i<agents.size();i++){
            Agent a = agents.get(i);
            if(a.getT()==1)
                agents1.add(a);
            else if (a.getT()==2)
                agents2.add(a);
        }
        
        for(int i = 0; i < agents1.size();i++){
            Agent a2= agents1.get(i);
            double a2x= a2.getX();
            double a2y= a2.getY(); 
            
            for(int j=0;j<agents2.size();j++){
                
                Agent a1= agents2.get(j);
                double a1x= a1.getX();
                double a1y= a1.getY();
                
                double dxx = a2x-a1x;
                double dyy = a2y-a1y;
                
                double distanse = Math.sqrt(dxx*dxx+dyy*dyy);
                if((int)distanse < a2.getR()+a1.getR()){
                    a1.collisionEnergy(a1.getT());
                    a2.collisionEnergy(a2.getT());
                    break;
                }            
            }
        }   
    }
}
    