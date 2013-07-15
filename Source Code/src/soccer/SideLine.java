package soccer;
import info.gridworld.actor.*;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
public class SideLine extends Actor
{
	/**
	 * Puts the lines in the grid.
     * @param gr the grid to put the sidelines into.
     */
	public void setLines(Grid<Actor> gr)
	{
		for(int r = 0; r < gr.getNumRows(); r++)
		{
			for(int c = 0; c < gr.getNumCols(); c++)
			{
				if(r == 0 || c == 0 || r == gr.getNumRows() - 1 || c == gr.getNumCols() - 1)
					new SideLine().putSelfInGrid(gr, new Location(r, c));
			}
		}	
	}
	/**
	* @return a string representation of the object
	*/
	public String toString()
	{
		return "a sideline";
	}
}
