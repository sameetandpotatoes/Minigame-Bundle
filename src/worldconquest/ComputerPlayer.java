package worldconquest;

import info.gridworld.grid.*;
import info.gridworld.actor.*;
import java.awt.Color;

public class ComputerPlayer {
	private int level;
	private Color color;
	private Grid<Actor> gr;
	private Battle battle;
	private static long currentTime;
	public ComputerPlayer(){
		level=1;
		color=Color.RED;
		currentTime=System.currentTimeMillis();
	}
	public void setGrid(Grid<Actor> g){
		gr=g;
	}
	public void setColor(Color c){
		color=c;
	}
	public void setBattle(Battle b){
		battle=b;
	}
	public Color getColor(){
		return color;
	}
	//Decides whether it will counter(counters by attacking row with most soldiers) or put a soldier in a random location.
	//it also only runs if pause(adjusts based off of level) amount of time has passed
	public void fightBattle(){
		int pause=3000-(level*200);
		if(pause<200)
			pause=200;
		int counterOrNot=(int)(Math.random()*2);
		if(counterOrNot==0){
			int row=(int) (Math.random()*gr.getNumRows());
			Location loc=new Location(row, gr.getNumCols()-1);
			
			if (System.currentTimeMillis() - currentTime > pause){
				currentTime=System.currentTimeMillis();
			
				if(gr.isValid(loc)&&gr.get(loc)==null){
		       	 	Soldier s;
		       	 	int currentSoldierChoice=(int) (Math.random()*3);
		       	 	if(currentSoldierChoice==0)
		       	 		s=new Swordsman(color);
		       	 	else if(currentSoldierChoice==1)
		       	 		s=new Spearman(color);
		       	 	else
		       	 		s=new Axeman(color);
		       	 	s.setBattle(battle);
		       	 	s.putSelfInGrid(gr, loc);
				}
			}		
		}
		else{
			
			int row= rowWithMostEnemies();
			Location loc=new Location(row, gr.getNumCols()-1);
			if (System.currentTimeMillis() - currentTime > pause){
				currentTime=System.currentTimeMillis();
				if(gr.isValid(loc)&&gr.get(loc)==null){
		       	 	Soldier s;
		       	 	int currentSoldierChoice=(int) (Math.random()*3);
		       	 	if(currentSoldierChoice==0)
		       	 		s=new Swordsman(color);
		       	 	else if(currentSoldierChoice==1)
		       	 		s=new Spearman(color);
		       	 	else
		       	 		s=new Axeman(color);
		       	 	s.setBattle(battle);
		       	 	s.putSelfInGrid(gr, loc);
		       }
			}
		}
		battle.show();
	}
	public void incrementLevel(){
		level++;
	}
	public int getHealthForThisBattle(){
		return 100+((level-1)*15);
	}
	//figures out the row with the most enemies, if no enemies returns first row
	public int rowWithMostEnemies(){
		int row=0;
		int maxEnemies=0;
		int thisRow=0;
		for(int r=0;r<gr.getNumRows();r++){
			for(int c=0;c<gr.getNumCols();c++){
				Actor actor=gr.get(new Location(r,c));
				if(actor instanceof Soldier	&&	actor.getColor().equals(Color.BLUE))
					thisRow++;
			}
			if(thisRow>maxEnemies){
				row=r;
				maxEnemies=thisRow;
			}
			thisRow=0;
		}
		return row;
	}
}

