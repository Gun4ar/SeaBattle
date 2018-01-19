
import java.util.List;
/**
 * Класс Игровое поле
 *
 * @author Илья Богачев
 * @since 14.01.2018
 */
public class Field {

    private int numbVert;
    private int numbGoris;

    /**
     * хранятся значения ячеек поля с кораблями и их состояние, а так же выстрелы игроков
     */
    enum FieldCells {
        EMPTY, ALIVESHIP, DEADSHIP, MISSED,
    }

    public FieldCells[][] getBattleField() {
        return battleField;
    }

    /**
     * метод установки одного корабля на поле
     */
    public FieldCells [][] setOneShip(Ship ship) {
        for (int i = 0; i < ship.getShipCells().size(); i++) {
            battleField[ship.getShipCells().get(i).getCoordinateY()][ship.getShipCells().get(i).getCoordinateX()] = FieldCells.ALIVESHIP;
        }
        return battleField;
    }

    /**
     * Принимает массив кораблей, для каждого коробля по координатом его палуб устанавливает значение поля ALIVESHIP
     */
    public FieldCells [][] setBattleField(List<Ship> shipsNavy) {
        for (int i = 0; i < shipsNavy.size(); i++) {
            for (int j = 0; j < shipsNavy.get(i).getShipCells().size(); j++) {
                battleField[shipsNavy.get(i).getShipCells().get(j).getCoordinateY()][shipsNavy.get(i).getShipCells().get(j).getCoordinateX()] = FieldCells.ALIVESHIP;
            }
        }
        return battleField;
    }

    private FieldCells[][] battleField;

    /**
     * При создании экземпляра класса создается игровое поля заданных размеров (двумерынй массив), на основе которого строится игровое поле
     * в массиве хранятся значения ячеек поля, которые устанавливаются посредством метода
     */
    public Field(int numbVert, int numbGoris) {
        battleField = new FieldCells[numbGoris][numbVert];
    }

    /**
     * создает поле заполненное значением EMPTY
     */
    public FieldCells[][] initBattleField() {
        for (int i = 0; i < battleField.length; i++) {
            for (int j = 0; j < battleField.length; j++) {
                battleField[i][j] = FieldCells.EMPTY;
            }
        }
        return battleField;
    }

    /**
     * Осуществляет проверку положения корабля
     *
     * @param ship передает число палуб корабля и положение на поле
     * @return
     */
    public boolean checkShip(Ship ship) {
         /**
         * проверяем, что корабль не выходит за границы поля в методе isOnField()
         * проверяем, что в данном месте поля нет корабля
         * перебираем в цикле все ячейки вокруг выбранной ячейки корабля в методе isNearShips()
         */
            for (int i = 0; i < ship.getShipCells().size(); i++) {
                if (isOnField(ship.getShipCells().get(i))) {
                    if (battleField[ship.getShipCells().get(i).getCoordinateY()][ship.getShipCells().get(i).getCoordinateX()] == FieldCells.EMPTY) {
                        if (isNearShips(ship.getShipCells().get(i))) {
                            return false;
                        }
                    }
                } else return false;
        } return true;
    }


    /**
     * проверка нахождения ячейки корабля на игровом поле
     */
    private boolean isOnField(ShipCell shipCell) {
        if (shipCell.getCoordinateX() <= (battleField.length - 1) && shipCell.getCoordinateY() <= (battleField.length - 1)) {
            if (shipCell.getCoordinateX()>=0&&shipCell.getCoordinateY()>=0){
                return true;
            }

        }
        return false;
    }

    /**проверка близости других кораблей вокруг указанной ячейки корабля, если есть, вернет true
     * переменные Ymin, Ymax, Xmin, Xmax - задают область вокруг ячейки кораблся, при этом, если
     * область выходит за пределы игрового поля, то начальные и конечные значения уменьшаются
     *
     * @param shipCell -ячейка корабля
     * @return boolean
     */
    private boolean isNearShips(ShipCell shipCell){
        int Ymin = shipCell.getCoordinateY() - 1;
        int Ymax = shipCell.getCoordinateY() + 1;
        int Xmin = shipCell.getCoordinateX() - 1;
        int Xmax = shipCell.getCoordinateX() + 1;
        if (Ymin<0){
            Ymin=0;
        }
        if (Xmin<0){
            Xmin =0;
        }
        if (Ymax>(battleField.length-1)){
            Ymax=(battleField.length-1);
        }
        if (Xmax>(battleField.length-1)){
            Xmax=(battleField.length-1);
        }
        for (int i = Ymin; i <=Ymax ; i++) {
            for (int j = Xmin; j <= Xmax; j++) {
                if (battleField[i][j] ==(FieldCells.ALIVESHIP)){
                    return true;
                }
            }

        }
        return false;
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
