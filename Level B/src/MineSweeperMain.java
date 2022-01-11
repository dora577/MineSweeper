public class MineSweeperMain implements Runnable  {
    Grid grid = new Grid();
    public static void main(String args[]) {
       new Thread(new MineSweeperMain()).start();

    }

public void run(){
        while(true){
            grid.repaint();
        }
}
}
