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

import scripts.Barrows.methods.tunnel.TunnelDoor;

public class TunnelTestPainter extends Script implements Painting {
	private Image i = getImage("http://i.imgur.com/TuV9Zmw.gif");
	private final float alpha = (float) 0.75;

	@Override
	public void run() {
		while(true)sleep(500);
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
	}
}

