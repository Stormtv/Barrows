package scripts.Barrows.types.enums;

import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;

import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.types.Brother.Brothers;

public class Prayer {

	public static void flicker(Brothers b) {
		if (!b.getPrayer().isActivated()) {
			int xp = Skills.getXP("Hitpoints");
			Prayer.activate(b);
			for (int fsafe = 0; Skills.getXP("Hitpoints") == xp
					&& !(Player.getRSPlayer().getInteractingCharacter() == null)
					&& fsafe < 41; fsafe++) {
				General.sleep(15);
				if (fsafe == 40) {
					System.out.println("Must of hit a 0 turnning piety off");
				}
			}
			if (b.getPrayer().isActivated()) {
				Prayer.disable(b);
			}
		} else {
			Prayer.disable(b);
		}
	}

	public enum Prayers {
		None(-1, -1, "", -1), Piety(55, 54, "Piety", 70), Chivalry(53, 52,
				"Chivalry", 60), ProtectFromMelee(41, 97, "Protect from Melee",
				43), ProtectFromMissiles(39, 96, "Protect from Missiles", 40), ProtectFromMagic(
				37, 95, "Protect from Magic", 37);

		private final int interfaceId, settingID, levelRequired;
		private final String name;

		Prayers(final int interfaceId, final int settingID, final String name,
				final int levelRequired) {
			this.interfaceId = interfaceId;
			this.settingID = settingID;
			this.name = name;
			this.levelRequired = levelRequired;
		}

		public int getInterfaceId() {
			return interfaceId;
		}

		public int getSettingID() {
			return settingID;
		}

		public String getName() {
			return name;
		}

		public int getReqLevel() {
			return levelRequired;
		}

		public boolean isActivated() {
			return Game.getSetting(getSettingID()) > 0;
		}
	}

	public static int getPoints() {
		return Skills.getCurrentLevel("Prayer");
	}

	public static int getLevel() {
		return Skills.getActualLevel("Prayer");
	}

	public static void disable(Brothers b) {
		if (GameTab.getOpen() != TABS.PRAYERS) {
			Keyboard.pressFunctionKey(5);
		}
		Point p = GeneralMethods.getRandomPoint(Interfaces.get(271,
				b.getPrayer().getInterfaceId())
				.getAbsoluteBounds());
		Mouse.move(p);// Could be move doesn't really matter
		for (int fsafe = 0; fsafe < 20
				&& !Game.getUptext().contains(
						"Deactivate " + b.getPrayer().getName()); fsafe++) {
			General.sleep(30);
		}
		if (Game.getUptext().contains(
				"Deactivate " + b.getPrayer().getName())) {
			GeneralMethods.leftClick(p);
		}
		for (int fsafe = 0; fsafe<20 && b.getPrayer().isActivated(); fsafe++){
			General.sleep(20);
		}
		if (b.getPrayer().isActivated()) {
			disable(b);
		}
	}

	public static void activate(Brothers b) {
		if (GameTab.getOpen() != TABS.PRAYERS) {
			Keyboard.pressFunctionKey(5);
		}
		Point p = GeneralMethods.getRandomPoint(Interfaces.get(271,
				b.getPrayer().getInterfaceId())
				.getAbsoluteBounds());
		Mouse.move(p);// Could be move doesn't really matter
		for (int fsafe = 0; fsafe < 20
				&& !Game.getUptext().contains(
						"Activate " + b.getPrayer().getName()); fsafe++) {
			General.sleep(30);
		}
		if (Game.getUptext().contains(
				"Activate " + b.getPrayer().getName())) {
			GeneralMethods.leftClick(p);
		}
		for (int fsafe = 0; fsafe<20 && !b.getPrayer().isActivated(); fsafe++){
			General.sleep(20);
		}
		if (!b.getPrayer().isActivated()) {
			activate(b);
		}
	}
}
