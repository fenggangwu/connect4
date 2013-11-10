package chessboard;


/**
 * Board class
 * Implemented by ai.BoardNode and chessboard.ChessBoard
 * @author 
 *
 */

abstract public class Board {

	/**
	 * @param t who first drop the stone
	 * 
	 * 
	 * */
	public int total_stone=0;
	public abstract int CheckWin();
//	public int turn;  /* can be only BLUE or RED */
	
}
