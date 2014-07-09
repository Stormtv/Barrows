package scripts.Barrows.methods;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

import scripts.Barrows.methods.Pathing.PathBank;
import scripts.Barrows.methods.Pathing.PathBarrows;
import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.Potions;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Equipment;
import scripts.Barrows.types.enums.Magic;
import scripts.Barrows.types.enums.Prayer;
import scripts.Barrows.types.enums.Staves;

public class BankHandler {

	public static int[] getRequiredItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();

		for (int[] i : Equipment.requiredEquipment()) {
			for (int j : i) {
				if (!items.contains(j)) {
					items.add(j);
				}
			}
		}
		for (int[] i : Var.tunnelEquipment) {
			for (int j : i) {
				if (!items.contains(j)) {
					items.add(j);
				}
			}
		}
		items.add(Var.food.getId());
		items.add(Var.SPADE_ID);
		items.add(Var.arrowId);
		if (Var.bankPath.equals(PathBank.ECTOPHIAL)) {
			items.add(Var.ECTOPHIAL);
		} else if (Var.bankPath.equals(PathBank.HOUSE)) {
			items.add(8013);
		} else if (Var.bankPath.equals(PathBank.VARROCK)) {
			items.add(8007);
		} else if (Var.barrowsPath.equals(PathBarrows.FAIRY_RINGS)) {
			items.add(772);
		}
		for (int i : Potions.PRAYER_POTIONS_3DOSE)
			items.add(i);
		for (int i : Potions.SUPER_POTS_3DOSE)
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
		if (!Banking.isPinScreenOpen() && !Banking.isBankScreenOpen()) {
			Equipment.equipMostFullSet();
		}
		if (!Banking.isBankScreenOpen()) {
			openBank();
		}
		if (Banking.isPinScreenOpen()) {
			Banking.inPin();
		}
		if (Banking.isBankScreenOpen()) {
			if (Skills.getActualLevel(Skills.SKILLS.HITPOINTS) > Skills
					.getCurrentLevel(Skills.SKILLS.HITPOINTS)
					+ Var.food.getHealAmount()) {
				int withdrawAmount = (int) Math.ceil((Skills
						.getActualLevel(Skills.SKILLS.HITPOINTS) - Skills
						.getCurrentLevel(Skills.SKILLS.HITPOINTS))
						/ Var.food.getHealAmount());
				Var.status = "Eating at bank";
				Banking.withdraw(withdrawAmount, Var.food.getId());
				Banking.close();
				eatAtBank(withdrawAmount);
				if (!Banking.isBankScreenOpen()) {
					openBank();
				}
			}
			if (Inventory.getCount(11908) > 0 || Equipment.isEquipped(11908)) {
				Var.status = "Recharging the Trident";
				Banking.deposit(Inventory.find(Var.food.getId()).length,
						Var.food.getId());
				if (Inventory.getCount(554) == 0) {
					if (Banking.find(554).length != 0) {
						Banking.withdraw(12500, 554);
					} else {
						Var.running = false;
					}
				}
				if (Inventory.getCount(560) == 0) {
					if (Banking.find(560).length != 0) {
					Banking.withdraw(2500, 560);
					} else {
						Var.running = false;
					}
				}
				if (Inventory.getCount(562) == 0) {
					if (Banking.find(562).length != 0) {
						Banking.withdraw(2500, 562);
					} else {
						Var.running = false;
					}
				}
				if (Inventory.getCount(995) == 0) {
					if (Banking.find(995).length != 0) {
						Banking.withdraw(25000, 995);
					} else {
						Var.running = false;
					}
				}
				Banking.close();
				Staves.rechargeTrident();
				if (!Banking.isBankScreenOpen()) {
					openBank();
				}
			}
			if (hasJunk()) {
				Banking.depositAllExcept(getRequiredItems());
			} else {
				if (GeneralMethods.needsToLogout()) {
					while (Banking.isBankScreenOpen())
						Banking.close();
					while (!Login.getLoginState().equals(
							Login.STATE.LOGINSCREEN))
						Login.logout();
					Var.running = false;
					return;
				}
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
						&& Inventory.getCount(Var.ECTOPHIAL) == 0) {
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
						&& Inventory.getCount(8013) == 0) {
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
				if (Var.bankPath.equals(Pathing.PathBarrows.FAIRY_RINGS)
						&& Inventory.getCount(772) == 0) {
					Var.status = "Withdrawing Dramen Staff";
					Banking.withdraw(1, 772);
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							return Inventory.getAll().length != count;
						}
					}, 3000);
					return;
				}
				if (Var.bankPath.equals(Pathing.PathBank.VARROCK)
						&& Inventory.getCount(8007) == 0) {
					Var.status = "Withdrawing Varrock Teletab";
					Banking.withdraw(1, 8007);
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							return Inventory.getAll().length != count;
						}
					}, 3000);
				}
				if (Var.arrowId > 0
						&& (Equipment.getCount(Var.arrowId) + Inventory
								.getCount(Var.arrowId)) < Var.arrowCount) {
					Var.status = "Withdrawing Arrows " + Var.arrowId;
					Banking.withdraw(
							(Var.arrowCount - Equipment.getCount(Var.arrowId)), Var.arrowId);
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							return Inventory.getAll().length != count;
						}
					}, 3000);
					while (Inventory.getCount(Var.arrowId) > 0
							&& Banking.isBankScreenOpen()) {
						Banking.close();
						for (int fail = 0; fail < 20
								&& Banking.isBankScreenOpen(); fail++) {
							General.sleep(50, 75);
						}
					}
					if (!Banking.isBankScreenOpen()) {
						GameTab.open(GameTab.TABS.INVENTORY);
						for (int fail = 0; fail < 20
								&& !GameTab.getOpen().equals(TABS.INVENTORY); fail++) {
							General.sleep(15, 50);
						}
						while (Inventory.find(Var.arrowId).length > 0) {
							Equipment.equip(Var.arrowId);
						}
					}
					return;
				}
				if (Inventory.getCount(Var.arrowId) > 0) {
					while (Inventory.getCount(Var.arrowId) > 0
							&& Banking.isBankScreenOpen()) {
						Banking.close();
						for (int fail = 0; fail < 20
								&& Banking.isBankScreenOpen(); fail++) {
							General.sleep(50, 75);
						}
					}
					if (!Banking.isBankScreenOpen()) {
						GameTab.open(GameTab.TABS.INVENTORY);
						for (int fail = 0; fail < 20
								&& !GameTab.getOpen().equals(TABS.INVENTORY); fail++) {
							General.sleep(15, 50);
						}
						while (Inventory.find(Var.arrowId).length > 0) {
							Equipment.equip(Var.arrowId);
						}
					}
					return;
				}
				if (Var.superAttack > 0
						&& Inventory.getCount(Potions.SUPER_ATTACK) != Var.superAttack) {
					if (Inventory.getCount(Potions.SUPER_ATTACK) < Var.superAttack) {
						Var.status = "Withdrawing Super Attack";
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
					} else {
						Var.status = "Depositing Super Attack";
						Banking.deposit(
								Inventory.getCount(Potions.SUPER_ATTACK)
										- Var.superAttack, Potions.SUPER_ATTACK);
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);

					}
					return;
				}
				if (Var.superStrength > 0
						&& Inventory.getCount(Potions.SUPER_STRENGTH) != Var.superStrength) {
					if (Inventory.getCount(Potions.SUPER_STRENGTH) < Var.superStrength) {
						Var.status = "Withdrawing Super Strength";
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
					} else {
						Var.status = "Depositing Super Strength";
						Banking.deposit(
								Inventory.getCount(Potions.SUPER_STRENGTH)
										- Var.superStrength,
								Potions.SUPER_STRENGTH);
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);
					}
					return;
				}
				if (Var.superDefence > 0
						&& Inventory.getCount(Potions.SUPER_DEFENCE) != Var.superDefence) {
					if (Inventory.getCount(Potions.SUPER_DEFENCE) < Var.superDefence) {
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
					} else {
						Var.status = "Depositing Super Defence";
						Banking.deposit(
								Inventory.getCount(Potions.SUPER_DEFENCE)
										- Var.superDefence,
								Potions.SUPER_DEFENCE);
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return Inventory.getAll().length != count;
							}
						}, 3000);

					}
					return;
				}
				if (Var.prayerPotion != Inventory
						.getCount(Potions.PRAYER_POTIONS)) {
					if (Inventory.getCount(Potions.PRAYER_POTIONS) < Var.prayerPotion) {
						Var.status = "Withdrawing Prayer Potions";
						Banking.withdraw(
								Var.prayerPotion
										- Inventory
												.getCount(Potions.PRAYER_POTIONS),
								Potions.PRAYER_POTIONS);
					} else {
						Banking.deposit(
								Inventory.getCount(Potions.PRAYER_POTIONS)
										- Var.prayerPotion,
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
				for (int[] i : Equipment.requiredEquipment()) {
					if (!has(i) && i[0] != -1) {
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
					} else if (i.length > 1
							&& Inventory.getCount(i)
									+ Equipment.getCount(i) > 1) {
						if (Inventory.getCount(i) > 0) {
							Banking.deposit(1, i);
						}
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
		}
	}

	private static void openBank() {
		RSObject[] banker = Objects.findNearest(15, "Bank booth");
		if (banker.length > 0 && !Banking.isBankScreenOpen()
				&& !Banking.isPinScreenOpen()) {
			Var.status = "Opening the bank";
			GeneralMethods.clickObject(banker[0], "Bank", true, true);
			for (int fail = 0; fail < 20 && !Banking.isBankScreenOpen()
					&& !Banking.isPinScreenOpen(); fail++) {
				General.sleep(20, 40);
			}
		}
	}

	private static void eatAtBank(int withdrawAmount) {
		General.sleep(90, 120);
		if (GameTab.getOpen() != TABS.INVENTORY) {
			GameTab.open(GameTab.TABS.INVENTORY);
		}
		General.sleep(30, 90);
		RSItem[] d = Inventory.find(Var.food.getId());
		for (int i = 0; i < withdrawAmount && i < d.length; i++) {
			final int prev = Inventory.getAll().length;
			d[i].click("Eat");
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					return Inventory.getAll().length < prev;
				}

			}, General.random(1700, 1900));
			for (int fsafe = 0; fsafe < 5 && Player.getAnimation() != -1; fsafe++) {
				General.sleep(75, 100);
			}
			General.sleep(150, 190);
		}
	}

	static boolean has(int... l) {
		return Inventory.getCount(l) > 0
				|| Equipment.isEquipped(l);
	}

	static int getSpaceLeft() {
		return (28 - Inventory.getAll().length);
	}
	
	public static boolean needToBank() {
		return needToBank(null);
	}

	public static boolean needToBank(Brothers b) {
		int count = 0;
		for (int i = 0; i < 5; i++) {
			if (Inventory.getCount(Var.food.getId()) == 0
					&& (Skills.getActualLevel(Skills.SKILLS.HITPOINTS) < 35 || GeneralMethods
							.getHPPercent() < 50)
					|| !Magic.hasCasts(1)
					|| Inventory.getCount(Var.SPADE_ID) == 0
					|| (Var.arrowId != -1
							&& Equipment.getCount(Equipment.SLOTS.ARROW) < 1 && Inventory
							.getCount(Var.arrowId) < 1)
					|| Inventory.getCount(11908) > 0
					|| Equipment.isEquipped(11908)
					|| (Inventory.getCount(Potions.PRAYER_POTIONS) == 0
							&& Var.prayerPotion > 0
							&& Prayer.getPoints() - Potions.prayerDrain() < 5 && Brother
								.isPrayerBrotherAlive(b))) {
				count++;
			}
		}
		if (count == 5 && Inventory.getAll().length != 0) {
			General.println("needToBank Force Bank");
			Var.forceBank = true;
		}
		return count == 5;
	}

	public static boolean needsMoreSupplies() {
		return Inventory.getCount(Var.food.getId()) < Var.foodAmount
				|| !Magic.hasCasts(Var.spellCount)
				|| Inventory.getCount(Var.SPADE_ID) == 0
				|| Inventory.getCount(11908) > 0
				|| Equipment.isEquipped(11908)
				|| Var.arrowId != -1
				&& Equipment.getCount(Equipment.SLOTS.ARROW) < Var.arrowCount
				|| Inventory.getCount(Potions.PRAYER_POTIONS) < Var.prayerPotion;
	}
}
