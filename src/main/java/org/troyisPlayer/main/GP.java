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



public class GP {

	public static int window_size_x = 1600;
	
	public static int window_size_y = 900;
	
	public static boolean display = false;

	public static int level = 0;

	public static int left = 714; 

	public static int top = 162;

	public static int width = 411;

	public static int height = 410;

	public static int board_size = 3; // for level one
	
	public static int rect_size = 137; // for level one
	
	public static Node[][] board = new Node[board_size][board_size];
	
	public static int board_size_number[] = {3, 4, 5, 6, 7, 8, 9, 10, 10, 10, //level 1 to 10 
		10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
		10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
		10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
		10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
		10, 10, 10, 10, 10, 10, 10, 10, 10, 10}; //level 10 to 20
	
	public static int STARTINGRECT_X = 168;
	
	public static int STARTINGRECT_Y = 98;
	
	public static int LEVELCOMPLETE_X = 36;
	
	public static int LEVELCOMPLETE_Y = 184;

	public static Node CurrentNode;
	
	//Some Color

	public static int faded_wall = -1250856;
	
	public static int unseen[] = {-1842182, -1973766, -1447430, -1513222, -1644806, -1710598,
		-1907974};
	
	public static int seen = -1000000;
	
	public static int full = -7434522; //-3784781; 
	
	public static int ready = -2696729;
	
	public static int message = -1;
}
