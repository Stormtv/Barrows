package scripts.Barrows.methods;

import java.awt.Point;
import java.awt.Rectangle;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.types.generic.CustomRet_0P;

public class GeneralMethods {
	public static Point getRandomPoint(Rectangle rec) {
		int randomX = getRandomInteger(rec.getMinX() + 10, rec.getMaxX() - 10);
		int randomY = getRandomInteger(rec.getMinY() + 10, rec.getMaxY() - 10);
		return new Point(randomX, randomY);
	}

	static int getRandomInteger(double min, double max) {
		return General.random((int) min, (int) max);
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
}
