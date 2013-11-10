package chessboard;

import gui.GUI;
import ai.Agent;
import ai.BoardNode;


public class ChessBoard extends Board{
	public final static int RED = 1;
	public final static int BLUE = 2;
	public final static int EMPTY = 0;
	public final static int FULL = -1;
	public static final int OUT = 3;
	
	public final static int REDWIN = RED;
	public final static int BLUEWIN = BLUE;
	public final static int DRAW = 3;
	public final static int UNFININSHED = 0;
	public final static int RESTART = -3;
	public  long time_consumed = 0;
//	public static int total_stone = 0;

	
	Chessline winline = null;	
	public int win = ChessBoard.UNFININSHED;
	
	BoardNode bnode;
	GUI gui = null;
	
	int last_beginning_player;
	/* for each column, the would-be y-value of the next stone.-1 if the column is full*/
	int []nexty = new int [7]; 
	/**
	 * @param t who first drop the stone
	 * 
	 * 
	 * */
	public ChessBoard(int t){
		
		int i;
		
		for(i=0;i<7;i++)
		{
			this.nexty[i] = 0;
			
		}
		this.bnode = new BoardNode();
		this.bnode.turn= this.last_beginning_player = this.bnode.turn =  t;
		System.out.println("New Game!");
		System.out.println((this.bnode.turn==ChessBoard.BLUE?"BLUE":"RED")+"'s turn");
	}
	
	public ChessBoard(int t,GUI g){
		
		int i;
		
		for(i=0;i<7;i++)
		{
			this.nexty[i] = 0;
			
		}
		this.gui = g;
		this.gui.SetBoard(this);
		this.bnode = new BoardNode();
		this.bnode.turn= this.last_beginning_player = this.bnode.turn =  t;
		System.out.println("New Game!");
		System.out.println((this.bnode.turn==ChessBoard.BLUE?"BLUE":"RED")+"'s turn");
	}
	
