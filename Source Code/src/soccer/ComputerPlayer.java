package soccer;
import java.util.ArrayList;
import info.gridworld.actor.*;
import info.gridworld.grid.*;

public class ComputerPlayer extends Player
{
	private Ball ball;
	/**
	* Moves the computer player, and the ball if it is in front
	*/
	public void move()
	{
		Grid<Actor> gr = getGrid();
		Location myLoc = getLocation();
		
		Location goalLoc = new Location(gr.getNumRows()/2,0);
		
		setDirection(myLoc.getDirectionToward(ball.getLocation()));
		int randomError = (int) (Math.random() * 50);
		if(ballInFront())
		{
			int direction = myLoc.getDirectionToward(goalLoc);
			setDirection(direction);
			if(ball.canMove(direction) && randomError > 6)
				ball.move(direction);
		}
		else
		{
			super.move();
		}
	}	
	/**
	* @param b the Ball to set the current Ball to
	*/
	public void setBall(Ball b)
	{
		ball=b;
	}
	/**
	* Moves the ComputerPlayer if it can
	*/
	public void act()
	{
		if(canMove())
			move();
	}
}
