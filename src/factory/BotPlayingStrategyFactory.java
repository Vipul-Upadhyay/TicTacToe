package factory;

import model.BotDifficultyLevel;
import strategy.botPlayingStrategy.BotPlayingStrategy;
import strategy.botPlayingStrategy.RandomBotPlayingStrategy;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getStrategyForDifficultyLevel(BotDifficultyLevel difficultyLevel){
        return new RandomBotPlayingStrategy();
    }
}
