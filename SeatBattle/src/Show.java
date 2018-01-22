import java.util.Scanner;

/**
 * Класс отображение
 *
 * @author Илья Богачев
 * @since 22.01.2018
 */
public class Show {
    Computer computer;
    Player player;


    /**
     * метод спрашивает имя пользователя, и выводит его в качестве имени нового игрока
     *
     * @return name
     */
    public void askUserName(ShootingShips shootingShips) {
        shootingShips = new Player();
        String name = "";
        System.out.println("Hello, my dear friend!");
        System.out.println("Please, introduce yourself:");
        Scanner scanner = new Scanner(System.in);
        try {
            name = scanner.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Our new Player's name is: " + name);
        System.out.println();
        shootingShips.setName(name);
    }

    /**
     * метод для определения режима игры- игрок -комьютер или игрок-игрок
     */
    public int chooseGameMode() {
        int number;
        System.out.println("Choose game mode: enter 1 to play with computer; enter 2 to play with Player");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                if (number < 1 || number > 2) {
                    System.out.println("Please enter number 1 or 2");
                    continue;
                } else {
                    break;
                }

            }
            String temp = scanner.nextLine();
            System.out.println("Please enter number 1 or 2");
        }
        return number;
    }

    /**
     * выводим сообщение о победе игрока или комьютера
     */
    public void showWinner(ShootingShips shootingShips) {
        System.out.println(shootingShips.getName() + " WINNER!!!");
    }
}
