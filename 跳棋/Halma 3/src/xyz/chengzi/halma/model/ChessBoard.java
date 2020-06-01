package xyz.chengzi.halma.model;

import xyz.chengzi.halma.listener.GameListener;
import xyz.chengzi.halma.listener.Listenable;
import xyz.chengzi.halma.view.InitChessBord;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChessBoard implements Listenable<GameListener> {
    private List<GameListener> listenerList = new ArrayList<>();

    private Square[][] grid;
    private int dimension;
    private static JLabel statusLabel = null;
    private InitChessBord initChessBord;

    public int getPeople() {
        return people;
    }


    private int people;

    public ChessBoard(int dimension, int people, InitChessBord initChessBord) {
        this.grid = new Square[dimension][dimension];
        this.dimension = dimension;
        this.people = people;
        this.initChessBord = initChessBord;
        initGrid();
    }

    public void initGrid() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                grid[i][j] = new Square(new ChessBoardLocation(i, j));
            }
        }
    }

    public void resetGrid(char[][] gridset) {
        initGrid();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (gridset[i][j]=='B')
                    grid[i][j].setPiece(new ChessPiece(Color.BLACK));
                if (gridset[i][j]=='R')
                    grid[i][j].setPiece(new ChessPiece(Color.RED));
                if (gridset[i][j]=='W')
                    grid[i][j].setPiece(new ChessPiece(Color.WHITE));
                if (gridset[i][j]=='G')
                    grid[i][j].setPiece(new ChessPiece(Color.GREEN));
            }
        }
        listenerList.forEach(listener -> listener.onChessBoardReload(this));
    }


    public void placeInitialPieces() {
        // TODO: This is only a demo implementation
        if (people==2) {
            initGrid();
            for (int i = 0; i < 5; i++) {
                grid[0][i].setPiece(new ChessPiece(Color.BLACK));
                grid[i][0].setPiece(new ChessPiece(Color.BLACK));
                grid[dimension - 1][dimension - 1 - i].setPiece(new ChessPiece(Color.WHITE));
                grid[dimension - i - 1][dimension - 1].setPiece(new ChessPiece(Color.WHITE));
            }
            for (int i = 1; i < 5; i++) {
                for (int j = 1; j < 6 - i; j++) {
                    grid[i][j].setPiece(new ChessPiece(Color.BLACK));
                    grid[dimension - i - 1][dimension - j - 1].setPiece(new ChessPiece(Color.WHITE));
                }
            }
        }
        if (people==4) {
            initGrid();
            for (int i = 0; i < 4; i++) {
                grid[0][i].setPiece(new ChessPiece(Color.BLACK));
                grid[i][0].setPiece(new ChessPiece(Color.BLACK));
                grid[0][dimension - 1 - i].setPiece(new ChessPiece(Color.RED));
                grid[i][dimension - 1].setPiece(new ChessPiece(Color.RED));
                grid[dimension - 1][i].setPiece(new ChessPiece(Color.GREEN));
                grid[dimension - 1 - i][0].setPiece(new ChessPiece(Color.GREEN));
                grid[dimension - 1][dimension - 1 - i].setPiece(new ChessPiece(Color.WHITE));
                grid[dimension - i - 1][dimension - 1].setPiece(new ChessPiece(Color.WHITE));
            }
            for (int i = 1; i < 4; i++) {
                for (int j = 1; j < 5 - i; j++) {
                    grid[i][j].setPiece(new ChessPiece(Color.BLACK));
                    grid[i][dimension - j - 1].setPiece(new ChessPiece(Color.RED));
                    grid[dimension - i - 1][j].setPiece(new ChessPiece(Color.GREEN));
                    grid[dimension - i - 1][dimension - j - 1].setPiece(new ChessPiece(Color.WHITE));
                }
            }
        }
        listenerList.forEach(listener -> listener.onChessBoardReload(this));
    }

    public void win() {
        int flag1 = 0,flag2 = 0,flag3 = 0,flag4 = 0;
        if (people==2) {
            for (int i = 0; i < 5; i++) {
                if (grid[0][i].getPiece()!=null && grid[i][0].getPiece()!=null)
                    if (grid[0][i].getPiece().getColor()==Color.WHITE && grid[i][0].getPiece().getColor()==Color.WHITE)
                        flag1 += 2;
                if (grid[dimension - 1][dimension - 1 - i].getPiece()!=null && grid[dimension - i - 1][dimension - 1].getPiece()!=null)
                    if (grid[dimension - 1][dimension - 1 - i].getPiece().getColor()==Color.BLACK && grid[dimension - i - 1][dimension - 1].getPiece().getColor()==Color.BLACK)
                        flag2 += 2;
            }
            for (int i = 1; i < 5; i++) {
                for (int j = 1; j < 6 - i; j++) {
                    if (grid[i][j].getPiece()!=null)
                        if (grid[i][j].getPiece().getColor()==Color.WHITE)
                            flag1++;
                    if (grid[dimension - i - 1][dimension - j - 1].getPiece()!=null)
                        if (grid[dimension - i - 1][dimension - j - 1].getPiece().getColor()==Color.BLACK)
                            flag2++;
                }
            }
            int reback = -1;
            if (flag1==20)
                reback = JOptionPane.showConfirmDialog(null,"白棋胜利，重新开始游戏","胜利！",JOptionPane.YES_NO_OPTION);
            if (flag2==20)
                reback = JOptionPane.showConfirmDialog(null,"黑棋胜利，重新开始游戏","胜利！",JOptionPane.YES_NO_OPTION);
            if (reback==JOptionPane.YES_OPTION)
                initChessBord.init(2);
        }
        if (people==4) {
            for (int i = 0; i < 4; i++) {
                if (grid[0][i].getPiece()!=null && grid[i][0].getPiece()!=null)
                    if (grid[0][i].getPiece().getColor()==Color.WHITE && grid[i][0].getPiece().getColor()==Color.WHITE)
                        flag1 += 2;
                if (grid[dimension - 1][dimension - 1 - i].getPiece()!=null && grid[dimension - i - 1][dimension - 1].getPiece()!=null)
                    if (grid[dimension - 1][dimension - 1 - i].getPiece().getColor()==Color.BLACK && grid[dimension - i - 1][dimension - 1].getPiece().getColor()==Color.BLACK)
                        flag2 += 2;
                if (grid[i][dimension - 1].getPiece()!=null && grid[0][dimension - 1 - i].getPiece()!=null)
                    if (grid[i][dimension - 1].getPiece().getColor()==Color.GREEN && grid[0][dimension - 1 - i].getPiece().getColor()==Color.GREEN)
                        flag3 += 2;
                if (grid[dimension - 1][i].getPiece()!=null && grid[dimension - 1 - i][0].getPiece()!=null)
                    if (grid[dimension - 1][i].getPiece().getColor()==Color.RED && grid[dimension - 1 - i][0].getPiece().getColor()==Color.RED)
                        flag4 += 2;
            }
            for (int i = 1; i < 4; i++) {
                for (int j = 1; j < 5 - i; j++) {
                    if (grid[i][j].getPiece()!=null)
                        if (grid[i][j].getPiece().getColor()==Color.WHITE)
                            flag1++;
                    if (grid[dimension - i - 1][dimension - j - 1].getPiece()!=null)
                        if (grid[dimension - i - 1][dimension - j - 1].getPiece().getColor()==Color.BLACK)
                            flag2++;
                    if (grid[i][dimension - j - 1].getPiece()!=null)
                        if (grid[i][dimension - j - 1].getPiece().getColor()==Color.GREEN)
                            flag3++;
                    if (grid[dimension - i - 1][j].getPiece()!=null)
                        if (grid[dimension - i - 1][j].getPiece().getColor()==Color.RED)
                            flag4++;
                }
            }
            int reback = -1;
            if (flag1==14)
                reback = JOptionPane.showConfirmDialog(null,"白棋胜利，重新开始游戏","胜利！",JOptionPane.YES_NO_OPTION);
            if (flag2==14)
                reback = JOptionPane.showConfirmDialog(null,"黑棋胜利，重新开始游戏","胜利！",JOptionPane.YES_NO_OPTION);
            if (flag3==14)
                reback = JOptionPane.showConfirmDialog(null,"绿棋胜利，重新开始游戏","胜利！",JOptionPane.YES_NO_OPTION);
            if (flag4==14)
                reback = JOptionPane.showConfirmDialog(null,"红棋胜利，重新开始游戏","胜利！",JOptionPane.YES_NO_OPTION);
            if (reback==JOptionPane.YES_OPTION)
                initChessBord.init(4);
        }

    }

    public Square getGridAt(ChessBoardLocation location) {
        return grid[location.getRow()][location.getColumn()];
    }

    public ChessPiece getChessPieceAt(ChessBoardLocation location) {
        return getGridAt(location).getPiece();
    }

    public void setChessPieceAt(ChessBoardLocation location, ChessPiece piece) {
        getGridAt(location).setPiece(piece);
        listenerList.forEach(listener -> listener.onChessPiecePlace(location, piece));
    }

    public ChessPiece removeChessPieceAt(ChessBoardLocation location) {
        ChessPiece piece = getGridAt(location).getPiece();
        getGridAt(location).setPiece(null);
        listenerList.forEach(listener -> listener.onChessPieceRemove(location));
        return piece;
    }

    public void moveChessPiece(ChessBoardLocation src, ChessBoardLocation dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal halma move");
        }
        setChessPieceAt(dest, removeChessPieceAt(src));
    }

    public int getDimension() {
        return dimension;
    }

    public ArrayList<ChessBoardLocation> Bfs(int startRow,int startCol) {
        ArrayList<ChessBoardLocation> pos = new ArrayList<>();
        ChessBoardLocation cur = new ChessBoardLocation(startRow,startCol);
        cur.setRe(1);
        pos.add(cur);
        int head = 0, tail = 1, curx, cury, tx, ty, nx, ny;
        int[][] next = {{1,0},{0,1},{-1,0},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
        int[][] book = new int[dimension][dimension];
        for (int i = 0; i < 8; i++) {
            ChessBoardLocation t = pos.get(head);
            curx = t.getRow();
            cury = t.getColumn();
            //周边格子坐标
            tx = curx + next[i][0];
            ty = cury + next[i][1];

            ChessBoardLocation tmp = new ChessBoardLocation(tx,ty);

            //判断周边格子是否在棋盘内
            if (tx < 0 || ty < 0 || tx >= dimension || ty >= dimension)
                continue;

            //周边空白格子加入队列，不可继续延伸，设置Re为0
            if (getChessPieceAt(tmp)==null && book[tx][ty]==0) {
                tmp.setRe(0);
                pos.add(tmp);
                book[tx][ty] = 1;
                tail++;
            }
        }
        while(head != tail) {
            ChessBoardLocation t = pos.get(head);
            if (t.getRe()==0) {
                head++;
                continue;
            }
            curx = t.getRow();
            cury = t.getColumn();
            book[curx][cury] = 1;

            for (int i = 0; i < 8; i++) {
                //周边格子坐标
                tx = curx + next[i][0];
                ty = cury + next[i][1];
                //跳跃格子坐标
                nx = tx + next[i][0];
                ny = ty + next[i][1];
                ChessBoardLocation tmp = new ChessBoardLocation(tx,ty);
                ChessBoardLocation jump = new ChessBoardLocation(nx,ny);

                //判断周边格子是否在棋盘内
                if (tx < 0 || ty < 0 || tx >= dimension || ty >= dimension)
                    continue;

                //判断跳跃格子是否在棋盘内
                if (nx < 0 || ny < 0 || nx >= dimension || ny >= dimension)
                    continue;

                //周边可跳跃格子加入对列，可继续延伸，设置Re为1
                if (getChessPieceAt(tmp)!=null && getChessPieceAt(jump)==null && book[nx][ny]==0) {
                    jump.setRe(1);
                    pos.add(jump);
                    book[nx][ny] = 1;
                    tail++;
                }
            }
            head++;
        }
        return pos;
    }
    
    public boolean isValidMove(ChessBoardLocation src, ChessBoardLocation dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        int srcRow = src.getRow(), srcCol = src.getColumn(), destRow = dest.getRow(), destCol = dest.getColumn();
        ArrayList<ChessBoardLocation> p = Bfs(srcRow,srcCol);
        for (int i = 0; i < p.size(); i++) {
            if (destRow==p.get(i).getRow() && destCol==p.get(i).getColumn()) {
                return true;
            }
        }
        return false;
    }

    public String[][] SquareConvert() {
        String[][] gridString = new String[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (grid[i][j].getPiece()==null)
                    gridString[i][j] = "n";
                else {
                    if (grid[i][j].getPiece().getColor()==Color.BLACK)
                        gridString[i][j] = "B";
                    if (grid[i][j].getPiece().getColor()==Color.WHITE)
                        gridString[i][j] = "W";
                    if (grid[i][j].getPiece().getColor()==Color.RED)
                        gridString[i][j] = "R";
                    if (grid[i][j].getPiece().getColor()==Color.GREEN)
                        gridString[i][j] = "G";
                }
            }
        }
        return gridString;
    }

    @Override
    public void registerListener(GameListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(GameListener listener) {
        listenerList.remove(listener);
    }
}
