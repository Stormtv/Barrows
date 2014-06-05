package scripts.Barrows.types;

import org.tribot.api.General;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;

import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.enums.Prayer;

public class Potions {

	public static int[] PRAYER_POTIONS = { 2434, 143, 141, 139, 3024, 3026,
			3028, 3030 };
	public static int[] PRAYER_POTIONS_3DOSE = {2434, 139, 3024, 3026};
	public static int[] SUPER_POTS = { 161, 159, 157, 2440, 167, 165, 163,
			2442, 149, 147, 145, 2436, 2444, 169, 171, 173 };
	public static int[] SUPER_POTS_3DOSE = { 2436, 145, 2440, 157, 2442, 163,
			2444, 169 };
	private static int[] PRAYER_POTIONS_LEAST_TO_HIGH = { 3030, 3028, 3026,
			3024, 143, 141, 139, 2434};
	public static int[] SUPER_ATTACK = { 2436, 145, 147, 149 };
	public static int[] SUPER_STRENGTH = { 2440, 157, 159, 161 };
	public static int[] SUPER_DEFENCE = { 2442, 163, 165, 167 };

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
			if (Inventory.find(doseID).length > 0) {
				openInventory();
				if (Inventory.find(doseID)[0].click("Drink")) {
					for (int fail = 0; fail < 10
							&& Player.getAnimation() != 829; fail++) {
						General.sleep(50, 75);
					}
					for (int fsafe = 0; fsafe < 50
							&& Player.getAnimation() == 829; fsafe++) {
						General.sleep(40);
					}
					General.sleep(450, 600);
				}
				if (Game.getUptext().contains("->")) {
					Walking.walkTo(Player.getPosition());
					for (int i = 0; i < 10 && !Game.getUptext().contains("->"); i++) {
						General.sleep(30, 50);
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
		if (Prayer.getPoints() - prayerDrain() < 5) {
			drink();
		}
	}

	public static void openInventory() {
		if (GameTab.getOpen() != TABS.INVENTORY) {
			GameTab.open(TABS.INVENTORY);
			for (int fsafe = 0; fsafe < 20
					&& !GameTab.getOpen().equals(TABS.INVENTORY); fsafe++) {
				General.sleep(15);
			}
		}
	}

	public static int prayerDrain() {
		int drain = 8;
		for (Brothers b : Brothers.values()) {
			if (b.isKilled()) {
				drain++;
			}
		}
		return drain;
	}

	public static void superPot() {
		if (Inventory.getCount(161, 159, 157, 2440) > 0
				&& Skills.getCurrentLevel(Skills.SKILLS.STRENGTH)
						- Skills.getActualLevel(Skills.SKILLS.STRENGTH) < 5) {
			// Strength
			openInventory();
			Inventory.find(161, 159, 157, 2440)[0].click("Drink");
			for (int fail = 0; fail < 10 && Player.getAnimation() != 829; fail++) {
				General.sleep(50, 75);
			}
			for (int fsafe = 0; fsafe < 50 && Player.getAnimation() == 829; fsafe++) {
				General.sleep(40);
			}
			General.sleep(450, 600);
		}
		if (Inventory.getCount(167, 165, 163, 2442) > 0
				&& Skills.getCurrentLevel(Skills.SKILLS.DEFENCE)
						- Skills.getActualLevel(Skills.SKILLS.DEFENCE) < 5) {
			openInventory();
			Inventory.find(167, 165, 163, 2442)[0].click("Drink");
			for (int fail = 0; fail < 10 && Player.getAnimation() != 829; fail++) {
				General.sleep(50, 75);
			}
			for (int fsafe = 0; fsafe < 50 && Player.getAnimation() == 829; fsafe++) {
				General.sleep(40);
			}
			General.sleep(450, 600);
		}
		if (Inventory.getCount(149, 147, 145, 2436) > 0
				&& Skills.getCurrentLevel(Skills.SKILLS.ATTACK)
						- Skills.getActualLevel(Skills.SKILLS.ATTACK) < 5) {
			openInventory();
			Inventory.find(149, 147, 145, 2436)[0].click("Drink");
			for (int fail = 0; fail < 10 && Player.getAnimation() != 829; fail++) {
				General.sleep(50, 75);
			}
			for (int fsafe = 0; fsafe < 50 && Player.getAnimation() == 829; fsafe++) {
				General.sleep(40);
			}
			General.sleep(450, 600);
		}
		if (Inventory.getCount(2444, 169, 171, 173) > 0
				&& Skills.getCurrentLevel(Skills.SKILLS.RANGED)
						- Skills.getActualLevel(Skills.SKILLS.RANGED) < 4) {
			openInventory();
			Inventory.find(2444, 169, 171, 173)[0].click("Drink");
			for (int fail = 0; fail < 10 && Player.getAnimation() != 829; fail++) {
				General.sleep(50, 75);
			}
			for (int fsafe = 0; fsafe < 50 && Player.getAnimation() == 829; fsafe++) {
				General.sleep(40);
			}
			General.sleep(450, 600);
		}
		if (Inventory.getCount(9745, 9743, 9741, 9739) > 0
				&& Skills.getCurrentLevel(Skills.SKILLS.STRENGTH)
						- Skills.getActualLevel(Skills.SKILLS.STRENGTH) < 3) {
			openInventory();
			Inventory.find(9745, 9743, 9741, 9739)[0].click("Drink");
			for (int fail = 0; fail < 10 && Player.getAnimation() != 829; fail++) {
				General.sleep(50, 75);
			}
			for (int fsafe = 0; fsafe < 50 && Player.getAnimation() == 829; fsafe++) {
				General.sleep(40);
			}
			General.sleep(450, 600);
		}
		if (Game.getUptext().contains("->")) {
			Walking.walkTo(Player.getPosition());
			for (int i = 0; i < 10 && !Game.getUptext().contains("->"); i++) {
				General.sleep(30, 50);
			}
		}
	}

	public static int getPrayerDoses() {
		int doses = 0;
		for (int i = 0; i < PRAYER_POTIONS_LEAST_TO_HIGH.length; i++) {
			doses += Inventory.find(PRAYER_POTIONS_LEAST_TO_HIGH[i]).length
					* (i + 1);
		}
		return doses;
	}

}
