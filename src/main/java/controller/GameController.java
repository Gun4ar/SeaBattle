package main.java.controller;

import model.Game;
import model.Point;
import model.Result;
import main.java.model.strategy.*;
import main.java.view.GameWindow;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Класс контроллер игры синглетон
 *
 * @author Богачев Илья
 * @since 17.02.2018
 */
public class GameController implements Runnable{
    private GameController(){}
    private Game game;
    private GameWindow gameWindow;
    private static Point point;
    private static Result.ShootResult shootResult;
    private static GameController ourInstance = new GameController();
    private static Game.gameMode mode;

    public static GameController getInstance() {
        return ourInstance;
    }


    public static Game.gameMode getMode() {
        return mode;
    }
    public static Result.ShootResult getShootResult() {
        return shootResult;
    }
    public static Point getPoint() {
        return point;
    }

    @Override
    public void run() {
        Game.getInstance().init();
        Game.getInstance().start();
    }

    /**метод запускает окно выбора режима игры*/
    public void setView(GameWindow gameWindow){
        /**вызов графического интерфейса (в процессе работы)*/
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    gameWindow.chooseGameMode();
                                   }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**получает значение ячейки, выбранной пользователем и отправляет эти значения в модель игры*/
    public static void getGUIShoot(Point point) {
         GameController.point = point;

    }

    /**получает информацию о результатах выстрела от противника и возвращает их в GUI*/
    public static void setModelResult(Result.ShootResult shootResult){
         GameController.shootResult =shootResult;
    }

    /**принимает значение выбора пользователем режима игры*/
    public static void setGameMode(Game.gameMode mode) {
        GameController.mode = mode;
    }
}
