/**
 * Класс Игра
 *
 * @author Илья Богачев
 * @since 28.01.2018
 */
public class Game {
    ConsoleShow consoleShow;
    Player player1;
    Player player2;
    Computer computer;
    private gameMode mode;
    private static Game ourInstance = new Game();

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
        consoleShow = new ConsoleShow();
        /**режим игрок против комьютера*/
        if (consoleShow.chooseGameMode() == 1) {
            player1 = new Player();
            player1.setName(consoleShow.askUserName());
            player1.putShips();
            computer = new Computer();
            computer.putShips();
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
            /**стрельба продолжается до тех пор, пока метод fight не вернет false, иными словами, пока комьюетер не промахнется*/
            while (true) {
                if (computer.fight()) {
                } else {
                    break;
                }
            }
            while (true) {
                if (player1.fight()) {
                } else {
                    break;
                }
            }
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
}