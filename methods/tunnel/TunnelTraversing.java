package scripts.Barrows.methods.tunnel;

import org.tribot.api.General;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.main.BrotherKilling;
import scripts.Barrows.methods.GeneralMethods;

public class TunnelTraversing {

	boolean isInChestRoom() {
		RSTile inside = new RSTile(3551, 9693, 0);
		return inside.distanceTo(Player.getPosition()) < 20
				&& PathFinding.canReach(inside, false);
	}

	boolean isInChestRoom(RSTile t) {
		RSTile inside = new RSTile(3551, 9693, 0);
		return inside.distanceTo(t) < 20
				&& PathFinding.canReach(t, inside, false);
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
			if (!comingBack()) {
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
		return true;
	}

	private void openChestDoor() {

	}

	public static void openNextDoor() {
		Walking.walking_timeout = 500;

		TunnelDoor[] path = WTunnelTraverse.pathToChest();

		if (path.length > 0) {
			RSObject[] nextDoor = Objects.getAt(path[0].getLocation());
			if (nextDoor.length > 0) {
				GeneralMethods.clickObject(nextDoor[0], "Open", false);
				General.sleep(10000);
			}
		}
	}

	boolean isBeingAttackedByBrother() {
		return BrotherKilling.aggressiveNPC() != null;
	}

}
