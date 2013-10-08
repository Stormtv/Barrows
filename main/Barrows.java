package scripts.Barrows.main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.SwingUtilities;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

import scripts.Barrows.gui.BarrowGUI;
import scripts.Barrows.methods.Pathing;
import scripts.Barrows.methods.tunnel.Rooms;
import scripts.Barrows.methods.tunnel.Tunnel;
import scripts.Barrows.methods.tunnel.TunnelTraversing;
import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.Brother.Brothers;

public class Barrows extends Script implements Painting{

	@Override
	public void run() {
		onStart();		
		while (Var.running) {
			loop();
		}
	}

	private void loop() {
		Mouse.setSpeed(General.random(250,350));
		//Bank Check
		//Tunnel Check
		//More checks
		
		//Kill Test
		if (Pathing.isInBarrows() && BrotherKilling.canKill()) {
			BrotherKilling.StartFight();
			return;
		}
		if (Pathing.isInBarrows() && !BrotherKilling.canKill()
				|| !BrotherKilling.canKill() && Tunnel.inCrypt()) {
			Tunnel.goToTunnel();
			return;
		}
		if (Rooms.getRoom()!=null && !BrotherKilling.canKill() && !Tunnel.inCrypt()) {
			TunnelTraversing.traverseTunnel();
			return;
		}
	}

	private void onStart() {
		Brother.Brothers.Guthan.setKilled(true);
		Brother.Brothers.Ahrim.setKilled(true);
		Brother.Brothers.Dharok.setKilled(true);
		Brother.Brothers.Karil.setTunnel(true);
		Brother.Brothers.Torag.setKilled(true);
		Brother.Brothers.Verac.setKilled(true);
        activateGUI();
	}

	private void activateGUI() {
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{ 
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
		for (Brothers b: Brothers.values()) {
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
