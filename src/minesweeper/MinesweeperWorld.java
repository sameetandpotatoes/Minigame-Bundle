package minesweeper;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import info.gridworld.gui.WorldFrame;
import info.gridworld.world.World;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MinesweeperWorld extends World<Actor>
{
	private boolean lose;
    private boolean win;
    private int mineNum;
	public MinesweeperWorld()
	{
		reset();
	}
	/**
	* Handles the events that occur when a user clicks on a GridPanel
	* @param loc the location that the user clicked
	* @return true to avoid showing any public methods
	*/
	public boolean locationClicked(Location loc)
	{
		setMessage("There are " + mineNum + " mines left on the field");
		Square a = (Square) getGrid().get(loc);
		if (a != null)
			a.click(); 
		
		ArrayList<Location> locs = getGrid().getOccupiedLocations();
		int unclickedSquares = 0;
		for (Location loc1:locs)
	    {
			Square x = (Square) getGrid().get(loc1);
			if (x != null)
	   		{
				if (!x.isClicked())
					unclickedSquares++;
			}
	   	}
		
		if (unclickedSquares == mineNum && !checkForMine(locs))
			win = true;
		if (checkForMine(locs))
			lose = true;
		
		
		if (lose)
		{	
			//Shows all of the squares
			for (Location loc1 : getGrid().getOccupiedLocations())
				((Square) getGrid().get(loc1)).click();
			JOptionPane.showMessageDialog(null, "Too bad...", "You lost!", JOptionPane.CLOSED_OPTION); 
		}
		if (win)	
		{	
			JOptionPane.showMessageDialog(null, "Congratulations!", "You won!", JOptionPane.CLOSED_OPTION); 
		}
		return true;
	}	
	/**
	* Checks for a mine among the occupied locations of the grid
	* @param locs the occupied locations in the grid 
	* @return true if there is a BombSquare among locs
	*/
	public boolean checkForMine(ArrayList<Location> locs)
	{
		for (Location loc: locs)
		{	
			if (getGrid().get(loc) instanceof BombSquare)
				return true;
		}
		return false;
	} 
	/**
	* Resets all of the instance variables
	* Instantiates square objects with random assignments
	*/
	public void reset()
	{
		lose = false;
		win = false;
		mineNum = 0;
		setMessage("Welcome to Minesweeper. Please click on a piece.");
		for (int r = 0; r < getGrid().getNumRows(); r++)
		{	
			for (int c = 0; c < getGrid().getNumCols(); c++)
        	{
        		Square temp = new Square();
        		temp.putSelfInGrid(getGrid(), new Location(r, c));
        		add(new Location(r, c), temp);	
        	}
		}
		// gets number of neighboring mines, sets equal to neighborMines 
		for (int r = 0; r < getGrid().getNumRows(); r++)
		{	
			for (int c = 0; c < getGrid().getNumCols(); c++)
			{
				Square number = (Square) getGrid().get(new Location(r, c));
				ArrayList<Actor> neighbors = getGrid().getNeighbors(number.getLocation());
				int count = 0;
				for (int i = 0; i < neighbors.size(); i++)
				{
					if (((Square)neighbors.get(i)).isMine())
						count++;
				}
				number.setNeighborMines(count);	
			}
		}
		//stores number of mines in Grid
        mineNum = 0;
        ArrayList<Location> locs = getGrid().getOccupiedLocations();
        for (Location loc:locs)
        {
        	if (((Square)getGrid().get(loc)).isMine)
        		mineNum++;	
        }
	}
	/**
	* Overrides the World's show method to change the 
	* background and size
	*/
	public void show()
    {
		if (frame == null)
        {
            frame = new WorldFrame<Actor>(this);
            ((WorldFrame<Actor>) frame).getGridPanel().setBackgroundColor(Color.BLUE); //sets background color to blue
            frame.setSize(540,660);
            frame.setResizable(false);
            frame.setLocation(500,250);
            frame.setVisible(true);
        }
        else
            frame.repaint();
    }
	/**
	* Overrides the World's step method so that it
	* carries out the specifications of starting
	* a new game
	*/
	public void newGame()
	{
		reset();
	}
}

