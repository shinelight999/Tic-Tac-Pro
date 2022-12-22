package tictacpro;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * This class stores all information on the Window and Canvas.
 * @author Brandon S costa
 */
public class Window extends Canvas {
    
    public Window(int width, int height, String title, TicTacPro game){
        JFrame frame = new JFrame(title);
        
        //sets size of frame
        Dimension size = new Dimension(width, height);
        frame.setPreferredSize(size);
        frame.setMaximumSize(size);
        frame.setMinimumSize(size);
        
        //makes undecorated full screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setUndecorated(true);
        frame.setResizable(false);
        
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.pack();
        
        //displays frame
        frame.setVisible(true);
        frame.toFront();
        //frame.setAlwaysOnTop(true);
        
        //starts game
        game.start();
    }
    
}
