import java.util.List;
import java.util.Random;

/**
 * Класс Игрок
 *
 * @author Илья Богачев
 * @since 23.01.2018
 */
public class Player implements ShootingShips {
    Show show;
    private String name;

    public Field getPlayerField() {//временно для теста
        return playerField;
    }

    public Field getPlayerCheckField() {//временно для теста
        return playerCheckField;
    }

    /**
     * поле игрока
     */
    private Field playerField;
    /**
     * поле игрока для проверки выстрелов
     */
    private Field playerCheckField;

    public List<Ship> getPlayerNavy() {
        return playerNavy;
    }

    /**
     * флот игрока
     */
    private List<Ship> playerNavy;

    public Player() {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * создает новое игровое поля, и поле проверки для игрока, создает флот и размещает его на игровом поле, и выводит его на экран
     */
    public void putShips() {
        show = new Show();
        NavyFactory navyFactory = new NavyFactory();
        playerNavy = navyFactory.createNavy();
        playerField = new Field(10, 10);
        playerField.initBattleField();
        playerField.setBattleField(playerNavy);
        show.drawField(playerField);
        playerCheckField = new Field(10, 10);
        playerCheckField.initBattleField();
    }

    /**
     * метод генерации координат выстрела
     *
     * @return shoots - массив с двумя значениями координат X и Y
     */
    public int[] createShootCoordinate() {
        int[] shoots = new int[2];
        Random random = new Random();
        shoots[0] = random.nextInt(10);
        shoots[1] = random.nextInt(10);
        return shoots;
    }

    /**
     * метод стрельбы игрока, автоматический режим генерации значений координат выстрела, временный
     */
    @Override
    public int[] shootShips() {
        while (true) {
            int shoots[] = createShootCoordinate();
            /**проверяем, что коорданаты выстрела не совпадают с предыдущими координатами и с уже потопленными кораблями противника*/
            if (playerCheckField.checkRandomShoots(shoots)) {
                return shoots;
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * метод проверки координта выстрела противника, вернет true , если только переданные координаты попали по кораблю игрока
     * и установит значение ячейки игрового поля в соответсвии с предыдущим ее значеним (DEADSHIP или MISSED)
     */
    public boolean checkShootCoordinate(int[] shootCoordinate) {
        switch (playerField.getBattleField()[shootCoordinate[1]][shootCoordinate[0]]) {
            case ALIVESHIP:
                playerField.getBattleField()[shootCoordinate[1]][shootCoordinate[0]] = Field.FieldCells.DEADSHIP;
                return true;
            case EMPTY:
                playerField.getBattleField()[shootCoordinate[1]][shootCoordinate[0]] = Field.FieldCells.MISSED;
                return false;
            case DEADSHIP:
                return false;
            case MISSED:
                return false;

        }
        return false;
    }

    /**
     * если был промах, то необходимо отметить на поле playerCheckField координату промаха
     */
    public void missed(int[] playerShoots) {
        playerCheckField.getBattleField()[playerShoots[1]][playerShoots[0]] = Field.FieldCells.MISSED;
    }

    public void killShips(int[] computerShoots) {
        /**ищем корабль в который попал противник и присваиваем ячейки корабля значение DEAD*/
        for (int i = 0; i < playerNavy.size(); i++) {//пробегаем по массиву кораблей
            for (int j = 0; j < playerNavy.get(i).getShipCells().size(); j++) { //пробегаем по массиву палуб конкретноего корабля
                /**находим ячейку корабля по указанным координатам*/
                if (playerNavy.get(i).getShipCells().get(j).getCoordinateY() == computerShoots[1] && playerNavy.get(i).getShipCells().get(j).getCoordinateX() == computerShoots[0]) {
                    /**устанавливаем значение этой ячейки DEAD*/
                    playerNavy.get(i).getShipCells().get(j).setState(ShipCell.State.DEAD);
                    break;
                }
            }
        }
    }

    /**
     * метод возвращает true, если весь флот игрока потоплен
     */
    public boolean isLooseNavy() {
        for (int i = 0; i < getPlayerNavy().size(); i++) {
            for (int j = 0; j < getPlayerNavy().get(i).getShipCells().size(); j++) {
                if (getPlayerNavy().get(i).getShipCells().get(j).getState() == ShipCell.State.DEAD) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * отмечаем потопленный корабль на проверочном поле
     */
    public void playerHit(int[] playerShoots) {
        playerCheckField.getBattleField()[playerShoots[1]][playerShoots[0]] = Field.FieldCells.DEADSHIP;
    }
}
