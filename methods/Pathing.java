package scripts.Barrows.methods;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Options;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Prayer;

public class Pathing {

	final static int[] ALTARS = {};
	final static int[] PORTALS = {};

	public enum PathBarrows {
		SWAMP, SHORTCUT
	}

	public enum PathBank {
		HOUSE, ECTOPHIAL, VARROCK
	}

	static boolean isInHouse() {
		return Player.getPosition().getX() > 10000;
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

	public static void goFromVarrock() {
		if (canWalkToBank())
			Walking.walkPath(toBank);
		else {
			if (isUndergroundCanifis()) {
				if (PathFinding.canReach(new RSTile(3439, 9896, 0), false)) {
					RSObject[] barrier = Objects
							.getAt(new RSTile(3440, 9886, 0));
					if (barrier.length > 0) {
						DynamicClicking.clickRSTile(new RSTile(3440, 9886, 0),
								1);
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return !isUndergroundCanifis();
							}
						}, 3000);
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
				RSObject[] trapdoor = Objects.getAt(new RSTile(3405, 3507, 0));
				if (trapdoor.length > 0
						&& trapdoor[0].getPosition().distanceTo(
								Player.getRSPlayer()) < 20) {
					GeneralMethods.clickObject(trapdoor[0], "", true, false);
				} else {
					RSTile after = new RSTile(3321, 3468, 0);
					if (after.distanceTo(Player.getRSPlayer()) < 10
							&& !PathFinding.canReach(after, false)) {
						RSObject[] door = Objects.getAt(new RSTile(3319, 3467,
								0));
						if (door.length > 1) {
							GeneralMethods.clickObject(door[0], "Open", true,
									false);
						}
					} else {
						if (canWalkToVarrock())
							Walking.walkPath(pathFromVarrock);
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

	private static boolean goViaSwamp() {
		Walking.control_click = true;
		Walking.walking_timeout = 500;
		Mouse.setSpeed(General.random(100, 130));
		if (Game.getRunEnergy() > General.random(9, 13) && !Game.isRunOn())
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
					Walking.walkPath(pathToBoat);
				General.sleep(500);
			} else if (isFromBankToGate()) {
				if (canOpenGate())
					openGate();
				else
					Walking.walkPath(pathToGate);
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
		}
	}

	static void useHouse() {
		if (Game.getRunEnergy() > General.random(9, 13) && !Game.isRunOn())
			Options.setRunOn(true);
		if (isInHouse()) {
			if (buildingMode())
				turnOnBuildingMode();
			else {
				if (Prayer.getPoints() < Prayer.getLevel()) {
					// TODO CHANGE INTERACTION STRINGS
					RSObject[] altar = Objects.findNearest(30, ALTARS);
					if (altar.length > 0) {
						GeneralMethods.clickObject(altar[0], "Pray", false,
								false);
					}
				} else {
					RSObject[] portal = Objects.findNearest(30, PORTALS);
					if (portal.length > 0) {
						GeneralMethods.clickObject(portal[0], "", false, false);
						Timing.waitCondition(new Condition() {

							@Override
							public boolean active() {
								return !isInHouse();
							}
						}, 10000);
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

	static void walkFromEcto() {
		if (Game.getRunEnergy() > General.random(9, 13) && !Game.isRunOn())
			Options.setRunOn(true);
		if (isFromEctoToBank()) {
			if (Var.recharge && Prayer.getPoints() < Prayer.getLevel()) {
				// TODO TELE TO LUMBM
			} else {
				if (Inventory.getCount(Var.EMPTY_ECTOPHIAL) > 0)
					General.sleep(1000);
				else {
					Walking.walkPath(pathFromEcto);
				}
			}
		} else {
			if (Var.recharge && Prayer.getPoints() < Prayer.getLevel()) {
				Restocking.restorePrayerAtLumbridge();
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
		Walking.walking_timeout = 1000;
		if (isInBarrows()) {
			System.out.println("Return");
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
			}
		}
		if (isFromBoatToBarrows()) {
			System.out.println("Going to barrows");
			Walking.walkPath(pathToBarrows);
		}
	}

	static void goViaShortcut() {
		Walking.walking_timeout = 2500;
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
						RSObject[] wall = Objects.getAt(new RSTile(3480, 9836,
								0));
						if (wall.length > 0) {
							GeneralMethods.clickObject(wall[0], "Search", true,
									true);
						}
					} else {
						Walking.walkPath(pathUnderground);
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
						Walking.walkPath(pathToBoatFromShortcut);
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

	private static void enterBoat() {
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

	static boolean canEnterBoat() {
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
			if (t.distanceTo(Player.getPosition()) < 5
					&& PathFinding.canReach(t, false)) {
				Walking.walkTo(t);
				return true;
			}
		}
		return false;
	}

	static boolean isFromBoatToBarrows() {
		for (RSTile t : pathToBarrows) {
			if (t.distanceTo(Player.getPosition()) < 5
					&& PathFinding.canReach(t, false))
				return true;
		}
		return false;
	}

	static boolean isFromBankToGate() {
		for (RSTile t : pathToGate) {
			if (t.distanceTo(Player.getPosition()) < 5
					&& PathFinding.canReach(t, false))
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

	static boolean buildingMode() {
		return Game.getSetting(261) == 1;
	}

	static void turnOnBuildingMode() {
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
}
