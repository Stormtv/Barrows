package scripts.Barrows.types.enums;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSModel;

import scripts.Barrows.methods.BankHandler;
import scripts.Barrows.types.Brother.Brothers;

public class Magic {
	public enum Spell {
		NONE(-1, -1, -1, "None", -1, null),
		FIRE_BOLT(35, 20, 17, "Fire Bolt", 8, new int[][] { { 554, 4 },
				{ 556, 2 }, { 562, 1 } }),
		WIND_BLAST(41, 24, 19, "Wind Blast", 9, new int[][] { { 556, 3 },
				{ 560, 1 } }),
		WATER_BLAST(47, 27, 21, "Water Blast", 10, new int[][] { { 555, 3 },
				{ 556, 3 }, { 560, 1 } }),
		EARTH_BLAST(53, 33, 23, "Earth Blast", 11, new int[][] { { 557, 4 },
				{ 556, 3 }, { 560, 1 } }),
		FIRE_BLAST(59, 38, 25, "Fire Blast", 12, new int[][] { { 554, 5 },
				{ 556, 3 }, { 560, 1 } }),
		WIND_WAVE(62, 45, 27, "Wind Wave", 13, new int[][] { { 556, 5 },
				{ 565, 1 } }),
		WATER_WAVE(65, 48, 29, "Water Wave", 14, new int[][] { { 553, 7 },
				{ 556, 5 }, { 565, 1 } }),
		EARTH_WAVE(70, 52, 31, "Earth Wave", 15, new int[][] { { 557, 7 },
				{ 556, 5 }, { 565, 1 } }),
		FIRE_WAVE(75, 55, 33, "Fire Wave", 16, new int[][] { { 554, 7 },
				{ 556, 5 }, { 565, 1 } }),
		MAGIC_DART(50, 31, 37, "Magic Dart", 18, new int[][] { { 560, 1 },
				{ 558, 4 } }),
		SMOKE_RUSH(50, 8, 63, "Smoke Rush", 31, new int[][] { { 562, 2 },
				{ 560, 2 }, { 554, 1 }, { 556, 1 } }),
		SHADOW_RUSH(52, 12, 65, "Shadow Rush", 32, new int[][] { { 562, 2 },
				{ 560, 2 }, { 556, 1 }, { 566, 1 } }),
		BLOOD_RUSH(56, 4, 67, "Blood Rush", 33, new int[][] { { 562, 2 },
				{ 560, 2 }, { 565, 1 } }),
		ICE_RUSH(58, 0, 69, "Ice Rush", 34, new int[][] { { 562, 2 },
				{ 560, 2 }, { 555, 2 } }),
		SMOKE_BLITZ(74, 9, 79, "Smoke Blitz", 39, new int[][] { { 565, 2 },
				{ 560, 2 }, { 554, 2 }, { 556, 2 } }),
		SHADOW_BLITZ(76, 13, 81, "Shadow Blitz", 40, new int[][] { { 565, 2 },
				{ 560, 2 }, { 556, 2 }, { 566, 2 } }),
		BLOOD_BLITZ(80, 5, 83, "Blood Blitz", 41, new int[][] { { 565, 4 },
				{ 560, 2 } }),
		ICE_BLITZ(82, 1, 85, "Ice Blitz", 42, new int[][] { { 565, 2 },
				{ 560, 2 }, { 555, 3 } }),
		CLAWS_OF_GUTHIX(60, 42, 39, "Claws of Guthix", 19, new int[][] {
				{ 556, 4 }, { 565, 2 }, { 554, 1 } }),
		CHARGE(80, 58, 0, "Charge", 1, new int[][] { { 556, 3 }, { 565, 3 },
				{ 554, 3 } });

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
		return Game.getSetting(108) == s.getSettingID()
				&& Interfaces.get(201, 0) == null;
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
			Keyboard.pressFunctionKey(1);
			for (int fail = 0; fail < 20
					&& !GameTab.getOpen().equals(TABS.COMBAT); fail++) {
				General.sleep(15, 50);
			}
		}
		if (Interfaces.get(593, 16) != null) {
			if (Interfaces.get(593, 16).click("Choose spell")) {
				for (int fail = 0; fail < 20 && Interfaces.get(201, 0) == null; fail++) {
					General.sleep(50, 75);
				}
			}
		}
		if (Equipment.isEquiped(8841)) {
			if (Interfaces.get(201, 0) != null) {
				if (Interfaces.get(201, 0).getChild(getVoidMaceInterface(s))
						.click(s.name)) {
					for (int fail = 0; fail < 20
							&& Interfaces.get(201, 0) != null; fail++) {
						General.sleep(50, 75);
					}
					Keyboard.pressKey((char) KeyEvent.VK_ESCAPE);
					for (int fail = 0; fail < 20
							&& !GameTab.getOpen().equals(TABS.INVENTORY); fail++) {
						General.sleep(15, 50);
					}
					if (isAutocasting(s))
						return true;
				}
			}
		} else {
			if (Interfaces.get(201, 0) != null) {
				if (Interfaces.get(201, 0).getChild(s.getSecondInterfaceID())
						.click(s.name)) {
					for (int fail = 0; fail < 20
							&& Interfaces.get(201, 0) != null; fail++) {
						General.sleep(50, 75);
					}
					Keyboard.pressKey((char) KeyEvent.VK_ESCAPE);
					for (int fail = 0; fail < 20
							&& !GameTab.getOpen().equals(TABS.INVENTORY); fail++) {
						General.sleep(15, 50);
					}
					if (isAutocasting(s))
						return true;
				}
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

	private static ArrayList<Integer> getStaffRunes() {
		ArrayList<Integer> staffRunes = new ArrayList<Integer>();
		for (int i : BankHandler.getRequiredItems()) {
			for (Staves.Staff s : Staves.Staff.values()) {
				if (s.getId() == i) {
					for (int b : s.getRunes()) {
						if (!staffRunes.contains(b)) {
							staffRunes.add(b);
						}
					}
				}
			}
		}
		return staffRunes;
	}

	public static boolean hasCasts(int numOfCasts) {
		for (Brothers b : Brothers.values()) {
			if (!b.getSpell().equals(Magic.Spell.NONE)) {
				int[][] req = b.getSpell().getRequiredRunes();
				for (int i = 0; i < req.length; i++) {
					if (Inventory.getCount(req[i][0]) < numOfCasts * req[i][1]
							&& !getStaffRunes().contains(req[i][0])) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static void withdrawCasts(int numOfCasts) {
		for (Brothers b : Brothers.values()) {
			if (!b.getSpell().equals(Magic.Spell.NONE)) {
				if (!hasCasts(numOfCasts)) {
					int[][] req = b.getSpell().getRequiredRunes();
					for (int i = 0; i < req.length; i++) {
						int need = numOfCasts * req[i][1]
								- Inventory.getCount(req[i][0]);
						int count = Inventory.getAll().length;
						if (need > 0 && !getStaffRunes().contains(req[i][0])) {
							Banking.withdraw(need, req[i][0]);
							for (int fail = 0; fail < 20
									&& Inventory.getAll().length == count; fail++) {
								General.sleep(50, 100);
							}
						}
					}
				}
			}
		}
	}

	public static int[] getRuneIDs() {
		ArrayList<Integer> runeIds = new ArrayList<Integer>();
		for (Brothers b : Brothers.values()) {
			if (!b.getSpell().equals(Magic.Spell.NONE)) {
				int[][] req = b.getSpell().getRequiredRunes();
				for (int i = 0; i < req.length; i++) {
					runeIds.add(req[i][0]);
				}
			}
		}
		int[] intArray = new int[runeIds.size()];
		for (int i = 0; i < runeIds.size(); i++) {
			intArray[i] = runeIds.get(i).intValue();
		}
		return intArray;
	}

	public static int getVoidMaceInterface(Spell s) {
		switch (s) {
		case WIND_WAVE:
			return 13;
		case WATER_WAVE:
			return 14;
		case EARTH_WAVE:
			return 15;
		case FIRE_WAVE:
			return 16;
		case CLAWS_OF_GUTHIX:
			return 19;
		}
		return 0;
	}

}
