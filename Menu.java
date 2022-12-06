package engine;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;

import launcher.GameProcess;

public class Menu {
    public Menu() {
        JFrame menu = new JFrame("Chasse");
        menu.setSize(new Dimension(500, 300));
        menu.setLayout(null);
        menu.setLocationRelativeTo(null);
        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // header
        JPanel header = new JPanel();
        header.setSize(new Dimension(500, 100));
        header.setLocation(0, 0);
        JLabel headerText = new JLabel("Fort-night");
        headerText.setAlignmentX(SwingConstants.CENTER);
        headerText.setAlignmentY(SwingConstants.CENTER);

        header.add(headerText);

        // main
        JPanel main = new JPanel();
        main.setSize(new Dimension(500, 150));
        main.setLocation(0, 100);

        GridLayout gl = new GridLayout(1, 2);
        gl.setHgap(20);
        gl.setVgap(20);
        main.setLayout(gl);

        JButton host = new JButton("Host The Game");
        host.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(50, 50, 50)));
        host.setBackground(new Color(230, 230, 230));
        host.setName("host");

        JButton join = new JButton("Join The Game");
        join.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(50, 50, 50)));
        join.setBackground(new Color(230, 230, 230));
        join.setName("join");

        main.add(host);
        main.add(join);
        /////

        // listeners;
        GameProcess process = new GameProcess(menu);
        host.addMouseListener(process);
        join.addMouseListener(process);

        menu.add(header);
        menu.add(main);

        menu.setVisible(true);
    }
}
