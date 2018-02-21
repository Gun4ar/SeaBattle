package model;

/**
 * Класс Комьютер
 *
 * @author Илья Богачев
 * @since 21.02.2018
 */
public class Computer extends Player implements ShootingShips {
    Strategy strategy = new Strategy();
    private Point computerShoots;

    public Point getComputerShoots() {
        return computerShoots;
    }

    /**
     * метод создания флота компьютера, создание полей проверки и поля с флотом компьютера
     */
    @Override
    public void putShips() {
        NavyFactory navyFactory = new NavyFactory();
        navy = navyFactory.createNavy();
        field = new Field(10, 10);
        field.setBattleField(navy);
        checkField = new Field(10, 10);


    }

    public Computer() {
        name = "GreatestComputerMindEVER";
    }

    /**
     * отмечаем потопленную ячейку корабля на проверочном поле
     */
    public void computerHit(Point computerShoots) {
        checkField.getBattleField()[computerShoots.getY()][computerShoots.getX()] = Field.FieldCells.DEADSHIP;
    }

    /**
     * метод проверки новых координат, сгенерированных в методе killWoundedShip в соответсвии с checkField комьюетера
     * вернет true если только по данной клетке комьютер еще не стрелял
     *
     * @param point
     * @return false
     */
    public boolean isAlreadyHit(Point point) {
        switch (checkField.getBattleField()[point.getY()][point.getX()]) {
            case EMPTY:
                return true;
            case DEADSHIP:
                return false;
            case MISSED:
                return false;
        }
        return false;
    }

    /**
     * метод возвращает координаты выстрела, сгенерированные выбранной стратегией комьютера или игрока
     */
    public Point makeTurn() {
        /**если флаг включен, значит было попадание по противнику*/
        if (Game.getInstance().isFlag()) {
            return strategy.killWoundedShip(Game.getInstance().getComputerPoint(), Game.getInstance().getComputerTryKill(), this);
        } else
            return strategy.findShip(Game.getInstance().computer);
    }
}