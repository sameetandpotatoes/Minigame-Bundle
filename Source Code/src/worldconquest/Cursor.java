package worldconquest;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

public class Cursor extends Actor {
	private Battle myBattle;
	//moves the cursor up or down if it can
	public void act(boolean up){
		
			if(up){
				if(getLocation().getRow()>0)
		       	 		moveTo(new Location(getLocation().getRow()-1,0));
				myBattle.show();
			}
			else{
				if(getLocation().getRow()<9)
		        	 moveTo(new Location(getLocation().getRow()+1,0));
				myBattle.show();
			}
		
		myBattle.show();
	}
	public void setBattle(Battle b){
		myBattle=b;
	}
}
