package sample.java.utils;

public enum Direction {
    NORTH(0, -Direction.MULTI, true), SOUTH(0, Direction.MULTI, true)
    , EAST(Direction.MULTI, 0, false), WEST(-Direction.MULTI, 0, false);
    public double X;
    public double Y;
    public boolean IS_FACING_UP;
    public static final double MULTI = 0.1f;
    Direction (double x, double y, boolean facing) {
        this.X = x;
        this.Y = y;
        this.IS_FACING_UP = facing;
    }
    //Fonctionne uniquement grâce à l'absence de diagonale
    public boolean isOpposite(Direction adv){return this.X == -adv.X || this.Y == -adv.Y;}
}
