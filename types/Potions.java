package scripts.Barrows.types;

import org.tribot.api.General;
import org.tribot.api.Inventory;
import org.tribot.api2007.Skills;

public class Potions {

	int[] PRAYER_POTIONS = { 143, 141, 139, 2434 };

	public int getPrayerPerDose() {
		return Skills.getActualLevel("Prayer") / 4 + 7;
	}

	boolean canDrinkWithoutWaste() {
		return (Skills.getActualLevel("Prayer") - Skills
				.getCurrentLevel("Prayer")) > getPrayerPerDose();
	}

	public int getBestDoseToDrink() {
		for (int i : PRAYER_POTIONS) {
			if (Inventory.find(i).length > 0) {
				return i;
			}
		}
		return 0;
	}

	public void drink() {
		if (canDrinkWithoutWaste()) {
			int doseID = getBestDoseToDrink();
			if (Inventory.find(doseID).length > 0) {
				if (Inventory.find(doseID)[0].click("Drink"))
					General.sleep(1000);
			}
		}
	}
}
