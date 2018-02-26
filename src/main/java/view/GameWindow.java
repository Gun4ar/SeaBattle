package main.java.view;

import com.sun.deploy.panel.JavaPanel;
import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Класс GUI
 *
 * @author Богачев Илья
 * @since 11.02.2018
 */
public class GameWindow extends JFrame implements Show {
    JButton[][] buttons;
    public static final int SIZE = 10;

    /**
     * показать окно игры
     */
    public void init() {
        setTitle("Sea Battle");
        setSize(1000, 500);
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
        JPanel playerField = new JPanel();
        playerField.setPreferredSize(new Dimension(400, 400));
        playerField.setLayout(new GridLayout(10, 10));
        JPanel structure =new JPanel();
        structure.setLayout(new GridLayout(2, 1));



        char letter = 'A';
        int number = 1;

        JPanel numbers = new JPanel();
        numbers.setPreferredSize(new Dimension(20, 1000));

        numbers.setLayout(new GridLayout(1, 10));

        for (int i = 0; i < 10; i++) {
            numbers.add(new JLabel(String.valueOf(number++)));

        }


        buttons = new JButton[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            playerField.add(new JLabel(String.valueOf(letter++)));
            for (int j = 0; j < SIZE; j++) {
                JButton BattleFieldCell = new JButton("");
                buttons[i][j] = BattleFieldCell;
                int finalX = j;
                int finalY = i;
                BattleFieldCell.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String buttonText = e.getActionCommand();
                        System.out.printf("Button, %s, x: %d, y: %d%n", buttonText, finalX, finalY);
                        GameController.getInstance().getGUIShoot(new model.Point(finalX, finalY));
                    }
                });

                playerField.add(BattleFieldCell);
            }
        }

        structure.add(numbers);
        structure.add(playerField);//TODO add to jPanel with two colum
        add(structure);
        setVisible(true);

    }


    @Override
    public String askUserName() {
        return null;
    }

    @Override
    public void chooseGameMode() {
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
                GameController.getInstance().setGameMode (Game.gameMode.PlayerVsComputer);
                System.out.println("VSComputer");
                gameMode.setVisible(false);
            }
        });

        vsPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.getInstance().setGameMode (Game.gameMode.PlayerVsPlayer);
                System.out.println("VSPlayer");
                gameMode.setVisible(false);
            }
        });

        gameMode.setVisible(true);
    }


    @Override
    public void showWinner(ShootingShips shootingShips) {

    }

    @Override
    public void drawField(Field field) {


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
                        buttons[GameController.getPoint().getY()][GameController.getPoint().getX()].setText("X");
                        break;
                    case KILL:
                        buttons[GameController.getPoint().getY()][GameController.getPoint().getX()].setText("X");
                        break;
                    case MISS:
                        buttons[GameController.getPoint().getY()][GameController.getPoint().getX()].setText("*");
                        break;
                    case UNKNOWN:
                        buttons[GameController.getPoint().getY()][GameController.getPoint().getX()].setText("");
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


}
