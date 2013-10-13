package scripts.Barrows.methods;

import org.tribot.api2007.Game;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Options;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.types.Var;

public class Pathing {

	public enum PathBarrows {
		SWAMP, SHORTCUT
	}

	public enum PathBank {
		HOUSE, ECTOPHIAL
	}

	static PathBarrows selectedPath = PathBarrows.SWAMP;

	final static RSTile[] pathToGate = { new RSTile(3508, 3481, 0),
			new RSTile(3503, 3481, 0), new RSTile(3498, 3481, 0),
			new RSTile(3493, 3482, 0), new RSTile(3488, 3481, 0),
			new RSTile(3483, 3479, 0), new RSTile(3478, 3476, 0),
			new RSTile(3473, 3475, 0), new RSTile(3468, 3474, 0),
			new RSTile(3465, 3470, 0), new RSTile(3461, 3467, 0),
			new RSTile(3456, 3465, 0), new RSTile(3452, 3461, 0),
			new RSTile(3448, 3458, 0), new RSTile(3444, 3458, 0) };

	final static RSTile[] pathToBoat = { new RSTile(3443, 3456, 0),
			new RSTile(3449, 3455, 0), new RSTile(3455, 3455, 0),
			new RSTile(3456, 3449, 0), new RSTile(3458, 3443, 0),
			new RSTile(3458, 3437, 0), new RSTile(3460, 3431, 0),
			new RSTile(3458, 3425, 0), new RSTile(3462, 3420, 0),
			new RSTile(3467, 3416, 0), new RSTile(3467, 3410, 0),
			new RSTile(3467, 3404, 0), new RSTile(3475, 3361, 0),
			new RSTile(3467, 3401, 0), new RSTile(3470, 3395, 0),
			new RSTile(3472, 3389, 0), new RSTile(3476, 3384, 0),
			new RSTile(3480, 3379, 0), new RSTile(3486, 3376, 0),
			new RSTile(3492, 3376, 0), new RSTile(3497, 3380, 0) };

	final static RSTile[] pathToBarrows = { new RSTile(3522, 3285, 0),
			new RSTile(3522, 3281, 0), new RSTile(3526, 3280, 0),
			new RSTile(3530, 3280, 0), new RSTile(3534, 3280, 0),
			new RSTile(3536, 3284, 0), new RSTile(3537, 3288, 0),
			new RSTile(3540, 3292, 0), new RSTile(3544, 3295, 0),
			new RSTile(3544, 3299, 0), new RSTile(3547, 3302, 0),
			new RSTile(3551, 3305, 0), new RSTile(3553, 3309, 0),
			new RSTile(3557, 3309, 0), new RSTile(3598, 3342, 0),
			new RSTile(3558, 3310, 0), new RSTile(3561, 3313, 0),
			new RSTile(3565, 3313, 0), new RSTile(3565, 3308, 0),
			new RSTile(3565, 3304, 0), new RSTile(3565, 3300, 0),
			new RSTile(3565, 3296, 0), new RSTile(3565, 3292, 0), };

	public static boolean isInBarrows() {
		return Var.barrowsArea.contains(Player.getPosition());
	}

	private static boolean goViaSwamp() {
		Walking.control_click = true;
		Walking.walking_timeout = 5500;
		if (Game.getRunEnergy() > 10 && !Game.isRunOn())
			Options.setRunOn(true);
		Mouse.setSpeed(130);
		if (!warning()) {
			if (isInBarrows())
				return true;
			if (isInBoat()) {
				General.sleep(500);
				return false;
			}
			if (isFromGateToBoat()) {
				if (canEnterBoat())
					enterBoat();
				else
					Walking.walkPath(pathToBoat);
				General.sleep(500);
			} else if (isFromBankToGate()) {
				if (canOpenGate())
					openGate();
				else
					Walking.walkPath(pathToGate);

			}
		}
		return false;
	}

	public static boolean getToBarrows() {
		if (isInBarrows())
			return true;

		if (isFromBoatToBarrows()) {
			Walking.walkPath(pathToBarrows);
		} else {
			switch (selectedPath) {
			case SWAMP:
				goViaSwamp();
			case SHORTCUT:
				goViaShortcut();
			}
		}

		return false;
	}

