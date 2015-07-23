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


import java.awt.Color;
import java.awt.Graphics;

public class ConnectedSet implements Drawable {
	int x, y;
	int width, height;
	int fg; // the value of the FG pixels
	public int xMin, xMax, yMin, yMax;
	Color c;
	public int px, py;
	boolean valid = false;

	public void draw(Graphics g, int w, int h) {
		validate();
		g.setColor(c);
		g.fillRect(xMin, yMin, width, height);
	}

	public void validate() {
		if (!valid) {
			width = xMax - xMin;
			height = yMax - yMin;
			valid = true;
		}
	}

	public ConnectedSet(int x, int y, int fg) {
		this.x = x;
		this.y = y;
		xMin = x;
		xMax = x;
		yMin = y;
		yMax = y;
		this.fg = fg;
		c = new Color((fg & 0xFF0000) >> 16, (fg & 0xFF00) >> 8, (fg & 0xFF));
	}
}
