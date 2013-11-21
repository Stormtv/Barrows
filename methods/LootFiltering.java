package scripts.Barrows.methods;

import java.util.HashMap;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.Barrows.gui.LootTable;

public class LootFiltering {

	static HashMap<Integer, Integer> startList = new HashMap<Integer, Integer>();
	static HashMap<Integer, Integer> currentList = new HashMap<Integer, Integer>();
	static HashMap<Integer, String> nameList = new HashMap<Integer, String>();

	public static void add(int id, int amm, boolean start, String name) {
		nameList.put(id, name);
		if (start) {
			if (startList.containsKey(id)) {
				startList.put(id, startList.get(id) + amm);
			} else {
				startList.put(id, amm);
			}
		} else {
			if (currentList.containsKey(id)) {
				currentList.put(id, currentList.get(id) + amm);
			} else {
				currentList.put(id, amm);
			}
		}
	}

	public static void addInventory(boolean start) {
		for (RSItem r : Inventory.getAll()) {
			if (r != null)
				add(r.getID(), r.getStack(), start, r.getDefinition().getName());
		}
	}

	public static void compare() {
		for (int i : currentList.keySet()) {
			int ammount = 0;
			if (startList.containsKey(i)) {
				ammount = currentList.get(i) - startList.get(i);
			} else {
				ammount = currentList.get(i);
			}
			if (ammount > 0) {
				LootTable.addReward(nameList.get(i), i, ammount,
						PriceHandler.getPrice(i));
				GeneralMethods.updateTracker(nameList.get(i), ammount);
			}
		}
	}

}
