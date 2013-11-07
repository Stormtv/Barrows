package scripts.Barrows.methods;

import java.awt.event.KeyEvent;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;

import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Food;

public class Looting {
	private static int getFoodAmount() {
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

	public static void loot(int... t) {
		try {
			RSGroundItem[] itemsOnG = GroundItems.findNearest(t);
			if (itemsOnG.length == 0) {
				return;
			}
			RSItem[] food = Inventory.find(Food.getFoodIDs());
			if ((Inventory.getAll().length + itemsOnG.length) > 28) {
				if (!(GameTab.getOpen() == TABS.INVENTORY)) {
					Keyboard.pressKey((char) KeyEvent.VK_ESCAPE);
				}
				if (getFoodAmount() > 0) {
					for (int i = 0; i < itemsOnG.length && i < food.length; i++) {
						eatFirstFood();
					}
				}
			}
			for (int a = 0; a < itemsOnG.length; a++) {
				if (!PathFinding.canReach(itemsOnG[a], false)) {
					continue;
				}
				if (Inventory.isFull()
						&& Inventory.find(Var.food.getId()).length > 0) {
					RSItem[] foodd = Inventory.find(Food.getFoodIDs());
					if ((Inventory.getAll().length + itemsOnG.length) > 28) {
						if (!(GameTab.getOpen() == TABS.INVENTORY)) {
							Keyboard.pressKey((char) KeyEvent.VK_ESCAPE);
						}
						if (getFoodAmount() > 0) {
							for (int i = 0; i < itemsOnG.length
									&& i < foodd.length; i++) {
								eatFirstFood();
							}
						}
					}
				}
				if (!Inventory.isFull() && itemsOnG[a] != null
						&& PathFinding.canReach(itemsOnG[a], false)) {
					GeneralMethods.leftClick(itemsOnG[a]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void eatFirstFood() {
		for (RSItem item : Inventory.getAll()) {
			if (item.getDefinition() != null) {
				for (String s : item.getDefinition().getActions()) {
					final int number = Inventory.getAll().length;
					if (s.contains("Eat")) {
						item.click("Eat");
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								return Inventory.getAll().length < number;
							}
						}, General.random(1200, 1300));
						for (int fsafe = 0; fsafe < 5
								&& Player.getAnimation() != -1; fsafe++) {
							General.sleep(75, 100);
						}
						return;
					}
				}
			}
		}
	}
}
