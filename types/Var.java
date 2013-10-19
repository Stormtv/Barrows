package scripts.Barrows.types;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Barrows.gui.BarrowGUI;
import scripts.Barrows.methods.Pathing;
import scripts.Barrows.methods.Pathing.PathBarrows;
import scripts.Barrows.methods.tunnel.Rooms.TunnelRoom;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.enums.Food;
import scripts.Barrows.util.RSArea;

public class Var {

	public static final RSArea veracDig = new RSArea(new RSTile[] {
			new RSTile(3555, 3300, 0), new RSTile(3559, 3300, 0),
			new RSTile(3559, 3296, 0), new RSTile(3555, 3296, 0) });

	public static final int ECTOPHIAL = 4251;
	public static final int EMPTY_ECTOPHIAL = 4252;

	public static final int[] armour_ids = { 4726, 4730, 4728, 4724, 4708, 4710, 4712,
			4714, 4716, 4718, 4720, 4722, 4732, 4734, 4736, 4738, 4745,
			4747, 4749, 4751, 4753, 4755, 4757, 4759 };

	public static final RSArea dharokDig = new RSArea(new RSTile[] {
			new RSTile(3574, 3301, 0), new RSTile(3578, 3301, 0),
			new RSTile(3578, 3297, 0), new RSTile(3574, 3297, 0) });

	public static int trips = 0;
	public static int pieces = 0;

	public final static int SPADE_ID = 952;

	public static final RSArea ahrimDig = new RSArea(new RSTile[] {
			new RSTile(3563, 3291, 0), new RSTile(3567, 3291, 0),
			new RSTile(3567, 3287, 0), new RSTile(3563, 3287, 0) });

	public static final RSArea guthanDig = new RSArea(new RSTile[] {
			new RSTile(3575, 3284, 0), new RSTile(3579, 3284, 0),
			new RSTile(3579, 3280, 0), new RSTile(3575, 3280, 0) });

	public static final RSArea karilDig = new RSArea(new RSTile[] {
			new RSTile(3564, 3277, 0), new RSTile(3567, 3277, 0),
			new RSTile(3567, 3274, 0), new RSTile(3564, 3274, 0) });

	public static final RSArea toragDig = new RSArea(new RSTile[] {
			new RSTile(3552, 3285, 0), new RSTile(3556, 3285, 0),
			new RSTile(3556, 3281, 0), new RSTile(3552, 3281, 0) });

	public static final RSArea barrowsArea = new RSArea(new RSTile[] {
			new RSTile(3562, 3307, 0), new RSTile(3557, 3307, 0),
			new RSTile(3552, 3304, 0), new RSTile(3550, 3300, 0),
			new RSTile(3547, 3295, 0), new RSTile(3548, 3290, 0),
			new RSTile(3545, 3286, 0), new RSTile(3546, 3281, 0),
			new RSTile(3548, 3276, 0), new RSTile(3553, 3275, 0),
			new RSTile(3558, 3273, 0), new RSTile(3561, 3269, 0),
			new RSTile(3566, 3268, 0), new RSTile(3571, 3270, 0),
			new RSTile(3575, 3272, 0), new RSTile(3580, 3274, 0),
			new RSTile(3583, 3278, 0), new RSTile(3583, 3283, 0),
			new RSTile(3581, 3288, 0), new RSTile(3582, 3293, 0),
			new RSTile(3583, 3298, 0), new RSTile(3581, 3303, 0),
			new RSTile(3578, 3307, 0), new RSTile(3573, 3307, 0),
			new RSTile(3568, 3308, 0) });

	public static final RSArea bankArea = new RSArea(new RSTile[] {
			new RSTile(3509, 3483, 0), new RSTile(3516, 3483, 0),
			new RSTile(3516, 3478, 0), new RSTile(3514, 3476, 0),
			new RSTile(3512, 3476, 0), new RSTile(3512, 3474, 0),
			new RSTile(3509, 3474, 0), new RSTile(3507, 3480, 0) });

	public static final boolean debug = false;

	public static Brothers curBrother;

	public static boolean running = true;

	public static BarrowGUI gui;

	public static boolean guiWait = true;

	public static Food.Edibles food;

	public static int superAttack, superStrength, superDefence, rangingPotion,
			prayerPotion, foodAmount;

	public static String status;

	public static boolean lootedChest = false;

	public static Pathing.PathBank bankPath;

	public static Pathing.PathBarrows barrowsPath = PathBarrows.SHORTCUT;

	public static int[] tunnelEquipment;

	public static TunnelRoom startingRoom;

	public static int arrowId;

	public static RSObject debugObject;

	public static Point centerPoint;

	public static long startTime;

	public static long runTime;

	public static int arrowCount;

	public static int spellCount;

	public static RSTile targetTile;

	public static ArrayList<RSTile> viableTiles;

	public static int[] lootIDs;

	public static HashMap<Integer, Integer> priceTable = new HashMap<Integer, Integer>();

	public static HashMap<Integer, Integer> lootCount = new HashMap<Integer, Integer>();

	public static int profit = 0;

	public static int chests;
}
