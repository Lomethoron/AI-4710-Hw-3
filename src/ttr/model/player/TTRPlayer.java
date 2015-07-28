package ttr.model.player;

import java.util.*;

import ttr.model.destinationCards.Destination;
import ttr.model.destinationCards.DestinationTicket;
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
		ArrayList<ArrayList<Route>> foo = getPath(Destination.Seattle, Destination.Boston, true);
		Routes routesf = Routes.getInstance();
		for(ArrayList<Route> routes :foo){
			System.out.println("Route from "+ routes.get(0).getDest1().name() + " to "+ routes.get(0).getDest2().name()+" at cost "+ routes.get(0).getCost());
			//System.out.print(routes.get(0).getDest1().name()+" ");
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
	
	/**
	 * Determines the routes comprising the shortest path between two cities
	 * @param to city 1
	 * @param from city 2
	 * @param considerMadeMoves should the algorithm look to see if all routes have been claimed
	 * @return the list of routes in the shortest path between destinations, nested ArrayList allows access to multiple existant routes between adjacent cities, returns null if no route is found
	 */
	
	public ArrayList<ArrayList<Route>> getPath(Destination to, Destination from, boolean considerMadeMoves){
		Routes routes = Routes.getInstance();
		PriorityQueue<PathNode> openList = new PriorityQueue<PathNode>(1, new PathNodeComparator());
		ArrayList<PathNode> closedList = new ArrayList<PathNode>();
		
		//get origin onto PQ
		PathNode origin = new PathNode(from);
		openList.add(origin);
		
		while(!openList.isEmpty()){
			PathNode node = openList.poll();
			//int fooCost = routes.shortestPathcost(from, node.getCurr());
			//System.out.println("Were looking at "+node.getCurr().name()+". The previous node is "+node.getPrev()+". The distance from start is "+ node.getDistToStart()+ ". The best possible cost to here is "+fooCost);
			//deal with the node having already been closed
			if(closedList.contains(node)){
				//System.out.println("Skipping already searched city "+node.getCurr().name()+"\n");
				continue;
			}
			//deal with this being the end node
			if(node.getCurr() == to){
				//push all the routes into a stack
				Stack<ArrayList<Route>> pathBuilder = new Stack<ArrayList<Route>>();
				boolean pathNotComplete = true;
				PathNode curr = node;
				PathNode prev = null;
				for(PathNode fooNode: closedList){
					if(fooNode.getCurr() == curr.getPrev()){
						prev = fooNode;
						break;
					}
				}
				while(pathNotComplete){
					//if its the start node					
					Destination currName = curr.getCurr();
					Destination prevName = prev.getCurr();
					if(currName == from){
						break;
					}
					//System.out.println("Building path from "+currName.name()+ " to "+prevName.name());
					pathBuilder.push(routes.getRoutes(currName, prevName));
					curr = prev;
					for(PathNode fooNode: closedList){
						if(fooNode.getCurr() == curr.getPrev()){
							prev = fooNode;
						}
					}	
				}
				
				//System.out.println("Building route");
				ArrayList<ArrayList<Route>> orderedRoute = new ArrayList<ArrayList<Route>>();
				while(!pathBuilder.isEmpty()){
					orderedRoute.add(pathBuilder.pop());
				}
				return orderedRoute;
			}
			//add all values to the pq
			for(Destination dest: routes.getNeighbors(node.getCurr())){
				//prevents looping
				boolean isClosed = false;
				for(PathNode fooNode : closedList)
				{
					if (dest == fooNode.getCurr()){
						//System.out.println(dest.name()+" Node is closed");
						isClosed = true;
						break;
					}
				}
				Route thisRoute = routes.getRoutes(dest, node.getCurr()).get(0);
				if(considerMadeMoves){
					if(!isClosed&&(!routes.isRouteClaimed(thisRoute)||(thisRoute.getOwner() instanceof TTRPlayer))){
						//get cost of this node
						int cost = thisRoute.getCost();	
						//System.out.println("Adding "+dest.name()+" to be searched. Its previous node is "+node.getCurr()+". Its cost is "+cost+". Its total cost is "+(cost+node.getDistToStart()));
						PathNode nextNode = new PathNode(dest, node.getCurr(), node.getDistToStart()+cost);
						openList.add(nextNode);
					}
					else{
						//System.out.println("Not searching "+dest.name()+" because it is already closed.");
					}
				}
				else{
					if(!isClosed){
						//get cost of this node
						int cost = thisRoute.getCost();	
						//System.out.println("Adding "+dest.name()+" to be searched. Its previous node is "+node.getCurr()+". Its cost is "+cost+". Its total cost is "+(cost+node.getDistToStart()));
						PathNode nextNode = new PathNode(dest, node.getCurr(), node.getDistToStart()+cost);
						openList.add(nextNode);
					}
					else{
						//System.out.println("Not searching "+dest.name()+" because it is already closed.");
					}
					
				}
			}
			//close node
			closedList.add(node);
			//System.out.println("Closing: "+ node.getCurr()+"\n");
		}
		return null;
		
		
	}

}

