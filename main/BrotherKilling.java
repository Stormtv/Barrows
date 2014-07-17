package scripts.Barrows.main;

import java.util.Arrays;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Game;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;

import scripts.Barrows.methods.BankHandler;
import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.methods.Looting;
import scripts.Barrows.methods.Pathing;
import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.Potions;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Equipment;
import scripts.Barrows.types.enums.Food;
import scripts.Barrows.types.enums.Magic;
import scripts.Barrows.types.enums.Prayer;
import scripts.Barrows.types.enums.Prayer.Prayers;

public class BrotherKilling {

	private static Brothers[] killOrder() {
		Brothers[] ba = new Brothers[6];
		for (Brothers bro : Brothers.values()) {
			ba[bro.killOrder()] = bro;
		}
		return ba;
	}

	public static boolean canKill() {
		for (Brothers b : Brothers.values()) {
			if (!b.isTunnel() && !b.isKilled()) {
				return true;
			}
		}
		return false;
	}

	public static void StartFight() {
		Brothers bro = null;
		for (Brothers b : killOrder()) {
			if (b != null && !b.isKilled() && !b.isTunnel()) {
				bro = b;
				break;
			}
		}
		int killCount = 0;
		for (Brothers b : Brothers.values()) {
			if (b.isKilled())
				killCount++;
		}
		if (killCount == 5) {
			bro.setTunnel(true);
		}
		if (bro != null && !bro.isTunnel() && !bro.isKilled()) {
			if (!isInCrypt(bro)) {
				Prayer.disableAllPrayers();
				Var.status = "Going to crypt " + bro.getName();
				goToCrypt(bro);
			}
			for (int fSafe = 0; fSafe < 20 && !isInCrypt(bro); fSafe++) {
				General.sleep(75);
			}
			if (isInCrypt(bro)) {
				if (aggressiveNPC() != null) {
					CombatManager(bro);
				} else {
					if (bro != null && !bro.isTunnel() && !bro.isKilled()) {
						Var.status = "Checking Coffin";
						kill(bro);
					} else {
						exitCrypt(bro);
					}
				}
			}
		}
	}

	private static void kill(Brothers bro) {
		RSObject[] coffin = Objects.find(10, bro.getCryptID());
		if (coffin.length > 0) {
			Var.status = "Searching the coffin";
			GeneralMethods.clickObject(coffin[0], "Search", false, false);
			Var.status = "Searched the coffin";
			General.sleep(350, 500);
			while (Player.isMoving()) {
				General.sleep(20, 40);
			}
		}
		for (int fSafe = 0; fSafe < 20 && !tunnelInterface() || fSafe < 20
				&& aggressiveNPC() == null; fSafe++) {
			General.sleep(20);
			Var.status = "Waiting";
		}
		if (tunnelInterface()) {
			Var.status = "Tunnel Found";
			Var.status = bro.getName() + " is tunnel";
			bro.setTunnel(true);
		} else {
			Var.status = "Searching Aggressive NPC";
			RSNPC target = aggressiveNPC();
			if (target != null) {
				Var.status = "Target Found";
				attackMob(target);
				CombatManager(bro);
			} else if (GeneralMethods.lastMessage().equals(
					"You don't find anything.")) {
				Var.status = "No Target must of killed him already";
				bro.setKilled(true);
			} else {
				General.println(GeneralMethods.lastMessage());
			}
		}
		Var.status = "Exiting Crypt";
		exitCrypt(bro);
	}

	private static void CombatManager(Brothers b) {
		while (Player.getRSPlayer().getInteractingCharacter() != null
				|| aggressiveNPC() != null
				|| !PathFinding.canReach(aggressiveNPC(), false)) {
			if (BankHandler.needToBank(b)) {
				return;
			}
			UpKeep(b);
			GeneralMethods.ABCL();
			if (!b.getPrayer().equals(Prayer.Prayers.None)
					&& !b.getPrayer().isActivated()) {
				Prayer.activate(b.getPrayer());
			}
			RSNPC target = aggressiveNPC();
			if (target == null || target.isInCombat()
					&& target.getHealth() == 0) {
				b.setKilled(true);
				while (GroundItems.find(Var.arrowId).length > 0
						&& PathFinding.canReach(
								GroundItems.find(Var.arrowId)[0], false)) {
					Looting.loot(Var.arrowId);
				}
				return;
			}
			if (Player.getRSPlayer().getInteractingCharacter() == null) {
				GeneralMethods.click(target, "Attack");
			}
			General.sleep(100, 150);
		}
	}

