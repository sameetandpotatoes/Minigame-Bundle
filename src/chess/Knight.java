package chess;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public class Knight extends Piece
{
	public Knight(Color color, Grid<Actor> grid, Location location)
	{
		super(color, grid, location);
	}
	/**
	* Returns the adjacent locations of a knight
	* @param pieceLoc the current location of the knight
	* @return locs the ArrayList containing the locations
	*/
	public ArrayList<Location> getMoves(Location pieceLoc)
	{
		ArrayList<Location> locs = new ArrayList<Location>();
		int row = pieceLoc.getRow();
		int col = pieceLoc.getCol();
		Location tempLoc = null;
		//Manually get all locations that a knight can move to 
		tempLoc = new Location(row - 2, col + 1);
		locs.add(tempLoc);
		tempLoc = new Location(row - 2, col - 1);
		locs.add(tempLoc);
		tempLoc = new Location(row + 2, col + 1);
		locs.add(tempLoc);
		tempLoc = new Location(row + 2, col - 1);
		locs.add(tempLoc);
		tempLoc = new Location(row - 1, col + 2);
		locs.add(tempLoc);
		tempLoc = new Location(row - 1, col - 2);
		locs.add(tempLoc);
		tempLoc = new Location(row + 1, col + 2);
		locs.add(tempLoc);
		tempLoc = new Location(row + 1, col - 2);
		locs.add(tempLoc);
		return checkArray(locs);
	}
	/**
	* Removes the invalid or occupied locations of the knight
	* @param locs the ArrayList of all of the knight's locations
	* @return locs the modified ArrayList containing only valid knight locations
	*/
	private ArrayList<Location> checkArray(ArrayList<Location> locs)
	{
		for (int i = locs.size() - 1; i >= 0; i--)
		{
			Location tempLoc = locs.get(i);
			if (!getGrid().isValid(tempLoc))
				locs.remove(i);
			else if (getGrid().get(tempLoc) != null)
			{
				if (getGrid().get(tempLoc).getColor().equals(getColor()))
					locs.remove(i);
			}
		}
		return locs;
	}
}
