package sample.java.utils;

/*
Toute les zones d'apparition possibles d'un véhicule
Ansi que la direction dans laquelle une voiture se dirigerais
 */
public enum SpawnPoints {
    //Si on veux avoir des spawns de toute les directions
    /*
    N1(100,125f, Direction.SOUTH), N2(150,125f, Direction.SOUTH), N3(200,125f, Direction.SOUTH),
    N4(250,125f, Direction.SOUTH), N5(300,125f, Direction.SOUTH),
    E1(325f,150f, Direction.WEST), E2(325f,200f, Direction.WEST),
    E3(325f,250f, Direction.WEST), E4(325f,300f, Direction.WEST),*/
    S1(100,325, Direction.NORTH), S2(150,325, Direction.NORTH), S3(200,325, Direction.NORTH),
    S4(250,325, Direction.NORTH), S5(300,325, Direction.NORTH),
    W1(75,150, Direction.EAST), W2(75,200, Direction.EAST),
    W3(75,250, Direction.EAST), W4(75,300, Direction.EAST);
    public double X;
    public double Y;
    public Direction DIRECTION;
    SpawnPoints(double x, double y, Direction dir){
        X = x;
        Y = y;
        DIRECTION = dir;
    }

}
