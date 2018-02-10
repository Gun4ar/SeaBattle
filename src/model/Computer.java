package model;


/**
 * Класс Комьютер
 *
 * @author Илья Богачев
 * @since 08.02.2018
 */
public class Computer extends Player implements ShootingShips {
    Strategy strategy;
    private Point computerShoots;

    public Point getComputerShoots() {
        return computerShoots;
    }

    /**
     * метод создания флота компьютера, создание полей проверки и поля с флотом компьютера
     */
    @Override
    public void putShips() {
        NavyFactory navyFactory = new NavyFactory();
        navy = navyFactory.createNavy();
        field = new Field(10, 10);
        field.setBattleField(navy);
        checkField = new Field(10, 10);


    }

    public Computer() {
        name = "GreatestComputerMindEVER";
    }

    /**
     * отмечаем потопленную ячейку корабля на проверочном поле
     */
    public void computerHit(Point computerShoots) {
        checkField.getBattleField()[computerShoots.getY()][computerShoots.getX()] = Field.FieldCells.DEADSHIP;
    }

    /**
     * метод проверки новых координат, сгенерированных в методе killWoundedShip в соответсвии с checkField комьюетера
     * вернет true если только по данной клетке комьютер еще не стрелял
     *
     * @param point
     * @return false
     */
    public boolean isAlreadyHit(Point point) {
        switch (checkField.getBattleField()[point.getY()][point.getX()]) {
            case EMPTY:
                return true;
            case DEADSHIP:
                return false;
            case MISSED:
                return false;
        }
        return false;
    }

    /**
     * метод возвращает координаты выстрела, сгенерированные выбранной стратегией комьютера или игрока
     */
    public Point makeTurn() {
        return strategy.findShip();
    }


    /**
     * метод стрельбы, который объединяет все стратегии комьютера
     */
//    public boolean fight() {
//        /**проверяем, если был подбит корабль противника, то значение countTry будет больше 0*/
//        if (countTry > 0) {
//            /**проверем, если жив корабль, то запускаем стратегию добивания корабля, если нет, то запускаем метод поиска нового корабля*/
//            if (player.isShipAlive(computerShoots)) {
//                /**проверяем, попал ли комьютер по кораблю по новым координатам*/
//                if (player.checkShootCoordinate(killWoundedShip(computerShoots, countTry))) {
//                    return true;
//                } else {
//                    countTry++;
//                    return false;
//                }
//                /**если корабль потоплен то обнуляем countTry*/
//            } else {
//                countTry = 0;
//            }
//        } else {
//            /**если countTry ==0, то запускаем стратегию поиска корабля противника*/
//            if (findShip()) {
//                /**проверем, если жив корабль, то запускаем стратегию добивания корабля, если нет, то запускаем метод поиска нового корабля*/
//                if (player.isShipAlive(computerShoots)) {
//                    /**проверяем, попал ли комьюетер по кораблю по новым координатам*/
//                    if (player.checkShootCoordinate(killWoundedShip(computerShoots, countTry))) {
//                        return true;
//                        /**если не попал, то увеличиваем счетчик countTry, и метод возвращает false*/
//                    } else {
//                        countTry++;
//                        return false;
//                    }
//                } else {
//                    countTry = 0;
//                }
//                /**если комьюетер промахнулся, то метод вернет false*/
//            } else {
//                return false;
//            }
//        }
//        return true;
//    }
}