package scripts.Barrows.main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSTile;
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
	String[] auths = { "E12MLDqNWpQAPcw", "RH585Khpd7MZkvD", "8634L4234Hn0PaL",
			"T26281g87514W7y", "TxN63P3898Pw512", "m3165u7i4eZW5rd",
			"131Zpzz15Kd5bGI", "424sAkN7rRYc32j", "KL4x044qQ2pr813",
			"74M6T36MtK406Qe", "74M6T36MtK406Qe", "dZyv87g38A8Y8MC",
			"F8uWE3LlzY0bW22", "3Oa6C4x0J85iI51", "cM4dfP7AnOC2fDA",
			"n1M0zJ01g498U67", "LYi3dheq91jVvjn", "a1dCG1e68epU659",
			"aF6t55x89206V4K", "qPDkkdGd3Ay3f3x" };
	String response;

	@Override
	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					response = JOptionPane.showInputDialog(null,
							"Please enter the wBarrows Beta password");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		while (response == null) {
			sleep(100);
		}
		for (String s : auths) {
			if (s.equalsIgnoreCase(response)) {
				onStart();
				while (Var.running) {
					loop();
				}
			}
		}
	}

	private void loop() {
		Mouse.setSpeed(General.random(250, 350));
		try {

			if (BankHandler.needToBank()
					&& !Var.bankArea.contains(Player.getPosition())) {
				Var.status = "Heading to the bank";
				Pathing.goToBank();
				return;
			}

			if (BankHandler.needsMoreSupplies()
					&& Var.bankArea.contains(Player.getPosition())) {
				BankHandler.bank();
				return;
			}

			if (!Pathing.isInBarrows() && !Tunnel.inCrypt()
					&& Rooms.getRoom() == null && !BankHandler.needToBank()) {
				Pathing.getToBarrows();
				return;
			}
			if (Pathing.isInBarrows() && BrotherKilling.canKill()
					&& !Var.lootedChest) {
				BrotherKilling.StartFight();
				return;
			}
			if (Pathing.isInBarrows() && !BrotherKilling.canKill()
					&& !Var.lootedChest || !BrotherKilling.canKill()
					&& Tunnel.inCrypt() && !Var.lootedChest) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void onStart() {
		activateGUI();
		GeneralMethods.setPrices();
		while (Var.guiWait) {
			sleep(100);
		}
		Var.startTime = System.currentTimeMillis();
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

	private final Color tRed = new Color(255, 0, 0, 100);
	private final Color tYellow = new Color(255, 255, 0, 100);
	private final Color tBlue = new Color(0, 0, 255, 100);

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
					g.drawString(s, 287, 362 + 15 * i);
					i++;
				}
			}
			g.drawString("We have completed " + Var.runs + " runs!", 287,
					362 + 15 * i);
			i++;
			Var.runTime = System.currentTimeMillis() - Var.startTime;
			g.drawString("Runtime: " + formatTime(Var.runTime), 287,
					362 + 15 * i);

			g.setColor(Color.GREEN);
			if (Var.status != null) {
				g.drawString(Var.status, 574, 376);
			}
			if (Var.startingRoom != null) {
				g.drawString("Starting Room: " + Var.startingRoom.toString(),
						574, 396);
			}
			if (Var.debugObject != null && Var.debugObject.isOnScreen()
					|| Var.centerPoint != null) {
				g.setColor(tRed);
				g.drawPolygon(Var.debugObject.getModel().getEnclosedArea());
				g.setColor(tYellow);
				g.drawRect(Var.centerPoint.x - 7, Var.centerPoint.y - 7, 14, 14);
			}
			if (Var.targetTile != null && Var.targetTile.isOnScreen()) {
				g.setColor(Color.BLACK);
				g.drawPolygon(Projection.getTileBoundsPoly(Var.targetTile, 0));
				g.setColor(tRed);
				g.fillPolygon(Projection.getTileBoundsPoly(Var.targetTile, 0));
			}
			if (Var.viableTiles != null) {
				for (RSTile t : Var.viableTiles) {
					if (t.isOnScreen()) {
						g.setColor(Color.BLACK);
						g.drawPolygon(Projection.getTileBoundsPoly(t, 0));
						g.setColor(tBlue);
						g.fillPolygon(Projection.getTileBoundsPoly(t, 0));
					}
				}
			}
			g.setColor(Color.RED);
			g.drawString("wBarrows Beta", 2, 20);
		}
	}

	private static String formatTime(long runTime) {
		long seconds = 0;
		long minutes = 0;
		long hours = 0;
		String second, minute, hour;
		seconds = runTime / 1000;
		if (seconds >= 60) {
			minutes = seconds / 60;
			seconds -= (minutes * 60);
		}
		if (minutes >= 60) {
			hours = minutes / 60;
			minutes -= (hours * 60);
		}
		if (hours < 10) {
			hour = "0" + hours;
		} else {
			hour = "" + hours;
		}
		if (minutes < 10) {
			minute = "0" + minutes;
		} else {
			minute = "" + minutes;
		}
		if (seconds < 10) {
			second = "0" + seconds;
		} else {
			second = "" + seconds;
		}
		return (hour + ":" + minute + ":" + second);
	}

}
