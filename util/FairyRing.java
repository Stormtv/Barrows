package scripts.Barrows.util;

import java.awt.Color;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.colour.Tolerance;
import org.tribot.api2007.Screen;

public class FairyRing {

	public static boolean SetFairyCombination(String Letter1, String Letter2,
			String Letter3) {
		while (AreColorsChanging())
			General.sleep(100);
		if (!AreColorsChanging()
				&& !getFairyCombinationLetter1().equalsIgnoreCase(Letter1)) {
			Mouse.clickBox(101, 179, 162, 139, 1);
			General.sleep(3000);
			return false;
		} else if (!AreColorsChanging()
				&& !getFairyCombinationLetter2().equalsIgnoreCase(Letter2)) {
			Mouse.clickBox(279, 179, 330, 140, 1);
			General.sleep(3000);
			return false;
		} else if (!AreColorsChanging()
				&& !getFairyCombinationLetter3().equalsIgnoreCase(Letter3)) {
			Mouse.clickBox(449, 179, 497, 140, 1);
			General.sleep(3000);
			return false;
		}
	
		return true;
	}

	public static boolean AreColorsChanging() {
		Color Letter1 = Screen.getColorAt(94, 208);
		Color Letter2 = Screen.getColorAt(268, 223);
		Color Letter3 = Screen.getColorAt(496, 173);
		General.sleep(100);
		if (!org.tribot.api.Screen.coloursMatch(Letter1,
				Screen.getColorAt(94, 208), new Tolerance(20))
				|| !org.tribot.api.Screen.coloursMatch(Letter2,
						Screen.getColorAt(268, 223), new Tolerance(20))
				|| !org.tribot.api.Screen.coloursMatch(Letter3,
						Screen.getColorAt(496, 173), new Tolerance(20))) {
			return true;
		} else {
			return false;
		}
	}

	static public String getFairyCombinationLetter1() {
		boolean LetterB = org.tribot.api.Screen.coloursMatch(new Color(205,
				201, 195), Screen.getColorAt(94, 208), new Tolerance(20));
		boolean LetterC = org.tribot.api.Screen.coloursMatch(new Color(191,
				182, 172), Screen.getColorAt(103, 202), new Tolerance(20));
		boolean LetterA = org.tribot.api.Screen.coloursMatch(new Color(205,
				202, 199), Screen.getColorAt(94, 202), new Tolerance(20));

		if (LetterB) {
			return "b";
		} else if (LetterC) {
			return "c";
		} else if (LetterA) {
			return "a";
		} else {
			return "d";
		}
	}

	static public String getFairyCombinationLetter2() {
		boolean LetterL = org.tribot.api.Screen.coloursMatch(new Color(206,
				201, 194), Screen.getColorAt(268, 223), new Tolerance(20));
		boolean LetterK = org.tribot.api.Screen.coloursMatch(new Color(212,
				210, 208), Screen.getColorAt(257, 228), new Tolerance(20));
		boolean LetterJ = org.tribot.api.Screen.coloursMatch(new Color(234,
				229, 224), Screen.getColorAt(253, 117), new Tolerance(20));

		if (LetterL) {
			return "l";
		} else if (LetterK) {
			return "k";
		} else if (LetterJ) {
			return "j";
		} else {
			return "i";
		}
	}

	static public String getFairyCombinationLetter3() {
		boolean LetterP = !org.tribot.api.Screen.coloursMatch(new Color(181,
				164, 145), Screen.getColorAt(496, 173), new Tolerance(20))
				&& org.tribot.api.Screen.coloursMatch(new Color(201, 196, 189),
						Screen.getColorAt(432, 201), new Tolerance(20))
				&& org.tribot.api.Screen.coloursMatch(new Color(216, 216, 217),
						Screen.getColorAt(420, 216), new Tolerance(20));
		boolean LetterS = org.tribot.api.Screen.coloursMatch(new Color(181,
				164, 145), Screen.getColorAt(496, 173), new Tolerance(20));
		boolean LetterR = org.tribot.api.Screen.coloursMatch(new Color(206,
				201, 194), Screen.getColorAt(435, 222), new Tolerance(20));

		if (LetterP) {
			return "p";
		} else if (LetterS) {
			return "s";
		} else if (LetterR) {
			return "r";
		} else {
			return "q";
		}
	}

}
