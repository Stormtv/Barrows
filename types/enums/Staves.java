package scripts.Barrows.types.enums;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

public class Staves {
	public enum Staff {
		STAFF_OF_AIR(1381,new int[] {556}),
		STAFF_OF_WATER(1383,new int[] {555}),
		STAFF_OF_EARTH(1385,new int[] {557}),
		STAFF_OF_FIRE(1387,new int[] {554}),
		MYSTIC_FIRE_STAFF(1401,new int[] {554}),
		MYSTIC_WATER_STAFF(1403,new int[] {555}),
		MYSTIC_AIR_STAFF(1405,new int[] {556}),
		MYSTIC_EARTH_STAFF(1407,new int[] {557}),
		MYSTIC_LAVA_STAFF(3054,new int[] {554,557}),
		MYSTIC_MUD_STAFF(6563,new int[] {555,557}),
		MYSTIC_STEAM_STAFF(11738,new int[] {554,555}),
		FIRE_BATTLESTAFF(1393, new int[]{554}),
		WATER_BATTLESTAFF(1395, new int[] {555}),
		AIR_BATTLESTAFF(1397, new int[] {556}),
		EARTH_BATTLESTAFF(1399, new int[] {557}),
		LAVA_BATTLESTAFF(3053, new int[] {554,557}),
		MUD_BATTLESTAFF(6562, new int[] {555,557}),
		SMOKE_BATTLESTAFF(11998, new int[] {554, 556}),
		STEAM_BATTLESTAFF(11736, new int[] {554,555});
		
		private final int id;
		private final int[] runes;
		private Staff(int id, int[] runes){
			this.id = id;
			this.runes = runes;
		}
		
		public ArrayList<Integer> getRunes() {
			ArrayList<Integer> arRunes = new ArrayList<Integer>();
			for (int i:runes) {
				arRunes.add(i);
			}
			return arRunes;
		}
		
		public int getId() {
			return id;
		}
	}

	public static void rechargeTrident() {
		int[] chargedTrident = new int[] {11907,11905};
		if (Inventory.getCount(554) > 0 && Inventory.getCount(560) > 0
				&& Inventory.getCount(995) > 0 && Inventory.getCount(562) > 0
				&& !Banking.isBankScreenOpen()) {
			if (Equipment.isEquipped(11908)) {
				Equipment.remove(Equipment.SLOTS.WEAPON);
				for (int i = 0; i < 25 && Equipment.isEquipped(11908); i++) {
					General.sleep(30, 50);
				}
			}
			int shield = Equipment.getItem(Equipment.SLOTS.SHIELD).getID();
			if (shield != -1) {
				Equipment.remove(Equipment.SLOTS.SHIELD);
				for (int i = 0; i < 25 && Equipment.isEquipped(shield); i++) {
					General.sleep(30, 50);
				}
			}
			if (Inventory.getCount(11908) > 0) {
				General.println("Charging Staff");
				RSItem[] runes = Inventory.find(554);
				if (runes.length > 0) {
					runes[0].click("Use");
					for (int i = 0; i < 10 && !Game.getUptext().contains("->"); i++) {
						General.sleep(30, 50);
					}
					if (Game.getUptext().contains("->")) {
						RSItem[] staff = Inventory.find(11908);
						if (staff.length > 0) {
							staff[0].click("Use");
							for (int i = 0; i < 30
									&& Inventory.getCount(chargedTrident) == 0; i++) {
								General.sleep(30, 50);
							}
						}
					}
				}
			}
			if (Inventory.getCount(chargedTrident) > 0) {
				Equipment.equip(chargedTrident);
				for (int i = 0; i < 30 && !Equipment.isEquipped(chargedTrident);i++) {
					General.sleep(30,50);
				}
			}
			if (Inventory.getCount(shield) > 0) {
				Equipment.equip(shield);
				for (int i = 0; i < 30 && !Equipment.isEquipped(shield);i++) {
					General.sleep(30,50);
				}
			}
		}
	}
}
