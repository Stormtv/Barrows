package scripts.Barrows.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.tribot.util.Util;

import scripts.Barrows.methods.Pathing;
import scripts.Barrows.types.Brother.Brothers;
import scripts.Barrows.types.Var;
import scripts.Barrows.types.enums.Food;
import scripts.Barrows.types.enums.Magic;
import scripts.Barrows.types.enums.Prayer;

public class GUISave {
	private static FileOutputStream streamO;
	private static FileInputStream streamI;
	
	public static void save() throws IOException {
		Properties prop = new Properties();
		for (Brothers b : Brothers.values()) {
			prop.setProperty(b.getName()+"KillOrder", Integer.toString(b.killOrder()));
			prop.setProperty(b.getName()+"wEquipment", Arrays.deepToString(b.getEquipmentIds()));
			prop.setProperty(b.getName()+"Spell", b.getSpell().toString());
			prop.setProperty(b.getName()+"Prayer", b.getPrayer().toString());
			prop.setProperty(b.getName()+"Potions", Boolean.toString(b.usePotions()));
			prop.setProperty(b.getName()+"selectedStance", Integer.toString(b.getSelectedStance()));
		}
		prop.setProperty("food", Var.food.toString());
		prop.setProperty("foodAmount", Integer.toString(Var.foodAmount));
		prop.setProperty("superAttack", Integer.toString(Var.superAttack));
		prop.setProperty("superStrength", Integer.toString(Var.superStrength));
		prop.setProperty("superDefence", Integer.toString(Var.superDefence));
		prop.setProperty("rangingPotion",Integer.toString(Var.rangingPotion));
		prop.setProperty("prayerPotion", Integer.toString(Var.prayerPotion));
		prop.setProperty("arrows", Integer.toString(Var.arrowCount));
		prop.setProperty("casts", Integer.toString(Var.spellCount));
		prop.setProperty("TunnelEquip", Arrays.deepToString(Var.tunnelEquipment));
		prop.setProperty("tunnelStance", Integer.toString(Var.tunnelStance));
		prop.setProperty("bankPath",Var.bankPath.toString());
		prop.setProperty("barrowsPath", Var.barrowsPath.toString());
		prop.setProperty("recharge",Boolean.toString(Var.recharge));
		prop.setProperty("killCount", Integer.toString(Var.killCount));
		prop.setProperty("minFood", Integer.toString(Var.nextRunFood));
		prop.setProperty("minDoses", Integer.toString(Var.nextRunDoses));
		prop.setProperty("mouseSpeed", Integer.toString(Var.mouseSpeed));
		try {
			boolean exist = (new File(Util.getWorkingDirectory()+File.separator+"barrows").mkdirs());
			if(exist) {
				System.out.println("Directory barrows was created");
			}
			streamO = new FileOutputStream(Util.getWorkingDirectory()
					+ File.separator + "barrows" + File.separator
					+ Var.fileName + ".ini");
			prop.store(streamO, null);
			streamO.flush();
			streamO.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void load() throws IOException {
		try {
			Properties prop = new Properties();
			streamI = new FileInputStream(Util.getWorkingDirectory()
					+ File.separator + "barrows" + File.separator
					+ Var.fileName + ".ini");
			prop.load(streamI);
			for (Brothers b : Brothers.values()) {
				b.setKillOrder(Integer.valueOf(prop.getProperty(b.getName()+"KillOrder")));
				b.setEquipmentIds(parseTwoDimensionalIntArray(prop.getProperty(b.getName()+"wEquipment")));
				for (Magic.Spell s : Magic.Spell.values()) {
					if (prop.getProperty(b.getName()+"Spell").equals(s.toString())) {
						b.setSpell(s);
					}
				}
				for (Prayer.Prayers p : Prayer.Prayers.values()) {
					if (prop.getProperty(b.getName()+"Prayer").equals(p.toString())) {
						b.setPrayer(p);
					}
				}
				b.setUsePotions(Boolean.parseBoolean(prop.getProperty(b.getName()+"Potions")));
				String stance = prop.getProperty(b.getName()+"selectedStance");
				if (stance != null) {
					b.setSelectedStance(Integer.valueOf(stance));
				}
			}
			for (Food.Edibles f : Food.Edibles.values()) {
				if (prop.getProperty("food").equals(f.toString())) {
					Var.food = f;
				}
			}
			Var.recharge = Boolean.parseBoolean(prop.getProperty("recharge"));
			Var.tunnelEquipment = parseTwoDimensionalIntArray(prop.getProperty("TunnelEquip"));
			String tunnelStance = prop.getProperty("tunnelStance");
			if (tunnelStance != null) {
				Var.tunnelStance = 	Integer.valueOf(tunnelStance);
			}
			Var.foodAmount = Integer.valueOf(prop.getProperty("foodAmount"));
			Var.superAttack = Integer.valueOf(prop.getProperty("superAttack"));
			Var.superStrength = Integer.valueOf(prop.getProperty("superStrength"));
			Var.superDefence = Integer.valueOf(prop.getProperty("superDefence"));
			Var.rangingPotion = Integer.valueOf(prop.getProperty("rangingPotion"));
			Var.prayerPotion = Integer.valueOf(prop.getProperty("prayerPotion"));
			Var.arrowCount = Integer.valueOf(prop.getProperty("arrows"));
			Var.spellCount = Integer.valueOf(prop.getProperty("casts"));
			Var.killCount = Integer.valueOf(prop.getProperty("killCount"));
			Var.nextRunDoses = Integer.valueOf(prop.getProperty("minDoses"));
			Var.nextRunFood = Integer.valueOf(prop.getProperty("minFood"));
			Var.mouseSpeed = Integer.valueOf(prop.getProperty("mouseSpeed"));
			for (Pathing.PathBank p : Pathing.PathBank.values()) {
				if (prop.getProperty("bankPath").equals(p.toString())) {
					Var.bankPath = p;
				}
			}
			for (Pathing.PathBarrows p : Pathing.PathBarrows.values()) {
				if (prop.getProperty("barrowsPath").equals(p.toString())) {
					Var.barrowsPath = p;
				}
			}
			
			streamI.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static int[][] parseTwoDimensionalIntArray(String s) {
		String b = s;
		b = b.replace("[", "");
		b = b.replace("]]", "");
		b =	b.replace(" ", "");
		String[] lines = b.split("],");
		int width = lines.length;
		ArrayList<String[]> cells = new ArrayList<String[]>();
		for (String i : lines) {
			cells.add(i.split(","));
		}
		int[] heights = new int[cells.size()];
		for (int i=0; i < cells.size(); i++) {
			heights[i] = cells.get(i).length;
		}
		int[][] results = new int[width][];
		for (int i=0; i < cells.size();i++) {
			results[i] = new int[heights[i]];
		}
		for (int i = 0; i < width;i++) {
			for (int j = 0; j < heights[i];j++) {
				results[i][j] = Integer.parseInt(cells.get(i)[j]);
			}
		}
		return results;
	}
}
