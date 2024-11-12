import java.util.ArrayList;
import javax.swing.*;
import java.util.Random;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
@SuppressWarnings("unused")
public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    private int BoardHeight;
    private int BoardWidth;
    private int TileSize = 25;
    private Tile SnakeHead;
    private Tile Food;
    private int velocityX = 0;
    private int velocityY = 0;
    private ArrayList<Tile> snakeBody;
    private boolean GameOver = false;
    // game loop
    Timer timer;
    // random object to place food
    Random random;

    public SnakeGame(int BoardHeight, int BoardWidth) {
        this.BoardHeight = BoardHeight;
        this.BoardWidth = BoardWidth;
        setPreferredSize(new Dimension(this.BoardWidth, this.BoardHeight));
        setBackground(Color.black);
        setOpaque(true);
        // snake head
        SnakeHead = new Tile(5, 5);
        // snake body
        snakeBody = new ArrayList<>();
        // food
        Food = new Tile(10, 10);
        // game loop
        timer = new Timer(100, this);
        timer.start();
        random = new Random();
        placeFood();
        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        // grid
        // for (int i = 0; i < BoardWidth / TileSize; i++) {
        //     g.drawLine((i * TileSize), 0, i * TileSize, BoardHeight);
        // }
        // for (int i = 0; i < BoardHeight / TileSize; i++) {
        //     g.drawLine(0, i * TileSize, BoardWidth, i * TileSize);
        // }

        // food
        g.setColor(Color.RED);
        g.fill3DRect(Food.x * TileSize, Food.y * TileSize, TileSize, TileSize,true);
        // snakehead
        g.setColor(Color.green);
        g.fill3DRect(SnakeHead.x * TileSize, SnakeHead.y * TileSize, TileSize, TileSize,true);
        // snake body
        g.setColor(Color.green);
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakeTile = snakeBody.get(i);
            g.fill3DRect(snakeTile.x * TileSize, snakeTile.y * TileSize, TileSize, TileSize,true);
        }
        if(GameOver){
            g.setColor(Color.RED);
            g.setFont(new Font("Times new Roman", Font.BOLD, 30));
            g.drawString("Game Over: "+snakeBody.size(), 8*TileSize, BoardHeight/2);
        }else{
            g.setFont(new Font("AERIAL", Font.BOLD, 16));
            g.drawString("Score: "+String.valueOf(snakeBody.size()), 10, 20);
        }
    }

    public void placeFood() {
        Food.x = random.nextInt(0, BoardWidth / TileSize);
        Food.y = random.nextInt(0, BoardWidth / TileSize);
    }

    private void move() {
        if (coallision(SnakeHead, Food)) {
            snakeBody.add(new Tile(Food.x, Food.y));
            // System.out.println(snakeBody);
            placeFood();
            // snakebody
        }
        
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            if (i == 0) {
                Tile snakeTile = snakeBody.get(i);
                snakeTile.x = SnakeHead.x;
                snakeTile.y = SnakeHead.y;
            } else {
                Tile snakeTile = snakeBody.get(i);
                Tile prevsnakeTile = snakeBody.get(i - 1);
                snakeTile.x = prevsnakeTile.x;
                snakeTile.y = prevsnakeTile.y;
            }
        }
        // snake head
        SnakeHead.x += velocityX;
        SnakeHead.y += velocityY;

        if (SnakeHead.x*TileSize > BoardWidth || SnakeHead.x < 0 || SnakeHead.y*TileSize > BoardHeight || SnakeHead.y < 0) {
            GameOver = true;
            //timer.stop();
        }
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakeTile = snakeBody.get(i);
            if(coallision(snakeTile, SnakeHead)){
                GameOver = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(GameOver){
            timer.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityY = 0;
            velocityX = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityY = 0;
            velocityX = -1;
        } 
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public boolean coallision(Tile obj1, Tile obj2) {
        return obj1.x == obj2.x && obj1.y == obj2.y;
    }
}