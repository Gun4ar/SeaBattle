/**
 * Класс Игра
 *
 * @author Илья Богачев
 * @since 23.01.2018
 */
public class Game {
    Show show;
    Player player1;
    Player player2;
    Computer computer;
    private int gameMode;
    private static Game ourInstance = new Game();

    public static Game getInstance() {
        return ourInstance;
    }

    private Game() {
    }

    /**
     * создание игроков и игровых полей для них
     */
    public void init() {
        gameMode = 0;
        show = new Show();
        /**режим игрок против комьютера*/
        if (show.chooseGameMode() == 1) {
            player1 = new Player();
            show.askUserName(player1);
            player1.putShips();
            computer = new Computer();
            computer.computerPutShips();
            gameMode = 1;
        } else {
            /**режим игрок против игрока*/
            player1 = new Player();
            show.askUserName(player1);
            player1.putShips();
            player2 = new Player();
            show.askUserName(player2);
            player2.putShips();
            gameMode = 2;
        }
    }

    /**
     * запускаем нужный режим игры в зависимости от выбранного игрок параметра
     */
    public void start() {
        if (gameMode == 1) {
            gameModeComputer();
        } else {
            if (gameMode == 2) {
                gameModePlayerVsPlayer();
            }
        }
    }

    /**
     * метод игры против другого человека(не раелизован)
     */
    private void gameModePlayerVsPlayer() {
        System.out.println("Player VS Player Mode");
    }

    /**
     * метод игры против комьютера
     */
    private void gameModeComputer() {
        int computerShoots[];
        int playerShoots[];
        while (!(findWinner())) {
            /**
             * если координаты выстрела совпадают с координатами корабля игрока, то метод вернет true и отметит на поле игрока эту ячейку как потопленную палубу
             * если комьютер промахнулся то венет false и отметит ячейку MISSED
             */
            while (true) {
                if (player1.checkShootCoordinate(computerShoots = computer.shootShips())) {//для теста координаты генерируются автоматически
                    /**отмечаем на проверочном поле попадание в корабль противника*/
                    computer.computerHit(computerShoots);
                    /**находим пораженный корабль флота игрока, и ячейку по переданным координатам, и присваиваем палубе сосотояние DEAD*/
                    player1.killShips(computerShoots);
                    System.out.println("After Computer shoot Ship: Y " + computerShoots[1] + " X " + computerShoots[0]);
                    show.drawField(player1.getPlayerField());
                    /**проверяем состояние флота игрока после попадания*/
                    System.out.println("Player's Navy: ");
                    for (int i = 0; i < player1.getPlayerNavy().size(); i++) {
                        for (int j = 0; j < player1.getPlayerNavy().get(i).getShipCells().size(); j++) {
                            System.out.print(player1.getPlayerNavy().get(i).getShipCells().get(j).getState() + " ");
                        }

                    }
                    continue;

                } else {
                    computer.missed(computerShoots);
                    System.out.println("Computer Missed");
                    show.drawField(computer.getComputerField());
                    show.drawField(player1.getPlayerField());
                    break;
                }
            }
            while (true) {


                /**для теста, координаты выстрела игрока будут генерироваться случайно*/
                if (computer.checkShootCoordinate(playerShoots = player1.shootShips())) {
                    player1.playerHit(playerShoots);
                    computer.killShips(playerShoots);
                    System.out.println("After Player Shot Ship: Y " + playerShoots[1] + " X " + playerShoots[0]);
                    show.drawField(computer.getComputerField());
                    /**проверяем состояние флота комьюетра после попадания*/
                    System.out.println("Computer's Navy: ");
                    for (int i = 0; i < computer.getComputerNavy().size(); i++) {
                        for (int j = 0; j < computer.getComputerNavy().get(i).getShipCells().size(); j++) {
                            System.out.print(computer.getComputerNavy().get(i).getShipCells().get(j).getState() + " ");
                        }
                    }
                    continue;


                } else {
                    player1.missed(playerShoots);
                    System.out.println("Player Missed");
                    show.drawField(computer.getComputerField());
                    show.drawField(player1.getPlayerCheckField());
                    break;
                }
            }
        }

    }

    /**
     * побеждает тот, кто первым потопил все корабли противника
     */
    public boolean findWinner() {
        ShootingShips shootingShips = player1;
        ShootingShips shootingShips1 = computer;
        show = new Show();
        /**если игрок потерял весь флот, то победил комьютер, иначе игрок*/
        if (player1.isLooseNavy()) {
            show.showWinner(computer);
            return true;
        } else {
            if (computer.isLooseNavy()) {
                show.showWinner(player1);
                return true;
            }
        }
        return false;
    }
}
