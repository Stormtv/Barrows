package scripts.Barrows;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

import scripts.Barrows.main.BrotherKilling;
import scripts.Barrows.methods.Pathing;
import scripts.Barrows.methods.tunnel.Rooms;
import scripts.Barrows.methods.tunnel.Tunnel;
import scripts.Barrows.methods.tunnel.TunnelDoor;
import scripts.Barrows.methods.tunnel.TunnelTraversing;
import scripts.Barrows.methods.tunnel.WTunnelTraverse;
import scripts.Barrows.types.Brother;

public class TunnelTestPainter extends Script implements Painting {
	private Image i = getImage("http://i.imgur.com/TuV9Zmw.gif");
	private final float alpha = (float) 0.75;
	private TunnelDoor[] Pathway;
	@Override
	public void run() {
		super.setLoginBotState(false);
		Brother.Brothers.Guthan.setTunnel(true);
		Brother.Brothers.Ahrim.setKilled(true);
		Brother.Brothers.Dharok.setKilled(true);
		Brother.Brothers.Karil.setKilled(true);
		Brother.Brothers.Torag.setKilled(true);
		Brother.Brothers.Verac.setKilled(true);
		Pathway = WTunnelTraverse.pathToChest();
		while(true) {
			if (Pathing.isInBarrows() && !BrotherKilling.canKill()
					|| Tunnel.inCrypt() && !BrotherKilling.canKill()) {
				Tunnel.goToTunnel();
				continue;
			}
			if (Rooms.getRoom()!=null) {
				TunnelTraversing.traverseTunnel();
				continue;
			}
		}
	}

	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			return null;
		}
	}
	
	@Override
	public void onPaint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g2d.setComposite(ac);
		g2d.drawImage(i,0,0,null);
		g.setColor(Color.RED);
		for (TunnelDoor d: TunnelDoor.values()) {
			if (!d.isOpenable()) {
				d.paint(g);
			}
		}
		g.setColor(Color.WHITE);
		if (Pathway !=null) {
			for (TunnelDoor d: Pathway) {
				d.paint(g);
			}
		}
	}
}

