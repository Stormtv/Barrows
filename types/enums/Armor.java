package scripts.Barrows.types.enums;

import org.tribot.api2007.Inventory;

import scripts.Barrows.types.enums.Equipment;

public class Armor {
	public enum Degradables {
		GuthanHELMET(new int[] { 4724, 4904, 4905, 4906, 4907 }, Equipment.SLOTS.HELMET, 4908),
		GuthanLegs(new int[] { 4730, 4922, 4923, 4924, 4925 }, Equipment.SLOTS.LEGS, 4926),
		GuthanBody(new int[] { 4728, 4916, 4917, 4918, 4919 }, Equipment.SLOTS.BODY, 4920),
		GuthanWarspear(new int[] { 4726, 4910, 4911, 4912, 4913 }, Equipment.SLOTS.WEAPON, 4914),

		DharoksGreatAxe(new int[] { 4718, 4886, 4887, 4888, 4889 }, Equipment.SLOTS.WEAPON, 4890),
		DharoksBody(new int[] { 4720, 4892, 4893, 4894, 4895 }, Equipment.SLOTS.BODY, 4896),
		DharoksLegs(new int[] { 4722, 4898, 4899, 4900, 4901 }, Equipment.SLOTS.LEGS, 4902),
		DharoksHELMET(new int[] { 4716, 4880, 4881, 4882, 4883 }, Equipment.SLOTS.HELMET, 4884),

		VeracsHELMET(new int[] { 4753, 4976, 4977, 4978, 4979 }, Equipment.SLOTS.HELMET, 4980),
		VeracsBody(new int[] { 4757, 4988, 4989, 4990, 4991 }, Equipment.SLOTS.BODY, 4992),
		VeracsLegs(new int[] { 4759, 4994, 4995, 4996, 4997 }, Equipment.SLOTS.LEGS, 4998),
		VeracsFlail(new int[] { 4755, 4982, 4983, 4984, 4985 }, Equipment.SLOTS.WEAPON, 4986),

		KarilsBody(new int[] { 4736, 4940, 4941, 4942, 4943 }, Equipment.SLOTS.BODY, 4944),
		KarilsHELMET(new int[] { 4732, 4928, 4929, 4930, 4931 }, Equipment.SLOTS.HELMET, 4932),
		KarilsLegs(new int[] { 4738, 4946, 4948, 4949, 4950 }, Equipment.SLOTS.LEGS, 4951),
		KarilsCBow(new int[] { 4734, 4934, 4935, 4936, 4937 }, Equipment.SLOTS.WEAPON, 4938),

		ToragHELMET(new int[] { 4745, 4952, 4953, 4954, 4955 }, Equipment.SLOTS.HELMET, 4956),
		ToragBody(new int[] { 4749, 4964, 4965, 4966, 4967 }, Equipment.SLOTS.BODY, 4968),
		ToragLegs(new int[] { 4751, 4970, 4971, 4972, 4973 }, Equipment.SLOTS.LEGS, 4974),
		ToragHammers(new int[] { 4747, 4958, 4959, 4960, 4961 }, Equipment.SLOTS.WEAPON, 4962),

		AhrimHELMET(new int[] { 4708, 4856, 4857, 4858, 4859 }, Equipment.SLOTS.HELMET, 4860),
		AhrimBody(new int[] { 4712, 4868, 4869, 4870, 4871 }, Equipment.SLOTS.BODY, 4872),
		AhrimLeg(new int[] { 4714, 4874, 4875, 4876, 4877 }, Equipment.SLOTS.LEGS, 4878),
		AhrimStaff(new int[] { 4710, 4862, 4863, 4864, 4865 }, Equipment.SLOTS.WEAPON, 4866),

		AmuletOfGlory(new int[] { 1704, 1706, 1708, 1710, 1712 }, Equipment.SLOTS.AMULET, -10),
		TridentOfTheSeas(new int[] {11905, 11907, 11908}, Equipment.SLOTS.WEAPON, -10);
		private int[] id;
		private Equipment.SLOTS SLOTS;
		private int Degraded;

		private Degradables(int[] id, Equipment.SLOTS SLOTS, int Degraded) {
			this.id = id;
			this.SLOTS = SLOTS;
			this.Degraded = Degraded;
		}

		public int[] getId() {
			return id;
		}

		public Equipment.SLOTS getSLOTS() {
			return SLOTS;
		}

		public int getDegraded() {
			return Degraded;
		}
	}

	public static int getCurrentDegraded() {
		for (Degradables d : Degradables.values()) {
			if (Inventory.getCount(d.getDegraded()) > 0
					|| Equipment.isEquipped(d.getDegraded())) {
				return d.getDegraded();
			}
		}
		return 0;
	}
}
