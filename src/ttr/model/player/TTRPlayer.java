package ttr.model.player;

import java.util.*;

import ttr.model.destinationCards.Destination;
import ttr.model.destinationCards.Route;
import ttr.model.destinationCards.Routes;

public class TTRPlayer extends Player {
	
	
	public TTRPlayer(String name) {
		super(name);
	}
	public TTRPlayer(){
		super("TTR Player");
	}
	
	@Override
	public void makeMove() {
		ArrayList<ArrayList<Route>> foo = getPath(Destination.Seattle, Destination.Boston);
		for(ArrayList<Route> routes :foo){
			System.out.print(routes.get(0).getDest1().name()+" ");
		}
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
	
	public ArrayList<ArrayList<Route>> getPath(Destination to, Destination from){
		Routes routes = Routes.getInstance();
		PriorityQueue<PathNode> openList = new PriorityQueue<PathNode>(1, new PathNodeComparator());
		ArrayList<PathNode> closedList = new ArrayList<PathNode>();
		HashMap<Destination, Destination> previousCityFinder = new HashMap<Destination, Destination>();
		
		//init
		for(Destination dest: routes.getNeighbors(from)){ 
			Route toDest = routes.getRoutes(dest, from).get(0);
			openList.add(new PathNode(dest, from, toDest.getCost()));
			previousCityFinder.put(dest, from);
			System.out.println(from+" links to " +dest);
		}
		
		while(!openList.isEmpty()){
			//System.out.println("Wheee");
			//pop pathnode off the top
			PathNode nextNode = openList.poll();
			//is it the end?
			if(nextNode.getCurr() == to){
				Stack<ArrayList<Route>> pathBuilder = new Stack<ArrayList<Route>>();
				boolean pathNotComplete = true;
				Destination prev = nextNode.getPrev();
				ArrayList<Route> initRoute = routes.getRoutes(to, prev);
				pathBuilder.push(initRoute);
				//build up the stack
				while(pathNotComplete){
					Destination next = previousCityFinder.get(prev);
					System.out.println("Pushing "+ prev+ " to "+next);
					pathBuilder.push(routes.getRoutes(prev, next));
					if(next == from){
						pathNotComplete = false;
						continue;
					}
					prev = next;
					
				}
				//reverse the ordering
				ArrayList<ArrayList<Route>> finalRoute = new ArrayList<ArrayList<Route>>();
				while(!pathBuilder.isEmpty()){
					finalRoute.add(pathBuilder.pop());
				}
				return finalRoute;
			}
			//keep searching
			for(Destination dest: routes.getNeighbors(nextNode.getCurr())){
				int bestCost = routes.shortestPathcost(from, dest);
				System.out.println("Best Cost: "+bestCost+ " Current Cost: "+nextNode.getDistToStart());
				if(!previousCityFinder.containsKey(dest)&&dest!=from){
					previousCityFinder.put(dest, nextNode.getCurr());
					System.out.println(nextNode.getCurr()+" links to " +dest);
				}
				//System.out.println("Best Cost: "+bestCost+ " Current Cost: "+nextNode.getDistToStart());
				else if(previousCityFinder.containsKey(dest)&&bestCost<nextNode.getDistToStart()){
					previousCityFinder.put(dest, nextNode.getCurr());
					System.out.println(nextNode.getCurr()+" links to " +dest);
				}
				if(!closedList.contains(nextNode)){
					Route toDest = routes.getRoutes(nextNode.getCurr(), dest).get(0);
					openList.add(new PathNode(dest, nextNode.getCurr(), toDest.getCost()+nextNode.getDistToStart()));
					//System.out.println("Adding "+nextNode.getCurr().name()+" to "+ dest.name());
				}
			}
			
			closedList.add(nextNode);
			
		}
		
		throw new RuntimeException("No path found");
		//return null;
	}

}

/**
 * used to calculate shortest path
 */
class PathNode {
	Destination curr, prev;
	int distToStart;
	
	public PathNode(){
		curr = null;
		prev = null;
		distToStart = Integer.MAX_VALUE;
	}
	
	public PathNode(Destination curr, Destination prev, int distToStart){
		this.curr = curr;
		this.prev = prev;
		this.distToStart = distToStart;
	}
	
	@Override
	public boolean equals(Object obj){
		PathNode b = (PathNode) obj;
		if(b.getCurr() == this.curr && b.getPrev() == this.prev){
			return true;
		}
		return false;
		
	}

	public Destination getCurr() {
		return curr;
	}

	public void setCurr(Destination curr) {
		this.curr = curr;
	}

	public Destination getPrev() {
		return prev;
	}

	public void setPrev(Destination prev) {
		this.prev = prev;
	}

	public int getDistToStart() {
		return distToStart;
	}

	public void setDistToStart(int distToStart) {
		this.distToStart = distToStart;
	}
	
}
/**
 * Compares two pathnodes, for a priority queue
 */
class PathNodeComparator implements Comparator<PathNode>{

	@Override
	public int compare(PathNode to, PathNode from) {
		return (to.getDistToStart()<from.getDistToStart()) ? -1: (to.getDistToStart()==from.getDistToStart()) ? 0 : 1;
		
	}
	
}


class Goal {
	Destination to, from;

	/**
	 * Default constructor
	 */
	public Goal(){
		
	}
	
	public Goal(Destination to, Destination from) {
		this.to = to;
		this.from = from;
	}
	//not sure how I feel about doing this this way
	public void execute(TTRPlayer player){
		//-
		
	}
	
	public Destination getTo() {
		return to;
	}

	public void setTo(Destination to) {
		this.to = to;
	}

	public Destination getFrom() {
		return from;
	}

	public void setFrom(Destination from) {
		this.from = from;
	}
	
}