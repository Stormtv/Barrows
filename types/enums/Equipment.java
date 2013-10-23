package scripts.Barrows.types.enums;

import java.util.Arrays;

import org.tribot.api.General;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.Brother.Brothers;

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
	
	public static int[][] getEquipedItems() {
		try {
		int[][] items = new int [11][];
			int count = 0;
			for (Gear i : Gear.values()) {
				int itemID = getEquipmentID(i);
				boolean isBarrows = false;
				for (Armor.Degradables b : Armor.Degradables.values()) {
					for (int a : b.getId()) {
						if (a == itemID) {
							items[count] = new int[b.getId().length];
							items[count] = b.getId();
							isBarrows = true;
						}
					}
				}
				if (!isBarrows) {
					items[count] = new int[1]; 
					items[count][0]= getEquipmentID(i);
				}
				count++;
			}
			return items;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isAllEquiped(int... id) {
		for (int i : id) {
			if (i != -1 && !isEquiped(i))
				return false;
		}
		return true;
	}
	
	public static boolean isAllEquiped(int[][] ids) {
		for (int[] i : ids) {
			boolean emptySpot = false;
			for (int a : i) {
				if (a == -1) {
					emptySpot = true;
				}
			}
			if (!emptySpot && !isEquiped(i)) {
				return false;
			}
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
	
	public static boolean isEquiped(int[] id) {
		if (Interfaces.get(387, 28) != null) {
			for (RSItem i : Interfaces.get(387, 28).getItems()) {
				for (int a : id) {
					if (i.getID() == a) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void equipMostFullSet() {
		int numberOfEmptySpots=11;
		int[][] bestEquip = Brother.Brothers.Dharok.getEquipmentIds();
		for (Brother.Brothers b : Brothers.values()) {
			int emptySpots=0;
			for (int[] i : b.getEquipmentIds()) {
				for (int t : i) {
					if (t == -1) {
						emptySpots++;
						break;
					}
				}
			}
			if (numberOfEmptySpots > emptySpots) {
				numberOfEmptySpots = emptySpots;
				bestEquip = b.getEquipmentIds();
			}
		}
		while (!isAllEquiped(bestEquip)) {
			for (int[] i : bestEquip) {
				if (!Equipment.isAllEquiped(i) && Inventory.getCount(i) > 0) {
					Equipment.equip(i);
				} else if (!Equipment.isEquiped(i) && Inventory.getCount(i) == 0) {
					Var.status = "Unable to find Equipment ("+i+")";
				}
			}
			for (int fsafe = 0; fsafe<20 && !Equipment.isAllEquiped(bestEquip); fsafe++) {
				General.sleep(50);
			}
			if (!Equipment.isAllEquiped(bestEquip) && GeneralMethods.lastMessage().equalsIgnoreCase("you don't have enough free inventory space to do that.")) {
				if (Inventory.getCount(Var.food.getId()) > 0 && Inventory.isFull()) {
					Food.eat();
				}
			}
		}
	}
	
	
	public static int[][] requiredEquipment() {
		int[][] equip = new int[66][];
		int count = 0;
		for (Brother.Brothers b : Brother.Brothers.values()) {
			for (int[] i : b.getEquipmentIds()) {
				equip[count] = i;
				count++;
			}
		}
		return equip;
	}
}
