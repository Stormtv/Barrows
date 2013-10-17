package scripts.Barrows.types;

import scripts.Barrows.util.RSArea;
import scripts.Barrows.types.enums.Magic;
import scripts.Barrows.types.enums.Prayer;

public class Brother {

	public enum Brothers {
		Dharok(0, "Dharok", false, false, Var.dharokDig, new int[] {},
				Prayer.Prayers.ProtectFromMelee, false, Magic.Spell.WIND_BLAST,
				20720, 20668, 443, 354), Karil(1, "Karil", false, false,
				Var.karilDig, new int[] {}, Prayer.Prayers.ProtectFromMissiles,
				false, Magic.Spell.NONE, 20771, 20670, 443, 394), Verac(2, "Verac",
				false, false, Var.veracDig, new int[] {},
				Prayer.Prayers.ProtectFromMelee, false, Magic.Spell.WIND_BLAST,
				20772, 20672, 443, 454), Guthan(3, "Guthan", false, false,
				Var.guthanDig, new int[] {}, Prayer.Prayers.ProtectFromMelee,
				false, Magic.Spell.WIND_BLAST, 20722, 20669, 443, 414), Torag(4,
				"Torag", false, false, Var.toragDig, new int[] {},
				Prayer.Prayers.ProtectFromMelee, false, Magic.Spell.WIND_BLAST,
				20721, 20671, 443, 434), Ahrim(5, "Ahrim", false, false,
				Var.ahrimDig, new int[] {}, Prayer.Prayers.ProtectFromMagic,
				false, Magic.Spell.NONE, 20770, 20667, 443, 374);

		private int killOrder;
		private final String name;
		private boolean killed;
		private boolean isTunnel;
		private final RSArea digArea;
		private int[] equipmentIds;
		private Prayer.Prayers prayer;
		private boolean usePotions;
		private Magic.Spell spell;
		private int cryptID;
		private int stairID;
		private int x;
		private int y;

		Brothers(final int killOrder, String name, boolean killed,
				boolean isTunnel, final RSArea digArea, int[] equipmentIds,
				final Prayer.Prayers prayer, boolean usePotions,
				Magic.Spell spell, int cryptID, int stairID, int x, int y) {
			this.killOrder = killOrder;
			this.name = name;
			this.setKilled(killed);
			this.setTunnel(isTunnel);
			this.digArea = digArea;
			this.setEquipmentIds(equipmentIds);
			this.prayer = prayer;
			this.spell = spell;
			this.cryptID = cryptID;
			this.stairID = stairID;
			this.x = x;
			this.y = y;
		}

		// Getters

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

		public boolean isTunnel() {
			return isTunnel;
		}

		public RSArea getDigArea() {
			return digArea;
		}

		public int[] getEquipment() {
			return getEquipmentIds();
		}

		public Prayer.Prayers getPrayer() {
			return prayer;
		}

		public boolean usePotions() {
			return isUsePotions();
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

		public void setEquipment(int[] equipIds) {
			setEquipmentIds(equipIds);
		}

		public void setKilled(boolean killed) {
			this.killed = killed;
		}

		public void setTunnel(boolean isTunnel) {
			this.isTunnel = isTunnel;
		}

		public boolean isUsePotions() {
			return usePotions;
		}

		public void setUsePotions(boolean usePotions) {
			this.usePotions = usePotions;
		}

		public int[] getEquipmentIds() {
			return equipmentIds;
		}

		public void setEquipmentIds(int[] equipmentIds) {
			this.equipmentIds = equipmentIds;
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
}