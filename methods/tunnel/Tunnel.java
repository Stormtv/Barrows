package scripts.Barrows.methods.tunnel;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Combat;
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
		return Brother.getTunnelBrother() == null ? getCount() : getCount() - 1;
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
		if (!Player.getRSPlayer().isInCombat()) {
			RSNPC monster = getMonster();
			if (monster != null) {
				if (monster.isOnScreen()) {
					if (monster.click("Attack")) {
						while (Player.isMoving())
							General.sleep(100);
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								return Combat.getAttackingEntities().length > 0;
							}
						}, 3000);
					}
				} else {
					Walking.walkTo(monster);
				}
			}
		} else {
			if (!Combat.isAutoRetaliateOn())
				Combat.setAutoRetaliate(true);
			Food.eatInCombat();
		}
	}

	private static int[] monsterIds = { 1921, 1920, 1922, 1916, 1915 };

	public static RSNPC getMonster() {
		for (RSNPC n : NPCs.findNearest(monsterIds)) {
			if (PathFinding.canReach(n, false))
				return n;
		}
		return null;
	}
}
