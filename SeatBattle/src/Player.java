import java.util.List;
import java.util.Random;

/**
 * Класс Игрок
 *
 * @author Илья Богачев
 * @since 05.02.2018
 */
public class Player extends Strategy implements ShootingShips {
    ConsoleShow consoleShow;
    Strategy strategy;
    private String name;
    public boolean isShipAlive;

    public Field getField() {//временно для теста
        return field;
    }

    public Field getCheckField() {//временно для теста
        return checkField;
    }

    /**
     * поле игрока
     */
    private Field field =new Field(10, 10);
    /**
     * поле игрока для проверки выстрелов
     */
    private Field checkField;

    public List<Ship> getNavy() {
        return navy;
    }

    /**
     * флот игрока
     */
    private List<Ship> navy;

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
        consoleShow = new ConsoleShow();
        NavyFactory navyFactory = new NavyFactory();
        navy = navyFactory.createNavy();
        field = new Field(10, 10);
        field.initBattleField();
        field.setBattleField(navy);
        consoleShow.drawField(field);
        checkField = new Field(10, 10);
        checkField.initBattleField();
    }

    /**
     * метод генерации координат выстрела
     *
     * @return shoots - массив с двумя значениями координат X и Y
     */
    public Point createShootCoordinate() {
        Point shoots = new Point();
        Random random = new Random();
        shoots.setX(random.nextInt(10));
        shoots.setY(random.nextInt(10));
        return shoots;
    }

    /**
     * метод стрельбы игрока, автоматический режим генерации значений координат выстрела, временный
     */
    @Override
    public Point shootShips() {
        while (true) {
            Point shoots = createShootCoordinate();
            /**проверяем, что коорданаты выстрела не совпадают с предыдущими координатами и с уже потопленными кораблями противника*/
            if (checkField.checkRandomShoots(shoots)) {
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
    public boolean checkShootCoordinate(Point shootCoordinate) {
        switch (field.getBattleField()[shootCoordinate.getY()][shootCoordinate.getX()]) {
            case ALIVESHIP:
                field.getBattleField()[shootCoordinate.getY()][shootCoordinate.getX()] = Field.FieldCells.DEADSHIP;
                /**отмечаем пораженную ячейку корабля*/
                killShipCell(shootCoordinate);
                return true;
            case EMPTY:
                field.getBattleField()[shootCoordinate.getY()][shootCoordinate.getX()] = Field.FieldCells.MISSED;
                return false;
            case DEADSHIP:
                return false;
            case MISSED:
                return false;

        }
        return false;
    }

    /**
     * если был промах, то необходимо отметить на поле checkField координату промаха
     */
    public void missed(Point playerShoots) {
        checkField.getBattleField()[playerShoots.getY()][playerShoots.getX()] = Field.FieldCells.MISSED;
    }

    /**отмечает пораженную противником ячейку корабля*/
    private void killShipCell(Point point) {
        /**ищем корабль в который попал противник и присваиваем ячейки корабля значение DEAD*/
        for (int i = 0; i < navy.size(); i++) {//пробегаем по массиву кораблей
            for (int j = 0; j < navy.get(i).getShipCells().size(); j++) { //пробегаем по массиву палуб конкретноего корабля
                /**находим ячейку корабля по указанным координатам*/
                if (navy.get(i).getShipCells().get(j).getCoordinateY() == point.getY() && navy.get(i).getShipCells().get(j).getCoordinateX() == point.getX()) {
                    /**устанавливаем значение этой ячейки DEAD*/
                    navy.get(i).getShipCells().get(j).setState(ShipCell.State.DEAD);
                    /**если все ячейки корабля имеют значение DEAD, то корабль считается потоплен и принимает это значение*/
                    if (navy.get(i).getShipCells().get(j).getState()== ShipCell.State.DEAD){
                        navy.get(i).setShipState(Ship.ShipState.DEAD);
                    }
                    break;
                }
            }
        }
    }

    /**
     * метод возвращает true, если весь флот игрока потоплен
     */
    public boolean isLooseNavy() {
        for (int i = 0; i < getNavy().size(); i++) {
            if (getNavy().get(i).getShipState() == Ship.ShipState.DEAD) {
                    continue;
                } else {
                    return false;
                }
            }
        return true;
    }

    /**
     * отмечаем потопленный корабль на проверочном поле
     */
    public void playerHit(Point playerShoots) {
        checkField.getBattleField()[playerShoots.getY()][playerShoots.getX()] = Field.FieldCells.DEADSHIP;
    }

    /**проверяет, жив ли корабль в который попал противник или потоплен*/
    public boolean isShipAlive(Point point) {
        /**ищем корабль по переданным координатам*/
        for (int i = 0; i < navy.size(); i++) {//пробегаем по массиву кораблей
            for (int j = 0; j < navy.get(i).getShipCells().size(); j++) { //пробегаем по массиву палуб конкретноего корабля
                /**находим ячейку корабля по указанным координатам*/
                if (navy.get(i).getShipCells().get(j).getCoordinateY() == point.getY() && navy.get(i).getShipCells().get(j).getCoordinateX() == point.getX()) {
                    /**проверяем, есть ли еще не потопленные ячейки этого корабля*/
                    if (navy.get(i).getShipCells().get(j).getState() == ShipCell.State.ALIVE) {
                        return true;
                    }
                }
            }
        }return false;
    }
    /**метод сражения игрока, реализуется через GUI*/
    public boolean fight(){
        return false;
    }

}
