import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * MemoryGame class represents the main game window and logic.
 * It extends JFrame to create the game window.
 */
public class MemoryGame extends JFrame {
    // Game components
    private ArrayList<Tile> tiles;        // List to hold all the game tiles
    private Tile selectedTile;            // Currently selected tile
    private Timer flipBackTimer;          // Timer for flipping unmatched tiles back
    private Timer gameTimer;              // Timer for tracking game duration
    private int pairs;                    // Number of pairs matched
    private JLabel scoreLabel;            // Label to display the score
    private JLabel timeLabel;             // Label to display the time elapsed
    private int score;                    // Current game score
    private int timeElapsed;              // Time elapsed in seconds

    /**
     * Constructor for the MemoryGame class.
     * Sets up the game window and initializes the game components.
     */
    public MemoryGame() {
        // Set up the main window
        setTitle("Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 550);
        setLayout(new BorderLayout());

        // Create the game panel with a 4x4 grid
        JPanel gamePanel = new JPanel(new GridLayout(4, 4, 10, 10));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tiles = new ArrayList<>();

        // Create and add tiles to the game
        for (int i = 1; i <= 8; i++) {
            tiles.add(new Tile(i));
            tiles.add(new Tile(i));  // Add each number twice for pairs
        }
        Collections.shuffle(tiles);  // Randomize tile positions

        // Add tiles to the game panel
        for (Tile tile : tiles) {
            gamePanel.add(tile);
            tile.addActionListener(new TileListener());
        }

        add(gamePanel, BorderLayout.CENTER);

        // Create info panel for score and time
        JPanel infoPanel = new JPanel(new FlowLayout());
        scoreLabel = new JLabel("Score: 0");
        timeLabel = new JLabel("Time: 0s");
        infoPanel.add(scoreLabel);
        infoPanel.add(timeLabel);
        add(infoPanel, BorderLayout.NORTH);

        // Add restart button
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> restartGame());
        add(restartButton, BorderLayout.SOUTH);

        // Initialize timers
        flipBackTimer = new Timer(750, new FlipBackListener());
        flipBackTimer.setRepeats(false);  // Ensure timer only triggers once

        gameTimer = new Timer(1000, e -> {
            timeElapsed++;
            timeLabel.setText("Time: " + timeElapsed + "s");
        });

        startNewGame();  // Initialize the game state
    }

    /**
     * Starts a new game by resetting all game components.
     */
    private void startNewGame() {
        pairs = 0;
        score = 0;
        timeElapsed = 0;
        scoreLabel.setText("Score: 0");
        timeLabel.setText("Time: 0s");
        for (Tile tile : tiles) {
            tile.reset();
        }
        Collections.shuffle(tiles);  // Randomize tile positions again
        gameTimer.start();  // Start the game timer
    }

    /**
     * Restarts the game when the restart button is clicked.
     */
    private void restartGame() {
        gameTimer.stop();  // Stop the current game timer
        startNewGame();    // Start a new game
    }

    /**
     * TileListener class handles the events when a tile is clicked.
     */
    private class TileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tile tile = (Tile) e.getSource();

            // Ignore clicks on matched tiles, already selected tile, or during flip back animation
            if (tile.isMatched() || tile == selectedTile || flipBackTimer.isRunning())
                return;

            tile.reveal();  // Show the tile's value

            if (selectedTile == null) {
                selectedTile = tile;  // This is the first tile selected
            } else {
                // This is the second tile selected
                if (selectedTile.getId() == tile.getId()) {
                    // Tiles match
                    selectedTile.setMatched(true);
                    tile.setMatched(true);
                    selectedTile = null;
                    pairs++;
                    score += 10;  // Increase score for a match
                    scoreLabel.setText("Score: " + score);
                    if (pairs == 8) {
                        // All pairs found, game over
                        gameTimer.stop();
                        JOptionPane.showMessageDialog(null, "Congratulations! You won!\nYour score: " + score + "\nTime: " + timeElapsed + "s");
                    }
                } else {
                    // Tiles don't match, start timer to flip them back
                    flipBackTimer.start();
                }
            }
        }
    }

    /**
     * FlipBackListener class handles flipping unmatched tiles back over.
     */
    private class FlipBackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            selectedTile.hide();
            tiles.get(tiles.indexOf(selectedTile)).hide();
            selectedTile = null;
            score -= 1;  // Decrease score for a mismatch
            scoreLabel.setText("Score: " + score);
        }
    }

    /**
     * Tile class represents individual tiles in the game.
     * It extends JButton to create clickable tiles.
     */
    private class Tile extends JButton {
        private int id;         // The value of the tile
        private boolean matched;  // Whether this tile has been matched

        /**
         * Constructor for the Tile class.
         * @param id The value of the tile
         */
        public Tile(int id) {
            this.id = id;
            this.matched = false;
            setFont(new Font("Arial", Font.PLAIN, 24));
            setText("?");
        }

        // Getter methods
        public int getId() {
            return id;
        }

        public boolean isMatched() {
            return matched;
        }

        /**
         * Sets the matched status of the tile and updates its appearance.
         * @param matched Whether the tile is matched
         */
        public void setMatched(boolean matched) {
            this.matched = matched;
            setEnabled(!matched);  // Disable the button if it's matched
        }

        /**
         * Reveals the tile's value.
         */
        public void reveal() {
            setText(String.valueOf(id));
        }

        /**
         * Hides the tile's value.
         */
        public void hide() {
            setText("?");
        }

        /**
         * Resets the tile to its initial state.
         */
        public void reset() {
            matched = false;
            setText("?");
            setEnabled(true);
        }
    }

    /**
     * Main method to start the game.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MemoryGame game = new MemoryGame();
            game.setVisible(true);
        });
    }
}