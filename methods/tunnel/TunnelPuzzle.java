package scripts.Barrows.methods.tunnel;

import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;

public class TunnelPuzzle {

	public static boolean isPuzzleScreenOpen() {
		return Interfaces.get(25) != null;
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
		if (isPuzzleScreenOpen()) {
			RSInterface answer = getAnswer();
			if (answer != null) {
				if (answer.click("Ok")) {
					// TODO dyanmic sleep
					return true;
				}
			}
		}
		return false;
	}

}
