package chess;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public class Queen extends Piece
{
	public Queen(Color color, Grid<Actor> grid, Location location)
	{
		super(color, grid, location);
	}
	/**
	* Gets all of the valid locations that a queen can move to
	* @param pieceLoc the current location of the queen
	* @return locs the ArrayList containing the locations
	*/
	public ArrayList<Location> getMoves(Location pieceLoc)
	{
		ArrayList<Location> locs = new ArrayList<Location>();
		int row = pieceLoc.getRow();
		int col = pieceLoc.getCol();
		//getting valid diagonals
		for (Location loc : searchDiagonalULDR(row, col))
			locs.add(loc);
		for (Location loc : searchDiagonalDLUR(row, col))
			locs.add(loc);
		for (Location loc : searchVerticalAndHorizontal(pieceLoc))
			locs.add(loc);
		return locs;
	}
}
