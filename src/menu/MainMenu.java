package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import static tictacpro.MouseInput.inBounds;
import tictacpro.State;
import tictacpro.TicTacPro;

public class MainMenu {
    
    //what is being hovered over
    private int option = -1;
    
    public void tick() {
        
    }
    
    public void render(Graphics g) {
        //set color
        g.setColor(Color.BLACK);
        
        //draw title
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 128));
        g.drawString("TicTacPro", (TicTacPro.WIDTH/2) - ("TicTacPro".length()*76/2), TicTacPro.HEIGHT/3);
        
        //draw options
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 32));
        
        int width = 256+128;
        int height = 64;
        int x = (TicTacPro.WIDTH/2);
        int y = (TicTacPro.HEIGHT/2);
        
        g.setColor(Color.BLACK);
        if(option == 0)
            g.setColor(Color.RED);
        g.drawRect(x - (width/2), y - (height/2), width, height);
        g.drawString("Play", x - ("Play".length()*19/2), y+10);
        
//        y += 64+32;
//        g.setColor(Color.BLACK);
//        if(option == 1)
//            g.setColor(Color.RED);
//        g.drawRect(x - (width/2), y - (height/2), width, height);
//        g.drawString("Learn Strategy", x - ("Learn Strategy".length()*19/2), y+10);
        
        y += 64+32;
        g.setColor(Color.BLACK);
        if(option == 2)
            g.setColor(Color.RED);
        g.drawRect(x - (width/2), y - (height/2), width, height);
        g.drawString("Quit", x - ("Quit".length()*19/2), y+10);
    }
    
    public void mouseMoved(MouseEvent e) {
        int width = 256+128;
        int height = 64;
        int x = (TicTacPro.WIDTH/2);
        int y = (TicTacPro.HEIGHT/2);
        
        if(inBounds(e, new Rectangle(x - (width/2), y - (height/2), width, height))) {
            option = 0;
            return;
        }
        
//        y += 64+32;
//        if(inBounds(e, new Rectangle(x - (width/2), y - (height/2), width, height))) {
//            option = 1;
//            return;
//        }
        
        y += 64+32;
        if(inBounds(e, new Rectangle(x - (width/2), y - (height/2), width, height))) {
            option = 2;
            return;
        }
        
        option = -1;
    }
    
    public void mousePressed(MouseEvent e) {
        if(option == 0) {
            TicTacPro.state = State.SandboxOptions;
            TicTacPro.sandbox.mouseMoved(e);
        } else if(option == 1) {
            
        } else if(option == 2) {
            System.exit(0);
        }
    }
    
}
