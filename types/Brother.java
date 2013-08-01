package scripts.Barrows.types;

import scripts.Barrows.util.RSArea;
import scripts.Barrows.types.enums.Magic;
import scripts.Barrows.types.enums.Prayer;

public class Brother {
	 
	public enum Brothers {
		Dharok(0,"Dharok",false,false,Var.dharokDig,new int[] {},Prayer.Prayers.ProtectFromMelee, false, Magic.Spell.WIND_BLAST),
		Karil(1,"Karil",false,false,Var.karilDig,new int[] {}, Prayer.Prayers.ProtectFromMissiles, false, Magic.Spell.NONE),
		Verac(2,"Verac",false,false,Var.veracDig,new int[] {}, Prayer.Prayers.ProtectFromMelee, false, Magic.Spell.WIND_BLAST),
		Guthan(3,"Guthan",false,false,Var.guthanDig, new int[] {}, Prayer.Prayers.ProtectFromMelee, false, Magic.Spell.WIND_BLAST),
		Torag(4,"Torag",false,false,Var.toragDig, new int[] {}, Prayer.Prayers.ProtectFromMelee, false, Magic.Spell.WIND_BLAST),
		Ahrim(5,"Ahrim",false,false,Var.ahrimDig, new int[] {}, Prayer.Prayers.ProtectFromMagic, false, Magic.Spell.NONE);

	    public int killOrder;
	    public final String name;
	    public boolean killed;
	    public boolean isTunnel;
	    public final RSArea digArea;
	    public int[] equipmentIds;
		public Prayer.Prayers prayer;
		public boolean usePotions;
		public Magic.Spell spell;
		Brothers(final int killOrder, String name, boolean killed,
				boolean isTunnel, final RSArea digArea,
				int[] equipmentIds, final Prayer.Prayers prayer,
				boolean usePotions, Magic.Spell spell) {
			this.killOrder = killOrder;
			this.name = name;
			this.killed = killed;
			this.isTunnel = isTunnel;
			this.digArea = digArea;
			this.equipmentIds = equipmentIds;
			this.prayer = prayer;
			this.spell = spell;
		}

		public boolean isKilled() {
			return killed;
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

		public RSArea digArea() {
			return digArea;
		}
		
		public int[] getEquipment() {
			return equipmentIds;
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
	}
}