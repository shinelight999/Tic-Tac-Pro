package sandbox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import static tictacpro.MouseInput.inBounds;
import tictacpro.State;
import tictacpro.TicTacPro;

public class SandboxOptions {
    
    //what is being hovered over
    private int option = -1;
    
    //the different possible options for a sandbox game
    int player = 0; //0;random, 1;X, 2;O
    int help = 0; //0;no help, 1;feedback, 2;guided
    int difficulty = 0; //0;easy, 1;hard
    
    public void tick() {
        
    }
    
    public void render(Graphics g) {
        //set color
        g.setColor(Color.BLACK);
        
        //draw title
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 64));
        g.drawString("Sandbox Mode", (TicTacPro.WIDTH/2) - ("Sandbox Mode".length()*38/2), TicTacPro.HEIGHT/5);
        g.drawString("Options", (TicTacPro.WIDTH/2) - ("Options".length()*38/2), TicTacPro.HEIGHT/5+64);
        
        //draw options
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 32));
        Rectangle box;
        
        int width = 256-32;
        int height = 64;
        int x = (TicTacPro.WIDTH/4);
        int y = (TicTacPro.HEIGHT/2)-64-32;
        
        box = new Rectangle(x - (width/2), y - (height/2), width, height);
        drawBox(g, box, "Random", player == 0, option == 0);
        
        x += (TicTacPro.WIDTH/4);
        box = new Rectangle(x - (width/2), y - (height/2), width, height);
        drawBox(g, box, "X", player == 1, option == 1);
        
        x += (TicTacPro.WIDTH/4);
        box = new Rectangle(x - (width/2), y - (height/2), width, height);
        drawBox(g, box, "O", player == 2, option == 2);
        
        x = (TicTacPro.WIDTH/3);
        y += 64+32;
        box = new Rectangle(x - (width/2), y - (height/2), width, height);
        drawBox(g, box, "No Help", help == 0, option == 3);
        
        x += (TicTacPro.WIDTH/3);
        box = new Rectangle(x - (width/2), y - (height/2), width, height);
        drawBox(g, box, "Guided", help == 2, option == 5);
        
        x = (TicTacPro.WIDTH/4);
        y += 64+32;
        box = new Rectangle(x - (width/2), y - (height/2), width, height);
        drawBox(g, box, "Easy", difficulty == 0, option == 6);
        
        x += (TicTacPro.WIDTH/4);
        box = new Rectangle(x - (width/2), y - (height/2), width, height);
        drawBox(g, box, "Medium", difficulty == 1, option == 7);
        
        x += (TicTacPro.WIDTH/4);
        box = new Rectangle(x - (width/2), y - (height/2), width, height);
        drawBox(g, box, "Impossible", difficulty == 2, option == 8);
        
        x = (TicTacPro.WIDTH/3);
        y += 128+64;
        box = new Rectangle(x - (width/2), y - (height/2), width, height);
        drawBox(g, box, "Return", false, option == 9);
        
        x += (TicTacPro.WIDTH/3);
        box = new Rectangle(x - (width/2), y - (height/2), width, height);
        drawBox(g, box, "Start", false, option == 10);
        
        //info text
        drawInfoText(g);
    }
    
    private void drawBox(Graphics g, Rectangle box, String str, boolean selected, boolean hover) {
        if(selected) {
            g.setColor(Color.RED);
            g.fillRect((int)box.getX(), (int)box.getY(), (int)box.getWidth(), (int)box.getHeight());
            
            g.setColor(Color.WHITE);
            g.drawString(str, (int)box.getX() + (int)box.getWidth()/2 - str.length()*19/2, (int)box.getY() + (int)box.getHeight()/2 + 10);
        } else {
            if(hover) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawRect((int)box.getX(), (int)box.getY(), (int)box.getWidth(), (int)box.getHeight());
            g.drawString(str, (int)box.getX() + (int)box.getWidth()/2 - str.length()*19/2, (int)box.getY() + (int)box.getHeight()/2 + 10);
        }
    }
    
    private void drawInfoText(Graphics g) {
        g.setColor(Color.BLACK);
        
        String choice = "";
        String text = "";
        if(option == 0) {
            choice = "Random";
            text = "The first player is chosen randomly.";
        } else if(option == 1) {
            choice = "X";
            text = "You play as X; you go first.";
        } else if(option == 2) {
            choice = "O";
            text = "You play as O; you go second.";
        } else if(option == 3) {
            choice = "No Help";
            text = "You're on your own, bud.";
        } else if(option == 5) {
            choice = "Guided";
            text = "We'll show you the best move before you take your turn.";
        } else if(option == 6) {
            choice = "Easy";
            text = "Computer makes choices randomly.";
        } else if(option == 7) {
            choice = "Medium";
            text = "Computer is competent, but can still be beaten.";
        } else if(option == 8) {
            choice = "Impossible";
            text = "Computer uses perfect play; winning in this mode is impossible.";
        } else if(option == 9) {
            choice = "Return";
            text = "Go back to the main menu.";
        } else if(option == 10) {
            choice = "Start";
            text = "Start the game!";
        }
        
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        g.drawString(choice, TicTacPro.WIDTH/2 - choice.length()*14/2, TicTacPro.HEIGHT - 64 - 24);
        g.setFont(new Font(Font.MONOSPACED, 0, 24));
        g.drawString(text, TicTacPro.WIDTH/2 - text.length()*14/2, TicTacPro.HEIGHT - 64);
    }
    
    public void mouseMoved(MouseEvent e) {
        int width = 256;
        int height = 64;
        int x = (TicTacPro.WIDTH/4);
        int y = (TicTacPro.HEIGHT/2)-64-32;
        
        if(inBounds(e, new Rectangle(x - (width/2), y - (height/2), width, height))) {
            option = 0;
            return;
        }
        
        x += (TicTacPro.WIDTH/4);
        if(inBounds(e, new Rectangle(x - (width/2), y - (height/2), width, height))) {
            option = 1;
            return;
        }
        
        x += (TicTacPro.WIDTH/4);
        if(inBounds(e, new Rectangle(x - (width/2), y - (height/2), width, height))) {
            option = 2;
            return;
        }
        
        x = (TicTacPro.WIDTH/3);
        y += 64+32;
        if(inBounds(e, new Rectangle(x - (width/2), y - (height/2), width, height))) {
            option = 3;
            return;
        }
        
        x += (TicTacPro.WIDTH/3);
        if(inBounds(e, new Rectangle(x - (width/2), y - (height/2), width, height))) {
            option = 5;
            return;
        }
        
        x = (TicTacPro.WIDTH/4);
        y += 64+32;
        if(inBounds(e, new Rectangle(x - (width/2), y - (height/2), width, height))) {
            option = 6;
            return;
        }
        
        x += (TicTacPro.WIDTH/4);
        if(inBounds(e, new Rectangle(x - (width/2), y - (height/2), width, height))) {
            option = 7;
            return;
        }
        
        x += (TicTacPro.WIDTH/4);
        if(inBounds(e, new Rectangle(x - (width/2), y - (height/2), width, height))) {
            option = 8;
            return;
        }
        
        x = (TicTacPro.WIDTH/3);
        y += 128+64;
        if(inBounds(e, new Rectangle(x - (width/2), y - (height/2), width, height))) {
            option = 9;
            return;
        }
        
        x += (TicTacPro.WIDTH/3);
        if(inBounds(e, new Rectangle(x - (width/2), y - (height/2), width, height))) {
            option = 10;
            return;
        }
        
        option = -1;
    }
    
    public void mousePressed(MouseEvent e) {
        if(option == 0) {
            player = 0;
        } else if(option == 1) {
            player = 1;
        } else if(option == 2) {
            player = 2;
        } else if(option == 3) {
            help = 0;
        } else if(option == 4) {
            help = 1;
        } else if(option == 5) {
            help = 2;
        } else if(option == 6) {
            difficulty = 0;
        } else if(option == 7) {
            difficulty = 1;
        } else if(option == 8) {
            difficulty = 2;
        } else if(option == 9) {
            //returns to the main menu
            TicTacPro.state = State.MainMenu;
            TicTacPro.mm.mouseMoved(e);
        } else if(option == 10) {
            //starts the game
            TicTacPro.sandbox.sg = new SandboxGame(player, help, difficulty);
            TicTacPro.state = State.SandboxGame;
        }
    }
    
}
