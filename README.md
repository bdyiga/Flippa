# Flippa
# Memory Game Design Document

## 1. Introduction

### 1.1 Purpose
The Memory Game is a Java-based desktop application designed to test and improve a player's memory skills. Players must match pairs of tiles by remembering their positions on a grid.

### 1.2 Scope
The game includes functionality for displaying a grid of tiles, allowing player interaction, tracking score and time, and providing basic game management features such as restarting.

## 2. System Architecture

The Memory Game is built using a simple object-oriented architecture with the following main components:

1. MemoryGame (main class)
2. Tile
3. TileListener
4. FlipBackListener

These components interact to create a functional memory game with a graphical user interface.

## 3. Component Design

### 3.1 MemoryGame

#### Description
The main class that extends JFrame and manages the overall game logic and UI.

#### Attributes
- tiles: ArrayList<Tile>
- selectedTile: Tile
- flipBackTimer: Timer
- gameTimer: Timer
- pairs: int
- scoreLabel: JLabel
- timeLabel: JLabel
- score: int
- timeElapsed: int

#### Methods
- Constructor: MemoryGame()
- startNewGame(): Initializes a new game
- restartGame(): Restarts the current game

### 3.2 Tile

#### Description
Represents a single tile in the game, extending JButton for clickable functionality.

#### Attributes
- id: int
- matched: boolean

#### Methods
- Constructor: Tile(int id)
- getId(): Returns the tile's ID
- isMatched(): Checks if the tile is matched
- setMatched(boolean): Sets the matched status
- reveal(): Shows the tile's value
- hide(): Hides the tile's value
- reset(): Resets the tile to its initial state

### 3.3 TileListener

#### Description
An ActionListener that handles tile click events.

#### Methods
- actionPerformed(ActionEvent): Processes tile clicks and game logic

### 3.4 FlipBackListener

#### Description
An ActionListener that handles flipping unmatched tiles back over.

#### Methods
- actionPerformed(ActionEvent): Flips unmatched tiles and updates score

## 4. User Interface

The game uses a graphical user interface with the following components:

1. Game Grid: A 4x4 grid of clickable tiles
2. Info Panel: Displays current score and elapsed time
3. Restart Button: Allows the player to restart the game at any time

## 5. Game Logic

1. Game Initialization:
   - 16 tiles are created (8 pairs)
   - Tiles are shuffled and placed on the grid

2. Gameplay:
   - Player clicks on tiles to reveal their values
   - Two tiles can be revealed at a time
   - If the tiles match, they remain face up
   - If the tiles don't match, they flip back over after a short delay

3. Scoring:
   - +10 points for each matched pair
   - -1 point for each mismatch

4. Game End:
   - Game ends when all pairs are matched
   - Final score and time are displayed

## 6. Data Flow

1. Player interacts with the UI by clicking tiles
2. TileListener processes click events and updates game state
3. FlipBackListener handles unmatched tiles
4. MemoryGame class updates UI elements (score, time) and checks for game end condition

## 7. Error Handling

The system includes basic error prevention:
- Ignores clicks on already matched tiles
- Prevents clicking during tile flip animations
- Disables matched tiles to prevent re-selection

## 8. Future Enhancements

Potential areas for future development include:
1. Difficulty levels (e.g., larger grids, shorter reveal times)
2. Custom themes or tile images
3. Sound effects and background music
4. Multiplayer mode
5. High score tracking and leaderboards
6. Animations for tile flips and matches

## 9. Conclusion

The Memory Game provides a solid foundation for a simple yet engaging memory-testing game. Its object-oriented design allows for easy expansion and enhancement of features in the future.