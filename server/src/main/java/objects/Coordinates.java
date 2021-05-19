package objects;

public class Coordinates {
    private float x; //Максимальное значение поля: 71
    private float y; //Максимальное значение поля: 556, Поле не может быть null
    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
