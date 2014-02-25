package scripts.Barrows.types;

import org.tribot.api2007.Game;

import scripts.Barrows.util.RSArea;
import scripts.Barrows.methods.tunnel.Rooms;
import scripts.Barrows.types.enums.Magic;
import scripts.Barrows.types.enums.Prayer;

public class Brother {

	public enum Brothers {
		Dharok(0, "Dharok", false, false, Var.dharokDig, new int[][] {}, Prayer.Prayers.ProtectFromMelee, false, Magic.Spell.NONE, 20720, 20668, 1, 443, 354),
		Karil(1, "Karil", false, false, Var.karilDig, new int[][] {}, Prayer.Prayers.ProtectFromMissiles, false, Magic.Spell.NONE, 20771, 20670, 3, 443, 394),
		Verac(2, "Verac", false, false, Var.veracDig, new int[][] {}, Prayer.Prayers.ProtectFromMelee, false, Magic.Spell.NONE, 20772, 20672, 5, 443, 454),
		Guthan(3, "Guthan", false, false, Var.guthanDig, new int[][] {}, Prayer.Prayers.ProtectFromMelee, false, Magic.Spell.NONE, 20722, 20669, 2, 443, 414),
		Torag(4, "Torag", false, false, Var.toragDig, new int[][] {}, Prayer.Prayers.ProtectFromMelee, false, Magic.Spell.NONE, 20721, 20671, 4, 443, 434),
		Ahrim(5, "Ahrim", false, false, Var.ahrimDig, new int[][] {}, Prayer.Prayers.ProtectFromMagic, false, Magic.Spell.NONE, 20770, 20667, 0, 443, 374);

		private int killOrder;
		private final String name;
		private boolean killed;
		private boolean isTunnel;
		private final RSArea digArea;
		private int[][] equipmentIds;
		private Prayer.Prayers prayer;
		private boolean usePotions;
		private Magic.Spell spell;
		private int cryptID;
		private int stairID;
		private int killedShift;
		private int x;
		private int y;

		Brothers(final int killOrder, String name, boolean killed,
				boolean isTunnel, final RSArea digArea, int[][] equipmentIds,
				final Prayer.Prayers prayer, boolean usePotions,
				Magic.Spell spell, int cryptID, int stairID, 
				int killedShift, int x, int y) {
			this.killOrder = killOrder;
			this.name = name;
			this.killed = killed;
			this.isTunnel = isTunnel;
			this.equipmentIds = equipmentIds;
			this.digArea = digArea;
			this.prayer = prayer;
			this.spell = spell;
			this.cryptID = cryptID;
			this.stairID = stairID;
			this.killedShift = killedShift;
			this.x = x;
			this.y = y;
		}

		// Getters
		
		public int getKilledShift(){
			return killedShift;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public boolean isKilled() {
			return killed;
		}

		public int getStair() {
			return stairID;
		}

		public int killOrder() {
			return killOrder;
		}

		public String getName() {
			return name;
		}
		public boolean isReallyTunnel() {
			int bitmask = 0x1;
			int val = Game.getSetting(452);
			int shifted = val >> getKilledShift();
			return (shifted & bitmask) == 1;
		}

		public boolean isTunnel() {
			return isTunnel;
		}

		public RSArea getDigArea() {
			return digArea;
		}

		public Prayer.Prayers getPrayer() {
			return prayer;
		}

		public boolean usePotions() {
			return usePotions;
		}

		public Magic.Spell getSpell() {
			return spell;
		}

		public int getCryptID() {
			return cryptID;
		}

		// Setters

		public void setKillOrder(int index) {
			killOrder = index;
		}

		public void setSpell(Magic.Spell s) {
			spell = s;
		}

		public void setPrayer(Prayer.Prayers selectedPrayer) {
			prayer = selectedPrayer;
		}

		public void setKilled(boolean killed) {
			this.killed = killed;
		}

		public void setTunnel(boolean isTunnel) {
			this.isTunnel = isTunnel;
		}

		public void setUsePotions(boolean usePotions) {
			this.usePotions = usePotions;
		}

		public int[][] getEquipmentIds() {
			return equipmentIds;
		}

		public void setEquipmentIds(int[][] equipmentIds) {
			this.equipmentIds = equipmentIds;
		}

		public boolean isReallyKilled() {
			int bitmask = 0x1;
			int val = Game.getSetting(453);
			int shifted = val >> getKilledShift();
			return (shifted & bitmask) == 1;
		}

		public static void statusCheck() {
				for (Brothers b : Brothers.values())
					b.setKilled(b.isReallyKilled());
				for (Brothers b : Brothers.values())
					b.setTunnel(b.isReallyTunnel());
			Var.startingRoom = Rooms.getStartingRoom();
		}
		
	}

	public static Brothers getTunnelBrother() {
		for (Brothers b : Brothers.values()) {
			if (b.isTunnel()) {
				return b;
			}
		}
		return null;
	}

	public static boolean isPrayerBrotherAlive(Brothers target) {
		for (Brothers b : Brothers.values()) {
			if (target != null && target.equals(b)
					&& target.getPrayer().equals(Prayer.Prayers.None))
				continue;
			if (!b.isKilled() && !b.getPrayer().equals(Prayer.Prayers.None)) {
				return true;
			}
		}
		return false;
	}
}