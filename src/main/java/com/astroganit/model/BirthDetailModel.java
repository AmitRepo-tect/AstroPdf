package com.astroganit.model;

public class BirthDetailModel {
	long id = -1L;
	String name;
	String sex;
	DateTimeModel dateTimeModel;
	PlaceDetailModel placeDetailModel;
	String dst;
	String ayanamsa;
	String charting;
	String kphn;
	String languageCode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public DateTimeModel getDateTimeModel() {
		return dateTimeModel;
	}

	public void setDateTimeModel(DateTimeModel dateTimeModel) {
		this.dateTimeModel = dateTimeModel;
	}

	public PlaceDetailModel getPlaceDetailModel() {
		return placeDetailModel;
	}

	public void setPlaceDetailModel(PlaceDetailModel placeDetailModel) {
		this.placeDetailModel = placeDetailModel;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getAyanamsa() {
		return ayanamsa;
	}

	public void setAyanamsa(String ayanamsa) {
		this.ayanamsa = ayanamsa;
	}

	public String getCharting() {
		return charting;
	}

	public void setCharting(String charting) {
		this.charting = charting;
	}

	public String getKphn() {
		return kphn;
	}

	public void setKphn(String kphn) {
		this.kphn = kphn;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

}
