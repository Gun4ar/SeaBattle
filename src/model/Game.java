package model;

import controller.GameController;
import view.ConsoleShow;
import view.GameWindow;

/**
 * Класс Игра
 *
 * @author Илья Богачев
 * @since 21.02.2018
 */
public class Game {
    Player currentPlayer;
    Player currentEnemy;
    Result result = new Result();
    ConsoleShow consoleShow;
    Player player1;
    Player player2;
    Computer computer;
    GameController gameController = new GameController();
    GameWindow gameWindow = new GameWindow();
    private gameMode mode;

    private static Game ourInstance = new Game();

    private Point point;

    /**
     * переменная отвечающая за попытки комьютера добить раненый корабль
     */
    private int computerTryKill = 0;
    /**
     * определяет ранен ли был корабль противника комьютером на предыдущем выстреле
     */
    private boolean flag;
    private Point computerPoint;
    public boolean isFlag() {
        return flag;
    }
    public Point getComputerPoint() {
        return computerPoint;
    }
    public Point getPoint() {
        return point;
    }
    public static Game getInstance() {
        return ourInstance;
    }

    public int getComputerTryKill() {
        return computerTryKill;
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
            currentPlayer = choose(point, player1, computer);
            point = currentPlayer.makeTurn();
            /**передать результат выстрела игрока по противнику в котроллер*/
           // gameController.setModelResult(sendMessage(point, currentPlayer, currentEnemy));// TODO раскомментировать для работы с интерфейсом
            sendMessage(point, currentPlayer, currentEnemy);
            /**отобразить результат выстрела*/
          //  gameWindow.showShotResult();//TODO раскомментировать для работы с интерфейсом
            System.out.println("CountTry: " + computerTryKill);
            System.out.println("Field Enemy: " + currentEnemy.getName());
            consoleShow.drawField(currentEnemy.getField());
            System.out.println("Player Field " + currentPlayer.getName());
            consoleShow.drawField(currentPlayer.getField());
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
    public Player choose(Point point, Player player1, Player player2) {
        /**если результат выстрела был успешен, то текущий игрок остается текущим игроком*/
        /**первый выбор должен быть по умолчанию, до того, как какой либо игрок сделал свой ход*/
        if (point == null) {
            return currentPlayer;

        } else {
            if (result.isSuccessfully(point, currentEnemy)) {
                System.out.println("Before Switch: currentPlayer: " + currentPlayer.getName() + " currentEnemy: " + currentEnemy.getName());
                return currentPlayer;
                /**иначе, игрок и комьютер меняются*/
            } else {
                Player temp = currentPlayer;
                currentPlayer = currentEnemy;
                currentEnemy = temp;
                System.out.println("After Switch: currentPlayer: " + currentPlayer.getName() + " currentEnemy: " + currentEnemy.getName());
                return currentPlayer;
            }
        }
    }

    /**
     * метод проверяет успешность выстрела текущего игрока по полю текущего противника
     *
     * @return ShootResult
     */
    public Result.ShootResult sendMessage(Point point, Player currentPlayer, Player currentEnemy) {
        switch (currentEnemy.getMessage(point)) {
            case WOUND:
                /**отмечаем попадание по противнику*/
                currentPlayer.playerHit(point);
                if (currentPlayer instanceof Computer) {
                    flag = true;
                    computerPoint = point;
                }
                return Result.ShootResult.WOUND;
            case KILL:
                currentPlayer.playerHit(point);
                if (currentPlayer instanceof Computer) {
                    computerTryKill = 0;
                    flag = false;
                }
                return Result.ShootResult.KILL;
            case MISS:
                /**отмечаем промах на проверочном поле*/
                currentPlayer.missed(point);
                /**Если текущий игрок комьютер, то увеличиваем попытки добить корабль*/
                if (currentPlayer instanceof Computer) {
                    if (flag) {
                          computerTryKill++;
                          if (computerTryKill >=4){
                              computerTryKill=0;
                              break;
                          }
                    }
                }
                return Result.ShootResult.MISS;
            case UNKNOWN:
                return Result.ShootResult.UNKNOWN;
        }
        return Result.ShootResult.UNKNOWN;
    }
}