	public static void goViaShortcut() {
		if (isNearTrapDoor()) {
			General.println("I am near the trap door");
			RSObject[] trapdoor = Objects.getAt(new RSTile(3495, 3464, 0));
			if (trapdoor.length > 0 && Player.getPosition().distanceTo(trapdoor[0])<15) {
				General.println("Trapdoor length is > 0");
				GeneralMethods.clickObject(trapdoor[0], "Open", true);
			} else {
				General.println("Trapdoor is far away walking closer");
				Walking.walkTo(new RSTile(3506, 3469, 0));
			}
		} else {
			if (isUnderground()) {
				General.println("I am underground");
				RSObject[] door = Objects.getAt(new RSTile(3500, 9812, 0));
				if (door.length > 0
						&& Player.getPosition().distanceTo(door[0]) < 20) {
					GeneralMethods.clickObject(door[0], "Open", true);
					for (int fail=0;fail<50 && !Player.getPosition().equals(new RSTile(3509,3449,0)); fail++) {
						General.sleep(25,50);
					}
				} else {
					if (new RSTile(3477, 9845, 0).distanceTo(Player
							.getRSPlayer()) < 15
							&& PathFinding.canReach(new RSTile(3477, 9845, 0),
									false)) {
						RSObject[] wall = Objects.getAt(new RSTile(3480, 9836,
								0));
						if (wall.length > 0) {
							GeneralMethods.clickObject(wall[0], "Search", true);
						}
					} else {
						Walking.walkPath(pathUnderground);
					}
				}
			} else {
				if (new RSTile(3505, 3437, 0).distanceTo(Player.getRSPlayer()) < 15
						&& PathFinding.canReach(new RSTile(3505, 3437, 0),
								false)) {
					RSObject[] bridge = Objects
							.getAt(new RSTile(3502, 3431, 0));
					if (bridge.length > 0 && Player.getPosition().distanceTo(bridge[0])<15) {
						General.println("Bridge Length > 0");
						GeneralMethods.clickObject(bridge[0], "Cross", true);
						General.sleep(3000);
						if (!PathFinding.canReach(new RSTile(3505, 3437, 0),
								false)) {
							for (int fail = 0; fail < 30
									&& !Player.getPosition().equals(
											new RSTile(3503, 3425, 0)); fail++) {
								General.sleep(100, 200);
							}
						}
					} else {
						General.println("Bridge far away walking to it");
						Walking.walkTo(new RSTile(3505,3437,0));
					}
				} else {
					if (canEnterBoat())
						enterBoat();
					else
						Walking.walkPath(pathToBoatFromShortcut);
				}
			}
		}
	}

	static boolean isUnderground() {
		for (RSTile r : pathUnderground) {
			if (r.distanceTo(Player.getPosition()) < 15
					|| new RSTile(3477, 9845, 0).distanceTo(Player
							.getRSPlayer()) < 15)
				return true;
		}
		return false;

	}

	static RSTile[] pathToBoatFromShortcut = { new RSTile(3495, 3416),
			new RSTile(3493, 3407), new RSTile(3495, 3396),
			new RSTile(3496, 3386) };

	static boolean canWalkUnderground() {
		for (RSTile r : pathToBoatFromShortcut) {
			if (r.distanceTo(Player.getPosition()) < 15)
				return true;
		}
		return false;
	}

	static RSTile[] pathUnderground = new RSTile[] { new RSTile(3483, 9826, 0),
			new RSTile(3492, 9817, 0), new RSTile(3499, 9811, 0) };

	static boolean isNearTrapDoor() {
		return new RSTile(3505, 3468, 0).distanceTo(Player.getRSPlayer()) < 15
				|| new RSTile(3495, 3464, 0).distanceTo(Player.getRSPlayer()) < 15;
	}

	private static void enterBoat() {
		RSObject[] boat = Objects.findNearest(9999, 6970);
		if (boat.length > 0) {
			if (boat[0].isOnScreen()) {
				if (boat[0].click("Board")) {
					General.sleep(1000);
					while (Player.isMoving())
						General.sleep(100);
				}
			} else {
				Walking.walkTo(boat[0].getPosition());
				while (Player.isMoving() && boat.length > 0
						&& !boat[0].isOnScreen())
					General.sleep(100);
			}
		}
	}

	static boolean isInBoat() {
		return Interfaces.get(322, 0) != null;
	}

	static boolean canEnterBoat() {
		RSObject[] boat = Objects.findNearest(9999, 6970);
		return !isInBoat() && boat.length > 0
				&& Player.getPosition().distanceTo(boat[0].getPosition()) < 20;
	}

	private static void openGate() {
		RSObject[] gate = Objects.getAt(new RSTile(3443, 3458, 0));
		if (gate.length > 0) {
			if (gate[0].isOnScreen()) {
				if (gate[0].click("Open")) {
					General.sleep(1000);
					while (Player.isMoving())
						General.sleep(100);
				}
			} else {
				Walking.walkTo(gate[0].getPosition());
				while (Player.isMoving() && gate.length > 0
						&& !gate[0].isOnScreen())
					General.sleep(100);
			}
		}
	}

	static boolean warning() {
		if (Interfaces.get(580, 17) != null) {
			Interfaces.get(580, 17).click("");
			General.sleep(1000);
			return true;
		}
		return false;
	}

	static boolean canOpenGate() {
		return new RSTile(3443, 3459, 0).distanceTo(Player.getPosition()) < 10
				&& !PathFinding.canReach(new RSTile(3444, 3454, 0), false)
				&& Objects.getAt(new RSTile(3443, 3458, 0)).length > 0;
	}

	static boolean isFromGateToBoat() {
		for (RSTile t : pathToBoat) {
			if (t.distanceTo(Player.getPosition()) < 5
					&& PathFinding.canReach(t, false)) {
				Walking.walkTo(t);
				return true;
			}
		}
		return false;
	}

	static boolean isFromBoatToBarrows() {
		for (RSTile t : pathToBarrows) {
			if (t.distanceTo(Player.getPosition()) < 5
					&& PathFinding.canReach(t, false))
				return true;
		}
		return false;
	}

	static boolean isFromBankToGate() {
		for (RSTile t : pathToGate) {
			if (t.distanceTo(Player.getPosition()) < 5
					&& PathFinding.canReach(t, false))
				return true;
		}
		return false;
	}
}
