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


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DisplayThread extends Thread {
	Display display;

	public DisplayThread(int w, int h, ArrayList<Drawable> ob, Display display) {
		this.display = display;
		display.setObjects(ob);
	}

	public DisplayThread(int w, int h) {
		display = new Display(w, h);
	}

	public void run() {
		display.updateObjects();
	}

	public void setObjects(ArrayList<Drawable> ob) {
		display.setObjects(ob);
	}
}

@SuppressWarnings("serial")
class Display extends JComponent {
	int w, h;
	Dimension d;
	ArrayList<Drawable> objects;

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, w, h);
		draw(g);
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, w, h);
		draw(g);
		System.out.println("Painted");
	}

	public void redraw() {
		// draws without overwriting
		draw(getGraphics());
	}

	public void draw(Graphics g) {
		if (objects != null && g != null) {
			// ensure only one thread gets through this at any one time
			synchronized (DisplayThread.class) {
				for (Drawable d : objects) {
					// System.out.println("Trying to draw " + d + " on " + g);
					d.draw(g, w, h);
				}
			}
		}
	}

	public void updateObjects(ArrayList<Drawable> objects) {
		// System.out.println("Objects: " + objects);
		synchronized (DisplayThread.class) {
			this.objects = objects;
		}
		repaint();
		// draw(getGraphics());
	}

	public Display(int w, int h) {
		this.w = w;
		this.h = h;
		d = new Dimension(w, h);
		setBackground(Color.black);
		setFocusable(true);
	}

	public void setObjects(ArrayList<Drawable> ob) {
		synchronized (DisplayThread.class) {
			this.objects = ob;
		}
	}

	public void updateObjects() {
		synchronized (Display.class) {

		}
		repaint();
	}

	public Dimension getPreferredSize() {
		return d;
	}
}
