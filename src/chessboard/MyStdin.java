package chessboard;

import java.util.Scanner;


/**
 * 
 * @author Sepsky
 *
 */
public class MyStdin {
	private Scanner stdin;
	
	public MyStdin(Scanner s)
	{
		this.stdin = s;
	}
	public MyStdin() {
		this.stdin = new Scanner(System.in);
	}
	public synchronized int nextInt() {
		int tmp=this.stdin.nextInt();
		while(tmp>6||tmp<0){
			System.out.println("The column must be 0<=col<6");
			tmp=this.stdin.nextInt();
		}

		return tmp;

	}

}
