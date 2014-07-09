/*package scripts.Barrows.methods;

import java.util.HashMap;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.Barrows.types.Var;

public class LootFiltering {

	static HashMap<Integer, Integer> startList = new HashMap<Integer, Integer>();
	static HashMap<Integer, Integer> currentList = new HashMap<Integer, Integer>();
	static HashMap<Integer, String> nameList = new HashMap<Integer, String>();

	public static void addInventory(boolean start) {
		if (start) {
			for (RSItem r : Inventory.getAll()) {
				if (r != null && contains(r.getID())) {
					try {
						nameList.put(r.getID(), r.getDefinition().getName());
					} catch (Exception e) {
					}
					startList.put(r.getID(), getStack(r.getID()));
				}
			}
		} else {
			for (RSItem r : Inventory.getAll()) {
				if (r != null && contains(r.getID())) {
					try {
						nameList.put(r.getID(), r.getDefinition().getName());
					} catch (Exception e) {
					}
					currentList.put(r.getID(), getStack(r.getID()));
				}
			}
		}
	}

	static int getStack(int id) {
		int count = 0;
		for (RSItem r : Inventory.find(id)) {
			if (r != null) {
				count = count + r.getStack();
			}
		}
		return count;
	}

	static boolean contains(int id) {
		for (int i : Var.lootIDs) {
			if (i == id)
				return true;
		}
		return false;
	}

}*/
