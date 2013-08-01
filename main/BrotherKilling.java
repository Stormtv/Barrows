package scripts.Barrows.main;

import java.awt.event.KeyEvent;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;

import scripts.Barrows.methods.Pathing;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.Potions;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Equipment;
import scripts.Barrows.types.enums.Prayer;

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
		if (!b.digArea().contains(Player.getPosition())) {
			if (Pathing.isInBarrows()) {
				if (b.digArea().getTiles().length > 0) {
					Walking.blindWalkTo(Pathing.getRandomTile(b.digArea));
					General.sleep(350,500);
					while (Player.isMoving()) {
						General.sleep(30);
					}
				}
			}
		} else {
			if (!isReadyToFight(b)) {
				getReadyToFight(b);
			} else {
				dig(b);
			}
		}
	}

	public static void killBrotherInTunnel() {
		// TODO Auto-generated method stub

	}

	private void dig(Brothers b) {
		if (!GameTab.getOpen().equals(TABS.INVENTORY)) {
			Keyboard.pressKey((char) KeyEvent.VK_ESCAPE);
		}
		if (Inventory.find(Var.SPADE_ID).length > 0) {
			Inventory.find(Var.SPADE_ID)[0].click("");
			for (int fSafe = 0; fSafe < 20
					&& Player.getPosition().getPlane() != 3; fSafe++) {
				General.sleep(50);
			}
			if (Player.getPosition().getPlane() == 3) {
				if (!b.prayer.equals(Prayer.Prayers.None)) {
					Prayer.activate();
				}
			} else {
				dig(b);
			}
		}
	}

	private void getReadyToFight(Brothers b) {
		if (!b.getPrayer().equals(Prayer.Prayers.None)) {
			Potions.fillPrayer();
		}
		while (!Equipment.isAllEquiped(b.getEquipment())) {
			for (int i : b.getEquipment()) {
				if (!Equipment.isEquiped(i) && Inventory.getCount(i) > 0) {
					Equipment.equip(i);
				}
			}
		}
		if (b.usePotions()) {
			Potions.superPot();
		}
	}

	boolean isInCrypt(Brothers b) {
		return false;
	}

	boolean isReadyToFight(Brothers b) {
		return false;
	}

}
