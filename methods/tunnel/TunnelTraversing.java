package scripts.Barrows.methods.tunnel;

import org.tribot.api.General;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

import scripts.Barrows.main.BrotherKilling;
import scripts.Barrows.methods.BankHandler;
import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.methods.Looting;
import scripts.Barrows.methods.LootFiltering;
import scripts.Barrows.methods.PriceHandler;
import scripts.Barrows.methods.tunnel.Rooms.TunnelRoom;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.Potions;
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
					&& Objects.getAt(room.getExitTile())[0].getModel() != null
					&& Objects.getAt(room.getExitTile())[0].getModel()
							.getPoints().length > 0) {
				Var.startingRoom = room;
			}
			if (Var.startingRoom == null) return;
		}
		if (Rooms.getRoom() != null) {
			if (TunnelPuzzle.isPuzzleScreenOpen()) {
				TunnelPuzzle.solvePuzzle();
			} else {
				if (Var.lootedChest) {
					if (Rooms.getRoom() != null
							&& Var.startingRoom != null
							&& Rooms.getRoom().equals(Var.startingRoom)) {
						climbLadder();
					} else if (Rooms.getRoom() != null && Var.startingRoom !=null) {
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
				for (Brothers b : Brothers.values()) {
					if (!b.isKilled() && b.isTunnel()) {
						BrotherKilling.getReadyToFight(b);
					}
				}
				GeneralMethods.clickObject(chest[0], "Open", false, true);
				BrotherKilling.killBrotherInTunnel();
			} else {
				LootFiltering.addInventory(true);
				int price = 0;
				for (RSItem i : Inventory.getAll()) {
					price += PriceHandler.getPrice(i.getID()) * i.getStack();
				}
				GeneralMethods.clickObject(chest[0], "Search", false, true);
				BrotherKilling.killBrotherInTunnel();
				Var.lootedChest = true;
				Looting.loot(Var.lootIDs);
				LootFiltering.addInventory(false);
				int finalPrice = 0;
				for (RSItem i : Inventory.getAll()) {
					if (GeneralMethods.arrayContains(Var.armour_ids, i.getID()))
						Var.pieces++;
					finalPrice += PriceHandler.getPrice(i.getID())
							* i.getStack();
				}
				Var.profit += finalPrice - price;
				Var.chests += 1;
				LootFiltering.addLoot();
				GeneralMethods.takeScreenShot();
				if (Potions.getPrayerDoses() < Var.nextRunDoses
						|| Inventory.getCount(Var.food.getId()) < Var.nextRunFood) {
					Var.forceBank = true;
				}
			}
		}
	}

	private static void tunnelWalkTo(TunnelRoom r) {
		Walking.setWalkingTimeout(500);
		Var.status = "Generating shortest path";
		TunnelDoor[] path = WTunnelTraverse.doorPathToChest(r);
		TunnelRoom curRoom = null;
		if (path != null && path.length > 0) {
			for (TunnelDoor door : path) {
				if (BankHandler.needToBank()) {
					return;
				}
				Var.status = "Checking if needing to eat food";
				Food.eatInCombat();
				TunnelRoom current = Rooms.getRoom();
				while (Rooms.getRoom() != null && Rooms.getRoom() == current
						&& Tunnel.isPuzzleDoor(door) && Tunnel.getKcLeft() > 0
						&& !BankHandler.needToBank()
						&& PathFinding.canReach(door.getLocation(), true)) {
					Tunnel.fightForKc();
					General.sleep(30, 100);
				}
				RSObject[] nextDoor = Objects.getAt(door.getLocation());
				if (nextDoor != null && nextDoor.length > 0
						&& PathFinding.canReach(nextDoor[0], true)) {
					RSObject next = null;
					for (RSObject o : nextDoor) {
						if (next == null) {
							next = o;
						}
						if (o !=null && o.getModel()!=null && o.getModel().getPoints().length > 1000) {
							next = o;
						}
					}
					curRoom = Rooms.getRoom();
					if (next != null) {
						GeneralMethods.clickObject(next, "Open", false, true);
						for (int i = 0; i < 20
								&& !TunnelPuzzle.isPuzzleScreenOpen()
								&& (curRoom == null || curRoom.equals(Rooms
										.getRoom())); i++) {
							General.sleep(100, 150);
						}
						Var.status = "Checking for puzzle";
						if (TunnelPuzzle.isPuzzleScreenOpen()) {
							TunnelPuzzle.solvePuzzle();
						}
						Var.status = "Checking for brother";
						BrotherKilling.killBrotherInTunnel();
					}
				}
			}
		}
	}
}
