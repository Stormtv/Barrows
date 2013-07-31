package scripts.Barrows.types.enums;

import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSModel;

public class Magic {

	public enum Spell {
		FIRE_BLAST(59, 3, 25, "Fire Blast");

		private final int req;
		private final int interfaceID;
		private final int setting;
		private final String name;

		private Spell(int req, int interfaceID, int setting, String name) {
			this.req = req;
			this.interfaceID = interfaceID;
			this.setting = setting;
			this.name = name;
		}

		int getRequiredlevel() {
			return req;
		}

		int getInterfaceID() {
			return interfaceID;
		}

		int getSettingID() {
			return setting;
		}

		String getName() {
			return name;
		}

		boolean canCast() {
			return Skills.getCurrentLevel("Magic") >= getRequiredlevel();
		}

		boolean isAutocasting() {
			return Game.getSetting(108) == getSettingID();
		}

	}

	boolean castOn(Spell s, RSModel m) {
		if (!s.canCast())
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

	boolean autoCast(Spell s) {
		if (!s.canCast())
			return false;
		if (s.isAutocasting())
			return true;

		if (GameTab.getOpen().equals(TABS.COMBAT)) {
			if (Interfaces.get(319, s.getInterfaceID()) != null) {
				if (Equipment.isEquiped(4170)) {
					// TODO slayer staff for magic dart
				} else {
					if (Interfaces.get(319, s.getInterfaceID()).click("Ok")) {
						// TODO dynamic sleep
					}
				}
			} else {
				if (Interfaces.get(90, 5) != null) {
					if (Interfaces.get(90, 5).click("Spell")) {
						// TODO dynamic sleep
					}
				}
			}
		} else {
			GameTab.open(TABS.COMBAT);
		}
		return false;
	}
}
