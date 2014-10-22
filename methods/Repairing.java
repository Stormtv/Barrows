package scripts.Barrows.methods;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Armor;
import scripts.Barrows.types.enums.Equipment;

public class Repairing {
	static RSTile bobsTile = new RSTile(3230, 3203, 0);

	private static void homeTele() {
		if (!GameTab.getOpen().equals(GameTab.TABS.MAGIC)) {
			GameTab.open(GameTab.TABS.MAGIC);
		}
		RSInterface home = Interfaces.get(192,0);
		if (home != null && Player.getAnimation() == -1) {
			home.click("Cast");
			General.sleep(1000,1250);
			for (int i = 0; i < 20 && Player.getAnimation() == -1; i++) {
				General.sleep(20,50);
			}
		}
	}
	
	private static void walkToBobs() {
		WebWalking.walkTo(bobsTile);
	}
	
	private static void repairArmor() {
		for (Armor.Degradables i : Armor.Degradables.values()) {
			int[] ids = i.getId();
			if (Equipment.isEquipped(ids)) {
				Equipment.remove(ids);
				for (int b = 0 ; b < 20 && Equipment.isEquipped(ids); b++) {
					General.sleep(27,45);
				}
			}
		}
		for (Armor.Degradables i : Armor.Degradables.values()) {
			int ids = i.getDegraded();
			if (Equipment.isEquipped(ids)) {
				Equipment.remove(ids);
				for (int b = 0 ; b < 20 && Equipment.isEquipped(ids); b++) {
					General.sleep(27,45);
				}
			}
		}
		int broken = -1;
		for (RSItem i : Inventory.getAll()) {
			for (Armor.Degradables b : Armor.Degradables.values()) {
				int id = b.getDegraded();
				if (i.getID() == id) {
					broken = id;
					break;
				}
			}
			if (broken != -1) {
				RSItem[] brokenItem = Inventory.find(broken);
				if (brokenItem.length > 0) {
					brokenItem[0].click("Use");
				}
				RSNPC[] bob = NPCs.find("Bob");
				if (bob.length > 0) {
					if (bob[0].click("Bob")) {
						for (int c = 0; c < 20
								&& Interfaces.get(230, 2) == null
								&& Interfaces.get(228, 1) == null; c++) {
							General.sleep(250, 500);
						}
					}
				}
				break;
			}
		}
		RSInterface multi = Interfaces.get(230, 2);
		if (multi != null) {
			multi.click("Continue");
			for (int i = 0; i < 20 && Armor.getCurrentDegraded() > 0; i++) {
				General.sleep(20,30);
			}
		}
		RSInterface single = Interfaces.get(228, 1);
		if (single != null) {
			single.click("Continue");
			for (int i = 0; i < 20 && Armor.getCurrentDegraded() > 0; i++) {
				General.sleep(20,30);
			}
		}
		if (Armor.getCurrentDegraded() == 0) {
			Var.status = "Repaired Successfuly";
			General.println(Var.status);
			Var.forceBank = true;
		}
	}

	private static void bank() {
		if (!Banking.isBankScreenOpen()) {
			Var.status = "Opening Bank to get money";
			Banking.openBank();
		}
		for (int i = 0; i < 20 && !Banking.isPinScreenOpen() && !Banking.isBankScreenOpen(); i++) {
			General.sleep(30,50);
		}
		if (Banking.isPinScreenOpen()) {
			Banking.inPin();
		}
		if (Banking.isBankScreenOpen()) {
			Var.status = "Clearing Inventory Space";
			Banking.deposit(Inventory.find(Var.food.getId()).length,
					Var.food.getId());
			if (Inventory.getCount(995) < 330000) {
				RSItem[] Gold = Banking.find(995);
				if (Gold.length > 0 && Gold[0].getStack() < 330000) {
					if (Inventory.getCount(995) < 330000){
						System.out.println("Well fuck no gold to repair armor (script requires 330K for saftey reasons)");
						Var.running = false;
					}
				}
				if (Gold.length > 0) {
					Var.status = "Getting dat bling bling";
					Banking.withdraw(330000, 995);
					for (int i = 0; i < 20 && Inventory.getCount(995) < 330000; i++) {
						General.sleep(290,430);
					}
				}
			}
			if (Var.bankPath.equals(Pathing.PathBank.ECTOPHIAL)
					&& Inventory.getCount(Var.ECTOPHIAL) == 0) {
				Var.status = "Withdrawing Ectophial";
				final int count = Inventory.getAll().length;
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
				final int count = Inventory.getAll().length;
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
				final int count = Inventory.getAll().length;
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
				final int count = Inventory.getAll().length;
				Banking.withdraw(1, 8007);
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						return Inventory.getAll().length != count;
					}
				}, 3000);
			}
			if (Inventory.getCount(995) >= 330000 && Banking.isBankScreenOpen()) {
				Var.status = "got dat bling bling closing the bank";
				Banking.close();
				for (int i = 0; i < 20 && Banking.isBankScreenOpen(); i++) {
					General.sleep(30,50);
				}
			}
		}
	}

	public static void repair() {
		if (Inventory.getCount(995) < 330000 && Armor.getCurrentDegraded() > 0) {
			if (!Var.bankArea.contains(Player.getPosition())) {
				Pathing.goToBank();
			} else {
				bank();
			}
		} else if (Banking.isBankScreenOpen()) {
			Var.status = "got dat bling bling closing the bank";
			Banking.close();
			for (int i = 0; i < 20 && Banking.isBankScreenOpen(); i++) {
				General.sleep(30,50);
			}
		} else if (Armor.getCurrentDegraded() > 0 && !Banking.isBankScreenOpen()) {
			if (Var.bankArea.contains(Player.getPosition())
					&& new RSTile(Player.getPosition().getX(), Player
							.getPosition().getY()).distanceTo(new RSTile(3223,
							3218)) > 50 && Player.getAnimation() == -1) {
				homeTele();
			} else if (!isAtBobs()
					&& new RSTile(Player.getPosition().getX(), Player
							.getPosition().getY()).distanceTo(new RSTile(3223,
							3218)) < 50) {
				walkToBobs();
			} else if (isAtBobs()
					&& new RSTile(Player.getPosition().getX(), Player
							.getPosition().getY()).distanceTo(new RSTile(3223,
							3218)) < 50) {
				repairArmor();
			}
		} else if (Armor.getCurrentDegraded() == 0) {
			Var.forceBank = true;
		}
	}

	private static boolean isAtBobs() {
		return Player.getPosition().distanceTo(bobsTile) < 2;
	}
}
