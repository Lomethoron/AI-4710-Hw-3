package ttr.main;

import ttr.model.player.HumanPlayer;
import ttr.model.player.Player;
import ttr.model.player.StupidPlayer;
import ttr.model.player.StupidAI;
import ttr.model.player.TTRPlayer;
import ttr.model.player.TestAI;
import ttr.view.scenes.TTRGamePlayScene;

public class TTRMain {

	public static void main(String[] args) {
		
		/* This is the game object required by the engine (essentially just the game window) */
		TicketToRide myGame = new TicketToRide();
		
		/* Initialize two players */
		Player player1 = new TTRPlayer("SUPER AMAZING AI 1");
		Player player2 = new StupidAI("Test AI 1");
		//player2.setHideStats(true);
		
		TTRGamePlayScene scene = new TTRGamePlayScene("Ticket To Ride", "woodBacking.jpg", myGame, player1, player2);
		myGame.setCurrentScene(scene);
		player1.setScene(scene);
		player2.setScene(scene);
		myGame.start();
		scene.playGame();
	}
}




