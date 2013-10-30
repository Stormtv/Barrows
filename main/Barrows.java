package scripts.Barrows.main;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.net.MalformedURLException;

import javax.swing.SwingUtilities;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.MouseActions;
import org.tribot.script.interfaces.MousePainting;
import org.tribot.script.interfaces.Painting;

import scripts.Barrows.gui.BarrowGUI;
import scripts.Barrows.gui.LootTable;
import scripts.Barrows.methods.BankHandler;
import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.methods.PaintHandler;
import scripts.Barrows.methods.Pathing;
import scripts.Barrows.methods.PriceHandler;
import scripts.Barrows.methods.tunnel.Rooms;
import scripts.Barrows.methods.tunnel.Tunnel;
import scripts.Barrows.methods.tunnel.TunnelTraversing;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Prayer;
import scripts.Barrows.util.Timer;

@ScriptManifest(authors = { "wussupwussup, integer" }, category = "Money Making", name = "wBarrows")
public class Barrows extends Script implements Painting, MouseActions,
		MousePainting, Ending {
	public static double version = 1.05;

	public static Timer runTime = new Timer(0);

	LootTable frame;

	@Override
	public void run() {
		onStart();
		runTime = new Timer(0);
		while (Var.running) {
			General.sleep(loop());
		}
	}

	private int loop() {
		Walking.setWalkingTimeout(500);
		Mouse.setSpeed(General.random(250, 350));
		try {
			GeneralMethods.adjustBrightness();
			if (Prayer.anyPrayerEnabled() && Rooms.getRoom() == null
					&& !Tunnel.inCrypt() && !Pathing.isInBarrows()) {
				Prayer.disableAllPrayers();
			}
			if (BankHandler.needToBank()
					&& !Var.bankArea.contains(Player.getPosition())
					&& !Player.getPosition().equals(new RSTile(3498, 3380, 1))
					&& !Player.getPosition().equals(new RSTile(3522, 3285, 0))) {
				Var.status = "Heading to the bank";
				Walking.setWalkingTimeout(1);
				Pathing.goToBank();
				return General.random(10, 30);
			}

			if (BankHandler.needsMoreSupplies()
					&& Var.bankArea.contains(Player.getPosition())) {
				BankHandler.bank();
				return General.random(10, 30);
			}

			if (!Pathing.isInBarrows() && !Tunnel.inCrypt()
					&& Rooms.getRoom() == null && !BankHandler.needToBank()) {
				if (Rooms.getRoom() == null) {
					Pathing.getToBarrows();
				}
				return General.random(10, 30);
			}
			if (Pathing.isInBarrows() && BrotherKilling.canKill()
					&& !Var.lootedChest) {
				BrotherKilling.StartFight();
				return General.random(10, 30);
			}
			if (Pathing.isInBarrows() && !BrotherKilling.canKill()
					&& !Var.lootedChest || !BrotherKilling.canKill()
					&& Tunnel.inCrypt() && !Var.lootedChest) {
				Tunnel.goToTunnel();
				return General.random(10, 30);
			}
			if (!BrotherKilling.canKill() && Tunnel.inCrypt()
					&& Var.lootedChest) {
				Tunnel.exitCrypt();
				return General.random(10, 30);
			}
			if (Rooms.getRoom() != null && !BrotherKilling.canKill()
					&& !Tunnel.inCrypt()
					&& !Var.bankArea.contains(Player.getPosition())) {
				TunnelTraversing.traverseTunnel();
				return General.random(10, 30);
			}
			if (Pathing.isInBarrows() && !BrotherKilling.canKill()
					&& Var.lootedChest) {
				BrotherKilling.reset();
				return General.random(10, 30);
			}
			if (!Pathing.isInBarrows() && Pathing.isCloseToBarrows()) {
				Pathing.walkToCenterOfBarrows();
				return General.random(10, 30);
			}
			if (Tunnel.inCrypt()) {
				Tunnel.exitCrypt();
				return General.random(10, 30);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return General.random(10, 30);
		}
		return General.random(10, 30);
	}

	private void onStart() {
		println("Thank you for using wBarrows " + General.getTRiBotUsername()
				+", if you have any issues contact me on skype: wussupscripts");
		GeneralMethods.adjustBrightness();
		activateGUI();
		new Thread(new PriceHandler()).start();
		activateTable();
		while (Var.guiWait) {
			sleep(100);
		}
		Var.startTime = System.currentTimeMillis();
	}

	private void activateTable() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					frame = new LootTable();
					frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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

	Rectangle paint = new Rectangle(0, 340, 480, 140);

	@Override
	public void mouseClicked(Point p, int arg1, boolean arg2) {
		if (!arg2 && paint.contains(p))
			frame.setVisible(getState());
	}

	@Override
	public void mouseDragged(Point arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(Point arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(Point arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	boolean getState() {
		if (frame.isVisible())
			return false;
		return true;
	}

	@Override
	public void paintMouse(Graphics arg0, Point arg1, Point arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onEnd() {
		try {
			GeneralMethods.updateSig();
			println("Upated Sig, to check yours, visit: http://polycoding.com/wbarrows/"
							+ General.getTRiBotUsername() + ".png");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
