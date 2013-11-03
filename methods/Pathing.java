package scripts.Barrows.methods;

import java.awt.Color;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Options;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Screen;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.methods.tunnel.Rooms;
import scripts.Barrows.methods.tunnel.Tunnel;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Prayer;
import scripts.Barrows.util.FairyRing;

public class Pathing {

	public enum PathBarrows {
		SWAMP, SHORTCUT, BURGH_DE_ROTT, FAIRY_RINGS
	}

	public enum PathBank {
		HOUSE, ECTOPHIAL, VARROCK, BURGH_DE_ROTT
	}

	public static boolean isInHouse() {
		return Objects.findNearest(30, "Kharyrll Portal").length > 0;
	}

	final static RSTile[] pathFromVarrock = { new RSTile(3214, 3429, 0),
			new RSTile(3218, 3429, 0), new RSTile(3261, 3421, 0),
			new RSTile(3221, 3429, 0), new RSTile(3225, 3429, 0),
			new RSTile(3229, 3430, 0), new RSTile(3233, 3430, 0),
			new RSTile(3237, 3430, 0), new RSTile(3241, 3430, 0),
			new RSTile(3245, 3430, 0), new RSTile(3249, 3430, 0),
			new RSTile(3253, 3430, 0), new RSTile(3257, 3429, 0),
			new RSTile(3261, 3429, 0), new RSTile(3303, 3429, 0),
			new RSTile(3263, 3429, 0), new RSTile(3267, 3429, 0),
			new RSTile(3271, 3429, 0), new RSTile(3275, 3431, 0),
			new RSTile(3279, 3429, 0), new RSTile(3283, 3430, 0),
			new RSTile(3284, 3434, 0), new RSTile(3284, 3438, 0),
			new RSTile(3284, 3442, 0), new RSTile(3286, 3446, 0),
			new RSTile(3286, 3450, 0), new RSTile(3288, 3454, 0),
			new RSTile(3291, 3457, 0), new RSTile(3295, 3458, 0),
			new RSTile(3299, 3459, 0), new RSTile(3340, 3491, 0),
			new RSTile(3300, 3459, 0), new RSTile(3304, 3460, 0),
			new RSTile(3308, 3462, 0), new RSTile(3312, 3464, 0),
			new RSTile(3316, 3466, 0), new RSTile(3320, 3467, 0),
			new RSTile(3324, 3469, 0), new RSTile(3326, 3473, 0),
			new RSTile(3330, 3475, 0), new RSTile(3334, 3477, 0),
			new RSTile(3338, 3479, 0), new RSTile(3342, 3481, 0),
			new RSTile(3346, 3481, 0), new RSTile(3350, 3481, 0),
			new RSTile(3354, 3481, 0), new RSTile(3358, 3482, 0),
			new RSTile(3362, 3482, 0), new RSTile(3366, 3482, 0),
			new RSTile(3370, 3481, 0), new RSTile(3374, 3481, 0),
			new RSTile(3378, 3481, 0), new RSTile(3421, 3481, 0),
			new RSTile(3381, 3481, 0), new RSTile(3385, 3482, 0),
			new RSTile(3389, 3483, 0), new RSTile(3393, 3484, 0),
			new RSTile(3397, 3485, 0), new RSTile(3401, 3486, 0),
			new RSTile(3403, 3490, 0), new RSTile(3404, 3494, 0),
			new RSTile(3404, 3498, 0), new RSTile(3405, 3502, 0), };

	final static RSTile[] pubToBank = { new RSTile(3494, 3478, 0),
			new RSTile(3502, 3480, 0), new RSTile(3511, 3479, 0) };

	final static RSTile[] pathToGate = { new RSTile(3508, 3481, 0),
			new RSTile(3503, 3481, 0), new RSTile(3498, 3481, 0),
			new RSTile(3493, 3482, 0), new RSTile(3488, 3481, 0),
			new RSTile(3483, 3479, 0), new RSTile(3478, 3476, 0),
			new RSTile(3473, 3475, 0), new RSTile(3468, 3474, 0),
			new RSTile(3465, 3470, 0), new RSTile(3461, 3467, 0),
			new RSTile(3456, 3465, 0), new RSTile(3452, 3461, 0),
			new RSTile(3448, 3458, 0), new RSTile(3444, 3458, 0) };

	final static RSTile[] pathToBoat = { new RSTile(3443, 3456, 0),
			new RSTile(3449, 3455, 0), new RSTile(3455, 3455, 0),
			new RSTile(3456, 3449, 0), new RSTile(3458, 3443, 0),
			new RSTile(3458, 3437, 0), new RSTile(3460, 3431, 0),
			new RSTile(3458, 3425, 0), new RSTile(3462, 3420, 0),
			new RSTile(3467, 3416, 0), new RSTile(3467, 3410, 0),
			new RSTile(3467, 3404, 0), new RSTile(3475, 3361, 0),
			new RSTile(3467, 3401, 0), new RSTile(3470, 3395, 0),
			new RSTile(3472, 3389, 0), new RSTile(3476, 3384, 0),
			new RSTile(3480, 3379, 0), new RSTile(3486, 3376, 0),
			new RSTile(3492, 3376, 0), new RSTile(3497, 3380, 0) };

