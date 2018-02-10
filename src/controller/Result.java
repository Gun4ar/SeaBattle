package controller;

import model.Player;
import model.Point;

/**
 * Класс отвечающий за обмен информацией между игроком и комьютером
 *
 * @author Илья Богачев
 * @since 09.02.2018
 */
public class Result {

    /**
     * возможные результаты выстрела комьютера
     */
    public enum ShootResult {
        MISS, KILL, WOUND, UNKNOWN
    }

    /**
     * проверяет значение выстреля и возращает true при попадании и потоплении противника
     */
    public boolean isSuccessfully(Point point, Player currentEnemy) {
        switch (currentEnemy.getMessage(point)) {
            case UNKNOWN:
                return false;
            case MISS:
                return false;
            case KILL:
                return true;
            case WOUND:
                return true;
        }
        return false;

    }

    /**
     * метод проверяет успешность выстрела текущего игрока по полю текущего противника
     *
     * @return ShootResult     *
     */
    public ShootResult sendMessage(Point point, Player currentPlayer, Player currentEnemy) {
        switch (currentEnemy.getMessage(point)) {
            case WOUND:
                /**отмечаем попадание по противнику*/
                currentPlayer.playerHit(point);
                return ShootResult.WOUND;
            case KILL:
                currentPlayer.playerHit(point);
                return ShootResult.KILL;
            case MISS:
                /**отмечаем промах на проверочном поле*/
                currentPlayer.missed(point);
                return ShootResult.MISS;
            case UNKNOWN:
                return ShootResult.UNKNOWN;


        }
        return ShootResult.UNKNOWN;
    }

}
