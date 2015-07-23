package org.troyisPlayer.utilities;

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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import org.troyisPlayer.main.GP;

public class Test {
	static Robot robot;
	public static void main(String[] args) throws IOException, AWTException {
		//BufferedImage bi = ImageIO.read(new File("resources/images/4.png"));
		robot = new Robot();
		write();
		//findBoard(GP.width, GP.height, -1);
		//BufferedImage br = bi.getSubimage(173, 107, 20, 20);
		/*Dimension dim = new Dimension(Toolkit.getDefaultToolkit()
				.getScreenSize());
		int size_x = dim.width;
		int size_y = dim.height;
		robot.mouseMove(30, 890);
		
		BufferedImage br = robot.createScreenCapture(new Rectangle(0, 0, size_x, size_y));*/
		/*BufferedImage bs = bi.getSubimage(48, 194, 60, 60);
		boolean isFinished = true;
		int count = 0;
		loop: for (int i = 0; i < 60 && isFinished; i++) {
			for (int j = 0; j < 60; j++) {
				if(bs.getRGB(j, i) != -1){
					count++;
				}
			}
			if(count > 50){
				isFinished = true;
				break loop;
			}
		}
		
		if(isFinished)
			System.out.println("The level is finished! ;)");
		else
			System.out.println("The level is not finished! :P");
		*/
		/*File n = new File("n.png");
		ImageIO.write(bs, "png", n);*/
		
	}
	public static void findBoard(int board_w, int board_h, int wallcolor) throws AWTException {
		Dimension dim = new Dimension(Toolkit.getDefaultToolkit()
				.getScreenSize());
		int size_x = dim.width;
		int size_y = dim.height;
		int pix_size = size_x * size_y;
		int[] pix = new int[size_x * size_y];
		int find_x = 0;
		int find_y = 0;
		boolean find = false;

		while (!find) {
			BufferedImage bi = robot.createScreenCapture(new Rectangle(0, 0,
					size_x, size_y));
			bi.getRGB(0, 0, size_x, size_y, pix, 0, size_x);
			
			loop: for (int i = 0; i < size_y; i++) {
				for (int j = 0; j < size_x; j++) {
					int count = 0;
					if (j <= size_x - board_w) {
						int index = i * size_x + j;
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
				int index = find_y * size_x + find_x;
				int count = 0;
				if (pix[index] == wallcolor) {
					for (int k = index; k < pix_size ; k += size_x) {
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
	public static void write(){
		try {
			BufferedImage bi = ImageIO.read(new File("resources/images/2.png"));
			PrintWriter pw = new PrintWriter(new File("resources/files/t2.txt"));
			for (int i = 0; i < bi.getHeight(); i++) {
				pw.println();
				for (int j = 0; j < bi.getWidth(); j++) {
					int cell = bi.getRGB(j, i);
					pw.printf("%10d", cell);
				}
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
