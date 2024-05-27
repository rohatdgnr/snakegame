package snakegame;

import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        // GUI işlemlerini olay kuyruğunda yapmak için SwingUtilities.invokeLater kullanılır / SwingUtilities.invokeLater is used to ensure GUI operations are done in the event queue
        SwingUtilities.invokeLater(() -> {
            // JFrame oluşturulur / Create the JFrame
            JFrame frame = new JFrame("Snake Game");
            // GamePanel nesnesi oluşturulur / Create the GamePanel instance
            GamePanel panel = new GamePanel();
            // GamePanel JFrame'e eklenir / Add the GamePanel to the JFrame
            frame.add(panel);
            // Çıkışta programın tamamen kapanmasını sağlar / Ensure the program exits on close
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Pencerenin yeniden boyutlandırılmasını engeller / Prevent the window from being resizable
            frame.setResizable(false);
            // Pencereyi ekrana uygun boyutlandırır / Size the window appropriately
            frame.pack();
            // Pencereyi görünür yapar / Make the window visible
            frame.setVisible(true);
            // Oyunu başlatır / Start the game
            panel.startGame();
        });
    }
}
