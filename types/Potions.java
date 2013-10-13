package scripts.Barrows.types;

import java.awt.event.KeyEvent;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;

import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.enums.Prayer;

public class Potions {

	public static int[] PRAYER_POTIONS = { 2434, 143, 141, 139 };
	public static int[] SUPER_POTS = { 161, 159, 157, 2440, 167, 165, 163,
			2442, 149, 147, 145, 2436, 2444, 169, 171, 173 };
	private static int[] PRAYER_POTIONS_LEAST_TO_HIGH = {143,141,139,2434};
	public static int SUPER_ATTACK = 2436;
	public static int SUPER_STRENGTH = 2440;
	public static int SUPER_DEFENCE = 2442;

	public static int getPrayerPerDose() {
		return Skills.getActualLevel(Skills.SKILLS.PRAYER) / 4 + 7;
	}

	public static boolean canDrinkWithoutWaste() {
		return (Skills.getActualLevel(Skills.SKILLS.PRAYER) - Skills
				.getCurrentLevel(Skills.SKILLS.PRAYER)) > getPrayerPerDose();
	}

	public static int getBestDoseToDrink() {
		for (int i : PRAYER_POTIONS_LEAST_TO_HIGH) {
			if (Inventory.find(i).length > 0) {
				return i;
			}
		}
		return 0;
	}

	public static void drink() {
		if (canDrinkWithoutWaste()) {
			int doseID = getBestDoseToDrink();
			int points = Skills.getCurrentLevel(Skills.SKILLS.PRAYER);
			if (Inventory.find(doseID).length > 0) {
				openInventory();
				if (Inventory.find(doseID)[0].click("Drink")) {
					for (int fsafe = 0; fsafe < 50
							&& Skills.getCurrentLevel(Skills.SKILLS.PRAYER) <= points; fsafe++) {
						General.sleep(40);
					}
				}
			}
		}
	}

	public static void fillPrayer() {
		while (canDrinkWithoutWaste()
				&& Inventory.find(PRAYER_POTIONS).length > 0) {
			drink();
		}
	}

	public static void drinkPrayerInCombat() {
		if (Prayer.getPoints() - prayerDrain() < 7) {
			drink();
		}
	}

	public static void openInventory() {
		if (GameTab.getOpen() != TABS.INVENTORY) {
			Keyboard.pressKey((char) KeyEvent.VK_ESCAPE);
			for (int fsafe = 0; fsafe < 20
					&& !GameTab.getOpen().equals(TABS.INVENTORY); fsafe++) {
				General.sleep(15);
			}
		}
	}

	private static int prayerDrain() {
		int drain = 8;
		for (Brothers b : Brothers.values()) {
			if (b.isKilled()) {
				drain++;
			}
		}
		return drain;
	}

	public static void superPot() {
		if (Inventory.getCount(161, 159, 157, 2440) > 0) {
			openInventory();
			int points = Skills.getCurrentLevel(Skills.SKILLS.STRENGTH);
			Inventory.find(161, 159, 157, 2440)[0].click("Drink");
			for (int fsafe = 0; fsafe < 50
					&& Skills.getCurrentLevel(Skills.SKILLS.STRENGTH) <= points; fsafe++) {
				General.sleep(40);
			}
		}
		if (Inventory.getCount(167, 165, 163, 2442) > 0) {
			openInventory();
			int points = Skills.getCurrentLevel(Skills.SKILLS.DEFENCE);
			Inventory.find(167, 165, 163, 2442)[0].click("Drink");
			for (int fsafe = 0; fsafe < 50
					&& Skills.getCurrentLevel(Skills.SKILLS.DEFENCE) <= points; fsafe++) {
				General.sleep(40);
			}
		}
		if (Inventory.getCount(149, 147, 145, 2436) > 0) {
			openInventory();
			int points = Skills.getCurrentLevel(Skills.SKILLS.ATTACK);
			Inventory.find(149, 147, 145, 2436)[0].click("Drink");
			for (int fsafe = 0; fsafe < 50
					&& Skills.getCurrentLevel(Skills.SKILLS.ATTACK) <= points; fsafe++) {
				General.sleep(40);
			}
		}
		if (Inventory.getCount(2444, 169, 171, 173) > 0) {
			openInventory();
			int points = Skills.getCurrentLevel(Skills.SKILLS.RANGED);
			Inventory.find(2444, 169, 171, 173)[0].click("Drink");
			for (int fsafe = 0; fsafe < 50
					&& Skills.getCurrentLevel(Skills.SKILLS.RANGED) <= points; fsafe++) {
				General.sleep(40);
			}
		}
	}

}
