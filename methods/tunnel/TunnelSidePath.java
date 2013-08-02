package scripts.Barrows.methods.tunnel;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import scripts.BarrowsScript;
import scripts.Barrows.methods.GeneralMethods;
import scripts.Barrows.util.RSArea;

public enum TunnelSidePath {

	WEST_PATH(Paths.westTiles), EAST_PATH(null);

	RSTile[] tilesInside;

	TunnelSidePath(RSTile[] tilesInside) {
		this.tilesInside = tilesInside;
	}

	RSTile[] getTilesInside() {
		return tilesInside;
	}

	public boolean isInside() {
		return canReachAny(getTilesInside());
	}

	public static boolean isInSidePath() {
		for (TunnelSidePath tsp : TunnelSidePath.values()) {
			if (canReachAny(tsp.getTilesInside()))
				return true;
		}
		return false;
	}

	private static boolean canReachAny(RSTile[] c) {
		if (c != null && c.length > 0) {
			for (RSTile t : c) {
				if (t.distanceTo(Player.getPosition()) < 10
						&& PathFinding.canReach(t, false))
					return true;
			}
		}
		return false;
	}

	public static TunnelSidePath getCurrentPath() {
		for (TunnelSidePath t : TunnelSidePath.values()) {
			if (t.isInside())
				return t;
		}
		return null;
	}

	public static RSTile[] getWalkableTiles() {
		int x = Player.getPosition().getX();
		int y = Player.getPosition().getY();
		int plane = Game.getPlane();
		RSArea k = new RSArea(new RSTile(x - 10, y - 10, plane), new RSTile(
				x + 10, y + 10, plane));

		ArrayList<RSTile> possible = new ArrayList<RSTile>();
		for (RSTile t : k.getTiles()) {
			if (t.distanceTo(Player.getPosition()) < 10
					&& PathFinding.canReach(t, false) && !possible.contains(t)) {
				possible.add(t);
			}
		}

		for (RSTile t : possible) {
			if (t.distanceTo(Player.getPosition()) > 10)
				possible.remove(t);
		}

		if (possible.size() > 0) {
			final RSTile[] array = possible
					.toArray(new RSTile[possible.size()]);
			BarrowsScript.k = GeneralMethods.getFurthestTileOnScreen(array);
			return array;
		}
		return null;
	}

	public static void walk() {
		if (!turnCamerasAccordingly()) {
			RSTile[] walkable = getWalkableTiles();
			if (walkable != null)
				GeneralMethods.walkScreen(walkable);

		}
	}

	public static boolean turnCamerasAccordingly() {
		if (isInSidePath()) {
			if ((Camera.getCameraRotation() < 0 || Camera.getCameraRotation() > 15)
					&& (getCurrentPath().equals(TunnelSidePath.WEST_PATH) || getCurrentPath()
							.equals(TunnelSidePath.EAST_PATH)))
				Camera.setCameraRotation(General.random(0, 15));

		}
		return false;
	}
}
