import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
    String [] input;
    int initialmines;

    boolean dummy;
    boolean inGame = true;

    int row;
    int column;
    final int spacing;
    int width;
    int difficulty;

    int shift;


    Grid() {
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


        while(true) {
            try {
                read = JOptionPane.showInputDialog("Enter the desired grid of board");
                input = read.split(",");

                int dummyrow = Integer.parseInt(input[0]);
                int dummycolumn = Integer.parseInt(input[1]);


                if (dummyrow <= 3) {
                    row = 3;
                } else
                    row = dummyrow;
                if (dummycolumn <= 3) {
                    column = 3;
                } else
                    column = dummycolumn;
            }
            catch(ArrayIndexOutOfBoundsException ex){ System.out.println("Invalid input type. Please try again!");}
            catch (java.lang.NumberFormatException ex) {
                System.out.println("Invalid input type. Please try again!");
            }


            if (row >= 22) {
                width = 20;
            }
            if (row >= 33) {
                width = 15;
            }
            if (row < 22) {
                width = 30;
            }
            if(row>=3 && column >=3){
                break;

            }        }
        name_first = JOptionPane.showInputDialog("Name of the first player");
        name_second = JOptionPane.showInputDialog("Name of the second player");
        spacing = width/14;

        cells = new Position[column][row];
        neighbourMines = new int[column][row];
        Random rand = new Random();

        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                boolean isMine = rand.nextInt(100) < 20;

                if (isMine) {
                    cells[i][j] = new Position(true);

                    for (int k = -1; k <=1; k++) {
                        for (int p = -1; p <=1; p++) {
                            if ((i + k >= 0) && (i + k < column) && (j + p >= 0) && (j + p < row)) {
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
        this.setSize((width+2*spacing)*column+width+150, (width+2*spacing)*column+200+width);
        this.setVisible(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackGround backe = new BackGround();
        this.setContentPane(backe);

        MouseMove move = new MouseMove();
        this.addMouseMotionListener(move);
        MouseClick click = new MouseClick();
        this.addMouseListener(click);


    }


    public int findRow() {  // in order to find number of element in a row (Y- axis) when it is clicked
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                if (x >= 2*spacing+ i *(width+2*spacing) && x <= (spacing+width)+i*(width+2*spacing) && y >= (spacing+105)+j*(width+2*spacing) && y <= (spacing+105+width)+j*(width+2*spacing))
                    return j;
            }
        }
        return -1;
    }

    public int findColumn() {  // in order to find number of element in a column (X- axis) when it is clicked
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                if (x >= 2*spacing+ i *(width+2*spacing) && x <= (spacing+width)+i*(width+2*spacing) && y >= (spacing+105)+j*(width+2*spacing) && y <= (spacing+105+width)+j*(width+2*spacing))
                    return i;
            }
        }
        return -1;
    }


    public void revealNear1(int x, int y) {


        for (int m = -1; m <= 1; m++) {
            for (int n = -1; n <= 1; n++) {
                if ((x + m >= 0) && (x + m < column) && (y + n >= 0) && (y + n < row)) {
                    if (!cells[x+ m ][ y+ n ].isFlagged() && cells[x + m ][y + n ].isHidden()) {
                        show1(x+m,y+n);


                    }

                }
            }
        }

    }

    public void show1(int x,int y) {

        if (cells[x][y].isState() == PositionState.Nothing) {
            cells[x][y].reveal();
            RevealedCells1++;
        }
        if (cells[x][y].isState() == PositionState.Nothing && (neighbourMines[x][y] == 0)) {
            revealNear1(x, y);
        }
    }

    public void revealNear2(int x, int y) {


        for (int m = -1; m <= 1; m++) {
            for (int n = -1; n <= 1; n++) {
                if ((x + m >= 0) && (x + m < column) && (y + n >= 0) && (y + n < row)) {
                    if (!cells[x+ m ][ y+ n ].isFlagged() && cells[x + m ][y + n ].isHidden()) {
                        show2(x+m,y+n);


                    }

                }
            }
        }

    }

    public void show2(int x,int y) {

        if (cells[x][y].isState() == PositionState.Nothing) {
            cells[x][y].reveal();
            RevealedCells2++;
        }
        if (cells[x][y].isState() == PositionState.Nothing && (neighbourMines[x][y] == 0)) {
            revealNear2(x, y);
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

        public int NumDigit(int n){
            int count =0;
            while(n!=0){
                n/=10;
                ++count;
            }
            return count;

        }

        public void paintComponent(Graphics c) {
            if(width ==20){
                shift = -9;
            }
          else  if(width==15){
                shift = -8;
            }
            c.setColor(Color.LIGHT_GRAY);
            c.fillRect(0, 0, 1800,1500);

            for (int i = 0; i < column; i++) {
                for (int j = 0; j < row; j++) {
                    c.setColor(Color.orange);

                    if (cells[i][j].isState() == PositionState.AllExplode) {
                        c.setColor(Color.LIGHT_GRAY);
                    }

                    if (cells[i][j].isHidden() == false) {
                        c.setColor(Color.LIGHT_GRAY);
                    }
                    c.fillRect(spacing + i *(width+2*spacing) , spacing+70+j * (width+2*spacing), width, width);

                    if(clock %2 ==1) {

                        if (cells[i][j].isState() == PositionState.Explosion) {
                            c.setColor(Color.red);
                            c.setFont(new Font("Courier New", Font.BOLD,20));
                            c.drawString(name_second + " lose",170+40*NumDigit(mines),(width+2*spacing)*column+110+width);
                            c.fillRect(spacing + i *(width+2*spacing) , spacing+70+j * (width+2*spacing), width, width);
                            inGame = false;
                        }

                        c.setColor(Color.black);
                        c.setFont(new Font("Courier New", Font.BOLD, 20));
                        c.drawString("Player one "+(numFlags_first) + " score " +RevealedCells1,5,50);
                        c.drawString("Сurrent player "+name_first,5,(width+2*spacing)*column+110+width);

                        if (mines == 0 )  {
                            c.setColor(Color.GREEN);
                            c.setFont(new Font("Courier New", Font.BOLD, 20));
                            c.drawString("YOU WIN", 20 + 40 * NumDigit(mines), (width+2*spacing)*column+95+width);
                            c.drawString("CONGRATS, " +name_second, 20 + 40 * NumDigit(mines), (width+2*spacing)*column+110+width);
                            inGame = false;

                        }
                        if (RevealedCells1+RevealedCells2 == (column * row) - initialmines)  {
                            if(RevealedCells2>RevealedCells1) {
                                c.setColor(Color.GREEN);
                                c.setFont(new Font("Courier New", Font.BOLD, 20));
                                c.drawString("YOU WIN", 20 + 40 * NumDigit(mines), (width + 2 * spacing) * column + 95 + width);
                                c.drawString("CONGRATS, " + name_second, 20 + 40 * NumDigit(mines), (width + 2 * spacing) * column + 110 + width);
                                inGame = false;
                            }
                        }

                        if (cells[i][j].isHidden() == false && neighbourMines[i][j] != 0) {
                            c.setColor(Color.black);
                            c.setFont(new Font("Courier New", Font.BOLD, width / 2));
                            c.drawString(Integer.toString(neighbourMines[i][j]), i * (width + 2 * spacing) + width / 3, j * (width + 2 * spacing) + 90 + width / 5 + shift);
                        }
                        if (cells[i][j].isState() == PositionState.Explosion) {
                            c.setColor(Color.black);
                            c.setFont(new Font("Courier", Font.BOLD, width / 2));
                            c.drawString("\uD83D\uDCA3", i * (width + 2 * spacing) + width / 3, j * (width + 2 * spacing) + 90 + width / 5 + shift);
                        }
                        if (cells[i][j].isState() == PositionState.AllExplode) {
                            c.setColor(Color.black);
                            c.setFont(new Font("Courier", Font.BOLD, width / 2));
                            c.drawString("\uD83D\uDCA3", i * (width + 2 * spacing) + width / 3, j * (width + 2 * spacing) + 90 + width / 5 + shift);
                        }

                        if (cells[i][j].isFlagged()) {
                            if (width == 30) {
                                c.setColor(Color.black);
                                c.fillRect(i * (width + 2 * spacing) + 16, j * (width + 2 * spacing) + 80, 5, 10);
                                c.fillRect(i * (width + 2 * spacing) + 10, j * (width + 2 * spacing) + 90, 15, 6);
                                c.setColor(Color.red);
                                c.fillRect(i * (width + 2 * spacing) + 8, j * (width + 2 * spacing) + 80, 10, 7);
                            }
                            if (width == 20) {
                                c.setColor(Color.black);
                                c.fillRect(i * (width + 2 * spacing) + 9, j * (width + 2 * spacing) + 77, 3, 9);
                                c.fillRect(i * (width + 2 * spacing) + 6, j * (width + 2 * spacing) + 85, 10, 4);
                                c.setColor(Color.red);
                                c.fillRect(i * (width + 2 * spacing) + 1, j * (width + 2 * spacing) + 77, 8, 4);
                            }

                            if (width == 15) {
                                c.setColor(Color.black);
                                c.fillRect(i * (width + 2 * spacing) + 8, j * (width + 2 * spacing) + 75, 2, 6);
                                c.fillRect(i * (width + 2 * spacing) + 5, j * (width + 2 * spacing) + 80, 7, 3);
                                c.setColor(Color.red);
                                c.fillRect(i * (width + 2 * spacing) + 2, j * (width + 2 * spacing) + 75, 6, 3);
                            }
                        }
                    }
                    else if(clock % 2 ==0){
                        if (cells[i][j].isState() == PositionState.Explosion) {
                            c.setColor(Color.red);
                            c.setFont(new Font("Courier New", Font.BOLD,20));
                            c.drawString(name_first + " lose",170+40*NumDigit(mines),(width+2*spacing)*column+110+width);
                            c.fillRect(spacing + i *(width+2*spacing) , spacing+70+j * (width+2*spacing), width, width);
                            inGame = false;
                        }

                        c.setColor(Color.black);
                        c.setFont(new Font("Courier New", Font.BOLD, 20));
                        c.drawString("Player two "+(numFlags_second)+" score " +RevealedCells2,5,50);
                        c.drawString("Сurrent player "+name_second,5,(width+2*spacing)*column+110+width);

                        if (mines == 0) {
                            c.setColor(Color.GREEN);
                            c.setFont(new Font("Courier New", Font.BOLD, 20));
                            c.drawString("YOU WIN", 20 + 40 * NumDigit(mines), (width+2*spacing)*column+95+width);
                            c.drawString("CONGRATS, " +name_first, 20 + 40 * NumDigit(mines), (width+2*spacing)*column+110+width);
                            inGame = false;

                        }
                        if (RevealedCells1+RevealedCells2 == (column * row) - initialmines) {
                            if (RevealedCells1 > RevealedCells2) {
                                c.setColor(Color.GREEN);
                                c.setFont(new Font("Courier New", Font.BOLD, 20));
                                c.drawString("YOU WIN", 20 + 40 * NumDigit(mines), (width + 2 * spacing) * column + 95 + width);
                                c.drawString("CONGRATS, " + name_first, 20 + 40 * NumDigit(mines), (width + 2 * spacing) * column + 110 + width);
                                inGame = false;

                            }
                        }


                        if (cells[i][j].isHidden() == false && neighbourMines[i][j] != 0) {
                            c.setColor(Color.black);
                            c.setFont(new Font("Courier New", Font.BOLD, width / 2));
                            c.drawString(Integer.toString(neighbourMines[i][j]), i * (width + 2 * spacing) + width / 3, j * (width + 2 * spacing) + 90 + width / 5 + shift);
                        }
                        if (cells[i][j].isState() == PositionState.Explosion) {
                            c.setColor(Color.black);
                            c.setFont(new Font("Courier", Font.BOLD, width / 2));
                            c.drawString("\uD83D\uDCA3", i * (width + 2 * spacing) + width / 3, j * (width + 2 * spacing) + 90 + width / 5 + shift);
                        }
                        if (cells[i][j].isState() == PositionState.AllExplode) {
                            c.setColor(Color.black);
                            c.setFont(new Font("Courier", Font.BOLD, width / 2));
                            c.drawString("\uD83D\uDCA3", i * (width + 2 * spacing) + width / 3, j * (width + 2 * spacing) + 90 + width / 5 + shift);
                        }

                        if (cells[i][j].isFlagged()) {
                            if (width == 30) {
                                c.setColor(Color.black);
                                c.fillRect(i * (width + 2 * spacing) + 16, j * (width + 2 * spacing) + 80, 5, 10);
                                c.fillRect(i * (width + 2 * spacing) + 10, j * (width + 2 * spacing) + 90, 15, 6);
                                c.setColor(Color.red);
                                c.fillRect(i * (width + 2 * spacing) + 8, j * (width + 2 * spacing) + 80, 10, 7);
                            }
                            if (width == 20) {
                                c.setColor(Color.black);
                                c.fillRect(i * (width + 2 * spacing) + 9, j * (width + 2 * spacing) + 77, 3, 9);
                                c.fillRect(i * (width + 2 * spacing) + 6, j * (width + 2 * spacing) + 85, 10, 4);
                                c.setColor(Color.red);
                                c.fillRect(i * (width + 2 * spacing) + 1, j * (width + 2 * spacing) + 77, 8, 4);
                            }

                            if (width == 15) {
                                c.setColor(Color.black);
                                c.fillRect(i * (width + 2 * spacing) + 8, j * (width + 2 * spacing) + 75, 2, 6);
                                c.fillRect(i * (width + 2 * spacing) + 5, j * (width + 2 * spacing) + 80, 7, 3);
                                c.setColor(Color.red);
                                c.fillRect(i * (width + 2 * spacing) + 2, j * (width + 2 * spacing) + 75, 6, 3);
                            }
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
