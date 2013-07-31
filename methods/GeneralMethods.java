package scripts.Barrows.methods;

import java.awt.Point;
import java.awt.Rectangle;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.types.generic.CustomRet_0P;
import org.tribot.api2007.Interfaces;

import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Var;

public class GeneralMethods {

	public boolean tunnelInterface() {
		return Interfaces.get(210, 0) != null;
	}

	public static Point getRandomPoint(Rectangle rec) {
		int randomX = getRandomInteger(rec.getMinX() + 10, rec.getMaxX() - 10);
		int randomY = getRandomInteger(rec.getMinY() + 10, rec.getMaxY() - 10);
		return new Point(randomX, randomY);
	}

	static int getRandomInteger(double min, double max) {
		return General.random((int) min, (int) max);
	}

	public static boolean contains(Object s, Object[] arr) {
		for (Object l : arr) {
			if (l == s)
				return true;
		}
		return false;
	}

	public static boolean contains(int s, int[] arr) {
		for (int l : arr) {
			if (l == s)
				return true;
		}
		return false;
	}

	// Fastest point clicking method (built for sudoku)
	public static void leftClick(final Point point) {
		DynamicClicking.clickPoint(new CustomRet_0P<Point>() {
			@Override
			public Point ret() {
				return point;
			}
		}, 1);
	}

	static void leftClick(int x, int y) {
		leftClick(new Point(x, y));
	}

	public void assignNewBrother() {
		for (Brother.Brothers b : Brother.Brothers.values()) {
			if (!b.isKilled() && !b.isTunnel()) {
				if (Var.curBrother == null) {
					Var.curBrother = b;
				} else if (Var.curBrother.killOrder() > b.killOrder()) {
					Var.curBrother = b;
				}
			}
		}
	}
}
