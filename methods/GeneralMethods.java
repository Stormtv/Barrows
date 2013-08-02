package scripts.Barrows.methods;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.CustomRet_0P;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Var;
import scripts.wWood.Vars;

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
	
	static void clickObject(RSObject o, String option) {
		clickObject(o,option,0, true);
	}
	
	static void turnTo(final RSTile loc) {
		if (loc == null) {
			return;
		}
		final int cAngle = Camera.getCameraRotation();
		final int angle = 180 + Camera.getTileAngle(loc);
		final int dir = cAngle - angle;
		if (Math.abs(dir) <= 190 && Math.abs(dir) >= 180) {
			return;
		}
		Camera.setCameraRotation(Camera.getCameraRotation()
				+ ((dir > 0 ^ Math.abs(dir) > 180) ? 10 : -10));
	}
	
	static void clickObject(RSObject o, String option, int fail, boolean fast) {
		if (o == null || o.getModel() == null 
				|| o.getID() != Objects.getAt(o.getPosition())[0].getID()
				||Banking.isBankScreenOpen())
			return;
		if (!o.isOnScreen() || fail > 4) {
			RSTile tile = o.getPosition();
			Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
			Walking.walkTo(tile);
			Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
			General.sleep(250, 350);
			while (Player.isMoving() && !o.isOnScreen()) {
				//If Object is moving or if using
				//NPC pass the npc/Object
				turnTo(tile);
			}
			//Additonal Failsafe so that it always clicks properly
			while (Player.isMoving()) General.sleep(30,50);
			//Probably not needed
			if (!o.isOnScreen()) {
				clickObject(o,option,fail+1,fast);
			}
			fail = 0;
		}
		// 5 Is a default offset of 5 Increase this to make it more random
		Point p = getAverage(o.getModel().getAllVisiblePoints(), 10);
		Mouse.move(p);
		if (!fast) {
			for (int fSafe = 0; fSafe < 20
					&& !Game.getUptext().contains(option) || fSafe < 20
					&& !Game.getUptext().contains("Use"); fSafe++)
				General.sleep(10, 15);
			if (Game.getUptext().contains(option)
					|| Game.getUptext().contains("Use")) {
				Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
				Mouse.click(1);
				Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
			} else {
				Mouse.click(3);
				for (int fSafe = 0; fSafe < 20 && !ChooseOption.isOpen(); fSafe++)
					General.sleep(20, 25);
				if (ChooseOption.isOpen() && ChooseOption.isOptionValid(option)) {
					Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
					ChooseOption.select(option);
					Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
				} else if (ChooseOption.isOpen()) {
					ChooseOption.close();
					clickObject(o, option, fail + 1, fast);
				} else {
					clickObject(o, option, fail + 1, fast);
				}
			}
		} else {
			Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
			Mouse.click(1);
			Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		}
		General.sleep(250, 350);
		while (Player.isMoving() && Player.getAnimation() == -1) {
			General.sleep(20, 30);
		}
	}
	
}
