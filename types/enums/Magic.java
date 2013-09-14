package scripts.Barrows.types.enums;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSModel;

public class Magic {

	public enum Spell {
		NONE(-1,-1,-1,"None",-1),
		WIND_BLAST(41, 24, 19, "Wind Blast", 8),
		WATER_BLAST(47, 27, 21,"Water Blast", 9),
		EARTH_BLAST(53, 33, 23, "Earth Blast", 10),
		FIRE_BLAST(59, 38, 25, "Fire Blast", 11),
		WIND_WAVE(62, 45, 27,"Wind Wave", 12),
		WATER_WAVE(65, 48, 29, "Water Wave", 13),
		EARTH_WAVE(70, 52, 31, "Earth Wave", 14),
		FIRE_WAVE(75, 55, 33, "Fire Wave", 15);

		// TODO add slayer dart
		private final int req;
		private final int interfaceID;
		private final int setting;
		private final String name;
		private final int secondInterface;

		private Spell(int req, int interfaceID, int setting, String name,
				int secondInterface) {
			this.req = req;
			this.interfaceID = interfaceID;
			this.setting = setting;
			this.name = name;
			this.secondInterface = secondInterface;
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
		return Skills.getCurrentLevel("Magic") >= s.getRequiredlevel();
	}

	public static boolean isAutocasting(Spell s) {
		return Game.getSetting(108) == s.getSettingID();
	}

	public static boolean castOn(Spell s, RSModel m) {
		if (!canCast(s))
			return false;

		if (!Game.getUptext().contains(s.getName())) {
			if (!GameTab.getOpen().equals(TABS.MAGIC)) {
				Keyboard.pressFunctionKey(6);
				for (int fsafe = 0; fsafe < 20 && !GameTab.getOpen().equals(TABS.MAGIC); fsafe++) {
					General.sleep(15);
				}
			}
			RSInterface spell = Interfaces.get(192, s.getInterfaceID());
			if (spell != null) {
				if (spell.click("Cast " + s.getName())) {
					for (int fsafe = 0; fsafe<25 && !Game.getUptext().contains(s.getName());fsafe++) {
						General.sleep(10);
					}
				}
			}
		} else {
			if (m != null && m.click("Cast " + s.getName())) {
				for (int fsafe = 0; fsafe<25 && Player.getAnimation() == -1; fsafe++) {
					General.sleep(40);
				}
				if (Player.getAnimation() != -1) {
					return true;
				}
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
			for (int fsafe = 0; fsafe < 20 && !GameTab.getOpen().equals(TABS.COMBAT); fsafe++) {
				General.sleep(15);
			}
		}
		if (Interfaces.get(90, 5) != null) {
			if (Interfaces.get(90, 5).click("Spell")) {
				for (int fsafe =0; fsafe<40 && Interfaces.get(319,s.getSecondInterfaceID()) != null; fsafe++) {
					General.sleep(50);
				}
			}
		}
		if (Interfaces.get(319, s.getSecondInterfaceID()) != null) {
			if (Equipment.isEquiped(4170)) {
				// TODO slayer staff for magic dart
			} else {
				if (Interfaces.get(319, s.getSecondInterfaceID()).click("Ok")) {
					for (int fsafe = 0; fsafe<40 && !isAutocasting(s);fsafe++) {
						General.sleep(25);
					}
					if (isAutocasting(s)) {
						return true;
					}
				}
			}
		} 
		return false;
	}
}
