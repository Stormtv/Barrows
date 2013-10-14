package scripts.Barrows.main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.SwingUtilities;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

import scripts.Barrows.gui.BarrowGUI;
import scripts.Barrows.methods.BankHandler;
import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.methods.Pathing;
import scripts.Barrows.methods.tunnel.Rooms;
import scripts.Barrows.methods.tunnel.Tunnel;
import scripts.Barrows.methods.tunnel.TunnelTraversing;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.Var;

@ScriptManifest(authors = { "wussupwussup", "Integer" }, category = "Minigames", name = "Barrows")
public class Barrows extends Script implements Painting {

	@Override
	public void run() {
		onStart();
		while (Var.running) {
			loop();
		}
	}

	private void loop() {
		Mouse.setSpeed(General.random(250, 350));
		
		//TODO NEED TO PATHING CHECK eg. out of food / pots
		
		if (BankHandler.needToBank() 
				&& Var.bankArea.contains(Player.getPosition())) {
			BankHandler.bank();
			return;
		}
		if (Pathing.isInBarrows() && BrotherKilling.canKill() 
				&& !Var.lootedChest) {
			BrotherKilling.StartFight();
			return;
		}
		if (Pathing.isInBarrows() && !BrotherKilling.canKill()
				&& !Var.lootedChest
				|| !BrotherKilling.canKill() && Tunnel.inCrypt()
				&& !Var.lootedChest) {
			Tunnel.goToTunnel();
			return;
		}
		if (!BrotherKilling.canKill() && Tunnel.inCrypt()
				&& Var.lootedChest) {
			Tunnel.exitCrypt();
			return;
		}
		if (Rooms.getRoom() != null && !BrotherKilling.canKill()
				&& !Tunnel.inCrypt() 
				&& !Var.bankArea.contains(Player.getPosition())) {
			TunnelTraversing.traverseTunnel();
			return;
		}
		if (Pathing.isInBarrows() && !BrotherKilling.canKill() 
				&& Var.lootedChest) {
			BrotherKilling.reset();
			return;
		}

	}

	private void onStart() {
		activateGUI();
		GeneralMethods.setPrices();
		while (Var.guiWait) {
			sleep(100);
		}
	}

	private void activateGUI() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Var.gui = new BarrowGUI();
					Var.gui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private final Color tRed = new Color(255, 0, 0, 110);
	private final Color tYellow = new Color(255, 255, 0, 110);
	@Override
	public void onPaint(Graphics g) {
		if (Var.debug) {
			int i = 0;
			for (Brothers b : Brothers.values()) {
				g.setColor(Color.BLACK);
				String s = b.getName();
				if (s != null) {
					if (b.isKilled()) {
						s = s + " Killed: " + b.isKilled();
					}
					if (b.isTunnel()) {
						s = s + " Tunnel: " + b.isTunnel();
					}
					g.drawString(s, 287, 362 + 10 * i);
					i++;
				}
				g.setColor(Color.GREEN);
			}
			if (Var.status != null) {
				g.drawString(Var.status, 574, 376);
			}
			if (Var.startingRoom !=null) {
				g.drawString("Starting Room: " + Var.startingRoom.toString(),574,396);
			}
			if (Var.debugObject != null && Var.debugObject.isOnScreen() 
					|| Var.centerPoint !=null ) {
				g.setColor(tRed);
				g.drawPolygon(Var.debugObject.getModel().getEnclosedArea());
				g.setColor(tYellow);
				g.drawRect(Var.centerPoint.x-7, Var.centerPoint.y-7, 14, 14);
			}
			if (Var.furthestTile!=null) {
				g.setColor(tRed);
				g.drawPolygon(Projection.getTileBoundsPoly(Var.furthestTile, 0));
			}
		}
	}

}
