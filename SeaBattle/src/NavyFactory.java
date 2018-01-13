import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bogachevy on 28.12.2017.
 */
public class NavyFactory {
    /**
     * Фабрика создания флота кораблей     *
     */
    public Map<Integer, Ship> getShips() {
        return ships;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    private Field field;

    public void setShips(Map<Integer, Ship> ships) {
        this.ships = ships;
    }

    private Map<Integer, Ship> ships;

    /**
     * Создает корабли количеством в зависимости от заданного типа
     * при создании кораблей проверяется возможность их размещения на поле в методе chechShip()
     * проводится 10 000 попыток разместить каждый новый корабль на поле
     * если за это число попыток не удастся его поставить то выбрасывается исколючение
     * очищается коллекция и метод вызывается заново     *
     * @return HashMap <Integer, Ship> ships
     */

    public Map<Integer, Ship> createNavy() {
        Ship ship;
        int countOfTries = 0;
        final int MAX_COUNT_OF_TRIES = 10_000;
        field = new Field(10, 10);
        field.createBattleFild();
        ships = new HashMap<Integer, Ship>();
        ShipFactory shipFactory = new ShipFactory();
        try {
            for (int i = 0; i < Ship.TypeOfShip.FOURDECKS.countShips; i++) {
                //вынуждаем систему провести большое число попыток генерации положения корабля до того, как его можно будет установить на поле
                while (countOfTries < MAX_COUNT_OF_TRIES) {
                    if (field.checkShip(ship = shipFactory.create4DeckShip(), field)) {
                        ships.put(i, ship);
                        break;
                    }
                }
            }
            for (int i = 1; i < 1 + Ship.TypeOfShip.THREEDECKS.countShips; i++) {
                while (countOfTries < MAX_COUNT_OF_TRIES) {
                    if (field.checkShip(ship = shipFactory.create3DeckShip(), field)) {
                        ships.put(i, ship);
                        break;
                    }
                }
            }
            for (int i = 4; i < 4 + Ship.TypeOfShip.TWODECKS.countShips; i++) {
                while (countOfTries < MAX_COUNT_OF_TRIES) {
                    if (field.checkShip(ship = shipFactory.create2DecksShip(), field)) {
                        ships.put(i, ship);
                        break;
                    }
                }
            }
            for (int i = 8; i <8+ Ship.TypeOfShip.ONEDECK.countShips; i++) {
                while (countOfTries < MAX_COUNT_OF_TRIES) {
                    if (field.checkShip(ship = shipFactory.create1DeckShip(), field)) {
                        ships.put(i, ship);
                        break;
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Ships are not installed");
            ships.clear();
            createNavy();
        }
        System.out.println("Ships are created!");

        return ships;
    }


}


