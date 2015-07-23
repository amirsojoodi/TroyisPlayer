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


import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Extractor {

	public int[] pixels;

	public int[][][] image;

	ArrayList<Drawable> all;

	ArrayList<Drawable> objects;
	
	BufferedImage im;

	public Robot robot;
	
	public int NUMBEROFSHOTS = 1;

	public Extractor() throws AWTException {
		robot = new Robot();
		objects = new ArrayList<Drawable>();
		pixels = new int[GP.width * GP.height];
		image = new int[GP.height][GP.width][NUMBEROFSHOTS];
		Dimension dim = new Dimension(Toolkit.getDefaultToolkit()
				.getScreenSize());
		GP.window_size_x = dim.width;
		GP.window_size_y = dim.height;
	}
	
	public void setPlaces() {
		Node[][] board = GP.board;
		for (int i = 0; i < GP.board_size; i++) {
			for (int j = 0; j < GP.board_size; j++) {
				if(i==0 && j==0){
					ConnectedSet cs = new ConnectedSet(j, i, GP.seen);
					cs.xMin = j + 1;
					cs.xMax = j + GP.rect_size - 1;
					cs.yMin = i + 1;
					cs.yMax = i + GP.rect_size - 1;
					cs.validate();
					objects.add(cs);
					board[i][j] = new Node(i, j, State.seen, cs);
					continue;
				}
				
				int count = 0;
				boolean flag = false;
				int index1 = i * GP.rect_size;
				int index2 = j * GP.rect_size;
				loop: for (int i2 = index1 + 3; i2 < index1 + GP.rect_size - 3; i2++) {
					for (int j2 = index2 +3; j2 < index2 + GP.rect_size - 3; j2++) {
						for (int k = 0; k < GP.unseen.length; k++) {
							if(testColor(image[i2][j2][0], GP.unseen[k], 3)){
								count++;
								break;
							}
						}
						if (count >= 70) {
							flag =true;
							break loop;
						}
					}
				}
				ConnectedSet cs;
				if (flag) {
					cs = new ConnectedSet(index2, index1, GP.unseen[0]);
					board[i][j] = new Node(i, j, State.unseen, cs);
				}
				else{
					cs = new ConnectedSet(index2, index1, GP.full);
					board[i][j] = new Node(i, j, State.full, cs);
				}
				cs.xMin = index2 + 1;
				cs.xMax = index2 + GP.rect_size - 1;
				cs.yMin = index1 + 1;
				cs.yMax = index1 + GP.rect_size - 1;
				cs.validate();
				objects.add(cs);
			}
		}
		System.out.println(objects.size()+"Places has been detected!");
	}

	public void findBoard(int board_w, int board_h, int wallcolor) {
		int pix_size = GP.window_size_x * GP.window_size_y;
		int[] pix = new int[GP.window_size_x * GP.window_size_y];
		int find_x = 0;
		int find_y = 0;
		boolean find = false;

		while (!find) {
			BufferedImage bi = robot.createScreenCapture(new Rectangle(0, 0,
					GP.window_size_x, GP.window_size_y));
			bi.getRGB(0, 0, GP.window_size_x, GP.window_size_y, pix, 0, GP.window_size_x);
			
			loop: for (int i = 0; i < GP.window_size_y; i++) {
				for (int j = 0; j < GP.window_size_x; j++) {
					int count = 0;
					if (j <= GP.window_size_x - board_w) {
						int index = i * GP.window_size_x + j;
						if (pix[index] == wallcolor) {
							for (int k = 0; k < board_w; k++) {
								if (pix[index + k] == wallcolor)
									count++;
							}
						}
						if (count == board_w) {
							find_x = j;
							find_y = i;
							find = true;
							break loop;
						}
					}	
				}
			}
			if(find){
				find = false;
				int index = find_y * GP.window_size_x + find_x;
				int count = 0;
				if (pix[index] == wallcolor) {
					for (int k = index; k < pix_size ; k += GP.window_size_x) {
						if (pix[k] == wallcolor)
							count++;
					}
				}
				if (count == board_h) {
					find = true;
				}
			}
		}
		System.out.println("Board found! x = "+find_x+" y = "+find_y);
		GP.top = find_y;
		GP.left = find_x;
	}

	public boolean gameReady(DisplayThread d) throws Exception {
		waitToStart(robot, true, GP.message);
		waitToStart(robot, false, GP.message);
		if (GP.display) {
			d.setObjects(objects);
			d.run();
		}
		//System.out.println("READY!");
		return true;
	}

	public void waitToStart(Robot robot, boolean chekThisFlag, int color) {
		BufferedImage br;
		BufferedImage bs;
		while (true) {
			br = robot.createScreenCapture(new Rectangle(GP.left, GP.top, GP.width, GP.height));
			bs = br.getSubimage(GP.STARTINGRECT_X, GP.STARTINGRECT_Y, 50, 50);
			
			boolean isGoingToStart = true;
			for (int i = 0; i < 18 && isGoingToStart; i++) { //18
				if(bs.getRGB(0, i) != color){
					isGoingToStart = false;
					break;
				}
			}
			for (int j = 0; j < 7 && isGoingToStart; j++) { //7
				if(bs.getRGB(j, 0) != color){
					isGoingToStart = false;
					break;
				}
			}
			if(isGoingToStart == chekThisFlag){
				if(isGoingToStart)
					System.out.println("The game is going to start! ;)");
				else
					System.out.println("The game is started! ;)");
				break;
			}	
		}
	}
	
	public boolean checkLevelFinished(boolean chekThisFlag, int color){
		BufferedImage br;
		BufferedImage bs;
		boolean isFinished = false;
		while(true){
			br = robot.createScreenCapture(new Rectangle(GP.left, GP.top, GP.width, GP.height));
			bs = br.getSubimage(GP.LEVELCOMPLETE_X, GP.LEVELCOMPLETE_Y, GP.width - GP.LEVELCOMPLETE_X, 40);
			/*File file = new File("n.png");
			try {
				ImageIO.write(bs, "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			int count = 0;
			loop: for (int i = 0; i < 30; i++) {
				for (int j = 0; j < 30; j++) {
					if(bs.getRGB(j, i) == color){
						count++;
						if(count > 100){ //160
							isFinished = true;
							break loop;
						}
					}
				}
				
			}
			if(isFinished == chekThisFlag){
				if(isFinished)
					System.out.println("The level is going to finish! ;)");
				else
					System.out.println("The level is finished! ;)");
				break;
			}
		}
		return true;
	}

	public void extractAShot() throws Exception {
		for (int i = 0; i < NUMBEROFSHOTS; i++) {
			getPixels();
			getImage(i);
			//Thread.sleep(5);
		}
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				Arrays.sort(image[i][j], 0, NUMBEROFSHOTS);
			}
		}
	}

	public int[] getPixels() throws Exception {
		im = robot.createScreenCapture(new Rectangle(GP.left, GP.top, GP.width,
				GP.height));
		im.getRGB(0, 0, GP.width, GP.height, pixels, 0, GP.width);
		return pixels;
	}

	public void getImage(int k) {
		for (int i = 0; i < GP.height; i++) {
			for (int j = 0; j < GP.width; j++) {
				image[i][j][k] = pixels[i * GP.width + j];
			}
		}
	}

	public void getImage(int[] pix, int k) {
		for (int i = 0; i < GP.height; i++) {
			for (int j = 0; j < GP.width; j++) {
				image[i][j][k] = pix[i * GP.width + j];
			}
		}
	}
	
	public static boolean testColor(int c1, int c2, int tolerance) {
		int[] temp = new int[4];
		int[] temp1 = new int[4];
		temp[0] = c1 & 0xFFFFFF;
		temp[1] = (temp[0] & 0xFF0000) >> 16;
		temp[2] = (temp[0] & 0xFF00) >> 8;
		temp[3] = (temp[0] & 0xFF);
		temp1[0] = c2 & 0xFFFFFF;
		temp1[1] = (temp1[0] & 0xFF0000) >> 16;
		temp1[2] = (temp1[0] & 0xFF00) >> 8;
		temp1[3] = (temp1[0] & 0xFF);
		for (int i = 1; i < temp1.length; i++) {
			if (Math.abs(temp[i] - temp1[i]) > tolerance)
				return false;
		}
		return true;
	}
}
