package snakegame;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel implements ActionListener {
    // Oyun ekranının genişliği / Screen width
    private static final int SCREEN_WIDTH = 700;
    // Oyun ekranının yüksekliği / Screen height
    private static final int SCREEN_HEIGHT = 700;
    // Her bir birimin boyutu (yılan ve yemler için) / Size of each unit (for snake and apple)
    private static final int UNIT_SIZE = 15;
    // Oyun alanındaki toplam birim sayısı / Total units in game area
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    // Oyun hızını belirleyen gecikme süresi (ms) / Delay in milliseconds for game speed
    private static final int DELAY = 75;

    // Yılanın x koordinatları / Snake's x coordinates
    private final int x[] = new int[GAME_UNITS];
    // Yılanın y koordinatları / Snake's y coordinates
    private final int y[] = new int[GAME_UNITS];
    // Yılanın başlangıçtaki gövde parça sayısı / Initial body parts of the snake
    private int bodyParts = 6;
    // Yenen elma sayısı / Number of apples eaten
    private int applesEaten;
    // Elmanın x koordinatı / Apple's x coordinate
    private int appleX;
    // Elmanın y koordinatı / Apple's y coordinate
    private int appleY;
    // Yılanın hareket yönü / Direction of the snake
    private char direction = 'R';
    // Oyun devam ediyor mu / Is the game running
    private boolean running = false;
    // Oyun zamanlayıcısı / Game timer
    private Timer timer;
    // Arka plan resmi / Background image
    private BufferedImage backgroundImage;
    // Önceki skor / Previous score
    private int previousScore = 0;
    // Yeniden başlat düğmesi / Restart button
    private JButton restartButton;

    // Oyun alanının başlangıç x koordinatı / Game area start x coordinate
    private static final int GAME_AREA_X = 0;
    // Oyun alanının başlangıç y koordinatı / Game area start y coordinate
    private static final int GAME_AREA_Y = 0;
    // Oyun alanının genişliği / Game area width
    private static final int GAME_AREA_WIDTH = SCREEN_WIDTH;
    // Oyun alanının yüksekliği / Game area height
    private static final int GAME_AREA_HEIGHT = SCREEN_HEIGHT;

    // Oyun panelini başlatır / Initializes the game panel
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        loadImage();
        initRestartButton();
    }

    // Arka plan resmini yükler / Loads the background image
    private void loadImage() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/snakegame/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Resmi yeniden boyutlandırır / Resizes the image
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        if (originalImage == null) {
            return null;
        }
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        double aspectRatio = (double) originalWidth / originalHeight;

        int newWidth;
        int newHeight;

        if (targetWidth / aspectRatio <= targetHeight) {
            newWidth = targetWidth;
            newHeight = (int) (targetWidth / aspectRatio);
        } else {
            newHeight = targetHeight;
            newWidth = (int) (targetHeight * aspectRatio);
        }

        Image resultingImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return outputImage;
    }

    // Oyunu başlatır ve yılanı oyun alanına yerleştirir / Initializes the game and positions the snake in the game area
    private void initGame() {
        bodyParts = 6;
        applesEaten = 0;
        direction = 'R';
        running = true;
        for (int i = 0; i < bodyParts; i++) {
            x[i] = GAME_AREA_X + (bodyParts - i) * UNIT_SIZE;
            y[i] = GAME_AREA_Y;
        }
        newApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    // Yeniden başlat düğmesini başlatır / Initializes the restart button
    private void initRestartButton() {
        restartButton = new JButton("Restart");
        restartButton.setBounds(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT / 2 + 50, 100, 50);
        restartButton.setFocusable(false);
        restartButton.addActionListener(e -> restartGame());
        restartButton.setVisible(false);
        this.add(restartButton);
    }

    // Oyunu başlatır / Starts the game
    public void startGame() {
        initGame();
        repaint();
    }

    // Oyunu yeniden başlatır / Restarts the game
    public void restartGame() {
        previousScore = applesEaten;
        running = true;
        restartButton.setVisible(false);
        initGame();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Oyun ekranını çizer / Draws the game screen
    public void draw(Graphics g) {
        if (running) {
            BufferedImage resizedImage = resizeImage(backgroundImage, SCREEN_WIDTH, SCREEN_HEIGHT);
            if (resizedImage != null) {
                g.drawImage(resizedImage, 0, 0, null);
            }
            g.setColor(Color.ORANGE);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    // Yeni bir elma oluşturur / Creates a new apple
    public void newApple() {
        boolean validPosition;
        do {
            validPosition = true;
            appleX = GAME_AREA_X + (int) (Math.random() * ((GAME_AREA_WIDTH - UNIT_SIZE) / UNIT_SIZE)) * UNIT_SIZE;
            appleY = GAME_AREA_Y + (int) (Math.random() * ((GAME_AREA_HEIGHT - UNIT_SIZE) / UNIT_SIZE)) * UNIT_SIZE;

            // Yılanın gövdesi ile çakışmadığını kontrol eder / Check if the apple's position overlaps with the snake's body
            for (int i = 0; i < bodyParts; i++) {
                if (x[i] == appleX && y[i] == appleY) {
                    validPosition = false;
                    break;
                }
            }
        } while (!validPosition);
    }

    // Yılanı hareket ettirir / Moves the snake
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    // Elmanın yendiğini kontrol eder / Checks if the apple is eaten
    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    // Çarpışmaları kontrol eder / Checks for collisions
    public void checkCollisions() {
        // Yılanın sınırların dışına çıkıp çıkmadığını kontrol eder / Check if the snake hits the borders
        if (x[0] < GAME_AREA_X || x[0] >= GAME_AREA_X + GAME_AREA_WIDTH || y[0] < GAME_AREA_Y || y[0] >= GAME_AREA_Y + GAME_AREA_HEIGHT) {
            running = false;
        }

        // Yılanın kendine çarpıp çarpmadığını kontrol eder / Check if the snake hits itself
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        if (!running) {
            timer.stop();
        }
    }

    // Oyun bittiğinde ekranı çizer / Draws the game over screen
    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        g.drawString("Previous Score: " + previousScore, (SCREEN_WIDTH - metrics.stringWidth("Previous Score: " + previousScore)) / 2, SCREEN_HEIGHT / 2 + 50);

        restartButton.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    // Klavye girişlerini dinler / Listens for keyboard inputs
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}