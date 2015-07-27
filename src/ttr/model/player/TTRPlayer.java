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
		ArrayList<Route> foo = getPath(Destination.Seattle, Destination.Denver);
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
	
	public ArrayList<Route> getPath(Destination to, Destination from){
		Routes routes = Routes.getInstance();
		PriorityQueue<PathNode> openList = new PriorityQueue<PathNode>(10, new PathNodeComparator());
		ArrayList<PathNode> closedList = new ArrayList<PathNode>();
		
		//init
		for(Destination dest: routes.getNeighbors(from)){ 
			Route toDest = routes.getRoutes(to, from).get(0);
			openList.add(new PathNode(from, to, toDest.getCost()));
		}
		
		while(!openList.isEmpty()){
			//pop pathnode off the top
			PathNode nextNode = openList.poll();
			//is it the end?
			if(nextNode.getCurr() == to){
				return null;
			}
			
			
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