package scripts.Barrows.main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.SwingUtilities;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

import scripts.Barrows.gui.BarrowGUI;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.Brother.Brothers;

public class Barrows extends Script implements Painting{

	@Override
	public void run() {
		onStart();		
		while (Var.running) {
			sleep(loop());
		}
	}

	private int loop() {
		Mouse.setSpeed(General.random(250,350));
		BrotherKilling.StartFight();
		return 50;
	}

	private void onStart() {
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
			b.getDigArea().drawArea(g);
		}
		if (Var.status != null) {
			g.drawString(Var.status, 574, 376);
		}
		

	}
	
}
