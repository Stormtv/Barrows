package scripts.Barrows;

import java.awt.Color;
import java.awt.Graphics;

import org.tribot.api.General;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

import scripts.Barrows.methods.Pathing;
import scripts.Barrows.types.Var;

public class TunnelTestPainter extends Script implements Painting {
	@Override
	public void run() {
		super.setLoginBotState(false);
		while(true) {
			General.sleep(100);
		}
	}
	
	private final Color tRed = new Color(255, 0, 0, 110);
	private final Color tYellow = new Color(255, 255, 0, 110);
	@Override
	public void onPaint(Graphics g) {
		if (Var.debug) {
			if (Var.debugObject != null && Var.debugObject.isOnScreen() 
					|| Var.centerPoint !=null ) {
				g.setColor(tRed);
				g.drawPolygon(Var.debugObject.getModel().getEnclosedArea());
				g.setColor(tYellow);
				g.drawRect(Var.centerPoint.x-7, Var.centerPoint.y-7, 14, 14);
			}
		}
	}
}

