package com.astroganit.kundlilib.calculation;

import com.astroganit.kundlilib.dhoro.DesktopHoro;

public class AshtakVargaCalculation {
	DesktopHoro desktopHoro;

	public AshtakVargaCalculation(DesktopHoro desktopHoro) {
		this.desktopHoro = desktopHoro;
	}

	public String[] getAshtakVargaData() {
		String[] ashtakvargaRashi = new String[12];
		for (int i = 0; i < 12; i++) {
			StringBuilder ashatakVarga = new StringBuilder();
			for (int j = 0; j < 7; j++) {
				ashatakVarga.append(desktopHoro.getAshtakvargaBinduForSignAndPlanet(j, i)).append(",");
			}
			ashtakvargaRashi[i] = ashatakVarga.toString();
		}
		return ashtakvargaRashi;
	}

}
