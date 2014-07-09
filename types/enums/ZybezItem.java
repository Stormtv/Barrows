package scripts.Barrows.types.enums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scripts.Barrows.util.JSONArray;
import scripts.Barrows.util.JSONException;
import scripts.Barrows.util.JSONObject;

public class ZybezItem {
	private int zybezId;
	private String name;
	private String image;
	private int recentHigh;
	private int recentLow;
	private int average;
	private int highAlch;
	private Offer[] offers;

	public ZybezItem(String name) {
		JSONObject json = null;
		try {
			json = readJsonFromUrl("http://forums.zybez.net/runescape-2007-prices/api/item/"
					+ format(name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (json != null && json.has("id")) {
			try {
				this.zybezId = json.getInt("id");
				this.name = json.getString("name");
				this.image = json.getString("image");
				this.recentHigh = (int) Double.parseDouble(json
						.getString("recent_high"));
				this.recentLow = (int) Double.parseDouble(json
						.getString("recent_low"));
				this.average = (int) Double.parseDouble(json
						.getString("average"));
				this.highAlch = json.getInt("high_alch");
				JSONArray jsonOfferArr = json.getJSONArray("offers");
				this.offers = new Offer[jsonOfferArr.length()];
				for (int i = 0; i < jsonOfferArr.length(); i++) {
					this.offers[i] = new Offer(jsonOfferArr.getJSONObject(i),
							this.getName());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public ZybezItem(int id) {
		JSONObject json = null;
		try {
			json = readJsonFromUrl("http://forums.zybez.net/runescape-2007-prices/api/item/"
					+ format(getItemName(id)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (json != null && json.has("id")) {
			try {
				this.zybezId = json.getInt("id");
				this.name = json.getString("name");
				this.image = json.getString("image");
				this.recentHigh = (int) Double.parseDouble(json
						.getString("recent_high"));
				this.recentLow = (int) Double.parseDouble(json
						.getString("recent_low"));
				this.average = (int) Double.parseDouble(json
						.getString("average"));
				this.highAlch = json.getInt("high_alch");
				JSONArray jsonOfferArr = json.getJSONArray("offers");
				this.offers = new Offer[jsonOfferArr.length()];
				for (int i = 0; i < jsonOfferArr.length(); i++) {
					this.offers[i] = new Offer(jsonOfferArr.getJSONObject(i),
							this.getName());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the zybez id associated to the zybezItem (THESE ARE NOT THE SAME
	 *         AS RUNESCAPE IDS)
	 */
	public int getZybezId() {
		return zybezId;
	}

	/**
	 * @return the name of the zybezItem
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the URL of an image of the zybezItem
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @return the recent high price of the zybezItem
	 */
	public int getRecentHigh() {
		return recentHigh;
	}

	/**
	 * @return the recent low price of the zybezItem
	 */
	public int getRecentLow() {
		return recentLow;
	}

	public void setZybezId(int zybezId) {
		this.zybezId = zybezId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setRecentHigh(int recentHigh) {
		this.recentHigh = recentHigh;
	}

	public void setRecentLow(int recentLow) {
		this.recentLow = recentLow;
	}

	public void setAverage(int average) {
		this.average = average;
	}

	public void setHighAlch(int highAlch) {
		this.highAlch = highAlch;
	}

	public void setOffers(Offer[] offers) {
		this.offers = offers;
	}

	public int getAverage() {
		return average;
	}

	public int getHighAlch() {
		return highAlch;
	}

	/**
	 * @return an array of offer objects
	 */
	public Offer[] getOffers() {
		return offers;
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url)
				.openConnection();
		connection
				.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
		InputStreamReader is = new InputStreamReader(
				connection.getInputStream());
		try {
			BufferedReader rd = new BufferedReader(is);
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	private static String getItemName(int id) {
		/*
		 * Idea taken from WillB
		 */
		URL url;
		String txt = null;
		try {
			url = new URL("http://www.runelocus.com/item-details/?item_id="
					+ id);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			StringBuilder sb = new StringBuilder();
			while (reader.ready()) {
				sb.append(reader.readLine());
			}
			Pattern p = Pattern.compile("<h2>Information about '(.*?)'</h2>");
			Matcher m = p.matcher(sb.toString());
			if (m.find()) {
				txt = m.group(1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return txt;
	}

	private String format(String name) {
		return name.replace(" ", "+").replace("'", "");
	}
}
