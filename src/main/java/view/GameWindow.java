package main.java.view;

import jdk.nashorn.internal.codegen.CompilerConstants;
import main.java.controller.GameController;
import main.java.model.*;
import main.java.model.Point;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Класс GUI
 *
 * @author Богачев Илья
 * @since 14.03.2018
 */
public class GameWindow extends JFrame implements Show, Callable {
    JButton[][] playerCheckFieldCells;
    JButton[][] playerBattlefieldCells;
    public static final int SIZE = 10;

    /**
     * показать окно игры
     */
    public void refreshWindow() {
        setTitle("Sea Battle");
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuBar jMenuBar = new JMenuBar();
        JMenu game = new JMenu("Game");
        JMenu about = new JMenu("About");

        jMenuBar.add(game);
        jMenuBar.add(about);

        JMenuItem newGame = new JMenuItem("New...");
        JMenuItem restartGame = new JMenuItem("Restart");
        JMenuItem closeGame = new JMenuItem("Close Game");
        JMenuItem help = new JMenuItem("Help");
        JMenuItem contacts = new JMenuItem("Contacts");

        game.add(newGame);
        game.add(restartGame);
        game.add(closeGame);
        about.add(help);
        about.add(contacts);

        closeGame.addActionListener(e -> System.exit(0));
        Object[] option = {"Yes", "No", "Cancel"};
        /**закрыть игру*/
        restartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame restartWindows = new JFrame();
                int n = JOptionPane.showOptionDialog(restartWindows, "Do you want Restart this Game?", "Restart Game", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, null, option, option[2]);
            }
        });

        setJMenuBar(jMenuBar);
        setLayout(new BorderLayout());
        JPanel playerCheckField = new JPanel();
        playerCheckField.setLayout(new GridLayout(10, 10));
        JPanel playerBattleField = new JPanel();
        playerBattleField.setLayout(new GridLayout(10, 10));
        JPanel structure =new JPanel();
        structure.setLayout(new GridLayout(2, 1));

        char letter = 'A';
        int number = 1;

        JPanel numbers = new JPanel();

        numbers.setLayout(new GridLayout(1, 10));

        for (int i = 0; i < 10; i++) {
            numbers.add(new JLabel(String.valueOf(number++)));

        }

        /**установить проверочное поле игрока*/
        playerCheckFieldCells = new JButton[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            playerCheckField.add(new JLabel(String.valueOf(letter++)));
            for (int j = 0; j < SIZE; j++) {
                JButton BattleFieldCell = new JButton("");
                playerCheckFieldCells[i][j] = BattleFieldCell;
                int finalX = j;
                int finalY = i;
                BattleFieldCell.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String buttonText = e.getActionCommand();
                        System.out.printf("Button, %s, x: %d, y: %d%n", buttonText, finalX, finalY);
                        CompletableFuture<Void> future = CompletableFuture
                                .runAsync(() ->GameController.getGUIShoot(new Point(finalX, finalY)))
                                .thenAccept(System.out::println);
                        try{
                            future.get();
                        }catch (InterruptedException | ExecutionException e1){
                            e1.printStackTrace();
                        }
                    }
                });
                playerCheckField.add(BattleFieldCell);
            }
        }
        structure.add(playerCheckField);


        /**создаем игровое поле игрока*/
        char letters ='A';
        playerBattlefieldCells = new JButton[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            playerBattleField.add(new JLabel(String.valueOf(letters++)));
            for (int j = 0; j < SIZE; j++) {
                JButton BattleFieldCell = new JButton("");
                playerCheckFieldCells[i][j] = BattleFieldCell;
                int finalX = j;
                int finalY = i;

                playerBattleField.add(BattleFieldCell);
            }
        }
        /**отрисовать поле игрока после выстрела комьютера*/
        structure.add(drawPlayerField(GameController.getPlayerBattleField()));
        structure.add(playerBattleField);
        add(structure);
        setVisible(true);

    }


    @Override
    public String askUserName() {
        return null;
    }
    /**Выводит окно выбора режима игры*/
    @Override
    public int chooseGameMode() {
        JFrame gameMode = new JFrame();
        gameMode.setTitle("Game Mode");
        gameMode.setSize(400, 200);
        gameMode.setLocationRelativeTo(null);
        gameMode.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameMode.setLayout(new GridLayout());
        JButton vsComputer = new JButton("One Player");
        JButton vsPlayer = new JButton("Two Players");
        gameMode.add(vsComputer);
        gameMode.add(vsPlayer);
        gameMode.setResizable(false);
        vsComputer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.getInstance().setGameMode (1);
                System.out.println("VSComputer");
                gameMode.setVisible(false);
            }
        });

        vsPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.getInstance().setGameMode (2);
                System.out.println("VSPlayer");
                gameMode.setVisible(false);
            }
        });

        gameMode.setVisible(true);

        return GameController.getMode();
    }



    @Override
    public void showWinner(ShootingShips shootingShips) {

    }

    @Override
    public void drawField(Field field) {

    }

    /**отрисовать поле игрока*/
    public JPanel drawPlayerField(Field field) {
        JButton battleFieldCell;
        JPanel jPanel =new JPanel();
        jPanel.setLayout(new GridLayout(SIZE, SIZE));
        char letters = 'A';
        JButton[][] buttons = new JButton[GameController.getPlayerBattleField().getBattleField().length][GameController.getPlayerBattleField().getBattleField().length];
        for (int i = 0; i < field.getBattleField().length; i++) {
            jPanel.add(new JLabel(String.valueOf(letters++)));
            for (int j = 0; j < field.getBattleField().length; j++) {
                switch (field.getBattleField()[i][j]) {
                    case EMPTY:
                        battleFieldCell = new JButton("");
                        break;
                    case ALIVESHIP:
                        battleFieldCell = new JButton("#");
                        break;
                    case DEADSHIP:
                        battleFieldCell = new JButton("X");
                        break;
                    case MISSED:
                        battleFieldCell = new JButton("*");
                        break;
                }
            }
        }
        return jPanel;
    }

    @Override
    public void createdShips() {

    }

    /**
     * отобразить результат выстрела по противнику
     */
    public void showShotResult(Result.ShootResult shootResult) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                switch (shootResult) {
                    case WOUND:
                        playerCheckFieldCells[GameController.getPoint().getY()][GameController.getPoint().getX()].setText("X");
                        break;
                    case KILL:
                        playerCheckFieldCells[GameController.getPoint().getY()][GameController.getPoint().getX()].setText("X");
                        break;
                    case MISS:
                        playerCheckFieldCells[GameController.getPoint().getY()][GameController.getPoint().getX()].setText("*");
                        break;
                    case UNKNOWN:
                        playerCheckFieldCells[GameController.getPoint().getY()][GameController.getPoint().getX()].setText("");
                        break;
                }
            }
        }
    }

    /**
     * метод устанавливает котроллер
     */
    public void setController(GameController controller) {//непонятно зачем устанавливать контроллер

    }

    /**вернет значение выбора режима игры*/
    @Override
    public Integer call() throws Exception {
        return chooseGameMode();
    }
}
