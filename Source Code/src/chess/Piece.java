package chess;

import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Piece extends Actor
{
	private Color color;
	public Piece(Color color, Grid<Actor> gr, Location loc)
	{
		this.color = color;
		putSelfInGrid(gr, loc);
	}
	/**
	* Determines if a piece can move to the specified location
	* @param pieceLoc the current location of the piece
	* @param endLoc the location that pieceLoc wants to move to
	* @return true if pieceLoc can move to endLoc
	*/
	public boolean canMove(Location pieceLoc, Location endLoc)
	{
		for (Location loc : getMoves(pieceLoc))
		{
			if (loc.equals(endLoc))
				return true;
		}
		return false;
	}
	/**
	* @return color
	*/
	public Color getColor()
	{
		return color;
	}
	/**
	* @return a string representation of a piece object i.e. 
	* the name of the piece
	*/
	public String toString()
	{
		String ans = "";
		if (color.equals(Color.WHITE))
			ans += "a white";
		else
			ans += "a black";
		
		if (this instanceof Rook)
			ans += " rook";
		else if (this instanceof Knight)
			ans += " knight";
		else if (this instanceof Bishop)
			ans += " bishop";
		else if (this instanceof Pawn)
			ans += " pawn";
		else if (this instanceof Queen)
			ans += " queen";
		else if (this instanceof King)
			ans += " king";
		
		return ans;
	}
	/**
	* @return the opposite color of this piece
	*/
	public Color getOppositeColor()
	{
		if (color.equals(Color.BLACK))
			return Color.WHITE;
		else
			return Color.BLACK;
	}
	/**
	* Gets all of the valid locations of a bishop by looking 
	* at its diagonals from up, left to down, right
	* @param rowBishop the row that the bishop occupies
	* @param colBishop the column that the bishop occupies
	* @return locs the ArrayList containing the locations
	*/
	public ArrayList<Location> searchDiagonalULDR(int rowBishop, int colBishop)
	{
		ArrayList<Location> locs = new ArrayList<Location>();
		int rowCounter = rowBishop;
		int colCounter = colBishop;
		while(rowCounter < 7 && colCounter < 7)
		{
			rowCounter++;
			colCounter++;
			Location loc = new Location(rowCounter, colCounter);
			Piece p = (Piece) getGrid().get(loc);
			if (p == null)
				locs.add(loc);
			else if (p != null && p.getColor().equals(this.getOppositeColor()))
			{
				locs.add(loc); 
				break;
			}
			else
				break;
		}		
		rowCounter = rowBishop;
		colCounter = colBishop;
		while(rowCounter > 0 && colCounter > 0)
		{
			rowCounter--;
			colCounter--;
			Location loc = new Location(rowCounter, colCounter);
			Piece p = (Piece) getGrid().get(loc);
			if (p == null)
				locs.add(loc);
			else if (p != null && p.getColor().equals(getOppositeColor()))
			{
				locs.add(loc); 
				break;
			}
			else
				break;
		}
		return locs;
	}
	/**
	* Gets all of the valid locations of a pawn
	* @param row the current row that the bishop occupies
	* @param col the current col that the bishop occupies
	* @return locs ArrayList containing the valid locations that the bishop can move to
	*/
	public ArrayList<Location> searchDiagonalDLUR(int row, int col)
	{
		ArrayList<Location> locs = new ArrayList<Location>();
		int rowCounter = row;
		int colCounter = col;
		while(rowCounter > 0 && colCounter < 7)
		{
			rowCounter--;
			colCounter++;
			Location loc = new Location(rowCounter, colCounter);
			Piece p = (Piece) getGrid().get(loc);
			if (p == null)
				locs.add(loc);
			else if (p != null && p.getColor().equals(getOppositeColor()))
			{
				locs.add(loc); 
				break;
			}
			else
				break;
		}	
		rowCounter = row;
		colCounter = col;
		while(rowCounter < 7 && colCounter > 0)
		{
			rowCounter++;
			colCounter--;
			Location loc = new Location(rowCounter, colCounter);
			Piece p = (Piece) getGrid().get(loc);
			if (p == null)
				locs.add(loc);
			else if (p != null && p.getColor().equals(getOppositeColor()))
			{
				locs.add(loc); 
				break;
			}
			else
				break;
		}
		return locs;
	}
	public ArrayList<Location> searchVerticalAndHorizontal(Location pieceLoc)
	{
		ArrayList<Location> locs = new ArrayList<Location>();
		int rowRook = pieceLoc.getRow();
		int colRook = pieceLoc.getCol();
		for (int row = rowRook - 1; row >= 0; row--)
		{
			Location loc = new Location(row, colRook);
			Piece p = (Piece) getGrid().get(loc);
			if (p == null)
				locs.add(loc);
			else if (p != null && p.getColor().equals(getOppositeColor()))
			{
				locs.add(loc); 
				break;
			}
			else
				break;
		}
		for (int row = rowRook + 1; row < 8; row++)
		{
			Location loc = new Location(row, colRook);
			Piece p = (Piece) getGrid().get(loc);
			if (p == null)
				locs.add(loc);
			else if (p != null && p.getColor().equals(getOppositeColor()))
			{
				locs.add(loc); 
				break;
			}
			else
				break;
		}
		for (int col = colRook - 1; col >= 0; col--)
		{
			Location loc = new Location(rowRook, col);
			Piece p = (Piece) getGrid().get(loc);
			if (p == null)
				locs.add(loc);
			else if (p != null && p.getColor().equals(getOppositeColor()))
			{
				locs.add(loc); 
				break;
			}
			else
				break;
		}
		for (int col = colRook + 1; col < 8; col++)
		{
			Location loc = new Location(rowRook, col);
			Piece p = (Piece) getGrid().get(loc);
			if (p == null)
				locs.add(loc);
			else if (p != null && p.getColor().equals(getOppositeColor()))
			{
				locs.add(loc); 
				break;
			}
			else
				break;
		}
		return locs;
	}
	/**
	* Will be implemented in all subclasses of piece to return an
	* ArrayList containing the valid locations that the subclass can move to
	*/
	public abstract ArrayList<Location> getMoves(Location pieceLoc);
}
