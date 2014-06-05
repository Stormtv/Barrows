package scripts.Barrows.methods;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.types.Var;

public class Repairing {
	static RSTile[] lumbyPathToBob = new RSTile[] {};

	private static void homeTele() {
		//TODO
	}
	
	private static void walkToBobs() {
		//TODO
	}
	
	private static void repairArmor() {
		//TODO
	}

	private static boolean isInArea(int dist, RSTile... Path) {
		for (RSTile t : Path) {
			if (t.distanceTo(Player.getPosition()) < dist) {
				return true;
			}
		}
		return false;
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
			if (Inventory.getCount(995) < 1000000) {
				if (Banking.find(995).length != 0) {
					Var.status = "Getting dat bling bling";
					Banking.withdraw(1000000, 995);
				} else {
					System.out.println("Well fuck no gold to repair armor (script requires 1M for saftey reasons)");
					Var.running = false;
				}
			}
			if (Inventory.getCount(995) > 1000000) {
				Var.status = "got dat bling bling closing the bank";
				Banking.close();
				for (int i = 0; i < 20 && Banking.isBankScreenOpen(); i++) {
					General.sleep(30,50);
				}
			}
		}
	}

	public static void repair() {
		if (Inventory.getCount(995) < 1000000) {
			if (!Var.bankArea.contains(Player.getPosition())) {
				Pathing.goToBank();
			} else {
				bank();
			}
		} else {
			if (!Var.bankArea.contains(Player.getPosition())) {
				homeTele();
			}
			if (!isAtBobs() && isInArea(15, lumbyPathToBob)) {
				walkToBobs();
			}
			if (isAtBobs()) {
				repairArmor();
			}
		}
	}

	private static boolean isAtBobs() {
		// TODO Auto-generated method stub
		return false;
	}
}
