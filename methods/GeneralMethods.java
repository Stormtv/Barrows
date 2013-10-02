package scripts.Barrows.methods;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.CustomRet_0P;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.methods.tunnel.TunnelTraversing;
import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Var;
import scripts.Barrows.util.RSArea;

public class GeneralMethods {

	public static double getHPPercent() {
		return ((double) Skills.getCurrentLevel(Skills.SKILLS.HITPOINTS)
				/ (double) Skills.getActualLevel(Skills.SKILLS.HITPOINTS) * 100);
	}

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

	public static void rightClick(final Point point) {
		DynamicClicking.clickPoint(new CustomRet_0P<Point>() {
			@Override
			public Point ret() {
				return point;
			}
		}, 3);
	}

	// Finds average points in a Point[] use offset to make it random
	static Point getAverage(Point[] pointArray, int offset) {
		int averagex = 0;
		int averagey = 0;
		int totalx = 0;
		int totaly = 0;
		for (int i = 0; i < pointArray.length; i++) {
			totalx = totalx + pointArray[i].x;
			totaly = totaly + pointArray[i].y;
		}
		if (pointArray.length != 0) {
			averagex = totalx / pointArray.length;
			averagey = totaly / pointArray.length;
		}
		return new Point(averagex + General.random(-offset, offset), averagey
				+ General.random(-offset, offset));
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

	public static void clickObject(RSObject o, String option,
			boolean minimapVisible) {
		clickObject(o, option, 0, minimapVisible);
	}

	static boolean turnTo(final Positionable o) {
		if (o == null) {
			return false;
		}
		final int cAngle = Camera.getCameraRotation();
		final int angle = 180 + Camera.getTileAngle(o);
		final int dir = cAngle - angle;
		if (Math.abs(dir) <= 190 && Math.abs(dir) >= 180) {
			return false;
		}
		Camera.setCameraRotation(Camera.getCameraRotation()
				+ ((dir > 0 ^ Math.abs(dir) > 180) ? 10 : -10));
		return true;
	}

	static void clickObject(RSObject o, String option, int fail, boolean minimap) {
		if (o == null || o.getModel() == null || Banking.isBankScreenOpen())
			return;
		if (!o.isOnScreen() || fail > 4) {
			RSTile tile = o.getPosition();
			if (minimap) {
				Walking.control_click = true;
				Walking.walkTo(tile);
				General.sleep(250, 350);
				while (Player.isMoving() && !o.isOnScreen()) {
					turnTo(tile);
				}
				while (Player.isMoving())
					General.sleep(30, 50);
				if (!o.isOnScreen()) {
					clickObject(o, option, fail + 1, minimap);
				}
				fail = 0;
			} else {
				if (!o.isOnScreen()) {
					while(!o.isOnScreen() && turnTo(o)!=false) {
						turnTo(o);
						walkScreen(getFurthestTileOnScreen(Walking
								.generateStraightScreenPath(tile),o));
					}
					while(Player.isMoving())General.sleep(25,40);
					/*if (!o.isOnScreen() && o.getPosition().distanceTo(Player.getRSPlayer()) > 1) {
						Walking.walking_timeout = 500;
						Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
						walkScreen(getFurthestTileOnScreen(Walking
								.generateStraightScreenPath(tile)));
						Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
					} else {
						Camera.turnToTile(o);
					}*/
				}
				if (!o.isOnScreen()) {
					clickObject(o, option, fail + 1, minimap);
				}
			}
		}
		Point p = getAverage(o.getModel().getAllVisiblePoints(), 10);
		Mouse.move(p);

		for (int fSafe = 0; fSafe < 20 && !Game.getUptext().contains(option); fSafe++)
			General.sleep(10, 15);
		if (Game.getUptext().contains(option)
				|| Game.getUptext().contains("Use")) {
			Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
			leftClick(p);
			Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		} else {
			rightClick(p);
			for (int fSafe = 0; fSafe < 20 && !ChooseOption.isOpen(); fSafe++)
				General.sleep(20, 25);
			if (ChooseOption.isOpen() && ChooseOption.isOptionValid(option)) {
				Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
				ChooseOption.select(option);
				Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
				General.sleep(450, 650);
				while (Player.isMoving() && Player.getAnimation() == -1) {
					General.sleep(20, 30);
				}
				return;
			} else if (ChooseOption.isOpen()) {
				ChooseOption.close();
				clickObject(o, option, fail + 1, minimap);
			} else {
				clickObject(o, option, fail + 1, minimap);
			}
		}
	}

	private static final Rectangle screen = new Rectangle(0, 0, 515, 335);

	static void clickPointOnScreen(RSTile r) {
		if (r == null)
			return;
		Point p = getRandomPoint(Projection.getTileBoundsPoly(r, 0).getBounds());
		if (screen != null && p != null && screen.contains(p)) {
			Mouse.click(p, 1);
		}
	}

	public static RSTile getFurthestTileOnScreen(RSTile[] t) {
		RSTile furthestVisibleTile = null;
		RSTile home = Player.getPosition();
		for (RSTile tile : t) {
			if (tile != null && tile.isOnScreen()) {
				if (furthestVisibleTile != null) {
					if (home.distanceTo(furthestVisibleTile) < home
							.distanceTo(tile)
							&& PathFinding.canReach(tile, false)
							&& PathFinding.isTileWalkable(tile) && !tile.equals(Player.getPosition())) {
						furthestVisibleTile = tile;
					}

				} else {
					if (PathFinding.canReach(tile, false)
							&& PathFinding.isTileWalkable(tile))
						furthestVisibleTile = tile;
				}
			}
		}
		return furthestVisibleTile;
	}
	
	public static RSTile getFurthestTileOnScreen(RSTile[] t, RSObject o) {
		RSTile furthestVisibleTile = null;
		RSTile home = Player.getPosition();
		for (RSTile tile : t) {
			if (tile != null && tile.isOnScreen()) {
				if (furthestVisibleTile != null) {
					if (home.distanceTo(furthestVisibleTile) < home
							.distanceTo(tile)
							&& PathFinding.canReach(tile, false)
							&& PathFinding.isTileWalkable(tile) && !tile.equals(Player.getPosition())
							&& o.getPosition().distanceTo(tile) < home.distanceTo(o)) {
						furthestVisibleTile = tile;
					}
				} else {
					if (PathFinding.canReach(tile, false)
							&& PathFinding.isTileWalkable(tile))
						furthestVisibleTile = tile;
				}
			}
		}
		return furthestVisibleTile;
	}
	

	public static RSTile getFurthestTileOnScreen(ArrayList<RSTile> t) {
		RSTile furthestVisibleTile = null;
		RSTile home = Player.getPosition();
		for (RSTile tile : t) {
			if (tile != null && tile.isOnScreen()) {
				if (furthestVisibleTile != null) {
					if (home.distanceTo(furthestVisibleTile) < home
							.distanceTo(tile)
							&& PathFinding.canReach(tile, false)
							&& PathFinding.isTileWalkable(tile)) {
						furthestVisibleTile = tile;
					}

				} else {
					if (PathFinding.canReach(tile, false)
							&& PathFinding.isTileWalkable(tile))
						furthestVisibleTile = tile;
				}
			}
		}
		return furthestVisibleTile;
	}

	public static void walkScreen(RSTile r) {
		if (r == null)
			return;
		clickPointOnScreen(r);
	}

	public static Point getVisiblePoint(RSTile t) {
		if (t != null) {
			Point[] pd = Projection.getTileBounds(t, 0);
			if (pd != null) {
				for (Point c : pd) {
					if (screen.contains(c))
						return c;
				}
			}
		}
		return null;
	}

	public static RSArea getWithin(int distance, RSTile refe) {
		if (refe == null)
			return null;
		if (distance == 0)
			return null;
		RSTile southwest = new RSTile(refe.getX() - distance, refe.getY()
				- distance);
		RSTile northeast = new RSTile(refe.getX() + distance, refe.getY()
				+ distance);
		return new RSArea(southwest, northeast);
	}

	public static boolean click(RSNPC m, String option) {
		Mouse.setSpeed(500);
		if (m.isValid()) {
			if (m.getModel() != null)
				Mouse.move(GeneralMethods.getAverage(m.getModel()
						.getAllVisiblePoints(), 0));
			for (int fSafe = 0; fSafe < 20
					&& !Game.getUptext().contains(option + " " + m.getName()); fSafe++)
				General.sleep(General.random(10, 15));
			if (Game.getUptext().contains(option + " " + m.getName())) {
				Mouse.click(1);
				return true;
			} else {
				Mouse.click(GeneralMethods.getAverage(m.getModel()
						.getAllVisiblePoints(), 0), 3);
				General.sleep(80, 120);
				for (int fSafe = 0; fSafe < 20 && !ChooseOption.isOpen(); fSafe++)
					General.sleep(General.random(10, 15));
				if (ChooseOption.isOpen()) {
					if (ChooseOption.isOptionValid(option + " " + m.getName())) {
						return ChooseOption.select(option + " " + m.getName());
					} else {
						General.println("Misclicked");
						click(m, option);
					}
				}
			}
		}
		return false;
	}
}
