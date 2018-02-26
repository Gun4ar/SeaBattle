package main.java.model;

/**
 * Класс отвечающий за обмен информацией между игроком и комьютером
 *
 * @author Илья Богачев
 * @since 17.02.2018
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

}
