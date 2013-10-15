package scripts.Barrows.types.enums;

import java.util.ArrayList;

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
}
