package main.java.model;

import java.util.ArrayList;
import main.java.model.ShipCell;

/**
 * Класс корабль
 *
 * @author Илья Богачев
 * @since 28.01.2018
 * Класс имеет четыре типа кораблей, каждый из которых конструируется в зависимости от переданного типа
 * каждый корабль создает свой уникальный набор model.ShipCell и добавляет их в ArrayList shipCells
 * метод setShipCellsCoordinate устанавливает корабль (т.е. каждую его палубу, начиная с первой) в зависимости от его расположения (передается как параметр)
 */
public class Ship {

    private Ship.ShipState shipState;

    private Ship.TypeOfShip typeOfShip;

    public ShipPosition getShipPosition() {
        return shipPosition;
    }

    public void setShipPosition(ShipPosition shipPosition) {
        this.shipPosition = shipPosition;
    }
    public ShipState getShipState() {
        return shipState;
    }

    public void setShipState(ShipState shipState) {
        this.shipState = shipState;
    }

    private ShipPosition shipPosition;

    /**
     * Перечисление содержит два возможных положения кораблей на игровом поле {@see} setShipCellsCoordinate()
     */
   public enum ShipPosition {
        HORIZONTAL, VERTICAL
    }

    /**состояние корабля, необходимо для определения ранен ли или убит*/
    protected enum ShipState{
        WOUNDED, DEAD, ALIVE
    }

    public ArrayList<ShipCell> getShipCells() {
        return shipCells;
    }

    public void setShipCells(ArrayList<ShipCell> shipCells) {
        this.shipCells = shipCells;
    }

    /**
     * Коллекция хранит палубы одного корабля
     */
    private ArrayList<ShipCell> shipCells = new ArrayList<>();

    public TypeOfShip getTypeOfShip() {
        return typeOfShip;
    }

    public void setTypeOfShip(TypeOfShip typeOfShip) {
        this.typeOfShip = typeOfShip;
    }

    /**
     * конструктор создает палубы корабля и устанавливает его состояние как ALIVE
     *
     * @param typeOfShip определяет количество ячеек корабля
     */
    public Ship(TypeOfShip typeOfShip) {//при конструировании корабля в зависимости от типа корабля создаются палубы и устанавливается их начальное состояние
        this.typeOfShip = typeOfShip;
        this.shipState = ShipState.ALIVE;
        ShipPosition position;
        for (int i = 0; i < typeOfShip.ordinal() + 1; i++) {
            ShipCell shipCell = new ShipCell(ShipCell.State.ALIVE);
            shipCells.add(shipCell);
        }

    }

    /**
     * задаем координаты всех ячеек корабля в зависимости от положения shipPosition итерируем либо x, либо y
     *
     * @param x0
     * @param y0
     * @param shipPosition
     */
    public void setShipCellsCoordinate(int x0, int y0, ShipPosition shipPosition) {
        this.shipPosition = shipPosition;
        int i = 0;
        for (int j = 0; j < shipCells.size(); j++) {
            /**перебираем коллекцию с палубами, задаем им координаты в зависимости от положения горизонтального или вертикального*/
            switch (shipPosition) {
                case HORIZONTAL:
                    shipCells.get(j).setCoordinateX(x0 + i);
                    shipCells.get(j).setCoordinateY(y0);
                    i++;
                    break;
                case VERTICAL:
                    shipCells.get(j).setCoordinateY(y0 + i);
                    shipCells.get(j).setCoordinateX(x0);
                    i++;
                    break;
            }
        }
    }

    /**
     * Перечисление типов кораблей, содержит их возможное число на игровом поле и имя
     */
    public enum TypeOfShip {
        ONEDECK(4, "One-deck"), TWODECKS(3, "Two-decks"), THREEDECKS(2, "Three-decks"), FOURDECKS(1, "Four-decks");
        public int countShips;
        String type;

        TypeOfShip(int countShips, String type) {
            this.countShips = countShips;
            this.type = type;
        }
    }
}
