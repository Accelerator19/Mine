package xyz.chengzi.halma.controller;

import xyz.chengzi.halma.listener.InputListener;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.model.ChessPiece;
import xyz.chengzi.halma.view.ChessBoardComponent;
import xyz.chengzi.halma.view.ChessComponent;
import xyz.chengzi.halma.view.SquareComponent;

import javax.swing.*;
import java.awt.*;

public class GameController implements InputListener {
    private ChessBoardComponent view;
    private ChessBoard model;
    private ChessBoardLocation selectedLocation;

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    private Color currentPlayer;

    public void setTemp(JLabel temp) {
        this.temp = temp;
    }

    private JLabel temp;

    public String convertColor(Color t) {
        if (t==Color.BLACK)
            return "BLACK";
        if (t==Color.WHITE)
            return "WHITE";
        if (t==Color.GREEN)
            return "GREEN";
        if (t==Color.RED)
            return "RED";
        return "";
    }
    private int index = 0;
    private Color[] players2 = {Color.BLACK, Color.WHITE};
    private Color[] players4 = {Color.BLACK, Color.RED, Color.WHITE, Color.GREEN};

    public GameController(ChessBoardComponent chessBoardComponent, ChessBoard chessBoard) {
        this.view = chessBoardComponent;
        this.model = chessBoard;
        this.currentPlayer = Color.BLACK;
        view.registerListener(this);
        model.registerListener(view);
        model.placeInitialPieces();
    }


    public Color loadController(String gameplayer) {
        if (gameplayer.equals("BLACK"))
            return Color.BLACK;
        if (gameplayer.equals("WHITE"))
            return Color.WHITE;
        if (gameplayer.equals("GREEN"))
            return Color.GREEN;
        if (gameplayer.equals("RED"))
            return Color.RED;
        return null;
    }

    public GameController(ChessBoardComponent chessBoardComponent, ChessBoard chessBoard, char[][] grid, String gameplayer, int people) {
        this.view = chessBoardComponent;
        this.model = chessBoard;

        this.currentPlayer = loadController(gameplayer);
        for (int i = 0; i < players4.length; i++)
            if (convertColor(players4[i]).equals(gameplayer))
                index = i;
        view.registerListener(this);
        model.registerListener(view);
        model.resetGrid(grid);
    }

    public ChessBoardLocation getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(ChessBoardLocation location) {
        this.selectedLocation = location;
    }

    public void resetSelectedLocation() {
        setSelectedLocation(null);
    }

    public boolean hasSelectedLocation() {
        return selectedLocation != null;
    }

    public Color nextPlayer() {
        if (model.getPeople()==2) {
            index += 1;
            index = index % players2.length;
            return currentPlayer = players2[index];
        }
        else {
            index += 1;
            index = index % players4.length;
            return currentPlayer = players4[index];
        }
    }

    @Override
    public void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component) {
        if (hasSelectedLocation() && model.isValidMove(getSelectedLocation(), location)) {
            model.moveChessPiece(selectedLocation, location);
            resetSelectedLocation();
            nextPlayer();
            this.temp.setText(convertColor(currentPlayer));
            model.win();
        }
    }

    @Override
    public void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component) {
        ChessPiece piece = model.getChessPieceAt(location);
        if (piece.getColor() == currentPlayer && (!hasSelectedLocation() || location.equals(getSelectedLocation()))) {
            if (!hasSelectedLocation()) {
                setSelectedLocation(location);
            } else {
                resetSelectedLocation();
            }
            component.setSelected(!component.isSelected());
            component.repaint();
        }
    }

}