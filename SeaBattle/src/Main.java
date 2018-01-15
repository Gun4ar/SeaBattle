public class Main {
    /**
     * Главный класс - точка фхода программы
     *
     * @author Илья Богачев
     * @since 14.01.2018
     */
    public static void main(String[] args) {
            Game.getInstance().init();
            Game.getInstance().start();
    }
}
