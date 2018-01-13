import java.util.Random;

/**
 * Created by Bogachevy on 24.12.2017.
 */
public class ShipFactory {
    /**
     * Создает корабль в зависимости от типа корабля и генерирует начальные координаты X и Y, а также положение корабля
     * по горизонтали или вертикали случайным образом в методе createShip(Ship.TypeOf)
     */

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
     * @return  Ship ship
     */

    private Ship createShip (Ship.TypeOfShip typeOfShip){
        Ship ship = new Ship(typeOfShip);
        Random random = new Random();
        int coordinateX = random.nextInt(10);
        int coordinateY = random.nextInt(10);
        int position = random.nextInt(2);
        if (!(typeOfShip== Ship.TypeOfShip.ONEDECK)) {
            switch (position) {
                case 0:
                    ship.setShipCellsCoordinate(coordinateX, coordinateY, Ship.ShipPosition.HORIZONTAL);
                    break;
                case 1:
                    ship.setShipCellsCoordinate(coordinateX, coordinateY, Ship.ShipPosition.VERTICAL);
                    break;
            }
        }else {
            ship.setShipCellsCoordinate(coordinateX, coordinateY, Ship.ShipPosition.HORIZONTAL);
        }
        return ship;
    }

}
