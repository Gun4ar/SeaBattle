package model;


import view.ConsoleShow;

import java.util.ArrayList;

/**
 * Класс Стратегий
 *
 * @author Илья Богачев
 * @since 10.02.2018
 */
public class Strategy {

    private ConsoleShow consoleShow;

    ArrayList<Point> points;

    /**
     * перечисления возможных попыток комьютера добить раненый корабль противника
     */
    public enum countTry {
        /**
         * REVERS - означает, что необходимо изменить значение текущей координаты на противоположное с изменением на величину приращения до промаха
         */
        XPLUS, XMINUS, YPLUS, YMINUS, REVERS
    }


    /**
     * метод который должен будет возвращать значение координат клетки поля, на которую указал player в GUI
     *
     * @return point
     *
     */
    public Point fight(Player player) {
        //на данный момент не реализован
        return player.shootShips();
    }

    /**
     * метод поиска комьютером кораблей противника
     * возвращает координату выстрела, сгененированную случайно
     */
    public Point findShip(Computer computer) {

        return computer.shootShips();
//       if (player.checkShootCoordinate(computerShoots = computer.shootShips())) {
//            /**отмечаем на проверочном поле попадание в корабль противника*/
//            computer.computerHit(computerShoots);
//            consoleShow.shotTheShip();
//            return true;
//        } else {
//            /**отмечаем промах на проверочном поле*/
//            computer.missed(computer.getComputerShoots());
//            consoleShow.computerMissed();
//            consoleShow.drawField(computer.getField());
//        }
//        return false;
    }

    /**
     * метод добивания раненых кораблей, создает новую координату, в зависимости от положения предыдущей на поле
     *
     * @param point координаты предыдущего выстрела
     * @return point координтаы попытки нового выстрела
     */
    public Point killWoundedShip(Point point, Strategy.countTry countTry, Computer computer) {
        /**величина приращения координаты X*/
        int i = 1;
        /**величина приращения координаты Y*/
        int j = 1;

        /**создаем массив для хранения значений начальных и последующих попыток выстрела*/
        points = new ArrayList<>();

        /**проверяем новые координаты, если они уже были то продолжаем итерировать координаты*/
        while (!(computer.isAlreadyHit(point))) {
            switch (countTry) {
                /**если это первая попытка добить корабль противника, то увеличиваем X*/
                case XPLUS:
                    point.setX(point.getX() + i);
                    break;
                /**если вторая то уменьшаем X*/
                case XMINUS:
                    point.setX(point.getX() - i);
                    break;
                /**для третьей измениям Y*/
                case YPLUS:
                    point.setY(point.getY() + j);
                    break;
                /**для четвертной уменьшаем Y*/
                case YMINUS:
                    point.setY(point.getY() - j);
                    break;
                case REVERS:


            }
            /**проверяем, не вышли ли новые координаты за пределы игрового поля*/
            if (point.getX() < 0) {
                point.setX(0);
            } else if (point.getX() > computer.getField().getBattleField().length - 1) {
                point.setX(computer.getField().getBattleField().length - 1);
            }
            if (point.getY() < 0) {
                point.setY(0);
            } else if (point.getY() > computer.getField().getBattleField().length - 1) {
                point.setY(computer.getField().getBattleField().length - 1);
            }
        }
        return point;
    }
}
