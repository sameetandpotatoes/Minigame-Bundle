package minesweeper;
import info.gridworld.actor.Actor;
import java.util.ArrayList;
import info.gridworld.grid.Location;
import java.awt.Color;

import javax.swing.JOptionPane;


public class Square extends Actor
{
	private boolean clicked;
	public boolean isMine;
	private int neighborMines;
	public Square()
	{
		// sets randomly to mine or regular square, initializes fields
		neighborMines = 0;
		clicked = false;
		
		//Random chance
		int prob = (int) (Math.random() * 8);
		if (prob == 0)
			isMine=true;
		else
			isMine= false;	
	}
	/**
	* @return true if isMine is true
	*/
	protected boolean isMine()
	{
		return isMine;
	}
	/**
	* Sets the number of neighbor mines to number specified
	* @param the number to set the neighbor mines equal to
	*/
	protected void setNeighborMines(int num)
	{
		neighborMines = num;
	}
	/**
	* Sets clicked to equal to true
	*/
	protected void makeClicked()
	{
		clicked = true;
	}
	/**
	* @return true if clicked is true
	*/
	protected boolean isClicked()
	{
		return clicked;
	}
	/**
	* Shows what the square contains and puts it in the grid
	*/
	protected void click()
	{
		if (clicked) 
			return;
		else 
			clicked = true;
		if (isMine)
		{
			new BombSquare().putSelfInGrid(getGrid(), getLocation());
		}	 
		else
		{ 
			ArrayList<Actor> neighbors=getGrid().getNeighbors(getLocation());
			// if no bombs around, clicks all surrounding squares;
			// otherwise, displays number of surrounding mines
			if (neighborMines==0)
			{
				new ZeroSquare().putSelfInGrid(getGrid(), getLocation());
				for (Actor a:neighbors)
					((Square) a).click();		
			}
			else if (neighborMines==1)
				new OneSquare().putSelfInGrid(getGrid(), getLocation());
			else if (neighborMines==2)
				new TwoSquare().putSelfInGrid(getGrid(), getLocation());
			else if (neighborMines==3)
				new ThreeSquare().putSelfInGrid(getGrid(), getLocation());
			else if (neighborMines==4)
				new FourSquare().putSelfInGrid(getGrid(), getLocation());
			else if (neighborMines==5)
				new FiveSquare().putSelfInGrid(getGrid(), getLocation());
			else if (neighborMines==6)
				new SixSquare().putSelfInGrid(getGrid(), getLocation());
			else if (neighborMines==7)
				new SevenSquare().putSelfInGrid(getGrid(), getLocation());
			else if (neighborMines==8)
				new EightSquare().putSelfInGrid(getGrid(), getLocation());
		}
	}
	/**
	* @return a string representation of this class
	*/
	public String toString()
	{
		return "a square";
	}
}

