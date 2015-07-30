package ttr.model.player;

import java.util.ArrayList;

import ttr.model.destinationCards.Destination;
import ttr.model.destinationCards.Route;
import ttr.model.destinationCards.Routes;
import ttr.model.trainCards.TrainCardColor;

public class TestAI extends Player {
	Routes routes;
	
	public TestAI(String name) {
		super(name);
		routes = Routes.getInstance();
	}
	public TestAI(){
		super("TTR Player");
		routes = Routes.getInstance();
	}
	
	@Override
	public void makeMove() {
		System.out.println("");
		Destination dest1 = Destination.Seattle;
		Destination dest2 = Destination.Vancouver;
		
		ArrayList<Route> routeBundle = routes.getRoutes(dest1, dest2); 
		
		boolean isRouteClaimed = false;
		for(Route route: routeBundle){
			if(routes.isRouteClaimed(route)){
				System.out.println("Routes.isValidRoutes(route) thinks this is not valid.");
				isRouteClaimed = true;
			}
			/*if(!routes.isValidRoute(dest1, dest2, TrainCardColor.rainbow)){
				System.out.println("Routes.isValidRoutes(dest, dest) thinks this is not valid.");
				isRouteClaimed = true;
			}*/
			if(!(route.getOwner()==null)){
				System.out.println("route.getOwner() thinks this route has an owner.");
				isRouteClaimed = true;
			}
		}
		if(!isRouteClaimed){
			super.claimRoute(routes.getRoutes(dest1, dest2).get(0), TrainCardColor.blue);
		}
	}

}