	private static void UpKeep(Brothers b) {
		if (!b.getPrayer().equals(Prayer.Prayers.None)) {
			Potions.drinkPrayerInCombat();
		}
		Food.eatInCombat();
	}

	private static void exitCrypt(Brothers b) {
		if (Objects.find(10, b.getStair()).length > 0) {
			RSObject stair = Objects.find(10, b.getStair())[0];
			GeneralMethods.clickObject(stair, "Climb", false, true);
			for (int fSafe = 0; fSafe < 20
					&& Player.getPosition().getPlane() == 3; fSafe++) {
				General.sleep(75);
			}
			if (Player.getPosition().getPlane() != 3) {
				Var.status = "Disabling Prayer";
				Prayer.disableAllPrayers();
			}
		}
	}

	public static void goToCrypt(Brothers b) {
		if (!b.getDigArea().contains(Player.getPosition())) {
			if (Pathing.isInBarrows()) {
				if (b.getDigArea() != null) {
					Var.status = "Walking to mound";
					Walking.setControlClick(true);
					Walking.setWalkingTimeout(1500);
					General.sleep(200, 350);
					Walking.blindWalkTo(b.getDigArea().getRandomTile());
					General.sleep(350, 500);
					while (Player.isMoving()) {
						General.sleep(30);
					}
				}
			}
		}
		if (b.getDigArea().contains(Player.getPosition())) {
			if (!b.isTunnel()) {
				Var.status = "Getting Ready to fight " + b.toString();
				getReadyToFight(b);
				Var.status = "Digging";
				if (!BankHandler.needToBank()) {
					dig(b);
				}
			} else {
				Var.status = "Getting Ready for tunnels";
				getReadyForTunnels();
				Var.status = "Digging";
				if (!BankHandler.needToBank()) {
					dig(b);
				}
			}
		}
	}

	public static boolean killBrotherInTunnel() {
		if (Brothers.isAllReallyDead()) {
			return false;
		}
		while (Player.isMoving())
			General.sleep(15, 30);
		for (int fSafe = 0; fSafe < 5 && aggressiveNPC() == null; fSafe++) {
			General.sleep(20, 30);
			Var.status = "Checking for npc spawn";
		}
		RSNPC target = aggressiveNPC();
		if (target != null && PathFinding.canReach(target.getPosition(), false)) {
			Var.status = "Found Brother in Tunnel";
			Brothers bro = Brother.getTunnelBrother();
			tunnelPrayer(bro);
			getReadyToFight(bro);
			attackMob(target);
			CombatManager(bro);
			if (!BankHandler.needToBank()) {
				Var.status = "Disabling Prayer";
				Prayer.disableAllPrayers();
				Var.status = "Switching back to tunnel gear";
				bro.setKilled(true);
				getReadyForTunnels();
			}
			return true;
		} else {
			Var.status = "No Brother spawned";
			return false;
		}
	}

	private static void tunnelPrayer(Brothers b) {
		if (!b.getPrayer().equals(Prayer.Prayers.None)
				&& Potions.canDrinkWithoutWaste()) {
			Potions.drink();
			Prayer.activate(b.getPrayer());
		} else if (!b.getPrayer().equals(Prayer.Prayers.None)
				&& !b.getPrayer().isActivated()) {
			Prayer.activate(b.getPrayer());
		}
	}

	private static void dig(Brothers b) {
		if (!GameTab.getOpen().equals(TABS.INVENTORY)) {
			GameTab.open(GameTab.TABS.INVENTORY);
			for (int fsafe = 0; fsafe < 20
					&& !GameTab.getOpen().equals(TABS.INVENTORY); fsafe++) {
				General.sleep(15);
			}
		}
		if (Inventory.find(Var.SPADE_ID).length > 0) {
			Inventory.find(Var.SPADE_ID)[0].click("Dig");
			for (int fSafe = 0; fSafe < 40
					&& (Objects.find(15, "Sarcophagus").length == 0
							|| Objects.find(15, "Sarcophagus")[0].getModel() == null
							|| Objects.find(15, "Sarcophagus")[0].getModel()
									.getAllVisiblePoints().length < 10
							|| PathFinding.canReach(
									Objects.find(15, "Sarcophagus")[0], true) || Player
							.getPosition().getPlane() != 3); fSafe++) {
				General.sleep(40, 60);
			}
		}
	}

