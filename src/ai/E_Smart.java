package ai;

import chessboard.ChessBoard;


//Smart evaluator, exclude the fake 3-in-a-row (OXXXO)
public class E_Smart implements Evaluator {
	BoardNode rootnode;
	int blue_value = 0;
	int red_value = 0;
	
	public E_Smart(BoardNode node){
		this.rootnode = node;
	}
	
	public E_Smart() {
	}

	@Override
	public int Evaluate(BoardNode node, int myclr) {
		int i,j,cnt,tmpclr;
		int current_clr = ChessBoard.EMPTY;
		this.red_value = 0;	/* for each static point, reset both the value*/
		this.blue_value = 0;
		
		/*initialize*/
		if(node!=null)
			this.rootnode = node;
		if(this.rootnode==null)
			throw new java.lang.RuntimeException("cannot evaluate null node!");

		
		
		/*horizontal*/
		cnt=0;
		for(j=0;j<6;j++){
			cnt=0;
			for(i=0;i<7;i++)
			{
				tmpclr = node.Get_clr(i,j);
				if(tmpclr==ChessBoard.EMPTY)
				{
					cnt=0;
					continue;
				}
				
				if(tmpclr==current_clr){
					if(++cnt==3&&(node.Get_clr(i-3, j)==ChessBoard.EMPTY || node.Get_clr(i+1, j)==ChessBoard.EMPTY)){
						if(current_clr==ChessBoard.RED)
							red_value++;
						else if(current_clr==ChessBoard.BLUE)
							blue_value++;
						else
							throw new java.lang.RuntimeException("inappropriate color!");
						
					}
					if(cnt>=4){
						if(current_clr==ChessBoard.RED)
							red_value=Agent.PSITV_INF;
						else if(current_clr==ChessBoard.BLUE)
							blue_value=Agent.PSITV_INF;
						else
							throw new java.lang.RuntimeException("inappropriate color!");
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
				tmpclr = node.Get_clr(i,j);
				if(tmpclr==ChessBoard.EMPTY)
				{
					cnt=0;
					continue;
				}
				
				if(tmpclr==current_clr){
					if(++cnt==3&&(node.Get_clr(i, j-3)==ChessBoard.EMPTY || node.Get_clr(i, j+1)==ChessBoard.EMPTY)){
						if(current_clr==ChessBoard.RED)
							red_value++;
						else if(current_clr==ChessBoard.BLUE)
							blue_value++;
						else
							throw new java.lang.RuntimeException("inappropriate color!");
						
					}
					if(cnt>=4){
						if(current_clr==ChessBoard.RED)
							red_value=Agent.PSITV_INF;
						else if(current_clr==ChessBoard.BLUE)
							blue_value=Agent.PSITV_INF;
						else
							throw new java.lang.RuntimeException("inappropriate color!");
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
				tmpclr = node.Get_clr(i+j,j);
				if(tmpclr==ChessBoard.EMPTY)
				{
					cnt=0;
					continue;
				}
				
				if(tmpclr==current_clr){
					if(++cnt==3&&(node.Get_clr(i+j-3, j)==ChessBoard.EMPTY || node.Get_clr(i+j+1,j)==ChessBoard.EMPTY)){
						if(current_clr==ChessBoard.RED)
							red_value++;
						else if(current_clr==ChessBoard.BLUE)
							blue_value++;
						else
							throw new java.lang.RuntimeException("inappropriate color!");
						
					}
					if(cnt>=4){
						if(current_clr==ChessBoard.RED)
							red_value=Agent.PSITV_INF;
						else if(current_clr==ChessBoard.BLUE)
							blue_value=Agent.PSITV_INF;
						else
							throw new java.lang.RuntimeException("inappropriate color!");
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
				tmpclr = node.Get_clr(i-j,j);
				if(tmpclr==ChessBoard.EMPTY)
				{
					cnt=0;
					continue;
				}
				
				if(tmpclr==current_clr){
					if(++cnt==3&&(node.Get_clr(i-j+3, j)==ChessBoard.EMPTY || node.Get_clr(i-j-1,j)==ChessBoard.EMPTY)){
						if(current_clr==ChessBoard.RED)
							red_value++;
						else if(current_clr==ChessBoard.BLUE)
							blue_value++;
						else
							throw new java.lang.RuntimeException("inappropriate color!");
						
					}
					if(cnt>=4){
						if(current_clr==ChessBoard.RED)
							red_value=Agent.PSITV_INF;
						else if(current_clr==ChessBoard.BLUE)
							blue_value=Agent.PSITV_INF;
						else
							throw new java.lang.RuntimeException("inappropriate color!");
					}
				}else
				{
					cnt=1;
					current_clr = tmpclr;					
				}
			}
		}
//		System.out.println("in E_Naive:red value: "+this.red_value+", blue value: "+this.blue_value);
//		System.out.println("in E_Naive:static:="+(myclr==ChessBoard2.RED?
//				(this.red_value-this.blue_value):(this.blue_value-this.red_value)));
		
		return myclr==ChessBoard.RED?
				(this.red_value-this.blue_value):(this.blue_value-this.red_value);
	}

}
