package sandbox;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import tictacpro.State;
import tictacpro.TicTacPro;

public class Sandbox {
    
    SandboxOptions so;
    SandboxGame sg;
    
    public Sandbox() {
        so = new SandboxOptions();
    }
    
    public void tick() {
        if(TicTacPro.state == State.SandboxOptions) {
            so.tick();
        } else if(TicTacPro.state == State.SandboxGame) {
            sg.tick();
        }
    }
    
    public void render(Graphics g) {
        if(TicTacPro.state == State.SandboxOptions) {
            so.render(g);
        } else if(TicTacPro.state == State.SandboxGame) {
            sg.render(g);
        }
    }
    
    public void mouseMoved(MouseEvent e) {
        if(TicTacPro.state == State.SandboxOptions) {
            so.mouseMoved(e);
        } else if(TicTacPro.state == State.SandboxGame) {
            sg.mouseMoved(e);
        }
    }
    
    public void mousePressed(MouseEvent e) {
        if(TicTacPro.state == State.SandboxOptions) {
            so.mousePressed(e);
        } else if(TicTacPro.state == State.SandboxGame) {
            sg.mousePressed(e);
        }
    }
    
}
