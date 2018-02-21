package controller;

import model.Game;
import model.Point;
import model.Result;
import model.Strategy;
import view.GameWindow;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Класс контроллер игры
 *
 * @author Богачев Илья
 * @since 17.02.2018
 */
public class GameController implements Runnable{
    private Game game;
    private GameWindow gameWindow;
    private static Point point;
    private static Result.ShootResult shootResult;

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

    /**метод устанавливает окно игры*/
    public void setView(GameWindow gameWindow){
        /**вызов графического интерфейса (в процессе работы)*/
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    gameWindow.showWindow();//необходимо дорабатывать
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
}
