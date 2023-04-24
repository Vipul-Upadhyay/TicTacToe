package model;

import exception.InvalidGameConstructionParameterException;
import strategy.gameWinningStrategy.GameWinningStrategy;
import strategy.gameWinningStrategy.OrderOneGameWinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private Player winner;
    private GameStatus gameStatus;
    private int nextPlayerIndex;
    private GameWinningStrategy gameWinningStrategy;
    private Game(){

    }

    public static Builder getBuilder(){
        return new Builder();
    }

    public void undo(){
        //TODO
    }

    public void makeNextMove() {
        Player toMovePlayer = players.get(nextPlayerIndex);

        System.out.println("It is " + players.get(nextPlayerIndex).getName() + "'s turn.");
        Move move = null;
        boolean validMove = false;
        while (!validMove){
            try {
                move = toMovePlayer.decideMove(this.board);
                validMove = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Make move again");

            }
        }

        if (board.getBoard().get(move.getCell().getRow()).get(move.getCell().getCol()).getCellState().equals(CellState.FILLED)) {
            System.out.println("Cell is already filled. Make move again");
        } else if (board.getBoard().get(move.getCell().getRow()).get(move.getCell().getCol()).getCellState().equals(CellState.BLOCKED)) {
            System.out.println("Cell is Blocked. Make move again");
        } else {
            int row = move.getCell().getRow();
            int col = move.getCell().getCol();

            System.out.println("Move happened at: " + row + " , " + col + " . ");

            board.getBoard().get(row).get(col).setCellState(CellState.FILLED);

            board.getBoard().get(row).get(col).setPlayer(players.get(nextPlayerIndex));

            Move finalMove = new Move(
                    players.get(nextPlayerIndex),
                    board.getBoard().get(row).get(col)
            );

            this.moves.add(finalMove);

            if (gameWinningStrategy.checkWinner(
                    board, players.get(nextPlayerIndex), finalMove.getCell()
            )) {
                gameStatus = GameStatus.ENDED;
                winner = players.get(nextPlayerIndex);
            }

            nextPlayerIndex += 1;
            nextPlayerIndex %= players.size();
        }


    }

    public void displayBoard(){
        this.board.display();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public GameWinningStrategy getGameWinningStrategy() {
        return gameWinningStrategy;
    }

    public void setGameWinningStrategy(GameWinningStrategy gameWinningStrategy) {
        this.gameWinningStrategy = gameWinningStrategy;
    }

    public static class Builder{
            private int dimension;
            private List<Player> players;

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }
        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        private boolean valid() throws InvalidGameConstructionParameterException{
            if(this.dimension<3){
                throw new InvalidGameConstructionParameterException("Dimension of Game can't be <3");
            }

            if(this.players.size() != this.dimension-1){
                throw new InvalidGameConstructionParameterException("No. of Players must be Dimension-1");
            }

            //validate no 2 players with same char
            //validate bot

            return true;
        }

        public Game build() throws InvalidGameConstructionParameterException {
            try {
                valid();
            } catch (Exception e) {
                throw new InvalidGameConstructionParameterException(e.getMessage());
            }

            Game game = new Game();
            game.setGameStatus(GameStatus.IN_PROGRESS);
            game.setPlayers(players);
            game.setMoves(new ArrayList<>());
            game.setBoard(new Board(dimension));
            game.setNextPlayerIndex(0);
            game.setGameWinningStrategy(new OrderOneGameWinningStrategy(dimension));

            return game;
        }
    }



}
