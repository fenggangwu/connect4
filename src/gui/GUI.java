package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import chessboard.ChessBoard;
import chessboard.Chessline;
import chessboard.Player;

public class GUI implements ActionListener,MouseListener {
	
	static final int AI = 0;
	static final int Human = 1;
	public static final int RESTART = -3;
	
	final List<Integer> holder = new LinkedList<Integer>();
	
	private ChessBoard board;
	
	private JPanel mainPanel;
	private JFrame mainFrame;
	private int x;                      
	private int y;
	private int bluePlayer;
	private int redPlayer;
	private int first;
	private boolean click;
	private JPanel[][] points = new JPanel[7][7];
	private JButton[][] buttons = new JButton[7][7];
	JButton bRestart;
	JButton bStop;
	private boolean right_click;
	private boolean left_click;
	private boolean init_click;
	Icon empty,mine,question,flag,wrong,explosion,b_click1,b_click2,b_click3,b_click4;
	
	private JRadioButton blueAI;
	private JRadioButton blueHuman;
	private ButtonGroup blueGroup;
	
	private JRadioButton redAI;
	private JRadioButton redHuman;
	private ButtonGroup redGroup;
	
	private JRadioButton red_first;
	private JRadioButton blue_first;
	private ButtonGroup first_group;
	
	private JButton ai_config_red;
	private JButton ai_config_blue;

/*////////////////////////////////////////////////
 * 主函数，显示图形接口
 *////////////////////////////////////////////////

	public static void main(String[] args)
	{
		System.out.println("good job!");
		GUI mygame = new GUI();
		
		mygame.ShowAll();
		
	}
	
	/*
	 * 构造函数
	 * 
	 */
	
	public GUI()
	{
		this.x = 0;
		this.y = 0;
		this.click = false;
		this.right_click = false;
		
		this.mine =new ImageIcon("./images/icons/blue.gif"); 
		this.flag=new ImageIcon("./images/icons/red.gif"); 
		this.empty = new ImageIcon("./images/icons/empty.gif");

		this.mainFrame = new JFrame("WuFG's Connect-4");
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setSize(280,350);
		this.mainFrame.setResizable(false);
//		this.mainFrame.pack();
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BorderLayout());
		this.mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JPanel controlPanel = new JPanel();
		controlPanel.setBorder(BorderFactory.createEtchedBorder());
		JPanel mapPanel = new JPanel();
		mapPanel.setBorder(BorderFactory.createEtchedBorder());
		
		this.mainPanel.add(controlPanel,BorderLayout.SOUTH);
		this.mainPanel.add(mapPanel,BorderLayout.CENTER);
				
		mapPanel.setLayout(new GridLayout(7,7));
		
