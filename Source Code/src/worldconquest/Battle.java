package worldconquest;

import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;
import info.gridworld.gui.*;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.*;

public class Battle extends World<Actor> {
	private static long currentTime;
	private int playerHealth;
	private int computerHealth;
	private static Cursor cursor;
	private static ComputerPlayer computer;
	private static int currentSoldierChoice;
	private WorldConquest thisWorld;
	private Location locationBeingAttacked;
	private boolean playerBeingAttacked;
	public Battle(){
		currentTime=System.currentTimeMillis();
		setGrid(new BoundedGrid<Actor>(10,11));
		currentSoldierChoice=1;
		cursor=new Cursor();
		cursor.putSelfInGrid(getGrid(), new Location(0,0));
		cursor.setBattle(this);
		computer=new ComputerPlayer();
		computer.setBattle(this);
		computer.setGrid(getGrid());
		playerHealth=100;
		computerHealth=computer.getHealthForThisBattle();
		setMessage("Click run to start");
		playerBeingAttacked=false;
	}
	//sets up the keyboard inputs if you press up the cursor moves up, down moves it down, left and right adjusts the soldier choice.
	//and space adds in a soldier
	public void play(){	
			show();
			java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager()
			.addKeyEventDispatcher(new java.awt.KeyEventDispatcher() 
			{
			   public boolean dispatchKeyEvent(java.awt.event.KeyEvent event) 
			   {
			      String key = javax.swing.KeyStroke.getKeyStrokeForEvent(event).toString();
			      if (key.equals("pressed UP"))
			      {
			         cursor.act(true);
			         show();
			      }
			      if (key.equals("pressed DOWN"))
			      {
			    	  cursor.act(false);
			    	  show();
			      }
			      if (key.equals("pressed RIGHT"))
			      {
			         if(currentSoldierChoice<3)
			        	 currentSoldierChoice++;
			         else
			        	 currentSoldierChoice=1;
			      }
			      if (key.equals("pressed LEFT"))
			      {
			    	  if(currentSoldierChoice>1)
			    		  currentSoldierChoice--;
				      else
				    	  currentSoldierChoice=3;
			      }
			      if (key.equals("pressed SPACE"))
			      {
			    	 addNewSoldier();
			    	 show();
			      }
			   return true;
			   }
			});
			
	}
	public void hurtPlayer(){
		playerHealth-=10;
	}
	public void hurtComputer(){
		computerHealth-=10;
	}
	public Battle getBattle(){
		return this;
	}
	// places a soldier in the row of the cursor. This will only run after 1.5 seconds have passed since it was previously called
	// So you can't just keep placing them.
	public void addNewSoldier(){
		Location cursorLoc=cursor.getLocation();
		if (System.currentTimeMillis() - currentTime > 1500){
			currentTime=System.currentTimeMillis();
			Location next=cursorLoc.getAdjacentLocation(90);
			if(getGrid().isValid(next)&&getGrid().get(next)==null){
	       	 	Soldier s;
	       	 	if(currentSoldierChoice==1)
	       	 		s=new Swordsman(Color.BLUE);
	       	 	else if(currentSoldierChoice==2)
	       	 		s=new Spearman(Color.BLUE);
	       	 	else
	       	 		s=new Axeman(Color.BLUE);
	       	 	s.setBattle(getBattle());
	       	 	s.putSelfInGrid(getGrid(), new Location(cursorLoc.getRow(),1));
	       }
		}
	}
	//resets the grid, instance fields, and frame, so that another battle can be played
	public void reset(){
		setGrid(new BoundedGrid<Actor>(10,11));
		locationBeingAttacked=null;
		currentSoldierChoice=1;
		cursor=new Cursor();
		cursor.putSelfInGrid(getGrid(), new Location(0,0));
		cursor.setBattle(this);
		computer.setGrid(getGrid());
		playerHealth=100;
		computerHealth=computer.getHealthForThisBattle();
		JFrame frame = new WorldFrame<Actor>(this);
        frame.setSize(700,700);
        frame.setVisible(true);
        setFrame(frame);
        show();
		setMessage("Click run to start");
	}
	public ComputerPlayer getComputer(){
		return computer;
	}
	//checks to see if the battle is done by seeing if either the player or computer's health is less than or equal to 0;
	public boolean isDone(){
		if(playerHealth>0&&computerHealth>0)
			return false;
		return true;
	}
	/*ends the game, by displaying whether the player won or lost, running the stop method so that step stops running,
	 * and closing the frame. If the Player is the one attacking, and they win, it has the WorldConquest class add in a new occupying army,
	 * increments the computer's level and checks to see if the game is done. If the player is being attacked, it does the opposite, so that
	 * if the player loses, a new computer occupying army is put into place.
	 * It then tells the WorldConquest class to move on to the next turn
	 */
	public void endGame(){
		if(playerBeingAttacked==false){
			if(playerHealth<=0){
				JOptionPane.showMessageDialog(null, "You lost the Battle", "You Lose", JOptionPane.ERROR_MESSAGE);
				JFrame frame=getFrame();
				((WorldFrame) frame).getGUIController().stop();
				frame.dispose();
			}
			else{
				JOptionPane.showMessageDialog(null, "You won the Battle", "You Win", JOptionPane.ERROR_MESSAGE);
				computer.incrementLevel();
				OccupyingArmy o=new OccupyingArmy();
				if(thisWorld.getGrid().get(locationBeingAttacked)!=null)
					thisWorld.getGrid().get(locationBeingAttacked).removeSelfFromGrid();
				o.putSelfInGrid(thisWorld.getGrid(), locationBeingAttacked);
				o.setColor(Color.BLUE);
				thisWorld.show();
				JFrame frame=getFrame();
				((WorldFrame) frame).getGUIController().stop();
				frame.dispose();
				if(thisWorld.isDone())
					thisWorld.endGame();
			}
		}
		else{
			if(playerHealth<=0){
				JOptionPane.showMessageDialog(null, "You lost the Battle", "You Lose", JOptionPane.ERROR_MESSAGE);
				if(thisWorld.getGrid().get(locationBeingAttacked)!=null)
					thisWorld.getGrid().get(locationBeingAttacked).removeSelfFromGrid();
				OccupyingArmy o=new OccupyingArmy();
				o.putSelfInGrid(thisWorld.getGrid(), locationBeingAttacked);
				o.setColor(computer.getColor());
				thisWorld.show();
				JFrame frame=getFrame();
				((WorldFrame) frame).getGUIController().stop();
				frame.dispose();
				if(thisWorld.isDone())
					thisWorld.endGame();
			}
			else{
				JOptionPane.showMessageDialog(null, "You won the Battle", "You Win", JOptionPane.ERROR_MESSAGE);
				JFrame frame=getFrame();
				((WorldFrame) frame).getGUIController().stop();
				frame.dispose();
			}
		}
		thisWorld.nextTurn();
		playerBeingAttacked=false;
		thisWorld.computersTurns();
	}
	//has the computer make its move, all the actors move, and displays which soldier choice the player is on
	public void battling(){

		computer.fightBattle();
		ArrayList<Location> allActors=getGrid().getOccupiedLocations();
		for(int i=0; i<allActors.size();i++){
			if(getGrid().get(allActors.get(i))!=null&&getGrid().get(allActors.get(i)) instanceof Soldier)
				getGrid().get(allActors.get(i)).act();
		}
		String currentSoldier;
		if(currentSoldierChoice==1)
   	 		currentSoldier="Swordsman";
   	 	else if(currentSoldierChoice==2)
   	 		currentSoldier="Spearman";
   	 	else
   	 		currentSoldier="Axeman";
		this.setMessage("Player Health: " + playerHealth+"        Enemy Health: "+computerHealth+"\n Current Soldier: "+currentSoldier);
		show();
	}
	//Overrides the step method, so that it also checks to see if the game is done.
	public void step()
	{
	        repaint();
	        if(!isDone())
	        	battling();
	        else
	        {
	        	endGame();
	        }
	        		
	}
	public void setWorldConquest(WorldConquest world)
	{
		thisWorld=world;
	}
	public void setLocationBeingAttacked(Location loc)
	{
		locationBeingAttacked=loc;
	}
	//tells the Battle class the player is being attacked.
	public void playerIsBeingAttacked()
	{
		playerBeingAttacked=true;
	}
}
