package scripts.Barrows.types.enums;

import scripts.Barrows.util.JSONException;
import scripts.Barrows.util.JSONObject;

public class Offer {
	private String name;
	private int price;
	private boolean selling;
	private String rsName;
	private int quantity;
	private String notes;
	private int date;
	private String contact;
	private boolean validated;

	public Offer(JSONObject offer, String name) {
		try {
			this.name = name;
			this.price = offer.getInt("price");
			this.selling = (offer.getInt("selling") == 1);
			this.rsName = offer.getString("rs_name");
			this.quantity = offer.getInt("quantity");
			this.notes = offer.getString("notes");
			this.date = offer.getInt("date");
			this.contact = offer.getString("contact");
			this.setValidated((offer.getInt("validated") == 1));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the price of the offers buy order or sell order
	 */
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	/**
	 * @return the name of the offer item within the buy order or sell order
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return <code>true</code> if the offer is a sell order <code>false</code>
	 *         if the offer is a buy order
	 */
	public boolean isSelling() {
		return selling;
	}

	public void setSelling(boolean selling) {
		this.selling = selling;
	}

	/**
	 * @return the runescape username associated with the offer
	 */
	public String getRsName() {
		return rsName;
	}

	public void setRsName(String rsName) {
		this.rsName = rsName;
	}

	/**
	 * @return quantity of the offer
	 */
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the notes the player left on the offer
	 */
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the time the offer was placed as a unix date time
	 */
	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	/**
	 * @return How to contact this offer CC or PM, CC meaning clan chat and PM
	 *         meaning private message
	 */
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}
}