		this.points = new JPanel[7][7];
		this.buttons = new JButton[9][9];
		int i = 0,j = 0;
		for(j=6;j>=0;j--)
		{
			for(i = 0;i<7;i++)
			{
				this.points[i][j] = new JPanel();
//				this.points[i][j].setBorder(BorderFactory.createEtchedBorder());
				
				this.points[i][j].setLayout(new GridLayout(1,1));
				
				this.buttons[i][j] = new JButton();
				this.buttons[i][j].setIcon(empty);
//				this.buttons[i][j].setBorderPainted(false);
				
				this.buttons[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				
				this.buttons[i][j].setBorderPainted(false);

				//this.buttons[i][j].setVisible(false);
				this.buttons[i][j].setContentAreaFilled(false);
				this.buttons[i][j].addActionListener(this);

				this.buttons[i][j].addMouseListener(this);
				if(j!=6)
					this.points[i][j].add(this.buttons[i][j]);

				mapPanel.add(this.points[i][j]);

			}
		}


		

		
		this.bRestart = new JButton("Click the Board to Play");
		this.bStop = new JButton("Stop!");
		this.bRestart.setEnabled(false);
		JPanel btnPanel = new JPanel();
		btnPanel.add(this.bRestart);
//		btnPanel.add(this.bStop);
		btnPanel.setBorder(BorderFactory.createEtchedBorder());
		
	
		this.first_group = new ButtonGroup();
		
		JLabel firstLabel = new JLabel("Who drop first?");
		this.red_first = new JRadioButton("RED");
		this.blue_first = new JRadioButton("BLUE");
		this.first_group.add(this.red_first);
		this.first_group.add(this.blue_first);
		ButtonModel bmdl_first =new JToggleButton.ToggleButtonModel();
		bmdl_first.setGroup(this.first_group);
		this.first = ChessBoard.RED;
		this.red_first.setSelected(true);
		JPanel firstPanel = new JPanel();
		firstPanel.setBorder(BorderFactory.createEtchedBorder());
		firstPanel.setLayout(new BorderLayout());
		firstPanel.add(firstLabel, BorderLayout.NORTH);
		firstPanel.add(this.red_first, BorderLayout.WEST);
		firstPanel.add(this.blue_first, BorderLayout.EAST);
		
		this.blueGroup = new ButtonGroup();
		ButtonModel bmdl_blue =new JToggleButton.ToggleButtonModel();
		bmdl_blue.setGroup(this.blueGroup);
		this.blueAI	= new JRadioButton("AI");
		this.blueHuman = new JRadioButton("Human");
		this.blueGroup.add(this.blueAI);
		this.blueGroup.add(this.blueHuman);
		this.ai_config_blue = new JButton("config");
//		JLabel bluep = new JLabel("BLUE Player");
		this.bluePlayer = GUI.AI;
		this.blueAI.setSelected(true);
		JPanel bluePanel = new JPanel();
		bluePanel.setBorder(BorderFactory.createEtchedBorder());
		bluePanel.setLayout(new BorderLayout());
		bluePanel.add(this.blueHuman, BorderLayout.NORTH);
		bluePanel.add(this.blueAI, BorderLayout.CENTER);
		bluePanel.add(this.ai_config_blue, BorderLayout.SOUTH);
		
		this.redGroup = new ButtonGroup();
		ButtonModel bmdl_red =new JToggleButton.ToggleButtonModel();
		bmdl_red.setGroup(this.redGroup);
		this.redAI	= new JRadioButton("AI");
		this.redHuman = new JRadioButton("Human");
		this.redGroup.add(this.redAI);
		this.redGroup.add(this.redHuman);
		this.ai_config_red = new JButton("config");
		this.redPlayer = GUI.Human;
		this.redHuman.setSelected(true);
		this.ai_config_red.setEnabled(false);

		JPanel redPanel = new JPanel();
		redPanel.setBorder(BorderFactory.createEtchedBorder());
		redPanel.setLayout(new BorderLayout());
		redPanel.add(this.redHuman, BorderLayout.NORTH);
		redPanel.add(this.redAI, BorderLayout.CENTER);
		redPanel.add(this.ai_config_red, BorderLayout.SOUTH);
		
//		controlPanel.setLayout(new BorderLayout());
//		controlPanel.add(firstPanel, BorderLayout.NORTH);
		controlPanel.add(btnPanel,BorderLayout.SOUTH);
//		controlPanel.add(redPanel, BorderLayout.WEST);
//		controlPanel.add(bluePanel,BorderLayout.EAST);
		
		this.bRestart.addActionListener(this);
		this.bStop.addActionListener(this);
		this.blueAI.addActionListener(this);
		this.blueHuman.addActionListener(this);
		this.redAI.addActionListener(this);
		this.redHuman.addActionListener(this);
		this.red_first.addActionListener(this);
		this.blue_first.addActionListener(this);
		this.ai_config_blue.addActionListener(this);
		this.ai_config_red.addActionListener(this);

		this.mainFrame.getContentPane().add(this.mainPanel);

	}
	
	@SuppressWarnings("deprecation")
	public void ShowAll()
	{
		this.mainFrame.show();
	}
	////////////////////////////////////////////////////////////
	//以下是与算法接口的函数
	//
	////////////////////////////////////////////////////////////
	
	public void Set_clr(int i, int j, int clr) {
		switch(clr){
		case ChessBoard.BLUE:
			this.SetBlue(i, j);
			break;
		case ChessBoard.RED:
			this.SetRed(i, j);
			break;
			default:
				throw new java.lang.RuntimeException("invalid clor");
		
		}
		
	}

	
	private void SetRed(int i, int j) {
		this.buttons[i][j].setIcon(this.flag);
		
	}

