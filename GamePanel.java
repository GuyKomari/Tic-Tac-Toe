import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
/**
 * This Class is responsible of drawing the game panel and actions
 */
public class GamePanel extends JPanel 
{
	private GameLogic gameMat;
	private int [][] workingMat;
	
	/**
	 * Constructor
	 */
	public GamePanel()
	{
		this.gameMat = new GameLogic();
		this.workingMat = this.gameMat.getMat().clone();
	}	
	/**
	 * works at the first time we see the DrawPanel(JPanel)
	 * works every time the system wants to refresh the drawing
	 */
	@Override
	public void paintComponent(Graphics g)//override of Jpanel.paintComponent
	{
		super.paintComponent(g);//draw the panel itself on the screen
		this.workingMat = this.gameMat.getMat().clone();	
		setBackground(Color.WHITE);
		g.drawLine(0, 100, 310, 100); //horizon
		g.drawLine(0, 200, 310, 200); //horizon
		g.drawLine(100, 0, 100, 310); //orthogonal
		g.drawLine(200, 0, 200, 310); //orthogonal
		for(int i=0;i<this.gameMat.getSize();i++)
		{
			for(int j=0;j<this.gameMat.getSize();j++)
			{
				if(this.workingMat[i][j]==1) //draw X
					drawX(i,j,g);
				else if(this.workingMat[i][j]==0) //draw O
					drawO(i,j,g);
			}
		}
	}

	/**
	 * Get game matrix
	 * @return LogicalPanel - game matrix (2D array represents the game)
	 */
	public GameLogic getGameMat() 
	{
		return gameMat;
	}	
	/**
	 * Draw O on the matrix in the right place
	 * @param row 
	 * @param col
	 */
	private void drawO(int row,int col,Graphics g)
	{
		if(row==0)
		{
			g.setColor(Color.BLUE);
			if(col==0)
				g.drawOval(20, 20, 60, 60);
			else if(col==1)
				g.drawOval(120, 20, 60, 60);
			else if(col==2)
				g.drawOval(220, 20, 60, 60);
		}
		else if (row==1)
		{
			g.setColor(Color.BLUE);
			if(col==0)
				g.drawOval(20, 120, 60, 60);
			else if(col==1)
				g.drawOval(120, 120, 60, 60);
			else if(col==2)
				g.drawOval(220, 120, 60, 60);	
		}
		else if (row==2)
		{
			g.setColor(Color.BLUE);
			if(col==0)
				g.drawOval(20, 220, 60, 60);
			else if(col==1)
				g.drawOval(120, 220, 60, 60);
			else if(col==2)
				g.drawOval(220, 220, 60, 60);	
		}
	}
	
	/**
	 * Draw X on the matrix in the right place
	 * @param row
	 * @param col
	 */
	private void drawX(int row,int col,Graphics g)
	{
		if(row==0)
		{
			g.setColor(Color.RED);
			if(col==0)
			{
				g.drawLine(20, 20, 80, 80); 
				g.drawLine(80, 20, 20, 80); 
			}
			else if(col==1)
			{
				g.drawLine(120, 20, 180, 80); 
				g.drawLine(180, 20, 120, 80);
			}
			else if(col==2)
			{
				g.drawLine(220, 20, 280, 80); 
				g.drawLine(280, 20, 220, 80);
			}
		}
		else if (row==1)
		{
			g.setColor(Color.RED);
			if(col==0)
			{
				g.drawLine(20, 120, 80, 190); 
				g.drawLine(80, 120, 20, 190); 
			}
			else if(col==1)
			{
				g.drawLine(120, 120, 180, 180); 
				g.drawLine(180, 120, 120, 180);
			}
			else if(col==2)
			{
				g.drawLine(220, 120, 280, 180); 
				g.drawLine(280, 120, 220, 180);
			}
		}
		else if (row==2)
		{
			g.setColor(Color.RED);
			if(col==0)
			{
				g.drawLine(20, 220, 80, 290); 
				g.drawLine(80, 220, 20, 290); 
			}
			else if(col==1)
			{
				g.drawLine(120, 220, 180, 280); 
				g.drawLine(180, 220, 120, 280);
			}
			else if(col==2)
			{
				g.drawLine(220, 220, 280, 280); 
				g.drawLine(280, 220, 220, 280);
			}
		}	
	}
}