package tictacpro;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    
    @Override
    public void mouseMoved(MouseEvent e) {
        if(TicTacPro.state == State.MainMenu) {
            TicTacPro.mm.mouseMoved(e);
        } else if(TicTacPro.state == State.SandboxOptions || TicTacPro.state == State.SandboxGame) {
            TicTacPro.sandbox.mouseMoved(e);
        } 
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(TicTacPro.state == State.MainMenu) {
            TicTacPro.mm.mousePressed(e);
        } else if(TicTacPro.state == State.SandboxOptions || TicTacPro.state == State.SandboxGame) {
            TicTacPro.sandbox.mousePressed(e);
        } 
    }
    
    public static boolean inBounds(MouseEvent e, Rectangle rect) {
        if(e.getX() >= rect.getMinX() && e.getY() >= rect.getMinY()) {
            if(e.getX() <= rect.getMaxX() && e.getY() <= rect.getMaxY()) {
                return true;
            }
        }
        return false;
    }
    
}