	/**
	 * @param s the stone obj
	 * @return the y-value of the newly dropped stone, or -1 when game over
	 */
	public synchronized int  PutStone(Stone s){
		int new_y;
		int clr = s.clr;		
		int x;
		
//		String tmp = s.clr==ChessBoard.BLUE?"BLUE":"RED";
//		System.out.println(tmp+"\tflag4");
		long time1 = System.currentTimeMillis(),time2;
		if(clr!=this.bnode.turn){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		System.out.println(tmp+"\tflag5, win = "+this.win);
		x = s.Getx();
		if(x<0){
			time2 =  System.currentTimeMillis();
			this.time_consumed += time2-time1;
			return -1;
		}
//		System.out.println(tmp+"\tx = "+x);
		if(this.win!=ChessBoard.UNFININSHED){
			time2 =  System.currentTimeMillis();
			this.time_consumed += time2-time1;
			this.notify();
			
//			System.out.println(tmp+"\tnotify successfully");
			return -1;
		}
		
		if(s.isAi()&&x==Agent.GIVEUP)
		{
			this.win = s.clr==ChessBoard.BLUE?ChessBoard.BLUE:ChessBoard.RED;
			
			System.out.println("AI("+s.clr+") GiveUP");
			System.out.println(s.clr==RED?"BLUE":"RED"+" win!");
			time2 =  System.currentTimeMillis();
			this.time_consumed += time2-time1;
			return 0;
		}
		
		if(s.isDorpped()||/*x<0||x>6||*/(clr!=this.bnode.turn&&clr!=ChessBoard.EMPTY)){
			System.out.println(s.isDorpped());
			System.out.println(s.y);
			time2 =  System.currentTimeMillis();
			this.time_consumed += time2-time1;
			return -1;
//			throw new java.lang.RuntimeException("The input stone is illegal!");
		}

		if(s.isGUI() && this.win==GUI.RESTART){
			time2 =  System.currentTimeMillis();
			this.time_consumed += time2-time1;
			this.notify();
			return -1;
		}
		
		if(this.nexty[x]==ChessBoard.FULL){
			System.out.println("Column "+x+" is full!\n");
			time2 =  System.currentTimeMillis();
			this.time_consumed += time2-time1;
			return -1;
//			throw new java.lang.RuntimeException("Column "+x+" is full!");
		}
			
		
		new_y = s.Sety(this.nexty[x]);
		if(++this.nexty[x]>5)
			this.nexty[x]=ChessBoard.FULL;
//		this.bnode.Set_clr(x, s.Gety(), this.bnode.turn);
		this.Set_clr(x, s.Gety(), this.bnode.turn);
		this.total_stone ++;
		this.bnode.turn= this.bnode.turn==ChessBoard.RED?BLUE:RED;
		this.PrintBoard();
		if(this.win==ChessBoard.RESTART){
			time2 =  System.currentTimeMillis();
			this.time_consumed += time2-time1;
			this.notify();
			return -1;
		}
		this.win = this.CheckWin();
		if(this.win!=ChessBoard.UNFININSHED)
		{
			if(this.win==ChessBoard.RED)
				System.out.println("RED Win!\n"+"the 4-row:\n"+this.winline.toString()+"\n");
			else if(this.win==ChessBoard.BLUE)
				System.out.println("BLUE Win!\n"+"the 4-row:\n"+this.winline.toString()+"\n");
			else
				System.out.println("This is a draw!\n");
			System.out.println("time = "+this.time_consumed);
			System.out.println("total stone = "+this.total_stone);
			System.out.println("avg time per stone: "+this.time_consumed/this.total_stone);
			
		}
		if(this.win==ChessBoard.UNFININSHED){
			System.out.println();
			System.out.println(this.bnode.turn==ChessBoard.RED?"RED's turn":"BLUE's turn");
		}
		time2 =  System.currentTimeMillis();
		this.time_consumed += time2-time1;
		this.notify();
		
		return new_y;
	}

	public int NewGame(){
		System.out.println("NewGame Called!!");
		int i;
		for(i=0;i<7;i++)
		{
			this.nexty[i] = 0;
		}
		this.bnode = new BoardNode();
		this.total_stone=0;
		this.win = ChessBoard.UNFININSHED;
		this.winline = null;
		this.bnode.turn= this.last_beginning_player = this.last_beginning_player==ChessBoard.RED?BLUE:RED;
		System.out.println("New Game!");
		System.out.println((this.bnode.turn==ChessBoard.BLUE?"BLUE":"RED")+"'s turn");
		this.gui.ShowNewBoard(this.bnode.turn);
		this.time_consumed = 0;
	
		return this.bnode.turn;
		
	}
	
	public int CheckWin(){
		int i,j,cnt,tmpclr;
		int current_clr = ChessBoard.EMPTY;
		
		/*horizontal*/
		cnt=0;
		for(j=0;j<6;j++){
			cnt=0;
			for(i=0;i<7;i++)
			{
				tmpclr = Get_clr(i,j);
				if(tmpclr==ChessBoard.EMPTY)
				{
					cnt=0;
					continue;
				}
				
				if(tmpclr==current_clr){
					if(++cnt==4){
						this.winline = new Chessline(i, j, Chessline.LEFT, 4, current_clr);
						this.gui.ShowWin(current_clr, this.winline);
						return current_clr;
					}
				}else
				{
					cnt=1;
					current_clr = tmpclr;					
				}
			}
		}
		/*vertical*/
		cnt=0;
		for(i=0;i<7;i++){
			cnt=0;
			for(j=0;j<6;j++)
			{
				tmpclr = Get_clr(i,j);
				if(tmpclr==ChessBoard.EMPTY)
				{
					cnt=0;
					continue;
				}
				
				if(tmpclr==current_clr){
					if(++cnt==4){
						this.winline = new Chessline(i, j, Chessline.DOWN, 4, current_clr);
						this.gui.ShowWin(current_clr, this.winline);
						return current_clr;
					}
				}else
				{
					cnt=1;
					current_clr = tmpclr;					
				}
			}
		}
		/*k=1*/
		cnt=0;
		for(i=-2;i<4;i++){
			cnt=0;
			for(j=0;j<6;j++)
			{
				if(i+j<0||i+j>6)
					continue;
				tmpclr = Get_clr(i+j,j);
				if(tmpclr==ChessBoard.EMPTY)
				{
					cnt=0;
					continue;
				}
				
				if(tmpclr==current_clr){
					if(++cnt==4){
						this.winline = new Chessline(i+j, j, Chessline.DOWNLEFT, 4, current_clr);
						this.gui.ShowWin(current_clr,this.winline);
						return current_clr;
					}
				}else
				{
					cnt=1;
					current_clr = tmpclr;					
				}
			}
		}
		
		/*k=-1*/
		cnt=0;
		for(i=3;i<9;i++){
			cnt=0;
			for(j=0;j<6;j++)
			{
				if(i-j<0||i-j>6)
					continue;
				tmpclr = Get_clr(i-j,j);
				if(tmpclr==ChessBoard.EMPTY)
				{
					cnt=0;
					continue;
				}
				
				if(tmpclr==current_clr){
					if(++cnt==4){
						this.winline = new Chessline(i-j, j, Chessline.DOWNRIGHT, 4, current_clr);
						this.gui.ShowWin(current_clr,this.winline);
						return current_clr;
					}
				}else
				{
					cnt=1;
					current_clr = tmpclr;					
				}
			}
		}
		if (this.total_stone==42){
			this.gui.ShowDraw();
			return ChessBoard.DRAW;
		}
		return ChessBoard.UNFININSHED; /* game is going on */ 
	}
	
	public void PrintBoard()
	{
		int i,j,tmpclr;
		String tmp = "";
		

		for(j=5;j>=0;j--){
			for(i=0;i<7;i++){
				tmpclr = this.Get_clr(i, j);
				switch(tmpclr)
				{
				case ChessBoard.RED:
					tmp+="O";break;
				case ChessBoard.BLUE:
					tmp+="X";break;
				case ChessBoard.EMPTY:
					tmp+="-";break;
					
					default:
						throw new java.lang.RuntimeException("The colour is illegal!");
				}
			}
			tmp+="\n";	
		}
		
//		tmp += "\n";
		System.out.println(tmp);

	}
	
	private int Get_clr(int i, int j) {
		return this.bnode.Get_clr(i, j);
	}
	
	private void Set_clr(int i, int j, int clr){
		this.bnode.Set_clr(i, j, clr);
		this.gui.Set_clr(i,j,clr);
	}

	public int getTurn() {
		return this.bnode.turn;
	}
	public int getTotalStone(){
		return this.total_stone;
	}
}

