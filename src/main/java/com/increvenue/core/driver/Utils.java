package com.increvenue.core.driver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.math.BigDecimal;
import java.math.MathContext;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;

public class Utils {

	public static double EPSILON = 0.01;
	public static String SEPARATOR = ",";
	public static String SEPARATOR_POINT_H_POINT = ";";
	public static String SEPARATOR_DOT = ".";
	public static String SEPARATOR_DASH = "-";
	public static final String SPL_CHARS = "!@#$%^&*_=+-/";
	public static String SIGN_MORE = ">";
	public static String SIGN_LESS_EQUAL = "<=";
	public static String SIGN_EQUAL = "=";
	private static final String[] WIN_RUNTIME = { "cmd.exe", "/C", };
	private static final String[] EMULATOR_WIN_RUNTIME = { "cmd.exe","/C",
			"cd \"/d C:\\Users\\Admin\\AppData\\Local\\Android\\Sdk\\tools\" && emulator -avd " };
	private static final String[] OS_LINUX_RUNTIME = { "/bin/bash", "-l", "-c" };
	static final String AB1 = "0123456789";
	static SecureRandom rnd = new SecureRandom();
	public static final String[] APK_COMMANDS_PM_CHECK_WIN = { "adb shell pm list packages -f io.appium.android.ime",
			"adb shell pm list packages -f io.appium.settings", "adb shell pm list packages -f io.appium.unlock" };
	public static final String[] APK_COMMANDS_PM_CHECK_MAC = {
			"adb shell pm list packages | grep io.appium.android.ime",
			"adb shell pm list packages | grep io.appium.settings",
			"adb shell pm list packages | grep io.appium.unlock" };

	/**
	 * method for verification if value is null
	 * 
	 * @param sValue
	 * @return
	 */
	public static Boolean isNull(String sValue) {
		return sValue == null || sValue.length() == 0 || sValue.equals("") || sValue.equals("null");
	}

	/**
	 * method for verification if value contains any digits
	 * 
	 * @param sValue
	 * @return
	 */
	public static Boolean containsDigit(String sValue) {
		for (char c : sValue.toCharArray()) {
			if (Character.isDigit(c))
				return true;
		}
		return false;
	}

	/**
	 * get massive of strings from string
	 * 
	 * @param data
	 *            string data
	 * @return string massive
	 */
	public static String[] getMassive(String data) {
		if (data.equals(""))
			return null;
		String[] str = data.split(SEPARATOR);
		return str;
	}

