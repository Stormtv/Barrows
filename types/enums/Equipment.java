package scripts.Barrows.types.enums;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

public class Equipment {

	public enum Gear {
		HELM(0), CAPE(1), NECK(2), WEAPON(3), BODY(4), SHIELD(5), LEGS(7), GLOVES(
				9), BOOTS(10), RING(12), ARROW(13);
		private final int value;

		private Gear(int value) {
			this.value = value;
		}
	}

	public static void equip(int... id) {
		if (GameTab.getOpen().equals(TABS.INVENTORY)) {
			if (Inventory.find(id).length > 0) {
				Inventory.find(id)[0].click("W");
				General.sleep(500);
			}
		} else {
			GameTab.open(TABS.INVENTORY);
		}
	}

	public static int getEquipmentID(Gear spot) {
		if (Interfaces.get(387, 28) != null) {
			for (RSItem i : Interfaces.get(387, 28).getItems()) {
				if (i.getIndex() == spot.value) {
					return i.getID();
				}
			}
		}
		return -1;
	}

	public static boolean isAllEquiped(int... id) {
		ArrayList<Integer> equip = new ArrayList<Integer>();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		if (Interfaces.get(387, 28) != null) {
			for (RSItem i : Interfaces.get(387, 28).getItems()) {
				equip.add(i.getID());
			}
		}
		for (int i : id) {
			ids.add(i);
		}
		if (equip.contains(ids))
			return true;
		return false;
	}

	public static boolean isEquiped(int id) {
		if (Interfaces.get(387, 28) != null) {
			for (RSItem i : Interfaces.get(387, 28).getItems()) {
				if (i.getID() == id) {
					return true;
				}
			}
		}
		return false;
	}
}