/**
 * used to calculate shortest path
 */
class PathNode {
	Destination curr, prev;
	int distToStart;
	
	/**
	 * Default constructor
	 */
	public PathNode(){
		curr = null;
		prev = null;
		distToStart = Integer.MAX_VALUE;
	}
	
	/**
	 * creating the origin node
	 * @param curr the origin node
	 */
	public PathNode(Destination curr){
		this.curr = curr;
		prev = null;
		distToStart = 0;
	}
	
	public PathNode(Destination curr, Destination prev, int distToStart){
		this.curr = curr;
		this.prev = prev;
		this.distToStart = distToStart;
	}
	
	@Override
	public boolean equals(Object obj){
		PathNode b = (PathNode) obj;
		if(b.getCurr() == this.curr){
			return true;
		}
		return false;
		
	}
	
	
	public Goal bestOption(ArrayList<DestinationTicket> al){
		int maxPoints = -1;
		for(int x=0; x<al.size(); x++)
		{
			maxPoints = -1;
			if(al.get(x).getValue()>0)
				maxPoints = x;
		}
		DestinationTicket destTick = al.get(maxPoints);
		return new Goal(destTick.getTo(), destTick.getFrom());
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
	boolean isCompleteable;

	/**
	 * Default constructor
	 */
	public Goal(){
		to = null;
		from = null;
		isCompleteable = true;
	}
	
	public Goal(Destination to, Destination from) {
		this.to = to;
		this.from = from;
		isCompleteable = true;
	}
	//not sure how I feel about doing this this way
	/**
	 * attempts to execute this goal
	 * @param player the player
	 * @return if the goal is unable to be executed
	 */
	public boolean execute(TTRPlayer player){
		boolean consideringClaimedRoutes = true;
		ArrayList<ArrayList<Route>> neededRoutes = player.getPath(to, from, consideringClaimedRoutes);
		if(neededRoutes == null){
			isCompleteable = false;
			return false;
		}
		//order routes by the level of threat they face
		ArrayList<Route> routesByThreat = new ArrayList<Route>();
		for(ArrayList<Route> routeBundle : neededRoutes){
			//for(Route singleRoute:)
		}
		return true;
		
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

	public boolean isCompleteable() {
		return isCompleteable;
	}

	public void setCompleteable(boolean isCompleteable) {
		this.isCompleteable = isCompleteable;
	}
	
	
}

class RouteThreatComparator implements Comparator<Route>{
	@Override
	public int compare(Route a, Route b){
		//find number
		int aTotalThreat =  calcTotalThreat(a.getDest1())+calcTotalThreat(a.getDest2());
		int bTotalThreat =  calcTotalThreat(b.getDest1())+calcTotalThreat(b.getDest2());
		return (aTotalThreat<bTotalThreat)?-1:(aTotalThreat==bTotalThreat)?0:1;
	}
	
	private int calcTotalThreat(Destination a){
		Routes routes = Routes.getInstance();
		int threat = 0;
		for(Destination dest: routes.getNeighbors(a)){
			ArrayList<Route> fooRouteBundle = routes.getRoutes(dest, a);
			for(Route fooRoute: fooRouteBundle){
				if(!(fooRoute.getOwner() instanceof TTRPlayer)){
					threat++;
				}
			}
		}
		return threat;
	}
}