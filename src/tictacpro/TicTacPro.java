package tictacpro;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import menu.MainMenu;
import sandbox.Sandbox;

public class TicTacPro extends Canvas implements Runnable {

    //the game
    public static TicTacPro GAME;
    
    //WIDTH and HEIGHT of game
    public static final int WIDTH = 1024, HEIGHT = 1024;
    
    //thread variables
    private Thread thread;
    private boolean running = false;
    
    //the game state
    public static State state = State.MainMenu;
    
    //game objects
    public static MainMenu mm = new MainMenu();
    public static Sandbox sandbox = new Sandbox();
    
    public TicTacPro() {
        //loads in assets
        //Assets.loadImages();
        
        //creates the handlers
        //dungeonHandler = new DungeonHandler();
        
        //load save
        //LoadSave.loadSave();
        
        //keyboared input
        this.addKeyListener(new KeyInput());
        
        //mouse input
        MouseInput mouse = new MouseInput();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.addMouseWheelListener(mouse);
        
        //creates a Window which starts the game
        new Window(WIDTH, HEIGHT, "TicTacPro", this);
    }
    
    //starts the thread running the game
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    //stops the thread running the game
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int FRAMES = 0;
    
    @Override
    /**
     * This stores the entire game loop.
     */
    public void run() {
        //puts screen on top
        this.requestFocus();
        
        //gameloop logic
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        double deltaR = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            deltaR += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                //tick
                tick();
                updates++;
                delta--;
            }
            
            if(deltaR >= 1) {
                //render
                render();
                frames++;
                deltaR--;
            }
            
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " Ticks, FPS " + frames);
                updates = 0;
                FRAMES = frames;
                frames = 0;
            }
        }
        
        stop();
    }
    
    private void tick() {
        if(state == State.MainMenu) {
            mm.tick();
        } else if(state == State.SandboxOptions || state == State.SandboxGame) {
            sandbox.tick();
        }
    }
    
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        
        // START DRAWING
        
        g.setColor(Color.WHITE);
        g.setColor(new Color(167, 199, 231));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        if(state == State.MainMenu) {
            mm.render(g);
        } else if(state == State.SandboxOptions || state == State.SandboxGame) {
            sandbox.render(g);
        }
        
        // STOP DRAWING
        
        g.dispose();
        bs.show();
    }
    
    public static void main(String[] args) {
        GAME = new TicTacPro();
    }
    
}
