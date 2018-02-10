package view;

import model.Field;
import model.ShootingShips;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс GUI
 *
 * @author Богачев Илья
 * @since 29.01.2018
 */
public class GuiShow extends JFrame implements Show {
    JFrame jFrame =this;
    /**показать окно игры*/
    public void showWindow(){
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
        Object[] option ={"Yes", "No", "Cancel"};
        restartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int n = JOptionPane.showOptionDialog(jFrame, "Do you want Restart this Game?", "Restart Game", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, null, option, option[2]);

            }
        });
        setJMenuBar(jMenuBar);

        setLayout(new BorderLayout());


        JPanel jPanel = new JPanel();

        jPanel.setLayout(new GridLayout(10, 10));



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
}
