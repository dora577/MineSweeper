import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class Grid extends JFrame {
    Position[][] cells;
    int[][] neighbourMines;
    int x, y;
    int numFlags;
    int mines;
    int RevealedCells;
    String read;
    int difficulty;

    boolean dummy;
    boolean inGame = true;

    Grid() {
        cells = new Position[9][9];
        neighbourMines = new int[9][9];
        Random rand = new Random();
        while(true) {
            try {

                read = JOptionPane.showInputDialog("Enter the difficulty of board\n 1 || 2 || 3");

                switch(read){
                    case "1" : difficulty = 15;
                    break;
                    case "2" : difficulty = 20;
                    break;
                    case "3" : difficulty = 30;
                }
                if(!(read.equals("1")||read.equals("2") || read.equals("3"))){
                    System.out.println("Retry the input. Make sure\n 1 || 2 || 3 ");
                }
                if(difficulty!= 0){
                    break;

                }


            } catch (Exception ex) {
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boolean isMine = rand.nextInt(100) < difficulty;

                if (isMine) {
                    cells[i][j] = new Position(true);

                    for (int k = -1; k <=1; k++) {
                        for (int p = -1; p <=1; p++) {
                            if ((i + k >= 0) && (i + k < 9) && (j + p >= 0) && (j + p < 9)) {
                                neighbourMines[i + k][j + p]++;
                            }
                        }
                    }
                } else {
                    cells[i][j] = new Position(false);
                }
            }
        }

        CountMines();
        numFlags = mines;

        this.setTitle("MineSweeper");
        this.setSize(644, 740);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackGround backe = new BackGround();
        this.setContentPane(backe);

        MouseMove move = new MouseMove();
        this.addMouseMotionListener(move);
        MouseClick click = new MouseClick();
        this.addMouseListener(click);


    }


    public int findRow() {  // in order to find number of element in a row (Y- axis) when it is clicked
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (x >= 10 + i * 70 && x <= i * 70 + 60 && y >= j * 70 + 101 && y <= j * 70 + 161)
                    return j;
            }
        }
        return -1;
    }

    public int findColumn() {  // in order to find number of element in a column (X- axis) when it is clicked
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (x >= 10 + i * 70 && x <= i * 70 + 60 && y >= j * 70 + 101 && y <= j * 70 + 161)
                    return i;
            }
        }
        return -1;
    }



    public void revealNear(int x, int y) {  // METHOD to reveal near. However, it is not recursive.
            for (int m = -1; m <= 1; m++) {
                for (int n = -1; n <= 1; n++) {
                    if ((x + m >= 0) && (x + m < 9) && (y + n >= 0) && (y + n < 9)) {
                        if (!cells[x+ m ][ y+ n ].isFlagged() && cells[x + m ][y + n ].isHidden()) {
                            show(x+m,y+n);


                        }

                    }
                }
            }

            }
    public void show(int x,int y){

    if(cells[x][y].isState()==PositionState.Nothing){cells[x][y].reveal();
        RevealedCells++;}
   if(cells[x][y].isState() == PositionState.Nothing && (neighbourMines[x][y] == 0)  ){
        revealNear(x,y);
    }
    }

    public void explodeAll() {
        dummy = true;
        for (Position[] row : cells) {
            for (Position cell : row) {
                if (cell.isState() == PositionState.Mine) {
                    cell.explodeAll();
                    if(cell.isFlagged()){
                        cell.changeFlag();
                    }
                }
            }
        }
    }
    public int CountMines(){

        for (Position[] row : cells) {
            for (Position cell : row) {
                if (cell.isState() == PositionState.Mine) {
                    mines++;
                }
            }
        }
        return mines;

    }



    public class BackGround extends JPanel {

        public void paintComponent(Graphics c) {
            c.setColor(Color.LIGHT_GRAY);
            c.fillRect(0, 0, 644, 740);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    c.setColor(Color.orange);

                    if (cells[i][j].isState() == PositionState.Explosion) {
                        c.setColor(Color.red);
                        c.setFont(new Font("Courier New", Font.BOLD, 90));
                        c.drawString("YOU LOSE",135,65);
                        inGame = false;
                    }
                    if (cells[i][j].isState() == PositionState.AllExplode) {
                        c.setColor(Color.LIGHT_GRAY);
                    }

                    if (cells[i][j].isHidden() == false) {
                        c.setColor(Color.LIGHT_GRAY);
                    }


                    c.fillRect(5 + i * 70, j * 70 + 75, 60, 60);

                    c.setColor(Color.black);
                    c.setFont(new Font("Courier New", Font.BOLD, 60));
                    c.drawString(Integer.toString(numFlags),5,50);

                    if(mines ==0 || RevealedCells==81-mines ){
                        c.setColor(Color.GREEN);
                        c.setFont(new Font("Courier New", Font.BOLD, 90));
                        c.drawString("YOU WIN",145,350);
                        c.drawString("CONGRATS",145,460);
                        inGame = false;
                    }


                    if (cells[i][j].isHidden() == false && neighbourMines[i][j] != 0) {
                        c.setColor(Color.black);
                        c.setFont(new Font("Courier New", Font.BOLD, 35));
                        c.drawString(Integer.toString(neighbourMines[i][j]), i * 70 + 23, j * 70 + 120);
                    }
                    if (cells[i][j].isState() == PositionState.Explosion) {
                        c.setColor(Color.black);
                        c.setFont(new Font("Courier", Font.BOLD, 35));
                        c.drawString("\uD83D\uDCA3", i * 70 + 17, j * 70 + 120);
                    }
                    if (cells[i][j].isState() == PositionState.AllExplode) {
                        c.setColor(Color.black);
                        c.setFont(new Font("Courier", Font.BOLD, 35));
                        c.drawString("\uD83D\uDCA3", i * 70 + 17, j * 70 + 120);
                    }
                    if (cells[i][j].isFlagged()){
                        c.setColor(Color.black);
                        c.fillRect(i*70+32,j*70+90,5,40);
                        c.fillRect(i*70+20,j*70+125,30,10);
                        c.setColor(Color.red);
                        c.fillRect(i*70+16,j*70+90,20,15);
                      }

                }
            }
        }
    }

        public class MouseMove implements MouseMotionListener {
            public void mouseDragged(MouseEvent e) {
            }

            public void mouseMoved(MouseEvent e) {
            }

        }

        public class MouseClick implements MouseListener {
            public void mouseClicked(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                if(inGame){
                x = e.getX();
                y = e.getY();
                if (SwingUtilities.isRightMouseButton(e)) {
                    if (findRow() != -1 && findColumn() != -1 && numFlags > 0 && cells[findColumn()][findRow()].isHidden()) {
                        if (!cells[findColumn()][findRow()].isFlagged()) {
                            if (cells[findColumn()][findRow()].isState() == PositionState.Mine) {
                                cells[findColumn()][findRow()].changeFlag();
                                numFlags--;
                                mines--;
                            } else if (cells[findColumn()][findRow()].isState() == PositionState.Nothing) {
                                cells[findColumn()][findRow()].changeFlag();
                                numFlags--;
                            }

                        } else {
                            cells[findColumn()][findRow()].changeFlag();
                            numFlags++;
                        }
                    } else if (findRow() != -1 && findColumn() != -1 && numFlags == 0 && cells[findColumn()][findRow()].isFlagged()) {
                        if (findRow() != -1 && findColumn() != -1) {
                            cells[findColumn()][findRow()].changeFlag();
                            numFlags++;
                        }

                    }

                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    if (findRow() != -1 && findColumn() != -1) {
                        if (cells[findColumn()][findRow()].isHidden() && !cells[findColumn()][findRow()].isFlagged()) {
                            if (cells[findColumn()][findRow()].isState() == PositionState.Mine) {
                                cells[findColumn()][findRow()].explode();
                                explodeAll();

                            }

                            if (cells[findColumn()][findRow()].isHidden() && cells[findColumn()][findRow()].isState() == PositionState.Nothing) {
                                try {
                                    show(findColumn(), findRow());
                                } catch (IndexOutOfBoundsException ex) {
                                }


                            }
                        }
                    }
                }
            }
            }


            public void mouseReleased(MouseEvent e) {
            }
        }


    }
