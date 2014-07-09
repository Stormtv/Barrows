package scripts.Barrows.types.enums;

import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;

import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.types.Potions;

public class Prayer {

	public static void flicker(Prayers p) {
		if (!p.isActivated()) {
			int xp = Skills.getXP(Skills.SKILLS.HITPOINTS);
			Prayer.activate(p);
			for (int fsafe = 0; Skills.getXP(Skills.SKILLS.HITPOINTS) == xp
					&& !(Player.getRSPlayer().getInteractingCharacter() == null)
					&& fsafe < 41; fsafe++) {
				General.sleep(15);
				if (fsafe == 40) {
					System.out.println("Must of hit a 0 turnning piety off");
				}
			}
			if (p.isActivated()) {
				Prayer.disable(p);
			}
		} else {
			Prayer.disable(p);
		}
	}

	public enum Prayers {
		None(-1, -1, "", -1), Piety(55, 54, "Piety", 70), Chivalry(53, 52,
				"Chivalry", 60), ProtectFromMelee(18, 16384, "Protect from Melee",
				43), ProtectFromMissiles(17, 8192, "Protect from Missiles", 40), ProtectFromMagic(
				16, 4096, "Protect from Magic", 37);

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
			return Game.getSetting(83) == getSettingID();
		}
	}

	public static int getPoints() {
		return Skills.getCurrentLevel(Skills.SKILLS.PRAYER);
	}

	public static int getLevel() {
		return Skills.getActualLevel(Skills.SKILLS.PRAYER);
	}

	public static boolean anyPrayerEnabled() {
		for (Prayers p : Prayers.values()) {
			if (p.isActivated())
				return true;
		}
		return false;
	}

	public static void disable(Prayers b) {
		if (b.equals(Prayer.Prayers.None))
			return;
		if (GameTab.getOpen() != TABS.PRAYERS) {
			GameTab.open(GameTab.TABS.PRAYERS);
			for (int fsafe = 0; fsafe < 20
					&& !GameTab.getOpen().equals(TABS.PRAYERS); fsafe++) {
				General.sleep(15);
			}
		}
		Point p = GeneralMethods.getRandomPoint(Interfaces.get(271,
				b.getInterfaceId()).getAbsoluteBounds());
		Mouse.move(p);// Could be move doesn't really matter
		for (int fsafe = 0; fsafe < 20
				&& !Game.getUptext().contains("Deactivate " + b.getName()); fsafe++) {
			General.sleep(30);
		}
		if (Game.getUptext().contains("Deactivate " + b.getName())) {
			Mouse.click(1);
		}
		for (int fsafe = 0; fsafe < 20 && b.isActivated(); fsafe++) {
			General.sleep(40);
		}
		if (b.isActivated()) {
			disable(b);
		}
		if (GameTab.getOpen() != TABS.INVENTORY) {
			GameTab.open(TABS.INVENTORY);
			for (int fsafe = 0; fsafe < 20
					&& !GameTab.getOpen().equals(TABS.INVENTORY); fsafe++) {
				General.sleep(15);
			}
		}
	}

	public static void disableAllPrayers() {
		for (Prayers p : Prayers.values()) {
			if (!p.equals(Prayer.Prayers.None) && p.isActivated()) {
				disable(p);
			}
		}
	}

	public static void activate(Prayers p) {
		if (p.equals(Prayer.Prayers.None)) return;
		if (Prayer.getPoints()==0) {
			Potions.drink();
		}
		if (GameTab.getOpen() != TABS.PRAYERS) {
			GameTab.open(GameTab.TABS.PRAYERS);
			for (int fsafe = 0; fsafe < 20
					&& !GameTab.getOpen().equals(TABS.PRAYERS); fsafe++) {
				General.sleep(15);
			}
		}
		Point a = GeneralMethods.getRandomPoint(Interfaces.get(271,
				p.getInterfaceId()).getAbsoluteBounds());
		Mouse.move(a);// Could be move doesn't really matter
		for (int fsafe = 0; fsafe < 20
				&& !Game.getUptext().contains("Activate " + p.getName()); fsafe++) {
			General.sleep(30);
		}
		if (Game.getUptext().contains("Activate " + p.getName())) {
			Mouse.click(1);
		}
		for (int fsafe = 0; fsafe < 20 && !p.isActivated(); fsafe++) {
			General.sleep(40);
		}
		if (!p.isActivated()) {
			activate(p);
		}
		if (GameTab.getOpen() != TABS.INVENTORY) {
			GameTab.open(TABS.INVENTORY);
			for (int fsafe = 0; fsafe < 20
					&& !GameTab.getOpen().equals(TABS.INVENTORY); fsafe++) {
				General.sleep(15);
			}
		}
	}
}
