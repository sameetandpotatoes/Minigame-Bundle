package soccer;

import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.*;

public class SoccerWorld extends World<Actor>
{
	private Player playerB;
	private Player playerA;
	private SideLine sideLine;
	private int playerAScore;
	private int playerBScore;
	private Goal goal;
	private Ball ball;
	protected static boolean checkGoal = false;
	private boolean twoPlayer;
	public SoccerWorld(BoundedGrid<Actor> bg, Player A, Player B)
	{
		super(bg);
		
		if(B == null)
			twoPlayer=false;
		else
			twoPlayer=true;
		
		playerA = A;
		
		if(twoPlayer)
			playerB = B;
		else
			playerB = new ComputerPlayer();
		
		sideLine = new SideLine();
		goal= new Goal(getGrid());
		ball = new Ball();
		ball.putSelfInGrid(getGrid(), new Location(getGrid().getNumRows()/2,getGrid().getNumCols()/2));
		sideLine.setLines(getGrid());
		goal.setUpGoals();
		playerAScore=0;
		playerBScore=0;
		setMessage("Score:\nPlayer A: " + playerAScore + " - PlayerB: " + playerBScore);
		playerA.putSelfInGrid(getGrid(), new Location(getGrid().getNumRows()/2,1));
		playerB.putSelfInGrid(getGrid(), new Location(getGrid().getNumRows()/2,getGrid().getNumCols()-2));
		
		if(!twoPlayer)
			((ComputerPlayer)playerB).setBall(ball);
		
		playerA.setBoard(this);
		playerB.setBoard(this);
	}
	/**
	* Sets up key listener.
	*/
	public void play()
	{
		/*try
		{
			Thread.sleep(1000);
		}
		catch (Exception e){
			
		}
		*/
		java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager()
		.addKeyEventDispatcher(new java.awt.KeyEventDispatcher() 
		{
			public boolean dispatchKeyEvent(java.awt.event.KeyEvent event) 
			{
				String key = javax.swing.KeyStroke.getKeyStrokeForEvent(event).toString();
				if (!checkGoal)
				{	
					if(key.equals("pressed W"))
					{
						playerA.setDirection(0);
						playerA.act();
						
						show();
						if (!twoPlayer)
							((ComputerPlayer) playerB).act();
						
						show();
					}
					if (key.equals("pressed S"))
					{
						playerA.setDirection(180);
						playerA.act(); 
						show();
						if (!twoPlayer)
							((ComputerPlayer) playerB).act();
						
						show();
					}
					if (key.equals("pressed D"))
					{
						playerA.setDirection(90);
						playerA.act();
						show();
						if (!twoPlayer)
							((ComputerPlayer) playerB).act();
						
						show();
					}
					if (key.equals("pressed A"))
					{
						playerA.setDirection(270);
						playerA.act();
						show();
						if (!twoPlayer)
							((ComputerPlayer) playerB).act();
						show();
					}
					if(twoPlayer)
					{
						if (key.equals("pressed UP"))
						{
							playerB.setDirection(0);
							playerB.act();
							show();
						}
						if (key.equals("pressed DOWN"))
						{
							playerB.setDirection(180);
							playerB.act(); 
							show();
						}
						if (key.equals("pressed RIGHT"))
						{
							playerB.setDirection(90);
							playerB.act(); 
							show();
						}
						if (key.equals("pressed LEFT"))
						{
							playerB.setDirection(270);
							playerB.act();   
							show();
						}
					}
				}
				return true;
			}
		});	
	}
	/**
	* Resets goals, sidelines, ball, and players on grid.
	* @param game true if the entire game should be reset,
	* false if just the board
	*/
	public void reset(boolean game)
	{
		sideLine.setLines(getGrid());
		goal.setUpGoals();
		if(ball.getGrid()!=null)
			ball.removeSelfFromGrid();
		ball.putSelfInGrid(getGrid(),new Location(getGrid().getNumRows()/2,getGrid().getNumCols()/2));
		if(playerA.getGrid()!=null)
			playerA.removeSelfFromGrid();
		playerA.putSelfInGrid(getGrid(),new Location(getGrid().getNumRows()/2,1));
		if(playerB.getGrid()!=null)
			playerB.removeSelfFromGrid();
		playerB.putSelfInGrid(getGrid(),new Location(getGrid().getNumRows()/2,getGrid().getNumCols()-2));
		
		if (game)
		{
			playerAScore = 0;
			playerBScore = 0;
		}
		show();
	}
	/**
	* @param loc the Location that the user clicked on the board. 
	* @return true to avoid showing methods if location is clicked.
	*/
	public boolean locationClicked(Location loc)
	{
		return true; 
	}
	/**
	* @returns true if both player scores are less than 10.
	*/
	public boolean isDone()
	{
		return!(playerAScore < 10 && playerBScore < 10);
	}
	/**
	* Shows a message dialog when game is over.
	*/
	public void endGame()
	{
		if(playerAScore > playerBScore)
			JOptionPane.showMessageDialog(null, "Player A is clearly superior. The final score is: "+playerAScore+" - "+playerBScore, "Player A Wins!", JOptionPane.DEFAULT_OPTION);
		else
			JOptionPane.showMessageDialog(null, "Player B is clearly superior. The final score is: "+playerAScore+" - "+playerBScore, "Player B Wins!", JOptionPane.DEFAULT_OPTION);
		reset(true);
	}
	/**
	* Checks which player scored a goal, if any, then resets the grid.
	*/
	public void goal()
	{
		checkGoal = true;
		if(goal.isGoal())
		{
			ArrayList<Location> goalLocs = goal.getGoalLocs();
			if (ball.getLocation().equals(goalLocs.get(0)) || ball.getLocation().equals(goalLocs.get(1))){
				playerBScore++;
				JOptionPane.showMessageDialog(null, "Goal for Player B!!", "Player B Scores!", JOptionPane.ERROR_MESSAGE);
				reset(false);
			}
			else
			{
				playerAScore++;
				JOptionPane.showMessageDialog(null, "Goal for Player A!!", "Player A Scores!", JOptionPane.ERROR_MESSAGE);
				reset(false);
			}
		}
		setMessage("Score:\nPlayer A: " + playerAScore + " - PlayerB: " + playerBScore);
		checkGoal = false;
	}
	/**
	* Overrides the step method to create a new game
	* whenver the button is pressed
	*/
	public void newGame()
	{
		reset(true);
	}
}
