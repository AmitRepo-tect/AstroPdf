package com.astroganit.kundlilib.calculation;

import java.util.ArrayList;
import java.util.Calendar;

import com.astroganit.kundlilib.dhoro.DesktopHoro;
import com.astroganit.kundlilib.util.SUtil;
import com.astroganit.model.BirthDetailModel;
import com.astroganit.model.CharAntaraDashaModel;
import com.astroganit.model.YoginiDashaModel;
import com.astroganit.utils.ConstantHindi;

public class YoginiDashaCalculation {
	BirthDetailModel birthDetailModel;
	DesktopHoro desktopHoro;
	int[] duration = { 1, 2, 3, 4, 5, 6, 7, 8 };
	String[] dashaNameArr = { "eaxyk", "fiaxyk", "/kfu;k", "Hkzejh", "Hkfædk", "mYdk", "fl)k", "ladVk" };
	String[] dashaStart = new String[24];
	String[] dashaEnd = new String[24];
	String[] monthNames;
	Calendar cal = null;
	ConstantHindi constantHindi;

	public YoginiDashaCalculation(BirthDetailModel birthDetailModel, DesktopHoro desktopHoro) {
		this.birthDetailModel = birthDetailModel;
		this.desktopHoro = desktopHoro;
		constantHindi = new ConstantHindi();
	}

	private int getYoginiDasha() {
		int naksh = desktopHoro.getNakshatra();
		return (naksh + 3) % 8;
	}

	public ArrayList<YoginiDashaModel> getYoginiDashaData() {
		monthNames = constantHindi.monthShortName;

		ArrayList<YoginiDashaModel> arrayList = new ArrayList<YoginiDashaModel>();
		int currentDasha = getYoginiDasha();
		initStartAndEndDate(currentDasha);
		Calendar calendar = (Calendar) Calendar.getInstance().clone();
		calendar.set(Calendar.DATE, Integer.parseInt(birthDetailModel.getDateTimeModel().getDay()));
		calendar.set(Calendar.MONTH, Integer.parseInt(birthDetailModel.getDateTimeModel().getMonth()) - 1);
		calendar.set(Calendar.YEAR, Integer.parseInt(birthDetailModel.getDateTimeModel().getYear()));
		int[] balance = getBalanceOfDasha(desktopHoro.getMoon());
		calendar.add(Calendar.DATE, balance[2]);
		calendar.add(Calendar.MONTH, balance[1]);
		calendar.add(Calendar.YEAR, balance[0]);
		calendar.add(Calendar.YEAR, -duration[currentDasha]);
		String dashaName;

		for (int i = 0; i < 12; i++) {
			if (currentDasha > 7) {
				currentDasha = 0;
			}
			ArrayList<CharAntaraDashaModel> anterDashaArr = getAnterDasha(currentDasha, calendar);
			dashaName = dashaNameArr[currentDasha];
			anterDashaArr.get(0).setStartDate(dashaStart[i]);
			anterDashaArr.get(anterDashaArr.size() - 1).setEndDate(dashaEnd[i]);
			/*
			 * for (j in 0 until anterDashaArr.size) { Log.i( "Anter Dasha",
			 * anterDashaArr[j].planetName + "-" + anterDashaArr[j].startDate + "-" +
			 * anterDashaArr[j].endDate ) }
			 */

			arrayList.add(new YoginiDashaModel(dashaName, duration[currentDasha], dashaStart[i], dashaEnd[i],

					anterDashaArr));
			currentDasha++;
		}

		return arrayList;
	}

	private void initStartAndEndDate(int dasha) {
		int dasa = dasha;
		Calendar calendar = (Calendar) Calendar.getInstance().clone();
		calendar.set(Calendar.DATE, Integer.parseInt(birthDetailModel.getDateTimeModel().getDay()));
		calendar.set(Calendar.MONTH, Integer.parseInt(birthDetailModel.getDateTimeModel().getMonth()));
		calendar.set(Calendar.YEAR, Integer.parseInt(birthDetailModel.getDateTimeModel().getYear()));
		int[] balance = getBalanceOfDasha(desktopHoro.getMoon());
		calendar.add(Calendar.DATE, balance[2]);
		calendar.add(Calendar.MONTH, balance[1]);
		calendar.add(Calendar.YEAR, balance[0]);
		calendar.add(Calendar.YEAR, -duration[dasa]);
		String startDate;
		String endDate;
		for (int i = 0; i < 24; i++) {
			startDate = String.valueOf(calendar.get(Calendar.DATE)) + " "
					+ monthNames[calendar.get(Calendar.MONTH) % 12] + "] " + calendar.get(Calendar.YEAR);
			calendar.add(Calendar.YEAR, duration[dasa % 8]);
			endDate = String.valueOf(calendar.get(Calendar.DATE)) + " " + monthNames[calendar.get(Calendar.MONTH) % 12]
					+ "] " + calendar.get(Calendar.YEAR);
			dashaStart[i] = startDate;
			dashaEnd[i] = endDate;
			dasa++;
		}
	}

	private int[] getBalanceOfDasha(Double moonDeg) {
		int[] dateArr = new int[3];
		int var1 = (int) (moonDeg / 13.3333);
		++var1;
		double var2 = var1 * 13.3333 - moonDeg;
		double var3 = var2 * duration[getYoginiDasha()] / 13.333;
		dateArr[0] = (int) var3;
		double var4 = SUtil.fract(var3) * 12.0;
		dateArr[1] = (int) var4;
		dateArr[2] = (int) (SUtil.fract(var4) * 31.0);
		return dateArr;
	}

	private ArrayList<CharAntaraDashaModel> getAnterDasha(int dasha, Calendar startDate) {
		ArrayList<CharAntaraDashaModel> arrayList = new ArrayList<CharAntaraDashaModel>();
		if (cal == null) {
			cal = (Calendar) startDate.clone();
		}

		int anterDasha = dasha;
		String dashaName;
		String startDateStr = String.valueOf(cal.get(Calendar.DATE)) + "/" + (cal.get(Calendar.MONTH) + 1) + "/"
				+ cal.get(Calendar.YEAR);
		String endDate;
		double var11 = (double) duration[anterDasha] / 36.0;
		for (int i = 0; i < 8; i++) {
			if (anterDasha > 7) {
				anterDasha = 0;
			}
			dashaName = dashaNameArr[anterDasha];
			double var13 = var11 * duration[anterDasha];
			int var6 = (int) var13;
			double var17 = SUtil.fract(var13) * 12.0;
			int var8 = (int) var17;
			int var9 = (int) (SUtil.fract(var17) * 31.0);

			cal.add(Calendar.YEAR, var6);
			cal.add(Calendar.MONTH, var8);
			cal.add(Calendar.DATE, var9);
			endDate = String.valueOf(cal.get(Calendar.DATE)) + " " + monthNames[cal.get(Calendar.MONTH)] + "] "
					+ cal.get(Calendar.YEAR);
			arrayList.add(new CharAntaraDashaModel(dashaName, startDateStr, endDate));
			startDateStr = endDate;

			anterDasha++;
		}
		return arrayList;
	}
}
