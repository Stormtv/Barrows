package scripts.Barrows.main;

import java.awt.Color;


import java.awt.Graphics;

import javax.swing.SwingUtilities;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

import scripts.Barrows.gui.BarrowGUI;
import scripts.Barrows.methods.BankHandler;
import scripts.Barrows.methods.Pathing;
import scripts.Barrows.methods.tunnel.Rooms;
import scripts.Barrows.methods.tunnel.Tunnel;
import scripts.Barrows.methods.tunnel.TunnelTraversing;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.Brother.Brothers;


@ScriptManifest(authors = { "wussupwussup", "Integer" }, category = "Money Making", name = "Barrows")

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
		// Bank Check
		// Tunnel Check
		// More checks

		// Kill Test
		if (Pathing.isInBarrows() && BrotherKilling.canKill()) {
			BrotherKilling.StartFight();
			return;
		}
		if (Pathing.isInBarrows() && !BrotherKilling.canKill()
				|| !BrotherKilling.canKill() && Tunnel.inCrypt()) {
			Tunnel.goToTunnel();
			return;
		}
		if (Rooms.getRoom() != null && !BrotherKilling.canKill()
				&& !Tunnel.inCrypt()) {
			TunnelTraversing.traverseTunnel();
			return;
		}
		if (BankHandler.needToBank()) {
			BankHandler.bank();
			return;
		}
	}

	private void onStart() {
		activateGUI();
	}

	private void activateGUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Var.gui = new BarrowGUI();
				Var.gui.setVisible(true);
			}
		});
		while (Var.guiWait) {
			sleep(100);
		}
	}

	@Override
	public void onPaint(Graphics g) {
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
			g.setColor(Color.RED);
		}
		if (Var.status != null) {
			g.drawString(Var.status, 574, 376);
		}

	}

}
