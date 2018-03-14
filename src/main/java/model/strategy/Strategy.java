package main.java.model.strategy;

import main.java.controller.GameController;
import main.java.view.ConsoleShow;

import java.util.Random;

import main.java.model.Point;
import main.java.model.Computer;
import main.java.model.Player;

/**
 * Класс Стратегий
 *
 * @author Илья Богачев
 * @since 17.02.2018
 */
public class Strategy {
    private ConsoleShow consoleShow;

    /**
     * метод который должен будет возвращать значение координат клетки поля, на которую указал player в GUI
     *
     * @return point
     */
    public Point fight(Player player) {
        return GameController.getInstance().getPoint();
    }

    /**
     * метод поиска комьютером кораблей противника
     * возвращает координату выстрела, сгененированную случайно
     */
    public Point findShip(Computer computer) {
        return computer.shootShips();
    }

    /**
     * метод добивания раненых кораблей, создает новую координату, в зависимости от положения предыдущей на поле
     *
     * @param point координаты предыдущего выстрела
     * @return point координтаы попытки нового выстрела
     */
    public Point killWoundedShip(Point point, int countTry, Computer computer) {
        /**величина приращения координаты X*/
        int i = 1;
        /**величина приращения координаты Y*/
        int j = 1;
        /**проверяем новые координаты, если они уже были то продолжаем итерировать координаты*/
        do {
            switch (countTry) {
                /**если это первая попытка добить корабль противника, то увеличиваем X*/
                case 0:
                    point.setX(point.getX() + i);
                    break;
                /**если вторая то уменьшаем X*/
                case 1:
                    point.setX(point.getX() - i);
                    break;
                /**для третьей измениям Y*/
                case 2:
                    point.setX(point.getX() - 2 * i);
                    point.setY(point.getY() + j);
                    break;
                /**для четвертной уменьшаем Y*/
                case 3:
                    point.setY(point.getY() - j);
                    break;
            }

            /**проверяем, не вышли ли новые координаты за пределы игрового поля*/
            if (point.getX() < 0) {
                point.setX(0);
                countTry++;

            } else if (point.getX() > computer.getField().getBattleField().length - 1) {
                point.setX(computer.getField().getBattleField().length - 1);
                countTry++;
            }
            if (point.getY() < 0) {
                point.setY(0);
                countTry++;
            } else if (point.getY() > computer.getField().getBattleField().length - 1) {
                point.setY(computer.getField().getBattleField().length - 1);
                countTry++;
            }
            /**если выходит так, что при всех возможных вариантах стрельбы комьютер не смог добраться до корабля противника, то генерируем случайную точку и проверяем ее на момент предыдущих вызовов*/
            if (countTry >= 4) {
                Random random = new Random();
                point.setX(random.nextInt(10));
                point.setY(random.nextInt(10));
            }
        } while (!(computer.isAlreadyHit(point)));
        return point;
    }
}
