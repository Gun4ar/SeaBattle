import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс Игрок
 *
 * @author Илья Богачев
 * @since 14.01.2018
 */
public class Player {

    private String name;

    public void setEnemyField(Field enemyField) {
        this.enemyField = enemyField;
    }


    private Field enemyField;

    /**
     * При создании нового игрока в конструкторе запрашивается его имя
     */
    public Player() {

        String name = null;
        System.out.println("Hello, my dear friend!");
        System.out.println("Please, introduce yourself:");
        Scanner scanner = new Scanner(System.in);
        try {
            name = scanner.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.name = name;
        toString();
        System.out.println();
    }

    /**
     * создается комьютер/противник
     *
     * @param computer
     */
    public Player(String computer) {
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Our new Player is: " + getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * создает новое игровое поля для каждого игрока, создает флот и размещает его на игровом поле, и выводит его на экран
     */
    public void putShips() {
        NavyFactory navyFactory = new NavyFactory();
        List<Ship> navy = navyFactory.createNavy();
        Field myField = navyFactory.getField();
        myField.setBattleField(navy);
        myField.drawField();
    }

    public void computerPutShips() {
        NavyFactory navyFactory = new NavyFactory();
        enemyField = new Field(10, 10);
        enemyField.initBattleField();
        //enemyField.drawField();
    }

    /**
     * метод стрельбы (автоматический режим)
     */
    public void shootShips(Field field) {
        for (int i = 0; i < field.getBattleField().length; i++) {
            for (int j = 0; j < field.getBattleField().length; j++) {
                while (field.getBattleField()[i][j] == Field.FieldCells.ALIVESHIP) {
                    switch (field.getBattleField()[i][j]) {
                        case EMPTY:
                            field.getBattleField()[i][j] = Field.FieldCells.MISSED;
                            break;
                        case MISSED:
                            field.getBattleField()[i][j] = Field.FieldCells.MISSED;
                            break;
                        case ALIVESHIP:
                            field.getBattleField()[i][j] = Field.FieldCells.DEADSHIP;
                            break;
                    }
                }
            }
        }
        field.drawField();
    }
}

