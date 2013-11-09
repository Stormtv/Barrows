package scripts.Barrows.util;

import org.tribot.api.General;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSTile; 

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class RSArea {
	private final Polygon area;
	private final int plane;

	public RSArea(final RSTile[] tiles, final int plane) {
		area = tilesToPolygon(tiles);
		this.plane = plane;
	}

	public RSArea(final RSTile[] tiles) {
		this(tiles, 0);
	}

	public RSArea(final RSTile southwest, final RSTile northeast) {
		this(southwest, northeast, 0);
	}

	public RSArea(final int swX, final int swY, final int neX, final int neY) {
		this(new RSTile(swX, swY), new RSTile(neX, neY), 0);
	}

	public RSArea(final int swX, final int swY, final int neX, final int neY,
			final int plane) {
		this(new RSTile(swX, swY), new RSTile(neX, neY), plane);
	}

	public RSArea(final RSTile southwest, final RSTile northeast,
			final int plane) {
		this(new RSTile[] { southwest,
				new RSTile(northeast.getX() + 1, southwest.getY()),
				new RSTile(northeast.getX() + 1, northeast.getY() + 1),
				new RSTile(southwest.getX(), northeast.getY() + 1) }, plane);
	}

	public boolean contains(final RSTile... tiles) {
		final RSTile[] areaTiles = getTiles();
		for (final RSTile check : tiles) {
			for (final RSTile space : areaTiles) {
				if (check.equals(space)) {
					return true;
				}
			}
		}
		return false;
	}
 
    public boolean contains(final int x, final int y) {
        return this.contains(new RSTile(x, y));
    }
 
    public boolean contains(final int plane, final RSTile... tiles) {
        return this.plane == plane && this.contains(tiles);
    }
 
    public Rectangle getDimensions() {
        return new Rectangle(area.getBounds().x + 1, area.getBounds().y + 1, getWidth(), getHeight());
    }
 
    public RSTile getNearestTile(final RSTile base) {
        RSTile tempTile = null;
        for (final RSTile tile : getTiles()) {
            if (tempTile == null || distanceBetween(base, tile) < distanceBetween(tempTile, tile)) {
                tempTile = tile;
            }
        }
        return tempTile;
    }
 
    public int getPlane() {
        return plane;
    }
 
    public Polygon getPolygon() {
        return area;
    }
 
    public RSTile[] getTiles() {
        ArrayList<RSTile> tiles = new ArrayList<RSTile>();
        for (int x = getX(); x <= getX() + getWidth(); x++) {
            for (int y = getY(); y <= getY() + getHeight(); y++) {
                if (area.contains(x, y)) {
                    tiles.add(new RSTile(x, y));
                }
            }
        }
        return tiles.toArray(new RSTile[tiles.size()]);
    }
 
    public int getWidth() {
    	return area.getBounds().width;
    }
 
    public int getHeight() {
        return area.getBounds().height;
    }
 
    public int getX() {
        return area.getBounds().x;
    }
 
    public int getY() {
        return area.getBounds().y;
    }
 
    public Polygon tilesToPolygon(final RSTile[] tiles) {
        final Polygon polygon = new Polygon();
        for (final RSTile t : tiles) {
            polygon.addPoint(t.getX(), t.getY());
        }
        return polygon;
    }
    
    //Draws the area
	public void drawArea(Graphics g) {
		Rectangle2D b = area.getBounds2D();
		for (int x = (int) b.getMinX(); x <= (int) b.getMaxX(); x++) {
			for (int y = (int) b.getMinX(); y <= (int) b.getMaxY(); y++) {
				if (area.contains(x, y))
					drawTile(new RSTile(x, y), (Graphics2D) g, false);
			}
		}
	}

	// Draws the tile
	private void drawTile(RSTile tile, Graphics2D g, boolean fill) {
		if (tile.isOnScreen()) {
			if (fill)
				g.fillPolygon(Projection.getTileBoundsPoly(tile, 0));
			else
				g.drawPolygon(Projection.getTileBoundsPoly(tile, 0));
		}
	}
 
    public static double distanceBetween(RSTile curr, RSTile dest) {
        return Math.sqrt((curr.getX() - dest.getX()) * (curr.getX() - dest.getX()) + (curr.getY() - dest.getY()) * (curr.getY() - dest.getY()));
    }

    public RSTile getRandomTile() {
        Rectangle2D bounds = area.getBounds2D();
        RSTile tile = new RSTile(General.random((int)bounds.getMinX(), (int)bounds.getMaxX()), General.random((int)bounds.getMinY(), (int)bounds.getMaxY()));
        return area.contains(tile.getX(), tile.getY()) ? tile : getRandomTile();
    }
}