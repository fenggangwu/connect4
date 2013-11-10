package chessboard;

import gui.GUI;
import ai.*;

public class MainConnect4 {
	BoardNode bnode = new BoardNode();
	
	public static void main(String argv[]){
		GUI gui = new GUI();
		ChessBoard b = new ChessBoard(ChessBoard.RED,gui);
		gui.ShowAll();

		MyStdin stdin = new MyStdin();
		
//		Evaluator e = new E_Naive();
		Evaluator e = new E_Smart();
//		AI ai = new Agent3(e,6, ChessBoard.BLUE);
		AI ai = new Agent3(e,6, ChessBoard.BLUE);

		AI ai2 = new Agent2(e,2, ChessBoard.RED);
		
		
		Player player_red;
		Player player_blue;
		Thread t1;
		Thread t2;
		
		
		//Red player get next drop from stdin when debug.
		//While in the final version, the red player get the input from the clicks from gui
		player_red = new Player(ChessBoard.RED, b,gui);
//		player_red = new Player(ChessBoard.RED, b,stdin);
//		player_blue = new Player(ChessBoard.BLUE, b,gui);
		player_blue = new Player(ChessBoard.BLUE, b,ai);
		
		System.out.println("looping starts");
		//loop forever
		while(true){
			t1= new Thread(player_red);
			t2= new Thread(player_blue);

			t1.start();
			t2.start();
			try {
				t1.join();
				t2.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			//game over when both the thread exit
			System.out.println("Game Over, both thread exit");
			gui.WaitInit();
			//exchange the first drop at a new game
			b.NewGame();
			

		}
	}
}
