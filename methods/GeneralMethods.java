package scripts.Barrows.methods;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Options;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.util.Util;

import scripts.Barrows.main.Barrows;
import scripts.Barrows.main.BrotherKilling;
import scripts.Barrows.methods.tunnel.Rooms;
import scripts.Barrows.methods.tunnel.Tunnel;
import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Var;

public class GeneralMethods {

	public static double getHPPercent() {
		return ((double) Skills.getCurrentLevel(Skills.SKILLS.HITPOINTS)
				/ (double) Skills.getActualLevel(Skills.SKILLS.HITPOINTS) * 100);
	}

	public static void adjustBrightness() {
		while (Game.getSetting(166) != 4) {
			System.out.println("PLEASE SET BRIGHTNESS TO FULL");
			General.println("PLEASE SET BRIGHTNESS TO FULL");
			if (!GameTab.getOpen().equals(TABS.OPTIONS)) {
				GameTab.open(GameTab.TABS.OPTIONS);
				for (int i = 0; i < 20
						&& !GameTab.getOpen().equals(TABS.OPTIONS); i++) {
					General.sleep(45, 75);
				}
			}
			if (Interfaces.get(261, 6) != null) {
				if (Interfaces.get(261, 6).click("Adjust Screen Brightness")) {
					for (int i = 0; i < 20 && Game.getSetting(166) != 4; i++) {
						General.sleep(10, 30);
					}
				}
			}
		}
	}

	static void notify(String s) {
		System.out.println(s);
		Var.status = s;
	}
	
	public static boolean isRunEnabled() {
		return Game.getSetting(173) == 1;
	}
	
	public static boolean needsToLogout() {
		if (!beSure()) {
			notify("Out of supplies, re-checking 5 times:");
			for (int i = 0; i < 5; i++) {
				notify("Out of supplies, re-checking 5 times: " + (i + 1));
				General.sleep(150);
				if (beSure())
					return false;
			}
			notify("Out of supplies, logging out.");
			return true;
		}
		return false;
	}

	public static boolean beSure() {
		if (Banking.isBankScreenOpen()) {
			return getBankCount(Var.food.getId()) > 10
					|| getBankCount(scripts.Barrows.types.Potions.PRAYER_POTIONS) > 5
					|| hasCastsInBank();
		}
		return false;
	}

	static boolean hasFood() {
		return getBankCount(Var.food.getId()) < 10;
	}
	
	public static void ABCL() {
		// ABC: Examining Random Objects
		Var.abc_util.performExamineObject();

		// ABC: Randomly Rotating Camera
		Var.abc_util.performRotateCamera();

		// ABC: Quest Checker
		Var.abc_util.performQuestsCheck();
		
		// ABC: Music Checker
		Var.abc_util.performMusicCheck();
		
		// ABC: Friend Checker
		Var.abc_util.performFriendsCheck();
		
		// ABC: Equipment Checker
		Var.abc_util.performEquipmentCheck();
		
		// Leave Game
		Var.abc_util.performLeaveGame();

		// Pick up mouse
		Var.abc_util.performPickupMouse();

		// Random Mouse Movement
		Var.abc_util.performRandomMouseMovement();

		// Random Right Click
		Var.abc_util.performRandomRightClick();

		// Rotate camera
		Var.abc_util.performRotateCamera();

		// ABCL: Check XP
		Var.abc_util.performXPCheck(Skills.SKILLS.HUNTER);
		
		// ABCL: Check Combat Tab
		Var.abc_util.performCombatCheck();
	}
	
	public static void enableRun() {
		if (Game.getRunEnergy() >= Var.abc_util.INT_TRACKER.NEXT_RUN_AT.next()) {
			Options.setRunOn(true);
			Var.abc_util.INT_TRACKER.NEXT_RUN_AT.reset();
		}
	}

