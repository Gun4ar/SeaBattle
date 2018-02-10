package model;

/**
 * Класс Точка реализует координаты выстрела игроков
 *
 * @author Илья Богачев
 * @since 25.01.2018
 */
public class Point {
    private int x;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int y;

}