	final static RSTile[] pathToBarrows = { new RSTile(3522, 3285, 0),
			new RSTile(3522, 3281, 0), new RSTile(3526, 3280, 0),
			new RSTile(3530, 3280, 0), new RSTile(3534, 3280, 0),
			new RSTile(3536, 3284, 0), new RSTile(3537, 3288, 0),
			new RSTile(3540, 3292, 0), new RSTile(3544, 3295, 0),
			new RSTile(3544, 3299, 0), new RSTile(3547, 3302, 0),
			new RSTile(3551, 3305, 0), new RSTile(3553, 3309, 0),
			new RSTile(3557, 3309, 0), new RSTile(3598, 3342, 0),
			new RSTile(3558, 3310, 0), new RSTile(3561, 3313, 0),
			new RSTile(3565, 3313, 0), new RSTile(3565, 3308, 0),
			new RSTile(3565, 3304, 0), new RSTile(3565, 3300, 0),
			new RSTile(3565, 3296, 0), new RSTile(3565, 3292, 0), };

	final static RSTile[] toBank = { new RSTile(3422, 3484, 0),
			new RSTile(3426, 3484, 0), new RSTile(3430, 3484, 0),
			new RSTile(3434, 3484, 0), new RSTile(3438, 3484, 0),
			new RSTile(3442, 3484, 0), new RSTile(3445, 3481, 0),
			new RSTile(3449, 3479, 0), new RSTile(3453, 3479, 0),
			new RSTile(3457, 3479, 0), new RSTile(3500, 3471, 0),
			new RSTile(3460, 3479, 0), new RSTile(3464, 3478, 0),
			new RSTile(3468, 3477, 0), new RSTile(3472, 3474, 0),
			new RSTile(3476, 3474, 0), new RSTile(3480, 3476, 0),
			new RSTile(3484, 3476, 0), new RSTile(3488, 3478, 0),
			new RSTile(3492, 3480, 0), new RSTile(3496, 3480, 0),
			new RSTile(3500, 3480, 0), new RSTile(3542, 3488, 0),
			new RSTile(3502, 3480, 0), new RSTile(3506, 3480, 0),
			new RSTile(3510, 3480, 0), };

	public static final RSTile[] pathFromEcto = { new RSTile(3660, 3522, 0),
			new RSTile(3659, 3527, 0), new RSTile(3657, 3532, 0),
			new RSTile(3652, 3534, 0), new RSTile(3647, 3534, 0),
			new RSTile(3642, 3534, 0), new RSTile(3637, 3535, 0),
			new RSTile(3632, 3535, 0), new RSTile(3627, 3535, 0),
			new RSTile(3622, 3535, 0), new RSTile(3617, 3535, 0),
			new RSTile(3612, 3536, 0), new RSTile(3607, 3536, 0),
			new RSTile(3602, 3537, 0), new RSTile(3597, 3537, 0),
			new RSTile(3592, 3537, 0), new RSTile(3587, 3535, 0),
			new RSTile(3582, 3533, 0), new RSTile(3577, 3532, 0),
			new RSTile(3572, 3530, 0), new RSTile(3567, 3528, 0),
			new RSTile(3562, 3527, 0), new RSTile(3557, 3526, 0),
			new RSTile(3552, 3526, 0), new RSTile(3547, 3526, 0),
			new RSTile(3542, 3527, 0), new RSTile(3537, 3527, 0),
			new RSTile(3532, 3526, 0), new RSTile(3527, 3526, 0),
			new RSTile(3522, 3526, 0), new RSTile(3519, 3522, 0),
			new RSTile(3516, 3518, 0), new RSTile(3513, 3513, 0),
			new RSTile(3510, 3509, 0), new RSTile(3507, 3505, 0),
			new RSTile(3504, 3501, 0), new RSTile(3501, 3497, 0),
			new RSTile(3499, 3492, 0), new RSTile(3498, 3487, 0),
			new RSTile(3503, 3485, 0), new RSTile(3506, 3481, 0),
			new RSTile(3510, 3478, 0) };

	public static boolean isInBarrows() {
		return Var.barrowsArea.contains(Player.getPosition());
	}

	static boolean isUndergroundCanifis() {
		RSTile lol[] = { new RSTile(3404, 9900, 0), new RSTile(3409, 9883, 0),
				new RSTile(3422, 9889, 0), new RSTile(3439, 9896, 0) };
		for (RSTile t : lol) {
			if (t.distanceTo(Player.getRSPlayer()) < 30)
				return true;
		}
		return false;
	}

