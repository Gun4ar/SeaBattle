package view;

import model.Field;
import model.ShootingShips;


import java.util.Scanner;

/**
 * Класс отображение
 *
 * @author Илья Богачев
 * @since 08.02.2018
 */
public class ConsoleShow implements Show {

    /**
     * метод спрашивает имя пользователя, и выводит его в качестве имени нового игрока
     *
     * @return name
     */
    public String askUserName() {
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
        return name;
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

    /**
     * выводит игровое поле в зависимости от переданных параметров enum FieldCells
     *
     * @param field
     */
    public void drawField(Field field) {
        char letter = 'A';
        for (int j = 0; j < field.getBattleField().length; j++) {
            System.out.printf("%3d", j + 1);
        }
        System.out.println();
        for (int i = 0; i < field.getBattleField().length; i++) {
            System.out.print(letter++);
            for (int j = 0; j < field.getBattleField().length; j++) {
                switch (field.getBattleField()[i][j]) {
                    case EMPTY:
                        System.out.print(" | ");
                        break;
                    case ALIVESHIP:
                        System.out.print(" # ");
                        break;
                    case DEADSHIP:
                        System.out.print(" X ");
                        break;
                    case MISSED:
                        System.out.print(" * ");
                        break;
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * выводит сообщение о создании флота кораблей
     */
    public void createdShips() {
        System.out.println("Ships are created!");
    }

    public void shotTheShip() {
        System.out.println("Shot the ship!");
    }

    public void computerMissed() {
        System.out.println("Computer Missed");
    }
}
