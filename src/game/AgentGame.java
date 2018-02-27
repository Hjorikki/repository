package game;

import Config.Config;
import Panel.GameField;
import Panel.Panel;
import Agents.Agent;
import food.Food;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.imageio.ImageIO;

/**Класс , в котором происходит работа потока и всей игры в целом.*/
public class AgentGame extends JPanel implements Runnable{
    /**Связь с классом конфигурации,для получения значений  */ 
    Config con = new Config();
    /**Ширина окна игры  */              
    private final int weigth = con.getWeigth();
    /**Высота окна игры  */   
    private final int height = con.getHeigth();
    /**Адрес подключения к БД */   
    private final String url = con.getUrl();
    /**Поле игры  */   
    private GameField field;
    Image image;
    private Graphics2D g;
    /**Поток*/   
    private  Thread thread ;
    /**Список еды */   
    public static ArrayList<Food> foods ;    
    /**Список агентов */   
    public static ArrayList<Agent> agents;
    
    /**Начальное окно игры.  */ 
    public AgentGame() {
        Agent a = null;
        String str="C:/Users/longh_000/Documents/NetBeansProjects/Game/src/game/Start.jpg";
        Back(str, a);
    }
    
    /**
     *Обрабатывает нажатие кнопок.
     * Space - запускает поток.
     * Escape - останавливает поток.
     * S - приостанавливает запущенный поток.
     * R - возобновляет приостановленный поток.
     */
    public void button(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {}

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_S) suspend();
                if (ke.getKeyCode() == KeyEvent.VK_SPACE) start();
                if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) exit();
                if (ke.getKeyCode() == KeyEvent.VK_R) resume();
            }

            @Override
            public void keyReleased(KeyEvent ke) {}
            
        });
    }
    /**Создает поток и заускает его, тем самым начиная игру.*/
    public void start(){
        thread =new Thread(this);
        thread.start();
    }
    /**
     *Останавливает поток, тем самым заканчивая игру.
     */
    public void exit(){
        Agent a = null;
        String str="C:/Users/longh_000/Documents/NetBeansProjects/Game/src/game/End.jpg";
//        if (agents.size()>1 || agents.size()==0)
//            a = null;
        Back(str, a);
        thread.stop();
    }
    /**Возобновляет работу поток*/
    public void resume(){
        thread.resume();
    }
    /**Приостанавливает работу поток*/
    public void suspend(){
        thread.suspend();
    }
    
    /**Создает агентов на поле, обновляет поле,обращаясь к методам gameUpdate(), gameRender() и gameDraw()*/
    @Override
    public void run() {
        image = new BufferedImage(weigth+10,height+10,BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D)image.getGraphics();

        field = new GameField();
        foods = new ArrayList<>();
        
        agents = new ArrayList<>();
        for(int i = 0;i<5;i++)
            agents.add(new Agent(2,i));
        for(int i = 0;i<8;i++)
            agents.add(new Agent(1,i));
          
        while(true){
            gameUpdate();
            gameRender();
            gameDraw(); 
        }
    }
    
    /**Создает на поле еду,изменяет положение агентов на поле,удаляет их(если уровень энергии ниже необходимого)
     * обрабатывает случаи сталкновения агентов с едой и нахождения на поле только одного агента 
     */
    public void gameUpdate(){
        
        if (Math.random() < 0.009) { 
            foods.add(new Food());
        }
        for(int i = 0; i< agents.size();i++){
            Agent a= agents.get(i);
            a.update();
            System.out.println("Type: "+a.getT()+" ID: "+a.getID()+" Energy: "+a.getE()+"");
            boolean remove = a.remove();
                if(remove){
                    System.out.println("Agent "+a.getID()+" from "+a.getT()+" is dead.");
                    agents.remove(i);
                    i--;
                    break;
                }
        }
        meetfood();
        LastOne();
        
    }
    /**Отрисовывает поле,агентов и еду , тем самым показывая их движение*/
    private void gameRender() {
        field.draw(g);
        for(int i = 0; i< agents.size();i++){
            agents.get(i).draw(g);
            String e =  "Type: "+ agents.get(i).getT() +" ID: "+agents.get(i).getID()+" Energy: "+agents.get(i).getE()+"";
            g.drawString(e, con.getWeigth()-195, 100+i*20);
        }
        for(int i = 0; i< foods.size();i++)
            foods.get(i).draw(g);
    }
    /**Отрисовака поля игры*/
    private void gameDraw() {
        Graphics gd = this.getGraphics();
        gd.drawImage(image,0,0,null);
        gd.dispose();
    }
    /**Обрабатывает столкновение еды и агентов.Еда при этом удаляется, 
     * а энергия агента увеличивается.*/
    private void meetfood() {
        for(int i = 0; i< foods.size();i++){
            Food f= foods.get(i);
            double fx= f.getX();
            double fy= f.getY(); 
            
            for(int j=0;j<agents.size();j++){
                
                Agent a= agents.get(j);
                double ax= a.getX();
                double ay= a.getY();
                
                double dx = fx-ax;
                double dy = fy-ay;
                
                double distanse = Math.sqrt(dx*dx+dy*dy);
                if((int)distanse < f.getR()+a.getR()){
                    a.eat();
                    j--;
                    foods.remove(i);
                    i--;
                    break;
                }            
            }
        }
    }
    /**Обрабаттывает нахождение на поле последнего агента,
     * его результаты сохраняются в БД, а поток останавливается.*/
     private void LastOne(){
        gameRender();
        gameDraw();    
        if(agents.size()==1){
            System.out.println("LAST ONE!!! ");
            Agent a=agents.get(0);
            AgentGame db = new AgentGame();
            if (db.open()){
                db.save(a);
            }
            String str="C:/Users/longh_000/Documents/NetBeansProjects/Game/src/game/End.jpg";
            Back(str,a);
            thread.stop();
        }
     }   
    /**Подключение к БД */   
    Connection c;
    /**Метод для подключения к БД
     * @return
     * true - Если подключение к базе данных прошло успешно.
     */
    boolean open(){
	try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            System.out.println("Connected to database.");
            return true;
            
	}catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
            return false;
	}
    }
    /**Сохраняет данные последнего агента в БД(Его интедефикатор, тип, коодинаты и энергия)*/
    void save(Agent a){
	try{
            int agent = a.getID(),type= a.getT();
            double x= a.getX(),y= a.getY(),energy= a.getE();
            
            String guery ="INSERT INTO game (agent,type,x,y,energy) "+
                    "VALUES ('" + agent + "','" + type + "','" + x + "','" + y + "','" + energy + "');";
            
            Statement state= c.createStatement();
            state.executeUpdate(guery);
            
            System.out.println("Added!");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        try{
            c.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    /**Очищает окно игры,вызывает метод Panel(String query,Agent a) , снова окно заполняет и выводит его
     * @param query  картика, которая будет отрисовывыться на фоне.
     * @param a  агент потока.*/
    private void Back(String str, Agent a){
        removeAll();
        setVisible(false);
        Panel(str,a);
        setVisible(true);
        setFocusable(true);
        requestFocus();
    }
    
    /**Отрисовывает панель при запуске и окончании игры.В случаи конца игры и наличии на поле лишь
     * одного агента, выводит на палель его тип, интедефикатор и уровень энергии.Других случиях выводит 
     * пустую строку.
     * @param query  картика, которая будет отрисовывыться на фоне.
     * @param a  агент потока.*/
    private void Panel(String query,Agent a){
        JLabel label;
        Panel pp = new Panel();
        pp.setLayout(new BorderLayout());
        
        try {
            pp.setImage(ImageIO.read(new File(query)));
        } catch (IOException e) {}
        
        JPanel panel = new JPanel();
        Font font = new Font("Corbel", Font.BOLD, 20);
        Color color =Color.decode("#461A1A");
        label = new JLabel();
        label.setFont(font);
        label.setForeground(color);
        label.setHorizontalAlignment(JLabel.CENTER);
        if(a!=null){
            String data= "* Won agent " +a.getID()+ " from "+a.getT()+" group. "+
                    "His energy : "+ a.getE()+"*";
            label.setText(data);
        }else{
        label.setText("");}
        
        panel.setLayout(new java.awt.GridLayout());
        panel.setOpaque(false);
        pp.add(label);
        pp.add(panel, java.awt.BorderLayout.NORTH);
        pp.setPreferredSize(new Dimension(weigth, height));
        add(pp);
    }
}