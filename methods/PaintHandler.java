package scripts.Barrows.methods;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import scripts.Barrows.main.Barrows;
import scripts.Barrows.types.Var;

import scripts.Barrows.util.Timer;
import scripts.Barrows.methods.tunnel.Rooms;

public class PaintHandler {

	static Image paint = getImage("http://i.imgur.com/j4Evq7u.png");

	private final static Font font1 = new Font("Arial", 0, 14);
	private final static Font font2 = new Font("Arial", 0, 13);

	public static void drawPaint(Graphics2D g) {
		g.drawImage(paint, -14, 262, null);
		g.setColor(Color.WHITE);
		g.setFont(font1);
		g.drawString("" + Barrows.runTime.toElapsedString(), 90, 385);

		g.drawString("" + formatNumber(Var.profit) + " ("
				+ formatNumber(getPerHour(Var.profit, Barrows.runTime)) + " /hr)",
				67, 409);
		g.drawString("add later", 65, 432);
		g.drawString("add later", 75, 456);
		g.drawString(""+getRoom(Rooms.getRoom()), 290, 385);
		g.drawString(""+getRoom(Var.startingRoom), 290, 407);
		g.setFont(font2);
		g.drawString(""+ Var.status, 235, 432);

	}
	
	static String getRoom(Rooms.TunnelRoom r){
		if(r == null)
			return "None";
		else
			return r.toString();
	}
	


	public static int getPerHour(int done, final Timer t) {
		if (done == 0)
			return 0;
		return (int) (done * 3600000l / t.getElapsed());
	}

	public static String formatNumber(int start) {
		DecimalFormat nf = new DecimalFormat("0");
		double i = start;
		if (i >= 10000000) {
			return nf.format((i / 1000000)) + "M";
		}
		if (i >= 1000) {
			return nf.format((i / 1000)) + "k";
		}
		return "" + start;
	}

	private static Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			return null;
		}
	}

}
