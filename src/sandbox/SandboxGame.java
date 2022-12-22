package sandbox;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Random;
import static tictacpro.MouseInput.inBounds;
import tictacpro.State;
import tictacpro.TicTacPro;

public class SandboxGame {
    
    //rng
    Random rand = new Random();
    
    //what is being hovered over
    private int option = -1;
    
    //settings
    int player, opponent; //1;X, 2;O
    int help;
    int difficulty;
    
    //boxes
    Rectangle boxes[] = new Rectangle[9];
    int moves[] = new int[9]; //0;nothing, 1;x, 2;o
    
    //gui variables
    boolean playerTurn = true;
    
    //how the winner won
    int waysToWin[][] = new int[8][3];
    int win = -1;
    int winner = -1;
    int numMoves = 0;
    boolean gameOver = false;
    
    //the ai variables
    int winningMove = -1;
    int best = -1;
    
    public SandboxGame(int player, int help, int difficulty) {
        //apply settings
        this.help = help;
        this.difficulty = difficulty;
        if(player == 0) {
            this.player += 1 + rand.nextInt(2);
        } else {
            this.player = player;
        }
        if(this.player == 1) {
            this.opponent = 2;
        } else { 
            this.opponent = 1;
            makeMove(); //if player is O, opponent goes first
            playerTurn = true;
        }
        
        //find best move
        best = bestMoveAllBoards(this.player);
        
        //create bounds
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                boxes[i + (j*3)] = new Rectangle(TicTacPro.WIDTH*(i+1)/5, TicTacPro.HEIGHT*(j+1)/5, TicTacPro.WIDTH/5, TicTacPro.HEIGHT/5);
            }
        }
        
        //initialize ways to win 2d array
        waysToWin[0][0] = 0;
        waysToWin[0][1] = 4;
        waysToWin[0][2] = 8;
        waysToWin[1][0] = 2;
        waysToWin[1][1] = 4;
        waysToWin[1][2] = 6;
        waysToWin[2][0] = 0;
        waysToWin[2][1] = 1;
        waysToWin[2][2] = 2;
        waysToWin[3][0] = 3;
        waysToWin[3][1] = 4;
        waysToWin[3][2] = 5;
        waysToWin[4][0] = 6;
        waysToWin[4][1] = 7;
        waysToWin[4][2] = 8;
        waysToWin[5][0] = 0;
        waysToWin[5][1] = 3;
        waysToWin[5][2] = 6;
        waysToWin[6][0] = 1;
        waysToWin[6][1] = 4;
        waysToWin[6][2] = 7;
        waysToWin[7][0] = 2;
        waysToWin[7][1] = 5;
        waysToWin[7][2] = 8;
    }
    
    public void tick() {
        
    }
    
    public void render(Graphics g) {
        //render board
        drawBoard(g);
        
        //draw moves
        String cha = "";
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 128));
        for(int i = 0; i < moves.length; i++) {
            g.setColor(Color.BLACK);
            if(win != -1)
                for(int j = 0; j < 3; j++)
                    if(waysToWin[win][j] == i)
                        g.setColor(Color.RED);
            
            cha = "";
            if(moves[i] == 1) {
                //draw X
                cha = "X";
            } else if(moves[i] == 2) {
                //draw O
                cha = "O";
            } else if(option == i && !gameOver) {
                //draw where the player is hovering
                g.setColor(new Color(0, 0, 0, .25f));
                cha = (player == 1 ? "X" : "O");
            } else if(best == i && help == 2) {
                g.setColor(new Color(0, 1.0f, 0, .5f));
                cha = (player == 1 ? "X" : "O");
            }
            g.drawString(cha, boxes[i].x + boxes[i].width/2 - 38, boxes[i].y + boxes[i].height/2 + 39);
        }
        
        //render return and play again buttons
        if(gameOver) {
            //set color
            g.setColor(Color.BLACK);

            //draw title
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 64));
            String temp = "Tie!";
            if(winner == player) {
                temp = "You won!";
            } else if(winner == opponent) {
                temp = "You lost.";
            }
            g.drawString(temp, (TicTacPro.WIDTH/2) - (temp.length()*38/2), 128);
            
            //draw options
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 32));
            Rectangle box;

            int width = 256+64;
            int height = 64;
            int x = (TicTacPro.WIDTH/3);
            int y = (TicTacPro.HEIGHT)-128;

            box = new Rectangle(x - (width/2), y - (height/2), width, height);
            drawBox(g, box, "Return To Menu", option == 0);

            x += (TicTacPro.WIDTH/3);
            box = new Rectangle(x - (width/2), y - (height/2), width, height);
            drawBox(g, box, "Play Again", option == 1);
        } else {
            //set color
            g.setColor(Color.BLACK);

            //draw title
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 64));
            String temp = "Your turn.";
            if(!playerTurn)
                temp = "Opponent's turn.";
            g.drawString(temp, (TicTacPro.WIDTH/2) - (temp.length()*38/2), 128);
        }
        
        //render hitboxes
