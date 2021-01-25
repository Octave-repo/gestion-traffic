package sample.java.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.java.utils.Direction;
import sample.java.utils.SpawnPoints;

import java.util.Random;

/*
On définit une classe voiture qui étend un Rectangle de javafx
 */
public class Car extends Rectangle {
    //On défini des dimensions statique à notre 'voiture'
    public static final double HEIGHT = 15;
    public static final double WIDTH = 10;
    public static final double LIFE_SPAN = 10;
    public static final double BASE_MAX_SPEED = 1;
    //La distance à laquelle on détecte un autre voiture, dépend de la vitesse de la voiture
    private final double detectionRadius;
    private final Direction dir;
    private final double acceleration;

    private boolean isRunning;
    //On l'utilise pas mais le delete demanderais du refactoring
    private final boolean isAccelerating;
    private boolean resolved = false;
    private boolean seenCar;
    private boolean encounteredTrafficLight = false;
    //La vitesse à laquelle se déplace la voiture
    private double maxSpeed = BASE_MAX_SPEED;
    private double speed;
    private int counter;

    //L'accélération correspond à la durée en seconde qu'il faut pour atteindre la vitesse maximale.
    /*
    public Car(double x, double y,double acceleration, Direction dir) {
        super(x, y, dir.IS_FACING_UP ? WIDTH : HEIGHT,
                dir.IS_FACING_UP ? HEIGHT : WIDTH);
        Random r = new Random();
        this.setFill(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        this.dir = dir;
        isRunning = true;
        isAccelerating = true;
        this.acceleration = 1f / (60f * acceleration);
        detectionRadius = 20 * acceleration;
        speed = 0;
        counter = 0;
    }*/

    public Car(SpawnPoints spawn, double acceleration){
        super(spawn.X, spawn.Y, spawn.DIRECTION.IS_FACING_UP ? WIDTH : HEIGHT,
                spawn.DIRECTION.IS_FACING_UP ? HEIGHT : WIDTH);
        Random r = new Random();
        this.setFill(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        this.dir = spawn.DIRECTION;
        isRunning = true;
        isAccelerating = true;
        detectionRadius = 17 * acceleration;
        this.acceleration = 1f / (60f * acceleration);
        speed = 0;
    }
    /*
    Cette méthode fait avancer une voiture en fonction de sa direction
     */
    public void avancer(){
        if (isRunning) {
            counter = 0;
            setX(getX() + dir.X * speed);
            setY(getY() + dir.Y * speed);
            if (isAccelerating) {
                if (speed < maxSpeed)
                    speed += acceleration;
                else
                    speed = maxSpeed;
            } else {
                if (speed >= 0)
                    speed -= (0.5f * acceleration);
                if (speed < 0){
                    speed = 0;
                    isRunning = false;
                }
            }
        }
        else{counter++;}
    }

    public boolean canBeDestroyed(){
        switch (dir){
            case SOUTH:
                return getY() >= 325;
            case NORTH:
                return getY() <= 125;
            case EAST:
                return getX() >= 325;
            case WEST:
                return getX() <= 75;
            default:
                return false;
        }
    }

    //Resolve les collisions entre les voitures
    public void resolveCollide(Car adversary){
        //On calcule la distance entre les deux voitures
        if (!this.equals(adversary)){
            double distX = (getX() + this.getWidth()/2) - (adversary.getX() + adversary.getWidth()/2);
            double distY = (getY() + this.getHeight()/2) - (adversary.getY() + adversary.getWidth()/2);
            double distance = Math.sqrt((distX*distX) + (distY*distY));
            if (distance <= detectionRadius + adversary.getDetectionRadius()){
                seenCar = true;
                adversary.setSeenCar(true);
                if (this.dir.isOpposite(adversary.getDir()) || distance <= 5){
                    this.stop();
                    adversary.stop();
                } else{
                    if (!resolved)
                        maxSpeed = 10;
                    if (!adversary.isResolved())
                        adversary.setMaxSpeed(0.5);
                }
                resolved = true;
                adversary.setResolved(true);
            }
        }
    }
    public void resetSpeed(){
        if (!seenCar){
            maxSpeed = BASE_MAX_SPEED;
        }
        seenCar = false;
    }
    public void stop() {isRunning = false;}
    public double getDetectionRadius() {return detectionRadius;}
    public boolean isDead() {return counter >= 60 * LIFE_SPAN;}
    public void setMaxSpeed(double speed){maxSpeed = speed;}
    public void setResolved(boolean r){resolved = r;}
    public boolean isResolved(){return resolved;}
    public void setSeenCar(boolean seen){seenCar = seen;}
    public Direction getDir() {return dir;}
    public void resetMovement(){isRunning = !seenCar && !encounteredTrafficLight || isRunning;}
    public void setEncounteredTrafficLight(boolean b){this.encounteredTrafficLight = b;}
    public boolean hasEncounteredTrafficLight(){return encounteredTrafficLight;}
}
