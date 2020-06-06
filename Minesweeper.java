import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    private static int dimension;
    private static String[][] board;
    private static String[][] myboard;
    private static int move;

    MineSweeper(int mine,int dim){
        dimension=dim;
        move=dimension*dimension-mine;
        board=new String[dimension][dimension];
        myboard=new String[dimension][dimension];
        for (int i=0;i<dimension;i++){
            for (int j=0;j<dimension;j++){
                board[i][j]="O";
                myboard[i][j]="O";
            }
        }
        Random random=new Random();
        int x,y;
        for (int i=0;i<mine;i++){
            while(true){
                x=random.nextInt(dimension);
                y=random.nextInt(dimension);
                if(board[x][y]=="O"){
                    board[x][y]="M";
                    break;
                }
            }
        }
        PrintBoard();
        System.out.println("=====================");
        PrintBoard2();
        System.out.println("=====================");
    }

    private static int isMine(int i,int j){
        if (i<0 || i>=dimension){
            return 0;
        }
        if (j<0 || j>=dimension){
            return 0;
        }
        if (board[i][j]=="M"){
            return 1;
        }
        return 0;
    }

    private static int ScanMines(int i,int j){
        if (i<0 || i>=dimension){
            return -1;
        }
        if (j<0 || j>=dimension){
            return -1;
        }
        if (myboard[i][j]!="O"){
            return -1;
        }
        int mines=0;
        for (int x=i-1;x<=i+1;x++){
            for (int y=j-1;y<=j+1;y++){
                if(x!=i || y!=j){
                    mines+=isMine(x,y);
                }
            }
        }
        return mines;
    }

    private static void Expand(int i, int j){
        for (int x=i-1;x<=i+1;x++){
            for (int y=j-1;y<=j+1;y++){
                if(x!=i || y!=j){
                    int mines=ScanMines(x,y);
                    if (mines>0){
                        myboard[x][y]=""+mines;
                        move--;
                    }else if(mines==0){
                        myboard[x][y]="X";
                        move--;
                        Expand(x,y);
                    }
                }
            }
        }
    }

    private static void PrintBoard(){
        for (int i=0;i<dimension;i++){
            for (int j=0;j<dimension;j++){
                System.out.print(myboard[i][j]+" ");
            }
            System.out.print("\n");
        }
    }

    private static void PrintBoard2(){
        for (int i=0;i<dimension;i++){
            for (int j=0;j<dimension;j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.print("\n");
        }
    }

    private static boolean Move(int type,int i,int j) {
        if (type!=1 && type!=2){
            return false;
        }
        if (i<0 || i>=dimension){
            return false;
        }
        if (j<0 || j>=dimension){
            return false;
        }
        if (myboard[i][j]=="O"){
            if (type==2){
                if(myboard[i][j]=="F"){
                    myboard[i][j]="O";
                }else{
                    myboard[i][j]="F";
                }
                return true;
            }else if (type==1){
                int mines=ScanMines(i, j);
                if (mines>0){
                    myboard[i][j]=""+mines;
                    move--;
                }else{
                    myboard[i][j]="X";
                    move--;
                    Expand(i,j);
                }
                return true;
            }
        }
        return false;
    }

    public void play(){
        int i,j,type;
        Scanner scan = new Scanner(System.in);
        boolean playing=true;
        do{
            while(true){
                System.out.println("type : ( 1 : move | 2 : flag )");
                type=scan.nextInt();
                System.out.println("x : ");
                i=scan.nextInt();
                System.out.println("y : ");
                j=scan.nextInt();
                if (board[i][j]=="M" && type==1){
                    System.out.println("You lose！");
                    playing=false;
                    break;
                }
                if (Move(type,i,j)){
                    PrintBoard();
                    System.out.println("=====================");
                    PrintBoard2();
                    System.out.println("=====================");
                    break;
                }else{
                    System.out.println("Illegal move");
                }
            }
            if(move==0){
                System.out.println("You win！");
                break;
            }
        }while(playing);
        scan.close();
    }
    public static void main(String[] argv) {
        Scanner scan = new Scanner(System.in);
        int mine=scan.nextInt();
        int dim=scan.nextInt();
        MineSweeper m=new MineSweeper(mine, dim);
        m.play();
        scan.close();
    }
}
