package scripts.Barrows.types.enums;

import scripts.Barrows.types.enums.Equipment;

public class Armor {
	public enum Degradables {
		GuthanHelm(new int[] { 4724, 4904, 4905, 4906, 4907 }, Equipment.Gear.HELM, 4908), 
		GuthanLegs(new int[] { 4730, 4922,4923, 4924, 4925 }, Equipment.Gear.LEGS, 4926), 
		GuthanBody(new int[] { 4728, 4916, 4917, 4918, 4919 }, Equipment.Gear.BODY, 4920),
	    GuthanWarspear(new int[] {4726,4910,4911,4912,4913}, Equipment.Gear.WEAPON, 4914),
	    DharoksGreatAxe(new int[]{4718,4886,4887,4888,4889}, Equipment.Gear.WEAPON, 4891);
		private int[] id;
		private Equipment.Gear gear;
		private int Degraded;

		private Degradables(int[] id, Equipment.Gear gear, int Degraded) {
			this.id = id;
			this.gear = gear;
			this.Degraded = Degraded;
		}

		public int[] getId() {
			return id;
		}

		public Equipment.Gear getGear() {
			return gear;
		}

		public int getDegraded() {
			return Degraded;
		}
	}
}
