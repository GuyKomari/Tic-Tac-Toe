import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.lang.Integer;
import java.util.Random;
/**
 * This class is responsible of the game conducting
 */
public class Game 
{	
	private JFrame frame;
	private GamePanel mat;
	private String dialogContent;
	/**
	 *  Constructor
	 */
	public Game()
	{
		this.frame = new JFrame("Tic Tac Toe Game");
		this.mat = new GamePanel();
		this.dialogContent = "Please enter the row and the column in this format:\n[1-3],[1-3]";
	}
	/**
	 *  This Method starts the game
	 * @throws InterruptedException 
	 */
	public void startGame()
	{
		Random rand = new Random();
		int whosTurn=rand.nextInt(2);
		String res;
		String [] resSplitted;
		boolean isFirstTime=true;
		this.frame.add(this.mat);
		this.frame.setResizable(false);
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.setSize(316, 339);
		this.frame.setVisible(true);
		do
		{
			if(whosTurn==0)//Computer Turn (Computer is O)
			{	
				int [] location = chooseBestLocation(isFirstTime);
				play(location[0]+1,location[1]+1,0);
				whosTurn=1;//change to user turn
			}
			else //1,User Turn
			{
				isFirstTime=false;//for computer only
				res = createGameJOption();
				if(res==null)// user press X button
					System.exit(0);
				resSplitted= res.split(",");
				while(inputValidation(resSplitted)==false)// complete  another chance
				{
					JOptionPane.showMessageDialog(null, "Invalid Input / Already Taken!");
					res = createGameJOption();				
					if(res==null)// user press X button
						System.exit(0);
					resSplitted= res.split(",");
				}
				//val=1=X (user is X)
				play(Integer.parseInt(resSplitted[0].replaceAll(" ", "")),Integer.parseInt(resSplitted[1].replaceAll(" ", "")),1);	
				whosTurn=0;//change to computer turn
			}
		}
		while(this.mat.getGameMat().endOfGame()==-1); //game didn't over
		//Conclusion of the game
		conclusionOfTheGame();
		//option to play again
		playAgainIfWanted();
	}
	/**
	 *  Creation of the GUI at the first time
	 *  @return the input String
	 */
	private String createGameJOption()
	{
		return JOptionPane.showInputDialog(
				null,
				dialogContent, 
				"Tic Tac Toe Game", 
				JOptionPane.WARNING_MESSAGE
				);
	}
	/**
	 * pop up the conclusion of the game
	 */
	private void conclusionOfTheGame()
	{
		if(this.mat.getGameMat().endOfGame()==0)//computer won
			JOptionPane.showMessageDialog(null, "Computer Won!" );
		else if(this.mat.getGameMat().endOfGame()==1)//user won
			JOptionPane.showMessageDialog(null, "You Won!" );
		else //no winner
			JOptionPane.showMessageDialog(null, "Game finished with no winner" );
	}
	/**
	 * play again if wanted
	 */
	private void playAgainIfWanted()
	{
		Object[] options = {"Yes","No"};
		int op = JOptionPane.showOptionDialog(
				null, 
				"Do you want to play again?", 
				"Tic Tac Toe Game", 
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				options, 
				options[0]);
		if(op==0)
		{
			Game g = new Game();
			g.startGame();
		}	
		else
			System.exit(0);
	}
	/**
	 * validation of the user input
	 * @param resSplitted - the row and the column the user choose
	 * @return true if valid, otherwise false (if taken, not valid)
	 */
	private boolean inputValidation(String [] resSplitted)
	{
		if(resSplitted.length!=2)
			return false;

		String r = resSplitted[0].replaceAll(" ", "");
		String c = resSplitted[1].replaceAll(" ", "");

		int [][] wMat = this.mat.getGameMat().getMat();

		if (r.length()!=1 || c.length()!=1)
			return false;
		else if(r.length() !=1 || c.length()!=1 )
			return false;
		else if((Integer.parseInt(r)!=1 && Integer.parseInt(r)!=2 && Integer.parseInt(r)!=3
				|| (Integer.parseInt(c)!=1 && Integer.parseInt(c)!=2 && Integer.parseInt(c)!=3)))
		{
			return false;
		}
		else if (wMat[Integer.parseInt(r)-1][Integer.parseInt(c)-1]!=-1)
			return false;
		return true;
	}
	/**
	 * draw in the right place
	 * @param row - [1-3] user choose
	 * @param col - [1-3] user choose
	 * @param val - 0 (O) or 1 (X) 
	 */
	private void play(int row,int col,int val)
	{
		this.mat.getGameMat().setMat(row-1, col-1, val);
		this.mat.repaint();
	}
	/**
	 * choose the best location for the computere
	 * @param isFirstTime - indicates if computer is the first one to play
	 * @return the best location
	 */
	private int [] chooseBestLocation(boolean isFirstTime)
	{
		int [][] wMat = this.mat.getGameMat().getMat();
		int [] bestLocation = new int[2];
		/**empty board - choose random**/
		if(isFirstTime==true)
		{
			Random rand = new Random();
			bestLocation[0]=rand.nextInt(3);//0-2
			bestLocation[1]=rand.nextInt(3);//0-2
			return bestLocation;
		}
		if(canComputerWinInRow(wMat,bestLocation,0))
			return bestLocation;
		else if(canComputerWinInRow(wMat,bestLocation,1))
			return bestLocation;
		else if(canComputerWinInRow(wMat,bestLocation,2))
			return bestLocation;

		else if(canComputerWinInColumn(wMat,bestLocation,0))
			return bestLocation;
		else if(canComputerWinInColumn(wMat,bestLocation,1))
			return bestLocation;
		else if(canComputerWinInColumn(wMat,bestLocation,2))
			return bestLocation;
		
		else if(canComputerWinFirstDiagonal(wMat,bestLocation))
			return bestLocation;
		else if(canComputerWinSecondDiagonal(wMat,bestLocation))
			return bestLocation;	
		
		else if(isGoingToWinInRow(wMat,bestLocation,0))
			return bestLocation;
		else if(isGoingToWinInRow(wMat,bestLocation,1))
			return bestLocation;
		else if(isGoingToWinInRow(wMat,bestLocation,2))
			return bestLocation;

		else if(isUserGoingToWinInColumn(wMat,bestLocation,0))
			return bestLocation;
		else if(isUserGoingToWinInColumn(wMat,bestLocation,1))
			return bestLocation;
		else if(isUserGoingToWinInColumn(wMat,bestLocation,2))
			return bestLocation;
		
		else if(isUserGoingToWinFirstDiagonal(wMat,bestLocation))
			return bestLocation;	
		else if(isUserGoingToWinSecondDiagonal(wMat,bestLocation))
			return bestLocation;
		else 
			return chooseFirstAvailableLocation(wMat);

	}

