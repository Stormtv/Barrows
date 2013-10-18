package scripts.Barrows.methods.tunnel;

import java.awt.Color;

import org.tribot.api.General;
import org.tribot.api.types.colour.Tolerance;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Screen;
import org.tribot.api2007.types.RSInterface;

public class TunnelPuzzle {

	public static boolean isPuzzleScreenOpen() {
		Color c = Screen.getColorAt(392, 86);
		Color b = new Color(163, 157, 150);
		return Interfaces.get(25, 1) != null
				&& !Interfaces.get(25, 1).isHidden()
				&& org.tribot.api.Screen.coloursMatch(b, c, new Tolerance(10));

	}

	private static int getPuzzle() {
		if (isPuzzleScreenOpen()) {
			if (Interfaces.get(25, 1) != null)
				return Interfaces.get(25, 1).getModelID();
		}
		return 0;
	}

	private static RSInterface getAnswer() {
		if (isPuzzleScreenOpen()) {
			for (int i = 3; i < 9; i++) {
				if (Interfaces.get(25, i) != null
						&& Interfaces.get(25, i).getModelID() == (getPuzzle() - 3)) {
					return Interfaces.get(25, i);
				}
			}
		}
		return null;
	}

	public static boolean solvePuzzle() {
		General.println("solving puzzle");
		if (isPuzzleScreenOpen()) {
			RSInterface answer = getAnswer();
			if (answer != null) {
				if (answer.click("Ok")) {
					for (int fail = 0; fail < 20 && isPuzzleScreenOpen(); fail++) {
						General.sleep(40, 50);
					}
					return true;
				}
			}
		}
		return false;
	}

}
