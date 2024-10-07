import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGame extends JFrame {
    private ArrayList<Tile> tiles;
    private Tile selectedTile;
    private Timer timer;
    private int pairs;
    private JLabel scoreLabel;
    private int score;

    public MemoryGame() {
        setTitle("Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel(new GridLayout(4, 4, 10, 10));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tiles = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {
            tiles.add(new Tile(i));
            tiles.add(new Tile(i));
        }
        Collections.shuffle(tiles);

        for (Tile tile : tiles) {
            gamePanel.add(tile);
            tile.addActionListener(new TileListener());
        }

        add(gamePanel, BorderLayout.CENTER);

        scoreLabel = new JLabel("Score: 0");
        add(scoreLabel, BorderLayout.NORTH);

        timer = new Timer(750, new TimerListener());
        pairs = 0;
        score = 0;
    }

    private class TileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tile tile = (Tile) e.getSource();

            if (tile.isMatched() || tile == selectedTile || timer.isRunning())
                return;

            tile.reveal();

            if (selectedTile == null) {
                selectedTile = tile;
            } else {
                if (selectedTile.getId() == tile.getId()) {
                    selectedTile.setMatched(true);
                    tile.setMatched(true);
                    selectedTile = null;
                    pairs++;
                    score += 10;
                    scoreLabel.setText("Score: " + score);
                    if (pairs == 8) {
                        JOptionPane.showMessageDialog(null, "Congratulations! You won!\nYour score: " + score);
                    }
                } else {
                    timer.start();
                }
            }
        }
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            selectedTile.hide();
            ((Tile) tiles.get(tiles.indexOf(selectedTile))).hide();
            selectedTile = null;
            timer.stop();
            score -= 2;
            scoreLabel.setText("Score: " + score);
        }
    }

    private class Tile extends JButton {
        private int id;
        private boolean matched;

        public Tile(int id) {
            this.id = id;
            this.matched = false;
            setFont(new Font("Arial", Font.PLAIN, 24));
            setText("?");
        }

        public int getId() {
            return id;
        }

        public boolean isMatched() {
            return matched;
        }

        public void setMatched(boolean matched) {
            this.matched = matched;
        }

        public void reveal() {
            setText(String.valueOf(id));
        }

        public void hide() {
            setText("?");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MemoryGame game = new MemoryGame();
            game.setVisible(true);
        });
    }
}