	static boolean canWalkToBank() {
		for (RSTile t : toBank) {
			if (t.distanceTo(Player.getRSPlayer()) < 10)
				return true;
		}
		return false;
	}

	static boolean canWalkToVarrock() {
		for (RSTile t : pathFromVarrock) {
			if (t.distanceTo(Player.getRSPlayer()) < 10)
				return true;
		}
		return false;
	}

	public static void walkPath(RSTile[] fs) {
		if (fs.length == 0)
			return;
		for (int i = 0; i < fs.length; i++) {
			RSTile t = Walking.invertPath(fs)[i];
			if (t.distanceTo(Player.getPosition()) < 30
					&& Projection.isInMinimap(Projection.tileToMinimap(t))) {
				Walking.walkTo(t);
				if (i == 0) {
					while (Player.isMoving() && !Player.getPosition().equals(t)) {
						Var.status = "Waiting until at target";
						General.sleep(20, 50);
					}
				} else {
					while (Player.isMoving() && !Player.getPosition().equals(t)
							&& Player.getPosition().distanceTo(t) > 5) {
						Var.status = "Waiting until near target";
						Walking.blindWalkTo(t);
						General.sleep(20, 50);
					}
				}
				return;
			}
		}
	}

	public static void goFromVarrock() {
		if (new RSTile(3412, 3488, 0).distanceTo(Player.getPosition()) < 20
				&& PathFinding.canReach(new RSTile(3412, 3488, 0), false)) {
			if (Prayer.getPoints() < Prayer.getLevel()) {
				RSObject[] altar = Objects.getAt(new RSTile(3416, 3488, 0));
				if (altar.length > 0) {
					if (PathFinding.canReach(altar[0], true)) {
						GeneralMethods.clickObject(altar[0], "Pray-at", true,
								false);
						while (Player.isMoving())
							General.sleep(100);
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								return Prayer.getPoints() == Prayer.getLevel();
							}
						}, 2000);
					}
				}
			} else {
				final RSObject[] door = Objects
						.getAt(new RSTile(3408, 3488, 0));
				if (door.length > 0) {
					GeneralMethods.clickObject(door[0], "Open", true, false);
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							return door.length == 0;
						}
					}, 2000);
				}
			}
			return;
		}
		if (canWalkToBank()) {
			walkPath(toBank);
		} else {
			if (isUndergroundCanifis()) {
				if (PathFinding.canReach(new RSTile(3439, 9896, 0), false)) {
					RSObject[] barrier = Objects
							.getAt(new RSTile(3440, 9886, 0));
					if (barrier.length > 0) {
						if (barrier[0].isOnScreen()) {
							GeneralMethods.clickObject(barrier[0], "Pass",
									true, true);
						} else {
							Walking.walkTo(barrier[0]);
						}
					}
				} else {
					if (PathFinding.canReach(new RSTile(3422, 9889, 0), false)) {
						RSObject[] gate = Objects.getAt(new RSTile(3431, 9897,
								0));
						if (gate.length > 0) {
							if (gate[0].getPosition().distanceTo(
									Player.getRSPlayer()) < 10)
								GeneralMethods.clickObject(gate[0], "Open",
										true, false);
							else {
								Walking.blindWalkTo(gate[0]);
							}
						}
					} else {
						RSObject[] gate = Objects.getAt(new RSTile(3405, 9895,
								0));
						if (gate.length > 0) {
							GeneralMethods.clickObject(gate[0], "Open", true,
									false);
						}
					}
				}
			} else {
				if (new RSTile(3408, 3488, 0).distanceTo(Player.getPosition()) < 20
						&& Var.recharge
						&& Prayer.getPoints() < Prayer.getLevel()
						&& (PathFinding.canReach(new RSTile(3408, 3488, 0),
								false) || PathFinding.canReach(new RSTile(3412,
								3488, 0), false))) {
					RSObject[] altar = Objects.getAt(new RSTile(3416, 3488, 0));
					if (altar.length > 0) {
						if (!PathFinding.canReach(altar[0], true)) {

							final RSObject[] door = Objects.getAt(new RSTile(
									3408, 3488, 0));
							if (door.length > 0) {
								if (door[0].getPosition().distanceTo(
										Player.getPosition()) < 5) {
									GeneralMethods.clickObject(door[0], "Open",
											true, false);
									Timing.waitCondition(new Condition() {
										@Override
										public boolean active() {
											return door.length == 0;
										}
									}, 2000);
								} else {
									Walking.blindWalkTo(door[0]);
								}
							}
						}
					}
				} else {
					Walking.setWalkingTimeout(500);
					RSObject[] trapdoor = Objects.getAt(new RSTile(3405, 3507,
							0));
					if (trapdoor.length > 0
							&& trapdoor[0].getPosition().distanceTo(
									Player.getPosition()) < 20) {
						if (trapdoor[0].getPosition().distanceTo(
								Player.getRSPlayer()) < 10) {
							GeneralMethods.clickObject(trapdoor[0], "", true,
									false);
						} else {
							Walking.blindWalkTo(trapdoor[0]);
						}
					} else {
						RSTile after = new RSTile(3321, 3468, 0);
						if (after.distanceTo(Player.getRSPlayer()) < 10
								&& !PathFinding.canReach(after, false)) {
							RSObject[] door = Objects.getAt(new RSTile(3319,
									3467, 0));
							if (door.length > 1
									&& door[0].getPosition().distanceTo(
											Player.getPosition()) < 10) {
								GeneralMethods.clickObject(door[0], "Open",
										true, false);
							}
						} else {
							if (canWalkToVarrock())
								walkPath(pathFromVarrock);
							else {
								RSItem[] teletab = Inventory.find(8007);
								if (teletab.length > 0) {
									if (teletab[0].click("")) {
										RSTile here = Player.getPosition();
										Timing.waitCondition(new Condition() {

											@Override
											public boolean active() {
												return Player.getAnimation() != -1;
											}
										}, 3000);
										General.sleep(200);
										Timing.waitCondition(new Condition() {

											@Override
											public boolean active() {
												return Player.getAnimation() == -1;
											}
										}, 7000);
										if (Player.getPosition() != here)
											Var.trips++;
									}
								}
							}

						}
					}
				}
			}
		}
	}

	public static boolean goViaSwamp() {
		Walking.setControlClick(true);
		Walking.setWalkingTimeout(500);
		Mouse.setSpeed(General.random(100, 130));
		if (Game.getRunEnergy() > General.random(9, 13) && !Game.isRunOn()
				&& Rooms.getRoom() == null && !Pathing.isInBarrows()
				&& !Tunnel.inCrypt())
			Options.setRunOn(true);
		if (!warning()) {
			if (isInBarrows())
				return true;
			if (isInBoat()) {
				General.sleep(500);
				return false;
			}
			if (isFromGateToBoat()) {
				if (canEnterBoat())
					enterBoat();
				else
					walkPath(pathToBoat);
				General.sleep(500);
			} else if (isFromBankToGate()) {
				if (canOpenGate())
					openGate();
				else
					walkPath(pathToGate);
			}
		}
		return false;
	}

	public static void goToBank() {
		Mouse.setSpeed(General.random(100, 130));
		switch (Var.bankPath) {
		case ECTOPHIAL:
			walkFromEcto();
			break;
		case HOUSE:
			useHouse();
			break;
		case VARROCK:
			goFromVarrock();
			break;
		case BURGH_DE_ROTT:
			walkPath(Walking.invertPath(pathFromBurghToBarrows));
			break;
		}
	}

	public static final RSTile[] pathFromBurghToBarrows = {
			new RSTile(3496, 3212, 0), new RSTile(3495, 3217, 0),
			new RSTile(3494, 3222, 0), new RSTile(3489, 3227, 0),
			new RSTile(3485, 3231, 0), new RSTile(3485, 3236, 0),
			new RSTile(3485, 3241, 0), new RSTile(3484, 3246, 0),
			new RSTile(3485, 3251, 0), new RSTile(3488, 3255, 0),
			new RSTile(3489, 3260, 0), new RSTile(3488, 3265, 0),
			new RSTile(3490, 3270, 0), new RSTile(3493, 3274, 0),
			new RSTile(3496, 3278, 0), new RSTile(3500, 3281, 0),
			new RSTile(3505, 3283, 0), new RSTile(3510, 3283, 0),
			new RSTile(3515, 3282, 0), new RSTile(3520, 3281, 0),
			new RSTile(3525, 3279, 0), new RSTile(3530, 3281, 0),
			new RSTile(3535, 3281, 0), new RSTile(3539, 3284, 0),
			new RSTile(3539, 3289, 0), new RSTile(3538, 3294, 0),
			new RSTile(3537, 3299, 0), new RSTile(3538, 3304, 0),
			new RSTile(3542, 3307, 0), new RSTile(3546, 3310, 0),
			new RSTile(3551, 3312, 0), new RSTile(3556, 3314, 0),
			new RSTile(3561, 3316, 0), new RSTile(3564, 3312, 0),
			new RSTile(3565, 3307, 0), new RSTile(3566, 3302, 0),
			new RSTile(3567, 3297, 0), new RSTile(3567, 3292, 0),
			new RSTile(3565, 3287, 0) };

	public static void pray() {
		RSObject[] altar = Objects.find(30, "Altar");
		if (altar.length > 0) {
			if (PathFinding.canReach(altar[0].getPosition(), true)) {
				GeneralMethods.clickObject(altar[0], "Pray", true, false);
			} else {
				RSObject door = getClosestDoor(altar[0]);
				if (door != null) {
					GeneralMethods.clickObject(door, "Open", true, false);
				}
			}
		}
	}

	public static RSObject getClosestDoor(RSObject target) {
		RSObject[] doors = Objects.find(30, "Door");

		int dist = 99;
		RSObject finalL = null;
		if (doors.length > 0) {
			for (RSObject door : doors) {
				if (door.getPosition().distanceTo(target.getPosition()) < dist) {
					if (PathFinding.canReach(door, true)) {
						dist = door.getPosition().distanceTo(
								target.getPosition());
						finalL = door;
					}
				}
			}
		}
		return finalL;
	}

	static RSObject getFocus(RSObject portal) {
		RSObject[] wave = Objects.findNearest(30, "Teleportation focus");
		int distance = 444;
		RSObject focus = null;
		if (wave.length > 0) {
			if (wave.length == 1)
				return wave[0];
			for (RSObject f : wave) {
				if (f.getPosition().distanceTo(portal) < distance) {
					distance = f.getPosition().distanceTo(portal);
					focus = f;
				}
			}
		}
		return focus;
	}

	public static void useHouse() {
		if (Game.getRunEnergy() > General.random(9, 13) && !Game.isRunOn()
				&& Rooms.getRoom() == null && !Pathing.isInBarrows()
				&& !Tunnel.inCrypt())
			Options.setRunOn(true);
		if (isInHouse()) {
			if (Prayer.getPoints() < Prayer.getLevel() && Var.recharge) {
				pray();
			} else {
				RSObject[] portal = Objects.findNearest(30, "Kharyrll Portal");
				if (portal.length > 0) {
					RSObject wave = getFocus(portal[0]);
					if (wave != null && PathFinding.canReach(wave, true)) {
						GeneralMethods.clickObject(portal[0], "Enter", false,
								false);
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return !isInHouse();
							}
						}, 10000);

					} else {
						RSObject door = getClosestDoor(portal[0]);
						if (door != null) {
							GeneralMethods.clickObject(door, "Open", true,
									false);
						}
					}
				}

			}
		} else {
			if (new RSTile(3510, 3478, 0).distanceTo(Player.getPosition()) < 30) {
				walkPath(pubToBank);
			} else {
				RSItem[] teletab = Inventory.find(8013);
				if (teletab.length > 0) {
					if (teletab[0].click("")) {
						RSTile here = Player.getPosition();
						General.sleep(500);
						if (Player.getAnimation() != -1) {
							Timing.waitCondition(new Condition() {

								@Override
								public boolean active() {
									return Player.getAnimation() == -1;
								}
							}, 7000);
							if (Player.getPosition() != here)
								Var.trips++;
							while (houseScreen())
								General.sleep(50);
						}
					}
				}
			}
		}
	}

	static void walkFromEcto() {
		if (Game.getRunEnergy() > General.random(9, 13) && !Game.isRunOn()
				&& Rooms.getRoom() == null && !Pathing.isInBarrows()
				&& !Tunnel.inCrypt())
			Options.setRunOn(true);
		if (Prayer.getPoints() == Prayer.getLevel()
				&& Restocking.canWalkToAltar()) {
			RSItem[] ectophial = Inventory.find(Var.ECTOPHIAL);
			if (ectophial.length > 0) {
				if (ectophial[0].click("")) {
					RSTile here = Player.getPosition();
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							return Player.getAnimation() != -1;
						}
					}, 3000);
					General.sleep(200);
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return Player.getAnimation() == -1;
						}
					}, 7000);
					if (Player.getPosition() != here)
						Var.trips++;
				}
			}
		}
		if (isFromEctoToBank() || Restocking.canWalkToAltar()) {
			if (Var.recharge && Prayer.getPoints() < Prayer.getLevel()) {
				Restocking.restorePrayerAtLumbridge();
			} else {
				if (Inventory.getCount(Var.EMPTY_ECTOPHIAL) > 0)
					General.sleep(1000);
				else {
					walkPath(pathFromEcto);
				}
			}
		} else {
			RSItem[] ectophial = Inventory.find(Var.ECTOPHIAL);
			if (ectophial.length > 0) {
				if (ectophial[0].click("")) {
					RSTile here = Player.getPosition();
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							return Player.getAnimation() != -1;
						}
					}, 3000);
					General.sleep(200);
					Timing.waitCondition(new Condition() {

						@Override
						public boolean active() {
							return Player.getAnimation() == -1;
						}
					}, 7000);
					if (Player.getPosition() != here)
						Var.trips++;
				}
			}
		}
	}

	static boolean isFromEctoToBank() {
		for (RSTile t : pathFromEcto) {
			if (t.distanceTo(Player.getRSPlayer()) < 15)
				return true;
		}
		return false;
	}

	public static void getToBarrows() {
		Mouse.setSpeed(General.random(100, 130));
		Walking.setWalkingTimeout(500);
		if (isInBarrows()) {
			return;
		}
		if (!isFromBoatToBarrows()) {
			switch (Var.barrowsPath) {
			case SWAMP:
				goViaSwamp();
				break;
			case SHORTCUT:
				goViaShortcut();
				break;
			case BURGH_DE_ROTT:
				walkPath(Walking.invertPath(pathFromBurghToBarrows));
				break;
			case FAIRY_RINGS:
				goFromEdge();
				break;
			}
		}
		if (isFromBoatToBarrows()) {
			Var.status = "Going to barrows";
			walkPath(pathToBarrows);
		}
	}

	static void goViaShortcut() {
		Walking.setWalkingTimeout(500);
		Mouse.setSpeed(General.random(100, 130));
		Var.status = "Using shortcut to get to barrows";
		Mouse.setSpeed(General.random(100, 150));
		if (isNearTrapDoor()) {
			RSObject[] trapdoor = Objects.getAt(new RSTile(3495, 3464, 0));
			if (trapdoor.length > 0
					&& Player.getPosition().distanceTo(trapdoor[0]) < 15) {
				GeneralMethods.clickObject(trapdoor[0], "Open", true, false);
			} else {
				Walking.walkTo(new RSTile(3506, 3469, 0));
			}
		} else {
			if (isUnderground()) {
				RSObject[] door = Objects.getAt(new RSTile(3500, 9812, 0));
				if (door.length > 0
						&& Player.getPosition().distanceTo(door[0]) < 20) {
					GeneralMethods.clickObject(door[0], "Open", true, false);
					for (int fail = 0; fail < 50
							&& !Player.getPosition().equals(
									new RSTile(3509, 3449, 0)); fail++) {
						General.sleep(25, 50);
					}
				} else {
					if (new RSTile(3477, 9845, 0).distanceTo(Player
							.getRSPlayer()) < 15
							&& PathFinding.canReach(new RSTile(3477, 9845, 0),
									false)) {
						RSObject[] wall = Objects.getAt(new RSTile(3480, 9837,
								0));
						if (wall.length > 0) {
							GeneralMethods.clickObject(wall[0], "Search", true,
									true);
							for (int i = 0; i < 20
									&& !Player.getPosition().equals(
											new RSTile(3480, 9836)); i++) {
								General.sleep(100, 150);
							}
						}
					} else {
						walkPath(pathUnderground);
					}
				}
			} else {
				if (new RSTile(3505, 3437, 0).distanceTo(Player.getRSPlayer()) < 15
						&& PathFinding.canReach(new RSTile(3505, 3437, 0),
								false)) {
					RSObject[] bridge = Objects
							.getAt(new RSTile(3502, 3431, 0));
					if (bridge.length > 0
							&& Player.getPosition().distanceTo(bridge[0]) < 15) {
						GeneralMethods.clickObject(bridge[0], "Cross-bridge",
								true, false);
						General.sleep(3000);
						if (!PathFinding.canReach(new RSTile(3505, 3437, 0),
								false)) {
							for (int fail = 0; fail < 30
									&& !Player.getPosition().equals(
											new RSTile(3503, 3425, 0)); fail++) {
								General.sleep(100, 200);
							}
						}
					} else {
						GeneralMethods.enableRun();
						Walking.walkTo(new RSTile(3505, 3437, 0));
						GeneralMethods.disableRun();
					}
				} else {
					if (canEnterBoat()) {
						enterBoat();
					} else {
						GeneralMethods.enableRun();
						walkPath(pathToBoatFromShortcut);
						GeneralMethods.disableRun();
					}
				}
			}
		}
	}

	static boolean isUnderground() {
		for (RSTile r : pathUnderground) {
			if (r.distanceTo(Player.getPosition()) < 15
					|| new RSTile(3477, 9845, 0).distanceTo(Player
							.getRSPlayer()) < 15)
				return true;
		}
		return false;

	}

	static RSTile[] pathToBoatFromShortcut = { new RSTile(3495, 3416),
			new RSTile(3493, 3407), new RSTile(3495, 3396),
			new RSTile(3496, 3386) };

	static boolean canWalkUnderground() {
		for (RSTile r : pathToBoatFromShortcut) {
			if (r.distanceTo(Player.getPosition()) < 15)
				return true;
		}
		return false;
	}

	static RSTile[] pathUnderground = new RSTile[] { new RSTile(3483, 9826, 0),
			new RSTile(3492, 9817, 0), new RSTile(3499, 9811, 0) };

	static boolean isNearTrapDoor() {
		return new RSTile(3505, 3468, 0).distanceTo(Player.getRSPlayer()) < 15
				|| new RSTile(3495, 3464, 0).distanceTo(Player.getRSPlayer()) < 15;
	}

	public static void enterBoat() {
		RSObject[] boat = Objects.findNearest(9999, 6970);
		if (boat.length > 0) {
			if (boat[0].isOnScreen()) {
				if (boat[0].click("Board")) {
					General.sleep(1000);
					while (Player.isMoving())
						General.sleep(100);
				}
			} else {
				Walking.walkTo(boat[0].getPosition());
				while (Player.isMoving() && boat.length > 0
						&& !boat[0].isOnScreen())
					General.sleep(100);
			}
		}
	}

	static boolean isInBoat() {
		return Interfaces.get(322, 0) != null;
	}

	public static boolean canEnterBoat() {
		RSObject[] boat = Objects.findNearest(9999, 6970);
		return !isInBoat() && boat.length > 0
				&& Player.getPosition().distanceTo(boat[0].getPosition()) < 20;
	}

	private static void openGate() {
		RSObject[] gate = Objects.getAt(new RSTile(3443, 3458, 0));
		if (gate.length > 0) {
			if (gate[0].isOnScreen()) {
				if (gate[0].click("Open")) {
					General.sleep(1000);
					while (Player.isMoving())
						General.sleep(100);
				}
			} else {
				Walking.walkTo(gate[0].getPosition());
				while (Player.isMoving() && gate.length > 0
						&& !gate[0].isOnScreen())
					General.sleep(100);
			}
		}
	}

	static boolean warning() {
		if (Interfaces.get(580, 17) != null) {
			Interfaces.get(580, 17).click("");
			General.sleep(1000);
			return true;
		}
		return false;
	}

	static boolean canOpenGate() {
		return new RSTile(3443, 3459, 0).distanceTo(Player.getPosition()) < 10
				&& !PathFinding.canReach(new RSTile(3444, 3454, 0), false)
				&& Objects.getAt(new RSTile(3443, 3458, 0)).length > 0;
	}

	static boolean isFromGateToBoat() {
		for (RSTile t : pathToBoat) {
			if (t.distanceTo(Player.getPosition()) < 15
					&& PathFinding.canReach(t, false)) {
				return true;
			}
		}
		return false;
	}

	static boolean isFromBoatToBarrows() {
		for (RSTile t : pathToBarrows) {
			if (t.distanceTo(Player.getPosition()) < 15)
				return true;
		}
		return false;
	}

	static boolean isFromBankToGate() {
		for (RSTile t : pathToGate) {
			if (t.distanceTo(Player.getPosition()) < 15)
				return true;
		}
		return false;
	}

	static boolean isBuildingInterfaceOpen() {
		if (Game.getSetting(262) == 14)
			return true;
		return false;
	}

	static void setBuildingMode() {
		if (Interfaces.get(398, 19) == null) {
			if (GameTab.getOpen() != GameTab.TABS.OPTIONS) {
				Keyboard.pressFunctionKey(10);
			}
			Interfaces.get(261, 35).click("Open");
			for (int fsafe = 0; fsafe < 20 && Interfaces.get(398, 19) == null; fsafe++) {
				General.sleep(20, 30);
			}
		}
		if (Interfaces.get(398, 19) != null) {
			if (!Interfaces.get(398, 19).click("Ok")) {
				setBuildingMode();
			} else {
				if (Banking.isPinScreenOpen()) {
					Banking.inPin();
				}
			}
		} else {
			setBuildingMode();
		}
	}

	public static boolean buildingMode() {
		return Game.getSetting(261) == 1;
	}

	public static boolean walkFromVarrock() {
		return canWalkToVarrock() || canWalkToBank();
	}

	public static void turnOnBuildingMode() {
		if (buildingMode())
			return;
		if (isBuildingInterfaceOpen())
			setBuildingMode();
	}

	public static boolean isCloseToBarrows() {
		for (RSTile r : Var.barrowsArea.getTiles()) {
			if (Player.getPosition().distanceTo(r) < 5) {
				return true;
			}
		}
		return false;
	}

	public static void walkToCenterOfBarrows() {
		Walking.blindWalkTo(new RSTile(3563, 3288, 0));
	}

	public static boolean houseScreen() {
		return Screen.getColorAt(77, 145).equals(new Color(0, 0, 0));
	}

	static void goFromEdge() {
		if (Pathing.canEnterBoat()) {
			Pathing.enterBoat();
		} else {
			if (canWalkToBoatFromRing()) {
				Pathing.walkPath(pathFromRing);
			} else {
				if (new RSTile(2412, 4434, 0).distanceTo(Player.getRSPlayer()) < 10) {
					if (Interfaces.get(426, 33) != null) {
						if (FairyRing.SetFairyCombination("b", "k", "r")) {
							if (Interfaces.get(426, 33).click("Ok")) {
								Timing.waitCondition(new Condition() {
									@Override
									public boolean active() {
										return Interfaces.get(426, 33) == null;
									}
								}, 3000);
								General.sleep(500);
							}
						}
					} else {
						RSObject[] ring = Objects.getAt(new RSTile(2412, 4434,
								0));
						if (ring.length > 0) {
							GeneralMethods.clickObject(ring[0], "Use", true,
									false);
						}
					}
				} else {
					if (isAtEdge()) {
						RSObject[] ring = Objects.getAt(new RSTile(3129, 3496,
								0));
						if (ring.length > 0
								&& ring[0].getPosition().distanceTo(
										Player.getRSPlayer()) < 5) {
							if (Equipment.getCount(772) > 0) {
								GeneralMethods.clickObject(ring[0], "Use",
										true, false);
							} else {
								RSItem[] staff = Inventory.find(772);
								if (staff.length > 0) {
									if (staff[0].click("Wield")) {
										General.sleep(1000);
									}
								}
							}
						} else {
							Pathing.walkPath(pathToRing);
						}
					} else {
						if (Pathing.isInHouse()
								|| Objects.find(999, 4525).length > 0) {
							if (Prayer.getPoints() < Prayer.getLevel()
									&& Var.recharge) {
								Pathing.pray();
							} else {
								if (Interfaces.get(234, 1) != null) {
									Interfaces.get(234, 1).click("");
								} else {
									RSObject[] glory = Objects.findNearest(30,
											13523);
									if (glory.length > 0) {
										if (PathFinding.canReach(glory[0],
												false)) {
											GeneralMethods.clickObject(
													glory[0], "Rub", false,
													false);
										} else {
											RSObject door = Pathing
													.getClosestDoor(glory[0]);
											if (door != null) {
												GeneralMethods.clickObject(
														door, "Open", true,
														false);
											}
										}
									}
								}

							}
						} else {
							RSItem[] teletab = Inventory.find(8013);
							if (teletab.length > 0) {
								if (teletab[0].click("")) {
									RSTile here = Player.getPosition();
									Timing.waitCondition(new Condition() {

										@Override
										public boolean active() {
											return Player.getAnimation() != -1;
										}
									}, 3000);
									General.sleep(1200);
									Timing.waitCondition(new Condition() {

										@Override
										public boolean active() {
											return Player.getAnimation() == -1;
										}
									}, 7000);
									if (Player.getPosition() != here)
										Var.trips++;
								}
							}
						}
					}
				}
			}
		}
	}

	static RSTile[] pathFromRing = { new RSTile(3469, 3430, 0),
			new RSTile(3473, 3430, 0), new RSTile(3477, 3428, 0),
			new RSTile(3479, 3424, 0), new RSTile(3478, 3420, 0),
			new RSTile(3475, 3417, 0), new RSTile(3472, 3414, 0),
			new RSTile(3469, 3411, 0), new RSTile(3468, 3407, 0),
			new RSTile(3468, 3403, 0), new RSTile(3469, 3399, 0),
			new RSTile(3472, 3395, 0), new RSTile(3482, 3353, 0),
			new RSTile(3474, 3393, 0), new RSTile(3478, 3391, 0),
			new RSTile(3476, 3387, 0), new RSTile(3476, 3383, 0),
			new RSTile(3479, 3380, 0), new RSTile(3481, 3376, 0),
			new RSTile(3485, 3376, 0), new RSTile(3489, 3376, 0),
			new RSTile(3493, 3379, 0), new RSTile(3497, 3380, 0),
			new RSTile(3501, 3380, 0), };

	static boolean canWalkToBoatFromRing() {
		for (RSTile t : pathFromRing) {
			if (t.distanceTo(Player.getRSPlayer()) < 15)
				return true;
		}
		return false;
	}

	static boolean isAtEdge() {
		for (RSTile t : pathToRing) {
			if (t.distanceTo(Player.getRSPlayer()) < 15)
				return true;
		}
		return false;
	}

	static RSTile[] pathToRing = { new RSTile(3087, 3496, 0),
			new RSTile(3090, 3500, 0), new RSTile(3094, 3500, 0),
			new RSTile(3098, 3500, 0), new RSTile(3102, 3500, 0),
			new RSTile(3106, 3501, 0), new RSTile(3110, 3502, 0),
			new RSTile(3114, 3501, 0), new RSTile(3118, 3504, 0),
			new RSTile(3120, 3508, 0), new RSTile(3123, 3511, 0),
			new RSTile(3127, 3513, 0), new RSTile(3130, 3517, 0),
			new RSTile(3134, 3517, 0), new RSTile(3136, 3513, 0),
			new RSTile(3136, 3509, 0), new RSTile(3134, 3505, 0),
			new RSTile(3134, 3501, 0), new RSTile(3131, 3497, 0),
			new RSTile(3129, 3493, 0) };

}
