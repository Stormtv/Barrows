package scripts.Barrows.methods;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.main.Barrows;
import scripts.Barrows.methods.tunnel.Rooms;
import scripts.Barrows.types.Brother;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.Var;
import scripts.Barrows.util.Timer;

public class PaintHandler {

	static Image paint = getImage("http://i.imgur.com/L78CUru.png");
	static Image skull = getImage("http://i.imgur.com/cT9vKDD.png");
	static Image tunnel = getImage("http://i.imgur.com/OmCBk5T.png");

	private final static Font font1 = new Font("Arial", 0, 14);
	private final static Font font2 = new Font("Arial", 0, 13);

	public static void drawPaint(Graphics2D g) {
		if (Var.debug) {
			debugPaint(g);
		}
		g.drawImage(paint, -14, 262, null);
		g.setColor(Color.WHITE);
		g.setFont(font1);
		g.drawString(Barrows.runTime.toElapsedString(), 90, 385);

		g.drawString(formatNumber(Var.profit) + " ("
				+ formatNumber(getPerHour(Var.profit, Barrows.runTime)) + " /hr)",
				67, 409);
		g.drawString("WIP", 65, 432);
		g.drawString(Integer.toString(Var.chests)+" ("
				+ formatNumber(getPerHour(Var.chests, Barrows.runTime))+" /hr)", 235, 432);
		g.drawString(getRoom(Rooms.getRoom()), 290, 385);
		g.drawString(getRoom(Var.startingRoom), 290, 407);
		g.setFont(font2);
		if (Var.status!=null) {
			g.drawString(Var.status, 75, 456);
		}
		for(Brothers b : Brother.Brothers.values()){
			if(b.isKilled()){
				g.drawImage(skull, b.getX(), b.getY(), null);
			}
			if(b.isTunnel()){
				g.drawImage(tunnel, b.getX() + 30, b.getY(), null);
			}
		}
	}
	
	

	private static void debugPaint(Graphics g) {
		final Color tRed = new Color(255, 0, 0, 100);
		final Color tYellow = new Color(255, 255, 0, 100);
		final Color tBlue = new Color(0, 0, 255, 100);

		if (Var.debugObject != null && Var.debugObject.isOnScreen()
				&& Var.centerPoint != null  && Var.debugObject.getModel() != null
				) {
			g.setColor(tRed);
			g.drawPolygon(Var.debugObject.getModel().getEnclosedArea());
			g.setColor(tYellow);
			g.drawRect(Var.centerPoint.x - 7, Var.centerPoint.y - 7, 14, 14);
		}
		if (Var.targetTile != null && Var.targetTile.isOnScreen()) {
			g.setColor(Color.BLACK);
			g.drawPolygon(Projection.getTileBoundsPoly(Var.targetTile, 0));
			g.setColor(tRed);
			g.fillPolygon(Projection.getTileBoundsPoly(Var.targetTile, 0));
		}
		if (Var.viableTiles != null) {
			for (RSTile t : Var.viableTiles) {
				if (t.isOnScreen()) {
					g.setColor(Color.BLACK);
					g.drawPolygon(Projection.getTileBoundsPoly(t, 0));
					g.setColor(tBlue);
					g.fillPolygon(Projection.getTileBoundsPoly(t, 0));
				}
			}
		}
		g.setColor(Color.RED);
		g.drawString("wBarrows Beta", 5, 40);
	}
	
	private static String getRoom(Rooms.TunnelRoom r){
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
