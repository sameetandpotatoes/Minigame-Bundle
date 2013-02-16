package worldconquest;


import info.gridworld.actor.*;
import info.gridworld.grid.*;
import info.gridworld.gui.WorldFrame;
import info.gridworld.world.*;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class WorldConquest extends World<Actor> {
	private Battle myBattles;
	private boolean[] turn;
	public WorldConquest(){
		setGrid(new BoundedGrid<Actor>(6,6));
		setUpArmies();
		setUpOceans();
		setMessage("Click on a location adjacent to your armies to attack.");
		myBattles=new Battle();
		myBattles.setWorldConquest(this);
		turn=new boolean[4];
		turn[0]=true;
		turn[1]=false;
		turn[2]=false;
		turn[3]=false;
		show();
	}
	//When a user clicks on a location it checks to see if it is their turn and if they can invade that loc, if so a battle occurs
	public boolean locationClicked(Location loc)
	{
		if(turn[0]==false)
			return false;
		if ((getGrid().get(loc) == null || (getGrid().get(loc).getColor() !=null &&!getGrid().get(loc).getColor().equals(Color.BLUE)))&&canAttack(loc))
		{	
				myBattles.reset(); 
				myBattles.setLocationBeingAttacked(loc);
				if(getGrid().get(loc)!=null)
			    	myBattles.getComputer().setColor(getGrid().get(loc).getColor());
				myBattles.show();
				myBattles.play();
				myBattles.show();
			return true;
		}
		else
			return false;
		
	}
	//sets up the different colored armies in the corners
	public void setUpArmies(){
		for(int r=0;r<getGrid().getNumRows();r++){
			for(int c=0;c<getGrid().getNumRows();c++){
				if(r<2&&c<2){
					OccupyingArmy o=new OccupyingArmy();
					o.putSelfInGrid(getGrid(), new Location(r,c));
					o.setColor(Color.BLUE);
				}
				else if(r>getGrid().getNumRows()-3&&c<2){
					OccupyingArmy o=new OccupyingArmy();
					o.putSelfInGrid(getGrid(), new Location(r,c));
					o.setColor(Color.RED);
				}
				else if(r<2&&c>getGrid().getNumCols()-3){
					OccupyingArmy o=new OccupyingArmy();
					o.putSelfInGrid(getGrid(), new Location(r,c));
					o.setColor(Color.GREEN);
				}
				else if(r>getGrid().getNumRows()-3&&c>getGrid().getNumCols()-3){
					OccupyingArmy o=new OccupyingArmy();
					o.putSelfInGrid(getGrid(), new Location(r,c));
					o.setColor(Color.YELLOW);
				}
				
			}
		}
	}
	//randomly puts oceans on the grid in empty locations
	public void setUpOceans(){
		for(int i=0;i<5;i++){
			new Ocean().putSelfInGrid(getGrid(), getRandomEmptyLocation());
		}
	}
	//Checks to see if this location can be attacked by the player by checking if it has at least one adjacent Player army next to it
	public boolean canAttack(Location loc){
		ArrayList<Actor> neighbors=getGrid().getNeighbors(loc);
		for(Actor a : neighbors){
			if(!(a instanceof Ocean) &&a.getColor()!=null && a.getColor().equals(Color.BLUE))
				return true;
		}
		return false;
	}
	/*First sees if the player's armies are all gone, if it passes that point,
	 * it checks if the first location has the same color as all the others, if so, the game is done.
	*/
	public boolean isDone(){
		Grid<Actor> gr= getGrid();
		boolean playerLost=true;
		for(int r=0;r<gr.getNumRows();r++){
			for(int c=0;c<gr.getNumCols();c++){
				Location loc= new Location(r,c);
				if(gr.get(loc)!=null&&(gr.get(loc) instanceof OccupyingArmy&&gr.get(loc).getColor().equals(Color.BLUE)))
					playerLost= false;
			}
		}
		if(playerLost)
			return true;
		Color color=null;
		if(getGrid().get(new Location(0,0))!= null)
			color=getGrid().get(new Location(0,0)).getColor();
		for(int r=0;r<gr.getNumRows();r++){
			for(int c=0;c<gr.getNumCols();c++){
				Location loc= new Location(r,c);
				if(gr.get(loc)==null||(gr.get(loc) instanceof OccupyingArmy&&!gr.get(loc).getColor().equals(color)))
					return false;
			}
		}
		return true;
	}
	//Precondition:Game is done
	//It sees if the player still has armies if so, the player won, otherwise they lost
	public void endGame(){
		Grid<Actor> gr= getGrid();
		Color color=gr.get(new Location(0,0)).getColor();
		boolean playerWon=false;
		for(int r=0;r<gr.getNumRows();r++){
			for(int c=0;c<gr.getNumCols();c++){
				Location loc= new Location(r,c);
				if(gr.get(loc)!=null&&(gr.get(loc) instanceof OccupyingArmy&&gr.get(loc).getColor().equals(Color.BLUE)))
					playerWon= true;
			}
		}
		if(playerWon)
			JOptionPane.showMessageDialog(null, "You now rule the world", "You Win", JOptionPane.ERROR_MESSAGE);
		else
			JOptionPane.showMessageDialog(null, "You are now enslaved by Enemy Armies. Enjoy endless years of suffering to come.", "You Lose", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}
	//Game moves on to the next turn
	public void nextTurn(){
		if(turn[0]==true){
			turn[0]=false;
			turn[1]=true;
		}
		else if(turn[1]==true){
			turn[1]=false;
			turn[2]=true;
		}
		else if(turn[2]==true){
			turn[2]=false;
			turn[3]=true;
		}
		else if(turn[3]==true){
			turn[3]=false;
			turn[0]=true;
		}
	}
	//if it is the computer's turn, it makes its decision and adjusts the map accordingly
	//if it attacks a player a battle occurs
	public void computersTurns(){
		if(turn[1]==true){
			JOptionPane.showMessageDialog(null, "It is now Red's Turn", "Red's Turn", JOptionPane.ERROR_MESSAGE);
			show();
			long currentTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - currentTime < 1000);
			ArrayList<Location> armiesThatCanAttack=getAllRedArmiesThatCanAttack();
			if(armiesThatCanAttack.size()!=0){
				int whichArmy=(int) (Math.random()*armiesThatCanAttack.size());
				Location locToAttack=whichLocToAttack(getGrid().get(armiesThatCanAttack.get(whichArmy)));
				if(locToAttack!=null){
					Actor a=getGrid().get(locToAttack);
					if(a==null||!a.getColor().equals(Color.BLUE)){
						int win=(int)(Math.random()*2);
						if(win==1){
							if(a!=null)
								a.removeSelfFromGrid();
							OccupyingArmy o=new OccupyingArmy();
							o.putSelfInGrid(getGrid(), locToAttack);
							o.setColor(Color.RED);
							show();
						}
						nextTurn();
					}
					else{
						myBattles.reset(); 
						myBattles.playerIsBeingAttacked();
						myBattles.setLocationBeingAttacked(locToAttack);
					    myBattles.getComputer().setColor(Color.RED);
						myBattles.show();
						myBattles.play();
						myBattles.show();
					}
				}
			}
			else{
				nextTurn();
			}
			show();
			currentTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - currentTime < 1000);
		}
		if(turn[2]==true){
			JOptionPane.showMessageDialog(null, "It is now Yellow's Turn", "Yellow's Turn", JOptionPane.ERROR_MESSAGE);
			show();
			long currentTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - currentTime < 1000);
			ArrayList<Location> armiesThatCanAttack=getAllYellowArmiesThatCanAttack();
			if(armiesThatCanAttack.size()!=0){
				int whichArmy=(int) (Math.random()*armiesThatCanAttack.size());
				Location locToAttack=whichLocToAttack(getGrid().get(armiesThatCanAttack.get(whichArmy)));
				if(locToAttack!=null){
					Actor a=getGrid().get(locToAttack);
					if(a==null||!a.getColor().equals(Color.BLUE)){
						int win=(int)(Math.random()*2);
						if(win==1){
							if(a!=null)
								a.removeSelfFromGrid();
							OccupyingArmy o=new OccupyingArmy();
							o.putSelfInGrid(getGrid(), locToAttack);
							o.setColor(Color.YELLOW);
							show();
						}
						nextTurn();
					}
					else{
						myBattles.reset(); 
						myBattles.playerIsBeingAttacked();
						myBattles.setLocationBeingAttacked(locToAttack);
					    myBattles.getComputer().setColor(Color.YELLOW);
						myBattles.show();
						myBattles.play();
						myBattles.show();
					}
				}
			}
			else{
				nextTurn();
			}
			show();
			currentTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - currentTime < 1000);
		}
		if(turn[3]==true){
			JOptionPane.showMessageDialog(null, "It is now Green's Turn", "Green's Turn", JOptionPane.ERROR_MESSAGE);
			show();
			long currentTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - currentTime < 1000);
			ArrayList<Location> armiesThatCanAttack=getAllGreenArmiesThatCanAttack();
			if(armiesThatCanAttack.size()!=0){
				int whichArmy=(int) (Math.random()*armiesThatCanAttack.size());
				Location locToAttack=whichLocToAttack(getGrid().get(armiesThatCanAttack.get(whichArmy)));
				if(locToAttack!=null){
					Actor a=getGrid().get(locToAttack);
					if(a==null||!a.getColor().equals(Color.BLUE)){
						int win=(int)(Math.random()*2);
						if(win==1){
							if(a!=null)
								a.removeSelfFromGrid();
							OccupyingArmy o=new OccupyingArmy();
							o.putSelfInGrid(getGrid(), locToAttack);
							o.setColor(Color.GREEN);
							show();
						}
						nextTurn();
					}
					else{
						myBattles.reset(); 
						myBattles.playerIsBeingAttacked();
						myBattles.setLocationBeingAttacked(locToAttack);
					    myBattles.getComputer().setColor(Color.GREEN);
						myBattles.show();
						myBattles.play();
						myBattles.show();
					}
				}
			}
			else{
				nextTurn();
			}
			show();
			currentTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - currentTime < 1000);
		}
		if(turn[0]==true){
			JOptionPane.showMessageDialog(null, "It is now Your turn", "Player's Turn", JOptionPane.ERROR_MESSAGE);
		}
	}
	//gets all red armies that have either open locations next to it or locations with opposing armies
	public ArrayList<Location> getAllRedArmiesThatCanAttack(){
		Grid<Actor> gr=getGrid();
		ArrayList<Location> redArmy=new ArrayList<Location>();
		for(int r=0;r<gr.getNumRows();r++){
			for(int c=0;c<gr.getNumCols();c++){
				Location loc= new Location(r,c);
				if(gr.get(loc)!=null&&(gr.get(loc) instanceof OccupyingArmy&&gr.get(loc).getColor().equals(Color.RED))&&canArmyAttack(gr.get(loc)))
					redArmy.add(loc);
			}
		}
		return redArmy;
	}
	//gets all yellow armies that have either open locations next to it or locations with opposing armies
	public ArrayList<Location> getAllYellowArmiesThatCanAttack(){
		Grid<Actor> gr=getGrid();
		ArrayList<Location> yellowArmy=new ArrayList<Location>();
		for(int r=0;r<gr.getNumRows();r++){
			for(int c=0;c<gr.getNumCols();c++){
				Location loc= new Location(r,c);
				if(gr.get(loc)!=null&&(gr.get(loc) instanceof OccupyingArmy&&gr.get(loc).getColor().equals(Color.YELLOW))&&canArmyAttack(gr.get(loc)))
					yellowArmy.add(loc);
			}
		}
		return yellowArmy;
	}
	//gets all green armies that have either open locations next to it or locations with opposing armies
	public ArrayList<Location> getAllGreenArmiesThatCanAttack(){
		Grid<Actor> gr=getGrid();
		ArrayList<Location> greenArmy=new ArrayList<Location>();
		for(int r=0;r<gr.getNumRows();r++){
			for(int c=0;c<gr.getNumCols();c++){
				Location loc= new Location(r,c);
				if(gr.get(loc)!=null&&(gr.get(loc) instanceof OccupyingArmy&&gr.get(loc).getColor().equals(Color.GREEN))&&canArmyAttack(gr.get(loc)))
					greenArmy.add(loc);
			}
		}
		return greenArmy;
	}
	//checks to see if this OccupyingArmy can attack
	public boolean canArmyAttack(Actor a){
		ArrayList<Location> surroundingLocs=getGrid().getValidAdjacentLocations(a.getLocation());
		for(int i=0;i<surroundingLocs.size();i++){
			Actor adjacent=getGrid().get(surroundingLocs.get(i));
			if(adjacent==null||(adjacent instanceof OccupyingArmy&& !adjacent.getColor().equals(a.getColor())))
				return true;
		}
		return false;
	}
	//Randomly picks a location that an army can attack
	public Location whichLocToAttack(Actor a)
	{
		ArrayList<Location> surroundingLocs=getGrid().getValidAdjacentLocations(a.getLocation());
		ArrayList<Location> possibleLocs=new ArrayList<Location>();
		for(int i=0;i<surroundingLocs.size();i++){
			Actor adjacent=getGrid().get(surroundingLocs.get(i));
			if(adjacent==null)
				possibleLocs.add(surroundingLocs.get(i));
			else if((adjacent instanceof OccupyingArmy&& !adjacent.getColor().equals(a.getColor())))
				possibleLocs.add(adjacent.getLocation());
		}
		if(possibleLocs.size()==0)
			return null;
		int whichLocToAttack=(int) (Math.random()*possibleLocs.size());
		return possibleLocs.get(whichLocToAttack);
	}
	public void show()
	{
		if (frame == null)
	    {
			frame = new WorldFrame<Actor>(this);
	        frame.setSize(400,520);
	        frame.setResizable(false);
	        frame.setLocation(500,250);
	        frame.setVisible(true);
	    }
	    else
	    	frame.repaint();
	}
}
