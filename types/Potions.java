package scripts.Barrows.types;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;

import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.enums.Prayer;

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
		while (canDrinkWithoutWaste() && Inventory.find(PRAYER_POTIONS).length > 0) {
			drink();
		}
	}
	
	public static void drinkPrayerInCombat() {
		if (Prayer.getPoints() - prayerDrain() < 7) {
			drink();
		}
	}
	
	private static int prayerDrain() {
		int drain = 8;
		for (Brothers b: Brothers.values()) {
			if (b.isKilled()) {
				drain++;
			}
		}
		return drain;
	}
	
	public static void superPot() {
		if (Inventory.getCount(161,159,157,2440) > 0) {
			int points = Skills.getCurrentLevel("Strength");
			Inventory.find(161,159,157,2440)[0].click("Drink");
			for (int fsafe = 0;fsafe<50 && Skills.getCurrentLevel("Strength") <= points; fsafe++) {
				General.sleep(40);
			}
		}
		if (Inventory.getCount(167,165,163,2442) > 0) {
			int points = Skills.getCurrentLevel("Defence");
			Inventory.find(167,165,163,2442)[0].click("Drink");
			for (int fsafe = 0;fsafe<50 && Skills.getCurrentLevel("Defence") <= points; fsafe++) {
				General.sleep(40);
			}
		}
		if (Inventory.getCount(149,147,145,2436) > 0) {
			int points = Skills.getCurrentLevel("Attack");
			Inventory.find(149,147,145,2436)[0].click("Drink");
			for (int fsafe = 0;fsafe<50 && Skills.getCurrentLevel("Attack") <= points; fsafe++) {
				General.sleep(40);
			}
		}
		if (Inventory.getCount(2444, 169, 171, 173) > 0) {
			int points = Skills.getCurrentLevel("Range");
			Inventory.find(2444, 169, 171, 173)[0].click("Drink");
			for (int fsafe = 0;fsafe<50 && Skills.getCurrentLevel("Range") <= points; fsafe++) {
				General.sleep(40);
			}
		}
	}
	
}
