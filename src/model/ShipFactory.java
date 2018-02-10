package model;

import model.Ship;

import java.util.Random;
/**
 * Класс фабрика кораблей
 *
 * @author Илья Богачев
 * @since 20.01.2018
 */
public class ShipFactory {

    public Ship create1DeckShip() {
        return createShip(Ship.TypeOfShip.ONEDECK);
    }

    public Ship create2DecksShip() {
        return createShip(Ship.TypeOfShip.TWODECKS);
    }

    public Ship create3DeckShip() {
        return createShip(Ship.TypeOfShip.THREEDECKS);
    }

    public Ship create4DeckShip() {
        return createShip(Ship.TypeOfShip.FOURDECKS);

    }

    /**
     * создает корабли в завистимости от их типа, задает значение начальных координат и положение
     * однопалубные корабли инициализируются с одним постоянным положением
     *
     * @return model.Ship ship
     */
    private Ship createShip(Ship.TypeOfShip typeOfShip) {
        Ship ship = new Ship(typeOfShip);
        Random random = new Random();
        int coordinateX = random.nextInt(10);
        int coordinateY = random.nextInt(10);
        int position = random.nextInt(2);
        if (!(typeOfShip == Ship.TypeOfShip.ONEDECK)) {
            switch (position) {
                case 0:
                    ship.setShipCellsCoordinate(coordinateX, coordinateY, Ship.ShipPosition.HORIZONTAL);
                    break;
                case 1:
                    ship.setShipCellsCoordinate(coordinateX, coordinateY, Ship.ShipPosition.VERTICAL);
                    break;
            }
        } else {
            ship.setShipCellsCoordinate(coordinateX, coordinateY, Ship.ShipPosition.HORIZONTAL);
        }
        return ship;
    }

}
