package chess;

import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.gui.WorldFrame;
import info.gridworld.world.World;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ChessWorld extends World<Actor>
{
	private Piece lastP;
	private static Grid<Actor> gr = new BoundedGrid(8,8);
	private boolean whiteTurn;
	private boolean blackCheck;
	private boolean whiteCheck;
	private boolean gameOver;
	public ChessWorld()
	{
		super(gr);
		reset();
	}
	/*
	*if loc is empty, if lastP is not empty, move lastP to loc 
	*else if loc is not empty, p.select();
	*/ 
	public boolean locationClicked(Location loc)
	{
		boolean kingCanMove = false;
		if (!gameOver)
		{
			Piece p = (Piece) getGrid().get(loc);
			
			if (lastP != null && ((lastP.getColor().equals(Color.WHITE) && !whiteTurn) || (lastP.getColor().equals(Color.BLACK) && whiteTurn)))
			{
				lastP = null;
				return true;
			}
			if (p != null && lastP != null && p.getColor().equals(lastP.getColor()))
			{
				lastP = null;
				return true;
			}
			
			if (p == null)
			{
				if (lastP != null)
				{
					if ((lastP.getColor().equals(Color.WHITE) && whiteTurn) || (lastP.getColor().equals(Color.BLACK) && !whiteTurn))
					{
						if (lastP instanceof King)
						{
							if(checkLoc(lastP, loc))
							{
								lastP = null;
								return true;
							}
							else
								kingCanMove = true;
						}
						if (lastP.canMove(lastP.getLocation(), loc) && (!whiteCheck || !checkKing(findKing(Color.WHITE)) && (!blackCheck || !checkKing(findKing(Color.BLACK)))|| kingCanMove))
						{
							Location oldLoc = lastP.getLocation();
							lastP.moveTo(loc);
							if(!whiteTurn && checkKing(findKing(Color.BLACK)) || whiteTurn && checkKing(findKing(Color.WHITE)))
							{
								lastP.moveTo(oldLoc); //moves it back
								lastP = null;
								return true;
							}
							//move is successful
							whiteTurn = !whiteTurn;
							
							if (whiteTurn)
								setMessage("White's turn.");
							else
								setMessage("Black's turn.");
							
							if (checkKing(findKing(Color.BLACK)))
							{
								setMessage("Black king is in check!");
								if (checkMate(findKing(Color.BLACK), loc) || blackCheck)
								{
									JOptionPane.showMessageDialog(null, "Game over!", "Black wins!", JFrame.DISPOSE_ON_CLOSE);
									gameOver = true;
								}
								else
									blackCheck = true;
							}
							else
								blackCheck = false;
							
							if (checkKing(findKing(Color.WHITE)))
							{
								setMessage("White king is in check!");
								if (checkMate(findKing(Color.WHITE), loc) || blackCheck)
								{
									JOptionPane.showMessageDialog(null, "Game over!", "Black wins!", JFrame.DISPOSE_ON_CLOSE);
									gameOver = true;
								}
								else
									whiteCheck = true;
							}
							else
								whiteCheck = false;
							lastP = null;
						}
					}
				}
			}
			else if (p != null)
			{
				if (lastP != null)
				{
					if ((lastP.getColor().equals(Color.WHITE) && whiteTurn || (lastP.getColor().equals(Color.BLACK) && !whiteTurn)))
					{
						if (lastP instanceof King)
						{
							Piece temp = (Piece) getGrid().get(loc);
							temp.removeSelfFromGrid();
							boolean result = checkLoc(lastP, loc);
							temp.putSelfInGrid(getGrid(), loc);
							if(result)
							{
								lastP = null;
								return true;
							}	
							else	
								kingCanMove = true;
						}
						if(p instanceof King)
							return true;
						if (lastP.canMove(lastP.getLocation(), loc) && (!whiteCheck || !checkKing(findKing(Color.WHITE)) && (!blackCheck || !checkKing(findKing(Color.WHITE))) || kingCanMove))
						{
							Location oldLoc = lastP.getLocation();
							lastP.moveTo(loc);
							if(!whiteTurn && checkKing(findKing(Color.BLACK)) || whiteTurn && checkKing(findKing(Color.WHITE)))
							{
								lastP.moveTo(oldLoc); //moves it back
								lastP = null;
								return true;
							}
							//move is successful
							whiteTurn = !whiteTurn;
							
							if (whiteTurn)
								setMessage("White's turn.");
							else
								setMessage("Black's turn.");
							
							if (checkKing(findKing(Color.BLACK)))
							{
								setMessage("Black king is in check!");
								if (checkMate(findKing(Color.BLACK), loc) || blackCheck)
								{
									JOptionPane.showMessageDialog(null, "Checkmate!", "Black wins!", JFrame.DISPOSE_ON_CLOSE);
									gameOver = true; 
								}
								else
								{
									blackCheck = true;
								}
							}
							else
								blackCheck = false;
							
							if (checkKing(findKing(Color.WHITE)))
							{
								setMessage("White king is in check!");
								if (checkMate(findKing(Color.WHITE), loc) || whiteCheck)
								{
									JOptionPane.showMessageDialog(null, "Checkmate!", "Black wins!", JFrame.DISPOSE_ON_CLOSE);
									gameOver = true;
								}
								else
								{
									whiteCheck = true;
								}
							}
							else
								whiteCheck = false;
						}
						lastP = null;
					}
				}
				else if (lastP == null)
				{
					lastP = p;
				}
			}
			if (whiteCheck)
			{
				if (!checkKing(findKing(Color.WHITE)))
				{
					whiteTurn = !whiteTurn;
					
					if (whiteTurn)
						setMessage("White's turn.");
					else
						setMessage("Black's turn.");
					lastP = null;
					whiteCheck = false;
				}
			}
			if (blackCheck)
			{
				if (!checkKing(findKing(Color.BLACK)))
				{
					whiteTurn = !whiteTurn;
					
					if (whiteTurn)
						setMessage("White's turn.");
					else
						setMessage("Black's turn.");
					
					blackCheck = false;
					lastP = null;
				}
			}
		}	
		return true;
	}

	public void reset()
	{
		whiteTurn = true;
		lastP = null;
		gameOver = false;
		whiteCheck = false;
		blackCheck = false;
		
		//removes anything on it first
		for(Location loc : getGrid().getOccupiedLocations())
			getGrid().get(loc).removeSelfFromGrid();
				
		//adding all white pieces
		add(new Location(7, 0), new Rook(Color.WHITE, getGrid(), new Location(7, 0)));
		add(new Location(7, 1), new Knight(Color.WHITE, getGrid(), new Location(7, 1)));
		add(new Location(7, 2), new Bishop(Color.WHITE, getGrid(), new Location(7, 2)));
		add(new Location(7, 3), new Queen(Color.WHITE, getGrid(), new Location(7, 3)));
		add(new Location(7, 4), new King(Color.WHITE, getGrid(), new Location(7, 4)));
		add(new Location(7, 5), new Bishop(Color.WHITE, getGrid(), new Location(7, 5)));
		add(new Location(7, 6), new Knight(Color.WHITE, getGrid(), new Location(7, 6)));
		add(new Location(7, 7), new Rook(Color.WHITE, getGrid(), new Location(7, 7)));
		for (int c = 0; c < getGrid().getNumCols(); c++)
			add(new Location(6, c), new Pawn(Color.WHITE, getGrid(), new Location(6, c)));
		
		//adding all black pieces
		add(new Location(0, 0), new Rook(Color.BLACK, getGrid(), new Location(0, 0)));
		add(new Location(0, 1), new Knight(Color.BLACK, getGrid(), new Location(0, 1)));
		add(new Location(0, 2), new Bishop(Color.BLACK, getGrid(), new Location(0, 2)));
		add(new Location(0, 3), new Queen(Color.BLACK, getGrid(), new Location(0, 3)));
		add(new Location(0, 4), new King(Color.BLACK, getGrid(), new Location(0, 4)));
		add(new Location(0, 5), new Bishop(Color.BLACK, getGrid(), new Location(0, 5)));
		add(new Location(0, 6), new Knight(Color.BLACK, getGrid(), new Location(0, 6)));
		add(new Location(0, 7), new Rook(Color.BLACK, getGrid(), new Location(0, 7)));
		for (int c = 0; c < getGrid().getNumCols(); c++)
			add(new Location(1, c), new Pawn(Color.BLACK, getGrid(), new Location(1, c)));
		
		setMessage("Directions: To move a piece, click on the piece\nand then click on the location you want to move to.");
	}
	
	public Location findKing(Color color)
	{
		for (Location loc : getGrid().getOccupiedLocations())
		{
			if (getGrid().get(loc) instanceof King)
			{
				if (getGrid().get(loc).getColor().equals(color)) 
					return loc;
			}				
		}
		return null;
	}
	/**
     * Checks if the location given will cause there to be a check
     * @param piece the location that the method will check
     * @return true if this location will result in a check 
     */
	public boolean checkKing(Location piece)
	{
		ArrayList<Location> occupied = getGrid().getOccupiedLocations();
		for (Location loc : occupied)
		{
			Piece p = (Piece) getGrid().get(loc);
			ArrayList<Location> locs = p.getMoves(loc);
			if (p instanceof Pawn)
				locs = (((Pawn) p).getAttackMoves(loc));
			for (Location move : locs)
			{
				if (getGrid().get(piece) != null)
				{
					if (move.equals(piece) && !p.getColor().equals(getGrid().get(piece).getColor()))
						return true;
				}
			}
		}
		return false;
	}
	public boolean checkKing(Piece piece, Location to, Color color)
	{
		ArrayList<Location> occupied = getGrid().getOccupiedLocations();
		for (Location loc : occupied)
		{
			Piece p = (Piece) getGrid().get(loc);
			ArrayList<Location> locs = p.getMoves(loc);
			if (p instanceof Pawn)
				locs = (((Pawn) p).getAttackMoves(loc));
			for (Location move : locs)
			{
				if(move.equals(to) && !checkKing(findKing(color)))
					return false;
			}
		}
		return true;
	}
	public boolean checkLoc(Piece king, Location to)
	{
		ArrayList<Location> occupied = getGrid().getOccupiedLocations();
		for (Location loc : occupied)
		{
			Piece enemy = (Piece) getGrid().get(loc);
			ArrayList<Location> locs = enemy.getMoves(loc);
			if (enemy instanceof Pawn)
				locs = (((Pawn) enemy).getAttackMoves(loc));
			for (Location enemyMoves : locs)
			{
				if(enemy.getColor().equals(king.getOppositeColor()) && enemyMoves.equals(to))
					return true;
			}
		}
		return false;
	}
	public boolean checkMate(Location piece, Location enemy) 
	{
		Piece king = (Piece) getGrid().get(piece);
		
		//Checks for empty/enemy adjacent locations to move to
		for (Location loc : getGrid().getValidAdjacentLocations(piece))
		{
			Piece p = (Piece) getGrid().get(loc);
			if (p == null && !checkLoc(king, loc))
			{ return false; }
			else if (p != null && p.getColor().equals(king.getOppositeColor()) && !checkLoc(king, loc))
			{ return false; }
		}
		//checks if other pieces can move in the way to block enemy
		
		//first gets the pieces of the king's
		ArrayList<Location> playerPieces = new ArrayList<Location>();
		for (Location loc : getGrid().getOccupiedLocations())
		{
			if (getGrid().get(loc).getColor().equals(king.getColor()))
				playerPieces.add(loc);
		}
		
		ArrayList<Location> locs = getMovesInBetween(enemy, piece);
		for (Location loc : locs)
		{
			for (Location player : playerPieces)
			{
				Piece p = (Piece) getGrid().get(player);
				if (!(p instanceof King))
				{	
					for (Location move : p.getMoves(player))
					{
						if (move.equals(loc))
						{ return false; }
					}
				}
			}
		}
		return true;
	}
    public void show()
    {
        if (frame == null)
        {
            frame = new WorldFrame<Actor>(this);
            frame.setSize(460,565);
            frame.setResizable(false);
            frame.setLocation(500,250);
            frame.setVisible(true);
        }
        else
            frame.repaint();
    }
    private ArrayList<Location> getMovesInBetween(Location from, Location to)
    {
    	ArrayList<Location> ans = new ArrayList<Location>();
    	ans.add(from);
    	int direction = from.getDirectionToward(to);
    	while (!from.equals(to))
    	{
    		ans.add(from.getAdjacentLocation(direction));
    		from = from.getAdjacentLocation(direction);
    	}
    	ans.remove(ans.size() - 1);
    	return ans;
    }
    public void newGame()
    {
    	reset();
    }
}
