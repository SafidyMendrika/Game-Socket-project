package module;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;

public class Canvas extends JPanel {

    public Canvas(JFrame container) {
        super();

        this.setSize(container.getSize());
        this.setLocation(0, 0);
        this.setBackground(new Color(50, 50, 50));
    }
}