	private int [] chooseFirstAvailableLocation(int [][] wMat)
	{
		int [] bestLocation = new int[2];
		if(wMat[0][0]==1)
		{
			if(wMat[1][1]==0&&wMat[0][1]==-1)
			{
				bestLocation[0]=0;
				bestLocation[1]=1;
				return bestLocation;
			}
			if(wMat[1][1]==-1)
			{
				bestLocation[0]=1;
				bestLocation[1]=1;
				return bestLocation;
			}
			if(wMat[0][2]==-1)
			{
				bestLocation[0]=0;
				bestLocation[1]=2;
				return bestLocation;
			}
			if(wMat[2][2]==-1)
			{
				bestLocation[0]=2;
				bestLocation[1]=2;
				return bestLocation;
			}

			if(wMat[0][2]==-1)
			{
				bestLocation[0]=0;
				bestLocation[1]=2;
				return bestLocation;
			}
		}
		else if(wMat[2][0]==1)
		{
			if(wMat[1][1]==0&&wMat[2][1]==-1)
			{
				bestLocation[0]=2;
				bestLocation[1]=1;
				return bestLocation;
			}
			if(wMat[1][1]==-1)
			{
				bestLocation[0]=1;
				bestLocation[1]=1;
				return bestLocation;
			}
			if(wMat[0][0]==-1)
			{
				bestLocation[0]=0;
				bestLocation[1]=0;
				return bestLocation;
			}
			if(wMat[0][2]==-1)
			{
				bestLocation[0]=0;
				bestLocation[1]=2;
				return bestLocation;
			}
			if(wMat[2][2]==-1)
			{
				bestLocation[0]=2;
				bestLocation[1]=2;
				return bestLocation;
			}
		}
		else if(wMat[0][2]==1)
		{
			if(wMat[1][1]==0&&wMat[0][1]==-1)
			{
				bestLocation[0]=0;
				bestLocation[1]=1;
				return bestLocation;
			}
			if(wMat[1][1]==-1)
			{
				bestLocation[0]=1;
				bestLocation[1]=1;
				return bestLocation;
			}
			if(wMat[0][0]==-1)
			{
				bestLocation[0]=0;
				bestLocation[1]=0;
				return bestLocation;
			}
			if(wMat[2][2]==-1)
			{
				bestLocation[0]=2;
				bestLocation[1]=2;
				return bestLocation;
			}
			if(wMat[2][0]==-1)
			{
				bestLocation[0]=2;
				bestLocation[1]=0;
				return bestLocation;
			}
		}
		else if(wMat[2][2]==1)
		{
			if(wMat[1][1]==0&&wMat[2][1]==-1)
			{
				bestLocation[0]=2;
				bestLocation[1]=1;
				return bestLocation;
			}
			if(wMat[1][1]==-1)
			{
				bestLocation[0]=1;
				bestLocation[1]=1;
				return bestLocation;
			}
			if(wMat[0][0]==-1)
			{
				bestLocation[0]=0;
				bestLocation[1]=0;
				return bestLocation;
			}
			if(wMat[2][0]==-1)
			{
				bestLocation[0]=2;
				bestLocation[1]=0;
				return bestLocation;
			}
			if(wMat[0][2]==-1)
			{
				bestLocation[0]=0;
				bestLocation[1]=2;
				return bestLocation;
			}

		}
		for(int i=0;i<wMat.length;i++)
		{
			for(int j=0;j<wMat.length;j++)
			{
				if(wMat[i][j]==-1)
				{
					bestLocation[0]=i;
					bestLocation[1]=j;
					return bestLocation;
				}
			}
		}
		return bestLocation;
	}
	/**
	 * checks if the computer can win in a row
	 * @param wMat - matrix represents the game
	 * @param bestLocation - the index of the location
	 * @return true - if there is an ideal location to win, otherwise false
	 */
	private boolean canComputerWinInRow(int [][] wMat,int [] bestLocation,int r)
	{
		if(wMat[r][0]==0 && wMat[r][1]==0 && wMat[r][2]==-1)
		{
			bestLocation[0]=r;
			bestLocation[1]=2;
			return true;
		}
		else if(wMat[r][0]==0 && wMat[r][2]==0 && wMat[r][1]==-1)
		{
			bestLocation[0]=r;
			bestLocation[1]=1;
			return true;
		}
		else if(wMat[r][1]==0 && wMat[r][2]==0 && wMat[r][0]==-1)
		{
			bestLocation[0]=r;
			bestLocation[1]=0;
			return true;
		}

		return false;
	}

