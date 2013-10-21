package scripts.Barrows.methods;

import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

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
						// TODO sleep
					}
				} else {
					Walking.walkTo(altar[0].getPosition());
					// TODO sleep
				}
			} else {
				RSObject[] door = Objects.getAt(new RSTile(3238, 3210, 0));
				if (door.length > 0) {
					if (door[0].isOnScreen()) {
						if (door[0].click("Open")) {
							// TODO sleep
						}
					} else {
						Walking.walkTo(door[0].getPosition());
						// TODO sleep
					}
				}

			}
		} else {
			Walking.walkPath(pathToChurch);
		}
	}

}
