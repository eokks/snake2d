import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

class Main extends JFrame{

    static ArrayList<Byte> play = new ArrayList<>();
    static ArrayList<Integer> time = new ArrayList<>();
    static JFrame window = new JFrame();
    static ArrayList<ArrayList<JLabel>> characters = new ArrayList<>();
    static JLabel apple =new JLabel(new ImageIcon("snake.jpg"));
    static HashMap<Integer, String> sprites = new HashMap<>();
    static ArrayList<Integer> x = new ArrayList<>();
    static ArrayList<Integer> y = new ArrayList<>();
    static int applex = 450;
    static int appley = 450;
    static ArrayList<Integer> xplus = new ArrayList<>();
    static ArrayList<Integer> yplus = new ArrayList<>();
    static int direction;
    static ArrayList<JLabel> ochko = new ArrayList<>();

    public static void placeApple(int i, int lastx,int lasty){
            applex = (int)(Math.random() * 900);
            appley = (int)(Math.random() * 690);
            applex = applex - applex % 30;
            appley = appley - (appley % 30);
            apple.setLocation(applex,appley);
            characters.get(i).add(new JLabel(new ImageIcon("snake_body.jpg")));
            window.add(characters.get(i).get(characters.get(i).toArray().length-1));
            characters.get(i).get(characters.get(i).toArray().length-1).setLocation(lastx,lasty);
            time.set(i, time.get(i)*19/20);
    }
    public static void changeplus(int changex, int changey, int direct, int index){
        if(play.get(index) == 0){
            play.set(index, (byte) 2);
        }
        xplus.set(index,changex);
        yplus.set(index,changey);
        direction = direct;
    }

    public static void moveCharacter(int i){
        if(play.get(i) == 2){
            int xp = xplus.get(i);
            int yp = yplus.get(i);
            ArrayList<ArrayList<Integer>> locations = new ArrayList<>();
                locations.add(new ArrayList<>());
                for (int snakepartindex = 0; snakepartindex < characters.get(i).size(); snakepartindex++) {
                    locations.add(new ArrayList<>());
                    locations.get(snakepartindex).add(characters.get(i).get(snakepartindex).getX());
                    locations.get(snakepartindex).add(characters.get(i).get(snakepartindex).getY());
                }

            for(int timer = 1; timer < 11; timer++){
                for(int snakepart_pozer = characters.get(i).toArray().length-1; snakepart_pozer > 0; snakepart_pozer--){
                    int myX =locations.get(snakepart_pozer).get(0);
                    int myY =locations.get(snakepart_pozer).get(1);
                    int needX =locations.get(snakepart_pozer-1).get(0);
                    int needY =locations.get(snakepart_pozer-1).get(1);
                    characters.get(i).get(snakepart_pozer).setLocation((myX/10*(10-timer)) + needX/10*timer,(myY/10*(10-timer)) + needY/10*timer);
                }
                characters.get(i).get(0).setLocation(x.get(i)/10*(10-timer) + (x.get(i)+xp)/10*timer,(y.get(i)/10*(10-timer)) + (y.get(i)+yp)/10*timer);
                try {
                    Thread.sleep(time.get(i)/10);
                } catch (InterruptedException ignored) {}
            }

            x.set(i,x.get(i) + xp);
            y.set(i,y.get(i) + yp);

            if(x.get(i) >= applex - 60 && x.get(i) <= applex + 60 && y.get(i) >= appley - 60 && y.get(i) <= appley + 60 ){
                characters.get(i).get(0).setIcon(new ImageIcon(sprites.get(direction * 10 + 1)));
                if(y.get(i) == appley && x.get(i) == applex){
                    ochko.get(i).setText(String.valueOf(characters.get(i).size()));
                    placeApple(i,locations.get(locations.get(i).size() - 1).get(0),locations.get(locations.get(i).size() - 1).get(1));
                }
            } else {
                characters.get(i).get(0).setIcon(new ImageIcon(sprites.get(direction * 10)));
            }
            if(x.get(i)<0||x.get(i)>870||y.get(i)<0||y.get(i)>690){
                for (JLabel character : characters.get(i)) {
                    character.setIcon(new ImageIcon("snake_dead.jpg"));
                    play.set(i, (byte) 1);
                }
            }
        }
    }

    public static void main(String[] args){
        x.add(0);
        y.add(0);
        x.add(210);
        y.add(210);

        Thread thread1 = new Thread(() -> {
            while (true){
                moveCharacter(0);
            }


        });
        Thread thread2 = new Thread(() -> {
            while (true){
                moveCharacter(1);
            }


        });

        Thread snakeListener1 = new Thread(() -> {
            window.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    switch (keyCode) {
                        case KeyEvent.VK_W -> changeplus(0,-30, 1,0);
                        case KeyEvent.VK_A -> changeplus(-30,0, 2,0);
                        case KeyEvent.VK_S -> changeplus(0,30, 3,0);
                        case KeyEvent.VK_D -> changeplus(30,0, 4,0);
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });

        });
        Thread snakeListener2 = new Thread(() -> {
            window.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    switch (keyCode) {
                        case KeyEvent.VK_I -> changeplus(0,-30, 1,1);
                        case KeyEvent.VK_J -> changeplus(-30,0, 2,1);
                        case KeyEvent.VK_K -> changeplus(0,30, 3,1);
                        case KeyEvent.VK_L -> changeplus(30,0, 4,1);
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });

        });

        window.setSize(900,750);
        window.setLayout(null);
        window.setLocationRelativeTo(null);
        for(int i =0;i<2;i++){
            play.add((byte) 0);
            ochko.add(new JLabel("5"));
            window.add(ochko.get(i));
            ochko.get(i).setBounds(i * 210, 210,30,30);
            time.add(200);
            xplus.add(0);
            yplus.add(0);
            characters.add(new ArrayList<>());
            characters.get(i).add(new JLabel(new ImageIcon("snake.jpg")));
            int basex = 210 * i;
            characters.get(i).get(0).setBounds(basex,basex,30,30);
            for(int f = 1; f < 5; f++){
                int basey = basex + f * 30;
                characters.get(i).add(new JLabel(new ImageIcon("snake_body.jpg")));
                window.add(characters.get(i).get(f));
                characters.get(i).get(f).setBounds(basex,basey,30,30);
                characters.get(i).get(f).setLocation(basex,basey);
            }
            window.add(characters.get(i).get(0));
        }
        apple.setIcon(new ImageIcon("apple.jpg"));
        window.add(apple);
        apple.setBounds(applex,appley,30,30);


        sprites.put(10, "snake_up_noapple.jpg");
        sprites.put(11, "snake_up_apple.jpg");
        sprites.put(20, "snake_left_noapple.jpg");
        sprites.put(21, "snake_left_apple.jpg");
        sprites.put(30, "snake_down_noapple.jpg");
        sprites.put(31, "snake_down_apple.jpg");
        sprites.put(40, "snake_right_noapple.jpg");
        sprites.put(41, "snake_right_apple.jpg");
        window.setTitle("snake");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(new JLabel(""));
        thread1.start();
        thread2.start();
        snakeListener1.start();
        snakeListener2.start();
        window.setFocusable(true);
        window.setVisible(true);
    }
}



