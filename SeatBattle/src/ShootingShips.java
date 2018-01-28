/**
 * Интерфейс Стреляющих по кораблям
 *
 * @since 22.01.2018
 * @author Илья Богачев
 */
public interface ShootingShips {
    /**метод стрельбы по кораблям*/
    Point shootShips();

    /**передаем имя игрока*/
    String getName();

    /**устанавливаем имя игрока*/
    void setName(String name);

    /**определяет, потерян ли весь флот*/
    boolean isLooseNavy();
}
