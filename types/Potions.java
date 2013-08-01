package scripts.Barrows.types;

import org.tribot.api.General;
import org.tribot.api.Inventory;
import org.tribot.api2007.Skills;

public class Potions {

	static int[] PRAYER_POTIONS = { 143, 141, 139, 2434 };

	public static int getPrayerPerDose() {
		return Skills.getActualLevel("Prayer") / 4 + 7;
	}

	public static boolean canDrinkWithoutWaste() {
		return (Skills.getActualLevel("Prayer") - Skills
				.getCurrentLevel("Prayer")) > getPrayerPerDose();
	}

	public static int getBestDoseToDrink() {
		for (int i : PRAYER_POTIONS) {
			if (Inventory.find(i).length > 0) {
				return i;
			}
		}
		return 0;
	}

	public static void drink() {
		if (canDrinkWithoutWaste()) {
			int doseID = getBestDoseToDrink();
			int points = Skills.getCurrentLevel("Prayer");
			if (Inventory.find(doseID).length > 0) {
				if (Inventory.find(doseID)[0].click("Drink")) {
					for (int fsafe = 0;fsafe<50 && Skills.getCurrentLevel("Prayer") <= points; fsafe++) {
						General.sleep(40);
					}
				}
			}
		}
	}
	
	public static void fillPrayer() {
		while (canDrinkWithoutWaste()) {
			drink();
		}
	}
	
}
