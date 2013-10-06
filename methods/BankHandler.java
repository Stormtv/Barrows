package scripts.Barrows.methods;

import java.util.ArrayList;

import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;

import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Equipment;
import scripts.Barrows.types.Potions;

public class BankHandler {

	public int[] getRequiredItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();

		for (int i : Equipment.requiedEquipment()) {
			items.add(i);
		}
		items.add(Var.food.getId());
		items.add(Var.SPADE_ID);
		items.add(Equipment.getEquipmentID(Equipment.Gear.ARROW));
		for (int i : Potions.PRAYER_POTIONS)
			items.add(i);
		for (int i : Potions.SUPER_POTS)
			items.add(i);

		int[] intArray = new int[items.size()];
		for (int i = 0; i < items.size(); i++) {
			intArray[i] = items.get(i).intValue();
		}

		return intArray;
	}

	void bank() {
		if (Banking.isPinScreenOpen()) {
			Banking.inPin();
		} else {
			if (Banking.isBankScreenOpen()) {
				if (Inventory.getCount(getRequiredItems()) > 0) {
					Banking.depositAllExcept(getRequiredItems());
				} else {
					final int count = Inventory.getAll().length;
					if (Var.foodAmount != Inventory.getCount(Var.food.getId())) {
						if (Inventory.getCount(Var.food.getId()) < Var.foodAmount) {
							Banking.withdraw((Var.foodAmount - Inventory
									.getCount(Var.food.getId())), Var.food
									.getId());
						} else {
							Banking.deposit(
									(Inventory.getCount(Var.food.getId()) - Var.foodAmount),
									Var.food.getId());
						}
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);
					} else if (Var.prayerPotion != Inventory.getCount(Var.food
							.getId())) {
						if (Inventory.getCount(Potions.PRAYER_POTIONS) < Var.prayerPotion) {
							Banking.withdraw((Var.prayerPotion - Inventory
									.getCount(Potions.PRAYER_POTIONS)),
									Var.food.getId());
						} else {
							Banking.deposit(
									(Inventory.getCount(Potions.PRAYER_POTIONS) - Var.prayerPotion),
									Potions.PRAYER_POTIONS);
						}
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);
					}
				}
			}
		}
	}
}
