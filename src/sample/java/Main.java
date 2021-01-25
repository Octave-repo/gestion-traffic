package sample.java;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.java.entity.Car;
import sample.java.entity.Grid;
import sample.java.utils.SpawnPoints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    private static final List<Car> listCar = Collections.synchronizedList(new ArrayList<>());
    private static final Grid roads = new Grid();
    private static final Random r = new Random();
    private static final Group root = new Group();

    public void addCar(){
        SpawnPoints sp;
        sp = SpawnPoints.values()[r.nextInt(SpawnPoints.values().length)];
        Car c = new Car(sp,1);
        listCar.add(c);
        root.getChildren().add(c);
    }

    public void setUp() {
        root.getChildren().addAll(roads.getRectangleGrid());
        root.getChildren().addAll(roads.getTrafficList());
    }

    private void startAnimation() {
        AnimationTimer loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try{
                    roads.resolveLights();
                    listCar.forEach(Car::resetMovement);
                    listCar.forEach(Car::resetSpeed);
                    listCar.forEach(r -> r.setResolved(false));
                    listCar.forEach(r -> r.setSeenCar(false));
                    listCar.forEach(r -> {
                        r.avancer();
                        if (r.canBeDestroyed() || r.isDead()){
                            listCar.remove(r);
                            root.getChildren().remove(r);
                            addCar();
                        }
                        listCar.forEach(c -> c.resolveCollide(r));
                        roads.resolveLightCollision(r);
                    });
                } catch (Exception ignored){}
            }
        };
        loop.start();
    }

    @Override
    public void start(Stage primaryStage) {
        setUp();
        for (int i = 0 ; i <= 5 ; i++)
            addCar();
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setTitle("Vroum vroum");
        primaryStage.setScene(scene);
        primaryStage.show();
        startAnimation();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
