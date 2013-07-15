package othello;

import info.gridworld.actor.Actor;

import java.awt.Color;
public class Othello
{
	private Color color;
	public Othello(Color color)
	{
		this.color = color;	
	}
	/**
	* @return color
	*/
	public Color getColor()
	{ 
		return color;
	}
	/**
	* Sets color to color specifed
	* @param color the color to set the Othello piece to
	*/
	public void setColor(Color color)
	{ 
		this.color = color;
	}
	/**
	* @return a string representation of Othello
	*/
	public String toString()
	{
		if (color.equals(Color.BLACK))
			return "a black piece";
		else
			return "a white piece";
	}
	/**
	* @return empty text
	*/
	public String getText()
	{
		return "";
	}
}