	private static void dropVials() {
		RSItem[] dropables = Inventory.find(new int[] {229, 2959});
		if (dropables != null && dropables.length > 0) {
			Inventory.drop(dropables);
		}
		if (Game.getUptext().contains("->")) {
			Walking.walkTo(Player.getPosition());
			for (int i = 0; i < 10 && !Game.getUptext().contains("->"); i++) {
				General.sleep(30, 50);
			}
		}
	}

	private static void getReadyForTunnels() {
		dropVials();
		while (Inventory.find(Var.arrowId).length > 0) {
			Equipment.equip(Var.arrowId);
			for (int fsafe = 0; fsafe < 20 && !Equipment.isEquipped(Var.arrowId); fsafe++) {
				General.sleep(50);
			}
		}
		while (!Equipment.isAllEquiped(Var.tunnelEquipment)) {
			for (int[] i : Var.tunnelEquipment) {
				if (!Equipment.isEquipped(i) && Inventory.getCount(i) > 0) {
					Equipment.equip(i);
					continue;
				} else if (!Equipment.isEquipped(i)
						&& Inventory.getCount(i) == 0) {
					Var.status = "Unable to find Equipment ("
							+ Arrays.toString(i) + ")";
				}
			}
			for (int fsafe = 0; fsafe < 20
					&& !Equipment.isAllEquiped(Var.tunnelEquipment); fsafe++) {
				General.sleep(50);
			}
			if (!Equipment.isAllEquiped(Var.tunnelEquipment)
					&& GeneralMethods.lastMessage().contains(
							"free inventory space")
					&& Inventory.getCount(Var.food.getId()) > 0
					&& Inventory.isFull()) {
				Food.forceFeed();
			}
		}
		if (Combat.getSelectedStyleIndex() != Var.tunnelStance
				&& Var.tunnelStance != -1) {
			Var.status = "Setting Combat Stance";
			Combat.selectIndex(Var.tunnelStance);
		}
		if (Equipment.isEquipped(4587) && Combat.getSelectedStyleIndex()!=1) {
			if (!GameTab.getOpen().equals(GameTab.TABS.COMBAT)) {
				GameTab.open(GameTab.TABS.COMBAT);
				for (int fail = 0; fail < 20
						&& !GameTab.getOpen().equals(GameTab.TABS.COMBAT); fail++) {
					General.sleep(15, 25);
				}
			}
			while (Combat.getSelectedStyleIndex() != 1) {
				Combat.selectIndex(1);
			}
		}
		if (Food.canEatWithoutWaste()) {
			Food.eat();
		}
	}

