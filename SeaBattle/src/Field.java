import java.util.Map;

/**
 * Created by Bogachevy on 24.12.2017.
 */
public class Field {
    /**
     * При создании экземпляра класса создается игровое поля заданных размеров (двумерынй массив), на основе которого строится игровое поле
     * в массиве хранятся значения ячеек поля, которые устанавливаются посредством метода
     */
    private int numbVert;
    private int numbGoris;

    //хранятся значения ячеек поля с кораблями и их состояние, а так же выстрелы игроков
    enum FieldCells {
        EMPTY, ALIVESHIP, DEADSHIP, MISSED
    }

    public FieldCells[][] getBattleField() {
        return battleField;
    }

    /**
     * Принимает массив кораблей, для каждого коробля по координатом его палуб устанавливает значение поля ALIVESHIP
     */

    public void setBattleField(Map<Integer, Ship> shipMap) {
        try {
            for (Map.Entry<Integer, Ship> entry : shipMap.entrySet()) {
                for (int j = 0; j < entry.getValue().getShipCells().size(); j++) {
                    battleField[entry.getValue().getShipCells().get(j).getCoordinateY()][entry.getValue().getShipCells().get(j).getCoordinateX()] = FieldCells.ALIVESHIP;
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Не поместился корабль на поле!");
        }
    }

    private FieldCells[][] battleField;


    public Field(int numbVert, int numbGoris) {
        battleField = new FieldCells[numbGoris][numbVert];
    }

    /**
     * создает поле заполненное значением EMPTY     *
     */
    public FieldCells[][] createBattleFild() {
        for (int i = 0; i < battleField.length; i++) {
            for (int j = 0; j < battleField.length; j++) {
                battleField[i][j] = FieldCells.EMPTY;
            }
        }
        return battleField;
    }

    /**
     * Осуществляет проверку положения корабля
     * @param ship передает число палуб корабля и положение на поле
     * @param field необходимо для задания граничных условий размещения корабля
     * @return
     */

    public boolean checkShip(Ship ship, Field field) {
            switch (ship.getShipPosition()) {
                case HORIZONTAL:
                    for (int i = 0; i < ship.getShipCells().size(); i++) {
                        if ((ship.getShipCells().get(i).getCoordinateX()) < ((field.getBattleField().length - (ship.getShipCells().size()) + 1) + i)) {
                            return true;
                        } else return false;
                    }
                    break;
                case VERTICAL:
                    for (int i = 0; i < ship.getShipCells().size(); i++) {
                        if ((ship.getShipCells().get(i).getCoordinateY()) < ((field.getBattleField().length - (ship.getShipCells().size()) + 1) + i)) {
                            return true;
                        } else return false;
                    }
                    break;
            }

        return false;
    }

    //метод стрельбы по кораблю
    public void shootShips() {
        for (int i = 0; i < battleField.length; i++) {
            for (int j = 0; j < battleField.length; j++) {
                while (battleField[i][j] == FieldCells.ALIVESHIP) switch (battleField[i][j]) {
                    case EMPTY:
                        battleField[i][j] = FieldCells.MISSED;
                        break;
                    case MISSED:
                        battleField[i][j] = FieldCells.MISSED;
                        break;
                    case ALIVESHIP:
                        battleField[i][j] = FieldCells.DEADSHIP;
                        break;
                }
            }
        }
        drawField();
    }

    /**
     * выводит игровое поле в зависимости от переданных параметров enum FieldCells
     */

    public void drawField() {
        char letter = 'A';
        for (int j = 0; j < battleField.length; j++) {
            System.out.printf("%3d", j + 1);
        }
        System.out.println();
        for (int i = 0; i < battleField.length; i++) {
            System.out.print(letter++);
            for (int j = 0; j < battleField.length; j++) {
                switch (battleField[i][j]) {
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
                        System.out.println(" * ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
