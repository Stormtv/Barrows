package scripts.Barrows.types.enums;

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
	
	public static int[] getEquipedItems() {
		int[] items = new int [11];
		int count = 0;
		for (Gear i : Gear.values()) {
			items[count] = getEquipmentID(i);
			count++;
		}
		return items;
	}

	public static boolean isAllEquiped(int... id) {
		for (int i : id) {
			if (!isEquiped(i))
				return false;
		}
		return true;
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
