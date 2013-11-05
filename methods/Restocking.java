package scripts.Barrows.methods;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.types.enums.Prayer;

public class Restocking {

	static RSTile[] pathToChurch = new RSTile[] { new RSTile(3222, 3217, 0),
			new RSTile(3231, 3218, 0), new RSTile(3234, 3211, 0) };

	public static void restorePrayerAtLumbridge() {
		RSObject[] altar = Objects.find(20, 409);
		if (altar.length > 0
				&& altar[0].getPosition().distanceTo(Player.getPosition()) < 20) {
			if (PathFinding.canReach(altar[0].getPosition(), true)) {
				if (altar[0].isOnScreen()) {
					if (altar[0].click("Pray-at")) {
						while (Player.isMoving())
							General.sleep(100);
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Prayer.getPoints() == Prayer.getLevel();
							}
						}, 2000);
					}
				} else {
					Walking.walkTo(altar[0].getPosition());
					while (Player.isMoving())
						General.sleep(100);
				}
			} else {
				final RSObject door = Pathing.getLumbDoor();
				if (door != null) {
					if (door.isOnScreen()) {
						if (door.click("Open")) {
							Timing.waitCondition(new Condition() {

								@Override
								public boolean active() {
									return door == null;
								}
							}, 1000);
						}
					} else {
						Walking.walkTo(door.getPosition());
						while (Player.isMoving())
							General.sleep(100);
					}
				}

			}
		} else {
			if (canWalkToAltar())
				Walking.walkPath(pathToChurch);
			else {
				if (Player.getAnimation() == -1) {
					if (GameTab.open(TABS.MAGIC)) {
						Mouse.click(573, 237, 1);
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								return Player.getAnimation() != -1;
							}
						}, 2000);
					}
				}
			}
		}
	}

	static boolean canWalkToAltar() {
		for (RSTile t : pathToChurch) {
			if (t.distanceTo(Player.getPosition()) < 15)
				return true;
		}
		return false;
	}
}
