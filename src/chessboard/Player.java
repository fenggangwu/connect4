package chessboard;



import gui.GUI;
import ai.AI;
import ai.Agent;

public class Player implements Runnable{
	private int myclr;
	private ChessBoard b = null;
	private MyStdin stdin = null;
	private AI ai = null;
	private GUI gui = null;
	public Player(int clr, ChessBoard bb, MyStdin s)
	{
		this.myclr = clr;
		this.b = bb;
		this.stdin = s;
	}
	public Player(int clr, ChessBoard bb, AI a) {
		this.myclr = clr;
		this.b = bb;
		this.ai = a;
	}
	public Player(int clr, ChessBoard bb, GUI g) {
		this.myclr = clr;
		this.b = bb;
		this.gui = g;
	}
	@Override
	public void run() {
		String tmp = this.myclr==ChessBoard.BLUE?"BLUE":"RED";
		while(b.win==ChessBoard.UNFININSHED){
			
			if(this.stdin!=null){/* player get input from the console */
				b.PutStone(new Stone(this.myclr,stdin));
			}
			else if(this.ai!=null)/* player get input from ai */
			{
				ai.SetRoot(b.bnode);
				b.PutStone(new Stone(this.myclr,this.ai));		
			}else if(this.gui!=null){
				b.PutStone(new Stone(this.myclr, this.gui));
			}	
			else
				throw new java.lang.RuntimeException("cannot get input!");
		}
	
	}

}
