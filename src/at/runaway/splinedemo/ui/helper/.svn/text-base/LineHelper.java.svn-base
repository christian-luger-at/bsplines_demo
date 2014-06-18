package at.runaway.splinedemo.ui.helper;

import java.awt.Graphics;

/**
 * UI-Helper class to draw lines
 * 
 * @author christianluger
 */
public class LineHelper {

	// --------------------------------------------------------------------------
	// S T A T I C  -  V a r i a b l e s 
	// --------------------------------------------------------------------------

	private static final float SEGMENT_LENGTH = 8;
	
	private static final int POINT_RADIUS = 3;
	
	// --------------------------------------------------------------------------
	// S T A T I C  -  P U B L I C  -  M e t h o d s
	// --------------------------------------------------------------------------
	
	public static void drawPoint(Graphics g, int x, int y) {
		g.fillOval(x - POINT_RADIUS, y - POINT_RADIUS, 2 * POINT_RADIUS, 2 * POINT_RADIUS);
	}

    public static void drawDashLine(Graphics g, int x1, int y1, int x2, int y2) {
		double x;
		double y;
		if (x1 == x2) {
			if (y1 > y2) {
				int tmp = y1;
				y1 = y2;
				y2 = tmp;
			}
			y = (double) y1;
			while (y < y2) {
				double y0 = Math.min(y + SEGMENT_LENGTH, (double) y2);
				g.drawLine(x1, (int) y, x2, (int) y0);
				y = y0 + SEGMENT_LENGTH;
			}
			return;
		} else if (x1 > x2) {
			int tmp = x1;
			x1 = x2;
			x2 = tmp;
			tmp = y1;
			y1 = y2;
			y2 = tmp;
		}
		double ratio = 1.0 * (y2 - y1) / (x2 - x1);
		double ang = Math.atan(ratio);
		double xinc = SEGMENT_LENGTH * Math.cos(ang);
		double yinc = SEGMENT_LENGTH * Math.sin(ang);
		x = (double) x1;
		y = (double) y1;
		while (x <= x2) {
			double x0 = x + xinc;
			double y0 = y + yinc;
			if (x0 > x2) {
				x0 = x2;
				y0 = y + ratio * (x2 - x);
			}
			g.drawLine((int) x, (int) y, (int) x0, (int) y0);
			x = x0 + xinc;
			y = y0 + yinc;
		}
	}
}
