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


import java.awt.Robot;
import java.awt.event.InputEvent;

public class TroyisMover {
	Robot robot;
	boolean keyPressed;
	static int autoDelay = 20;

	public TroyisMover() {
		keyPressed = false;
		try {
			robot = new Robot();
			// prevent the robot from being flooded with too many events
			robot.setAutoWaitForIdle(false);
			robot.setAutoDelay(autoDelay);
			//System.out.println(robot.getAutoDelay());
			//System.out.println(robot.isAutoWaitForIdle());
			// robot.waitForIdle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void move(Node node) {
		//int x = GP.left + node.x * (GP.rect_size + 2) + GP.rect_size / 2;
		//int y = GP.top + node.y * (GP.rect_size + 2) + GP.rect_size / 2;
		int a = GP.left + (node.x * GP.width) / GP.board_size + GP.rect_size / 2;
		int b = GP.top + (node.y * GP.height) / GP.board_size + GP.rect_size / 2;
		
		robot.mouseMove(a, b);
	}
	
	public void moveAndClick(int x, int y){
		move(x, y);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	public void move(int x, int y){
		//int a = GP.left + x * GP.rect_size + GP.rect_size / 2;
		//int b = GP.top + y * GP.rect_size + GP.rect_size / 2;
		int a = GP.left + (x * GP.width) / GP.board_size + GP.rect_size / 2;
		int b = GP.top + (y * GP.height) / GP.board_size + GP.rect_size / 2;
		robot.mouseMove(a, b);
	}
	public void move2(int a, int b){
		robot.mouseMove(a, b);
	}
}
