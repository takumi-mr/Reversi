package jp.tkm.reversi;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int SIZE = 8;
    public static final int BLACK = 1;
    public static final int WHITE = -1;
    protected int Player;
    protected int setCount;
    public int WhiteCount = 0;
    public int BlackCount = 0;

    protected int[][] board;
    //clock wise
    protected static int[][] vec = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
    protected List<Integer> tmp = new ArrayList<>();

    protected List<String> candidates = new ArrayList<>();
    public static final String cols = "ABCDEFGH";
    boolean settable = false;

    Board(){
        board = new int[SIZE][SIZE];
        //Initial setup
        board[SIZE/2 - 1][SIZE/2] = WHITE;
        board[SIZE/2][SIZE/2 - 1] = WHITE;
        board[SIZE/2 - 1][SIZE/2 - 1] = BLACK;
        board[SIZE/2][SIZE/2] = BLACK;
        Player = BLACK;
        count();
        setCount = WhiteCount+BlackCount;
    }
    Board(Board b, int player){
        this.board = b.board;
        Player = player;
        count();
        setCount = WhiteCount+BlackCount;
    }
    public int getWhiteCount() {
        return WhiteCount;
    }
    public int getBlackCount() {
        return BlackCount;
    }
    public int getSetCount(){
        return setCount;
    }
    public int getPiece(int row, int col){
        return board[row][col];
    }
    public String[] getCandidates(){
        tmp.clear();
        candidates.clear();
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(board[i][j] != 0){
                    continue;
                }
                check(i,j);
                if(tmp.size() != 0){
                    candidates.add(String.valueOf(cols.charAt(j)) + (i+1));
                    settable = true;
                }
            }
        }
        return candidates.toArray(new String[0]);
    }
    public boolean isSettable(){
        return settable;
    }
    public int getPlayer(){
        return Player;
    }
    public void changePlayer(){
        Player *= -1;
    }
    public void count(){
        //コマの数を数える
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(getPiece(i,j) == BLACK){
                    BlackCount++;
                }
                if(getPiece(i,j) == WHITE){
                    WhiteCount++;
                }
            }
        }
    }
    public boolean setPiece(int row, int column){
        if (!settablePiece(row, column)){
            System.out.println("Can't set your Piece");
            return false;
        }
        board[row][column] = Player;
        setCount++;
        revert(row, column);
        return true;
    }
    private void revert(int row, int column){
        for (int i : tmp) {
            int j = 0;
            do {
                j++;
                board[row + j*vec[i][0]][column + j*vec[i][1]] *= -1;
            }while(board[row + (j+1)*vec[i][0]][column + (j+1)*vec[i][1]] == Player * -1);
        }
    }
    private boolean settablePiece(int row, int column){
        //範囲内かどうか
        if(row >= SIZE || column >= SIZE || row < 0 || column < 0){
            System.out.println("Out of range");
            return false;
        }
        //置かれていないかどうか
        else if(board[row][column] != 0){
            System.out.println("Already placed");
            return false;
        }
        //置けるマスかどうか
        check(row, column);
        if(tmp.size() == 0){
            System.out.println("Can't place this cell");
            return false;
        }
        return true;
    }
    private void check(int row, int column){
        tmp.clear();
        for (int i = 0; i < SIZE; i++) {
            try{
                if (board[row + vec[i][0]][column + vec[i][1]] == Player * -1) {
                    for(int j = 0; j < 8; j++){
                        if(board[row + j*vec[i][0]][column + j*vec[i][1]] == Player){
                            tmp.add(i);
                            break;
                        }
                    }
                }
            }catch(Exception ignored) {
            }
        }
    }
}
