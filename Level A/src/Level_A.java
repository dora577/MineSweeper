import java.util.Scanner;


public class Level_A {
    public static void purifying (char arr_d[][], char arr_h[][], int i, int j)
    {
        if (i>0 && i<8 && j>0 && j<8)
        {
            if (arr_h[i][j] == '_' && arr_d[i][j] != '_')
            {
                arr_d[i][j] = arr_h[i][j];
                for (int ju=j-1; ju<i+1; ju++)
                {
                    purifying(arr_d,arr_h, i-1, ju);
                }
                for (int ju=j-1; ju<i+1; ju++)
                {
                    purifying(arr_d,arr_h, i+1, ju);
                }
                purifying(arr_d, arr_h, i, j-1);
                purifying(arr_d, arr_h, i, j+1);
            }
        }
        else if(i==0 && j==0)
        {
            if (arr_h[i][j] == '_' && arr_d[i][j] != '_')
            {
                arr_d[i][j] = arr_h[i][j];
                purifying(arr_d,arr_h, i, j+1);
                purifying(arr_d,arr_h, i+1, j);
                purifying(arr_d, arr_h, i+1, j+1);
            }
        }
        else if(i==8 && j==8)
        {
            if (arr_h[i][j] == '_' && arr_d[i][j] != '_')
            {
                arr_d[i][j] = arr_h[i][j];
                purifying(arr_d,arr_h, i, j-1);
                purifying(arr_d,arr_h, i-1, j);
                purifying(arr_d, arr_h, i-1, j-1);
            }
        }
        else if(i==0 && j==8)
        {
            if (arr_h[i][j] == '_' && arr_d[i][j] != '_')
            {
                arr_d[i][j] = arr_h[i][j];
                purifying(arr_d,arr_h, i, j-1);
                purifying(arr_d,arr_h, i+1, j);
                purifying(arr_d, arr_h, i+1, j-1);
            }
        }
        else if(i==8 && j==0)
        {
            if (arr_h[i][j] == '_' && arr_d[i][j] != '_')
            {
                arr_d[i][j] = arr_h[i][j];
                purifying(arr_d,arr_h, i-1, j+1);
                purifying(arr_d,arr_h, i-1, j);
                purifying(arr_d, arr_h, i, j+1);
            }
        }
        else if (i==0 &&(j>0 && j<8))
        {
            if (arr_h[i][j] == '_' && arr_d[i][j] != '_')
            {
                arr_d[i][j] = arr_h[i][j];
                purifying(arr_d,arr_h, i, j+1);
                purifying(arr_d,arr_h, i, j-1);
                purifying(arr_d,arr_h, i+1, j+1);
                purifying(arr_d,arr_h, i+1, j);
                purifying(arr_d,arr_h, i+1, j-1);
            }
        }
        else if (i==8 &&(j>0 && j<8))
        {
            if (arr_h[i][j] == '_' && arr_d[i][j] != '_')
            {
                arr_d[i][j] = arr_h[i][j];
                purifying(arr_d,arr_h, i, j+1);
                purifying(arr_d,arr_h, i, j-1);
                purifying(arr_d,arr_h, i-1, j+1);
                purifying(arr_d,arr_h, i-1, j);
                purifying(arr_d,arr_h, i-1, j-1);
            }
        }
        else if (j==0 &&(i>0 && i<8))
        {
            if (arr_h[i][j] == '_' && arr_d[i][j] != '_')
            {
                arr_d[i][j] = arr_h[i][j];
                purifying(arr_d,arr_h, i+1, j);
                purifying(arr_d,arr_h, i-1, j);
                purifying(arr_d,arr_h, i-1, j+1);
                purifying(arr_d,arr_h, i, j+1);
                purifying(arr_d,arr_h, i+1, j+1);
            }
        }
        else if (j==8 &&(i>0 && i<8))
        {
            if (arr_h[i][j] == '_' && arr_d[i][j] != '_')
            {
                arr_d[i][j] = arr_h[i][j];
                purifying(arr_d,arr_h, i+1, j);
                purifying(arr_d,arr_h, i-1, j);
                purifying(arr_d,arr_h, i-1, j-1);
                purifying(arr_d,arr_h, i, j-1);
                purifying(arr_d,arr_h, i+1, j-1);
            }
        }
    }
    public static void main(String[] args) {
        boolean Continue_game = true; //boolean for loop, which would check for new game
        String g_rest = new String(); //String status yes/no to the new game
        Scanner in = new Scanner(System.in);//Scanner =-)
        Scanner inp = new Scanner(System.in);
        boolean dummy = true;
        boolean dummy_prob=false;
        int prob=0;
        int mines=0;
        int temp_p;

        System.out.println("Welcome to the Minesweeper");
        System.out.println("Created by the team: Protect public by private methods");
        System.out.println("Darkhan Baizhan and Asset Baisalov present");
        System.out.println("The minesweeper  without OOP");
        System.out.println("Please choose the level of the game");
        System.out.println("1 is for easy game, \n2 is for medium game\n3 is for hard");
        temp_p=inp.nextInt();
        do{switch (temp_p) //loop for thaking the level of the game
        {
            case 1: prob = 15; dummy_prob=false; break;
            case 2: prob = 20; dummy_prob=false; break;
            case 3: prob = 30; dummy_prob=false; break;
            default: System.out.println("Please choose the one from 3 choices"); dummy_prob=true; break;
        }
        }while(dummy_prob);
        System.out.println("The probability to find a bomb is " + prob + "%");
        System.out.println("At this level, the board will be 9x9 size");
        System.out.println("Please enjoy the game");
        System.out.println("Do want to start the new game? yes/no");
        do {
            g_rest = in.nextLine();
            if (g_rest.equalsIgnoreCase("yes")) {
                boolean in_da_game = true;
                System.out.println("Starting of the new game");
                char desk[][] = new char[9][9]; //creating the layer for graphical representation


                char hidden_layer[][] = new char[9][9]; //creating the layer for status of the squares
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        hidden_layer[i][j] = '_';
                    }
                }

                for (int i=0; i<9; i++)
                {
                    for (int j=0; j<9; j++)
                    {
                        boolean isMine = (Math.random()*100)<prob;
                        if (isMine)
                        {
                            hidden_layer[i][j]=(char) 0xF0;
                            mines++;
                        }
                    }
                }
                System.out.println("Number of mines at this game " + mines);

                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        desk[i][j] = (char) 9633;
                        System.out.print(desk[i][j]);
                    }
                    System.out.println();
                }
                for (int i=0; i<9; i++)
                {
                    for (int j=0; j<9; j++)
                    {
                        if (hidden_layer[i][j]!=(char) 0xF0)
                        {
                            if (i > 0 && j > 0 && i < 8 && j < 8)
                            {
                                int temp_ind = 0;
                                for (int iu=i-1; iu<i+1; iu++)
                                {
                                    if (hidden_layer[iu][j-1]==(char) 0xF0)
                                    {
                                        temp_ind++;
                                    }
                                }
                                for (int iu=i-1; iu<i+1; iu++)
                                {
                                    if (hidden_layer[iu][j+1]==(char) 0xF0)
                                    {
                                        temp_ind++;
                                    }
                                }
                                if (hidden_layer[i-1][j]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if (hidden_layer[i+1][j]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if (temp_ind!=0) hidden_layer[i][j]=(char)(temp_ind+'0');
                            }
                            else if(i==0 && j==0)
                            {
                                int temp_ind=0;
                                if(hidden_layer[i+1][j+1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i][j+1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i+1][j]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if (temp_ind!=0) hidden_layer[i][j]=(char)(temp_ind+'0');
                            }
                            else if(i==8 && j==8)
                            {
                                int temp_ind=0;
                                if(hidden_layer[i-1][j-1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i][j-1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i-1][j]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if (temp_ind!=0) hidden_layer[i][j]=(char)(temp_ind+'0');
                            }
                            else if(i==0 && j==8)
                            {
                                int temp_ind=0;
                                if(hidden_layer[i+1][j-1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i][j-1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i+1][j]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if (temp_ind!=0) hidden_layer[i][j]=(char)(temp_ind+'0');
                            }
                            else if(i==8 && j==0)
                            {
                                int temp_ind=0;
                                if(hidden_layer[i-1][j+1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i][j+1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i-1][j]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if (temp_ind!=0) hidden_layer[i][j]=(char)(temp_ind+'0');
                            }
                            else if (i==0 &&(j>0 && j<8))
                            {
                                int temp_ind=0;
                                if(hidden_layer[i][j+1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i][j-1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i+1][j]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i+1][j-1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i+1][j+1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if (temp_ind!=0) hidden_layer[i][j]=(char)(temp_ind+'0');
                            }
                            else if (i==8 &&(j>0 && j<8))
                            {
                                int temp_ind=0;
                                if(hidden_layer[i][j+1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i][j-1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i-1][j]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i-1][j-1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i-1][j+1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if (temp_ind!=0) hidden_layer[i][j]=(char)(temp_ind+'0');
                            }
                            else if (j==0 &&(i>0 && i<8))
                            {
                                int temp_ind=0;
                                if(hidden_layer[i][j+1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i+1][j]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i-1][j]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i+1][j+1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i-1][j+1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if (temp_ind!=0) hidden_layer[i][j]=(char)(temp_ind+'0');
                            }
                            else if (j==8 &&(i>0 && i<8))
                            {
                                int temp_ind=0;
                                if(hidden_layer[i][j-1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i+1][j]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i-1][j]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i+1][j-1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if(hidden_layer[i-1][j-1]==(char) 0xF0)
                                {
                                    temp_ind++;
                                }
                                if (temp_ind!=0) hidden_layer[i][j]=(char)(temp_ind+'0');
                            }
                        }
                    }
                }

                System.out.println("Now you see the board, which would be used at the game.");
                int num_of_boxes = 81;
                do {
                    Scanner coord = new Scanner(System.in);
                    System.out.println("Please, start enter the coordinates");
                    boolean correct_coord = true;
                    int column = 0;
                    int line = 0;
                    do {
                        while(dummy){
                            try {
                                System.out.println("Enter the number of column");
                                column = coord.nextInt();
                                column = column - 1;
                                System.out.println("Enter the coordinate of the line");
                                line = coord.nextInt();
                                line = line - 1;
                                if(column <0 && line<0)
                                {
                                    throw new Exception("You made a mistake, please enter the coordinates again");
                                }
                            } catch (Exception ex) {
                                System.out.println(ex);
                                in.nextLine();
                            }
                            finally
                            {
                                if (column >= 0 && line >= 0) {
                                    break;
                                }
                            }
                        }
                        if (column < 9 && line < 9) {
                            correct_coord = false;}
                        else {
                            System.out.println("You made a mistake, please enter the coordinates again");
                            correct_coord = true;
                        }
                    }while (correct_coord);
                    purifying(desk, hidden_layer, line, column);
                    desk[line][column] = hidden_layer[line][column];
                    num_of_boxes--;
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            System.out.print(desk[i][j]);
                        }
                        System.out.println();
                    }
                    if (hidden_layer[line][column] == (char) 0xF0) {
                        System.out.println("Sorry, you lose!");
                        in_da_game = false;
                    } else if ((num_of_boxes - mines) == 0) {
                        System.out.println("You win!");
                        in_da_game = false;
                    } else {
                        in_da_game = true;
                    }
                } while (in_da_game);
                System.out.println("Do you want to start new game?");
                System.out.println("Please write yes or no as your answer");
            }
            else if (g_rest.equalsIgnoreCase("no")) {
                Continue_game = false;
            } else {
                System.out.println("Unknown command");
                System.out.println("Please rewrite your answer");
            }
        } while (Continue_game) ;
    }
}