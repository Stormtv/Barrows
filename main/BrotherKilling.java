package scripts.Barrows.main;

import org.tribot.api.General;
import org.tribot.api.Inventory;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;

import scripts.Barrows.methods.Pathing;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.Var;

public class BrotherKilling {

	void StartFight() {
		Brothers bro = Var.curBrother;
		if (bro != null && !bro.isTunnel && !bro.killed) {
			if (isInCrypt(bro)) {
				kill(bro);
			} else {
				goToCrypt(bro);
			}
		}
	}

	private void kill(Brothers bro) {

	}

	private void goToCrypt(Brothers b) {
		if (b.digArea().contains(Player.getPosition())) {
			if (isReadyToFight(b)) {
				dig();
			} else {
				getReadyToFight(b);
			}
		} else {
			if (Pathing.isInBarrows()) {
				if (b.digArea().getTiles().length > 0) {
					Walking.blindWalkTo(Pathing.getRandomTile(b.digArea));
					General.sleep(500);
				}
			}
		}
	}

	public static void killBrotherInTunnel() {
		// TODO Auto-generated method stub

	}

	private void dig() {
		if (GameTab.getOpen().equals(TABS.INVENTORY)) {
			if (Inventory.find(Var.SPADE_ID).length > 0) {
				Inventory.find(Var.SPADE_ID)[0].click("");
				General.sleep(1000);// TODO Replace with dynamic sleep
			}
		} else {
			GameTab.open(TABS.INVENTORY);
		}
	}

	private void getReadyToFight(Brothers b) {

	}

	boolean isInCrypt(Brothers b) {
		return false;
	}

	boolean isReadyToFight(Brothers b) {
		return false;
	}

}
