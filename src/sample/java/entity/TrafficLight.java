package sample.java.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import sample.java.utils.Direction;

import java.util.HashSet;
import java.util.Set;

/**
 * Cette classe représente un feu de traffic
 * Elle étends la class Cercle pour pouvoir change la couleur aisément
 */
public class TrafficLight extends Circle {
    public static final double RADIUS = 8;
    //Les couleurs des feux
    public static final Color COLOR = Color.RED;
    /*Nombre de cycle avant un changement de couleur
    On le détermine par (durée en seconde * 60)
     */
    public static final int CYCLE_DURATION = 240;
    public static final double OFFSET = 20;

    //Nombre de cycle effectué
    private int cycleCounter;
    //S'agit-il d'un feu vert ou rouge ?
    private boolean goEast;
    private final double startingX;
    private final double startingY;
    //Les cars qui ont été stoppé par le feu
    private final Set<Car> carSet = new HashSet<>();


    public TrafficLight(double x, double y, boolean startingState, int cycleOffset){
        super(x, y, RADIUS, COLOR);
        startingX = x;
        startingY = y;
        goEast = startingState;
        cycleCounter = cycleOffset <= CYCLE_DURATION && cycleOffset >= 0 ? cycleOffset : 0;
    }
    /*
    Par défaut, un feu commence au début du "cycle vert"
     */
    public TrafficLight(double x, double y){
        super(x, y, RADIUS, COLOR);
        startingX = x;
        startingY = y;
        goEast = true;
        cycleCounter = 0;
    }

    public void resolve(){
        if (cycleCounter == CYCLE_DURATION){
            goEast = !goEast;
            cycleCounter = 0;
            System.out.println(goEast);
            setCenterX(goEast ? startingX : startingX - OFFSET);
            setCenterY(goEast ? startingY + OFFSET : startingY);
        }
        cycleCounter++;
    }

    public void resolveCollision(Car adversary) {
        if ((!goEast && adversary.getDir() == Direction.EAST) || (goEast && adversary.getDir() == Direction.NORTH)){
            double distX = (getCenterX()) - (adversary.getX() + adversary.getWidth() / 2);
            double distY = (getCenterY()) - (adversary.getY() + adversary.getWidth() / 2);
            double distance = Math.sqrt((distX * distX) + (distY * distY));
            if (distance <= RADIUS - 2 + adversary.getDetectionRadius()) {
                adversary.stop();
                adversary.setEncounteredTrafficLight(true);
                carSet.add(adversary);
            }
        }
        else {
            if (adversary.hasEncounteredTrafficLight()) {
                adversary.setMaxSpeed(10);
                adversary.resetMovement();
            }
            adversary.setEncounteredTrafficLight(false);
        }
    }
}
