package ai;


/**
 * Naive Agent, top election: just simple alpha-beta algorithm
 * @author 
 *
 */
public class Agent implements AI {
	
	private Evaluator evaluator = null;
	private BoardNode rootnode=null;
	private int depth = 6;
//	private MyStack stack = null;
	private int myClr;
//	private int a,b;/* the global variable for a alpha-beta tree*/
//	private int choice; /*for NextMove, can be only one of (0~6)*/
	
	public static final int GIVEUP = -2;
	public static final int CHESSBOARDFULL = -1;
	public static final int MINUS_INF = -999999;
	public static final int PSITV_INF = 999999;
	
	public Agent(Evaluator e, int clr){
		this.evaluator = e;
//		this.stack = new MyStack(this.depth);
		this.myClr = clr;
	}
	
	public Agent(Evaluator e, int d, int clr){
		this.evaluator = e;
		this.depth = d;
//		this.stack = new MyStack(this.depth);
		this.myClr = clr;
	}
	
	
	public Agent(Evaluator e, BoardNode r, int clr){
		this.evaluator = e;
		this.rootnode = r;
//		this.stack = new MyStack(this.depth);
		this.myClr = clr;
	}
	
	public Agent(Evaluator e, BoardNode r, int d, int clr){
		this.evaluator = e;
		this.rootnode = r;
		this.depth = d;
//		this.stack = new MyStack(this.depth);
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
		int a,b,i, maxvalue,tmpvalue;
		int choice;		/*for NextMove, can be only one of (0~6)*/
		BoardNode tmpnode;
		choice = -1;
		a = Agent.MINUS_INF;
		b = Agent.PSITV_INF;
		this.rootnode.isMAX=true;/*Set the start node MAX node*/
		maxvalue=Agent.MINUS_INF;
		for(i=0;i<7;i++)
		{
			if((tmpnode=this.rootnode.NewNode(i))==null)
				continue;
			tmpvalue=this.alpha_beta(tmpnode,a, b, this.depth-1);
			if(tmpvalue>=maxvalue){
				maxvalue=tmpvalue;
				choice=i;
			}
		}
		
		if(maxvalue<Agent.MINUS_INF+99){
			return Agent.GIVEUP;
		}
		if (choice<0)
			throw new java.lang.RuntimeException("invalid choice!");
		return choice;
	
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
	 * @return
	 */
	private int min(int b, int AB) {
		if(b<AB)
		{
			return b;
		}
		else{
			return AB;
		}
	}
}
