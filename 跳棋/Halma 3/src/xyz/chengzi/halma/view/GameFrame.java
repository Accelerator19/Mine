package xyz.chengzi.halma.view;

import xyz.chengzi.halma.controller.GameController;
import xyz.chengzi.halma.model.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GameFrame extends JFrame {

    public GameFrame(InitChessBord initChessBord, GameController gameController, ChessBoard chessBoard) {
        setTitle("2020 CS102A Project Demo");
        setSize(770, 850);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel statusLabel = new JLabel(gameController.convertColor(gameController.getCurrentPlayer()));
        statusLabel.setLocation(0, 760);
        statusLabel.setSize(200, 10);
        add(statusLabel);
        gameController.setTemp(statusLabel);

        JMenuBar menuBar = new JMenuBar();
        JMenu start=new JMenu("游戏");
        JMenu file = new JMenu("文件");

        start.setFont(new Font("微软雅黑",Font.ROMAN_BASELINE,15));
        file.setFont(new Font("微软雅黑",Font.ROMAN_BASELINE,15));


        JMenuItem two = new JMenuItem("双人");
        JMenuItem four = new JMenuItem("四人");
        JMenuItem save=new JMenuItem("保存");
        JMenuItem load=new JMenuItem("读取");

        two.setName("two");
        four.setName("four");

        two.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initChessBord.init(2);
            }
        });

        four.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initChessBord.init(4);
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gameplayer = gameController.convertColor(gameController.getCurrentPlayer());
                String[][] grid = chessBoard.SquareConvert();
                int people = chessBoard.getPeople();
                int dimension = chessBoard.getDimension();
                if (people==2) {
                    File f = new File("twoplayer.txt");
                    try {
                        f.createNewFile();
                        BufferedWriter writer = new BufferedWriter(new FileWriter(f.getName()));
                        String num = "2";
                        writer.write(num);
                        writer.newLine();
                        writer.write(gameplayer);
                        writer.newLine();
                        for (int i = 0; i < dimension; i++) {
                            for (int j = 0; j < dimension; j++)
                                writer.write(grid[i][j]);
                            writer.newLine();
                        }
                        writer.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                if (people==4) {
                    File f = new File("fourplayer.txt");
                    try {
                        f.createNewFile();
                        BufferedWriter writer = new BufferedWriter(new FileWriter(f.getName()));
                        String num = "4";
                        writer.write(num);
                        writer.newLine();
                        writer.write(gameplayer);
                        writer.newLine();
                        for (int i = 0; i < dimension; i++) {
                            for (int j = 0; j < dimension; j++)
                                writer.write(grid[i][j]);
                            writer.newLine();
                        }
                        writer.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File directory = new File("");
                JFileChooser jFileChooser= null;
                try {
                    jFileChooser = new JFileChooser(directory.getCanonicalPath());
                    javax.swing.filechooser.FileFilter filter = new javax.swing.filechooser.FileFilter() {
                        @Override
                        public boolean accept(File pathname) {
                            return (pathname.isFile() && pathname.getName().endsWith(".txt"));
                        }

                        @Override
                        public String getDescription() {
                            return "文本文件";
                        }
                    };
                    jFileChooser.setFileFilter(filter);
                    int option = jFileChooser.showOpenDialog(null);
                    File f = jFileChooser.getSelectedFile();
                    if (option==JFileChooser.CANCEL_OPTION)
                        return;
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(f.getName()));
                        try {
                            String num = reader.readLine();
                            String gameplayer = reader.readLine();
                            String line;
                            int dimension = chessBoard.getDimension();
                            char[][] grid = new char[dimension][dimension];
                            int k = 0;
                            if (num.equals("2")) {
                                line = reader.readLine();
                                while (line!=null) {
                                    if (line.length()!=dimension) {
                                        JOptionPane.showMessageDialog(null,"棋盘规格错误");
                                        return;
                                    }
                                    for (int i = 0; i < dimension; i++)
                                        grid[k][i] = line.charAt(i);
                                    k++;
                                    line = reader.readLine();
                                }

                                int b = 0, w = 0, g = 0, r = 0;
                                for (int i = 0; i < dimension; i++) {
                                    for (int j = 0; j < dimension; j++) {
                                        if (grid[i][j]=='B')
                                            b++;
                                        if (grid[i][j]=='W')
                                            w++;
                                        if (grid[i][j]=='G')
                                            g++;
                                        if (grid[i][j]=='R')
                                            r++;
                                    }
                                }
                                if (g!=0 || r!=0) {
                                    JOptionPane.showMessageDialog(null,"玩家人数不正确");
                                    return;
                                }
                                if (b!=19 || w!=19) {
                                    JOptionPane.showMessageDialog(null,"棋子数目不对");
                                    return;
                                }
                                initChessBord.load(2, grid, gameplayer);
                            }
                            if (num.equals("4")) {
                                line = reader.readLine();
                                while (line!=null) {
                                    if (line.length()!=dimension) {
                                        JOptionPane.showMessageDialog(null,"棋盘规格错误");
                                        break;
                                    }
                                    for (int i = 0; i < dimension; i++)
                                        grid[k][i] = line.charAt(i);
                                    k++;
                                    line = reader.readLine();
                                }
                                int b = 0, w = 0, g = 0, r = 0;
                                for (int i = 0; i < dimension; i++) {
                                    for (int j = 0; j < dimension; j++) {
                                        if (grid[i][j]=='B')
                                            b++;
                                        if (grid[i][j]=='W')
                                            w++;
                                        if (grid[i][j]=='G')
                                            g++;
                                        if (grid[i][j]=='R')
                                            r++;
                                    }
                                }
                                if (b!=13 || w!=13 || g!=13 || r!=13) {
                                    JOptionPane.showMessageDialog(null,"棋子数目不对");
                                    return;
                                }
                                initChessBord.load(4, grid, gameplayer);
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        start.add(two);
        start.add(four);
        file.add(save);
        file.add(load);
        menuBar.add(start);
        menuBar.add(file);

        setJMenuBar(menuBar);

//        JButton button = new JButton("...");
//        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Button clicked!"));
//        button.setLocation(740, 760);
//        button.setSize(20, 12);
//        add(button);
    }
}
