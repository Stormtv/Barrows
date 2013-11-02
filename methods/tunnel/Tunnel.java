package scripts.Barrows.methods.tunnel;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;

import scripts.Barrows.main.BrotherKilling;
import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.enums.Food;
import scripts.Barrows.types.Var;

public class Tunnel {

	public static void exitCrypt() {
		Var.status = "Lost Exiting Crypt";
		for (Brothers b : Brothers.values()) {
			if (Objects.find(10, b.getStair()).length > 0) {
				RSObject stair = Objects.find(10, b.getStair())[0];
				GeneralMethods.clickObject(stair, "Climb", false, true);
				for (int fSafe = 0; fSafe < 20
						&& Player.getPosition().getPlane() == 3; fSafe++) {
					General.sleep(75);
				}
			}
		}
	}

	public static void goToTunnel() {
		for (Brothers b : Brothers.values()) {
			if (b.isTunnel()) {
				if (!BrotherKilling.isInCrypt(b)) {
					BrotherKilling.goToCrypt(b);
				}
				if (BrotherKilling.isInCrypt(b)) {
					RSObject[] sarco = Objects.find(20, b.getCryptID());
					if (Interfaces.get(210) == null
							&& Interfaces.get(228) == null) {
						GeneralMethods.clickObject(sarco[0], "Search", false,
								true);
					}
					for (int fail = 0; fail < 20
							&& Interfaces.get(210, 1) == null; fail++) {
						General.sleep(10, 21);
					}
					if (Interfaces.get(210, 1) != null) {
						Interfaces.get(210, 1).click("Continue");
					}
					for (int fail = 0; fail < 20
							&& Interfaces.get(228, 1) == null; fail++) {
						General.sleep(10, 21);
					}
					if (Interfaces.get(228, 1) != null) {
						Interfaces.get(228, 1).click("Continue");
					}
					for (int fail = 0; fail < 40 && Rooms.getRoom() == null; fail++) {
						General.sleep(10, 21);
					}
					General.sleep(450, 650);
					Rooms.TunnelRoom room = Rooms.getRoom();
					if (room != null
							&& room.getExitTile() != null
							&& Objects.getAt(room.getExitTile())[0].getModel()
									.getPoints().length > 0) {
						Var.startingRoom = room;
					}
					Camera.setCameraAngle(General.random(80, 100));
				}
			}
		}
	}

	public static boolean inCrypt() {
		return Objects.find(20, "Sarcophagus").length > 0;
	}

	public static Brothers whosCrypt() {
		for (Brother.Brothers b : Brother.Brothers.values()) {
			if (Objects.find(20, b.getCryptID()).length > 0) {
				return b;
			}
		}
		return null;
	}

	public static int getKillCount() {
		return Game.getSetting(453) / 131189;
	}

	public static int getKcLeft() {
		return Brother.getTunnelBrother().isKilled() ? getCount() : getCount() - 1;
	}

	private static int getCount() {
		return Var.killCount - getKillCount();
	}

	public static boolean isPuzzleDoor(TunnelDoor door) {
		return door.equals(TunnelDoor.N_S_DOOR)
				|| door.equals(TunnelDoor.W_E_DOOR)
				|| door.equals(TunnelDoor.S_N_DOOR)
				|| door.equals(TunnelDoor.E_W_DOOR);
	}

	public static void fightForKc() {
		Var.status = "Getting Kc up to " + Var.killCount;
		RSNPC target;
		Food.eatInCombat();
		if (Player.getRSPlayer().getInteractingCharacter() == null) {
			if (agressiveNPC().isEmpty()) {
				target = closestNPC(combatFilter(reachFilter()));
			} else {
				target = closestNPC(agressiveNPC());
			}
			if (target != null && !target.isOnScreen()) {
				walkToMob(target);
			}
			if(!attackMob(target)) {
				
			}
		} else {
			RSNPC attackingNPC = (RSNPC) Player.getRSPlayer()
					.getInteractingCharacter();
			if (attackingNPC.getHealth() > 0 && isAttackable(attackingNPC)) {
				target = attackingNPC;
			}
			levelUpCloser();
		}
	}

	private static RSNPC closestNPC(ArrayList<RSNPC> a) {
		int dist = Integer.MAX_VALUE;
		int level = 90;
		RSNPC target = null;
		for (RSNPC n : a) {
			if (n.getCombatLevel() < level) {
				level = n.getCombatLevel();
			}
		}
		for (RSNPC n : a) {
			if (n.getCombatLevel() == level) {
				int thisDist = n.getPosition().distanceTo(Player.getPosition());
				if (thisDist < dist) {
					dist = thisDist;
					target = n;
				}
			}
		}
		return target;
	}

	public static ArrayList<RSNPC> reachFilter() {
		ArrayList<RSNPC> ourNPCs = new ArrayList<RSNPC>();
		for (RSNPC n : NPCs.getAll()) {
			if (PathFinding.canReach(n, false)) {
				ourNPCs.add(n);
			}
		}
		return ourNPCs;
	}

	public static ArrayList<RSNPC> combatFilter(ArrayList<RSNPC> npcs) {
		ArrayList<RSNPC> ourNPCs = new ArrayList<RSNPC>();
		ArrayList<RSNPC> badNPCs = new ArrayList<RSNPC>();
		for (RSNPC n : npcs) {
			if (!n.isInCombat()) {
				ourNPCs.add(n);
			} else {
				badNPCs.add(n);
			}
		}
		return ourNPCs;
	}

	private static ArrayList<RSNPC> agressiveNPC() {
		ArrayList<RSNPC> ourNPCs = new ArrayList<RSNPC>();
		for (RSNPC n : NPCs.getAll()) {
			if (n.isInteractingWithMe()
					&& Player.getRSPlayer().getInteractingCharacter() == null
					&& PathFinding.canReach(n, false) && isAttackable(n)
					&& n.getCombatLevel() < 90) {
				ourNPCs.add(n);
			}

		}
		return ourNPCs;
	}

	private static void walkToMob(RSNPC n) {
		Var.status = "Navigating to target";
		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		Walking.walkTo(n);
		General.sleep(250, 350);
		while (Player.isMoving() && !n.isOnScreen()) {
			General.sleep(25, 50);
		}
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
	}

	private static boolean attackMob(RSNPC target) {
		Var.status = "Attacking monster";
		if (target != null) {
			Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
			GeneralMethods.click(target, "Attack");
			General.sleep(250, 350);
			while (Player.isMoving())
				General.sleep(10);
			Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
			return true;
		} else {
			General.println("No Target Found this is strange");
			return false;
		}
	}

	private static boolean isAttackable(RSNPC npc) {
		return npc.getCombatLevel() > 0;
	}

	private static void levelUpCloser() {
		if (Interfaces.get(171, 2) != null) {
			Var.status = "Closing Level up interface";
			Interfaces.get(171, 2).click("Continue");
		}
		if (Interfaces.get(167, 2) != null) {
			Var.status = "Closing Level up interface";
			Interfaces.get(167, 2).click("Continue");
		}
		if (Interfaces.get(168, 2) != null) {
			Var.status = "Closing Level up interface";
			Interfaces.get(168, 2).click("Continue");
		}
		if (Interfaces.get(158, 2) != null) {
			Var.status = "Grats on level let me close that interface";
			Interfaces.get(158, 2).click("Continue");
		}
		if (Interfaces.get(161, 2) != null) {
			Var.status = "Grats on a def level let me close that interface";
			Interfaces.get(161, 2).click("Continue");
		}
	}
}
