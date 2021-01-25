package sample.java.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Grid {
    public static final Color ROAD_COLOR = Color.PURPLE;
    private final ArrayList<Rectangle> rectangleGrid;
    private final ArrayList<TrafficLight> trafficList;
    private void createRectangle(double v,double v1, double v2, double v3) {
        Rectangle r = new Rectangle(v, v1, v2, v3);
        r.setFill(ROAD_COLOR);
        rectangleGrid.add(r);
    }

    public Grid () {
        rectangleGrid = new ArrayList<>();
        trafficList = new ArrayList<>();
        for (int i = 150 ; i <= 300 ; i += 50)
            createRectangle(75f ,  i, 275f, 18f);
        for (int i = 100 ; i <= 300 ; i += 50)
            createRectangle( i , 125f, 18f, 225f);
        //Je trouve que plusieurs feu rendent la chose illisible
        /*
        for (int i = 160 ; i <= 260 ; i+=100){
            for (int j = 210 ; j <= 260; j+=50)
                trafficList.add(new TrafficLight(i, j, true, (i+j)%TrafficLight.CYCLE_DURATION));
        }
        */
        //Pour un seul feu
        trafficList.add(new TrafficLight(160, 210));
        //Pour aucun feux
        //trafficList = new ArrayList<>();
    }

    public ArrayList<Rectangle> getRectangleGrid() {return rectangleGrid;}
    public ArrayList<TrafficLight> getTrafficList(){return trafficList;}
    //Resolve les cycle de chaque feu de traffic
    public void resolveLights(){trafficList.forEach(TrafficLight::resolve);}
    public void resolveLightCollision(Car adv){trafficList.forEach(c -> c.resolveCollision(adv));
    }
}
