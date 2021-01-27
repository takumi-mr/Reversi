package jp.tkm.reversi;

public class Main {
    public static void main(String[] args) {
        State s = new State();
        s.start();

        int WhiteCount = s.getBoard().getWhiteCount();
        int BlackCount = s.getBoard().getBlackCount();
        System.out.println("White : "+ WhiteCount);
        System.out.println("Black : "+ BlackCount);
        if(WhiteCount == BlackCount){
            System.out.println("draw");
        }
        else{
            System.out.print("Winner : ");
            System.out.print(WhiteCount >= BlackCount ? "White" : "Black");
        }
    }
}
