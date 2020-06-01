package xyz.chengzi.halma.view;

import xyz.chengzi.halma.controller.GameController;
import xyz.chengzi.halma.model.ChessBoard;



public class InitChessBord {
    GameFrame gameFrame;
    public void init(int people) {

        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
        ChessBoard chessBoard = new ChessBoard(16, people, this);
        GameController controller = new GameController(chessBoardComponent, chessBoard);
        GameFrame mainFrame = new GameFrame(this,controller,chessBoard);
        if (gameFrame==null) {
            gameFrame = mainFrame;
        }
        else {
            gameFrame.setVisible(false);
            gameFrame = mainFrame;
        }
        mainFrame.add(chessBoardComponent);
        mainFrame.setVisible(true);
    }

    public void load(int people, char[][] grid, String gameplayer) {
        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 16);
        ChessBoard chessBoard = new ChessBoard(16, people, this);
        GameController controller = new GameController(chessBoardComponent, chessBoard, grid, gameplayer, people);
        GameFrame mainFrame = new GameFrame(this,controller,chessBoard);
        chessBoard.win();
        if (gameFrame==null) {
            gameFrame = mainFrame;
        }
        else {
            gameFrame.setVisible(false);
            gameFrame = mainFrame;
        }
        mainFrame.add(chessBoardComponent);
        mainFrame.setVisible(true);
    }

}
