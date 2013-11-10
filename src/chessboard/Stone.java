package chessboard;

import gui.GUI;
import ai.AI;

/**
 * The chess
 * @author 
 *
 */
public class Stone {
	int x=-1;
	int y=-1;
	int clr=-1;
	MyStdin stdin=null;
	AI ai = null;
	GUI gui = null;
	
	public Stone(int xx)
	{
		this.x = xx;
		this.y = -1;
		this.clr = ChessBoard.EMPTY;
//		System.out.println("Constru:"+this.toString());
	}
	/**
	 * Constructor, when the y-value of a stone is unknown
	 * 
	 */
	public Stone(int turn, int xx)
	{
		this.clr = turn;
		this.x = xx;
		this.y = -1;
	}
	
	public Stone(int turn ,int xx, int yy)
	{
		this.clr = turn;
		this.x = xx;
		this.y = yy;
	}
	
	public Stone(int myclr, MyStdin stdin) {
		this.clr = myclr;
		this.stdin = stdin;
		this.x = -1;
		this.y = -1;
	}
	public Stone(int myclr, AI a) {
		this.clr = myclr;
		this.ai = a;
	}
	public Stone(int myclr, GUI g) {
		this.clr = myclr;
		this.gui = g;
	}
	
	/*the x value can be got from gui, console window, and ai
	The final version of the homework is a game between gui and ai*/
	public int Getx()
	{
		System.out.println("Stone.Getx(), this.x="+this.x);
		
		if(this.x==-1){
			if(this.stdin!=null){
				System.out.println("Stone.Getx() stdin");
				this.x = stdin.nextInt();
			}
			else if(this.ai!=null){
				System.out.println("Stone.Getx() ai");
				this.x = this.ai.NextMove();
			}else if (this.gui!=null){
				System.out.println("Stone.Getx() gui");
				this.x = this.gui.getX();
			}else
				throw new java.lang.RuntimeException("cannot get input x!");
		}
	
		return this.x;
	}
	
	public int Gety()
	{
		return this.y;
	}
	
	public int GetClr()
	{
		return this.clr;
	}
	
	public int Sety(int yy){
		this.y = yy;
		return this.y;
	}
	
	/**
	 * to check whether a stone is dropped in to the board 
	 * only when a stone is dropped into the board can the y-value be determined
	 */
	public boolean isDorpped(){
		return (!(y==-1));
	}
	
	public boolean isAi(){
		return this.ai!=null;
	}
	
	public boolean isGUI(){
		return this.gui!=null;
	}
	
	public String toString(){
		return "Stone:("+x+","+y+","+this.clr+")";
	}

}
