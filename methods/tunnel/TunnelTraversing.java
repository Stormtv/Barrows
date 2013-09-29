package scripts.Barrows.methods.tunnel;

import java.io.ObjectInputStream.GetField;

import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.util.RSArea;
import scripts.Barrows.main.BrotherKilling;
import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.methods.tunnel.Rooms.TunnelRoom;

public class TunnelTraversing {

	static boolean comingBack = false;

	static void walkToChest() {
		//if (isBeingAttackedByBrother()) {
			//BrotherKilling.killBrotherInTunnel();
	//	} else {
			openNextDoor();
		//}
	}

	public static void traverseTunnel() {
		if (TunnelPuzzle.isPuzzleScreenOpen()) {
			TunnelPuzzle.solvePuzzle();
		} else {
			if (comingBack) {
				walkToLadder();
			} else {
				if (Rooms.getRoom() != null && Rooms.getRoom().equals(Rooms.TunnelRoom.CC)) {
					openChest();
				} else {
					walkToChest();
				}
			}
		}
	}

	// TODO add brother killing @ chest
	// TODO add inventory space checkup before looting
	// TODO if chest mode points .length < 800 = closed (to pot up before
	// brother killing if needed)

	private static void openChest() {
		RSObject[] chest = Objects.find(10, 20973);
		if (chest.length > 0) {
			GeneralMethods.clickObject(chest[0], "", false);
		}
	}

	private static void walkToLadder() {
		// TODO Auto-generated method stub

	}

	public static void openNextDoor() {
		Walking.walking_timeout = 500;

		TunnelDoor[] path = WTunnelTraverse.pathToChest();
		TunnelRoom curRoom = Rooms.getRoom();
		if (path.length > 0) {
			System.out.println(path[0].toString());
			RSObject[] nextDoor = Objects.getAt(path[0].getLocation());
			if (nextDoor.length > 0) {
				if (Rooms.InTunnel() && !nextDoor[0].isOnScreen()) {
					System.out.println("yes");
					Camera.setCameraAngle(General.random(90, 99));
					for (int i = 0; i < 50; i++) {
						if (Rooms.InTunnel()
								&& nextDoor[0] != null
								&& !nextDoor[0].isOnScreen()
								&& nextDoor[0].getPosition().distanceTo(
										Player.getRSPlayer()) > 5) {
							System.out.println("lol " + i);
							walkInsideTunnel(path[0].getLocation());
						}
					}
				} else {
					System.out.println("no");
					GeneralMethods.clickObject(nextDoor[0], "Open", false);
					for (int i=0; i<200 && curRoom != null && curRoom.equals(Rooms.getRoom()); i++) {
						General.sleep(10);
					}
				}
			}
		}
	}

	private static void walkInsideTunnel(RSTile location) {
		RSTile tile = Player.getPosition();
		if (location.getX() > (Player.getPosition().getX() + 5)) {
			System.out.println("ta mais � direita");
			tile = new RSTile(Player.getPosition().getX() + 5, Player
					.getPosition().getY());
		} else if (location.getX() < (Player.getPosition().getX() - 5)) {
			System.out.println("ta mais � esquerda");
			tile = new RSTile(Player.getPosition().getX() - 5, Player
					.getPosition().getY());
		}

		if (location.getY() > (Player.getPosition().getY() + 5)) {
			System.out.println("ta mais pa cima");
			tile = new RSTile(Player.getPosition().getX(), Player.getPosition()
					.getY() + 5);
		} else if (location.getY() < (Player.getPosition().getY() - 5)) {
			System.out.println("ta mais pa baixo");
			tile = new RSTile(Player.getPosition().getX(), Player.getPosition()
					.getY() - 5);
		}
		GeneralMethods.walkScreen(tile);

	}

	

	static boolean isBeingAttackedByBrother() {
		return BrotherKilling.aggressiveNPC() != null;
	}

}
