package com.astroganit.kundlilib.calculation;

import java.util.Calendar;

import com.astroganit.kundlilib.dhoro.DesktopHoro;
import com.astroganit.model.BirthDetailModel;
import com.astroganit.model.DateTimeModel;
import com.astroganit.model.TransitModel;

public class TransitCalculation {
	public TransitModel calculate(BirthDetailModel birthDetailModel) {

		BirthDetailModel birthDetailModelNew = getBirthDetailForTransit(birthDetailModel);
		int lagna = getDesktopHoro(birthDetailModel).getPositionForShodasvarg(0)[0];

		int[] plaRashiArr = getIntArray(getDesktopHoro(birthDetailModelNew).getPositionForShodasvarg(0), 0);
		plaRashiArr[plaRashiArr.length - 1] = lagna;
		TransitModel transitModel = new TransitModel();
		transitModel.setLagna(lagna);
		transitModel.setPlaRashiArray(plaRashiArr);

		return transitModel;
	}

	/*
	 * private fun getIntArrayFromString(planetPositionArr: IntArray, lagnaPos:
	 * Int): IntArray { val intArray = IntArray(13) val lagna =
	 * planetPositionArr[lagnaPos] for (i in 0 until planetPositionArr.size - 1) {
	 * intArray[i] = planetPositionArr[i + 1] } intArray[planetPositionArr.size - 1]
	 * = lagna return intArray }
	 */
	private int[] getIntArray(int[] planetPosition, int lagnaPos) {

		int[] intArray = new int[13];
		int lagna = planetPosition[lagnaPos];
		for (int i = 0; i < planetPosition.length - 1; i++) {
			intArray[i] = planetPosition[i + 1];
		}
		intArray[planetPosition.length - 1] = lagna;
		return intArray;
	}

	private DesktopHoro getDesktopHoro(BirthDetailModel birthDetailModel) {
		DesktopHoro desktopHoro = new DesktopHoro();
		try {
			desktopHoro.setName(birthDetailModel.getName());
			desktopHoro.setPlace(birthDetailModel.getPlaceDetailModel().getPlace());
			desktopHoro.setTimeZone(birthDetailModel.getPlaceDetailModel().getTimezoneStr());
			desktopHoro.setMaleFemale(birthDetailModel.getSex());
			desktopHoro.setSecondOfBirth(birthDetailModel.getDateTimeModel().getSec());
			desktopHoro.setMinuteOfBirth(birthDetailModel.getDateTimeModel().getMin());
			desktopHoro.setHourOfBirth(birthDetailModel.getDateTimeModel().getHrs());
			desktopHoro.setDayOfBirth(birthDetailModel.getDateTimeModel().getDay());
			desktopHoro.setMonthOfBirth(birthDetailModel.getDateTimeModel().getMonth());
			desktopHoro.setYearOfBirth(birthDetailModel.getDateTimeModel().getYear());
			desktopHoro.setDegreeOfLattitude(birthDetailModel.getPlaceDetailModel().getLatDeg());
			desktopHoro.setMinuteOfLattitude(birthDetailModel.getPlaceDetailModel().getLatMin());
			desktopHoro.setDegreeOfLongitude(birthDetailModel.getPlaceDetailModel().getLongDeg());
			desktopHoro.setMinuteOfLongitude(birthDetailModel.getPlaceDetailModel().getLongMin());
			desktopHoro.setEastWest(birthDetailModel.getPlaceDetailModel().getLongEW());
			desktopHoro.setNorthSouth(birthDetailModel.getPlaceDetailModel().getLatNS());
			desktopHoro.setLanguageCode(birthDetailModel.getLanguageCode());
			desktopHoro.setDST(birthDetailModel.getDst());
			desktopHoro.setKPHorarySeed(Integer.parseInt(birthDetailModel.getKphn()));
			desktopHoro.setAyanamsaType(Integer.parseInt(birthDetailModel.getAyanamsa()));
			desktopHoro.initialize();

			System.out.print(desktopHoro.getAsc());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return desktopHoro;
	}

	private BirthDetailModel getBirthDetailForTransit(BirthDetailModel birthDetailModel) {
		Calendar cal = Calendar.getInstance();
		DateTimeModel dateTimeModel = new DateTimeModel();
		dateTimeModel.setDay(String.valueOf(cal.get(Calendar.DATE)));
		dateTimeModel.setMonth(String.valueOf(cal.get(Calendar.MONTH)));
		dateTimeModel.setYear(String.valueOf(cal.get(Calendar.YEAR)));
		dateTimeModel.setHrs(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
		dateTimeModel.setMin(String.valueOf(cal.get(Calendar.MINUTE)));
		dateTimeModel.setSec(String.valueOf(cal.get(Calendar.SECOND)));
		BirthDetailModel birthDetailModel2 = new BirthDetailModel();
		birthDetailModel2.setName(birthDetailModel.getName());
		birthDetailModel2.setSex(birthDetailModel.getSex());
		birthDetailModel2.setPlaceDetailModel(birthDetailModel.getPlaceDetailModel());
		birthDetailModel2.setAyanamsa(birthDetailModel.getAyanamsa());
		birthDetailModel2.setDst(birthDetailModel.getDst());
		birthDetailModel2.setKphn(birthDetailModel.getKphn());
		birthDetailModel2.setLanguageCode(birthDetailModel.getLanguageCode());
		birthDetailModel2.setId(birthDetailModel.getId());
		birthDetailModel2.setDateTimeModel(dateTimeModel);

		return birthDetailModel2;

	}
}
