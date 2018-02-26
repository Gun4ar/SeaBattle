import main.java.controller.GameController;
import model.Game;
import main.java.view.GameWindow;



/**
 * Главный класс - точка фхода программы
 *
 * @author Илья Богачев
 * @since 21.02.2018
 */
public class Main {

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setController(GameController.getInstance());
        GameController.getInstance().setView(gameWindow);
        /**запускаем GUI интерфейс*/
        gameWindow.init();
        /**запускаем игру*/
        Thread thread = new Thread(GameController.getInstance());
        thread.start();
    }
}
