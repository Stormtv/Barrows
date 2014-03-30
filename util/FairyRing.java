package scripts.Barrows.util;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;

public class FairyRing {

	public enum FairyLetter {

		B,
		K,
		R;

		public char getChar() {
			return toString().toLowerCase().charAt(0);
		}

		public boolean isSelected() {
			String binary = Integer.toBinaryString(Game.getSetting(816));
			if (ordinal() == 0)
				return binary.endsWith("11");
			if (ordinal() == 1)
				return binary.substring(binary.length() - 4,
						binary.length() - 3).equals("1")
						&& binary.substring(binary.length() - 3,
								binary.length() - 2).equals("0");
			if (ordinal() == 2)
				return binary.substring(binary.length() - 6,
						binary.length() - 5).equals("1");
			return false;
		}
	}

	public static boolean setFairyCombination() {
		for (final FairyLetter f : FairyLetter.values()) {
			if (!f.isSelected()) {
				int x = 90 + (170 * f.ordinal());
				System.out.println(f.getChar() + " x=" + x);
				while (!f.isSelected() && Interfaces.get(398, 2) != null) {
					Mouse.click(x, 215, 1);
					General.sleep(2000);
				}
				return false;
			}
		}
		return true;
	}
}
