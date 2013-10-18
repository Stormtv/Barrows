package scripts.Barrows.main;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

import scripts.Barrows.gui.BarrowGUI;
import scripts.Barrows.methods.BankHandler;
import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.methods.Pathing;
import scripts.Barrows.methods.PaintHandler;
import scripts.Barrows.methods.tunnel.Rooms;
import scripts.Barrows.methods.tunnel.Tunnel;
import scripts.Barrows.methods.tunnel.TunnelTraversing;
import scripts.Barrows.types.Var;
import scripts.Barrows.util.Timer;

@ScriptManifest(authors = { "wussupwussup", "Integer" }, category = "Minigames", name = "Barrows")
public class Barrows extends Script implements Painting {
	String[] auths = { "E12MLDqNWpQAPcw", "RH585Khpd7MZkvD", "8634L4234Hn0PaL",
			"T26281g87514W7y", "TxN63P3898Pw512", "m3165u7i4eZW5rd",
			"131Zpzz15Kd5bGI", "424sAkN7rRYc32j", "KL4x044qQ2pr813",
			"74M6T36MtK406Qe", "74M6T36MtK406Qe", "dZyv87g38A8Y8MC",
			"F8uWE3LlzY0bW22", "3Oa6C4x0J85iI51", "cM4dfP7AnOC2fDA",
			"n1M0zJ01g498U67", "LYi3dheq91jVvjn", "a1dCG1e68epU659",
			"aF6t55x89206V4K", "qPDkkdGd3Ay3f3x", "cba" };
	String response;

	public static double version = 0.3;
	
	public static Timer runTime = new Timer(0);

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
		Walking.walking_timeout = 500;
		Mouse.setSpeed(General.random(250, 350));
		try {

			if (BankHandler.needToBank()
					&& !Var.bankArea.contains(Player.getPosition())
					&& !Player.getPosition().equals(new RSTile(3498,3380,1))
					&& !Player.getPosition().equals(new RSTile(3522,3285,0))) {
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
				if (Rooms.getRoom() == null) {
					Pathing.getToBarrows();
					return;
				}
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
			if (!Pathing.isInBarrows() && Pathing.isCloseToBarrows()) {
				Pathing.walkToCenterOfBarrows();
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


	@Override
	public void onPaint(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;
		PaintHandler.drawPaint(g);
	}

}
