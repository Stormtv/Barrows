package scripts.Barrows.methods.tunnel;

import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import scripts.Barrows.main.BrotherKilling;
import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.methods.Looting;
import scripts.Barrows.methods.tunnel.Rooms.TunnelRoom;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Food;

public class TunnelTraversing {

	public static void traverseTunnel() {
		if (Var.startingRoom == null) {
			Rooms.TunnelRoom room = Rooms.getRoom();
			if (room != null
					&& room.getExitTile() != null
					&& Objects.getAt(room.getExitTile()) != null
					&& Objects.getAt(room.getExitTile()).length > 0
					&& Objects.getAt(room.getExitTile())[0].getModel()
							.getPoints().length > 0) {
				Var.startingRoom = room;
			} else {
				return;
			}
		}
		if (Rooms.getRoom() != null) {
			if (TunnelPuzzle.isPuzzleScreenOpen()) {
				TunnelPuzzle.solvePuzzle();
			} else {
				if (Var.lootedChest) {
					if (Rooms.getRoom() != null
							&& Rooms.getRoom().equals(Var.startingRoom)) {
						climbLadder();
					} else if (Rooms.getRoom() != null) {
						tunnelWalkTo(Var.startingRoom);
					}
				} else {
					if (Rooms.getRoom() != null
							&& Rooms.getRoom().equals(Rooms.TunnelRoom.CC)) {
						openChest();
					} else if (Rooms.getRoom() != null) {
						tunnelWalkTo(Rooms.TunnelRoom.CC);
					}
				}
			}
		}
	}

	private static void climbLadder() {
		RSObject climbingTool = null;
		if (Objects.getAt(Var.startingRoom.getExitTile()).length > 0) {
			climbingTool = Objects.getAt(Var.startingRoom.getExitTile())[0];
		}
		if (climbingTool != null) {
			GeneralMethods.clickObject(climbingTool, "Climb", false, false);
			for (int i = 0; i < 40
					&& !BrotherKilling.isInCrypt(Tunnel.whosCrypt()); i++) {
				General.sleep(10, 20);
			}
		}
	}

	private static void openChest() {
		BrotherKilling.killBrotherInTunnel();
		RSObject[] chest = Objects.find(10, 20973);
		if (chest.length > 0) {
			if (chest[0].getModel().getPoints().length == 606) {
				GeneralMethods.clickObject(chest[0], "Open", false, true);
				BrotherKilling.killBrotherInTunnel();
			} else {
				int price = 0;
				for (RSItem i : Inventory.getAll()) {
					price += GeneralMethods.getPrice(i.getID()) * i.getStack();
				}
				GeneralMethods.clickObject(chest[0], "Search", false, true);
				BrotherKilling.killBrotherInTunnel();
				Var.lootedChest = true;
				Looting.loot(Var.lootIDs);
				int finalPrice = 0;
				for (RSItem i : Inventory.getAll()) {
					if (GeneralMethods.arrayContains(Var.armour_ids, i.getID()))
						Var.pieces++;
					finalPrice += GeneralMethods.getPrice(i.getID())
							* i.getStack();
				}
				Var.profit += finalPrice - price;
				Var.chests += 1;
			}
		}
	}

	static boolean isAtRoom(TunnelRoom t) {
		TunnelRoom mine = Rooms.getRoom();
		return mine != null && t != null && t.equals(mine);
	}

	public static void tunnelWalkTo(TunnelRoom r) {
		Walking.walking_timeout = 500;
		BrotherKilling.killBrotherInTunnel();
		Var.status = "Checking if needing to eat food";
		Food.eatInCombat();
		Var.status = "Generating shortest path";
		TunnelDoor[] path = WTunnelTraverse.pathToChest(r);
		TunnelRoom curRoom = null;
		if (path != null && path.length > 0) {
			RSObject[] nextDoor = Objects.getAt(path[0].getLocation());
			if (nextDoor.length > 0) {
				if (!nextDoor[0].isOnScreen()) {
					curRoom = Rooms.getRoom();
					if (Rooms.InTunnel()) {
						Var.status = "Tunnel Walking";
						Camera.setCameraAngle(General.random(90, 99));
						for (int i = 0; i < 10 && nextDoor != null
								&& nextDoor.length > 0
								&& curRoom.equals(Rooms.getRoom())
								&& !nextDoor[0].isOnScreen(); i++) {
							GeneralMethods.screenWalkTo(nextDoor[0]);
						}
						Var.status = "Finished Walking";
					} else {
						Var.status = "Screen walking";
						for (int i = 0; i < 10 && nextDoor != null
								&& nextDoor.length > 0
								&& curRoom.equals(Rooms.getRoom())
								&& !nextDoor[0].isOnScreen(); i++) {
							GeneralMethods.screenWalkTo(nextDoor[0]);
						}
						Var.status = "Finished Walking";
					}
				}
				if (nextDoor[0].isOnScreen()) {
					curRoom = Rooms.getRoom();
					try {
						Var.status = "Clicking next door!";
						GeneralMethods.clickObject(nextDoor[0], "Open", false,
								false);
						for (int i = 0; i < 200
								&& !TunnelPuzzle.isPuzzleScreenOpen()
								&& (curRoom == null || curRoom.equals(Rooms
										.getRoom())); i++) {
							General.sleep(10, 15);
						}
						Var.status = "Checking for puzzle";
						if (TunnelPuzzle.isPuzzleScreenOpen()) {
							TunnelPuzzle.solvePuzzle();
						}
						Var.status = "Checking for brother";
						BrotherKilling.killBrotherInTunnel();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	static boolean isBeingAttackedByBrother() {
		return BrotherKilling.aggressiveNPC() != null;
	}

}
