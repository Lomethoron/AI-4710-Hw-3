package ttr.model.player;

import ttr.model.destinationCards.Route;

public class TTRPlayer extends Player {
	
	public TTRPlayer(String name) {
		super(name);
	}
	public TTRPlayer(){
		super("TTR Player");
	}

	@Override
	public void makeMove() {
		//Do I still have goals to accomplish
			//if no, get new goals
		//attempt to do current goal
			//is it a good goal still?
			//if it isnt move onto next goal
		//what defines a good goal
			//time left in game
			//chance of successful completion
			//interference from opponent
		

	}

}


class Goal {
	Route goalRoute;
	
	/**
	 * Default constructor
	 */
	public Goal(){
		
	}
	
	public Goal(Route route) {
		
	}
	
	public void execute(TTRPlayer player){
		
	}
}