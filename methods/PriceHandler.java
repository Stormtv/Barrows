package scripts.Barrows.methods;

import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.ZybezItem;

public class PriceHandler implements Runnable {

	public static void setPrices() {

		String[] names = { "Guthans Warspear", "Guthans Chainskirt",
				"Guthans Helm", "Guthans Platebody", "Ahrims hood",
				"Ahrims staff", "Ahrims robetop", "Ahrims robeskirt",
				"Dharoks helm", "Dharoks greataxe", "Dharoks platebody",
				"Dharoks platelegs", "Karils coif", "Karils crossbow",
				"Karils leathertop", "Karils leatherskirt", "Bolt rack",
				"Torags helm", "Torags hammers", "Torags platebody",
				"Torags platelegs", "Veracs helm", "Veracs flail",
				"Veracs brassard", "Veracs plateskirt", "Blood rune",
				"Death rune", "Chaos rune", "Mind rune", "Dragon med helm",
				"Tooth half of a key", "Loop half of a key" };

		int[] ids = { 4726, 4730, 4724, 4728, 4708, 4710, 4712, 4714, 4716,
				4718, 4720, 4722, 4732, 4734, 4736, 4738, 4740, 4745, 4747,
				4749, 4751, 4753, 4755, 4757, 4759, 565, 560, 562, 558, 1149,
				985, 987 };
		Var.lootIDs = ids;
		for (int i = 0; i < names.length; i++) {
			ZybezItem item = new ZybezItem(names[i]);
			if (item != null) {
				Var.priceTable.put(names[i], item);
				System.out.println("Name = " + names[i] + "  |  Price = "
						+ item.getAverage());
			}
		}
	}

	public static int getPrice(String name) {
		if (Var.priceTable.containsKey(name)) {
			return Var.priceTable.get(name).getAverage();
		} else {
			return 0;
		}
	}

	@Override
	public void run() {
		setPrices();
	}

}
