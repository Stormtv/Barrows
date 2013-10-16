package scripts.Barrows.types.enums;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;

import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.types.Var;

public class Food {
	public enum Edibles {		
		None(-1,-1),
		Shark(385,20),
		Monkfish(7946,16),
		Swordfish(373,14),
		Bass(365,13),
		Lobster(379,12),
		Tuna(361,10),
		Salmon(329,9),
		Trout(333,7);


		private int Id, healAmount;
		Edibles(final int Id, final int healAmount) {
			this.Id = Id;
			this.healAmount = healAmount;
		}
		
		public int getId() {
			return Id;
		}
		
		public int getHealAmount() {
			return healAmount;
		}
	}

	public static void eat() {
		if((Skills.getActualLevel(Skills.SKILLS.HITPOINTS) - Skills.getCurrentLevel(Skills.SKILLS.HITPOINTS)) >= Var.food.getHealAmount()) {
			if (!(GameTab.getOpen() == TABS.INVENTORY)) {
				Keyboard.pressKey((char) KeyEvent.VK_ESCAPE);
				for (int fsafe = 0; fsafe < 20 && !GameTab.getOpen().equals(TABS.INVENTORY); fsafe++) {
					General.sleep(15);
				}
			}
			if (Inventory.find(Var.food.getId()).length > 0) {
				final int t = Inventory.getAll().length;
				Inventory.find(Var.food.getId())[0].click("Eat"); 
				Timing.waitCondition(new Condition(){;
				@Override
				public boolean active()
				{
					return Inventory.getAll().length < t;
				}}, General.random(1200, 1300));
				if (Inventory.getAll().length == t) {
					eat();
				}
			}			
		}			
	}
	
	public static void eatInCombat() {
		if (Skills.getActualLevel(Skills.SKILLS.HITPOINTS) < 20 || GeneralMethods.getHPPercent() < 50) {
			if (Inventory.find(Var.food.getId()).length >0) {
				eat();
			}
		}
	}
	
	public static boolean canEatWithoutWaste() {
		return ((Skills.getActualLevel(Skills.SKILLS.HITPOINTS) - Skills.getCurrentLevel(Skills.SKILLS.HITPOINTS)) >= Var.food.getHealAmount());
	}

	public static int getFoodAmount() {
		int total = 0;
		for (RSItem i : Inventory.getAll()) {
			if (i.getDefinition() != null) {
				for (String s : i.getDefinition().getActions()) {
					if (s.contains("Eat")) {
						total+=1;
					}
				}
			}
		}
		return total;
	}
	
	public static int[] getFoodIDs() {
		ArrayList<Integer> foodIDs = new ArrayList<Integer>();
		for (RSItem i : Inventory.getAll()) {
			if (i.getDefinition() != null) {
				for (String s : i.getDefinition().getActions()) {
					if (s.contains("Eat")) {
						if (!foodIDs.contains(i.getID())) {
							foodIDs.add(i.getID());
						}
					}
				}
			} 
		}
		return convertIntegers(foodIDs);
	}
	
	private static int[] convertIntegers(ArrayList<Integer> integers) {
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++) {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}
}
