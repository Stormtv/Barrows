package scripts.Barrows.methods;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.types.enums.Armor;

public class Repairing {

	private static RSTile[] pathToBob = { new RSTile(3224, 3218, 0),
			new RSTile(3234, 3218, 0), new RSTile(3235, 3210, 0),
			new RSTile(3234, 3203, 0) };

	private static boolean clickInterface(int parent, int child) {
		final RSInterface chat = Interfaces.get(parent, child);
		if (chat != null) {
			chat.click("");
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					return chat == null;
				}
			}, 2000);
		}
		return false;
	}

	private static int getCashAmount() {
		if (minCash == 0)
			return 300000;
		else
			return minCash;
	}

	public static void repair() {
		if (Inventory.getCount(995) > (getCashAmount() - 1)) {
			if (isInLumbridge()) {
				RSNPC[] bob = NPCs.findNearest("Bob");
				if (bob.length > 0
						&& bob[0].getPosition()
								.distanceTo(Player.getPosition()) < 5) {
					if (PathFinding.canReach(bob[0], false)) {
						if (!clickInterface(230, 2)) {
							if (!clickInterface(228, 1)) {
								if (bob[0].isOnScreen()) {
									int armorID = Armor.getCurrentDegraded();
									RSItem[] armour = Inventory.find(armorID);
									if (armour.length > 0) {
										if (armour[0].click("Use")) {
											if (bob[0].click("Use")) {
												General.sleep(1000);
												while (Player.isMoving())
													General.sleep(100);
												General.sleep(1000);
											}
										}
									} else {
										Equipment.remove(armorID);
									}
								}
							} else {
								Walking.walkTo(bob[0]);
								General.sleep(1000);
							}
						}
					} else {
						Doors.handleDoorAt(new RSTile(3234, 3203, 0), true);
					}
				} else {
					Walking.walkPath(pathToBob);
				}

			} else {
				if (Banking.close())
					General.sleep(200);
				if (new RSTile(3208, 3218, 2).distanceTo(Player.getPosition()) < 20
						&& Game.getPlane() == 2
						|| new RSTile(3208, 3218, 1).distanceTo(Player
								.getPosition()) < 20 && Game.getPlane() == 1) {
					RSObject[] stairs = Objects.findNearest(30, "Staircase");
					if (stairs.length > 0) {
						GeneralMethods.clickObject(stairs[0], "Climb-down",
								true, false);
					}
				} else {
					if (new RSTile(3214, 3218, 0).distanceTo(Player
							.getPosition()) < 20) {
						Walking.blindWalkTo(new RSTile(3214, 3218, 0));
					} else {
						if (Player.getAnimation() == -1) {
							if (GameTab.open(TABS.MAGIC)) {
								Mouse.click(573, 237, 1);
								Timing.waitCondition(new Condition() {
									@Override
									public boolean active() {
										return Player.getAnimation() != -1;
									}
								}, 2000);
							}
						}
					}
				}
			}
		} else {
			if (Banking.isBankScreenOpen()) {
				if (Banking.find(995).length > 0) {
					if (Banking.find(995)[0].getStack() > 299999) {
						Banking.withdraw(300000, 995);
						General.sleep(2500);
						return;
					} else {
						while (minCash == 0)
							minCash = Banking.find(995)[0].getStack();
						Banking.withdraw(0, 995);
						General.sleep(2500);
						return;
					}
				}
			} else {
				if (new RSTile(3208, 3218, 2).distanceTo(Player.getPosition()) < 20
						&& Game.getPlane() == 2) {

					RSNPC[] banker = NPCs.findNearest("Banker tutor");
					if (banker.length > 0) {
						if (banker[0].isOnScreen()) {
							GeneralMethods.click(banker[0], "Bank");
							General.sleep(1000);
						} else {
							Walking.walkTo(new RSTile(3209, 3219, 0));
						}
					}
				} else {
					if (new RSTile(3208, 3218, 1).distanceTo(Player
							.getPosition()) < 20 && Game.getPlane() == 1) {
						RSObject[] stairs = Objects
								.findNearest(30, "Staircase");
						if (stairs.length > 0) {
							GeneralMethods.clickObject(stairs[0], "Climb-up",
									true, false);
						}
					} else {
						if (new RSTile(3208, 3218, 0).distanceTo(Player
								.getPosition()) < 20
								|| new RSTile(3217, 3218, 0).distanceTo(Player
										.getPosition()) < 20) {
							RSObject[] stairs = Objects.findNearest(30,
									"Staircase");
							if (stairs.length > 0) {
								if (stairs[0].getPosition().distanceTo(
										Player.getPosition()) < 10) {
									GeneralMethods.clickObject(stairs[0],
											"Climb-up", true, false);
								} else {
									Walking.blindWalkTo(stairs[0]);
								}

							}
						} else {
							if (Player.getAnimation() == -1) {
								if (GameTab.open(TABS.MAGIC)) {
									Mouse.click(573, 237, 1);
									Timing.waitCondition(new Condition() {
										@Override
										public boolean active() {
											return Player.getAnimation() != -1;
										}
									}, 2000);
								}
							}
						}
					}
				}
			}
		}
	}

	private static int minCash = 0;

	static boolean isInLumbridge() {
		for (RSTile t : pathToBob) {
			if (t.distanceTo(Player.getPosition()) < 15) {
				return true;
			}
		}
		return false;
	}

}
