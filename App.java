import javax.swing.*;

public class App {
    public static void main(String[] args) {
        int BoardWidth = 600;
        int BoardHeight = 600;

        JFrame frame = new JFrame("Snake game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(BoardWidth, BoardHeight);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        SnakeGame snakeGame = new SnakeGame(BoardHeight, BoardWidth);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}
