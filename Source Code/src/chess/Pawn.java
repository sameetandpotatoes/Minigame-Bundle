package chess;

import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public class Pawn extends Piece
{
	private int oneDir;
	private int twoDir;
	private int rightDir;
	private int leftDir;
	public Pawn(Color color, Grid<Actor> gr, Location pieceLoc)
	{
		super(color, gr, pieceLoc);
		//setColor(color);
		oneDir = 0;
		rightDir = 45;
		if (color.equals(Color.BLACK))
		{
			oneDir += 180;
			rightDir += 180;
		}
	}
	/**
	* Gets all of the valid locations of a pawn
	* @param pieceLoc the current location of the pawn
	* @return locs the ArrayList containing the locations
	*/
	public ArrayList<Location> getMoves(Location pieceLoc)
	{
		ArrayList<Location> locs = getAttackMoves(pieceLoc);
		Location oneLoc = pieceLoc.getAdjacentLocation(oneDir);
		Location twoLoc = oneLoc.getAdjacentLocation(oneDir);
		if (getGrid().isValid(oneLoc) && getGrid().get(oneLoc) == null)
		{
			locs.add(oneLoc);
		}
		if (getGrid().isValid(twoLoc) && getGrid().get(twoLoc) == null && (getGrid().get(pieceLoc).getColor().equals(Color.white) && pieceLoc.getRow() == 6 || (getGrid().get(pieceLoc).getColor().equals(Color.BLACK) && pieceLoc.getRow() == 1)))
		{
			locs.add(twoLoc);
		}
		return locs;
	}
	/**
	* Only gets the valid attack locations of the pawn
	* @param pieceLoc the current location of the pawn
	* @return locs the ArrayList containing the locations
	*/
	public ArrayList<Location> getAttackMoves(Location pieceLoc)
	{
		ArrayList<Location> locs = new ArrayList<Location>();
		Color oppositeColor = getOppositeColor();
		Location upRight = pieceLoc.getAdjacentLocation(rightDir);
		Location upLeft = pieceLoc.getAdjacentLocation(-rightDir);
		if (getGrid().isValid(upLeft) && getGrid().get(upLeft) != null && getGrid().get(upLeft).getColor().equals(oppositeColor))
		{
			locs.add(upLeft);
		}
		if (getGrid().isValid(upRight) && getGrid().get(upRight) != null && getGrid().get(upRight).getColor().equals(oppositeColor))
		{
			locs.add(upRight);
		}
		return locs;
	}
}
