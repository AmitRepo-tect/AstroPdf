package com.astroganit.calculation.util;

public class Util {
	public static String getValuesFromDictionary(String key, int langCode) {
		// String returnVal = key;

		String returnVal = "";

		if (langCode != 0) {
			// returnVal = "";
			if (key.indexOf(",") > 0) {
				String[] splitedvalues = key.split(",");
				for (int i = 0; i < splitedvalues.length; i++) {
					returnVal = returnVal + splitedvalues[i] + ",";
				}
				returnVal = returnVal.replaceAll(",$", "");
			} else {

				// HashMap<String, String> hm = new HashMap<String, String>();
				String[] dictionaryConstants = DictionaryConstants.langConstants[langCode];
				for (int i = 0; i <= 67; i++) {
					String[] keys = DictionaryConstants.EngConstantValues[i].split("ÿ");
					String[] values = dictionaryConstants[i].split("ÿ");
					if (keys.length != values.length) {
						System.out.println("Length Mismatch : KEYS LENGTH : " + keys.length + ", VALUES : "
								+ values.length + " at i : " + i + ", key : " + keys[0]);
					}
					for (int j = 0; j < keys.length; j++) {
						if (keys[j].trim().equals(key)) {
							returnVal = values[j].trim();
							break;
						}
					}
					if (!returnVal.equals("")) {
						break;
					}

				}

			}

		}
		if (returnVal.equals("")) {
			returnVal = key;
		}

		return returnVal;
	}
}
