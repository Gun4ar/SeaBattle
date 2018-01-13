/**
 * Created by Bogachevy on 06.01.2018.
 */
public class ShipCell {
    /**
     * устанавливает координаты Х, Y и состояние палубы корабля
     */
    private int coordinateX;
    private int coordinateY;
    private State state;
    enum State{
        INDEFINITELY, ALIVE, DEAD,
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ShipCell(State state){
        this.state = state;
    }
}
