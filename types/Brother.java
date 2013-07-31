package scripts.Barrows.types;

import scripts.Barrows.util.RSArea;
import scripts.Barrows.types.enums.Prayer;

public class Brother {
	 
	public enum Brothers {
		Dharok(0,"Dharok",false,false,Var.dharokDig,new int[] {},Prayer.Prayers.ProtectFromMelee, false),
		Karil(1,"Karil",false,false,Var.karilDig,new int[] {}, Prayer.Prayers.ProtectFromMissiles, false),
		Verac(2,"Verac",false,false,Var.veracDig,new int[] {}, Prayer.Prayers.ProtectFromMelee, false),
		Guthan(3,"Guthan",false,false,Var.guthanDig, new int[] {}, Prayer.Prayers.ProtectFromMelee, false),
		Torag(4,"Torag",false,false,Var.toragDig, new int[] {}, Prayer.Prayers.ProtectFromMelee, false),
		Ahrim(5,"Ahrim",false,false,Var.ahrimDig, new int[] {}, Prayer.Prayers.ProtectFromMagic, false);

	    public int killOrder;
	    private final String name;
	    public boolean killed;
	    public boolean isTunnel;
	    private final RSArea digArea;
	    public int[] equipmentIds;
		public Prayer.Prayers prayer;
		public boolean usePotions;

		Brothers(final int killOrder, String name, boolean killed,
				boolean isTunnel, final RSArea digArea,
				int[] equipmentIds, final Prayer.Prayers prayer,
				boolean usePotions) {
			this.killOrder = killOrder;
			this.name = name;
			this.killed = killed;
			this.isTunnel = isTunnel;
			this.digArea = digArea;
			this.equipmentIds = equipmentIds;
			this.prayer = prayer;
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
	}
}