//        g.setColor(Color.red);
//        for(Rectangle rect : boxes) {
//            g.drawRect(rect.x, rect.y, rect.width, rect.height);
//        }
    }
    
    private void drawBoard(Graphics g) {
        g.setColor(Color.BLACK);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(8f));
        
        g.drawLine(TicTacPro.WIDTH*2/5, TicTacPro.HEIGHT*1/5, TicTacPro.WIDTH*2/5, TicTacPro.HEIGHT*4/5);
        g.drawLine(TicTacPro.WIDTH*3/5, TicTacPro.HEIGHT*1/5, TicTacPro.WIDTH*3/5, TicTacPro.HEIGHT*4/5);
        
        g.drawLine(TicTacPro.WIDTH*1/5, TicTacPro.HEIGHT*2/5, TicTacPro.WIDTH*4/5, TicTacPro.HEIGHT*2/5);
        g.drawLine(TicTacPro.WIDTH*1/5, TicTacPro.HEIGHT*3/5, TicTacPro.WIDTH*4/5, TicTacPro.HEIGHT*3/5);
        
        g2d.setStroke(new BasicStroke(1f));
    }
    
    private void drawBox(Graphics g, Rectangle box, String str, boolean hover) {
        if(hover) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }
        g.drawRect((int)box.getX(), (int)box.getY(), (int)box.getWidth(), (int)box.getHeight());
        g.drawString(str, (int)box.getX() + (int)box.getWidth()/2 - str.length()*19/2, (int)box.getY() + (int)box.getHeight()/2 + 10);
    }
    
    public void mouseMoved(MouseEvent e) {
        if(gameOver) {
            Rectangle box;

            int width = 256+64;
            int height = 64;
            int x = (TicTacPro.WIDTH/3);
            int y = (TicTacPro.HEIGHT)-128;

            box = new Rectangle(x - (width/2), y - (height/2), width, height);
            if(inBounds(e, box)) {
                option = 0;
                return;
            }
            
            x += (TicTacPro.WIDTH/3);
            box = new Rectangle(x - (width/2), y - (height/2), width, height);
            if(inBounds(e, box)) {
                option = 1;
                return;
            }
        } else {
            for(int i = 0; i < boxes.length; i++) {
                if(inBounds(e, boxes[i])) {
                    option = i;
                    return;
                }
            }
        }
        option = -1;
    }
    
    public void mousePressed(MouseEvent e) {
        //don't do anything if nothign is selected
        if(option == -1)
            return;
        
        if(gameOver) {
            if(option == 0) {
                //return to menu
                TicTacPro.state = State.MainMenu;
                TicTacPro.mm.mouseMoved(e);
            } else if(option == 1) {
                TicTacPro.sandbox.sg = new SandboxGame(TicTacPro.sandbox.so.player, help, difficulty);
            }
        } else {
            if(moves[option] == 0) {
                best = -1;
                //player move
                moves[option] = player;
                numMoves++;

                //check if win
                if(win == -1)
                    win = isWinner(player);
                if(win == -1 && numMoves >= 9) {
                    winner = 0;
                    gameOver = true;
                }
                if(gameOver) {
                    mouseMoved(e);
                    return;
                }
                
                //enemy turn
                playerTurn = false;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
                //AI MOVE
                makeMove();
                playerTurn = true;

                //check if win
                if(win == -1)
                    win = isWinner(opponent);
                if(win == -1 && numMoves >= 9) {
                    winner = 0;
                    gameOver = true;
                }
                mouseMoved(e);
                
                //update best move
                if(win == -1)
                    best = bestMoveAllBoards(player);
            }
        }
    }
    
    //AI decision making 
    private void makeMove() {
        if(difficulty == 0) { //if playing on easy
            //otherwise do random move
            int move;
            do {
                move = rand.nextInt(9);
            } while(moves[move] != 0);
            moves[move] = opponent;
            numMoves++;
        } else if(difficulty == 1) { //if playing on medium
            //win if you can
            winningMove = aboutToWin(opponent);
            if(winningMove != -1) {
                moves[winningMove] = opponent;
                numMoves++;
                return;
            }
            //make sure to stop player about to win
            winningMove = aboutToWin(player);
            if(winningMove != -1) {
                moves[winningMove] = opponent;
                numMoves++;
                return;
            }
            //otherwise do random move
            int move;
            do {
                move = rand.nextInt(9);
            } while(moves[move] != 0);
            moves[move] = opponent;
            numMoves++;
        } else if(difficulty == 2) { //if playing on impossible
            //does best move if there is a clear best move, otherwise does random move
            int move;
            move = bestMoveAllBoards(opponent);
            if(move != -1) {
                moves[move] = opponent;
            } else {
                do {
                    move = rand.nextInt(9);
                } while(moves[move] != 0);
                moves[move] = opponent;
            }
            numMoves++;
        }
    }
    
    private int aboutToWin(int player) {
        boolean goals[];
        int goalsMet;
        for(int i = 0; i < waysToWin.length; i++) {
            goals = new boolean[3];
            goalsMet = 0;
            for(int j = 0; j < waysToWin[i].length; j++) { 
                if(moves[waysToWin[i][j]] == player) {
                    goals[j] = true;
                    goalsMet++;
                }
            }
            //if about to win
            if(goalsMet == 2) {
                for(int j = 0; j < waysToWin[i].length; j++) { 
                    if(moves[waysToWin[i][j]] == 0) {
                        return waysToWin[i][j];
                    }
                }
            }
        }
        return -1;
    }
    
    private int bestMoveAllBoards(int player) {
        int[] board = moves;
        int bm = -1;
        for(int i = 0; i < 4; i++) {
            bm = bestMove(board, player);
            System.out.println(bm);
            if(bm != -1) {
                for(int j = 0; j < i; j++) {
                    bm = rotateSpot(bm);
                }
                return bm;
            }
            board = rotateBoard(board);
        }
        return -1; //should never be called
    }
    
    private int bestMove(int[] board, int player) {
        int opponent = (player == 1 ? 2 : 1);
        
        //player should win
        if(aboutToWin(player) != -1)
            return aboutToWin(player);
        
        //player should not let opponent win
        if(aboutToWin(opponent) != -1)
            return aboutToWin(opponent);
        
        //first move should always be corner
        if(player == 1) { //X
            boolean temp;
            
            //first move, go in corner
            if(numMoves == 0)
                return 0;
            
            if(numMoves == 2 && board[0] == 1) {
                //second move, O in 1
                if(secondMove(board, 1) || secondMove(board, 7))
                    return 6;

                //second move, O in 2
                if(secondMove(board, 2) || secondMove(board, 4) || secondMove(board, 6))
                    return 8;

                //second move, O in 3
                if(secondMove(board, 3) || secondMove(board, 5) || secondMove(board, 8))
                    return 2;
            } else if(numMoves == 2 && board[4] == 1 && board[1] == 2) {
                return 0;
            }
            
            //if not first or third move, place x in center
            if(numMoves == 4) {
                if(board[0] != 0 && board[1] != 0 && board[4] != 0 && board[8] != 0)
                    return 6;
                if(board[0] != 0 && board[3] != 0 && board[4] != 0 && board[8] != 0)
                    return 2;
                if(board[0] != 0 && board[1] != 0 && board[2] != 0 && board[3] != 0)
                    return 4;
                if(board[0] != 0 && board[1] != 0 && board[2] != 0 && board[5] != 0)
                    return 4;
                
                //if 3 corners full, go in open corner
                int corners = 0;
                if(board[0] != 0)
                    corners++;
                if(board[2] != 0)
                    corners++;
                if(board[6] != 0)
                    corners++;
                if(board[8] != 0)
                    corners++;
                if(corners == 3) {
                    if(board[0] == 0)
                        return 0;
                    if(board[2] == 0)
                        return 2;
                    if(board[6] == 0)
                        return 6;
                    if(board[8] == 0)
                        return 8;
                }
            }
        } else { //O
            //first move should always be center in response to non-center move
            if(numMoves == 1) {
                if(board[4] == 0) {
                    return 4;
                } else {
                    return 0;
                }
            }
            
            //if the diagonal is occupied, go in a side, not a corner
            if(numMoves == 3) {
                if(board[0] == 1 && board[4] == 2 && board[8] == 1) {
                    return 1;
                } else if(board[0] == 2 && board[4] == 1 && board[8] == 1) {
                    return 2;
                } else if(board[0] != 0 && board[4] != 0 && board[5] != 0) {
                    return 7;
                } else if(board[0] != 0 && board[4] != 0 && board[7] != 0) {
                    return 5;
                } else if(board[1] != 0 && board[3] != 0 && board[4] != 0) {
                    return 0;
                } else if(board[1] != 0 && board[4] != 0 && board[7] != 0) {
                    return 0;
                }
            }
            
            //if move 5
            if(numMoves == 5) {
                if(board[0] != 0 && board[1] != 0 && board[2] != 0 && board[4] != 0 && board[7] != 0) {
                    return 3;
                } else if(board[0] != 0 && board[1] != 0 && board[3] != 0 && board[4] != 0 && board[8] != 0) {
                    return 2;
                }
            }
        }
        
        //if board doesn't match
        return -1;
    }
    
    private boolean secondMove(int[] board, int spot) {
        boolean temp = true;
        for(int i = 0; i < board.length; i++) {
            if(i == 0 && board[i] != 1) {
                temp = false;
            }
            if(i == spot && board[i] != 2) {
                temp = false;
            }
            if(i != 0 && i != spot && board[i] != 0) {
                temp = false;
            }
        }
        return temp;
    }
    
    //rotates the board 90 degrees clockwise
    private int[] rotateBoard(int[] board) {
        int[] newBoard = new int[9];
        newBoard[0] = board[6];
        newBoard[1] = board[3];
        newBoard[2] = board[0];
        newBoard[3] = board[7];
        newBoard[4] = board[4];
        newBoard[5] = board[1];
        newBoard[6] = board[8];
        newBoard[7] = board[5];
        newBoard[8] = board[2];
        return newBoard;
    }
    
    //rotates the spot 90 degrees clockwise
    private int rotateSpot(int spot) {
        switch(spot) {
            case(0):
                return 6;
            case(1):
                return 3;
            case(2):
                return 0;
            case(3):
                return 7;
            case(4):
                return 4;
            case(5):
                return 1;
            case(6):
                return 8;
            case(7):
                return 5;
            case(8):
                return 2;
        }
        return -1; //should never be called
    }
    
    /**
     * 
     * @param player 1;x, 2;0
     * @return an integer representing how they won, -1 if didn't win
     */
    private int isWinner(int player) {
        boolean won;
        for(int i = 0; i < waysToWin.length; i++) {
            won = true;
            for(int j = 0; j < waysToWin[i].length; j++) {
                if(moves[waysToWin[i][j]] != player) {
                    won = false;
                }
            }
            if(won) {
                winner = player;
                gameOver = true;
                return i;
            }
        }
        return -1;
    }
    
}
