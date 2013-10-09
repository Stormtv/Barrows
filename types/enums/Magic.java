package scripts.Barrows.types.enums;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSModel;

import scripts.Barrows.types.Brother.Brothers;

public class Magic {

	public enum Spell {
		NONE(-1, -1, -1, "None", -1, null),
		FIRE_BOLT(35, 20, 17, "Fire Bolt", 8, new int[][] {{554,4},{556,2},{562,1}}),
		WIND_BLAST(41, 24, 19,"Wind Blast", 9, new int[][] {{556,3},{560,1}}),
		WATER_BLAST(47, 27, 21, "Water Blast", 10, new int[][] {{555,3},{556,3},{560,1}}),
		EARTH_BLAST(53, 33, 23, "Earth Blast", 11, new int[][] {{557,4},{556,3},{560,1}}),
		FIRE_BLAST(59, 38, 25,"Fire Blast", 12, new int[][] {{554,5},{556,3},{560,1}}),
		WIND_WAVE(62, 45, 27, "Wind Wave", 13, new int[][] {{556,5},{565,1}}),
		WATER_WAVE(65, 48, 29, "Water Wave", 14, new int[][] {{553,7},{556,5},{565,1}}),
		EARTH_WAVE(70, 52, 31,"Earth Wave", 15, new int[][] {{557,7},{556,5},{565,1}}),
		FIRE_WAVE(75, 55, 33, "Fire Wave", 16, new int[][] {{554,7},{556,5},{565,1}});

		// TODO add slayer dart
		private final int req;
		private final int interfaceID;
		private final int setting;
		private final String name;
		private final int secondInterface;
		private final int[][] requiredRunes;
		private Spell(int req, int interfaceID, int setting, String name,
				int secondInterface, int[][] requiredRunes) {
			this.req = req;
			this.interfaceID = interfaceID;
			this.setting = setting;
			this.name = name;
			this.secondInterface = secondInterface;
			this.requiredRunes = requiredRunes;
		}

		int[][] getRequiredRunes() {
			return requiredRunes;
		}
		
		int getRequiredlevel() {
			return req;
		}

		int getInterfaceID() {
			return interfaceID;
		}

		int getSecondInterfaceID() {
			return secondInterface;
		}

		int getSettingID() {
			return setting;
		}

		String getName() {
			return name;
		}
	}

	public static boolean canCast(Spell s) {
		return Skills.getCurrentLevel(Skills.SKILLS.MAGIC) >= s
				.getRequiredlevel();
	}

	public static boolean isAutocasting(Spell s) {
		return Game.getSetting(108) == s.getSettingID();
	}

	public static boolean castOn(Spell s, RSModel m) {
		if (!canCast(s))
			return false;

		if (Game.getUptext().contains(s.getName())) {
			if (m != null && m.click("Cast " + s.getName())) {
				// TODO dynamic sleep
			}
		} else {
			if (GameTab.getOpen().equals(TABS.MAGIC)) {
				RSInterface spell = Interfaces.get(192, s.getInterfaceID());
				if (spell != null) {
					if (spell.click("Cast " + s.getName())) {
						// TODO dynamic sleep
					}
				}
			} else {
				GameTab.open(TABS.MAGIC);
			}
		}
		return false;
	}

	public static boolean autoCast(Spell s) {
		if (!canCast(s))
			return false;
		if (isAutocasting(s))
			return true;

		if (!GameTab.getOpen().equals(TABS.COMBAT)) {
			GameTab.open(TABS.COMBAT);
			General.sleep(500, 700);
		}

		if (Interfaces.get(90, 5) != null) {
			if (Interfaces.get(90, 5).click("Spell")) {
				General.sleep(700, 800);
			}
		}
		if (Interfaces.get(201, 2) != null) {
			if (Interfaces.get(201, 2).getChild(s.getSecondInterfaceID())
					.click(s.name)) {
				General.sleep(700, 800);
				GameTab.open(TABS.INVENTORY);
			}
		}

		return false;
	}

	public static boolean isUsingSpells() {
		for (Brothers b : Brothers.values()) {
			if (!b.getSpell().equals(Magic.Spell.NONE)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasCasts(int numOfCasts) {
		for (Brothers b : Brothers.values()) {
			if (!b.getSpell().equals(Magic.Spell.NONE)) {
				int[][] req = b.getSpell().getRequiredRunes();
				for (int i=0;i<req.length;i++) {
					if (Inventory.getCount(req[i][0]) 
							< numOfCasts*req[i][1]) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static void withdrawCasts(int numOfCasts) {
		for (Brothers b: Brothers.values()) {
			if (!b.getSpell().equals(Magic.Spell.NONE)) {
				if (!hasCasts(numOfCasts)) {
					int[][] req = b.getSpell().getRequiredRunes();
					for (int i=0;i<req.length;i++) {
						int need = numOfCasts*req[i][1]
								- Inventory.getCount(req[i][0]);
						if (need > 0) {
							Banking.withdraw(need, req[i][0]);
						}
					}
				}
			}
		}
	}
	
}
