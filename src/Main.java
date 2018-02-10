import model.Game;

/**
 * Главный класс - точка фхода программы
 *
 * @author Илья Богачев
 * @since 08.02.2018
 */
public class Main {

    public static void main(String[] args) {
        Game.getInstance().init();
        Game.getInstance().start();
    }
}
