import java.util.List;
import java.util.Random;

/**
 * Класс Комьютер
 *
 * @author Илья Богачев
 * @since 22.01.2018
 */
public class Computer implements ShootingShips {
    /**
     * поле комьютера
     */
    private Field computerField;
    /**
     * поле проверки комьютера
     */
    private Field computerCheckField;

    /**
     * имя комьютера
     */
    private String name;

    public List<Ship> getComputerNavy() {
        return computerNavy;
    }

    /**
     * флот компьютера
     */
    private List<Ship> computerNavy;

    /**
     * метод создания флота компьютера, создание полей проверки и поля с флотом компьютера
     */
    public void computerPutShips() {
        NavyFactory navyFactory = new NavyFactory();
        computerNavy = navyFactory.createNavy();
        computerField = new Field(10, 10);
        computerField.initBattleField();
        computerField.setBattleField(computerNavy);
        computerCheckField = new Field(10, 10);
        computerCheckField.initBattleField();
    }

    public Field getComputerField() {//временно для теста
        return computerField;
    }

    public Computer() {
        name = "GreatestComputerMindEVER";
    }

    public Field getComputerCheckField() {//временно для теста
        return computerCheckField;
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
     * метод стрельбы компьютера
     */
    @Override
    public int[] shootShips() {
        while (true) {
            int shoots[] = createShootCoordinate();
            /**проверяем, что коорданаты выстрела не совпадают с предыдущими координатами и с уже потопленными кораблями противника*/
            if (computerCheckField.checkRandomShoots(shoots)) {
                return shoots;
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public boolean isLooseNavy() {
        for (int i = 0; i < getComputerNavy().size(); i++) {
            for (int j = 0; j < getComputerNavy().get(i).getShipCells().size(); j++) {
                if (getComputerNavy().get(i).getShipCells().get(j).getState() == ShipCell.State.DEAD) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * если был промах, то необходимо отметить на поле computerCheckField координату промаха
     */
    public void missed(int[] computerShoots) {
        computerCheckField.getBattleField()[computerShoots[1]][computerShoots[0]] = Field.FieldCells.MISSED;
    }

    /**
     * если комьютер попал, то он отмечает попадание на поле computerCheckField координату попадания
     */
    public void computerHit(int[] computerShoots) {
        computerCheckField.getBattleField()[computerShoots[1]][computerShoots[0]] = Field.FieldCells.DEADSHIP;
    }

    public boolean checkShootCoordinate(int[] shootCoordinate) {
        switch (computerField.getBattleField()[shootCoordinate[1]][shootCoordinate[0]]) {
            case ALIVESHIP:
                computerField.getBattleField()[shootCoordinate[1]][shootCoordinate[0]] = Field.FieldCells.DEADSHIP;
                return true;
            case EMPTY:
                computerField.getBattleField()[shootCoordinate[1]][shootCoordinate[0]] = Field.FieldCells.MISSED;
                return false;
            case DEADSHIP:
                return false;
            case MISSED:
                return false;

        }
        return false;
    }

    public void killShips(int[] playerShoots) {
        /**ищем корабль в который попал противник и присваиваем ячейки корабля значение DEAD*/
        for (int i = 0; i < computerNavy.size(); i++) {//пробегаем по массиву кораблей
            for (int j = 0; j < computerNavy.get(i).getShipCells().size(); j++) { //пробегаем по массиву палуб конкретноего корабля
                /**находим ячейку корабля по указанным координатам*/
                if (computerNavy.get(i).getShipCells().get(j).getCoordinateY() == playerShoots [1] && computerNavy.get(i).getShipCells().get(j).getCoordinateX() == playerShoots[0]) {
                    /**устанавливаем значение этой ячейки DEAD*/
                    computerNavy.get(i).getShipCells().get(j).setState(ShipCell.State.DEAD);
                    break;
                }
            }
        }
    }
}
