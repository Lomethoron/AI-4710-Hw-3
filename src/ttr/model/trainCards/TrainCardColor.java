package ttr.model.trainCards;


public enum TrainCardColor{
	orange, black, white, blue, green, purple, yellow, red, rainbow;	
	
	public static TrainCardColor getRandomColor(){
		return TrainCardColor.values()[(int)(Math.random()*TrainCardColor.values().length)];
		
	}
}