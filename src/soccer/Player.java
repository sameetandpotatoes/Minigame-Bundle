package soccer;
import info.gridworld.actor.*;
import javax.swing.JFrame;
import info.gridworld.grid.*;
public class Player extends Actor
{	
	private SoccerWorld sb;
	/**
     * @param sb the SoccerBoard to set the board to
     */
	public void setBoard(SoccerWorld sb)
	{
		this.sb = sb;
	}	
	/**
     * Tests whether this SoccerPlayer can move forward into a location that is empty or
     * contains a sideline or a soccerball.
     * @return true if this bug can move.
     */
	public boolean canMove()
	{
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return false;
		Location loc = getLocation();
		Location next = loc.getAdjacentLocation(getDirection());
		if (!gr.isValid(next))
			return false;
		Actor neighbor = gr.get(next);
		return (neighbor == null) || (neighbor instanceof SideLine) || (neighbor instanceof Ball);
	}
	/**
     * Tests whether there is a ball in the adjacent location of this SoccerPlayer
     * @return true if there is a soccer ball in front of the SoccerPlayer.
     */
	public boolean ballInFront()
	{
		int direction = getDirection();
		Location myLoc = getLocation();
		Grid<Actor> gr = getGrid();
		Location ballLoc = myLoc.getAdjacentLocation(direction);
		return(gr.isValid(ballLoc) && gr.get(ballLoc) instanceof Ball);
	}
	/**
     * Moves if it can move. Checks if the game is done.
     */
	public void act()
    {
		Grid<Actor> gr=getGrid();
		Location myLoc=getLocation();
		int direction =getDirection();
        if (canMove())
        {
        	if (ballInFront() && ((Ball)gr.get(myLoc.getAdjacentLocation(direction))).canMove(direction))
        		((Ball)gr.get(myLoc.getAdjacentLocation(direction))).move(direction);
        	if(!ballInFront())
        		move();
        }
    }
	/**
     * Moves the SoccerPlayer one step forward.
     * Puts SideLine if SoccerPlayer stepped onto it.
     */
	public void move()
	{
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return;
		Location loc = getLocation();
		Location next = loc.getAdjacentLocation(getDirection());
		if (gr.isValid(next) && !(gr.get(next) instanceof Player))
		{
			moveTo(next);
			if(loc.getRow()==0 || loc.getCol()==0 || loc.getRow()==gr.getNumRows()-1 || loc.getCol()==gr.getNumCols()-1)
				new SideLine().putSelfInGrid(gr, loc);
		}
		if(!sb.isDone())
        {	
        	sb.show();
        	sb.goal();
        }
        else
        	sb.endGame();
	}
	/**
	* @return a string representation of the object
	*/
	public String toString()
	{
		return "a soccer player";
	}
}
