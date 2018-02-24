package model;


import view.ConsoleShow;

import java.util.List;
import java.util.Random;

/**
 * Класс Игрок
 *
 * @author Илья Богачев
 * @since 17.02.2018
 */
public class Player extends Strategy implements ShootingShips {
    Strategy strategy = new Strategy();
    protected ConsoleShow consoleShow = new ConsoleShow();
    protected String name;
    private Point point;
    /**
     * поле игрока
     */
    protected Field field;
    /**
     * поле игрока для проверки выстрелов
     */
    protected Field checkField;

    /**
     * флот игрока
     */
    protected List<Ship> navy;

    public Field getCheckField() {
        return checkField;
    }

    public Point getPoint() {
        return point;
    }

    public List<Ship> getNavy() {
        return navy;
    }

    public Field getField() {//временно для теста
        return field;
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
        field.setBattleField(navy);
        consoleShow.drawField(field);
        checkField = new Field(10, 10);

    }

    /**
     * метод генерации координат выстрела
     *
     * @return shoots - массив с двумя значениями координат X и Y
     */
    public Point createShootCoordinate() {
        Random random = new Random();
        return new Point(random.nextInt(10), random.nextInt(10));
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
     * если был промах, то необходимо отметить на поле checkField координату промаха
     */
    public void missed(Point playerShoots) {
        checkField.getBattleField()[playerShoots.getY()][playerShoots.getX()] = Field.FieldCells.MISSED;
    }

    /**
     * отмечает пораженную противником ячейку корабля
     */
    private void killShipCell(Point point) {
        /**ищем корабль в который попал противник и присваиваем ячейки корабля значение DEAD*/
        for (int i = 0; i < navy.size(); i++) {//пробегаем по массиву кораблей
            for (int j = 0; j < navy.get(i).getShipCells().size(); j++) { //пробегаем по массиву палуб конкретноего корабля
                /**находим ячейку корабля по указанным координатам*/
                if (navy.get(i).getShipCells().get(j).getCoordinateY() == point.getY() && navy.get(i).getShipCells().get(j).getCoordinateX() == point.getX()) {
                    /**устанавливаем значение этой ячейки DEAD*/
                    navy.get(i).getShipCells().get(j).setState(ShipCell.State.DEAD);
                    /**если все ячейки корабля имеют значение DEAD, то корабль считается потоплен и принимает это значение*/
                    for (int k = 0; k < navy.get(i).getShipCells().size(); k++) {
                        if (navy.get(i).getShipCells().get(k).getState() == ShipCell.State.ALIVE) {
                            return;
                        } else {
                            continue;
                        }
                    }navy.get(i).setShipState(Ship.ShipState.DEAD);
                }
            }
        }
    }


        /**
         * метод возвращает true, если весь флот игрока потоплен
         */
        public boolean isLooseNavy () {
            for (int i = 0; i < getNavy().size(); i++) {
                if (getNavy().get(i).getShipState() == Ship.ShipState.DEAD) {
                } else {
                    return false;
                }
            }
            return true;
        }

        /**
         * отмечаем потопленный корабль на проверочном поле
         */
        public void playerHit (Point playerShoots){
            checkField.getBattleField()[playerShoots.getY()][playerShoots.getX()] = Field.FieldCells.DEADSHIP;
        }

        /**
         * проверяет, жив ли корабль в который попал противник или потоплен
         */
        public boolean isShipAlive (Point point){
            /**ищем корабль по переданным координатам*/
            for (int i = 0; i < navy.size(); i++) {//пробегаем по массиву кораблей
                for (int j = 0; j < navy.get(i).getShipCells().size(); j++) { //пробегаем по массиву палуб конкретноего корабля
                    /**находим ячейку корабля по указанным координатам*/
                    if (navy.get(i).getShipCells().get(j).getCoordinateY() == point.getY() && navy.get(i).getShipCells().get(j).getCoordinateX() == point.getX()) {
                        /**проверяем, есть ли еще не потопленные ячейки этого корабля*/
                        if (navy.get(i).getShipState() == Ship.ShipState.ALIVE) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /**
         * передача значений результата выстрела
         */
        public Result.ShootResult getMessage (Point point){
            switch (field.getBattleField()[point.getY()][point.getX()]) {
                case ALIVESHIP:
                    field.getBattleField()[point.getY()][point.getX()] = Field.FieldCells.DEADSHIP;
                    /**отмечаем пораженную ячейку корабля*/
                    killShipCell(point);
                    /**проверяем, не потопил ли противник корабль, если да, вернет значение KILL*/
                    if (!(isShipAlive(point))) {
                        System.out.println("KILL in getMessage");
                        return Result.ShootResult.KILL;
                    }
                    return Result.ShootResult.WOUND;
                case EMPTY:
                    field.getBattleField()[point.getY()][point.getX()] = Field.FieldCells.MISSED;
                    return Result.ShootResult.MISS;
                case DEADSHIP:
                    return Result.ShootResult.WOUND;
                case MISSED:
                    return Result.ShootResult.MISS;
            }
            return Result.ShootResult.UNKNOWN;
        }

        /**
         * метод возвращает координаты выстрела, сгенерированные выбранной стратегией комьютера или игрока
         */
        public Point makeTurn () {
            return strategy.fight(this);
        }
    }