package scripts.Barrows.methods.tunnel;

import java.util.LinkedList;

import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.main.BrotherKilling;
import scripts.Barrows.methods.GeneralMethods;

public class TunnelTraversing {

	static int[] doorIDs = { 20691, 20696, 20715, 26082, 20701, 20710, 20694,
			20713, 20708, 20689 };

	RSTile[] chestDoorTiles = {};

	RSTile[] adjacentTiles = { new RSTile(3541, 9695, 0),
			new RSTile(3541, 9694, 0), new RSTile(3552, 9705, 0),
			new RSTile(3551, 9705, 0), new RSTile(3562, 9694, 0),
			new RSTile(3562, 9695, 0), new RSTile(3551, 9684, 0),
			new RSTile(3552, 9684, 0) };

	boolean isInChestRoom() {
		RSTile inside = new RSTile(3551, 9693, 0);
		return inside.distanceTo(Player.getPosition()) < 20
				&& PathFinding.canReach(inside, false);
	}

	boolean canOpenToChest() {
		for (RSTile t : chestDoorTiles) {
			RSObject[] k = Objects.getAt(t);
			if (PathFinding.canReach(t, false) && k.length > 0
					&& isOpenable(k[0]))
				return true;
		}
		return false;
	}

	void walkToChest() {
		if (isBeingAttackedByBrother()) {
			BrotherKilling.killBrotherInTunnel();
		} else {
			if (canOpenToChest()) {
				openChestDoor();
			} else {
				openNextDoor();
			}
		}
	}

	void traverseTunnel() {
		if (comingBack()) {
			walkToChest();
		} else {
			walkToRope();
		}
	}

	private void walkToRope() {
		// TODO Auto-generated method stub

	}

	private boolean comingBack() {
		// TODO Auto-generated method stub
		return false;
	}

	private void openChestDoor() {

	}

	private void openNextDoor() {

	}

	boolean isBeingAttackedByBrother() {
		return false;
	}

	public static RSObject getDoor() {
		LinkedList<RSObject> possible = new LinkedList<RSObject>();
		for (RSObject b : Objects.getAll(20)) {
			if (isOpenable(b)) {
				possible.add(b);
			}
		}
		if (possible.size() > 0) {
			final RSObject[] array = possible.toArray(new RSObject[possible
					.size()]);
			RSObject[] k = Objects.sortByDistance(Player.getPosition(), array);
			if (k.length > 0) {
				if (k.length > 1) {
					return k[1];
				} else {
					return k[0];
				}
			}
		}
		return null;

	}

	static boolean isOpenable(RSObject o) {
		return o.getID() == 23731
				&& o.getDefinition() != null
				&& o.getDefinition().getActions() != null
				&& o.getDefinition().getActions().length > 0
				&& GeneralMethods.contains("Open", o.getDefinition()
						.getActions());
	}

	static boolean isDoor(int i) {
		return GeneralMethods.contains(i, doorIDs);
	}
}
