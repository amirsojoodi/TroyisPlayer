package org.troyisPlayer.main;

/*
============================================================================
Author      : Amir Hossein Sojoodi, Amir Hossein Shahriari
Version     : 0.0.1
Year        : 2011
Copyright   : GNU
Description : TroyisPlayer in Java
============================================================================
*/


import org.troyisPlayer.utilities.JEasyFrame;

public class Troyis {

	static int delay = 10;

	Extractor ex;

	DisplayThread di;
	
	TroyisMover tm;
	
	horseBrain horseBrain;
	
	public Troyis() throws Exception {
		ex = new Extractor();
		di = new DisplayThread(GP.width, GP.height);
		tm = new TroyisMover();
		new JEasyFrame(di.display, "TroyisPlayer", true);
	}
	
	public static void main(String[] args) throws Exception {
		Thread.sleep(100);
		Troyis tr = new Troyis();
		
 		GP.level = 0;
 		GP.display =true;
 		GP.board_size = GP.board_size_number[GP.level];
		GP.CurrentNode = GP.board[0][0];
		GP.rect_size = GP.width / GP.board_size_number[GP.level];
		tr.ex.objects.clear();
		GP.board = new Node[GP.board_size][GP.board_size];
	
		System.out.println("Try to find the window...");
		tr.ex.findBoard(GP.width, GP.height,GP.faded_wall);
		
		tr.tm.move2(GP.left + GP.board_size / 2, GP.top + GP.board_size / 2);
		System.out.println("Mouse moved to the center...");
		
		/*if (GP.display){
			tr.di.setObjects(tr.ex.objects);
			tr.di.join();
			tr.di.start();
		}*/	
		
		while (true) {
			tr.ex.gameReady(tr.di);
			Thread.sleep(200);
			tr.ex.extractAShot();
			tr.ex.setPlaces();
			//tr.printBoard();
			GP.CurrentNode = GP.board[0][0];
			tr.horseBrain = new horseBrain(GP.board);
			if (GP.display){
				tr.di.setObjects(tr.ex.objects);
				tr.di.run();
			}
			while(true){
				Thread.sleep(150);
				System.out.println("Current Position: "+GP.CurrentNode.x+" , "+GP.CurrentNode.y+"\n");
				Node dest = tr.horseBrain.nextMove();
				if(dest == null){
					break;
				}
				if (dest==GP.CurrentNode)
					System.exit(0);
				System.out.printf("Move to %d , %d\n",dest.x, dest.y);
				dest.state = State.seen;
				dest.cs.fg = GP.seen;
				if (GP.display){
					tr.di.setObjects(tr.ex.objects);
					tr.di.run();
				}
				tr.tm.moveAndClick(dest.y, dest.x); //moveAndClick
				GP.CurrentNode = dest;
			}
			
			System.out.println("Check to see wehther level is changing or not...");
			tr.changeLevel();
			Thread.sleep(delay);
		}

	}
	
	public void changeLevel() throws Exception {
		ex.checkLevelFinished(true, GP.message);
		tm.move2(GP.left + GP.width / 2, GP.top + GP.height / 2);
		System.out.println("Change level...");
		GP.level++;
		GP.board_size = GP.board_size_number[GP.level];
		GP.CurrentNode = GP.board[0][0];
		GP.rect_size = GP.width / GP.board_size_number[GP.level];
		ex.objects.clear();
		GP.board = new Node[GP.board_size][GP.board_size];
	}
	
	public void printBoard(){
		System.out.println("Rect_size: "+GP.rect_size);
		for (int i = 0; i < GP.board.length; i++) {
			System.out.println();
			for (int j = 0; j < GP.board.length; j++) {
				System.out.printf("%15s",GP.board[i][j].state.toString());
			}
			System.out.println();
			for (int j = 0; j < GP.board.length; j++) {
				System.out.printf("| %d %d %d %d",GP.board[i][j].cs.xMin, GP.board[i][j].cs.xMax, 
						GP.board[i][j].cs.yMin, GP.board[i][j].cs.yMax);
			}
		}
	}
}
