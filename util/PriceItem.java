package scripts.Barrows.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.tribot.api2007.types.RSItem;

public class PriceItem {
	/**
	 * author: Integer
	 */

	private int id;
	private final String name;
	private final int price;

	public PriceItem(int id, String name, int price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public PriceItem(int id) {
		this.id = id;
		this.price = 0;
		this.name = "";
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}


	public static PriceItem lookup(RSItem r) {
		try {
			if (r.getDefinition() != null
					&& r.getDefinition().getName() != null
					&& r.getDefinition().getName().length() > 0)
				return new PriceItem(r.getID(), r.getDefinition().getName(),
						getPrice(r.getDefinition().getName()));
		} catch (Exception e) {

		}
		return null;
	}

	public static int getPrice(String name) {
		try {
			if (name != null && name.length() > 0) {
				URL url = new URL(
						"http://forums.zybez.net/runescape-2007-prices/api/"
								+ name.toLowerCase().replace(" ", "_")
										.replace("(1)", "").replace("(2)", "")
										.replace("(3)", "").replace("(4)", ""));
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection
						.setRequestProperty(
								"User-Agent",
								"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				String inputLine = "";
				int line = 0;
				while ((inputLine = in.readLine()) != null && line < 3) {
					if (inputLine.contains("average")) {
						String s = inputLine.substring(
								inputLine.indexOf("average") + 10,
								inputLine.indexOf("average") + 21);
						char c[] = s.toCharArray();
						StringBuilder sb = new StringBuilder();
						for (char l : c) {
							if (new String(l + "").equals(".")) {
								return Integer.parseInt(sb.toString());
							} else {
								sb.append(l);
							}
						}
					}
					line++;
				}
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	public void setID(int id) {
		this.id = id;
	}

}