	/**
	 * checks if the user going to in a row
	 * @param wMat - matrix represents the game
	 * @param bestLocation - the index of the location to block
	 * @return true - if there is an ideal location to block, otherwise false
	 */
	private boolean isGoingToWinInRow(int [][] wMat,int [] bestLocation,int r)
	{
		if(wMat[r][0]==1 && wMat[r][1]==1 && wMat[r][2]==-1)
		{
			bestLocation[0]=r;
			bestLocation[1]=2;
			return true;
		}
		else if(wMat[r][0]==1 && wMat[r][2]==1 && wMat[r][1]==-1)
		{
			bestLocation[0]=r;
			bestLocation[1]=1;
			return true;
		}
		else if(wMat[r][1]==1 && wMat[r][2]==1 && wMat[r][0]==-1)
		{
			bestLocation[0]=r;
			bestLocation[1]=0;
			return true;
		}

		return false;
	}
	/**
	 * checks if the computer can win in a column
	 * @param wMat - matrix represents the game
	 * @param bestLocation - the index of the location
	 * @return true - if there is an ideal location to win, otherwise false
	 */
	private boolean canComputerWinInColumn(int [][] wMat,int [] bestLocation,int c)
	{
		if(wMat[0][c]==0 && wMat[1][c]==0 && wMat[2][c]==-1)
		{
			bestLocation[0]=2;
			bestLocation[1]=c;
			return true;
		}
		else if(wMat[0][c]==0 && wMat[2][c]==0 && wMat[1][c]==-1)
		{
			bestLocation[0]=1;
			bestLocation[1]=c;
			return true;
		}
		else if(wMat[1][c]==0 && wMat[2][c]==0 && wMat[0][c]==-1)
		{
			bestLocation[0]=0;
			bestLocation[1]=c;
			return true;
		}
		return false;
	}

