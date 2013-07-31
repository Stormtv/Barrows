package scripts.Barrows.methods;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.types.Var;
import scripts.Barrows.util.RSArea;

public class Pathing {

	public enum Path {
		SWAMP, SHORTCUT
	}

	static Path selectedPath = Path.SWAMP;

	final static RSTile[] pathToGate = { new RSTile(3508, 3481, 0),
			new RSTile(3503, 3481, 0), new RSTile(3498, 3481, 0),
			new RSTile(3493, 3482, 0), new RSTile(3488, 3481, 0),
			new RSTile(3483, 3479, 0), new RSTile(3478, 3476, 0),
			new RSTile(3473, 3475, 0), new RSTile(3468, 3474, 0),
			new RSTile(3465, 3470, 0), new RSTile(3461, 3467, 0),
			new RSTile(3456, 3465, 0), new RSTile(3452, 3461, 0),
			new RSTile(3448, 3458, 0), new RSTile(3444, 3458, 0) };

	final static RSTile[] pathToBoat = { new RSTile(3443, 3455, 0),
			new RSTile(3446, 3451, 0), new RSTile(3449, 3447, 0),
			new RSTile(3451, 3442, 0), new RSTile(3452, 3437, 0),
			new RSTile(3454, 3432, 0), new RSTile(3455, 3427, 0),
			new RSTile(3456, 3422, 0), new RSTile(3459, 3418, 0),
			new RSTile(3460, 3413, 0), new RSTile(3463, 3409, 0),
			new RSTile(3465, 3404, 0), new RSTile(3468, 3400, 0),
			new RSTile(3470, 3395, 0), new RSTile(3472, 3390, 0),
			new RSTile(3474, 3385, 0), new RSTile(3477, 3381, 0),
			new RSTile(3481, 3378, 0), new RSTile(3485, 3375, 0),
			new RSTile(3490, 3374, 0), new RSTile(3495, 3375, 0),
			new RSTile(3499, 3378, 0) };

	final static RSTile[] pathToBarrows = { new RSTile(3516, 3281, 0),
			new RSTile(3526, 3280, 0), new RSTile(3536, 3279, 0),
			new RSTile(3538, 3284, 0), new RSTile(3539, 3289, 0),
			new RSTile(3541, 3294, 0), new RSTile(3543, 3299, 0),
			new RSTile(3544, 3304, 0), new RSTile(3548, 3307, 0),
			new RSTile(3553, 3310, 0), new RSTile(3557, 3313, 0),
			new RSTile(3562, 3315, 0), new RSTile(3565, 3305, 0),
			new RSTile(3564, 3297, 0), new RSTile(3565, 3292, 0),
			new RSTile(3565, 3287, 0) };

	public static boolean isInBarrows() {
		return Var.barrowsArea.contains(Player.getPosition());
	}

	private static boolean goViaSwamp() {
		Walking.control_click = true;
		Walking.walking_timeout = 500;
		if (!warning()) {
			if (isInBarrows())
				return true;

			if (isInBoat())
				return false;

			if (isFromBoatToBarrows()) {
				System.out.println("hi");
				Walking.walkPath(Walking.randomizePath(pathToBarrows, 2, 3));
			} else if (isFromGateToBoat()) {
				if (canEnterBoat())
					enterBoat();
				else
					Walking.walkPath(Walking.randomizePath(pathToBoat, 2, 3));
			} else if (isFromBankToGate()) {
				if (canOpenGate())
					openGate();
				else
					Walking.walkPath(Walking.randomizePath(pathToGate, 2, 3));

			}
		}
		return false;
	}

	public static boolean getToBarrows() {
		if (isInBarrows())
			return true;
		if (isFromBoatToBarrows())
			Walking.randomizePath(pathToBarrows, 2, 3);
		else {
			switch (selectedPath) {
			case SWAMP:
				goViaSwamp();
			case SHORTCUT:
				goViaShortcut();
			}
		}

		return false;
	}

	private static void goViaShortcut() {
		// TODO Auto-generated method stub
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
			if (t.distanceTo(Player.getPosition()) < 10
					&& PathFinding.canReach(t, false)) {
				Walking.walkTo(t);
				return true;
			}
		}
		return false;
	}

	static boolean isFromBoatToBarrows() {
		for (RSTile t : pathToBarrows) {
			if (t.distanceTo(Player.getPosition()) < 20
					&& PathFinding.canReach(t, false))
				return true;
		}
		return false;
	}

	static boolean isFromBankToGate() {
		for (RSTile t : pathToGate) {
			if (t.distanceTo(Player.getPosition()) < 20
					&& PathFinding.canReach(t, false))
				return true;
		}
		return false;
	}

	public static RSTile getRandomTile(RSArea r) {
		int rand = General.random(0, r.getTiles().length - 1);
		if (r.getTiles().length > 0) {
			for (int i = 0; i < r.getTiles().length; i++) {
				if (r.getTiles().length > i && i == rand)
					return r.getTiles()[i];
			}
		}
		return null;
	}
}
