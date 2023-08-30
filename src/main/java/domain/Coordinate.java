package domain;

public class Coordinate {
    private Double x;
    private Double y;
    public Coordinate(){

    }

    public void setX(Double x) {
        this.x = x;
    }
    public Double getX(){
        return x;
    }

    public void setY(Double y) {
        this.y = y;
    }
    public Double getY(){
        return y;
    }
    @Override
    public String toString(){
        return "[x=" + x + ", y=" + y + "]";
    }
}
