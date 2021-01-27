package jp.tkm.reversi;

import java.util.HashMap;
import java.util.Scanner;

public class State {
    public static final int SIZE = 8;
    public static final int BLACK = 1;
    public static final int WHITE = -1;
    private final Board board;
    private final HashMap<Integer, String> hash = new HashMap<>();
    public static final String cols = "ABCDEFGH";

    State(){
        this("B", "W");
    }
    State(Board b){
        this(b, "B", "W");
    }
    State(String blackPlayer, String whitePlayer){
        hash.put(BLACK, blackPlayer);
        hash.put(WHITE, whitePlayer);
        board = new Board();
        show();
    }
    State(Board b, String blackPlayer, String whitePlayer){
        hash.put(BLACK, blackPlayer);
        hash.put(WHITE, whitePlayer);
        board = b;
        show();
    }
    public Board getBoard(){
        return board;
    }
    public void start(){
        int[] cor;
        boolean result;
        int passCount = 0;
        //全部のマスに置かれる->ゲームが終了するまでループは抜けない
        while(board.getSetCount() != SIZE * SIZE){
            System.out.println("Turn : " + hash.get(board.getPlayer()));
            System.out.print("Candidates: ");
            for(String str: board.getCandidates()){
                System.out.print(str + " ");
            }
            System.out.println();
            if(board.isSettable()){
                passCount = 0;
                do{
                    cor = input();
                    result = board.setPiece(cor[0], cor[1]);
                }while(!result);
            }
            else{
                passCount++;
                System.out.println("There are no space to place");
            }
            show();
            if(passCount == 2){
                System.out.println("Checkmate");
                break;
            }
            board.changePlayer();
        }
        board.count();
    }
    private int[] input(){
        int[] coordinate = new int[2];
        boolean status;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Please Input");
            String t = sc.nextLine();
            try{
                String[] input = t.split("");
                System.out.println(input[0]);
                coordinate[0] = Integer.parseInt(input[1]) - 1;
                coordinate[1] = cols.indexOf(input[0]);
                System.out.println(coordinate[0]+" "+coordinate[1]);
                status = true;
            }catch(Exception e){
                System.out.println("Wrong format. Please Input like 'E3'");
                status = false;
            }
        }while(!status);
        return coordinate;
    }
    private void show(){
        System.out.print("   |");
        for(char i : cols.toCharArray()){
            System.out.print(" " + i + " |");
        }
        System.out.println();

        for(int i = 0; i < SIZE; i++){
            System.out.print(" "+ (i+1) +" |");
            for(int j = 0; j < SIZE; j++){
                System.out.print(" " + (board.getPiece(i,j)==1 ? "B" : board.getPiece(i,j)==-1 ? "W" : 'N')+ " |");
            }
            System.out.println();
        }
    }
}
