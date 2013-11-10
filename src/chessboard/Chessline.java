package chessboard;


/**
 * Store the 4-in-row when one side of the player win the game
 * @author 
 *
 */
public class Chessline{
	int start_x;
	int start_y;
	int direction;
	int chess_num;
	int clr;
	
	public final static int LEFT = 0;
	public final static int DOWN = 1;
	public final static int DOWNLEFT = 2;
	public final static int DOWNRIGHT = 3;
	
	public Chessline(int s_x, int s_y, int dir, int num, int clr){
		this.start_x = s_x;
		this.start_y = s_y;
		this.direction = dir;
		this.chess_num = num;
		this.clr = clr;
	}
	
	public int[][] GetLine(){
		int line[][] = new int [4][2];
		int i;
		switch(this.direction){
		case Chessline.LEFT:
			for(i=this.chess_num-1;i>=0;i--){
				line[i][0] = this.start_x-i;
				line[i][1] = this.start_y;
			}
			break;
			
		case Chessline.DOWN:
			for(i=this.chess_num-1;i>=0;i--)
			{
				line[i][0] = this.start_x;
				line[i][1] = this.start_y-i;
			}
			break;
		case Chessline.DOWNLEFT:
			for(i=this.chess_num-1;i>=0;i--)
			{
				line[i][0] = this.start_x-i;
				line[i][1] = this.start_y-i;
			}
			break;
		
		case Chessline.DOWNRIGHT:
			for(i=this.chess_num-1;i>=0;i--)
			{
				line[i][0] = this.start_x+i;
				line[i][1] = this.start_y-i;
			}
			break;
			
			default:
				throw new java.lang.RuntimeException("The direction is illegal!");
		}
		
		return line;
		
	}
	
	public String toString(){
		int i;
		String tmp = "";
		tmp += this.clr==ChessBoard.RED?"RED":"BLUE";
		switch(this.direction){
		case Chessline.LEFT:
			for(i=this.chess_num-1;i>=0;i--){
				tmp+="("+(this.start_x-i)+","+this.start_y+")";
			}
			break;
			
		case Chessline.DOWN:
			for(i=this.chess_num-1;i>=0;i--)
			{
				tmp+="("+this.start_x+","+(this.start_y-i)+")";
			}
			break;
		case Chessline.DOWNLEFT:
			for(i=this.chess_num-1;i>=0;i--)
			{
				tmp+="("+(this.start_x-i)+","+(this.start_y-i)+")";
			}
			break;
		
		case Chessline.DOWNRIGHT:
			for(i=this.chess_num-1;i>=0;i--)
			{
				tmp+="("+(this.start_x+i)+","+(this.start_y-i)+")";
			}
			break;
			
			default:
				throw new java.lang.RuntimeException("The direction is illegal!");
		}
		
		return tmp;
	}
}

