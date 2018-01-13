/**
 * Created by Bogachevy on 24.12.2017.
 */
public class Main {
    /**
     * точка входа программы, инициализация игры и запуск     *
     */
    public static void main(String[] args) {
            Game.getInstance().init();
            Game.getInstance().start();
    }
}
