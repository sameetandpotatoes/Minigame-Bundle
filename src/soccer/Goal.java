package soccer;
import info.gridworld.actor.*;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;
public class Goal extends Actor
{
	private ArrayList<Location> goalLocs;
	private Grid<Actor> gr;
	public Goal() {} 
	public Goal(Grid<Actor> grid)
	{
		goalLocs = new ArrayList<Location>();
		gr = grid;
		goalLocs.add(new Location(gr.getNumRows()/2,0));
		goalLocs.add(new Location(gr.getNumRows()/2-1,0));
		goalLocs.add(new Location(gr.getNumRows()/2,gr.getNumCols()-1));
		goalLocs.add(new Location(gr.getNumRows()/2-1,gr.getNumCols()-1));
	}
	/**
     * Puts goals into the proper positions.
     */
	public void setUpGoals()
	{
		Goal firstGoal= new Goal();
		firstGoal.putSelfInGrid(gr, goalLocs.get(0));
		firstGoal.setDirection(180);
		Goal secondGoal= new Goal();
		secondGoal.putSelfInGrid(gr, goalLocs.get(1));
		secondGoal.setDirection(270);
		Goal thirdGoal= new Goal();
		thirdGoal.putSelfInGrid(gr, goalLocs.get(2));
		thirdGoal.setDirection(90);
		Goal fourthGoal= new Goal();
		fourthGoal.putSelfInGrid(gr, goalLocs.get(3));
		fourthGoal.setDirection(0);
	}
	/**
     * @return goalLocs
     */
	public ArrayList<Location> getGoalLocs()
	{
		return goalLocs;
	}
	/**
     * Determines if a goal was scored
     * @return true if goal is scored
     */
	public boolean isGoal()
	{
		for(Location loc : goalLocs)
		{
			if(gr.get(loc) instanceof Ball)
				return true;
		}
		return false;
	}
	public String toString()
	{
		return "the goal";
	}
}
