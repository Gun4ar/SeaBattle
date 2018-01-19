import java.util.Random;
/**
 * Класс Игра
 *
 * @author Илья Богачев
 * @since 14.01.2018
 */
public class Game {

    Player player1;
    Player computer;
    private static Game ourInstance = new Game();

    public static Game getInstance() {
        return ourInstance;
    }

    private Game() {
    }

    /**
     * создание игроков и игровых полей для них
     */
    public void init() {
        player1 = new Player();
        player1.putShips();
        computer = new Player("Computer");
        computer.computerPutShips();

    }

    public void start() {
        findWinner();
    }

    /**
     * метод заглушка случайного выбора победителя
     */
    public void findWinner() {
        Random random = new Random();
        int winnerNumber = random.nextInt(10);
        if (winnerNumber < 5) {
            System.out.println("You WON the game!");
        } else System.out.println("Sorry, the computer was better :(");
    }
}
