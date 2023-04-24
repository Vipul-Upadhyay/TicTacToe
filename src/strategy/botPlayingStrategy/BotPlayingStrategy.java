package strategy.botPlayingStrategy;

import model.Board;
import model.Move;
import model.Player;

public interface BotPlayingStrategy {

    Move decideMove(Player player, Board board);
}
