package scripts.Barrows.types.enums;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;

import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.types.Var;

public class Food {
	public enum Edibles {		
		None(-1,-1),
		Shark(385,20),
		Monkfish(7946,16),
		Swordfish(373,14),
		Bass(365,13),
		Lobster(379,12),
		Tuna(361,10),
		Salmon(329,9),
		Trout(333,7);

		private int Id, healAmount;

		Edibles(final int Id, final int healAmount) {
			this.Id = Id;
			this.healAmount = healAmount;
		}

		public int getId() {
			return Id;
		}

		public int getHealAmount() {
			return healAmount;
		}
	}

	public static void forceFeed() {
		if (GameTab.getOpen() != TABS.INVENTORY) {
			GameTab.open(GameTab.TABS.INVENTORY);
			for (int fsafe = 0; fsafe < 20
					&& !GameTab.getOpen().equals(TABS.INVENTORY); fsafe++) {
				General.sleep(15);
			}
		}
		if (Inventory.find(Var.food.getId()).length > 0) {
			final int t = Inventory.getAll().length;
			Inventory.find(Var.food.getId())[0].click("Eat");
			Timing.waitCondition(new Condition() {
				;
				@Override
				public boolean active() {
					return Inventory.getAll().length < t;
				}
			}, General.random(1200, 1300));
			if (Inventory.getAll().length == t) {
				forceFeed();
			}
		}
	}

	public static void eat() {
		if ((Skills.getActualLevel(Skills.SKILLS.HITPOINTS) - Skills
				.getCurrentLevel(Skills.SKILLS.HITPOINTS)) >= Var.food
				.getHealAmount()) {
			if (GameTab.getOpen() != TABS.INVENTORY) {
				GameTab.open(GameTab.TABS.INVENTORY);
				for (int fsafe = 0; fsafe < 20
						&& !GameTab.getOpen().equals(TABS.INVENTORY); fsafe++) {
					General.sleep(15);
				}
			}
			if (Game.getUptext().contains("Use Vial ->")) {
				Walking.walkTo(Player.getPosition());
				for (int i = 0; i < 10
						&& !Game.getUptext().contains("Use Vial->"); i++) {
					General.sleep(30, 50);
				}
			}
			if (Inventory.find(Var.food.getId()).length > 0) {
				final int t = Inventory.getAll().length;
				Inventory.find(Var.food.getId())[0].click("Eat");
				Timing.waitCondition(new Condition() {
					;
					@Override
					public boolean active() {
						return Inventory.getAll().length < t;
					}
				}, General.random(1200, 1300));
				if (Inventory.getAll().length == t) {
					eat();
				}
			}
		}
	}

	public static void eatInCombat() {
		if (Skills.getActualLevel(Skills.SKILLS.HITPOINTS) < 35
				|| GeneralMethods.getHPPercent() < 50) {
			if (Inventory.find(Var.food.getId()).length > 0) {
				eat();
			}
		}
	}

	public static boolean canEatWithoutWaste() {
		return ((Skills.getActualLevel(Skills.SKILLS.HITPOINTS) - Skills
				.getCurrentLevel(Skills.SKILLS.HITPOINTS)) >= Var.food
				.getHealAmount());
	}

	public static int getFoodAmount() {
		int total = 0;
		for (RSItem i : Inventory.getAll()) {
			if (i.getDefinition() != null) {
				for (String s : i.getDefinition().getActions()) {
					if (s.contains("Eat")) {
						total += 1;
					}
				}
			}
		}
		return total;
	}

	public static int[] getFoodIDs() {
		ArrayList<Integer> foodIDs = new ArrayList<Integer>();
		for (RSItem i : Inventory.getAll()) {
			if (i.getDefinition() != null) {
				for (String s : i.getDefinition().getActions()) {
					if (s.contains("Eat")) {
						if (!foodIDs.contains(i.getID())) {
							foodIDs.add(i.getID());
						}
					}
				}
			}
		}
		return convertIntegers(foodIDs);
	}

	private static int[] convertIntegers(ArrayList<Integer> integers) {
		int[] ret = new int[integers.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = integers.get(i).intValue();
		}
		return ret;
	}
}
