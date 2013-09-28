package scripts.Barrows;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.tribot.api.General;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

import scripts.Barrows.methods.tunnel.Rooms;
import scripts.Barrows.methods.tunnel.TunnelDoor;
import scripts.Barrows.methods.tunnel.TunnelTraversing;
import scripts.Barrows.methods.tunnel.WTunnelTraverse;

public class TunnelTestPainter extends Script implements Painting {
	private Image i = getImage("http://i.imgur.com/TuV9Zmw.gif");
	private final float alpha = (float) 0.75;
	private TunnelDoor[] Pathway;
	@Override
	public void run() {
		super.setLoginBotState(false);
		long start = System.currentTimeMillis();
		while(!Rooms.getRoom().equals(Rooms.TunnelRoom.CC)) TunnelTraversing.traverseTunnel();
		General.println(System.currentTimeMillis()-start+" ms to reach chest");
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

