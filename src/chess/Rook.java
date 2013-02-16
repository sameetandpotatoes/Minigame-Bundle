package chess;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public class Rook extends Piece
{
	public Rook(Color color, Grid<Actor> grid, Location location)
	{
		super(color, grid, location);
	}
	/**
	* Gets all of the valid locations of a rook
	* @param pieceLoc the current location of the rook
	* @return locs the ArrayList containing the locations
	*/
	public ArrayList<Location> getMoves(Location pieceLoc)
	{
		return searchVerticalAndHorizontal(pieceLoc);
	}
}
