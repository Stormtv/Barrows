package scripts.Barrows.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
			prop.setProperty(b.getName()+"Equipment", b.getEquipment().toString());
			prop.setProperty(b.getName()+"Spell", b.getSpell().toString());
			prop.setProperty(b.getName()+"Prayer", b.getPrayer().toString());
			prop.setProperty(b.getName()+"Potions", Boolean.toString(b.usePotions()));
		}
		prop.setProperty("food", Var.food.toString());
		prop.setProperty("foodAmount", Integer.toString(Var.foodAmount));
		prop.setProperty("superAttack", Integer.toString(Var.superAttack));
		prop.setProperty("superStrength", Integer.toString(Var.superStrength));
		prop.setProperty("superDefence", Integer.toString(Var.superDefence));
		prop.setProperty("rangingPotion",Integer.toString(Var.rangingPotion));
		prop.setProperty("prayerPotion", Integer.toString(Var.prayerPotion));

		prop.setProperty("bankPath",Var.bankPath.toString());
		prop.setProperty("barrowsPath", Var.barrowsPath.toString());
		
		try {
			boolean exist = (new File(Util.getWorkingDirectory()+File.separator+"barrows").mkdirs());
			if(exist) {
				System.out.println("Directory barrows was created");
			}
			streamO = new FileOutputStream(Util.getWorkingDirectory()
					+ File.separator + "barrows" + File.separator
					+ ".ini");
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
					+ ".ini");
			prop.load(streamI);
			for (Brothers b : Brothers.values()) {
				b.setKillOrder(Integer.valueOf(prop.getProperty(b.getName()+"KillOrder")));
				b.setEquipmentIds(parseIntegerArray(prop.getProperty(b.getName()+"Equipment")));
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
			}
			for (Food.Edibles f : Food.Edibles.values()) {
				if (prop.getProperty("food").equals(f.toString())) {
					Var.food = f;
				}
			}
			Var.foodAmount = Integer.valueOf(prop.getProperty("foodAmount"));
			Var.superAttack = Integer.valueOf(prop.getProperty("superAttack"));
			Var.superStrength = Integer.valueOf(prop.getProperty("superStrength"));
			Var.superDefence = Integer.valueOf(prop.getProperty("superDefence"));
			Var.rangingPotion = Integer.valueOf(prop.getProperty("rangingPotion"));
			Var.prayerPotion = Integer.valueOf(prop.getProperty("prayerPotion"));
			
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
		}
	}
	
	private static int[] parseIntegerArray(String s) {
		String arr = s;
		String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\ ", "").split(",");
		int[] results = new int[items.length];
		for (int i = 0; i < items.length; i++) {
		    try {
		        results[i] = Integer.parseInt(items[i]);
		    } catch (NumberFormatException nfe) {};
		}
		return results;
	}
}
