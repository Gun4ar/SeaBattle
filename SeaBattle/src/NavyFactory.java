import java.util.ArrayList;
import java.util.List;

public class NavyFactory {
    /**
     * Класс фабрика по созданию флота кораблей
     *
     * @author Илья Богачев
     * @since 14.01.2018  *
     */
    public List<Ship> getShips() {
        return ships;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    private Field field;

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    private List <Ship> ships;

    /**
     * Создает корабли количеством в зависимости от заданного типа
     * при создании кораблей проверяется возможность их размещения на поле в методе {@see} checkShip()
     * проводится 10 000 попыток разместить каждый новый корабль на поле
     *
     * @return ArrayList <Ship> ships
     */
    public List<Ship> createNavy() {
        Ship ship;
        int countOfTries = 0;
        final int MAX_COUNT_OF_TRIES = 10_000;
        field = new Field(10, 10);
        field.initBattleField();
        ships = new ArrayList<Ship>();
        ShipFactory shipFactory = new ShipFactory();
        for (int i = 0; i < Ship.TypeOfShip.FOURDECKS.countShips; i++) {
            /**вынуждаем метод провести большое число попыток генерации положения корабля до того, как его можно будет установить на поле*/
            while (countOfTries < MAX_COUNT_OF_TRIES) {
                if (field.checkShip(ship = shipFactory.create4DeckShip())) {
                    ships.add(ship);
                    field.setBattleField(ships);
                    break;
                }
            }
        }
        for (int i = 0; i < Ship.TypeOfShip.THREEDECKS.countShips; i++) {
            while (countOfTries < MAX_COUNT_OF_TRIES) {
                if (field.checkShip(ship = shipFactory.create3DeckShip())) {
                    ships.add(ship);
                    field.setBattleField(ships);
                    break;
                }
            }
        }
        for (int i = 0; i < Ship.TypeOfShip.TWODECKS.countShips; i++) {
            while (countOfTries < MAX_COUNT_OF_TRIES) {
                if (field.checkShip(ship = shipFactory.create2DecksShip())) {
                    ships.add(ship);
                    field.setBattleField(ships);
                    break;
                }
            }
        }
        for (int i = 0; i < Ship.TypeOfShip.ONEDECK.countShips; i++) {
            while (countOfTries < MAX_COUNT_OF_TRIES) {
                if (field.checkShip(ship = shipFactory.create1DeckShip())) {
                    ships.add(ship);
                    field.setBattleField(ships);
                    break;
                }
            }
        }
        System.out.println("Ships are created!");
        return ships;
    }
}


