import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

class Main extends JFrame{
    static ArrayList<JLabel> characters = new ArrayList<>();
    JLabel apple =new JLabel(new ImageIcon("snake.jpg"));
    static HashMap<Integer, String> sprites = new HashMap<>();
    static int x = 0;
    static int y = 0;
    static int applex = 150;
    static int appley = 150;
    static int xplus = 0;
    static int yplus = 0;
    static int direction;
    public static void changeplus(int changex, int changey, int direction){
        xplus = changex;
        yplus = changey;
        Main.direction = direction;
    }
    public static void moveCharacter(int delx,int dely){
        for(int i = characters.toArray().length-1; i > 0; i--){
            characters.get(i).setLocation(characters.get(i-1).getLocation());
        }
        x = x + delx;
        y = y + dely;
        if(x >= applex - 2 && x <= applex + 2 && y >= appley - 2 && y <= appley + 2 ){
            characters.get(0).setIcon(new ImageIcon(sprites.get(direction * 10 + 1)));
        }
        characters.get(0).setIcon(new ImageIcon(sprites.get(direction * 10)));
        characters.get(0).setLocation(x, y);
    }

    public static void main(String[] args){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    moveCharacter(xplus,yplus);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });


        JFrame window = new JFrame();
        window.setSize(400,300);
        characters.add(new JLabel(new ImageIcon("snake.jpg")));
        window.add(characters.get(0));
        characters.get(0).setBounds(x,y,30,30);
        for(int i = 1; i < 5; i++){
            characters.add(new JLabel(new ImageIcon("snake_body.jpg")));
            window.add(characters.get(i));
            characters.get(i).setBounds(x,y,30,30);
        }

        window.add(new JLabel(""));

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

        thread.start();
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W -> changeplus(0,-30, 1);
                    case KeyEvent.VK_A -> changeplus(-30,0, 2);
                    case KeyEvent.VK_S -> changeplus(0,30, 3);
                    case KeyEvent.VK_D -> changeplus(30,0, 4);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        window.setFocusable(true);
        window.setVisible(true);
    }
}



