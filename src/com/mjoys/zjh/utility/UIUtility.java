package com.mjoys.zjh.utility;

import javax.swing.JComponent;

public class UIUtility {

	public static final int TOP = 0;
	public static final int BOTTOM = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int LEFT_TOP = 4;
	public static final int RIGHT_TOP = 5;
	public static final int RIGHT_BOTTOM = 6;
	public static final int LEFT_BOTTOM = 7;

	public static void relative(JComponent component, JComponent relative,
			int loc, int xOffset, int yOffset) {
		int x = relative.getX(), y = relative.getY();
		int w = relative.getWidth(), h = relative.getHeight();
		int w1 = component.getWidth(), h1 = component.getHeight();
		// 先设置到中间
		int x1 = x + ((w - w1) >> 1), y1 = y + ((h - h1) >> 1);
		if (loc == TOP || loc == LEFT_TOP || loc == RIGHT_TOP) {// y值相同在上面
			y1 = y - h1;
		} else if (loc == BOTTOM || loc == LEFT_BOTTOM || loc == RIGHT_BOTTOM) { // y在下面
			y1 = y + h;
		}
		if (loc == LEFT || loc == LEFT_TOP || loc == LEFT_BOTTOM) { // x在左边
			x1 = x - w1;
		} else if (loc == RIGHT || loc == RIGHT_TOP || loc == RIGHT_BOTTOM) {// x在右边
			x1 = x + w;
		}
		component.setLocation(x1 + xOffset, y1 + yOffset);
	}

	public static void relative(JComponent component, JComponent relative,
			int loc) {
		relative(component, relative, loc, 0, 0);
	}

	public static void relativeHorizontal(JComponent component,
			JComponent relative, int loc) {
		int offsetX = 0;
		if (loc == LEFT || loc == LEFT_TOP || loc == LEFT_BOTTOM) {//
			offsetX = component.getWidth();
		} else if (loc == RIGHT || loc == RIGHT_BOTTOM || loc == RIGHT_TOP) { //
			offsetX = -component.getWidth();
		}
		relative(component, relative, loc, offsetX, 0);
	}

	public static void relativeVertical(JComponent component,
			JComponent relative, int loc) {
		int offsetY = 0;
		if (loc == TOP || loc == LEFT_TOP || loc == RIGHT_TOP) {//
			offsetY = component.getHeight();
		} else if (loc == BOTTOM || loc == LEFT_BOTTOM || loc == RIGHT_BOTTOM) { //
			offsetY = -component.getHeight();
		}
		relative(component, relative, loc, 0, offsetY);
	}
}
