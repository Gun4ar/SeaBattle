import java.util.Random;
import java.util.Scanner;

/**
 * Created by Bogachevy on 24.12.2017.
 */
public class Game {
    /**
     * Создается игра на двух игроков или игрок-комьютер
     */
    Player player1;
    Player computer;
    private static Game ourInstance = new Game();

    public static Game getInstance() {
        return ourInstance;
    }

    private Game() {
    }

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
