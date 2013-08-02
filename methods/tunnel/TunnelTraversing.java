package scripts.Barrows.methods.tunnel;

import java.util.LinkedList;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.BarrowsScript;
import scripts.Barrows.main.BrotherKilling;

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

	private boolean canOpenToChest() {
		// TODO Auto-generated method stub
		return false;
	}

	void traverseTunnel() {
		if (TunnelPuzzle.isPuzzleScreenOpen())
			TunnelPuzzle.solvePuzzle();
		else {
			if (comingBack()) {
				walkToChest();
			} else {
				walkToRope();
			}
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

	public static void openNextDoor() {
		Walking.walking_timeout = 500;
		RSObject nextDoor = getDoor();
		if (nextDoor != null) {
			if (TunnelSidePath.isInSidePath()) {
				TunnelSidePath.walk();
			} else {
				if (nextDoor.isOnScreen()) {
					if (containsMouse(nextDoor.getModel())
							&& nextDoor.click("Open")) {
						General.sleep(1000);
						while (Player.isMoving())
							General.sleep(100);
						General.sleep(1000);
					} else {
						nextDoor.hover();
					}

				} else {
					if (nextDoor.getPosition().distanceTo(Player.getPosition()) < 13) {
						Camera.turnToTile(nextDoor.getPosition());
						Camera.setCameraAngle(40);
					} else {
						TunnelSidePath.walk();
					}
				}
			}
		}

	}

	boolean isBeingAttackedByBrother() {
		return false;
	}

	public static RSObject getDoor() {
		LinkedList<RSObject> possible = new LinkedList<RSObject>();
		for (RSObject b : Objects.getAll(50)) {
			if (TunnelDoor.isOpenable(b) && b != null
					&& b.getModel().getPoints().length == 1494
					&& PathFinding.canReach(b.getPosition(), true)
					&& !possible.contains(b)) {
				possible.add(b);
			}
		}
		if (possible.size() > 0) {
			final RSObject[] array = possible.toArray(new RSObject[possible
					.size()]);
			RSObject[] k = Objects.sortByDistance(Player.getPosition(), array);
			if (k.length > 0) {
				System.out.println(k.length);
				if (k.length > 1) {
					if (k.length > 2) {
						RSObject[] more = removedFirst(k);
						if (more != null && more.length > 0) {
							if (more.length > 1) {
								RSObject[] closest = Objects.sortByDistance(
										new RSTile(3551, 9693, 0), more);
								if (closest != null && closest.length > 0) {
									BarrowsScript.status = "Multiple doors avaliable, choosing closest to chest";
									return closest[0];
								}
							}
							BarrowsScript.status = "Two doors avaliable, choosing the second closest";
							return more[0];
						}
					} else {
						BarrowsScript.status = "Two doors avaliable, choosing the second closest";
						return k[1];
					}
				} else {
					BarrowsScript.status = "Only one door avaliable, choosing it.";
					return k[0];
				}
			}
		}
		return null;

	}

	public static RSObject[] removedFirst(RSObject[] symbols) {
		RSObject[] copy = new RSObject[symbols.length - 1];
		System.arraycopy(symbols, 0, copy, 0, 0);
		System.arraycopy(symbols, 0 + 1, copy, 0, symbols.length - 0 - 1);
		return copy;
	}

	public static boolean containsMouse(RSModel m) {
		return m.getEnclosedArea().contains(Mouse.getPos());
	}
}
