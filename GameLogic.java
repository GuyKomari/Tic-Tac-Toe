/**
 * This Class represents the game logic
 */
public class GameLogic 
{
	private int [][] mat;
	private final int SIZE = 3;
	/**
	 * Constructor - initialize matrix of 3X3, with default value -1
	 */
	public GameLogic()
	{
		mat = new int [SIZE][SIZE];
		for(int i=0;i<this.SIZE;i++)
			for(int j=0;j<this.SIZE;j++)
				mat[i][j]=-1;
	}
	/**
	 * Get Matrix
	 */
	public int[][] getMat() 
	{
		return this.mat;
	}
	/**
	 * Get matrix size
	 */
	public int getSize() 
	{
		return this.SIZE;
	}
	/**
	 * Set specific cell in matrix
	 * @param i - row
	 * @param j - column
	 * @param val - X or O (1 or 0)
	 */
	public void setMat(int i,int j, int val) 
	{
		mat[i][j]=val;
	}
	/**
	 * checks if the game ended by winning or dead end
	 * @return 1 - user won, 0 - computer won, -2 - dead end, -1 - not over yet
	 */
	public int endOfGame()
	{
		if(checksIfWon(0)==true)//checks if computer won
			return 0;
		else if(checksIfWon(1)==true)//checks if user won
			return 1;
		for(int i=0;i<this.SIZE;i++) // checks if game not over yet
		{
			for(int j=0;j<this.SIZE;j++)
			{
				if (this.mat[i][j]==-1)
					return -1;
			}
		}
		return -2; //dead end
	}	
	/**
	 * checks if won
	 * @param k - 0 or 1 (computer or user)
	 * @return true - if won
	 */
	private boolean checksIfWon(int k)
	{
		if(this.mat[0][0]==k)
		{
			if(this.mat[0][1]==k && this.mat[0][2]==k)
				return true;
			else if(this.mat[1][0]==k && this.mat[2][0]==k)
				return true;
		}
		if(this.mat[2][2]==k)
		{
			if(this.mat[1][2]==k && this.mat[0][2]==k)
				return true;
			else if(this.mat[2][0]==k && this.mat[2][1]==k)
				return true;
		}
		if(this.mat[1][1]==k)
		{
			if(this.mat[0][1]==k && this.mat[2][1]==k)
				return true;
			else if(this.mat[1][0]==k && this.mat[1][2]==k)
				return true;
			else if(this.mat[0][2]==k && this.mat[2][0]==k)
				return true;
			else if(this.mat[0][0]==k && this.mat[2][2]==k)
				return true;
		}
		return false;
	}
}//end of class