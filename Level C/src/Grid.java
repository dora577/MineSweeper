import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class Grid extends JFrame {
    Position[][] cells;
    int[][] neighbourMines;
    int x, y;
    int numFlags_first, numFlags_second;
    int mines;
    int RevealedCells1;
    int RevealedCells2;
    int clock = 1;
    String name_first;
    String name_second;
    String read;
    int difficulty;
    int initialmines;

    boolean dummy;
    boolean inGame = true;

    Grid() {
        cells = new Position[9][9];
        neighbourMines = new int[9][9];
        Random rand = new Random();

        while(true) {
            try {

                read = JOptionPane.showInputDialog("Enter the desired difficulty of board\n 1 || 2 || 3");

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

        name_first = JOptionPane.showInputDialog("Name of the first player");
        name_second = JOptionPane.showInputDialog("Name of the second player");


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boolean isMine = rand.nextInt(100) < 20;

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

        numFlags_first = numFlags_second = mines;
        initialmines = mines;

        this.setTitle("MineSweeper");
        this.setSize(744, 840);
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



    public void revealNear1(int x, int y) {  // METHOD to reveal near. However, it is not recursive.


        for (int m = -1; m <= 1; m++) {
            for (int n = -1; n <= 1; n++) {
                if ((x + m >= 0) && (x + m < 9) && (y + n >= 0) && (y + n < 9)) {
                    if (!cells[x+ m ][ y+ n ].isFlagged() && cells[x + m ][y + n ].isHidden()) {
                        show1(x+m,y+n);


                    }

                }
            }
        }

    }
    public void show1(int x,int y){

        if(cells[x][y].isState()==PositionState.Nothing){cells[x][y].reveal();
            RevealedCells1++;}
        if(cells[x][y].isState() == PositionState.Nothing && (neighbourMines[x][y] == 0)  ){
            revealNear1(x,y);
        }
    }

    public void revealNear2(int x, int y) {  // METHOD to reveal near. However, it is not recursive.


        for (int m = -1; m <= 1; m++) {
            for (int n = -1; n <= 1; n++) {
                if ((x + m >= 0) && (x + m < 9) && (y + n >= 0) && (y + n < 9)) {
                    if (!cells[x+ m ][ y+ n ].isFlagged() && cells[x + m ][y + n ].isHidden()) {
                        show2(x+m,y+n);


                    }

                }
            }
        }

    }
    public void show2(int x,int y){

        if(cells[x][y].isState()==PositionState.Nothing){cells[x][y].reveal();
            RevealedCells2++;}
        if(cells[x][y].isState() == PositionState.Nothing && (neighbourMines[x][y] == 0)  ){
            revealNear2(x,y);
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
            c.fillRect(0, 0, 744, 840);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    c.setColor(Color.orange);

                    if (cells[i][j].isState() == PositionState.AllExplode) {
                        c.setColor(Color.LIGHT_GRAY);
                    }
                    if (cells[i][j].isHidden() == false) {
                        c.setColor(Color.LIGHT_GRAY);
                    }


                    c.fillRect(5 + i * 70, j * 70 + 75, 60, 60);
                    if (clock%2==1)
                    {
                        if (cells[i][j].isState() == PositionState.Explosion) {
                            c.setColor(Color.red);
                            c.fillRect(5 + i * 70, j * 70 + 75, 60, 60);
                            c.setFont(new Font("Courier New", Font.BOLD, 40));
                            c.drawString(name_second +" lose",420,50);
                            inGame = false;
                        }
                        c.setColor(Color.black);
                        c.setFont(new Font("Courier New", Font.BOLD, 30));
                        c.drawString("Player one "+(numFlags_first)+" score:" +RevealedCells1,5,50);
                        c.drawString("Сurrent player "+name_first,5,750);

                        if(mines ==0 ) {
                                c.setColor(Color.GREEN);
                                c.setFont(new Font("Courier New", Font.BOLD, 40));
                                c.drawString("YOU WIN", 145, 350);
                                c.drawString("CONGRATS, " + name_second, 145, 400);
                                inGame = false;

                        }
                        if(RevealedCells1+RevealedCells2==81-initialmines ) {
                          if ( RevealedCells2>RevealedCells1) {
                              c.setColor(Color.GREEN);
                              c.setFont(new Font("Courier New", Font.BOLD, 40));
                              c.drawString("YOU WIN", 145, 350);
                              c.drawString("CONGRATS, " + name_second, 145, 400);
                              inGame = false;
                          }
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
                    else if (clock%2==0)
                    {
                        if (cells[i][j].isState() == PositionState.Explosion) {
                            c.setColor(Color.red);
                            c.fillRect(5 + i * 70, j * 70 + 75, 60, 60);
                            c.setFont(new Font("Courier New", Font.BOLD, 40));
                            c.drawString(name_first +" lose",420,50);
                            inGame = false;

                        }
                        c.setColor(Color.black);
                        c.setFont(new Font("Courier New", Font.BOLD, 30));
                        c.drawString("Player two "+(numFlags_second)+" score: " + RevealedCells2,5,50);
                        c.drawString("Сurrent player "+name_second ,5,750);
                        if(mines ==0 ){

                                c.setColor(Color.GREEN);
                                c.setFont(new Font("Courier New", Font.BOLD, 40));
                                c.drawString("YOU WIN", 145, 350);
                                c.drawString("CONGRATS, " + name_first, 145, 400);
                                inGame = false;
                            }

                        if(RevealedCells1+RevealedCells2==81-initialmines ) {
                            if (RevealedCells2 < RevealedCells1) {
                                c.setColor(Color.GREEN);
                                c.setFont(new Font("Courier New", Font.BOLD, 40));
                                c.drawString("YOU WIN", 145, 350);
                                c.drawString("CONGRATS, " + name_first, 145, 400);
                                inGame = false;
                            }
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
            if (inGame) {
                x = e.getX();
                y = e.getY();
                boolean changed = false;
                if (clock % 2 == 1) {
                    if (SwingUtilities.isRightMouseButton(e)) {

                        if (findRow() != -1 && findColumn() != -1 && numFlags_first > 0 && cells[findColumn()][findRow()].isHidden()) {
                            if (!cells[findColumn()][findRow()].isFlagged()) {
                                if (cells[findColumn()][findRow()].isState() == PositionState.Mine) {
                                    cells[findColumn()][findRow()].changeFlag();
                                    numFlags_first--;
                                    mines--;
                                    changed = true;
                                } else if (cells[findColumn()][findRow()].isState() == PositionState.Nothing) {
                                    cells[findColumn()][findRow()].changeFlag();
                                    numFlags_first--;
                                    changed = true;
                                }

                            } else {
                                cells[findColumn()][findRow()].changeFlag();
                                numFlags_first++;
                                changed = true;
                            }
                        } else if (findRow() != -1 && findColumn() != -1 && numFlags_first == 0 && cells[findColumn()][findRow()].isFlagged()) {
                            if (findRow() != -1 && findColumn() != -1) {
                                cells[findColumn()][findRow()].changeFlag();
                                numFlags_first++;
                                changed = true;
                            }

                        }
                    } else if (SwingUtilities.isLeftMouseButton(e)) {
                        if (findRow() != -1 && findColumn() != -1) {
                            if (cells[findColumn()][findRow()].isHidden() && !cells[findColumn()][findRow()].isFlagged()) {
                                if (cells[findColumn()][findRow()].isState() == PositionState.Mine) {
                                    cells[findColumn()][findRow()].explode();
                                    explodeAll();
                                    changed = true;
                                }

                                if (cells[findColumn()][findRow()].isHidden() && cells[findColumn()][findRow()].isState() == PositionState.Nothing) {
                                    changed = true;
                                    try {
                                        show1(findColumn(), findRow());
                                    } catch (IndexOutOfBoundsException ex) {

                                    }
                                }
                            }
                        }
                    }

                }

                if (clock % 2 == 0) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        if (findRow() != -1 && findColumn() != -1 && numFlags_second > 0 && cells[findColumn()][findRow()].isHidden()) {
                            if (!cells[findColumn()][findRow()].isFlagged()) {
                                if (cells[findColumn()][findRow()].isState() == PositionState.Mine) {
                                    cells[findColumn()][findRow()].changeFlag();
                                    numFlags_second--;
                                    mines--;
                                    changed = true;
                                } else if (cells[findColumn()][findRow()].isState() == PositionState.Nothing) {
                                    cells[findColumn()][findRow()].changeFlag();
                                    numFlags_second--;
                                    changed = true;
                                }

                            } else {
                                cells[findColumn()][findRow()].changeFlag();
                                changed = true;
                                numFlags_second++;
                            }
                        } else if (findRow() != -1 && findColumn() != -1 && numFlags_second == 0 && cells[findColumn()][findRow()].isFlagged()) {
                            if (findRow() != -1 && findColumn() != -1) {
                                cells[findColumn()][findRow()].changeFlag();
                                numFlags_second++;
                                changed = true;
                            }

                        }
                    }
                    else if (SwingUtilities.isLeftMouseButton(e)) {
                        if (findRow() != -1 && findColumn() != -1) {
                            if (cells[findColumn()][findRow()].isHidden() && !cells[findColumn()][findRow()].isFlagged()) {
                                if (cells[findColumn()][findRow()].isState() == PositionState.Mine) {
                                    cells[findColumn()][findRow()].explode();
                                    explodeAll();
                                    changed = true;
                                }

                                if (cells[findColumn()][findRow()].isHidden() && cells[findColumn()][findRow()].isState() == PositionState.Nothing) {
                                    changed = true;
                                    try {
                                        show2(findColumn(), findRow());
                                    } catch (IndexOutOfBoundsException ex) {

                                    }
                                }
                            }
                        }
                    }

                }


            if (changed == true) {
                clock++;
            }
        }
        }
        public void mouseReleased(MouseEvent e) {
        }
    }
}
