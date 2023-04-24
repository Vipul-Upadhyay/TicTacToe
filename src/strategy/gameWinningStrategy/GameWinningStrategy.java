package strategy.gameWinningStrategy;

import model.Board;
import model.Cell;
import model.Player;

public interface GameWinningStrategy {

    boolean checkWinner(Board board, Player lastMovePlayer, Cell lastMoveCell);
}