	private void SetBlue(int i, int j) {
		this.buttons[i][j].setIcon(this.mine);
		
	}

	void ShowUnKnown(int x,int y)
	{

		this.buttons[x][y].setEnabled(true);
		this.buttons[x][y].setBorderPainted(true);
		this.buttons[x][y].setIcon(null);
	}
	
	void ShowSafe(int x,int y)             //显示点(x,y)周围无雷
	{
		this.buttons[x][y].setEnabled(false);
		this.buttons[x][y].setBorderPainted(false);
		this.buttons[x][y].setIcon(null);

	}

	void ShowMine(int x,int y)             //显示此点有雷
	{

		this.buttons[x][y].setEnabled(false);
		this.buttons[x][y].setBorderPainted(false);

		this.buttons[x][y].setIcon(this.mine);
	}
	
	void SetFlag(int x,int y)              //在此点插小旗
	{


//		
		this.buttons[x][y].setIcon(this.flag);

	}

	
	void ShowSteppedMine(int x,int y)
	{

		this.buttons[x][y].setEnabled(false);
		this.buttons[x][y].setBorderPainted(false);
		this.buttons[x][y].setIcon(this.explosion);
	}


	boolean GetClick()                    //返回是否有鼠标键点击
	{
		if( this.click)
		{
			this.click=false;
			return true;
		}
		else
			return false;
	}
	
	public boolean GetLeftClick() {
		if(this.left_click==true)
		{
			this.left_click = false;
			this.click = false;
			return true;
		}
		else
			return false;
			
		
	}
	
	public boolean GetRightClick() {
		if(this.right_click==true)
		{
			this.right_click = false;
			this.click = false;
			return true;
		}
		else
			return false;
			
		
	}
	
	
	int GetClick_x()                      //返回点击的x坐标
	{
		
		return this.x;
	}
	
	int GetClick_y()                      //返回点击的y坐标
	{
		
		return this.y;
	}
	



