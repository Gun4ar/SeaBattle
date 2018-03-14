package main.java.controller;

import main.java.model.Field;
import main.java.model.Game;
import main.java.model.Point;
import main.java.model.Result;
import main.java.view.GameWindow;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Класс контроллер игры синглетон
 *
 * @author Богачев Илья
 * @since 14.03.2018
 */
public class GameController implements Runnable {
    private static Field playerBattleField;
    private Game game;
    private GameWindow gameWindow;
    private static Point point;
    private static Result.ShootResult shootResult;
    private static GameController ourInstance = new GameController();
    private static int mode;
    private GameController() { }
    public static GameController getInstance() {
        return ourInstance;
    }

    public static int getMode() {
        return mode;
    }

    public static Result.ShootResult getShootResult() {
        return shootResult;
    }

    public static Point getPoint() {
        return point;
    }

    public static Field getPlayerBattleField() {
        return playerBattleField;
    }

    @Override
    public void run() {
        Game.getInstance().init();
        Game.getInstance().start();
    }

    /**
     * метод запускает окно выбора режима игры
     */
    public void setView(Callable gameWindow) {
        FutureTask futureTask = new FutureTask(gameWindow);
        new Thread(futureTask).start();
        try {
           setGameMode((Integer) futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    /**
     * получает значение ячейки, выбранной пользователем и отправляет эти значения в модель игры
     */
    public static void getGUIShoot(Point point) {
        GameController.point = point;
    }

    /**
     * получает информацию о результатах выстрела от противника и возвращает их в GUI
     */
    public static void setModelResult(Result.ShootResult shootResult) {
        GameController.shootResult = shootResult;
    }

    /**
     * принимает значение выбора пользователем режима игры
     */
    public static void setGameMode(int mode) {
        GameController.mode = mode;
    }


    public static void getComputerShot(Field field) {
        GameController.playerBattleField = field;

    }
}
