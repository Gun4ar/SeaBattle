import java.util.List;

/**
 * Класс Комьютер
 *
 * @author Илья Богачев
 * @since 28.01.2018
 */
public class Computer extends Player implements ShootingShips, Strategy {
    Player player = new Player();

    private int countTry;

    private Point computerShoots;
    /**
     * поле комьютера
     */
    private Field field;
    /**
     * поле проверки комьютера
     */
    private Field checkField;

    /**
     * имя комьютера
     */
    private String name;

    /**
     * флот компьютера
     */
    private List<Ship> navy;

    public int getCountTry() {
        return countTry;
    }

    public Point getComputerShoots() {
        return computerShoots;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public Field getCheckField() {
        return checkField;
    }

    @Override
    public List<Ship> getNavy() {
        return navy;
    }

    /**
     * метод создания флота компьютера, создание полей проверки и поля с флотом компьютера
     */
    @Override
    public void putShips() {
        NavyFactory navyFactory = new NavyFactory();
        navy = navyFactory.createNavy();
        field = new Field(10, 10);
        field.initBattleField();
        field.setBattleField(navy);
        checkField = new Field(10, 10);
        checkField.initBattleField();

    }

    public Computer() {
        name = "GreatestComputerMindEVER";
    }

    /**
     * метод возвращает true, если весь флот игрока комьютера потоплен
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
     * метод стрельбы автоматический режим генерации значений координат выстрела
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

    /**
     * если был промах, то необходимо отметить на поле checkField координату промаха
     */
    @Override
    public void missed(Point playerShoots) {
        checkField.getBattleField()[playerShoots.getY()][playerShoots.getX()] = Field.FieldCells.MISSED;
    }

    /**
     * метод проверки координта выстрела противника, вернет true , если только переданные координаты попали по кораблю игрока
     * и установит значение ячейки игрового поля в соответсвии с предыдущим ее значеним (DEADSHIP или MISSED)
     */
    @Override
    public boolean checkShootCoordinate(Point shootCoordinate) {
        switch (field.getBattleField()[shootCoordinate.getY()][shootCoordinate.getX()]) {
            case ALIVESHIP:
                field.getBattleField()[shootCoordinate.getY()][shootCoordinate.getX()] = Field.FieldCells.DEADSHIP;
                /**отмечаем пораженную ячейку корабля и устанавливаем значение DEAD кораблю, если он потоплен*/
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
     * отметить поподание противника
     */
    private void killShipCell(Point playerShoot) {
        /**ищем корабль в который попал противник и присваиваем ячейки корабля значение DEAD*/
        for (int i = 0; i < navy.size(); i++) {//пробегаем по массиву кораблей
            for (int j = 0; j < navy.get(i).getShipCells().size(); j++) { //пробегаем по массиву палуб конкретноего корабля
                /**находим ячейку корабля по указанным координатам*/
                if (navy.get(i).getShipCells().get(j).getCoordinateY() == playerShoot.getY() && navy.get(i).getShipCells().get(j).getCoordinateX() == playerShoot.getX()) {
                    /**устанавливаем значение этой ячейки DEAD*/
                    navy.get(i).getShipCells().get(j).setState(ShipCell.State.DEAD);
                    /**если все ячейки корабля имеют значение DEAD, то корабль считается потоплен и принимает это значение*/
                    if (navy.get(i).getShipCells().get(j).getState() == ShipCell.State.DEAD) {
                        navy.get(i).setShipState(Ship.ShipState.DEAD);
                    }
                    break;
                }
            }
        }
    }

    /**
     * отмечаем потопленную ячейку корабля на проверочном поле
     */
    public void computerHit(Point computerShoots) {
        checkField.getBattleField()[computerShoots.getY()][computerShoots.getX()] = Field.FieldCells.DEADSHIP;
    }

    /**
     * метод поиска комьютером кораблей противника
     * вернет true если попал по кораблю противника
     */
    public boolean findShip() {
        /**
         * если координаты выстрела совпадают с координатами корабля игрока, то метод вернет true и отметит на поле игрока эту ячейку как потопленную палубу
         * если комьютер промахнулся то венет false и отметит ячейку MISSED
         */
        if (player.checkShootCoordinate(computerShoots = shootShips())) {
            /**отмечаем на проверочном поле попадание в корабль противника*/
            computerHit(computerShoots);
            return true;
        } else {
            /**отмечаем промах на проверочном поле*/
            missed(computerShoots);
            System.out.println("Computer Missed");
            consoleShow.drawField(getField());
            consoleShow.drawField(player.getField());
        }
        return false;
    }

    /**
     * метод добивания раненых кораблей, создает новую координату, в зависимости от положения предыдущей на поле
     *
     * @param point    координаты предыдущего выстрела
     * @param countTry номер попытки стрельбы
     * @return point координтаы попытки нового выстрела
     */
    public Point killWoundedShip(Point point, int countTry) {
        /**проверяем новые координаты, если они уже были то продолжаем итерировать координаты*/
        while (!(isAlreadyHit(point))) {
            switch (countTry) {
                /**если это первая попытка добить корабль противника, то увеличиваем X*/
                case 0:
                    point.setX(point.getX() + 1);
                    break;
                /**если вторая то уменьшаем X*/
                case 1:
                    point.setX(point.getX() - 1);
                    break;
                /**для третьей измениям Y*/
                case 2:
                    point.setY(point.getY() + 1);
                    break;
                /**для четвертной уменьшаем Y*/
                case 3:
                    point.setY(point.getY() - 1);
                    break;
            }
            /**проверяем, не вышли ли новые координаты за пределы игрового поля*/
            if (point.getX() < 0) {
                point.setX(0);
            } else if (point.getX() > field.getBattleField().length - 1) {
                point.setX(field.getBattleField().length - 1);
            }
            if (point.getY() < 0) {
                point.setY(0);
            } else if (point.getY() > field.getBattleField().length - 1) {
                point.setY(field.getBattleField().length - 1);
            }
        }
        return point;
    }

    /**
     * определяет, потоплен ли корабль, вернет true если есть еще палубы на плаву
     *
     * @param point
     * @return false
     */

    /**
     * метод проверки новых координат, сгенерированных в методе killWoundedShip в соответсвии с checkField комьюетера
     * вернет true если только по данной клетке комьютер еще не стрелял
     *
     * @param point
     * @return false
     */
    private boolean isAlreadyHit(Point point) {
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
        }
        return false;
    }

    /**
     * метод стрельбы, который объединяет все стратегии комьюетра
     */
    public boolean fight() {
        /**проверяем, если был подбит корабль противника, то значение countTry будет больше 0*/
        if (countTry > 0) {
            /**проверем, если жив корабль, то запускаем стратегию добивания корабля, если нет, то запускаем метод поиска нового корабля*/
            if (player.isShipAlive(computerShoots)) {
                /**проверяем, попал ли комьютер по кораблю по новым координатам*/
                if (player.checkShootCoordinate(killWoundedShip(computerShoots))) {
                    return true;
                } else {
                    countTry++;
                    return false;
                }
                /**если корабль потоплен то обнуляем countTry*/
            } else {
                countTry = 0;
            }
        } else {
            /**если conutTry ==0, то запускаем стратегию поиска корабля противника*/
            if (findShip()) {
                /**проверем, если жив корабль, то запускаем стратегию добивания корабля, если нет, то запускаем метод поиска нового корабля*/
                if (player.isShipAlive(computerShoots)) {
                    /**проверяем, попал ли комьюетер по кораблю по новым координатам*/
                    if (player.checkShootCoordinate(killWoundedShip(computerShoots))) {
                        return true;
                        /**если не попал, то увеличиваем счетчик countTry, и метод возвращает false*/
                    } else {
                        countTry++;
                        return false;
                    }
                } else {
                    countTry = 0;
                }
                /**если комьюетер промахнулся, то метод вернет false*/
            } else {
                return false;
            }
        }
        return true;
    }
}