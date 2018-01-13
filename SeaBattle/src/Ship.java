import java.util.ArrayList;


/**
 * Created by Bogachevy on 24.12.2017.
 */
public class Ship {
    /**
     * Класс имеет четыре типа кораблей, каждый из которых конструируется в зависимости от переданного типа
     * каждый корабль создает свой уникальный набор ShipCell и добавляет их в ArrayList shipCells
     * метод setShipCellsCoordinate устанавливает корабль (т.е. каждую его палубу, начиная с первой) в зависимости от его расположения (передается как параметр)
     */
    private Ship.TypeOfShip typeOfShip;

    public ShipPosition getShipPosition() {
        return shipPosition;
    }

    public void setShipPosition(ShipPosition shipPosition) {
        this.shipPosition = shipPosition;
    }

    private ShipPosition shipPosition;

    enum ShipPosition {
        HORIZONTAL, VERTICAL
    }

    public ArrayList<ShipCell> getShipCells() {
        return shipCells;
    }

    public void setShipCells(ArrayList<ShipCell> shipCells) {
        this.shipCells = shipCells;
    }

    /**
     * создаем коллекцию для хранения ячеек оодного корабля
     */
    private ArrayList<ShipCell> shipCells = new ArrayList<ShipCell>();

    public TypeOfShip getTypeOfShip() {
        return typeOfShip;
    }

    public void setTypeOfShip(TypeOfShip typeOfShip) {
        this.typeOfShip = typeOfShip;
    }

    /**
     * конструктор создает палубы корабля
     * @param typeOfShip определяет количество ячеек корабля
     */
    public Ship(TypeOfShip typeOfShip) {//при конструировании корабля в зависимости от типа корабля создаются палубы и устанавливается их начальное состояние
        this.typeOfShip = typeOfShip;
        ShipPosition position;
        for (int i = 0; i < typeOfShip.ordinal()+1; i++) {
            ShipCell shipCell = new ShipCell(ShipCell.State.ALIVE);
            shipCells.add(shipCell);
        }

    }

    /**
     * задаем координаты всех ячеек корабля в зависимости от положения shipPosition итерируем либо x, либо y
     * @param x0
     * @param y0
     * @param shipPosition
     */

    public void setShipCellsCoordinate(int x0, int y0, ShipPosition shipPosition) {
        this.shipPosition = shipPosition;
        int i = 0;
        for (int j = 0; j < shipCells.size(); j++) {
       //перебираем коллекцию с палубами, задаем им координаты в зависимости от положения горизонтального или вертикального
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
     * создаем перечисления для типов кораблей, устанавливаем количество каждого типа и название
     */

    enum TypeOfShip {
        ONEDECK(4, "One-deck"), TWODECKS(3, "Two-decks"), THREEDECKS(2, "Three-decks"), FOURDECKS(1,  "Four-decks");
        int countShips;
        String type;

        TypeOfShip(int countShips,  String type) {
            this.countShips = countShips;
            this.type = type;
        }
    }
}
