package worldconquest;

import java.awt.Color;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Soldier extends Actor {
	private int defense;
	private int health;
	private int power;
	private int directionGoing;
	private Battle myBattle;
	public Soldier(int d,int p, Color c){
		health=100;
		defense=d;
		power=p;
		setColor(c);
		if(c.equals(Color.BLUE))
			directionGoing=90;
		else
			directionGoing=270;
	}
	//it fights a soldier directly adjacent in the direction it is going. First one to die is removed from the grid.
	public void attack(){
		Location loc=getLocation().getAdjacentLocation(directionGoing);
		Grid<Actor> gr= getGrid();
		if(canAttack()){
			Soldier opponent=(Soldier)gr.get(loc);
			int powerUsed=power;
			int oppDefense=opponent.getDefense();
			int oppHealth=opponent.getHealth();
			if(oppDefense>0&&powerUsed>oppDefense){
				opponent.setDefense(0);
				powerUsed-=oppDefense;
			}
			if((oppHealth>0&&powerUsed>oppHealth)||oppHealth<=0)
				opponent.removeSelfFromGrid();
			else
				opponent.setHealth(oppHealth-powerUsed);
		}
	}
	public void act(){
		if(canAttack())
			attack();
		else{
			move();
		}
	}
	//Moves in the direction going. If it has reached the end of the Battle Grid, 
	//it removes its self from the grid and hurts the health of the opposing army
	public void move() 
	  { 
	    Grid<Actor> gr = getGrid(); 
	    if (gr == null) 
	      return; 
	    Location loc = getLocation(); 
	    Location next = loc.getAdjacentLocation(directionGoing); 
	    if (gr.isValid(next)&&next.getCol()!=0){ 
	      if(gr.get(next)==null)
	    	moveTo(next); 
	    }
	    else{ 
	      if(myBattle!=null){
	    	  if(getColor().equals(Color.BLUE))
	    		  myBattle.hurtComputer();
	    	  else
	    		  myBattle.hurtPlayer();
	      }
	      removeSelfFromGrid();
	    }
	  } 
	public int getDefense(){
		return defense;
	}
	public int getHealth(){
		return health;
	}
	public int getPower(){
		return power;
	}
	public void setHealth(int h){
		health=h;
	}
	public void setDefense(int d){
		defense=d;
	}
	public void setBattle(Battle b){
		myBattle=b;
	}
	//checks to see if it can attack by seeing if there is an opposing soldier in front of it.
	public boolean canAttack(){
		Location loc=getLocation().getAdjacentLocation(directionGoing);
		Grid<Actor> gr= getGrid();
		if(!gr.isValid(loc)||gr.get(loc)==null||!(gr.get(loc) instanceof Soldier))
			return false;
		else if(gr.get(loc).getColor().equals(getColor()))
			return false;
		else
			return true;
	}
	
}
