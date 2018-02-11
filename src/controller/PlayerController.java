package controller;

import model.Computer;
import model.Player;
import model.Point;

/**
 * Класс управляющий передачей информации от пользователя через GUI в модель
 *
 * @author Богачев Илья
 * @since 11.02.2018
 */
public class PlayerController {
    static Player player;
    static Computer computer;
    static Result result = new Result();
    public static void doShoot(Point point) {
        switch (result.sendMessage (point, player, computer)){
            case WOUND:
                System.out.println("WOUND");
                break;
            case KILL:
                System.out.println("KILL");
                break;
            case MISS:
                System.out.println("MISS");
                break;
            case UNKNOWN:
                System.out.println("UNKNOWN");
                break;

        }
    }
}