	/**
	 * checks if the user going to win a column
	 * @param wMat - matrix represents the game
	 * @param bestLocation - the index of the location to block
	 * @return true - if there is an ideal location to block, otherwise false
	 */
	private boolean isUserGoingToWinInColumn(int [][] wMat,int [] bestLocation,int c)
	{
		if(wMat[0][c]==1 && wMat[1][c]==1 && wMat[2][c]==-1)
		{
			bestLocation[0]=2;
			bestLocation[1]=c;
			return true;
		}
		else if(wMat[0][c]==1 && wMat[2][c]==1 && wMat[1][c]==-1)
		{
			bestLocation[0]=1;
			bestLocation[1]=c;
			return true;
		}
		else if(wMat[1][c]==1 && wMat[2][c]==1 && wMat[0][c]==-1)
		{
			bestLocation[0]=0;
			bestLocation[1]=c;
			return true;
		}
		return false;
	}

	/**
	 * checks if the computer can win in the first diagonal
	 * @param wMat - matrix represents the game
	 * @param bestLocation - the index of the location
	 * @return true - if there is an ideal location to win, otherwise false
	 */
	private boolean canComputerWinFirstDiagonal(int [][] wMat,int [] bestLocation)
	{
		if(wMat[0][0]==0 && wMat[1][1]==0 && wMat[2][2]==-1)
		{
			bestLocation[0]=2;
			bestLocation[1]=2;
			return true;
		}
		else if(wMat[0][0]==0 && wMat[2][2]==0 && wMat[1][1]==-1)
		{
			bestLocation[0]=1;
			bestLocation[1]=1;
			return true;
		}
		else if(wMat[1][1]==0 && wMat[2][2]==0 && wMat[0][0]==-1)
		{
			bestLocation[0]=0;
			bestLocation[1]=0;
			return true;
		}

		return false;
	}
	/**
	 * checks if the user going to win in the first diagonal
	 * @param wMat - matrix represents the game
	 * @param bestLocation - the index of the location to block
	 * @return true - if there is an ideal location to block, otherwise false
	 */
	private boolean isUserGoingToWinFirstDiagonal(int [][] wMat,int [] bestLocation)
	{
		if(wMat[0][0]==1 && wMat[1][1]==1 && wMat[2][2]==-1)
		{
			bestLocation[0]=2;
			bestLocation[1]=2;
			return true;
		}
		else if(wMat[0][0]==1 && wMat[2][2]==1 && wMat[1][1]==-1)
		{
			bestLocation[0]=1;
			bestLocation[1]=1;
			return true;
		}
		else if(wMat[1][1]==1 && wMat[2][2]==1 && wMat[0][0]==-1)
		{
			bestLocation[0]=0;
			bestLocation[1]=0;
			return true;
		}

		return false;
	}

	/**
	 * checks if the computer can win in the Second diagonal
	 * @param wMat - matrix represents the game
	 * @param bestLocation - the index of the location
	 * @return true - if there is an ideal location to win, otherwise false
	 */
	private boolean canComputerWinSecondDiagonal(int [][] wMat,int [] bestLocation)
	{
		if(wMat[0][2]==0 && wMat[1][1]==0 && wMat[2][0]==-1)
		{
			bestLocation[0]=2;
			bestLocation[1]=0;
			return true;
		}
		else if(wMat[0][2]==0 && wMat[2][0]==0 && wMat[1][1]==-1)
		{
			bestLocation[0]=1;
			bestLocation[1]=1;
			return true;
		}
		else if(wMat[1][1]==0 && wMat[2][0]==0 && wMat[0][2]==-1)
		{
			bestLocation[0]=0;
			bestLocation[1]=2;
			return true;
		}

		return false;
	}

	/**
	 * checks if the user going to win in the second diagonal
	 * @param wMat - matrix represents the game
	 * @param bestLocation - the index of the location to block
	 * @return true - if there is an ideal location to block, otherwise false
	 */
	private boolean isUserGoingToWinSecondDiagonal(int [][] wMat,int [] bestLocation)
	{
		if(wMat[0][2]==1 && wMat[1][1]==1 && wMat[2][0]==-1)
		{
			bestLocation[0]=2;
			bestLocation[1]=0;
			return true;
		}
		else if(wMat[0][2]==1 && wMat[2][0]==1 && wMat[1][1]==-1)
		{
			bestLocation[0]=1;
			bestLocation[1]=1;
			return true;
		}
		else if(wMat[1][1]==1 && wMat[2][0]==1 && wMat[0][2]==-1)
		{
			bestLocation[0]=0;
			bestLocation[1]=2;
			return true;
		}

		return false;
	}
}//end of class 