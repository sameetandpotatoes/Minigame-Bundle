package chess;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public class King extends Piece
{
	public King(Color color, Grid<Actor> grid, Location location)
	{
		super(color, grid, location);
	}
	/**
	* Returns the valid adjacent locations of a king
	* @param pieceLoc the current location of the king
	* @return locs the ArrayList containing the locations
	*/
	public ArrayList<Location> getMoves(Location pieceLoc)
	{
		ArrayList<Location> locs = getGrid().getValidAdjacentLocations(pieceLoc);
		for (int i = locs.size() - 1; i >= 0; i--)
		{
			Location loc = locs.get(i);
			if (getGrid().get(loc) != null)
			{
				if (!getGrid().get(loc).getColor().equals(getOppositeColor()))
					locs.remove(i);
			}
		}
		return locs;
	}
}
