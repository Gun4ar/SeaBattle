import controller.GameController;
import model.Game;
import view.GameWindow;

/**
 * Главный класс - точка фхода программы
 *
 * @author Илья Богачев
 * @since 21.02.2018
 */
public class Main {

    public static void main(String[] args) {
        GameController controller = new GameController();
        GameWindow gameWindow = new GameWindow();
        gameWindow.setController(controller); //не понимаю логику этого метода
        controller.setView(gameWindow); //не понимаю что должно быть установлено
        /**запускаем GUI интерфейс*/
        gameWindow.init();
        /**запускаем игру*/
        controller.run();
    }
}
