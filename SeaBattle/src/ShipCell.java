public class ShipCell {
    /**
     * Класс палуб (ячеек) корабля
     *
     * @author Илья Богачев
     * @since 14.01.2018
     */
    private int coordinateX;
    private int coordinateY;
    private State state;

    /**
     * Содержит перечисления возможных состояний ячеек корабля
     */
    enum State {
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

    public ShipCell(State state) {
        this.state = state;
    }
}
