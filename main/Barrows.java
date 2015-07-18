package scripts.Barrows.main;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Player;
import org.tribot.api2007.Screen;
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
import scripts.Barrows.methods.Repairing;
import scripts.Barrows.methods.PriceHandler;
import scripts.Barrows.methods.TrialVersionHandler;
import scripts.Barrows.methods.tunnel.Rooms;
import scripts.Barrows.methods.tunnel.Tunnel;
import scripts.Barrows.methods.tunnel.TunnelTraversing;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Prayer;
import scripts.Barrows.types.enums.Armor;
import scripts.Barrows.util.Timer;

@ScriptManifest(authors = { "wussupwussup, integer" }, category = "Money Making", name = "wBarrows")
public class Barrows extends Script implements Painting, MouseActions,
		MousePainting, Ending {

	@Override
	public void run() {
		if (Var.trial) {
			TrialVersionHandler.setAuthorized(General.getTRiBotUsername());
		}
		onStart();
		Var.runTime = new Timer(0);
		while (Var.running && TrialVersionHandler.isAuthorized()) {
			Walking.setWalkingTimeout(500);
			if (Var.trial && TrialVersionHandler.canUpdate()) {
				TrialVersionHandler.updateTrial(General.getTRiBotUsername());
			} else {
				General.sleep(loop());
			}
		}
	}
	
	public BufferedImage getScreenshot(boolean includePaint) {
		BufferedImage i = new BufferedImage(765, 502,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = i.getGraphics();
		for (int x = 0; x <= 765; x++) {
			for (int y = 0; y <= 502; y++) {
				g.setColor(Screen.getColorAt(x, y));
				g.fillRect(x, y, 1, 1);
			}
		}
		if (includePaint) {
			onPaint(i.getGraphics());
		}
		return i;
	}

	@SuppressWarnings("deprecation")
	private int loop() {
		Mouse.setSpeed(Var.mouseSpeed+General.random(-100, 100));
		try {
			GeneralMethods.adjustBrightness();
			if (Prayer.anyPrayerEnabled() && Rooms.getRoom() == null
					&& !Tunnel.inCrypt() && !Pathing.isInBarrows()) {
				Prayer.disableAllPrayers();
			}
			
			if(Player.getPosition().getX() > 3580 && Player.getPosition().getX() < 3590){
				Walking.walkTo(new RSTile(3575, 3292, 0));
				while(Player.isMoving())
					General.sleep(100);
			}

			if (Armor.getCurrentDegraded() > 0) {
				Repairing.repair();
				return General.random(10, 30);
			}
			if (BankHandler.needsMoreSupplies()
					&& Var.bankArea.contains(Player.getPosition())) {
				Var.forceBank = false;
				BankHandler.bank();
				return General.random(10, 30);
			}
			if (Var.forceBank && Var.bankArea.contains(Player.getPosition())) {
				Var.forceBank = false;
				return General.random(10, 30);
			}
			if (BankHandler.needToBank()
					&& !Var.bankArea.contains(Player.getPosition())
					&& !Player.getPosition().equals(new RSTile(3498, 3380, 1))
					&& !Player.getPosition().equals(new RSTile(3522, 3285, 0))
					&& !Player.getPosition().equals(new RSTile(3497, 3381, 1))
					&& !Player.getPosition().equals(new RSTile(3490, 3413, 0))
					|| Var.forceBank
					&& !Var.bankArea.contains(Player.getPosition())
					&& !Player.getPosition().equals(new RSTile(3498, 3380, 1))
					&& !Player.getPosition().equals(new RSTile(3490, 3413, 0))
					&& !Player.getPosition().equals(new RSTile(3497, 3381, 1))
					&& !Player.getPosition().equals(new RSTile(3522, 3285, 0))) {
				Var.status = "Heading to the bank";
				Walking.setWalkingTimeout(1);
				Pathing.goToBank();
				return General.random(10, 30);
			}
			if (!Pathing.isInBarrows() && !Tunnel.inCrypt()
					&& Rooms.getRoom() == null && !BankHandler.needToBank()) {
				if (Rooms.getRoom() == null) {
					Pathing.getToBarrows();
				}
				return General.random(10, 30);
			}
			if (!Tunnel.isCompleted() || !Pathing.isInBarrows()) Brothers.statusCheck();
			if (Pathing.isInBarrows() && BrotherKilling.canKill()) {
				BrotherKilling.StartFight();
				return General.random(10, 30);
			}
			if (Pathing.isInBarrows() && !BrotherKilling.canKill()
					&& !Tunnel.isCompleted() || !BrotherKilling.canKill()
					&& Tunnel.inCrypt() && !Tunnel.isCompleted()) {
				Tunnel.goToTunnel();
				return General.random(10, 30);
			}
			if (!BrotherKilling.canKill() && Tunnel.inCrypt()
					&& Tunnel.isCompleted()) {
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
					&& Tunnel.isCompleted()) {
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
		if (TrialVersionHandler.isAuthorized()) {
			GeneralMethods.updateTracker("Script Runs", 1);
			println("Thank you for using wBarrows "
					+ General.getTRiBotUsername()
					+ ", if you have any issues contact us on skype: wussupscripts / integerscripting");
			GeneralMethods.adjustBrightness();
			activateGUI();
			Brothers.statusCheck();
			new Thread(new PriceHandler()).start();
			activateTable();
			while (Var.guiWait) {
				sleep(100);
			}
			Var.startTime = System.currentTimeMillis();
		}
	}

	private void activateTable() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Var.frame = new LootTable();
					Var.frame.setVisible(false);
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

	boolean showPaint = true;

	@Override
	public void onPaint(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;
		if (showPaint)
			PaintHandler.drawPaint(g);
		else{
			g.draw(closePaint);
			g.drawString("Enable Paint", (closePaint.x - 80), (closePaint.y + 15));
		}
	}

	Rectangle paint = new Rectangle(0, 340, 480, 140);
	Rectangle closePaint = new Rectangle(487, 343, 15, 15);

	@Override
	public void mouseClicked(Point p, int arg1, boolean arg2) {
		if (!arg2 && closePaint.contains(p)) {
			showPaint = !showPaint;
		} else {
			if (!arg2 && paint.contains(p)) {
				Var.frame.setVisible(getState());
			}
		}
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
		if (Var.frame.isVisible())
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
					+ General.getTRiBotUsername().replace(" ", "_") + ".png");
			if (Var.trial)
				TrialVersionHandler.updateTrial(General.getTRiBotUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
