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


public class Node{
	int x, y;
	State state;
	ConnectedSet cs;
	public Node(int x, int y, State state, ConnectedSet cs){
		this.x = x;
		this.y = y;
		this.state = state;
		this.cs = cs;
	}
}
