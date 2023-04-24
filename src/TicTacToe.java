import controller.GameController;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {

        //it will take input dimension, players
        Scanner scanner = new Scanner(System.in);
        GameController gameController = new GameController();

        System.out.println("Enter the dimension of the game : ");
        int dimension = scanner.nextInt();

        System.out.println("Will there be any bot? y/n");
        String isBotString = scanner.next();

        List<Player> players = new ArrayList<>();

        int toIterate = dimension-1;

        if(isBotString.equals("y")){
            toIterate = dimension-2;
        }

        for(int i=0; i<toIterate; i++){
            System.out.println("Enter the name of the player " + i);
            String playerName = scanner.next();

            System.out.println("Enter the symbol of the player " + i);
            String playerSymbol = scanner.next();

            players.add(new Player(playerName, playerSymbol.charAt(0), PlayerType.HUMAN));
        }

        if(isBotString.equals("y")){
            System.out.println("Enter the name of the bot: ");
            String botName = scanner.next();

            System.out.println("Enter the character of the bot: ");
            String botSymbol = scanner.next();

            players.add(new Bot(botName, botSymbol.charAt(0), BotDifficultyLevel.EASY));
        }

        Game game =  gameController.createGame(dimension, players);

        while(gameController.getGameStatus(game).equals(GameStatus.IN_PROGRESS)){
            System.out.println("The current state of the board is ");

            gameController.displayBoard(game);

/*            System.out.println("Do you want to undo? y/n");

            String input = scanner.next();

            if(input.equals("y")){
                gameController.undo(game);
            }else{*/
                gameController.executeNextMove(game);
            /*}*/
        }

        System.out.println("Game has been ended. Result is: ");

        if(!game.getGameStatus().equals(GameStatus.DRAW)){
            System.out.println("Winner is: " + gameController.getWinner(game).getName());
            game.displayBoard();
        }else{
            System.out.println("---Draw---");
        }


    }
}