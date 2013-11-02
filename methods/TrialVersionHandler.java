package scripts.Barrows.methods;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.tribot.api.General;

import scripts.Barrows.types.Var;

public class TrialVersionHandler {

	private static String checkUpLink = "http://polycoding.com/sigs/integer/list/index.php?a=checkup&t=0";

	public static boolean authorized = false;

	private static long lastTime = 0;

	private static int minutesInBetween = 5;

	public static void setAuthorized(String username) {
		if (isInDatabase(username)) {
			int minutes = getMinutesLeft(username);
			if (minutes > 0) {
				authorized = true;
			}else{
				General.println("Your trial has expired.");
			}
		} else {
			update(username, 60);
			authorized = true;
		}
	}

	public static boolean isAuthorized() {
		if (Var.trial) {
			return authorized;
		}
		return true;
	}

	public static String updateTrial(String username) {
		int minutes = getMinutesLeft(username);
		int minutesToBeSubmitted = (minutes - (int) Var.runTime
				.getElapsed() / 60000);
		update(username, minutesToBeSubmitted);
		lastTime = System.currentTimeMillis();
		General.println("updated " + username + " with "
				+ minutesToBeSubmitted + " minutes left.");
		return "";
	}

	public static boolean canUpdate() {
		return System.currentTimeMillis() > (lastTime + (minutesInBetween * 60000));
	}

	public static long fds() {
		return (lastTime + (minutesInBetween * 60000))
				- System.currentTimeMillis();
	}

	private static String update(final String username, final int minutesLeft) {
		final String unformattedUrl = "http://polycoding.com/sigs/integer/list/index.php?a=%s&t=%s";
		final String formattedUrl = String.format(unformattedUrl, username, ""
				+ minutesLeft);
		final StringBuilder builder = new StringBuilder();
		URL url = null;
		InputStream stream = null;
		try {
			url = new URL(formattedUrl);
			stream = url.openStream();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(stream));
			String line = br.readLine();
			while (line != null) {
				line = br.readLine();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			url = null;
			stream = null;
		}
		return builder.toString();
	}

	private static boolean isInDatabase(String username) {
		try {
			URL url = new URL(checkUpLink);
			URLConnection con = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				if (line.contains(username + "(")) {
					return true;
				}
			}
		} catch (Exception e) {

		}
		return false;
	}

	public static int getMinutesLeft(String username) {
		try {
			URL url = new URL(checkUpLink);
			URLConnection con = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			String[] names = sb.toString().split(",");
			for (String s : names) {
				if (s.contains(username)) {
					String num = s
							.substring(s.indexOf("(") + 1, s.indexOf(")"));
					return Integer.parseInt(num);
				}
			}
		} catch (Exception e) {

		}
		return 0;
	}

}
