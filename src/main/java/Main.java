package main.java;

import main.java.controller.GameController;
import main.java.model.Computer;
import main.java.view.GameWindow;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Главный класс - точка входа программы
 *
 * @author Илья Богачев
 * @since 14.03.2018
 */
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setController(GameController.getInstance());
        /**запустить окно выбора режима игры*/
        Callable<Integer> chooseGameMode = new GameWindow();
        GameController.getInstance().setView(chooseGameMode);
        /**запускаем GUI интерфейс*/
        gameWindow.refreshWindow();
        /**запускаем игру*/
        Thread thread = new Thread(GameController.getInstance());
        thread.start();
    }
}
