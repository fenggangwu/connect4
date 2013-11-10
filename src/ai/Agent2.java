package ai;


/**
 * Improved Agent.
 * Top election: loop deepening
 * @author Sepsky
 *
 */
public class Agent2 implements AI {
	
	private Evaluator evaluator = null;
	private BoardNode rootnode=null;
	private int depth = 6;
	private int myClr;	
	public static final int GIVEUP = -2;
	public static final int CHESSBOARDFULL = -1;
	public static final int MINUS_INF = -999999;
	public static final int PSITV_INF = 999999;
	
	public Agent2(Evaluator e, int clr){
		this.evaluator = e;
		this.myClr = clr;
	}
	
	public Agent2(Evaluator e, int d, int clr){
		this.evaluator = e;
		this.depth = d;
		this.myClr = clr;
	}
	
	
	public Agent2(Evaluator e, BoardNode r, int clr){
		this.evaluator = e;
		this.rootnode = r;
		this.myClr = clr;
	}
	
	public Agent2(Evaluator e, BoardNode r, int d, int clr){
		this.evaluator = e;
		this.rootnode = r;
		this.depth = d;
		this.myClr = clr;
	}
	
	public void SetRoot(BoardNode r){
		this.rootnode = r;
	}
	
	/**
	 * 
	 * @return if the chance to win is -inf, return Agent.GIVEUP(-1)
	 * 			else return the best column
	 * 			return -1 on error
	 */
	public int NextMove(){
		int a,b,i, maxvalue = Agent.MINUS_INF*1000,tmpvalue;
		int choice;		/*for NextMove, can be only one of (0~6)*/
		BoardNode tmpnode;
		choice = -1;
		a = Agent2.MINUS_INF;
		b = Agent2.PSITV_INF;
		this.rootnode.isMAX=true;/*Set the start node MAX node*/
		for(i=0;i<7;i++)
		{
			if((tmpnode=this.rootnode.NewNode(i))==null)
				continue;
			
			maxvalue=this.CalScore(tmpnode,a, b, this.depth-1);
			break;
		}
		if (i==7)
			return -1;
		for(;i<7;i++)
		{
			if((tmpnode=this.rootnode.NewNode(i))==null)
				continue;
			
			//Agent2: replace the simple alpha-beta function with CalScore.
			
			tmpvalue=this.CalScore(tmpnode,a, b, this.depth-1);
//			tmpvalue=this.alpha_beta(tmpnode,a, b, this.depth-1);

			if(tmpvalue>=maxvalue){
				maxvalue=tmpvalue;
				choice=i;
			}
		}
		

		if (choice<0)
			throw new java.lang.RuntimeException("invalid choice!");
		return choice;
	
	}

	//Agent2: Weighted sum of each depth.
	//more the depth, less the weight
	private int CalScore(BoardNode tmpnode, int a, int b, int d) {
		int tmpScore=0;
		for(int i=0;i<=d;i++)
			tmpScore+= this.alpha_beta(tmpnode, a, b, i)*10*(d-i+1)/(d+1);
		return tmpScore;
	}

	private int alpha_beta(BoardNode root,int a, int b, int d) {
		int i,var;
		BoardNode tmp;
			
		if(d==0){/*if reach the max depth, return the static value */

			var = this.evaluator.Evaluate(root, this.myClr);

			return var;
		}
		
		if (root.isMAX){/*for max node*/
			for(i=0;i<7;i++)
			{
				if((tmp=root.NewNode(i))==null)/*if this column is full, try the next node*/
					continue;
				var = alpha_beta(tmp,a, b, d-1);
				
				if(var>=a)
				{
					a = var;
				}
				
				if (a>=b)
					return b;
			}
			return a;
			
		}else			/*for min node*/
		{
			for(i=0;i<7;i++)
			{
				if((tmp=root.NewNode(i))==null)/*if this column is full, try the next node*/
					continue;
				b = min(b,alpha_beta(root.NewNode(i),a, b, d-1));
				if (b<=a)
					return a;
			}
			return b;
		}
		
	}

	
	/**
	 * Note the side-effect of recording the best choice
	 * @param a
	 * @param AB
	 * @param i
	 * @return
	 */
	private int max(int a, int AB) {
		if(a>AB)
		{
			return a;
		}else{
//			this.choice = i;/*remembering the best choice*/
			return AB;
		}
	}
	/**
	 * Note the side-effect of recording the best choice
	 * @param b
	 * @param AB
	 * @param i
	 * @return
	 */
	private int min(int b, int AB) {
		if(b<AB)
		{
			return b;
		}
		else{
//			this.choice = i;/*remembering the best choice*/
			return AB;
		}
	}
}
