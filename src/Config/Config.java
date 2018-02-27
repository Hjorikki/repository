
package Config;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**Класс-посредник между файлом конфигурации и другими классами.Передает значения параметров.*/
public class Config {
    /** ширина JFrame  */  
    private static int WEIGTH;
    /**высота JFrame  */  
    private static int HEIGTH;
    /**Энергия агента */   
    private static double Energy;
    /**Скорость агента 1 типа*/   
    private static double Speed1;
    /**Скорость агента 2 типа*/   
    private static double Speed2;
    /**Минимальный уровень энергии агента */
    private static double Min_energy;
    /**Уменьшения уровня энеригии агента при движении*/
    private static double Energy_Lost;
    /**Адрес подключения к БД*/
    private static String Url;
    /**Значение увеличения уровня энеригии при столкновении с едой */  
    private static double Feed;
    /**Значение уменьшения уровня энергии агента 1 типа при столкновении с агентом 2 типа */
    private static double CollisionA;
    /**Значение уменьшения уровня энергии агента 2 типа при столкновении с агентом 1 типа */
    private static double CollisionB;
    
    /***Получает данные их файла конфигурации Config.properties*/
    public static void Config(){
        Properties prop = new Properties();
        try{
            FileInputStream in = new FileInputStream("Config.properties");
            prop.load(new InputStreamReader(in));
            
            Speed1= Double.valueOf(prop.getProperty("SPEED_1"));         
            Speed2= Double.valueOf(prop.getProperty("SPEED_2")); 
            HEIGTH= Integer.valueOf(prop.getProperty("HEIGTH"));            
            WEIGTH= Integer.valueOf(prop.getProperty("WEIGTH"));
            Energy= Double.valueOf(prop.getProperty("ENERGY")); 
            Min_energy= Double.valueOf(prop.getProperty("MIN_ENERGY")); 
            Energy_Lost= Double.valueOf(prop.getProperty("ENERGY_LOST")); 
            Url= prop.getProperty("URL");
            Feed=Double.valueOf(prop.getProperty("FEED"));
            CollisionA=Double.valueOf(prop.getProperty("COLLISION_A"));
            CollisionB=Double.valueOf(prop.getProperty("COLLISION_B"));
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**Возвращает значение ширины из файла конфигурации
     *@return*/
    public int getWeigth(){ return WEIGTH;}

    /**Возвращает значение высоты из файла конфигурации
     *@return*/
    public int getHeigth(){ return HEIGTH;}

    /**Возвращает значение адрес подключения к БД из файла конфигурации
     * @return*/
    public String getUrl(){ return Url;}

    /**Возвращает  начальное значение энергии агента из файла конфигурации
     *@return*/
    public double getEnergy(){return Energy;}

    /**Возвращает минимальное значение энергии агентаиз файла конфигурации
    *@return*/
    public double getMinEnergy(){return Min_energy;}

    /**Возвращает значение потери энергии агента при движении из файла конфигурации
     * @return*/
    public double getEnergyLost(){return Energy_Lost;}

    /**Возвращает значение скорости агента 1 типа из файла конфигурации
     * @return*/
    public double getSpeed_1(){return Speed1;}
    /**Возвращает значение скорости агента 2 типа из файла конфигурации
     *@return*/
    public double getSpeed_2(){ return Speed2;}
    /**Возвращает значение увеличения энергии агента при столкновении с едой из файла конфигурации
     *@return*/
    public double getFeed() { return Feed;}
    /**Возвращает значение уменьшения уровня энергии агента 1 типа при столкновении с агентом другого типа из файла конфигурации
     *@return*/
    public double getCollisionA() { return CollisionA;}
    /**Возвращает значение уменьшения уровня энергии агента 2 типа при столкновении с агентом другого типа из файла конфигурации
     *@return*/
    public double getCollisionB() { return CollisionB;}
}