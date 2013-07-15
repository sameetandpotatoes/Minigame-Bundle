package othello;

import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.gui.WorldFrame;
import info.gridworld.world.World;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class OthelloWorld extends World<Othello>
{
	private Color userColor;
	private boolean onePlayer;
	public OthelloWorld(boolean onePlayer)
	{
		super(new BoundedGrid(8, 8));
		this.onePlayer = onePlayer;
		reset();
	}
	/**
	* Counts the number of Othello pieces that have 
	* the same color as specified by the parameter
	* @param color the color
	* @return the number of Othello pieces that are the
	* same color as the paramter
	*/
	private int countColor(Color color)
	{
		int counter = 0;
		for (Location loc : getGrid().getOccupiedLocations())
			if (getGrid().get(loc).getColor().equals(color))
				counter++;
		return counter;
	}
	/**
	* Handles the events that occur when a user clicks on a GridPanel
	* @param loc the location that the user clicked
	* @return true to avoid showing any public methods
	*/
	public boolean locationClicked(Location loc)
	{
		if (getGrid().get(loc) == null && getGrid().getNeighbors(loc).size() > 0)
		{	
			add(loc, new Othello(userColor));
			searchHorizontal(loc.getRow(), loc.getCol(), false);
			searchVertical(loc.getRow(), loc.getCol(), false);
			searchDiagonalDLUR(loc.getRow(), loc.getCol(), false);
			searchDiagonalULDR(loc.getRow(), loc.getCol(), false);
			show();	
		}
		else //returns true because it does not want to progress if no piece was added
			return true;
		
		if (userColor.equals(Color.BLACK)) //changes color
			userColor = Color.WHITE;
		else
			userColor = Color.BLACK;
		
		if(onePlayer) //only runs if the game is 1 player
		{
			int maxPieces = 0;
			Location loc1 = null;
			ArrayList<Location> possibleLocs = new ArrayList<Location>();
			//Gets locations that have at least something around them
			for (int row = 0; row < getGrid().getNumRows(); row++)
			{
				for (int col = 0; col < getGrid().getNumCols(); col++)
				{
					Location tempLoc = new Location(row, col);
					if (getGrid().get(tempLoc) == null && getGrid().getNeighbors(tempLoc).size() > 0)
						possibleLocs.add(tempLoc);
				}
			}
			//Finds the location that flips over all of the pieces
			for (Location possible : possibleLocs)
			{
				int max = determineBest(possible);
				if (max > maxPieces)
				{
					loc1 = possible;
					maxPieces = max;
				}
			}
			if (loc1 == null)
				loc1 = possibleLocs.get((int) (Math.random() * possibleLocs.size()));
			add(loc1, new Othello(userColor));
			searchHorizontal(loc1.getRow(), loc1.getCol(), false);
			searchVertical(loc1.getRow(), loc1.getCol(), false);
			searchDiagonalDLUR(loc1.getRow(), loc1.getCol(), false);
			searchDiagonalULDR(loc1.getRow(), loc1.getCol(), false);
			
			if (userColor.equals(Color.BLACK)) //change color
				userColor = Color.WHITE;
			else
				userColor = Color.BLACK;	
		}
		
		String message = "Black: " + countColor(Color.BLACK) + " White: " + countColor(Color.WHITE);
		if (getGrid().getOccupiedLocations().size() >= 63)
		{	displayEndGame(); return true; }
		if (userColor == Color.WHITE)
		{	message += "\nWhites turn."; }
		else
		{	message += "\nBlacks turn."; }
		setMessage(message);
		
		return true;
	}
	/**
	* Counts the number of pieces that would be flipped if 
	* that location was the computer's choice
	* @param loc the location that is being tested 
	* @return the number of pieces that would be flipped
	*/
	private int determineBest(Location loc)
	{
		return searchHorizontal(loc.getRow(), loc.getCol(), true)
				+ searchVertical(loc.getRow(), loc.getCol(), true)
				+ searchDiagonalDLUR(loc.getRow(), loc.getCol(), true)
				+ searchDiagonalULDR(loc.getRow(), loc.getCol(), true);
	}
	/**
	* Displays who wins as a JOptionPane
	*/
	private void displayEndGame()
	{
		int black = countColor(Color.BLACK);
		int white = countColor(Color.WHITE);
		if (black > white)
			JOptionPane.showMessageDialog(null, "Game over", "Black wins!", JOptionPane.CLOSED_OPTION);
		else if(black < white)
			JOptionPane.showMessageDialog(null, "Game over", "White wins!", JOptionPane.CLOSED_OPTION);
		else
			JOptionPane.showMessageDialog(null, "Game over", "It's a tie game!", JOptionPane.CLOSED_OPTION);
	}
	/**
	* Counts the number of pieces that will be flipped horizontally
	* @param row the row of the location
	* @param col the column of the location
	* @search true if no pieces are to be flipped, false if otherwise 
	* @return the number of pieces that are, or would be, flipped
	*/
	private int searchHorizontal(int row, int col, boolean search)
	{
		int maxToFlip = 0;
		Grid<Othello> gr = getGrid();
		int colCounter = col;
		while(colCounter > 0)
		{
			colCounter--;
			Othello o = gr.get(new Location(row, colCounter));
			if(o != null && o.getColor().equals(userColor))
			{
				if (!search)
				{
					if (col <= colCounter)
						flipHorizontal(col, colCounter, row);
					else
						flipHorizontal(colCounter, col, row);
				}
				maxToFlip += Math.abs(col - colCounter) - 1;
				break;
			}
			else if (o == null)
			{
				break;
			}
		}
		colCounter = col;
		while(colCounter < 7)
		{
			colCounter++;
			Othello o = gr.get(new Location(row, colCounter));
			if(o != null && o.getColor().equals(userColor))
			{
				if (!search)
				{	
					flipHorizontal(col, colCounter, row);
				}
				maxToFlip += Math.abs(colCounter - col) - 1;
				break;
			}
			else if (o == null)
				break;
		}
		show();
		return maxToFlip;
	}	
	/**
	* Counts the number of pieces that will be flipped diagonally
	* @param row the row of the location
	* @param col the column of the location
	* @search true if no pieces are to be flipped, false if otherwise 
	* @return the number of pieces that are, or would be, flipped
	*/
	private int searchDiagonalULDR(int row, int col, boolean search)
	{
		int maxToFlip = 0;
		int rowCounter = row;
		int colCounter = col;
		while(rowCounter < 7 && colCounter < 7)
		{
			rowCounter++;
			colCounter++;
			Othello o = getGrid().get(new Location(rowCounter, colCounter));
			if(o != null && o.getColor().equals(userColor))
			{
				if (!search)
				{flipDiagonalULDR(row, rowCounter, col, colCounter); }
				maxToFlip += Math.abs(rowCounter - row) - 1;
				break;
			}
			else if (o == null)
				break;
		}		
		rowCounter = row;
		colCounter = col;
		while(rowCounter > 0 && colCounter > 0)
		{
			rowCounter--;
			colCounter--;
			Othello o = getGrid().get(new Location(rowCounter, colCounter));
			if(o != null && o.getColor().equals(userColor))
			{
				if (!search)
				{ flipDiagonalULDR(rowCounter, row, colCounter, col); }
				maxToFlip += Math.abs(rowCounter - row) - 1;
				break;
			}
			else if (o == null)
				break;
		}
		show();
		return maxToFlip;
	}
	/**
	* 'Flips' (sets color) all of the Othello pieces
	*  at locations with given parameters
	* @param rowStart the row of the starting location
	* @param rowEnd the row of the ending location
	* @param colStart the column of the starting location
	* @param colEnd the column of the ending location
	*/
	private void flipDiagonalULDR(int rowStart, int rowEnd, int colStart, int colEnd)
	{
		rowStart++;
		colStart++;
		while(rowStart < rowEnd && colStart < colEnd)
		{
			getGrid().get(new Location(rowStart, colStart)).setColor(userColor);
			rowStart++;
			colStart++;
		}
	}
	/**
	* 'Flips' (sets color) all of the Othello pieces
	*  at locations with given parameters
	* @param start the column of the starting location
	* @param end the column of the ending location
	* @param row the row of these locations
	*/
	private void flipHorizontal(int start, int end, int row)
	{
		for(int i = start + 1; i < end; i++)
			getGrid().get(new Location(row, i)).setColor(userColor);
	}
	/**
	* Counts the number of pieces that will be flipped vertically
	* @param row the row of the location
	* @param col the column of the location
	* @search true if no pieces are to be flipped, false if otherwise 
	* @return the number of pieces that are, or would be, flipped
	*/
	private int searchVertical(int row, int col, boolean search)
	{
		int maxToFlip = 0;
		Grid<Othello> gr = getGrid();
		int rowCounter = row;
		while(rowCounter > 0)
		{
			rowCounter--;
			Othello o = gr.get(new Location(rowCounter, col));
			if(o != null && o.getColor().equals(userColor))
			{
				if (!search)
				{
					if (row <= rowCounter)
						flipVertical(row, rowCounter, col);
					else
						flipVertical(rowCounter, row, col);
				}
				maxToFlip += Math.abs(rowCounter - row) - 1;
				break;
			}
			else if (o == null)
				break;
		}
		rowCounter = row;
		while(rowCounter < 7)
		{
			rowCounter++;
			Othello o = gr.get(new Location(rowCounter, col));
			if(o != null && o.getColor().equals(userColor))
			{
				if (!search)
				{ flipVertical(row, rowCounter, col); }
				maxToFlip += Math.abs(rowCounter - row) - 1;
				break;
			}
			else if (o == null)
				break;
		}
		show();
		return maxToFlip;
	}
	/**
	* 'Flips' (sets color) all of the Othello pieces
	*  at locations with given parameters
	* @param start the row of the starting Location
	* @param end the row of the ending Location
	* @param colPosition the column of all of these locations
	*/
	private void flipVertical(int start, int end, int colPosition)
	{
		for (int i = start + 1; i < end; i++)
			getGrid().get(new Location(i, colPosition)).setColor(userColor);
	}
	/**
	* Counts the number of pieces that will be flipped diagonally
	* @param row the row of the location
	* @param col the column of the location
	* @search true if no pieces are to be flipped, false if otherwise 
	* @return the number of pieces that are, or would be, flipped
	*/
	private int searchDiagonalDLUR(int row, int col, boolean search)
	{
		int maxToFlip = 0;
		Grid<Othello> gr = getGrid();
		int theRow = row;
		int theCol = col;
		while(theRow > 0 && theCol < 7)
		{
			theRow--;
			theCol++;
			Othello o = gr.get(new Location(theRow, theCol));
			if(o != null && o.getColor().equals(userColor))
			{
				if (!search)
				{ flipDiagonalDLUR(theRow, row, col, theCol); }
				maxToFlip += Math.abs(row - theRow) - 1;
				break;
			}
			else if (o == null)
				break;
		}	
		theRow = row;
		theCol = col;
		while(theRow < 7 && theCol > 0)
		{
			theRow++;
			theCol--;
			Othello o = gr.get(new Location(theRow, theCol));
			if(o != null && o.getColor().equals(userColor))
			{
				if (!search)
				{ flipDiagonalDLUR(row, theRow, theCol, col); }
				maxToFlip += Math.abs(row - theRow) - 1;
				break;
			}
			else if (o == null)
				break;
		}	
		show();
		return maxToFlip;
	}
	/**
	* 'Flips' (sets color) all of the Othello pieces
	*  at locations with given parameters
	* @param rowStart the row of the starting location
	* @param rowEnd the row of the ending location
	* @param colStart the column of the starting location
	* @param colEnd the column of the ending location
	*/
	private void flipDiagonalDLUR(int rowStart, int rowEnd, int colStart, int colEnd)
	{
		rowStart++;
		colEnd--;
		while(rowStart < rowEnd && colEnd > colStart)
		{
			Othello o = getGrid().get(new Location(rowStart, colEnd));
			o.setColor(userColor);
			rowStart++;
			colEnd--;
		}
	}
	/**
     * Constructs and shows a frame for this world.
     */
    public void show()
    {
        if (frame == null)
        {
            frame = new WorldFrame<Othello>(this);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(460,565);
            frame.setResizable(false);
            frame.setAlwaysOnTop(true);
            frame.setLocation(500,250);
            frame.setVisible(true);
        }
        else
            frame.repaint();
    }
	/**
	* Resets the board by removing anything from it
	* and resetting all instance variables back to
	* their default value
	*/
    public void reset()
    {
    	setMessage("Black goes first. Click on a grid panel to place a piece.");
    	userColor = Color.BLACK;
    	//Removes anything on board
    	for (Location loc : getGrid().getOccupiedLocations())
    		removeFromGrid(loc);
    	
		add(new Location(3, 3), new Othello(Color.WHITE));
		add(new Location(3, 4), new Othello(Color.BLACK));
		add(new Location(4, 3), new Othello(Color.BLACK));
		add(new Location(4, 4), new Othello(Color.WHITE));
    }
	/**
	* Removes the specified location from the grid
	* @param loc the location
	*/
    public void removeFromGrid(Location loc)
    {
    	if (getGrid() != null)
    		getGrid().remove(loc);
    }
    /**
	* Overrides the newGame method to call reset
	*/
    public void newGame()
    {
    	reset();
    }
}
