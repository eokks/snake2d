import javax.swing.*;
import java.util.ArrayList;

public class Snake {
    private Location headLoc;
    private int time;
    private ArrayList<JLabel> snakeparts;
    private byte life = 0;
    private int xplus = 0;
    private int yplus = 0;

    public Snake(int x, int y, int time, int partAmount) {
        headLoc = new Location(x,y);
        this.time = time;

        snakeparts = new ArrayList<>();
        for (int i = 0; i < partAmount; i++) {
            JLabel snakepart = new JLabel();
            snakeparts.add(new JLabel());
        }
    }

    public void changePlus(int xplus, int yplus) {
        if (life == 0) {
            life = (byte) 2;
        }
        this.xplus = xplus;
        this.yplus = yplus;
    }

    public ArrayList makeLocationsArray() {
        ArrayList<Location> locations = new ArrayList<>();
        for (int snakepartIndex = 0; snakepartIndex < snakeparts.size(); snakepartIndex++) {
            locations.add(new Location(snakeparts.get(snakepartIndex).getX(),snakeparts.get(snakepartIndex).getY()));
        }
        return locations;
    }
}
