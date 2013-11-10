package ai;
import chessboard.ChessBoard;


/**
 * Record the chessboard state, which can be evaluate by the Evaluator
 * @author Sepsky
 *
 */
public class BoardNode extends chessboard.Board{
	
	short board[]= new short[6]; 
	int value;
	BoardNode child[] = new BoardNode[7];
	boolean isMAX = false;/*default: start point is MIN node*/
	public int turn;  /* can be only BLUE or RED */
	public int new_y = -1;
	
	public BoardNode() {
		int i;
		for(i=0;i<6;i++)
		{
			this.board[i] = 0;
		}
		this.value = 0;
		for(i=0;i<7;i++)
		{
			child[i] = null;
		}
	}
	public BoardNode(BoardNode n, boolean ismax){
		int i;
		for(i=0;i<6;i++)
			this.board[i] = n.board[i];
		this.value = 0;
		for(i=0;i<7;i++){
			this.child[i]=null;
		}
		this.turn = n.turn;
		this.isMAX = ismax;
	}
	@Override
	public int CheckWin() {
		// this class is only used to record the chess board state. 
		// Chess board over which AI and Human really plays is 
		//            implemented by chessboard.ChessBoard 
		return 0;
	}

	public  void Set_clr(int x, int y, int clr) {
		if(x<0||x>6||y<0||y>5)
			throw new java.lang.RuntimeException("incoming x,y ("+x+","+y+") is illegal!");
		switch(clr){
		case ChessBoard.RED:
			this.board[y]|=(1<<2*x);
			this.board[y]&=(~(1<<2*x+1));
			break;
			
		case ChessBoard.BLUE:
			this.board[y]&=(~(1<<2*x));
			this.board[y]|=(1<<(2*x+1));
			break;
			
		case ChessBoard.EMPTY:
			this.board[y]&=(~(1<<2*x));
			this.board[y]&=(~(1<<2*x+1));
			break;
			
			default:
				throw new java.lang.RuntimeException("incoming colour is illegal!");
		}
		
	}
	
	public int  Get_clr(int x, int y)
	{
		if(x<0||x>6||y<0||y>5)
			return ChessBoard.OUT;
		short tmp = this.board[y];
		int a0 = ((tmp&(1<<2*x))==0)?0:1;
		int a1 = ((tmp&(1<<(2*x+1)))==0)?0:1;
		switch (a1*2+a0){
		case 0:
			return ChessBoard.EMPTY;
		case 1:
			return ChessBoard.RED;
		case 2:
			return ChessBoard.BLUE;
			default:
				throw new java.lang.RuntimeException("The color in ("+x+","+y+") is illegal!");
		}
	}
	
	public boolean isMax(){
		return this.isMAX;
	}

	//give a new chess which drop in column 'col', return new node of state 
	public BoardNode NewNode(int col){
		BoardNode n = new BoardNode(this, !this.isMAX);
		if(n.dropstone(col))
			return n;
		return null;
	}
	//try to drop a new chess on column 'col'
	private boolean dropstone(int col) {
		int i;
		for(i=0;i<6;i++){
			if (this.Get_clr(col, i)==ChessBoard.EMPTY)
			{
				this.Set_clr(col, i, this.turn);
				this.new_y = i;
				this.turn = this.turn==ChessBoard.BLUE?ChessBoard.RED:ChessBoard.BLUE;
				return true;
			}
		}
		return false;
	}
}
