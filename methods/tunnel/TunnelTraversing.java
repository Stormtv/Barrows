package scripts.Barrows.methods.tunnel;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.main.BrotherKilling;
import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.methods.tunnel.Rooms.TunnelRoom;
import scripts.Barrows.util.RSArea;

public class TunnelTraversing {

	static boolean comingBack = false;

	static void walkToChest() {
		// if (isBeingAttackedByBrother()) {
		// BrotherKilling.killBrotherInTunnel();
		// } else {
		openNextDoor();
		// }
	}

	public static void traverseTunnel() {
		if (Rooms.getRoom() != null) {
			if (TunnelPuzzle.isPuzzleScreenOpen()) {
				TunnelPuzzle.solvePuzzle();
			} else {
				if (comingBack) {
					walkToLadder();
				} else {
					if (Rooms.getRoom() != null
							&& Rooms.getRoom().equals(Rooms.TunnelRoom.CC)) {
						openChest();
					} else {
						walkToChest();
					}
				}
			}
		} else {
			General.sleep(200);
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

	static boolean isAtRoom(TunnelRoom t) {
		TunnelRoom mine = Rooms.getRoom();
		return mine != null && t != null
				&& t.equals(mine);
	}

	public static void openNextDoor() {
		Walking.walking_timeout = 500;
		TunnelDoor[] path = WTunnelTraverse.pathToChest();
		TunnelRoom curRoom = null;
		if (path != null && path.length > 0) {
			RSObject[] nextDoor = Objects.getAt(path[0].getLocation());
			if (nextDoor.length > 0) {
				if (nextDoor[0].isOnScreen()) {
					curRoom = Rooms.getRoom();
					final TunnelRoom fml = curRoom;
					GeneralMethods.clickObject(nextDoor[0], "Open", false);
					//General.sleep(5000);
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return !isAtRoom(fml);
						}
					}, 5000);
					/*
					 * curRoom = Rooms.getRoom(); if (curRoom != null) { for
					 * (int i = 0; i < 200 && curRoom != null &&
					 * curRoom.equals(Rooms.getRoom()); i++) {
					 * General.sleep(10); } }
					 */
				} else {
					if (Rooms.InTunnel()) {
						Camera.setCameraAngle(General.random(90, 99));
						for (int i = 0; i < 50; i++) {
							if (Rooms.InTunnel() && nextDoor.length > 0
									&& !nextDoor[0].isOnScreen()) {
								walkInsideTunnel(path[0].getLocation());
							}
						}
					} else {
						RSTile[] t = Walking
								.generateStraightScreenPath(nextDoor[0]);
						for (int i = 0; i < 10; i++) {
							if (nextDoor.length > 0 && nextDoor[0] != null
									&& !nextDoor[0].isOnScreen())
								GeneralMethods.walkScreen(GeneralMethods
										.getFurthestTileOnScreen(t));
						}
					}
				}
			}
		}
	}

	private static void walkInsideTunnel(RSTile location) {
		RSTile tile = Player.getPosition();
		if (location.getX() > (Player.getPosition().getX() + 5)) {
			System.out.println("ta mais a direita");
			tile = new RSTile(Player.getPosition().getX() + 5, Player
					.getPosition().getY());
		} else if (location.getX() < (Player.getPosition().getX() - 5)) {
			System.out.println("ta mais a esquerda");
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
		GeneralMethods.walkScreen(filter(tile, location));

	}

	public static RSTile filter(RSTile tile, RSTile end) {
		RSArea aroundit = GeneralMethods.getWithin(5, tile);
		ArrayList<RSTile> h = new ArrayList<RSTile>();

		for (RSTile t : aroundit.getTiles()) {
			if (t != null
					&& PathFinding.canReach(t, false)
					&& PathFinding.isTileWalkable(t)
					&& Objects.getAt(t).length > 0
					&& Objects.getAt(t)[0].getModel().getPoints().length != 6
					&& Objects.getAt(t)[0].getModel().getPoints().length < 200
					&& !t.equals(Player.getPosition())
					&& (tile.distanceTo(end) < Player.getPosition().distanceTo(
							end))) {
				h.add(t);
			}
		}
		if (h.size() > 0)
			return GeneralMethods.getFurthestTileOnScreen(h);
		return null;
	}

	static boolean isBeingAttackedByBrother() {
		return BrotherKilling.aggressiveNPC() != null;
	}

}
