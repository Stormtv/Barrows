package scripts.Barrows;

import java.awt.Graphics;

import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

public class TunnelTest extends Script implements Painting {

	String[] actions;
	int[] modelIDs;
	RSObject[] doors;
	static int[] doorIDs = { 20691, 20696, 20715, 26082, 20701, 20710, 20694,
			20713, 20708, 20689 };

	static int[] doorRevised = { 20693, 20712, 20688, 20707, 20696, 20715,
			20695, 20714, 20677, 20669, 20705, 20686, 20700, 20681, 20687,
			20706, 20671 };

	@Override
	public void onPaint(Graphics g) {
		int i = 0;
		if (doors != null) {
			for (RSObject door : doors) {
				g.drawString("" + door.getModel().getPoints().length, 13,
						55 + 20 * i);
				i++;
			}
			/*
			 * Point pa = Projection.tileToMinimap(Player.getPosition()); if
			 * (Projection.isInMinimap(pa)) { g.setColor(Color.YELLOW);
			 * g.fillRect(pa.x, pa.y, 3, 3); } for (RSObject door : doors) { if
			 * (PathFinding.canReach(door.getPosition(), true)) { actions =
			 * door.getDefinition().getActions(); modelIDs =
			 * door.getDefinition().getModelIDs(); if (actions != null) { if
			 * (actions.length == 0) { g.setColor(Color.WHITE);
			 * g.drawString("No actions " + door.getDefinition().getName(), 13,
			 * 55 + 20 * i); Point p = Projection.tileToMinimap(door
			 * .getPosition()); if (Projection.isInMinimap(p)) {
			 * g.setColor(Color.RED); g.fillRect(p.x, p.y, 3, 3); } i++; } for
			 * (String s : actions) { if (s != null) { g.setColor(Color.WHITE);
			 * g.drawString(s + " " + door.getDefinition().getName(), 13, 55 +
			 * 20 * i); Point p = Projection.tileToMinimap(door .getPosition());
			 * if (Projection.isInMinimap(p)) { g.setColor(Color.GREEN);
			 * g.fillRect(p.x, p.y, 3, 3); } i++; } } } else {
			 * g.setColor(Color.WHITE); g.drawString("null", 13, 55 + 20 * i); }
			 * i = 0; if (modelIDs != null) { if (modelIDs.length == 0) {
			 * g.setColor(Color.WHITE);
			 * g.drawString("Model Length is 0",363,55+20*i); Point p =
			 * Projection.tileToMinimap(door .getPosition()); if
			 * (Projection.isInMinimap(p)) { g.setColor(Color.RED);
			 * g.fillRect(p.x, p.y, 3, 3); } i++; } for (int a : modelIDs) {
			 * g.setColor(Color.WHITE); g.drawString(""+a, 363, 55 + 20 * i);
			 * Point p = Projection.tileToMinimap(door .getPosition()); if
			 * (Projection.isInMinimap(p)) { g.setColor(Color.GREEN);
			 * g.fillRect(p.x, p.y, 3, 3); } i++; } } i++; } }
			 */
		} else {
			println("Loading");
			sleep(1000);
		}
	}

	@Override
	public void run() {
		while (true) {
			doors = Objects.find(20, doorRevised);
		}
	}

}
