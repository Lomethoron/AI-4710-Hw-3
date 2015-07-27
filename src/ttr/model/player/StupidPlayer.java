package ttr.model.player;

import ttr.model.destinationCards.Route;
import ttr.model.destinationCards.Routes;
import ttr.model.trainCards.TrainCard;
import ttr.model.trainCards.TrainCardColor;

/**
 * A very stupid player that simply draws train cards only. Shown as an example of implemented a player.
 * */
public class StupidPlayer extends Player{
	
	//Routes routes;

	/**
	 * Need to have this constructor so the player has a name, you can use no parameters and pass the name of your player
	 * to the super constructor, or just take in the name as a parameter. Both options are shown here.
	 * */
	public StupidPlayer(String name) {
		super(name);
		//routes = new Routes();
	}
	public StupidPlayer(){
		super("Stupid Player");
		//routes = new Routes();
	}
	
	/**
	 * MUST override the makeMove() method and implement it.
	 * */
	@Override
	public void makeMove(){
		//if I have no dest.tickets
		if(super.getDestinationTickets().isEmpty()){
			super.drawDestinationTickets();
		}
		
		//can I complete any route
		for(Route route: Routes.getInstance().getAllRoutes() ){
			if(!Routes.getInstance().isRouteClaimed(route)){
				TrainCardColor routeColor = route.getColor();
				int routeCost = route.getCost();
				//grey routes
				if(routeColor==TrainCardColor.rainbow){
					for(TrainCardColor color: TrainCardColor.values()){
						int numCardsOfColor = getNumTrainCardsByColor(color);
						if(numCardsOfColor >= routeCost){
							//System.out.println("grey route");
							super.claimRoute(new Route(route.getDest1(),route.getDest2(),route.getCost(), route.getColor()), color);
							break;
						}
					}
				}
				
				//colored routes
				else if(getNumTrainCardsByColor(routeColor)+getNumTrainCardsByColor(TrainCardColor.rainbow)>=routeCost){
					//System.out.println("non-grey route");
					super.claimRoute(new Route(route.getDest1(),route.getDest2(),route.getCost(),route.getColor()), routeColor);
					break;
				}
			}
		}
		
		/* Always draw train cards (0 means we are drawing from the pile, not from the face-up cards) */
		super.drawTrainCard(0);
		
	}
	
	/**
	 * Returns the number of train cards this player has of the given color
	 * */
	private int getNumTrainCardsByColor(TrainCardColor color){
		int count = 0;
		for(TrainCard card : super.getHand()){
			if(card.getColor() == color) count++;
		}
		return count;
	}
	

}
