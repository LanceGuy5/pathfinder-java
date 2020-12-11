import javax.swing.*;
import java.awt.*;

public class Window {
    /**
     * Constructor - builds a window (JFrame) with the following information:
     * @param width The width of the window
     * @param height The height of the window
     * @param name The name of the window
     * @param c The program being put on the window as a component
     */
    public Window(int width, int height, String name, Component c, Panel p){
        JFrame frame = new JFrame(name);

        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.requestFocus();

        frame.add(c);
        frame.pack();

        frame.setVisible(true);
    }

    /**
     * Designs a window without the JPanel
     * @param width Width of the window
     * @param height Height of the window
     * @param name Name of the window
     * @param c Program beign ran on the window
     */
    public Window(int width, int height, String name, Component c){
        JFrame frame = new JFrame(name);

        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.requestFocus();

        frame.add(c);
        frame.pack();

        frame.setVisible(true);
    }
}
