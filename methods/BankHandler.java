package scripts.Barrows.methods;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.methods.Pathing.PathBank;
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
		if (Var.bankPath.equals(PathBank.ECTOPHIAL)) {
			items.add(Var.ECTOPHIAL);
		} else if (Var.bankPath.equals(PathBank.HOUSE)) {
			items.add(8013);
		}
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
		Mouse.setSpeed(General.random(100, 130));
		if (Banking.isPinScreenOpen()) {
			Banking.inPin();
		} else {
			if (Banking.isBankScreenOpen()) {
				if (hasJunk()) {
					Banking.depositAllExcept(getRequiredItems());
				} else {
					final int count = Inventory.getAll().length;
					if (Inventory.getCount(Var.SPADE_ID) < 1) {
						Var.status = "Withdrawing Spade";
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
						if (!Magic.hasCasts(Var.spellCount)) {
							Var.status = "Withdrawing Runes";
							Magic.withdrawCasts(Var.spellCount);
							return;
						}
					}
					if (Var.bankPath.equals(Pathing.PathBank.ECTOPHIAL) 
							&& Inventory.getCount(Var.ECTOPHIAL)==0) {
						Var.status = "Withdrawing Ectophial";
						Banking.withdraw(1, Var.ECTOPHIAL);
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);
						return;
					}
					if (Var.bankPath.equals(Pathing.PathBank.HOUSE) 
							&& Inventory.getCount(8013)==0) {
						Var.status = "Withdrawing House Teleport";
						Banking.withdraw(1, 8013);
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);
						return;
					}
					if (Var.arrowId > 0
							&& (org.tribot.api2007.Equipment
									.getCount(Var.arrowId)+Inventory.getCount(Var.arrowId)) < Var.arrowCount) {
						Var.status = "Withdrawing Arrows";
						Banking.withdraw((Var.arrowCount - org.tribot.api2007.Equipment
								.getCount(Var.arrowId)), Var.arrowId);
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);
						Banking.close();
						for (int fail=0;fail<20 && Banking.isBankScreenOpen();fail++){
							General.sleep(50,75);
						}
						if (!Banking.isBankScreenOpen()) {
							Keyboard.pressKey((char) KeyEvent.VK_ESCAPE);
							for (int fail=0; fail<20 
									&& !GameTab.getOpen().equals(TABS.INVENTORY);fail++) {
								General.sleep(15,50);
							}
							while (Inventory.find(Var.arrowId).length > 0) {
								Equipment.equip(Var.arrowId);
							} 
						}
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
						Var.status = "Withdrawing Super Defence";
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
							Var.status = "Withdrawing Prayer Potions";
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
							Var.status = "Withdrawing Equipment";
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
						Var.status = "Withdrawing Food";
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
					Var.status = "Opening the bank";
					if (!banker[0].isOnScreen()) {
						Camera.turnToTile(banker[0]);
					}
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
		return Inventory.getCount(Var.food.getId()) == 0
				|| !Magic.hasCasts(1)
				|| Var.arrowId != -1
				&& org.tribot.api2007.Equipment.getCount(Var.arrowId) < 1
				|| Inventory.getCount(Potions.PRAYER_POTIONS) == 0;
	}
	
	public static boolean needsMoreSupplies() {
		return Inventory.getCount(Var.food.getId()) < Var.foodAmount
				|| !Magic.hasCasts(Var.spellCount)
				|| Var.arrowId != -1
				&& org.tribot.api2007.Equipment.getCount(Var.arrowId) < Var.arrowCount
				|| Inventory.getCount(Potions.PRAYER_POTIONS) < Var.prayerPotion;
	}

	boolean isNearBank() {
		return Player.getPosition().distanceTo(new RSTile(3512, 3480, 0)) < 15;
	}
}
