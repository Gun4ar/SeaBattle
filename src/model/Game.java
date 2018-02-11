package model;

import controller.Result;
import view.ConsoleShow;
import view.GuiShow;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Класс Игра
 *
 * @author Илья Богачев
 * @since 11.02.2018
 */
public class Game {
    Player currentPlayer;
    Player currentEnemy;
    Result result = new Result();
    private int countTry = 0;
    ConsoleShow consoleShow;
    GuiShow guiShow;
    Player player1;
    Player player2;
    Computer computer;
    private gameMode mode;
    private static Game ourInstance = new Game();
    private Point point;


    public static Game getInstance() {
        return ourInstance;
    }

    enum gameMode {
        PlayerVsComputer, PlayerVsPlayer,
    }

    private Game() {
    }

    /**
     * создание игроков и игровых полей для них
     */
    public void init() {
        /**вызов графического интерфейса (в процессе работы)*/
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    guiShow = new GuiShow();

                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
         //guiShow.showWindow(player1);//необходимо дорабатывать


        consoleShow = new ConsoleShow();
        /**режим игрок против комьютера*/
        if (consoleShow.chooseGameMode() == 1) {
            player1 = new Player();
            player1.setName(consoleShow.askUserName());
            player1.putShips();
            currentPlayer = player1;
            computer = new Computer();
            computer.putShips();
            currentEnemy = computer;
            mode = Game.gameMode.PlayerVsComputer;
        } else {
            /**режим игрок против игрока*/
            player1 = new Player();
            player1.setName(consoleShow.askUserName());
            player1.putShips();
            player2 = new Player();
            player2.setName(consoleShow.askUserName());
            player2.putShips();
            mode = Game.gameMode.PlayerVsPlayer;
        }
    }

    /**
     * запускаем нужный режим игры в зависимости от выбранного игрок параметра
     */
    public void start() {
        switch (mode) {
            case PlayerVsPlayer:
                gameModePlayerVsPlayer();
                break;
            case PlayerVsComputer:
                gameModeComputer();
                break;
        }
    }

    /**
     * метод игры против другого человека(не реализован)
     */
    private void gameModePlayerVsPlayer() {
        System.out.println("Player VS Player Mode");
    }

    /**
     * метод игры против комьютера
     */
    private void gameModeComputer() {
        while (!(findWinner())) {
            currentPlayer = choose(player1, computer);
            point = currentPlayer.makeTurn();
            result.sendMessage(point, currentPlayer, currentEnemy);
            consoleShow.drawField(currentEnemy.getField());
            consoleShow.drawField(currentPlayer.getCheckField());
        }
    }

    /**
     * побеждает тот, кто первым потопил все корабли противника
     */
    public boolean findWinner() {
        consoleShow = new ConsoleShow();
        /**если игрок потерял весь флот, то победил комьютер, иначе игрок*/
        if (player1.isLooseNavy()) {
            consoleShow.showWinner(computer);
            return true;
        } else {
            if (computer.isLooseNavy()) {
                consoleShow.showWinner(player1);
                return true;
            }
        }
        return false;
    }

    /**
     * метод возвращает текущего игрока
     */
    public Player choose(Player currentPlayer, Player currentEnemy) {
        /**если результат выстрела был успешен, то текущий игрок остается текущим игроком*/
        /**первый выбор должен быть по умолчанию, до того, как какой либо игрок сделал свой ход*/
        if (currentPlayer.getPoint() == null) {
            this.currentEnemy = currentEnemy;
            return this.currentPlayer = currentPlayer;

        } else {
            if (result.isSuccessfully(currentPlayer.getPoint(), currentEnemy)) {
                this.currentEnemy = currentEnemy;
                return this.currentPlayer = currentPlayer;
                /**иначе, игрок и комьютер меняются*/
            } else {
                this.currentEnemy = currentPlayer;
                return this.currentPlayer = currentEnemy;
            }
        }
    }
}