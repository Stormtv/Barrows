package scripts.Barrows.methods;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.types.Potions;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Equipment;
import scripts.Barrows.types.enums.Magic;

public class BankHandler {

	public static int[] getRequiredItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();

		for (int i : Equipment.requiedEquipment()) {
			items.add(i);
		}
		items.add(Var.food.getId());
		items.add(Var.SPADE_ID);
		items.add(Var.arrowId);
		for (int i : Potions.PRAYER_POTIONS)
			items.add(i);
		for (int i : Potions.SUPER_POTS)
			items.add(i);
		for (int i : Magic.getRuneIDs())
			items.add(i);
		int[] intArray = new int[items.size()];
		for (int i = 0; i < items.size(); i++) {
			intArray[i] = items.get(i).intValue();
		}

		return intArray;
	}

	public static boolean hasJunk() {
		ArrayList<Integer> items = new ArrayList<Integer>();
		for (int i : getRequiredItems())
			items.add(i);
		for (RSItem r : Inventory.getAll()) {
			if (!items.contains(r.getID()))
				return true;
		}
		return false;
	}

	public static void bank() {
		if (Banking.isPinScreenOpen()) {
			Banking.inPin();
		} else {
			if (Banking.isBankScreenOpen()) {
				if (hasJunk()) {
					Banking.depositAllExcept(getRequiredItems());
				} else {
					final int count = Inventory.getAll().length;
					if (Inventory.getCount(Var.SPADE_ID) < 1) {
						Banking.withdraw(1, Var.SPADE_ID);
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);
						return;
					}
					if (Magic.isUsingSpells()) {
						if (!Magic.hasCasts(200)) {
							Magic.withdrawCasts(200);
							return;
						}
					}
					if (Var.arrowId > 0
							&& org.tribot.api2007.Equipment
									.getCount(Var.arrowId) < 100) {
						Banking.withdraw((100 - org.tribot.api2007.Equipment
								.getCount(Var.arrowId)), Var.arrowId);
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);
						return;
					}
					if (Var.superAttack > 0
							&& Inventory.getCount(Potions.SUPER_ATTACK) < Var.superAttack) {
						Banking.withdraw(
								Var.superAttack
										- Inventory
												.getCount(Potions.SUPER_ATTACK),
								Potions.SUPER_ATTACK);
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);
						return;
					}
					if (Var.superStrength > 0
							&& Inventory.getCount(Potions.SUPER_STRENGTH) < Var.superStrength) {
						Banking.withdraw(
								Var.superStrength
										- Inventory
												.getCount(Potions.SUPER_STRENGTH),
								Potions.SUPER_STRENGTH);
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);
						return;
					}
					if (Var.superDefence > 0
							&& Inventory.getCount(Potions.SUPER_DEFENCE) < Var.superDefence) {
						Banking.withdraw(
								Var.superDefence
										- Inventory
												.getCount(Potions.SUPER_DEFENCE),
								Potions.SUPER_DEFENCE);
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);
						return;
					}
					if (Var.prayerPotion != Inventory
							.getCount(Potions.PRAYER_POTIONS)) {
						if (Inventory.getCount(Potions.PRAYER_POTIONS) < Var.prayerPotion) {
							Banking.withdraw((Var.prayerPotion - Inventory
									.getCount(Potions.PRAYER_POTIONS)),
									Potions.PRAYER_POTIONS);
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
						return;
					}
					for (int i : Equipment.requiedEquipment()) {
						if (!has(i) && i > 0) {
							Banking.withdraw(1, i);
							Timing.waitCondition(new Condition() {

								@Override
								public boolean active() {
									return Inventory.getAll().length != count;
								}
							}, 3000);
							System.out.println("lil " + i);
							return;
						}
					}
					if (getSpaceLeft() > 0) {
						Banking.withdraw(0, Var.food.getId());
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);
						return;
					}
				}
			} else {
				RSNPC[] banker = NPCs.findNearest("Banker");
				if (banker.length > 0) {
					GeneralMethods.click(banker[0], "Bank");
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return Banking.isBankScreenOpen();
						}
					}, 3000);
				}
			}
		}
	}

	static boolean has(int l) {
		return Inventory.getCount(l) > 0
				|| org.tribot.api2007.Equipment.getCount(l) > 0;
	}

	static int getSpaceLeft() {
		return (28 - Inventory.getAll().length);
	}

	public static boolean needToBank() {
		return Inventory.getCount(Var.food.getId()) < Var.foodAmount
				|| !Magic.hasCasts(200)
				|| Var.arrowId != 0
				&& org.tribot.api2007.Equipment.getCount(Var.arrowId) < 100
				|| Inventory.getCount(Potions.PRAYER_POTIONS[0]) < Var.prayerPotion;
	}

	boolean isNearBank() {
		return Player.getPosition().distanceTo(new RSTile(3512, 3480, 0)) < 15;
	}
}