	@Override
	public void actionPerformed(ActionEvent e) {
		int i,j;
		for(i=0;i<7;i++)
		{
			for(j=0;j<7;j++)
			{
				if(e.getSource()==buttons[i][j])
				{
					System.out.println("You'v pressed "+i+","+j);
					this.x=i;
					this.y=j;
					this.click=true;	
					this.left_click = true;
					synchronized (this.holder){
						this.holder.notifyAll();
					}
					return;
				}
			}
		}
			
		if(e.getSource()==bRestart)
		{
			System.out.println("bRestart clicked");
			this.bRestart.setText("Click the Board to Play");
//			bRestart.setText("Restart!");
			this.init_click = true;
			this.bRestart.setEnabled(false);
			this.bStop.setEnabled(true);
			synchronized (this.holder){
				this.holder.notifyAll();
			}
			return;
		}
		if(e.getSource()==this.bStop){
			this.click = true;
//			System.out.println(this.board);
			this.board.win = ChessBoard.RESTART;
			this.bRestart.setEnabled(true);
			this.bStop.setEnabled(false);
			
			System.out.println("Stop!!");
			return;
		}
		if(e.getSource()==this.blueAI){
//			this.blueHuman.setSelected(false);
//			if(this.bluePlayer==GUI.AI)
//				this.blueAI.setSelected(true);
			this.bluePlayer = GUI.AI;
			this.ai_config_blue.setEnabled(true);
			System.out.println("blueAI");
		}else if(e.getSource()==this.blueHuman){
//			this.blueAI.setSelected(false);
//			if(this.bluePlayer==GUI.Human)
//				this.blueHuman.setSelected(true);
			this.bluePlayer = GUI.Human;
			this.ai_config_blue.setEnabled(false);
			System.out.println("blueHuman");
		}else if(e.getSource()==this.redAI){
//			this.redHuman.setSelected(false);
//			if(this.redPlayer==GUI.AI)
//				this.redAI.setSelected(true);
			this.redPlayer = GUI.AI;
			this.ai_config_red.setEnabled(true);
			System.out.println("redAI");
		}else if(e.getSource()==this.redHuman){
			System.out.println("redHuman");
//			if(this.redPlayer==GUI.Human)
//				this.redHuman.setSelected(true);
			this.redPlayer = GUI.Human;
			this.ai_config_red.setEnabled(false);
//			this.redAI.setSelected(false);
		}
		else if(e.getSource()==this.red_first){
			System.out.println("red first");
//			if(this.first==ChessBoard.RED)
//				this.red_first.setSelected(true);
//			this.blue_first.setSelected(false);
			this.first = ChessBoard.RED;
		}
		else if(e.getSource()==this.blue_first){
			System.out.println("blue first");
//			if(this.first==ChessBoard.BLUE)
//				this.blue_first.setSelected(true);
//			this.red_first.setSelected(false);
			this.first = ChessBoard.BLUE;
		}
		else if(e.getSource()==this.ai_config_blue){
			System.out.println("ai_config_blue");
			//TODO
		}else if(e.getSource()==this.ai_config_red){
			System.out.println("ai_config_red");
			//TODO
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int i,j;
		JButton b = (JButton)e.getSource(); 

		boolean right = SwingUtilities.isRightMouseButton(e); 
		if((right == true)) {
			for(i=0;i<7;i++){
				for(j=0;j<7;j++)
				{
					if(b==this.buttons[i][j])
					{
						this.x = i;
						this.y = j;
						System.out.println("Right Clicked!"+i+","+j);
						this.click = true;
						this.right_click=true;
					}

				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public int getX() {
		System.out.println("GUI.getX()");
		while(!this.click){
			synchronized (this.holder){
				try {
					this.holder.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		this.click = false;
		return this.x;
	}

	public void ShowDraw() {
		
		this.click = true;
		this.board.win = ChessBoard.RESTART;
		this.bRestart.setEnabled(true);
		System.out.println("P!!");
		this.bStop.setEnabled(false);
		this.bRestart.setText("New Game!");
		JOptionPane.showMessageDialog(null, "DRAW!");
		
	}

	public void ShowWin(int current_clr, Chessline chessline) {
		int i,j;
		int [][]line = chessline.GetLine();
		String tmp = current_clr==ChessBoard.BLUE?"BLUE":"RED";
		tmp+=" WIN!!";
//		 if(JOptionPane.showConfirmDialog(null,   "新游戏:是, 退出:否",     tmp,  
//		            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)   !=  JOptionPane.YES_OPTION)  
//		              System.exit(0); 
//		 this.click = true;
//			this.board.win = ChessBoard.RESTART;
//
//		 this.init_click = true;
//			this.bRestart.setEnabled(false);
//			this.bStop.setEnabled(true);
		this.click = true;
//		System.out.println(this.board);
		this.board.win = ChessBoard.RESTART;
		this.bRestart.setEnabled(true);
//		this.bStop.setEnabled(false);
		
		for(i=0;i<4;i++){
			this.buttons[line[i][0]][line[i][1]].setBorderPainted(true);
		}
		
		System.out.println("P!!");
		this.bStop.setEnabled(false);
		this.bRestart.setText("New Game!");
		JOptionPane.showMessageDialog(null, tmp);
		
	}

	public void ShowNewBoard(int clr) {
		int i,j;
		this.click = false;
		for(i=0;i<7;i++){
			for(j=0;j<6;j++){
//				this.buttons[i][j].setEnabled(true);
				this.buttons[i][j].setBorderPainted(false);
				this.buttons[i][j].setIcon(this.empty);
			}
		}
		this.Set_first(clr);
	}

	private void Set_first(int clr) {
		this.first = clr;
		switch(this.first){
		case ChessBoard.BLUE:
			this.blue_first.setSelected(true);
			this.red_first.setSelected(false);
			break;
		case ChessBoard.RED:
			this.red_first.setSelected(true);
			this.blue_first.setSelected(false);
			break;
			
			default:
				throw new java.lang.RuntimeException("invalid clr!!");
		}
	}

	public void SetBoard(ChessBoard bb) {
		this.board = bb;
	}


	public void WaitInit() {
		System.out.println("WaitInit Called");
		this.init_click = false;
		while(!this.init_click)
			synchronized(holder){
				try {
					this.holder.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		this.init_click = false;
		System.out.println("WaitInit Finished");

		return;
		
	}

	public boolean Red_isAI() {
		return this.redAI.isSelected();
	}

	public boolean Blue_isAI(){
		return this.blueAI.isSelected();
	}
}