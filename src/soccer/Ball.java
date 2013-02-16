package soccer;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Ball extends Actor
{
	/**
	 * Tests if the SoccerBall can move in the direction specified.
     * @param direction the direction the SoccerBall needs to go to.
     * @return true if the SoccerBall can move in the direction specified.
     */
	public boolean canMove(int direction)
	{
		Grid<Actor> gr = getGrid();
		Location destLoc = getLocation().getAdjacentLocation(direction);
		return(gr.isValid(destLoc) && ((gr.get(destLoc) == null) || gr.get(destLoc) instanceof Goal));
	}
	/**
     * Moves the ball forward in the direction specified.
     * @param direction where SoccerBall will move to.
     */
	public void move(int direction)
	{
		Grid<Actor> gr=getGrid();
		Location ballLoc=getLocation();
		Location destLoc=ballLoc.getAdjacentLocation(direction);
		if(gr.isValid(destLoc))
			moveTo(destLoc);
	}
	/**
     * Returns a string representation of the object.
     * @return the name of the object
     */
	public String toString()
	{
		return "the soccer ball";
	}
}
