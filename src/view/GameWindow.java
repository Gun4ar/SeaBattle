package view;

import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Класс GUI
 *
 * @author Богачев Илья
 * @since 11.02.2018
 */
public class GameWindow extends JFrame implements Show {
    GameController gameController = new GameController();
    JButton[][] buttons;

    public static final int SIZE = 10;

    /**
     * показать окно игры
     */
    public void showWindow() {
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
        restartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame restartWindows = new JFrame();
                int n = JOptionPane.showOptionDialog(restartWindows, "Do you want Restart this Game?", "Restart Game", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, null, option, option[2]);

            }
        });
        setJMenuBar(jMenuBar);

        setLayout(new BorderLayout());


        JPanel PlayerField = new JPanel();

        PlayerField.setLayout(new GridLayout(10, 10));

        char letter = 'A';
        int number = 1;

        JPanel numbers = new JPanel();
        numbers.setLayout(new GridLayout(1, 10));


        for (int i = 0; i < 10; i++) {
            numbers.add(new JLabel(String.valueOf(number++)));

        }
        add(numbers);

        buttons = new JButton[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            PlayerField.add(new JLabel(String.valueOf(letter++)));
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
                        GameController.getGUIShoot(new model.Point(finalX, finalY));
                    }
                });

                PlayerField.add(BattleFieldCell);
            }
        }
        add(PlayerField);
        setVisible(true);
    }


    @Override
    public String askUserName() {
        return null;
    }

    @Override
    public int chooseGameMode() {
        return 0;
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
    public void showShotResult() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                switch (GameController.getShootResult()) {
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

    /**
     * инициализация окна
     */
    public void init() {//нет ясности что инициализировать

    }
}
