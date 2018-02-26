package main.java.view;

import model.Field;
import model.ShootingShips;

/**
 * Интерфейс выводящий информацию на экран
 *
 * @author Илья Богачев
 * @since 28.01.2018
 */
public interface Show  {
    /**запросить имя игрока*/
    String askUserName();
    /**выбрать режим игры*/
    void chooseGameMode();
    /**вывести победителя*/
    void showWinner(ShootingShips shootingShips);
    /**показать игровое поле*/
    void drawField(Field field);
    /**информация о создании флота*/
    void createdShips();


}
