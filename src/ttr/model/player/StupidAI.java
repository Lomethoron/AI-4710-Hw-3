package ttr.model.player;

import java.util.ArrayList;

import ttr.model.destinationCards.Destination;
import ttr.model.destinationCards.Route;
import ttr.model.destinationCards.Routes;
import ttr.model.trainCards.TrainCard;
import ttr.model.trainCards.TrainCardColor;

/**
 * A very stupid player that simply draws train cards only. Shown as an example of implemented a player.
 * */
public class StupidAI extends Player{
	
	Routes routes;

	/**
	 * Need to have this constructor so the player has a name, you can use no parameters and pass the name of your player
	 * to the super constructor, or just take in the name as a parameter. Both options are shown here.
	 * */
	public StupidAI(String name) {
		super(name);
		routes = Routes.getInstance();
	}
	public StupidAI(){
		super("Stupid Player");
		routes = Routes.getInstance();
	}
	
	/**
	 * MUST override the makeMove() method and implement it.
	 * */
	@Override
	public void makeMove(){
		System.out.println("");
		//if I have no dest.tickets
		if(super.getDestinationTickets().isEmpty()){
			super.drawDestinationTickets();
		}
		
		//can I complete any route
		for(Route route: routes.getAllRoutes()){
			//System.out.println("Looking at route from "+route.getDest1()+ " to "+route.getDest2()+". That it is claimed is "+isRouteClaimed(route.getDest1(), route.getDest2()));
			if(!routes.isRouteClaimed(route)){
				TrainCardColor routeColor = route.getColor();
				int routeCost = route.getCost();
				//grey routes
				if(routeColor==TrainCardColor.rainbow){
					for(TrainCardColor color: TrainCardColor.values()){
						int numCardsOfColor = super.getNumTrainCardsByColor(color);
						//System.out.println("Grey route, owner is "+route.getOwner());
						if((numCardsOfColor+super.getNumTrainCardsByColor(TrainCardColor.rainbow)) >= routeCost&&route.getOwner()==null){
							System.out.println("Looking at route from "+route.getDest1()+ " to "+route.getDest2()+" Grey route, owner is "+route.getOwner()+ " Using "+color+ ". That it is claimed is "+routes.isRouteClaimed(route));
							super.claimRoute(route, color);
							break;
						}
					}
				}
				
				//colored routes
				else if((super.getNumTrainCardsByColor(routeColor)+super.getNumTrainCardsByColor(TrainCardColor.rainbow))>=routeCost&&route.getOwner()==null){
					System.out.println("Colored route, owner is "+route.getOwner()+ " That it is claimed is "+routes.isRouteClaimed(route));
					super.claimRoute(route, routeColor);
					break;
				}
				//System.out.println("Colored route, owner is "+route.getOwner());
			}
		}
		
		/* Always draw train cards (0 means we are drawing from the pile, not from the face-up cards) */
		super.drawTrainCard(0);
		
	}
	
	/**
	 * Returns the number of train cards this player has of the given color
	 * */
	/*private int getNumTrainCardsByColor(TrainCardColor color){
		int count = 0;
		for(TrainCard card : super.getHand()){
			if(card.getColor() == color) count++;
		}
		return count;
	}*/
	

}