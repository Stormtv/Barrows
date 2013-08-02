package scripts.Barrows.main;

import java.awt.event.KeyEvent;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;

import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.methods.Pathing;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.Potions;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Equipment;
import scripts.Barrows.types.enums.Food;
import scripts.Barrows.types.enums.Magic;
import scripts.Barrows.types.enums.Prayer;

public class BrotherKilling {

	public static void StartFight() {
		for (Brothers bro : Brothers.values()) {
			if (bro != null && !bro.isTunnel && !bro.killed) {
				if (!isInCrypt(bro)) {
					goToCrypt(bro);
				}
				if (isInCrypt(bro)) {
					kill(bro);
				}
			}
		}
	}

	private static void kill(Brothers bro) {
		RSObject[] coffin = Objects.find(10, bro.getCryptID());
		if (coffin.length > 0) {
			GeneralMethods.clickObject(coffin[0], "Search", false);
		}
		if (tunnelInterface()) {
			bro.isTunnel = true;
		} else {
			RSNPC target = aggressiveNPC();
			if (target!=null) {
				attackMob(target);
				CombatManager(bro);
			} else {
				bro.killed = true;
			}
			exitCrypt(bro);
		}
	}
	
	private static void CombatManager(Brothers b) {
		while (Player.getRSPlayer().getInteractingCharacter() != null
				|| aggressiveNPC() != null) {
			UpKeep();
			RSNPC target = aggressiveNPC();
			if (target.isInCombat() && target.getHealth() == 0) {
				break;
			}
			if (Player.getRSPlayer().getInteractingCharacter() == null) {
				GeneralMethods.click(target, "Attack");
			}
		}
		b.killed = true;
	}

	private static void UpKeep() {
		Potions.drinkPrayerInCombat();
		Food.eatInCombat();
	}

	private static void exitCrypt(Brothers b) {
		if (Objects.find(10, b.getStair()).length > 0) {
			GeneralMethods.clickObject(Objects.find(10, b.getStair())[0],"Climb", false);
			for (int fSafe = 0; fSafe < 20
					&& !Var.barrowsArea.contains(Player.getPosition()); fSafe++) {
				General.sleep(50);
			}
			if (Var.barrowsArea.contains(Player.getPosition())) {
				if (!b.prayer.equals(Prayer.Prayers.None)) {
					Prayer.disable(b);
				}
			} else {
				exitCrypt(b);
			}
		}
	}

	private static void goToCrypt(Brothers b) {
		if (!b.getDigArea().contains(Player.getPosition())) {
			if (Pathing.isInBarrows()) {
				if (b.getDigArea().getTiles().length > 0) {
					Walking.blindWalkTo(Pathing.getRandomTile(b.getDigArea()));
					General.sleep(350, 500);
					while (Player.isMoving()) {
						General.sleep(30);
					}
				}
			}
			if (!b.getDigArea().contains(Player.getPosition()))
				goToCrypt(b);
		}
		if (b.getDigArea().contains(Player.getPosition())) {
			getReadyToFight(b);
			if (b.usePotions()) {
				Potions.superPot();
			}
			dig(b);
		}
	}

	public static void killBrotherInTunnel() {
		// TODO Auto-generated method stub
	}

	private static void dig(Brothers b) {
		if (!GameTab.getOpen().equals(TABS.INVENTORY)) {
			Keyboard.pressKey((char) KeyEvent.VK_ESCAPE);
		}
		if (Inventory.find(Var.SPADE_ID).length > 0) {
			Inventory.find(Var.SPADE_ID)[0].click("");
			for (int fSafe = 0; fSafe < 20
					&& Player.getPosition().getPlane() != 3; fSafe++) {
				General.sleep(50);
			}
			if (Player.getPosition().getPlane() == 3) {
				if (!b.prayer.equals(Prayer.Prayers.None)) {
					Prayer.activate(b);
				}
			} else {
				dig(b);
			}
		}
	}

	private static void getReadyToFight(Brothers b) {
		if (!b.getPrayer().equals(Prayer.Prayers.None)) {
			Potions.fillPrayer();
		}
		while (!Equipment.isAllEquiped(b.getEquipment())) {
			for (int i : b.getEquipment()) {
				if (!Equipment.isEquiped(i) && Inventory.getCount(i) > 0) {
					Equipment.equip(i);
				}
			}
		}
		if (Food.canEatWithoutWaste()) {
			Food.eat();
		}
		if (!b.getSpell().equals(Magic.Spell.NONE)) {
			General.println("Spell is not NONE");
			while (!Magic.isAutocasting(b.getSpell())) {
				General.println("Turning ON auto Cast");
				Magic.autoCast(b.getSpell());
				General.println("Spell should be turned on now");
			}
		}
	}

	 private static boolean tunnelInterface() {
		  return Interfaces.get(210, 0) != null;
	}
	
	private static boolean isInCrypt(Brothers b) {
		return (Player.getPosition().getPlane() == 3 && Objects.find(15,
				b.getCryptID()).length > 0);
	}
	
	private static RSNPC aggressiveNPC() {
		for (RSNPC n : NPCs.getAll()) {
				if (n.isInteractingWithMe() 
						&& isBrother(n)) {
					return n;
				}
		}
		return null;
	}

	private static void attackMob(RSNPC n) {
		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
			GeneralMethods.click(n, "Attack");
		General.sleep(50, 100);
		while (Player.isMoving())
			General.sleep(10);
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
	}
	
	private static boolean isBrother(RSNPC n) {
		for (Brothers bro : Brothers.values()) {
			if (n.getName().equals(bro.getName())) {
				return true;
			}
		}
		return false;
	}
}