	/**
	 * get list from string
	 * 
	 * @param data
	 *            string data
	 * @param separator
	 *            separator
	 * @return list of strings
	 */
	public static List<String> getList(String data, String separator, Boolean isTrim) {
		if (isNull(data))
			return new ArrayList<String>();
		String[] str = data.split(separator);
		List<String> lst = new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			if (isTrim)
				lst.add(str[i].trim());
			else
				lst.add(str[i]);
		}
		return lst;
	}

	/**
	 * get list from string
	 * 
	 * @param data
	 *            string data
	 * @return list of strings
	 */
	public static List<String> getList(String data) {
		return getList(data, SEPARATOR, true);
	}

	/**
	 * get list from string
	 * 
	 * @param data
	 *            string data
	 * @param separator
	 *            - separator
	 * @return list of strings
	 */
	public static List<String> getList(String data, String separator) {
		return getList(data, separator, true);
	}

	/**
	 * get Date from String
	 * 
	 * @param textWithDate
	 *            - date in string
	 * @return date
	 */
	public static Date getDateFromString(String textWithDate) {
		try {
			textWithDate = textWithDate.substring(0, textWithDate.indexOf(" "));
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			return formatter.parse(textWithDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * method gets +2 month
	 * 
	 * @return +2 month
	 */
	public static String getNextMonth() {
		return getNextMonth(false);
	}

	/**
	 * method gets +2 month
	 * 
	 * @param isMonthAsNumber
	 *            - if month should be a number
	 */
	public static String getNextMonth(Boolean isMonthAsNumber) {
		return getMonth(2, isMonthAsNumber);
	}

	/**
	 * method gets current month
	 * 
	 * @return current month
	 */
	public static String getCurrentMonth() {
		return getCurrentMonth(false);
	}

	/**
	 * method gets current month
	 * 
	 * @param isMonthAsNumber
	 *            - if month should be a number
	 */
	public static String getCurrentMonth(Boolean isMonthAsNumber) {
		return getMonth(0, isMonthAsNumber);
	}

	/**
	 * method for getting required month
	 * 
	 * @param monthsPlusToCurrent
	 * @return month
	 */
	private static String getMonth(int monthsPlusToCurrent, Boolean isMonthAsNumber) {
		Calendar cal = Calendar.getInstance();
		int currentMonth = cal.get(Calendar.MONTH);
		if (currentMonth >= 10 && monthsPlusToCurrent != 0) {
			currentMonth = 1;
		}
		if (isMonthAsNumber) {
			return (monthsPlusToCurrent == 0) ? String.valueOf(currentMonth + 1)
					: String.valueOf(currentMonth + monthsPlusToCurrent);
		} else
			return new DateFormatSymbols(Locale.US).getMonths()[currentMonth + monthsPlusToCurrent];
	}

	/**
	 * method for getting previos month
	 * 
	 * @return month
	 */
	public static String getPreviosMonthTwoSymbols() {
		Calendar cal = Calendar.getInstance();
		String returnValue = null;
		int currentMonth = cal.get(Calendar.MONTH);
		if (currentMonth == 0) {
			currentMonth = 11;
		}
		returnValue = (currentMonth < 10) ? "0" + currentMonth : String.valueOf(currentMonth);
		return returnValue;
	}

	/**
	 * method for getting previos month
	 * 
	 * @return month
	 */
	public static String getPreviosMonth() {
		Calendar cal = Calendar.getInstance();
		int currentMonth = cal.get(Calendar.MONTH);
		if (currentMonth == 0) {
			currentMonth = 11;
		}
		return String.valueOf(currentMonth);
	}

	/**
	 * get current year
	 * 
	 * @return string
	 */
	public static String getCurrentYear() {
		return getYear(true);
	}

	/**
	 * get previous year
	 * 
	 * @return string
	 */
	public static String getNextYear() {
		return getYear(false);
	}

	/**
	 * get next year
	 * 
	 * @return string
	 */
	public static String getNextYearTwoSymbols() {
		Calendar cal = Calendar.getInstance();
		String currentYear = Integer.toString(cal.get(Calendar.YEAR) + 1);
		currentYear = currentYear.substring(2, currentYear.length());
		return currentYear;
	}

	/**
	 * get current year
	 * 
	 * @return string
	 */
	public static String getCurrentYearTwoSymbols() {
		Calendar cal = Calendar.getInstance();
		String currentYear = Integer.toString(cal.get(Calendar.YEAR));
		currentYear = currentYear.substring(2, currentYear.length());
		return currentYear;
	}

	/**
	 * method for getting required year
	 * 
	 * @param isCurrentYear
	 *            - is get current or previous year
	 */
	private static String getYear(Boolean isCurrentYear) {
		Calendar cal = Calendar.getInstance();
		String currentYear = Integer.toString(isCurrentYear ? cal.get(Calendar.YEAR) : (cal.get(Calendar.YEAR) + 1));
		return currentYear;
	}

	/**
	 * method for getting current day
	 * 
	 * @return current day
	 */
	public static String getDay() {
		Calendar cal = Calendar.getInstance();
		String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		return day;
	}

	/**
	 * method for getting current day
	 * 
	 * @return current day
	 */
	public static String getDayOfTheWeek() {
		Calendar cal = Calendar.getInstance();
		String day = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
		return day;
	}

	/**
	 * get count of day in current month
	 * 
	 * @return count of day
	 */
	public static Integer getCountOfDayInCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * get count of day in previous month
	 * 
	 * @return count of day
	 */
	public static Integer getCountOfDayInPreviuosMonth() {
		Calendar cal = Calendar.getInstance();
		// set previous month
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) == 0 ? 11 : cal.get(Calendar.MONTH) - 1,
				cal.get(Calendar.DAY_OF_MONTH));
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * method for getting current year
	 * 
	 * @return current day
	 */
	public static String getYear() {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		return year;
	}

	/**
	 * method for getting next day date
	 * 
	 * @param format
	 *            - date format
	 * @param dayPlusToCurrent
	 *            - day for plus to current day
	 * @return string
	 */
	public static String getNextDayDate(String format, int dayPlusToCurrent) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Calendar nextDate = Calendar.getInstance();
		nextDate.add(Calendar.DAY_OF_YEAR, dayPlusToCurrent);
		Date time = nextDate.getTime();
		return dateFormat.format(time);
	}

	/**
	 * method for getting current date
	 * 
	 * @return current date
	 */
	public static String getDate(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date time = Calendar.getInstance().getTime();
		return dateFormat.format(time);
	}

	public static int compareStrings(String s1, String s2) {
		if ((s1 == null || s1.length() == 0) && (s2 == null || s2.length() == 0))
			return 0;
		if (s1 == null || s1.length() == 0)
			return -1;
		if (s2 == null || s2.length() == 0)
			return 1;
		return s1.trim().compareToIgnoreCase(s2.trim());
	}

	public static int compareBoolean(Boolean s1, Boolean s2) {
		if (s1 == null && s2 == null)
			return 0;
		if (s1 == null)
			return -1;
		if (s2 == null)
			return 1;
		return s1.compareTo(s2);
	}

	public static int compareDouble(Double d1, Double d2) {
		if (d1 == null && d2 == null) {
			return 0;
		}
		if (d1 == null) {
			return -1;
		}
		if (d2 == null) {
			return 1;
		}
		if (Utils.compareDoubles(d1, d2)) {
			return 0;
		}
		return d1.compareTo(d2);
	}

	public static boolean compareDoubles(double arg1, double arg2) {
		return Math.abs(arg1 - arg2) <= EPSILON;
	}

	/**
	 * method returns string array form testdata string
	 * 
	 * @param yearString
	 *            - string in format 'year;1997-1900;Before 1900'
	 */
	public static ArrayList<String> getYearArray(String yearString) {
		List<String> list = getList(yearString);
		ArrayList<String> resultList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).contains("-") && !list.get(i).contains("--")) {
				String interval = list.get(i);
				Integer start = Integer.valueOf(interval.substring(0, interval.indexOf("-")));
				Integer end = Integer.valueOf(interval.substring(interval.indexOf("-") + 1, interval.length()));
				if (start < end) {
					System.out.println("Ascending order");
					for (int k = start; k <= end; k++) {
						resultList.add(String.valueOf(k));
					}
				} else {
					System.out.println("Descending order.");
					for (int k = start; k >= end; k--) {
						resultList.add(String.valueOf(k));
					}
				}
			} else {
				resultList.add(list.get(i));
			}
		}
		return resultList;
	}

	/**
	 * Method removes duplicates
	 * 
	 * @param productsList
	 *            products List
	 */
	public static void removeDuplicates(List productsList) {
		HashSet hs = new HashSet();
		hs.addAll(productsList);
		productsList.clear();
		productsList.addAll(hs);

	}

	/**
	 * compare string lists ignore case
	 * 
	 * @param list1
	 *            - the first list
	 * @param list2
	 *            - the second list
	 * @return boolean
	 */
	public static boolean compareStringListsIgnoreCase(List<String> list1, List<String> list2) {
		System.out.println("Compare lists:" + list1 + " and " + list2);
		boolean result = true;
		if (list1.size() != list2.size()) {
			System.out.println(
					"Different parameter - Sizes of lists" + " \"" + list1.size() + "\" != \"" + list2.size() + "\"");
			return false;
		} else {
			for (int i = 0; i < list1.size(); i++) {
				// for comparision of Delivery methods in Mobile APP.
				// Example: 'USPS First Class: FREE, (All states: 2-4 business
				// days. Delivery Monday-Saturday)' and '(All states: 2-4
				// business days. Delivery Monday-Saturday), USPS First Class:
				// FREE'
				if (list2.get(i).contains("(") && list2.get(i).contains(")")) {
					String textInBrackets = list2.get(i)
							.substring(list2.get(i).indexOf("("), list2.get(i).indexOf(")") + 1).replaceAll(",", "");
					String textWithoutBrackets = list2.get(i).replace(textInBrackets, "").replaceAll(",", "").trim();
					if (Utils.splitString(list1.get(i)).replaceAll(",", "").contains(Utils.splitString(textInBrackets))
							&& Utils.splitString(list1.get(i)).replaceAll(",", "")
									.contains(Utils.splitString(textWithoutBrackets))) {
						System.out.println("Equal parameters " + list1.get(i) + " = " + list2.get(i));
						result &= true;
					} else {
						System.out.println("Different parameters " + list1.get(i) + " != " + list2.get(i));
						result &= false;
					}
				} else if (Utils.splitString(list1.get(i)).equalsIgnoreCase(Utils.splitString(list2.get(i)))) {
					System.out.println("Equal parameters " + list1.get(i) + " = " + list2.get(i));
					result &= true;
				} else {
					System.out.println("Different parameters " + list1.get(i) + " != " + list2.get(i));
					result &= false;
				}
			}
		}
		return result;
	}

	/**
	 * get splited string
	 * 
	 * @param string
	 *            string for split
	 * @return splited string
	 */
	public static String splitString(String string) {
		String splitedString = string.replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "").replace("'", "")
				.replaceAll("\"", "").toLowerCase().trim();
		return splitedString;
	}

	/**
	 * round double value with scale 2
	 * 
	 * @param number
	 *            - number
	 * @return rounded number
	 */
	public static Double roundDouble(Double number) {
		BigDecimal bd = new BigDecimal(number, MathContext.DECIMAL32);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * round double value
	 * 
	 * @param number
	 *            - number
	 * @param scale
	 *            - scale
	 * @return rounded number
	 */
	public static Double roundDouble(Double number, int scale) {
		return roundDouble(number, scale, BigDecimal.ROUND_UP);
	}

	/**
	 * round double value
	 * 
	 * @param number
	 *            - number
	 * @param scale
	 *            - scale
	 * @param roundMehod
	 *            - for example BigDecimal.ROUND_UP
	 * @return rounded number
	 */
	public static Double roundDouble(Double number, int scale, int roundMehod) {
		BigDecimal bd = new BigDecimal(number);
		bd = bd.setScale(scale, roundMehod);
		return bd.doubleValue();
	}

	/**
	 * round double value with scale 2
	 * 
	 * @param number
	 *            - number
	 * @return rounded number
	 */
	public static String roundDoubleToString(Double number) {
		return new java.text.DecimalFormat("0.00").format(roundDouble(number));
	}

	public static String getMonthNumber(String month) {
		return getMonthNumber(month, false);
	}

	/**
	 * get month number
	 * 
	 * @param month
	 *            - month name
	 * @param isRound2DigitRequred
	 *            is Round 2 Digit Required(example 02 and 2)
	 * @return String
	 */
	public static String getMonthNumber(String month, Boolean isRound2DigitRequired) {
		Integer returnValue = null;
		String returnValueString = null;
		if (month.equalsIgnoreCase("January"))
			returnValue = 1;
		if (month.equalsIgnoreCase("February"))
			returnValue = 2;
		if (month.equalsIgnoreCase("March"))
			returnValue = 3;
		if (month.equalsIgnoreCase("April"))
			returnValue = 4;
		if (month.equalsIgnoreCase("May"))
			returnValue = 5;
		if (month.equalsIgnoreCase("June"))
			returnValue = 6;
		if (month.equalsIgnoreCase("July"))
			returnValue = 7;
		if (month.equalsIgnoreCase("August"))
			returnValue = 8;
		if (month.equalsIgnoreCase("September"))
			returnValue = 9;
		if (month.equalsIgnoreCase("October"))
			returnValue = 10;
		if (month.equalsIgnoreCase("November"))
			returnValue = 11;
		if (month.equalsIgnoreCase("December"))
			returnValue = 12;
		if (isRound2DigitRequired) {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMinimumIntegerDigits(2);
			returnValueString = nf.format(returnValue);
		} else
			returnValueString = String.valueOf(returnValue);
		return returnValueString;
	}

	/**
	 * get month number
	 * 
	 * @param month
	 *            - month name
	 * @param isRound2DigitRequred
	 *            is Round 2 Digit Required(example 02 and 2)
	 * @return String
	 */
	public static String getMonthString(String monthNum) {
		String returnValue = null;

		if (monthNum == null)
			return returnValue;
		if (monthNum.length() == 1) {
			monthNum = "0" + monthNum;
		}
		if (monthNum.equalsIgnoreCase("01"))
			returnValue = "January";
		if (monthNum.equalsIgnoreCase("02"))
			returnValue = "February";
		if (monthNum.equalsIgnoreCase("03"))
			returnValue = "March";
		if (monthNum.equalsIgnoreCase("04"))
			returnValue = "April";
		if (monthNum.equalsIgnoreCase("05"))
			returnValue = "May";
		if (monthNum.equalsIgnoreCase("06"))
			returnValue = "June";
		if (monthNum.equalsIgnoreCase("07"))
			returnValue = "July";
		if (monthNum.equalsIgnoreCase("08"))
			returnValue = "August";
		if (monthNum.equalsIgnoreCase("09"))
			returnValue = "September";
		if (monthNum.equalsIgnoreCase("10"))
			returnValue = "October";
		if (monthNum.equalsIgnoreCase("11"))
			returnValue = "November";
		if (monthNum.equalsIgnoreCase("12"))
			returnValue = "December";
		return returnValue;
	}

	public static String generateAlpanumericStringWithSpecSymbols(int count) {
		String text;
		Random rnd = new Random();
		int alphanumericCount = count / 2;
		text = RandomStringUtils.randomAlphanumeric(alphanumericCount);
		for (int i = alphanumericCount; i <= count; i++) {
			text += SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
		}
		return text;
	}

	public Map<String, String> getAppStringMap(String language, String stringFile) {
		// TODO Auto-generated method stub
		return null;
	}

	private static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public List<String> runProcess(boolean isWin, String... command) {
		System.out.print("command to run: ");
		for (String s : command) {
			System.out.print(s);
		}
		System.out.print("\n");
		String[] allCommand = null;
		try {
			if (isWin) {
				allCommand = concat(WIN_RUNTIME, command);
			} else {
				allCommand = concat(OS_LINUX_RUNTIME, command);
			}
			ProcessBuilder pb = new ProcessBuilder(allCommand);
			pb.redirectErrorStream(true);
			Process p = pb.start();
			for (String s : command) {
				if (!s.contains("dumpsys")) {
					p.waitFor();
				}
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String _temp = null;
			List<String> line = new ArrayList<String>();
			while ((_temp = in.readLine()) != null) {
				System.out.println("temp line: " + _temp);
				line.add(_temp.trim());
			}
			return line;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean isWindows() {
		boolean flag = false;
		RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
		Map<String, String> systemProperties = runtimeBean.getSystemProperties();
		Set<String> keys = systemProperties.keySet();
		for (String key : keys) {
			systemProperties.get(key);
			// System.out.printf("[%s] = %s.\n", key, value);
		}
		if (systemProperties.containsValue("windows")) {
			System.out.println("Sysytem in use in Windows");
			flag = true;
		} else if (systemProperties.containsValue("Mac OS X")) {
			System.out.println("Sysytem in use in Mac");
			flag = false;
		}
		return flag;
	}

	public void uninstallPreviousVersionFileOnDevice(String UDID, String... cmd) throws Exception {
		try {
			boolean isWin = isWindows();
			for (String command : cmd) {
				if (isWin) {
					if ((UDID == null) || UDID.isEmpty()) {
						List<String> results = runProcess(isWin, "adb shell pm list packages -f " + command);
						if (results.toString().contains(command)) {
							runProcess(isWin, "adb uninstall " + command);
							System.out.println("'" + "adb uninstall " + command + "' is executed ");
						} else {
							System.out.println("Package '" + command + "' is not installed on device ");

						}
					} else {
						List<String> results = runProcess(isWin,
								"adb -s " + UDID + " shell pm list packages -f " + command);
						if (results.toString().contains(command)) {
							runProcess(isWin, "adb -s " + UDID + " uninstall " + command);
							System.out.println("'" + "adb -s " + UDID + " uninstall " + command + "' is executed ");
						} else {
							System.out.println("Package '" + command + "' is not installed on device ");

						}
					}

				} else {

					if ((UDID == null) || UDID.isEmpty()) {

						List<String> results = runProcess(isWin, "adb shell pm list packages -f " + command);
						if (results.toString().contains(command)) {
							runProcess(isWin, "adb uninstall " + command);
							System.out.println("'" + "adb uninstall " + command + "' is executed ");
						} else {
							System.out.println("Package '" + command + "' is not installed on device ");

						}

					} else {
						List<String> results = runProcess(isWin,
								"adb -s " + UDID + " shell pm list packages | grep " + command);
						if (results.toString().contains(command)) {
							runProcess(isWin, "adb -s " + UDID + " uninstall " + command);
							System.out.println("'" + "adb -s " + UDID + " uninstall " + command + "' is executed ");
						} else {
							System.out.println("Package '" + command + "' is not installed on device ");

						}
					}

				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * DESCRIPTION: To get Random numeric string PARAMETERS: int len RETURN:
	 * String
	 */
	public String generateRandomNumericString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB1.charAt(rnd.nextInt(AB1.length())));
		return sb.toString();
	}

	public void startEmulator(String emulaorName) {
		String command[] = {emulaorName};
		System.out.print("Stating " + command[0]);
		System.out.println("\n");
		String[] allCommand = null;
		try {
			if (isWindows()) {
				allCommand = concat(EMULATOR_WIN_RUNTIME, command);
			} else {
				allCommand = concat(OS_LINUX_RUNTIME, command);
			}
			ProcessBuilder pb = new ProcessBuilder(allCommand);
			pb.redirectErrorStream(true);
			Process p = pb.start();
			try {
				System.out.println("Waiting for "+command[0]+" to up and running");
				Thread.sleep(34000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String _temp = null;
			List<String> line = new ArrayList<String>();
			while ((_temp = in.readLine()) != null) {
				line.add(_temp);
				System.out.println("temp line: " + _temp);
				if(_temp.contains("emulator runs")){
					break;
				}
				
			}
			if(line.contains("emulator runs")){
			System.out.println(command[0]+" is up and running");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