	public static void getReadyToFight(Brothers b) {
		if (b.getPrayer() != null && !b.getPrayer().equals(Prayer.Prayers.None)) {
			Potions.fillPrayer();
		}
		dropVials();
		while (Inventory.find(Var.arrowId).length > 0) {
			Equipment.equip(Var.arrowId);
			for (int fsafe = 0; fsafe < 20 && !Equipment.isEquipped(Var.arrowId); fsafe++) {
				General.sleep(50);
			}
		}
		while (!Equipment.isAllEquiped(b.getEquipmentIds())) {
			for (int[] i : b.getEquipmentIds()) {
				if (!Equipment.isEquipped(i) && Inventory.getCount(i) > 0) {
					Equipment.equip(i);
					continue;
				} else if (!Equipment.isEquipped(i)
						&& Inventory.getCount(i) == 0
						&& !Arrays.toString(i).contains("-1")) {
					Var.status = "Unable to find Equipment ("
							+ Arrays.toString(i) + ")";
				}
			}
			for (int fsafe = 0; fsafe < 20
					&& !Equipment.isAllEquiped(b.getEquipmentIds()); fsafe++) {
				General.sleep(50);
			}
			if (!Equipment.isAllEquiped(b.getEquipmentIds())
					&& GeneralMethods.lastMessage().contains(
							"free inventory space")
					&& Inventory.getCount(Var.food.getId()) > 0
					&& Inventory.isFull()) {
				Food.forceFeed();
			}
		}
		if (b.getSpell().equals(Magic.Spell.NONE)
				&& Combat.getSelectedStyleIndex() != b.getSelectedStance()
				&& b.getSelectedStance() != -1) {
			Var.status = "Setting Combat Stance";
			Combat.selectIndex(b.getSelectedStance());
		}
		if (Food.canEatWithoutWaste()) {
			Food.eat();
		}
		if (Equipment.isEquipped(Var.Salamanders)) {
			if (!GameTab.getOpen().equals(GameTab.TABS.COMBAT)) {
				GameTab.open(GameTab.TABS.COMBAT);
				for (int fail = 0; fail < 20
						&& !GameTab.getOpen().equals(GameTab.TABS.COMBAT); fail++) {
					General.sleep(15, 25);
				}
			}
			if (b.equals(Brother.Brothers.Ahrim)) {
				while (Combat.getSelectedStyleIndex() != 1) {
					Combat.selectIndex(1);
				}
			} else {
				while (Combat.getSelectedStyleIndex() != 2) {
					Combat.selectIndex(2);
				}
			}
		}
		if (Equipment.isEquipped(4587) && Combat.getSelectedStyleIndex()!=1) {
			if (!GameTab.getOpen().equals(GameTab.TABS.COMBAT)) {
				GameTab.open(GameTab.TABS.COMBAT);
				for (int fail = 0; fail < 20
						&& !GameTab.getOpen().equals(GameTab.TABS.COMBAT); fail++) {
					General.sleep(15, 25);
				}
			}
			while (Combat.getSelectedStyleIndex() != 1) {
				Combat.selectIndex(1);
			}
		}
		if (Equipment.isEquipped(11907) && Combat.getSelectedStyleIndex() != 0) {
			if (!GameTab.getOpen().equals(GameTab.TABS.COMBAT)) {
				GameTab.open(GameTab.TABS.COMBAT);
				for (int fail = 0; fail < 20
						&& !GameTab.getOpen().equals(GameTab.TABS.COMBAT); fail++) {
					General.sleep(15, 25);
				}
			}
			while (Combat.getSelectedStyleIndex() != 0) {
				Combat.selectIndex(0);
			}
		}
		if (Equipment.isEquipped(861) && Combat.getSelectedStyleIndex() != 1) {
			if (!GameTab.getOpen().equals(GameTab.TABS.COMBAT)) {
				GameTab.open(GameTab.TABS.COMBAT);
				for (int fail = 0; fail < 20
						&& !GameTab.getOpen().equals(GameTab.TABS.COMBAT); fail++) {
					General.sleep(15, 25);
				}
			}
			while (Combat.getSelectedStyleIndex() != 1) {
				Combat.selectIndex(1);
			}
		}
		if (!b.getSpell().equals(Magic.Spell.NONE)) {
			if (!Magic.isAutocasting(b.getSpell())) {
				Var.status = "Activating auto casting";
				for (int fsafe = 0; fsafe < 10
						&& !Magic.isAutocasting(b.getSpell()); fsafe++) {
					Magic.autoCast(b.getSpell());
				}
			}
		}
		if (b.usePotions()) {
			Potions.superPot();
		}
		if (!b.getPrayer().equals(Prayer.Prayers.None)
				&& !b.getPrayer().isActivated()) {
			Prayer.activate(b.getPrayer());
			Var.status = "Activated Prayer";
		}
		if (b.getPrayer().equals(Prayer.Prayers.None)
				&& Prayer.getPoints() > 0) {
			if (b.equals(Brother.Brothers.Ahrim)) {
				Prayer.activate(Prayers.ProtectFromMagic);
			} else if (b.equals(Brother.Brothers.Karil)) {
				Prayer.activate(Prayers.ProtectFromMissiles);
			} else {
				Prayer.activate(Prayers.ProtectFromMelee);
			}
			Var.status = "Activated remaining prayer";
		}
	}

	private static boolean tunnelInterface() {
		return Interfaces.get(210, 0) != null;
	}

	public static boolean isInCrypt(Brothers b) {
		if (b == null) {
			return false;
		}
		return (Player.getPosition().getPlane() == 3 && Objects.find(15,
				b.getCryptID()).length > 0);
	}

	public static RSNPC aggressiveNPC() {
		for (RSNPC n : NPCs.getAll()) {
			if (n.isInteractingWithMe() && isBrother(n)) {
				return n;
			}
		}
		return null;
	}

	private static void attackMob(RSNPC n) {
		GeneralMethods.click(n, "Attack");
		General.sleep(50, 100);
		while (Player.isMoving())
			General.sleep(10);
	}

	private static boolean isBrother(RSNPC n) {
		for (Brothers bro : Brothers.values()) {
			if (n.getName().contains(bro.getName())) {
				return true;
			}
		}
		return false;
	}

	public static void reset() {
		Var.startingRoom = null;
		for (Brothers b : Brothers.values()) {
			b.setKilled(false);
			b.setTunnel(false);
		}
	}
}
