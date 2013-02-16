package chess;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public class Bishop extends Piece
{
	public Bishop(Color color, Grid<Actor> grid, Location location)
	{
		super(color, grid, location);
	}
	/**
	* Gets the valid diagonal locations that a bishop can move to.
	* @param pieceLoc the location of the bishop
	* @return locs the ArrayList containing the locations
	*/
	public ArrayList<Location> getMoves(Location pieceLoc)
	{
		int rowBishop = pieceLoc.getRow();
		int colBishop = pieceLoc.getCol();
		ArrayList<Location> locs = new ArrayList<Location>();
		for (Location loc : searchDiagonalULDR(rowBishop, colBishop))
			locs.add(loc);
		for (Location loc : searchDiagonalDLUR(rowBishop, colBishop))
			locs.add(loc);
		return locs;
	}
}