	static boolean hasCastsInBank() {
		if (scripts.Barrows.types.enums.Magic.getRuneIDs().length == 0
				|| scripts.Barrows.types.enums.Magic.getRuneIDs() == null)
			return true;
		return scripts.Barrows.types.enums.Magic.getRuneIDs() != null
				&& getBankCount(scripts.Barrows.types.enums.Magic.getRuneIDs()) < 50;
	}

	public static int getBankCount(int id) {
		if (Banking.isBankScreenOpen() && Banking.find(id).length > 0)
			return Banking.find(id)[0].getStack();
		return -1;
	}

	public static int getBankCount(int[] ids) {
		int am = -1;
		for (int i : ids) {
			int count = getBankCount(i);
			General.sleep(200);
			if (count > 0)
				am = am + count;
		}
		if (am > -1)
			am++;
		return am;
	}

	public static void takeScreenShot() {
		try {
			File dir = new File(Util.getWorkingDirectory(), "Barrows"
					+ File.separator + "screenshots");
			if (!dir.exists()) {
				dir.mkdir();
			}
			Date k = new Date(System.currentTimeMillis());
			ImageIO.write(
					new Barrows().getScreenshot(true),
					"png",
					new File(dir, "Reward - " + k.toString() + " "
							+ System.currentTimeMillis() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void updateSig() {
		try {
			update(General.getTRiBotUsername().replace(" ", "_"),
					unzero((int) Var.runTime.getElapsed() / 1000),
					unzero(Var.profit), unzero(Var.trips), unzero(Var.chests),
					unzero(Var.pieces));
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static String unzero(final int num) {
		return String.valueOf((num == 0 ? "null" : num));
	}

	public static String update(final String usr, final String time,
			final String pft, final String trips, final String chst,
			final String pcs) {
		final String unformattedUrl = "http://polycoding.com/sigs/integer/barrows/update.php?username=%s&runtime=%s&profit=%s&trips=%s&chests=%s&pieces=%s";
		final String formattedUrl = String.format(unformattedUrl, usr, time,
				pft, trips, chst, pcs);

		final StringBuilder builder = new StringBuilder();

		URL url = null;
		InputStream stream = null;
		try {
			url = new URL(formattedUrl);
			stream = url.openStream();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(stream));
			String line = br.readLine();
			while (line != null) {
				line = br.readLine();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			url = null;
			stream = null;
		}
		return builder.toString();
	}

	public static String updateTracker(final String item, final int amm) {
		final String unformattedUrl = "http://polycoding.com/sigs/integer/stats/?a=%s&add=%s";
		final String formattedUrl = String.format(unformattedUrl,
				item.replace(" ", "%20"), "" + amm);
		final StringBuilder builder = new StringBuilder();
		URL url = null;
		InputStream stream = null;
		try {
			url = new URL(formattedUrl);
			stream = url.openStream();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(stream));
			String line = br.readLine();
			while (line != null) {
				line = br.readLine();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			url = null;
			stream = null;
		}
		return builder.toString();
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
	
	public static void waitNewOrSwitchDelay(final long last_busy_time,
			final boolean combat) {
		// All ABCUtil objects contain the exact same data, so it doesn't matter
		// whether we construct a new one, or use a constant one.
		final ABCUtil abc = new ABCUtil();

		if (Timing.timeFromMark(last_busy_time) >= General.random(8000, 12000)) {
			if (combat) {
				General.sleep(abc.DELAY_TRACKER.NEW_OBJECT_COMBAT.next());

				abc.DELAY_TRACKER.NEW_OBJECT_COMBAT.reset();
			} else {
				General.sleep(abc.DELAY_TRACKER.NEW_OBJECT.next());

				abc.DELAY_TRACKER.NEW_OBJECT.reset();
			}
		} else {
			if (combat) {
				General.sleep(abc.DELAY_TRACKER.SWITCH_OBJECT_COMBAT.next());

				abc.DELAY_TRACKER.SWITCH_OBJECT_COMBAT.reset();
			} else {
				General.sleep(abc.DELAY_TRACKER.SWITCH_OBJECT.next());

				abc.DELAY_TRACKER.SWITCH_OBJECT.reset();
			}
		}
	}


	public static void clickObject(RSObject o, String option,
			boolean minimapVisible, boolean checkReachable) {
		if (option.toLowerCase().contains("attack")) {
			waitNewOrSwitchDelay(Var.lastBusyTime, true);
		} else {
			waitNewOrSwitchDelay(Var.lastBusyTime, false);
		}
		clickObject(o, option, 0, minimapVisible, checkReachable);
		Var.lastBusyTime = System.currentTimeMillis();
	}

	public static boolean turnTilOnScreen(Positionable p) {
		if (p == null) {
			return false;
		} else if (p.getPosition().isOnScreen()) {
			return true;
		}
		int cAngle = Camera.getCameraRotation();
		int angle = 180 + Camera.getTileAngle(p);
		int dir = cAngle - angle;
		if (Math.abs(dir) <= 190 && Math.abs(dir) >= 180) {
			return true;
		}
		Camera.setCameraRotation(Camera.getCameraRotation()
				+ ((dir > 0 ^ Math.abs(dir) > 180) ? 10 : -10));
		return false;
	}

	private static boolean isObjectValid(RSObject o) {
		if (o == null || Banking.isBankScreenOpen()) {
			return false;
		}
		for (RSObject a : Objects.getAt(o.getPosition())) {
			if (a.getID() == o.getID()) {
				return true;
			}
		}
		return false;
	}

	private static void clickObject(RSObject o, String option, int fail,
			boolean minimap, boolean checkReachable) {
		Rooms.TunnelRoom curRoom = Rooms.getRoom();
		Var.status = "Checking Validity of Object";
		if (!isObjectValid(o)) {
			General.println("Invalid Object");
			return;
		}
		if (checkReachable && !PathFinding.canReach(o, true))
			return;
		if (!o.isOnScreen() || fail > 1) {
			RSTile tile = o.getPosition();
			if (minimap) {
				Var.status = "Walking to object";
				Walking.setControlClick(true);
				Walking.walkTo(tile);
				General.sleep(250, 350);
				while (Player.isMoving() && !o.isOnScreen()) {
					General.sleep(15, 30);
				}
				Camera.turnToTile(o);
				while (Player.isMoving())
					General.sleep(30, 50);
			} else {
				Camera.turnToTile(o);
				if (!o.isOnScreen() || fail > 1) {
					if (!Pathing.isInBarrows()) {
						Var.status = "ScreenWalking to object";
						screenWalkTo(o);
					}
				}
			}
		}
		if (fail > 2) {
			Var.status = "Failed to Click";
			return;
		}
		while (Player.isMoving()) {
			General.sleep(15, 30);
		}
		if (!o.isOnScreen()) {
			clickObject(o, option, fail + 1, minimap, checkReachable);
		}

		if (Rooms.getRoom() != null && !BrotherKilling.canKill()
				&& !Tunnel.inCrypt()
				&& !Var.bankArea.contains(Player.getPosition())) {
			for (RSObject a : Objects.getAt(o)) {
				if (o.getModel() == null
						|| o.getModel().getAllVisiblePoints().length <= 0) {
					o = a;
				}
				if (a.getModel() != null
						&& a.getModel().getPoints().length >= o.getModel()
								.getPoints().length) {
					o = a;
				}
			}
		}
			
		if (Rooms.getRoom() != curRoom) {
			return;
		}
		Var.debugObject = o;
		Var.centerPoint = getAverage(o.getModel().getAllVisiblePoints(), 0);
		Var.status = "Clicking Object";
		Point p = getAverage(o.getModel().getAllVisiblePoints(), 7);
		Mouse.move(p);
		for (int fSafe = 0; fSafe < 20 && !Game.getUptext().contains(option); fSafe++)
			General.sleep(10, 15);
		if (Game.getUptext().contains(option)
				|| Game.getUptext().contains("Use")) {
			Mouse.click(1);
			Var.debugObject = null;
			Var.centerPoint = null;
			General.sleep(250, 350);
			while (Player.isMoving() && Player.getAnimation() == -1) {
				General.sleep(20, 30);
			}
		} else {
			Var.status = "Right Clicking Object";
			Mouse.click(3);
			for (int fSafe = 0; fSafe < 20 && !ChooseOption.isOpen(); fSafe++)
				General.sleep(20, 25);
			if (ChooseOption.isOpen() && ChooseOption.isOptionValid(option)) {
				ChooseOption.select(option);
				Var.debugObject = null;
				Var.centerPoint = null;
				General.sleep(250, 350);
				while (Player.isMoving() && Player.getAnimation() == -1) {
					General.sleep(20, 30);
				}
				return;
			} else if (ChooseOption.isOpen()) {
				ChooseOption.close();
				clickObject(o, option, fail + 1, minimap, checkReachable);
			} else {
				clickObject(o, option, fail + 1, minimap, checkReachable);
			}
		}
	}

	public static void screenWalkTo(Positionable p) {
		waitNewOrSwitchDelay(Var.lastBusyTime, false);
		screenWalkTo(p, 0);
		Var.lastBusyTime = System.currentTimeMillis();
	}
	
	private static void screenWalkTo(Positionable p, int count) {
		if (count == 20) {
			return;
		}
		if (!Player.getPosition().equals(p)) {
			Var.status = "Generating Target Tile";
			Positionable target = getClosestVisibleTile(p);
			if (target == null) {
				return;
			}
			Var.targetTile = (RSTile) target;
			Var.status = "Clicking Target Tile";
			Point i = Projection.tileToScreen(target, 0);
			Mouse.move(i);
			for (int fSafe = 0; fSafe < 15
					&& !Game.getUptext().contains("Walk"); fSafe++)
				General.sleep(5, 10);
			if (Game.getUptext().contains("Walk")) {
				Mouse.click(1);
				for (int fail = 0; fail < 20 && Game.getDestination() == null; fail++) {
					General.sleep(12, 18);
				}
				if (Game.getDestination() == null) {
					screenWalkTo(p, count + 1);
				}
			} else {
				Var.status = "Right clicking target tile";
				Mouse.click(3);
				for (int fSafe = 0; fSafe < 15 && !ChooseOption.isOpen(); fSafe++)
					General.sleep(5, 10);
				if (ChooseOption.isOpen() && ChooseOption.isOptionValid("Walk")) {
					ChooseOption.select("Walk here");
					for (int fail = 0; fail < 20
							&& Game.getDestination() == null; fail++) {
						General.sleep(12, 18);
					}
					if (Game.getDestination() == null) {
						screenWalkTo(p, count + 1);
					}
				} else if (ChooseOption.isOpen()) {
					ChooseOption.close();
				}
			}
			while (Player.isMoving()
					&& !p.getPosition().isOnScreen()
					&& !Player.getPosition().equals(target)
					&& Player.getPosition().distanceTo(target) > 2
					&& Player.getPosition().distanceTo(p) > target
							.getPosition().distanceTo(p)) {
				Var.status = "Waiting until near target";
				General.sleep(20, 50);
			}
			if (!p.getPosition().isOnScreen() && count < 20) {
				Var.status = "Screenwalking again";
				screenWalkTo(p, count + 1);
			}
		}
	}

	private static Positionable getClosestVisibleTile(Positionable p) {
		RSTile closestTile = null;
		RSTile home = Player.getPosition();
		for (RSTile t : getViableTiles()) {
			if (closestTile == null) {
				if (!t.equals(home) && t.distanceTo(p) < home.distanceTo(p)) {
					closestTile = t;
				}
			} else {
				if (!t.equals(home) && t.distanceTo(p) < home.distanceTo(p)
						&& closestTile.distanceTo(p) > t.distanceTo(p)) {
					closestTile = t;
				}
			}
		}
		return closestTile;
	}

	private static ArrayList<RSTile> getViableTiles() {
		ArrayList<RSTile> tiles = new ArrayList<RSTile>();
		RSTile home = Player.getPosition();
		for (int x = home.getX() - 10; x <= home.getX() + 10; x++) {
			for (int y = home.getY() - 10; y <= home.getY() + 10; y++) {
				RSTile testTile = new RSTile(x, y);
				if (testTile.isOnScreen()
						&& PathFinding.canReach(testTile, false)
						&& PathFinding.isTileWalkable(testTile)
						&& !tiles.contains(testTile)) {
					tiles.add(testTile);
				}
			}
		}
		Var.viableTiles = tiles;
		return tiles;
	}

	public static boolean click(RSNPC m, String option) {
		if (option.toLowerCase().contains("attack")) {
			waitNewOrSwitchDelay(Var.lastBusyTime, true);
		} else {
			waitNewOrSwitchDelay(Var.lastBusyTime, false);
		}
		if (m != null && m.isValid() && m.getModel() != null) {
			if (!m.isOnScreen()) {
				Camera.turnToTile(m);
			}
			if (m.getModel() != null)
				Mouse.move(GeneralMethods.getAverage(m.getModel()
						.getAllVisiblePoints(), 0));
			for (int fSafe = 0; fSafe < 20
					&& !Game.getUptext().contains(option + " " + m.getName()); fSafe++)
				General.sleep(5, 10);
			if (Game.getUptext().contains(option + " " + m.getName())) {
				Mouse.click(1);
				return true;
			} else if (m.getModel() != null) {
				Mouse.click(GeneralMethods.getAverage(m.getModel()
						.getAllVisiblePoints(), 0), 3);
				General.sleep(80, 120);
				for (int fSafe = 0; fSafe < 20 && !ChooseOption.isOpen(); fSafe++)
					General.sleep(General.random(10, 15));
				if (ChooseOption.isOpen()) {
					if (ChooseOption.isOptionValid(option + " " + m.getName())) {
						return ChooseOption.select(option + " " + m.getName());
					} else {
						ChooseOption.close();
						for (int fSafe = 0; fSafe < 20 && ChooseOption.isOpen(); fSafe++)
							General.sleep(General.random(10, 15));
					}
				}
			}
		}
		Var.lastBusyTime = System.currentTimeMillis();
		return false;
	}

	private static boolean groundItemCheck(RSGroundItem g) {
		for (RSGroundItem a : GroundItems.getAt(g)) {
			if (a.getID() == g.getID() && PathFinding.canReach(a, false)) {
				return true;
			}
		}
		return false;
	}

	public static boolean arrayContains(int array[], int id) {
		for (int i : array) {
			if (i == id)
				return true;
		}
		return false;
	}

	public static void leftClick(RSGroundItem n) {
		waitNewOrSwitchDelay(Var.lastBusyTime, false);
		try {
			if (!groundItemCheck(n))
				return;
			Var.status = "Looting: " + n.getDefinition().getName();
			if (!n.isOnScreen()) {
				enableRun();
				screenWalkTo(n);
				General.sleep(250, 350);
				while (Player.isMoving()) {
					General.sleep(20, 30);
				}
			}
			if (n.getPosition().distanceTo(Player.getPosition()) > 8) {
				enableRun();
				screenWalkTo(n);
			}
			if (!groundItemCheck(n))
				return;
			enableRun();
			n.click("Take " + n.getDefinition().getName());
			General.sleep(250, 350);
			while (Player.isMoving()) {
				Var.lastBusyTime = System.currentTimeMillis();
				General.sleep(40);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String lastMessage() {
		try {
			if (Interfaces.get(137) != null) {
				for (int i = Interfaces.get(137, 2).getChildren().length - 1; i > 0; i--) {
					if (Interfaces.get(137, 2).getChildren()[i].getText() != "") {
						return Interfaces.get(137, 2).getChildren()[i]
								.getText();
					}
				}
			}
		} catch (Exception e) {
		}
		return "";
	}

}
