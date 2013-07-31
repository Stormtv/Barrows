package scripts.Barrows.methods;

import java.util.LinkedList;

import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.main.BrotherKilling;

public class TunnelTraversing {

	static int doorId = 1;

	RSTile[] chestDoorTiles = {};

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

	public static boolean can(RSObject c) {
		return c.getID() == 3030 || c.getID() == 3031;
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
		return o.getID() == doorId && o.getDefinition() != null
				&& o.getDefinition().getActions() != null
				&& o.getDefinition().getActions().length > 0
				&& contains("Open", o.getDefinition().getActions());
	}

	static boolean contains(String s, String[] arr) {
		for (String l : arr) {
			if (l == s)
				return true;
		}
		return false;
	}

}
