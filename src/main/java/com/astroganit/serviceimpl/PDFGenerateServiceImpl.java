package com.astroganit.serviceimpl;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Service;
import com.astroganit.kundlilib.calculation.AshtakVargaCalculation;
import com.astroganit.kundlilib.calculation.ChalitCalculation;
import com.astroganit.kundlilib.calculation.KarkanshAndSwanshCalcultion;
import com.astroganit.kundlilib.calculation.PlanetAndSunPlanetPositionCalculation;
import com.astroganit.kundlilib.calculation.PrashtakVargaCalculation;
import com.astroganit.kundlilib.calculation.TransitCalculation;
import com.astroganit.kundlilib.calculation.YoginiDashaCalculation;
import com.astroganit.kundlilib.dhoro.DesktopHoro;
import com.astroganit.model.BasicPlanetDataModel;
import com.astroganit.model.BasicPlanetSubDataModel;
import com.astroganit.model.BirthDetailModel;
import com.astroganit.model.CharAntaraDashaModel;
import com.astroganit.model.KundliChalitTableModel;
import com.astroganit.model.PlanetDataModel;
import com.astroganit.model.PrastharashtakvargaModel;
import com.astroganit.model.TransitModel;
import com.astroganit.model.YoginiDashaModel;
import com.astroganit.service.PDFGenerateService;
import com.astroganit.utils.AxisPoint;
import com.astroganit.utils.ConstantHindi;
import com.astroganit.utils.DrawShape;

@Service
public class PDFGenerateServiceImpl implements PDFGenerateService {
	DesktopHoro desktopHoro;
	BirthDetailModel birthDetailModel;
	PDDocument document;
	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	PDPageContentStream contentStream;
	ConstantHindi constantHindi;
	DrawShape drawShape = new DrawShape();
	PDRectangle mediaBox;
	float pageWidth;
	float pageHeight;
	ArrayList<PrastharashtakvargaModel> prashtakVargaList;

	public byte[] generatePDF(BirthDetailModel birthDetailModel) throws IOException {
		try {
			this.birthDetailModel = birthDetailModel;
			constantHindi = new ConstantHindi();
			initHoro();
			document = new PDDocument();
			printFirstPage();
			printSecondPage();
			printThirdPage();
			printForthPage();
			printFifthPage();
			printSixthPage();
			document.save(byteArrayOutputStream);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return byteArrayOutputStream.toByteArray();
	}

	ArrayList<YoginiDashaModel> getYoginiDashaData() {
		YoginiDashaCalculation yoginiDashaCalculation = new YoginiDashaCalculation(birthDetailModel, desktopHoro);
		return yoginiDashaCalculation.getYoginiDashaData();
	}

	void addPageHeading(float pageHeight, float pageWidth) throws Exception {
		drawShape.drawLine(0, pageHeight - 30, pageWidth, pageHeight - 30);
		drawShape.drawText(pageWidth / 2 - 30, pageHeight - 20, "Astro Ganit", 12);
	}

	void printFirstPage() {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			mediaBox = page.getMediaBox();
			pageWidth = mediaBox.getWidth();
			pageHeight = mediaBox.getHeight();
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addPageHeading(pageHeight, pageWidth);
			printPersonalDetail(pageHeight, pageWidth);
			printPanchang(pageHeight, pageWidth);
			printAvakahadaChakar(pageHeight, pageWidth);
			contentStream.close();
		} catch (Exception e) {

		}
	}

	void printPersonalDetail(float pageHeight, float pageWidth) throws IOException {
		float x = 20;
		float y = pageHeight - 210;
		float h = 150;
		float w = 267;

		drawShape.drawKrutiDevText(20, pageHeight - 50, "tUe fooj.k", 16);
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, 4, 30);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + w / 2, y, x + w / 2, y + h));
		drawShape.drawColumn(points);
		String[] labels = constantHindi.birthDetailLabel;
		String[] values = { desktopHoro.getName(), desktopHoro.getBirthDate(), desktopHoro.getBirthTime(),
				desktopHoro.getPlace(), String.valueOf(desktopHoro.getAyan()) };
		printLabel(drawShape, x + 5, pageHeight - 80, labels);
		printValue(drawShape, x + (w / 2) + 5, pageHeight - 80, values);
	}

	void printPanchang(float pageHeight, float pageWidth) {
		try {
			float x = 20;
			float y = pageHeight - 510;
			float h = 270;
			float w = 267;
			drawShape.drawKrutiDevText(20, pageHeight - 230, "iapkax", 16);
			drawShape.drawRoundedRectangle(x, y, h, w);
			drawShape.drawRowAndColoum(x, y, h, w, 8, 30);
			ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
			points.add(new AxisPoint(x + w / 2, y, x + w / 2, y + h));
			drawShape.drawColumn(points);
			String[] panchangLabels = constantHindi.panchangLabel;

			printLabel(drawShape, x + 5, pageHeight - 260, panchangLabels);
			String[] values = { desktopHoro.getPakshaName(), desktopHoro.getTithiName(), desktopHoro.getNakshatraName(),
					desktopHoro.getHinduWeekdayName(), desktopHoro.getHinduWeekdayName(), desktopHoro.getYoganame(),
					desktopHoro.getKaranName(), desktopHoro.getSunRiseTimeHms(), desktopHoro.getSunSetTimeHms() };
			printValueWithDev(drawShape, x + (w / 2) + 5, pageHeight - 260, values);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void printAvakahadaChakar(float pageHeight, float pageWidth) {
		try {
			float x = 307;
			float y = pageHeight - 660;
			float h = 600;
			float w = 267;
			drawShape.drawKrutiDevText(x, pageHeight - 50, "vodgM+k pØ", 16);
			drawShape.drawRoundedRectangle(x, y, h, w);
			drawShape.drawRowAndColoum(x, y, h, w, 19, 30);
			ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
			points.add(new AxisPoint(x + w / 2, y, x + w / 2, y + h));
			drawShape.drawColumn(points);
			String[] panchangLabels = constantHindi.avakahadaChakarLabel;
			printLabel(drawShape, x + 5, pageHeight - 80, panchangLabels);
			String[] values = { desktopHoro.getPayaName(), desktopHoro.getVarnaName(), desktopHoro.getYoniName(),
					desktopHoro.getGanaName(), desktopHoro.getVasyaName(), desktopHoro.getNadiName(),
					desktopHoro.getBalanceOfDasaString(), "" + desktopHoro.getLagna(), desktopHoro.getLagnaLordName(),
					desktopHoro.getRasiName(), desktopHoro.getRasiLordName(), desktopHoro.getNakshatraName(),
					desktopHoro.getNakshatraLordName(), desktopHoro.getJulianDayValue(),
					desktopHoro.getIndianSunSignName(), desktopHoro.getSunSignName(), "" + desktopHoro.getAyanamsaDms(),
					"" + desktopHoro.getAyanamsaType(), desktopHoro.getObliquityDms(),
					desktopHoro.getSiderealTimeHms() };
			printValueWithDev(drawShape, x + (w / 2) + 5, pageHeight - 80, values);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void printSecondPage() {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addPageHeading(pageHeight, pageWidth);
			printLagnaChart();
			printMoonChart();
			printNavmanshChart();
			printPlanetPositionTable();
			printSubPlanetPositionTable();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		// addChart();

	}

	void printLagnaChart() throws IOException {
		drawShape.drawKrutiDevText(20, pageHeight - 50, "yXu pkVZ", 16);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 230f);
		float[] x = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y = { pageHeight - 230f + 90, pageHeight - 230f + 132, pageHeight - 230f + 122, pageHeight - 230f + 80,
				pageHeight - 230f + 40, pageHeight - 230f + 30, pageHeight - 230f + 70, pageHeight - 230f + 30,
				pageHeight - 230f + 40, pageHeight - 230f + 80, pageHeight - 230f + 122, pageHeight - 230f + 132 };
		int[] planetArray = getIntArray(desktopHoro.getPositionForShodasvarg(0), 0);
		drawRashiInBhav(x, y, planetArray[12]);
		printPlanetsInHouse(20f, pageHeight - 230f, planetArray, planetArray[12]);
	}

	void printMoonChart() throws IOException {
		drawShape.drawKrutiDevText(20, pageHeight - 250f, "paæ pkVZ", 16);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 430f);

		float[] x1 = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y1 = { pageHeight - 430f + 90, pageHeight - 430f + 132, pageHeight - 430f + 122, pageHeight - 430f + 80,
				pageHeight - 430f + 40, pageHeight - 430f + 30, pageHeight - 430f + 70, pageHeight - 430f + 30,
				pageHeight - 430f + 40, pageHeight - 430f + 80, pageHeight - 430f + 122, pageHeight - 430f + 132 };
		int[] planetArray = getIntArray(desktopHoro.getPositionForShodasvarg(0), 2);
		drawRashiInBhav(x1, y1, planetArray[12]);
		printPlanetsInHouse(20f, pageHeight - 430f, planetArray, planetArray[12]);
	}

	void printNavmanshChart() throws IOException {
		drawShape.drawKrutiDevText(20, pageHeight - 450f, "uoeka'k pkVZ", 16);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 630f);

		float[] x2 = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y2 = { pageHeight - 630f + 90, pageHeight - 630f + 132, pageHeight - 630f + 122, pageHeight - 630f + 80,
				pageHeight - 630f + 40, pageHeight - 630f + 30, pageHeight - 630f + 70, pageHeight - 630f + 30,
				pageHeight - 630f + 40, pageHeight - 630f + 80, pageHeight - 630f + 122, pageHeight - 630f + 132 };
		int[] planetArray = getIntArray(desktopHoro.getPositionForShodasvarg(5), 0);
		drawRashiInBhav(x2, y2, planetArray[12]);
		printPlanetsInHouse(20f, pageHeight - 630f, planetArray, planetArray[12]);
	}

	void printPlanetPositionTable() throws IOException {
		drawShape.drawKrutiDevText(210, pageHeight - 50, "xzg fLFkfr", 16);
		drawShape.drawTableHeader(210, pageHeight - 85, 25, 360);
		float x = 210;
		float y = pageHeight - 410f;
		float h = 350;
		float w = 360;
		drawShape.drawTableWithHeader(x, y, h, w, 25);
		drawShape.drawRaw(x, y, h, w, 13, 25);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 50, y, x + 50, y + 350));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 350));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 350));
		points.add(new AxisPoint(x + 300, y, x + 300, y + 350));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 60, x + 130, x + 230, x + 310 };
		ArrayList<BasicPlanetDataModel> list = new PlanetAndSunPlanetPositionCalculation(desktopHoro).getPlanetsData();
		populatePlanetTable(xaxis, y + 325, list);
	}

	void populatePlanetTable(float[] xaxis, float y, ArrayList<BasicPlanetDataModel> list) throws IOException {
		y = y + 8;
		BasicPlanetDataModel basicPlanetDataModel;
		for (int i = 0; i < list.size(); i++) {
			basicPlanetDataModel = list.get(i);
			drawShape.drawKrutiDevText(xaxis[0], y - (i + 1) * 25, basicPlanetDataModel.getPlaName(), 14);
			drawShape.drawKrutiDevText(xaxis[1], y - (i + 1) * 25, String.valueOf(basicPlanetDataModel.getSign()), 14);
			drawShape.drawText(xaxis[2], y - (i + 1) * 25, basicPlanetDataModel.getDegree(), 10);
			drawShape.drawKrutiDevText(xaxis[3], y - (i + 1) * 25, String.valueOf(basicPlanetDataModel.getNaks()), 14);
			drawShape.drawText(xaxis[4], y - (i + 1) * 25, String.valueOf(basicPlanetDataModel.getPlaCharan()), 10);
		}
	}

	void printSubPlanetPositionTable() throws IOException {

		drawShape.drawKrutiDevText(210, pageHeight - 430, "lc xzg fLFkfr", 16);
		drawShape.drawTableHeader(210, pageHeight - 465, 25, 360);
		float x = 210;
		float y = pageHeight - 790f;
		float h = 350;
		float w = 360;
		drawShape.drawTableWithHeader(x, y, h, w, 25);
		drawShape.drawRaw(x, y, h, w, 13, 25);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 50, y, x + 50, y + 350));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 350));
		points.add(new AxisPoint(x + 210, y, x + 210, y + 350));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 350));
		points.add(new AxisPoint(x + 310, y, x + 310, y + 350));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 60, x + 170, x + 220, x + 270, x + 320 };
		ArrayList<BasicPlanetSubDataModel> list = new PlanetAndSunPlanetPositionCalculation(desktopHoro)
				.getPlanetsSubData();
		populateSubPlanetTable(xaxis, y + 325, list);
	}

	void populateSubPlanetTable(float[] xaxis, float y, ArrayList<BasicPlanetSubDataModel> list) throws IOException {
		y = y + 8;
		BasicPlanetSubDataModel basicPlanetSubDataModel;
		for (int i = 0; i < list.size(); i++) {
			basicPlanetSubDataModel = list.get(i);
			drawShape.drawKrutiDevText(xaxis[0], y - (i + 1) * 25, basicPlanetSubDataModel.getPlaName(), 14);
			drawShape.drawText(xaxis[1], y - (i + 1) * 25, basicPlanetSubDataModel.getPlaDeg(), 10);
			drawShape.drawKrutiDevText(xaxis[2], y - (i + 1) * 25, basicPlanetSubDataModel.getSignLord(), 14);
			drawShape.drawKrutiDevText(xaxis[3], y - (i + 1) * 25, basicPlanetSubDataModel.getNakshLord(), 14);
			drawShape.drawKrutiDevText(xaxis[4], y - (i + 1) * 25, basicPlanetSubDataModel.getSubLord(), 14);
			drawShape.drawKrutiDevText(xaxis[5], y - (i + 1) * 25, basicPlanetSubDataModel.getSubSubLord(), 14);
		}
	}

	void printThirdPage() throws Exception {

		PDPage page = new PDPage(PDRectangle.A4);
		document.addPage(page);
		contentStream = new PDPageContentStream(document, page);
		contentStream.setLineWidth(1);
		contentStream.setStrokingColor(Color.GRAY);
		contentStream.setNonStrokingColor(Color.BLACK);
		drawShape.initialize(pageHeight, pageWidth, document, contentStream);
		addPageHeading(pageHeight, pageWidth);
		printChalitChart();
		printGocharChart();
		printKarkanshChart();
		printSwanshChart();
		printChalitTable();
		printAstakvargTable();
		contentStream.close();

	}

	void printChalitChart() throws IOException {
		drawShape.drawKrutiDevText(20, pageHeight - 50, "pfyr pkVZ", 16);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 230f);
		float[] x = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y = { pageHeight - 230f + 90, pageHeight - 230f + 132, pageHeight - 230f + 122, pageHeight - 230f + 80,
				pageHeight - 230f + 40, pageHeight - 230f + 30, pageHeight - 230f + 70, pageHeight - 230f + 30,
				pageHeight - 230f + 40, pageHeight - 230f + 80, pageHeight - 230f + 122, pageHeight - 230f + 132 };
		ChalitCalculation chalitCalculation = new ChalitCalculation(desktopHoro);
		int[] planetArray = chalitCalculation.getChalitChartArray();
		drawRashiInBhavForChalit(x, y, chalitCalculation.getKpDegreeArray());
		printPlanetsInHouse(20f, pageHeight - 230f, planetArray, planetArray[12]);
	}

	void printGocharChart() throws IOException {
		drawShape.drawKrutiDevText(20, pageHeight - 250f, "xkspj", 16);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 430f);

		float[] x1 = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y1 = { pageHeight - 430f + 90, pageHeight - 430f + 132, pageHeight - 430f + 122, pageHeight - 430f + 80,
				pageHeight - 430f + 40, pageHeight - 430f + 30, pageHeight - 430f + 70, pageHeight - 430f + 30,
				pageHeight - 430f + 40, pageHeight - 430f + 80, pageHeight - 430f + 122, pageHeight - 430f + 132 };

		TransitModel transitModel = new TransitCalculation().calculate(birthDetailModel);
		drawRashiInBhav(x1, y1, transitModel.getLagna());
		printPlanetsInHouse(20f, pageHeight - 430f, transitModel.getPlaRashiArray(), transitModel.getLagna());
	}

	void printKarkanshChart() throws IOException {
		drawShape.drawKrutiDevText(20, pageHeight - 450f, "dkjdka'k", 16);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 630f);

		float[] x2 = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y2 = { pageHeight - 630f + 90, pageHeight - 630f + 132, pageHeight - 630f + 122, pageHeight - 630f + 80,
				pageHeight - 630f + 40, pageHeight - 630f + 30, pageHeight - 630f + 70, pageHeight - 630f + 30,
				pageHeight - 630f + 40, pageHeight - 630f + 80, pageHeight - 630f + 122, pageHeight - 630f + 132 };
		/*
		 * drawRashiInBhav(x2, y2, 10); int[] planetArray =
		 * desktopHoro.getPositionForShodasvarg(0); printPlanetsInHouse(20f, pageHeight
		 * - 630f, planetArray);
		 */

		int lagna = new KarkanshAndSwanshCalcultion(desktopHoro).getKarakanshLagna();

		int[] planetArray = getIntArray(desktopHoro.getPositionForShodasvarg(0), 0);
		drawRashiInBhav(x2, y2, lagna);
		printPlanetsInHouse(20f, pageHeight - 630f, planetArray, lagna);
	}

	void printSwanshChart() throws IOException {
		drawShape.drawKrutiDevText(20, pageHeight - 650f, "Loka'k", 16);
		drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 830f);

		float[] x2 = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f + 80, 20f + 124, 20f + 136,
				20f + 95, 20f + 136, 20f + 124 };
		float[] y2 = { pageHeight - 830f + 90, pageHeight - 830f + 132, pageHeight - 830f + 122, pageHeight - 830f + 80,
				pageHeight - 830f + 40, pageHeight - 830f + 30, pageHeight - 830f + 70, pageHeight - 830f + 30,
				pageHeight - 830f + 40, pageHeight - 830f + 80, pageHeight - 830f + 122, pageHeight - 830f + 132 };
		int lagna = new KarkanshAndSwanshCalcultion(desktopHoro).getKarakanshLagna();

		int[] planetArray = getIntArray(desktopHoro.getPositionForShodasvarg(5), 0);
		drawRashiInBhav(x2, y2, lagna);
		printPlanetsInHouse(20f, pageHeight - 830f, planetArray, lagna);

	}

	void printChalitTable() throws IOException {
		drawShape.drawKrutiDevText(210, pageHeight - 50, "pfyr rkfydk", 16);
		drawShape.drawTableHeader(210, pageHeight - 85, 25, 360);
		float x = 210;
		float y = pageHeight - 385f;
		float h = 325;
		float w = 360;
		drawShape.drawTableWithHeader(x, y, h, w, 25);
		drawShape.drawRaw(x, y, h, w, 12, 25);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 30, y, x + 30, y + 325));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 325));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 325));
		points.add(new AxisPoint(x + 235, y, x + 235, y + 325));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 40, x + 90, x + 190, x + 245 };
		ArrayList<KundliChalitTableModel> list = new ChalitCalculation(desktopHoro).getChalitTableData();
		populateChalitTableData(xaxis, y + 300, list);

	}

	void populateChalitTableData(float[] xaxis, float y, ArrayList<KundliChalitTableModel> list) throws IOException {
		y = y + 8;
		KundliChalitTableModel kundliChalitTableModel;
		for (int i = 0; i < list.size(); i++) {
			kundliChalitTableModel = list.get(i);
			drawShape.drawKrutiDevText(xaxis[0], y - (i + 1) * 25, String.valueOf(kundliChalitTableModel.getBhav()),
					14);
			drawShape.drawKrutiDevText(xaxis[1], y - (i + 1) * 25,
					String.valueOf(kundliChalitTableModel.getBhBegSign()), 14);
			drawShape.drawText(xaxis[2], y - (i + 1) * 25, String.valueOf(kundliChalitTableModel.getBhBegDeg()), 10);
			drawShape.drawKrutiDevText(xaxis[3], y - (i + 1) * 25,
					String.valueOf(kundliChalitTableModel.getBhMidSign()), 14);
			drawShape.drawText(xaxis[4], y - (i + 1) * 25, String.valueOf(kundliChalitTableModel.getBhMidDeg()), 10);
		}
	}

	void printAstakvargTable() throws IOException {
		drawShape.drawKrutiDevText(210, pageHeight - 430, "v\"VdoxZ", 16);
		drawShape.drawTableHeader(210, pageHeight - 465, 25, 360);
		float x = 210;
		float y = pageHeight - 765f;
		float h = 325;
		float w = 360;
		drawShape.drawTableWithHeader(x, y, h, w, 25);
		drawShape.drawRaw(x, y, h, w, 12, 25);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 40, y, x + 40, y + 325));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 325));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 325));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 325));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 325));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 325));
		points.add(new AxisPoint(x + 280, y, x + 280, y + 325));
		points.add(new AxisPoint(x + 320, y, x + 320, y + 325));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 20, x + 60, x + 100, x + 140, x + 180, x + 220, x + 260, x + 300, x + 340 };
		String[] ashtakvarg = new AshtakVargaCalculation(desktopHoro).getAshtakVargaData();
		populateAshtakvargaTable(xaxis, y + 300, ashtakvarg);
	}

	void printForthPage() {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addPageHeading(pageHeight, pageWidth);
			prashtakVargaList = new PrashtakVargaCalculation(desktopHoro).getPrashtakVargaData();
			printPrashtakvargaTable1();
			printPrashtakvargaTable2();
			printPrashtakvargaTable3();
			printPrashtakvargaTable4();
			printPrashtakvargaTable5();
			printPrashtakvargaTable6();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printFifthPage() {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addPageHeading(pageHeight, pageWidth);
			printPrashtakvargaTable7();
			printPrashtakvargaTable8();
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void printPrashtakvargaTable1() throws IOException {

		drawShape.drawKrutiDevText(10, pageHeight - 50, "lw;Z", 16);
		float x = 10;
		float y = pageHeight - 260;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(10, pageHeight - 260, 200, 282);
		drawShape.drawRowAndColoum(10, pageHeight - 260, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(0));
	}

	void printPrashtakvargaTable2() throws IOException {
		drawShape.drawKrutiDevText(302, pageHeight - 50, "paæ", 16);
		float x = 302;
		float y = pageHeight - 260;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(302, pageHeight - 260, 200, 282);
		drawShape.drawRowAndColoum(302, pageHeight - 260, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(1));
	}

	void printPrashtakvargaTable3() throws IOException {
		drawShape.drawKrutiDevText(10, pageHeight - 280, "eaxy", 16);
		float x = 10;
		float y = pageHeight - 490;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(10, pageHeight - 490, 200, 282);
		drawShape.drawRowAndColoum(10, pageHeight - 490, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(2));
	}

	void printPrashtakvargaTable4() throws IOException {
		drawShape.drawKrutiDevText(302, pageHeight - 280, "cq/k", 16);
		float x = 302;
		float y = pageHeight - 490;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(302, pageHeight - 490, 200, 282);
		drawShape.drawRowAndColoum(302, pageHeight - 490, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(3));
	}

	void printPrashtakvargaTable5() throws IOException {
		drawShape.drawKrutiDevText(10, pageHeight - 510, "xq#", 16);
		float x = 10;
		float y = pageHeight - 720;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(10, pageHeight - 720, 200, 282);
		drawShape.drawRowAndColoum(10, pageHeight - 720, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(4));
	}

	void printPrashtakvargaTable6() throws IOException {
		drawShape.drawKrutiDevText(302, pageHeight - 510, "'kqØ", 16);
		float x = 302;
		float y = pageHeight - 720;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(302, pageHeight - 720, 200, 282);
		drawShape.drawRowAndColoum(302, pageHeight - 720, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(5));
	}

	void printPrashtakvargaTable7() throws IOException {

		drawShape.drawKrutiDevText(10, pageHeight - 50, "'kfu ", 16);
		float x = 10;
		float y = pageHeight - 260;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(10, pageHeight - 260, 200, 282);
		drawShape.drawRowAndColoum(10, pageHeight - 260, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(6));
	}

	void printPrashtakvargaTable8() throws IOException {
		drawShape.drawKrutiDevText(302, pageHeight - 50, "jkgq", 16);
		float x = 302;
		float y = pageHeight - 260;
		float h = 200;
		float w = 282;
		drawShape.drawRoundedRectangle(302, pageHeight - 260, 200, 282);
		drawShape.drawRowAndColoum(302, pageHeight - 260, 200, 282, 9, 20);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 20, y, x + 20, y + 200));
		points.add(new AxisPoint(x + 40, y, x + 40, y + 200));
		points.add(new AxisPoint(x + 60, y, x + 60, y + 200));
		points.add(new AxisPoint(x + 80, y, x + 80, y + 200));
		points.add(new AxisPoint(x + 100, y, x + 100, y + 200));
		points.add(new AxisPoint(x + 120, y, x + 120, y + 200));
		points.add(new AxisPoint(x + 140, y, x + 140, y + 200));
		points.add(new AxisPoint(x + 160, y, x + 160, y + 200));
		points.add(new AxisPoint(x + 180, y, x + 180, y + 200));
		points.add(new AxisPoint(x + 200, y, x + 200, y + 200));
		points.add(new AxisPoint(x + 220, y, x + 220, y + 200));
		points.add(new AxisPoint(x + 240, y, x + 240, y + 200));
		points.add(new AxisPoint(x + 260, y, x + 260, y + 200));
		drawShape.drawColumn(points);
		float[] xaxis = { x + 10, x + 30, x + 50, x + 70, x + 90, x + 110, x + 130, x + 150, x + 170, x + 190, x + 210,
				x + 230, x + 250, x + 270 };
		populatePrashtakvargaTable(xaxis, y + 180, prashtakVargaList.get(7));
	}

	void printSixthPage() {
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.GRAY);
			contentStream.setNonStrokingColor(Color.BLACK);
			drawShape.initialize(pageHeight, pageWidth, document, contentStream);
			addPageHeading(pageHeight, pageWidth);
			ArrayList<YoginiDashaModel> dataList = getYoginiDashaData();
			printYoginiDasha1(dataList.get(0));
			printYoginiDasha2(dataList.get(1));
			printYoginiDasha3(dataList.get(2));
			printYoginiDasha4(dataList.get(3));
			printYoginiDasha5(dataList.get(4));
			printYoginiDasha6(dataList.get(5));
			printYoginiDasha7(dataList.get(6));
			printYoginiDasha8(dataList.get(7));
			contentStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void populateYoginiDashaData(float x, float y, YoginiDashaModel yoginiDashaModel) throws IOException {
		float[] xaxis = { x + 5, x + 72, x + 172 };
		y = y + 5;
		drawShape.drawKrutiDevText(x + 5, y, yoginiDashaModel.getPlanetName() + " " + yoginiDashaModel.getDuration()
				+ " ¼ " + yoginiDashaModel.getStartYear() + "&" + yoginiDashaModel.getEndYear() + " ½", 14);
		ArrayList<CharAntaraDashaModel> list = yoginiDashaModel.getAntaraDashaList();
		for (int i = 0; i < list.size(); i++) {
			drawShape.drawKrutiDevText(xaxis[0], y - (i + 1) * 18, list.get(i).getPlanetName(), 14);
			drawShape.drawKrutiDevText(xaxis[1], y - (i + 1) * 18, list.get(i).getStartDate(), 14);
			drawShape.drawKrutiDevText(xaxis[2], y - (i + 1) * 18, list.get(i).getEndDate(), 14);
		}
	}

	void populateAshtakvargaTable(float[] xaxis, float y, String[] strArray) throws IOException {
		y = y + 8;
		for (int i = 0; i < strArray.length; i++) {
			String[] arr = strArray[i].split(",");
			int total = 0;
			for (int j = 0; j < arr.length + 1; j++) {
				if (j == 0) {
					drawShape.drawKrutiDevText(xaxis[j] - 3, y - (i + 1) * 25, String.valueOf(i + 1), 14);
				} else {
					total += Integer.parseInt(arr[j - 1]);
					drawShape.drawKrutiDevText(xaxis[j] - 3, y - (i + 1) * 25, arr[j - 1], 14);
				}
			}
			drawShape.drawKrutiDevText(xaxis[8] - 3, y - (i + 1) * 25, String.valueOf(total), 14);
		}
	}

	void populatePrashtakvargaTable(float[] xaxis, float y, PrastharashtakvargaModel prastharashtakvargaModel)
			throws IOException {
		y = y + 5;
		String plaName[] = new ConstantHindi().praPlaName;
		String[] arr1 = prastharashtakvargaModel.getSu().split(",");
		String[] arr2 = prastharashtakvargaModel.getMo().split(",");
		String[] arr3 = prastharashtakvargaModel.getMa().split(",");
		String[] arr4 = prastharashtakvargaModel.getMe().split(",");
		String[] arr5 = prastharashtakvargaModel.getJu().split(",");
		String[] arr6 = prastharashtakvargaModel.getVe().split(",");
		String[] arr7 = prastharashtakvargaModel.getSa().split(",");
		String[] arr8 = prastharashtakvargaModel.getAsc().split(",");
		String[] arr9 = getPraTotal(arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8);

		ArrayList<String[]> list = new ArrayList<String[]>();
		list.add(arr1);
		list.add(arr2);
		list.add(arr3);
		list.add(arr4);
		list.add(arr5);
		list.add(arr6);
		list.add(arr7);
		list.add(arr8);
		list.add(arr9);
		for (int i = 0; i < 10; i++) {
			if (i == 0) {
				for (int j = 0; j < 13; j++) {
					if (j == 0) {
						drawShape.drawKrutiDevText(xaxis[j] - 3, y, "", 14);
					} else {

						drawShape.drawKrutiDevText(xaxis[j] - 3, y, String.valueOf(j), 14);
					}
				}
				drawShape.drawKrutiDevText(xaxis[13] - 3, y, ";ks", 14);
			} else if (i == 9) {
				for (int j = 0; j < 13; j++) {
					if (j == 0) {
						drawShape.drawKrutiDevText(xaxis[j] - 3, y - i * 20, ";ks", 14);
					} else {

						drawShape.drawKrutiDevText(xaxis[j] - 3, y - i * 20, arr9[j - 1], 14);
					}
				}
			} else {
				String[] arr = list.get(i - 1);
				int total = 0;
				for (int j = 0; j < arr.length + 1; j++) {
					if (j == 0) {
						drawShape.drawKrutiDevText(xaxis[j] - 3, y - i * 20, plaName[i - 1], 14);
					} else {
						total += Integer.parseInt(arr[j - 1]);
						drawShape.drawKrutiDevText(xaxis[j] - 3, y - i * 20, arr[j - 1], 14);
					}
				}
				drawShape.drawKrutiDevText(xaxis[13] - 3, y - i * 20, String.valueOf(total), 14);
			}

		}
	}

	String[] getPraTotal(String[] arr1, String[] arr2, String[] arr3, String[] arr4, String[] arr5, String[] arr6,
			String[] arr7, String[] arr8) {
		String[] arr = new String[12];
		for (int i = 0; i < arr1.length; i++) {
			arr[i] = String.valueOf(Integer.parseInt(arr1[i]) + Integer.parseInt(arr2[i]) + Integer.parseInt(arr3[i])
					+ Integer.parseInt(arr4[i]) + Integer.parseInt(arr5[i]) + Integer.parseInt(arr6[i])
					+ Integer.parseInt(arr7[i]) + Integer.parseInt(arr8[i]))

			;
		}
		return arr;

	}

	void printYoginiDasha1(YoginiDashaModel yoginiDashaModel) throws IOException {
		// drawShape.drawKrutiDevText(20, pageHeight - 50, "tUe fooj.k", 12);
		float x = 20;
		float y = pageHeight - 222;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;

		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha2(YoginiDashaModel yoginiDashaModel) throws IOException {
		// drawShape.drawKrutiDevText(307, pageHeight - 50, "tUe fooj.k", 12);
		float x = 307;
		float y = pageHeight - 222;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha3(YoginiDashaModel yoginiDashaModel) throws IOException {
		// drawShape.drawKrutiDevText(20, pageHeight - 242, "tUe fooj.k", 12);
		float x = 20;
		float y = pageHeight - 414;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		drawShape.drawKrutiDevText(x, pageHeight - 50, "tUe fooj.k", 12);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha4(YoginiDashaModel yoginiDashaModel) throws IOException {
		// drawShape.drawKrutiDevText(307, pageHeight - 242, "tUe fooj.k", 12);
		float x = 307;
		float y = pageHeight - 414;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha5(YoginiDashaModel yoginiDashaModel) throws IOException {
		// drawShape.drawKrutiDevText(20, pageHeight - 434, "tUe fooj.k", 12);
		float x = 20;
		float y = pageHeight - 606;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		drawShape.drawKrutiDevText(x, pageHeight - 50, "tUe fooj.k", 12);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha6(YoginiDashaModel yoginiDashaModel) throws IOException {
		// drawShape.drawKrutiDevText(307, pageHeight - 434, "tUe fooj.k", 12);
		float x = 307;
		float y = pageHeight - 606;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha7(YoginiDashaModel yoginiDashaModel) throws IOException {
		// drawShape.drawKrutiDevText(20, pageHeight - 626, "tUe fooj.k", 12);
		float x = 20;
		float y = pageHeight - 798;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		drawShape.drawKrutiDevText(x, pageHeight - 50, "tUe fooj.k", 12);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}

	void printYoginiDasha8(YoginiDashaModel yoginiDashaModel) throws IOException {
		// drawShape.drawKrutiDevText(307, pageHeight - 626, "tUe fooj.k", 12);
		float x = 307;
		float y = pageHeight - 798;
		float h = 162;
		float w = 267;
		int row = 8;
		int rowH = 18;
		drawShape.drawRoundedRectangle(x, y, h, w);
		drawShape.drawRowAndColoum(x, y, h, w, row, rowH);
		ArrayList<AxisPoint> points = new ArrayList<AxisPoint>();
		points.add(new AxisPoint(x + 67, y, x + 67, y + 144));
		points.add(new AxisPoint(x + 167, y, x + 167, y + 144));
		drawShape.drawColumn(points);
		populateYoginiDashaData(x, y + 144, yoginiDashaModel);
	}
	/*
	 * void drawPlanetPositionTable(PDPageContentStream contentStream, float x,
	 * float y, float height, float width) throws IOException { float k =
	 * 0.552284749831f; int radius = 6; drawShape.drawTableHeader(x, y + 390, 30,
	 * width); contentStream.setLineWidth(1f);
	 * contentStream.setStrokingColor(Color.black);
	 * contentStream.setNonStrokingColor(Color.WHITE); // Start from top-left
	 * contentStream.moveTo(x, y + height - 30); // Top line contentStream.lineTo(x
	 * + width, y + height - 30); // Right line contentStream.lineTo(x + width, y +
	 * radius); // Bottom-right corner contentStream.curveTo(x + width, y + radius -
	 * radius * k, x + width - radius + radius * k, y, x + width - radius, y); //
	 * Bottom line contentStream.lineTo(x + radius, y); // Bottom-left corner
	 * contentStream.curveTo(x + radius - radius * k, y, x, y + radius - radius * k,
	 * x, y + radius); // Left line contentStream.lineTo(x, y + height - 30);
	 * contentStream.closePath(); contentStream.stroke(); drawRow(contentStream, x,
	 * y, height, width, 13); drawShape.drawLine(x + 30, y, x + 30, y + 420);
	 * drawShape.drawLine(x + 90, y, x + 90, y + 420); drawShape.drawLine(x + 140,
	 * y, x + 140, y + 420); drawShape.drawLine(x + 220, y, x + 220, y + 420);
	 * populatePlanetPosition(contentStream, x, y, height, width); }
	 */

	/*
	 * void addChart() { try { PDPage page = new PDPage(PDRectangle.A4);
	 * document.addPage(page); contentStream = new PDPageContentStream(document,
	 * page); contentStream.setLineWidth(1);
	 * contentStream.setStrokingColor(Color.GRAY);
	 * contentStream.setNonStrokingColor(Color.BLACK); PDRectangle mediaBox =
	 * page.getMediaBox(); float pageWidth = mediaBox.getWidth(); float pageHeight =
	 * mediaBox.getHeight(); // addPageHeading(contentStream, pageHeight,
	 * pageWidth); drawText(contentStream, "Lagna Chart", 20, pageHeight - 50);
	 * drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 230f);
	 * 
	 * float[] x = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38, 20f
	 * + 80, 20f + 124, 20f + 136, 20f + 95, 20f + 136, 20f + 124 }; float[] y = {
	 * pageHeight - 230f + 90, pageHeight - 230f + 132, pageHeight - 230f + 122,
	 * pageHeight - 230f + 80, pageHeight - 230f + 40, pageHeight - 230f + 30,
	 * pageHeight - 230f + 70, pageHeight - 230f + 30, pageHeight - 230f + 40,
	 * pageHeight - 230f + 80, pageHeight - 230f + 122, pageHeight - 230f + 132 };
	 * drawRashiInBhav(x, y, 10); int[] planetArray =
	 * desktopHoro.getPositionForShodasvarg(0); printPlanetsInHouse(20f, pageHeight
	 * - 230f + 152, planetArray); drawText(contentStream, "Moon Chart", 20,
	 * pageHeight - 250f); drawShape.drawKundli(pageHeight, pageWidth, 20f,
	 * pageHeight - 430f);
	 * 
	 * float[] x1 = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38,
	 * 20f + 80, 20f + 124, 20f + 136, 20f + 95, 20f + 136, 20f + 124 }; float[] y1
	 * = { pageHeight - 430f + 90, pageHeight - 430f + 132, pageHeight - 430f + 122,
	 * pageHeight - 430f + 80, pageHeight - 430f + 40, pageHeight - 430f + 30,
	 * pageHeight - 430f + 70, pageHeight - 430f + 30, pageHeight - 430f + 40,
	 * pageHeight - 430f + 80, pageHeight - 430f + 122, pageHeight - 430f + 132 };
	 * drawRashiInBhav(x1, y1, 10);
	 * 
	 * drawText(contentStream, "Navmansh Chart", 20, pageHeight - 450f);
	 * drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 630f);
	 * 
	 * float[] x2 = { 20f + 80, 20f + 38, 20f + 26, 20f + 70, 20f + 26, 20f + 38,
	 * 20f + 80, 20f + 124, 20f + 136, 20f + 95, 20f + 136, 20f + 124 }; float[] y2
	 * = { pageHeight - 630f + 90, pageHeight - 630f + 132, pageHeight - 630f + 122,
	 * pageHeight - 630f + 80, pageHeight - 630f + 40, pageHeight - 630f + 30,
	 * pageHeight - 630f + 70, pageHeight - 630f + 30, pageHeight - 630f + 40,
	 * pageHeight - 630f + 80, pageHeight - 630f + 122, pageHeight - 630f + 132 };
	 * drawRashiInBhav(x2, y2, 10); drawText(contentStream, "Planetray Position",
	 * 210, pageHeight - 50); drawPlanetPositionTable(contentStream, 210, pageHeight
	 * - 480, 420, 360);
	 * 
	 * 
	 * drawText(contentStream, "Ashtakvarga Table", 210, pageHeight - 500);
	 * drawAshtakvargaTable(contentStream, 210, pageHeight - 780, 270, 360);
	 * 
	 * contentStream.close(); } catch (Exception e) { // TODO: handle exception } }
	 */

	/*
	 * void addChalitChart() { try { PDPage page = new PDPage(PDRectangle.A4);
	 * document.addPage(page); contentStream = new PDPageContentStream(document,
	 * page); contentStream.setLineWidth(1);
	 * contentStream.setStrokingColor(Color.GRAY);
	 * contentStream.setNonStrokingColor(Color.BLACK); PDRectangle mediaBox =
	 * page.getMediaBox(); float pageWidth = mediaBox.getWidth(); float pageHeight =
	 * mediaBox.getHeight(); // addPageHeading(contentStream, pageHeight,
	 * pageWidth); drawText(contentStream, "Chalit Chart", 20, pageHeight - 50);
	 * drawShape.drawKundli(pageHeight, pageWidth, 20f, pageHeight - 230f);
	 * drawText(contentStream, "Chalit Table", 210, pageHeight - 50);
	 * drawChalitTable(contentStream, 210, pageHeight - 450, 390, 360);
	 * contentStream.close(); } catch (Exception e) { // TODO: handle exception } }
	 */

	void printLabel(DrawShape drawShape, float x, float pageHeight, String[] labels) {
		//
		float y = pageHeight;
		for (int i = 0; i < labels.length; i++) {
			try {
				drawShape.drawKrutiDevText(x, y, labels[i], 14);
				y = y - 30;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void printValue(DrawShape drawShape, float x, float pageHeight, String[] values) {
		// String[] labels = { "Name1", "Name2", "Name3", "Name4", "Name5" };
		float y = pageHeight;
		for (int i = 0; i < values.length; i++) {
			try {
				drawShape.drawText(x/* 150 */, y, values[i], 14);
				y = y - 30;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void printValueWithDev(DrawShape drawShape, float x, float pageHeight, String[] values) {
		// String[] labels = { "Name1", "Name2", "Name3", "Name4", "Name5" };
		float y = pageHeight;
		for (int i = 0; i < values.length; i++) {
			try {
				drawShape.drawKrutiDevText(x/* 150 */, y, values[i], 14);
				y = y - 30;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void drawRectWithSolid(PDPageContentStream contentStream, float x, float y, float height, float width,
			int rowCount) throws IOException {
		// contentStream.setNonStrokingColor(Color.RED); // Set fill color
		contentStream.setStrokingColor(Color.black);
		contentStream.setNonStrokingColor(Color.WHITE);
		contentStream.addRect(x, y, width, height);
		contentStream.fill();
		contentStream.moveTo(x, y);
		contentStream.lineTo(x, y + height);
		contentStream.lineTo(x + width, y + height);
		contentStream.lineTo(x + width, y);
		contentStream.lineTo(x, y);
		contentStream.closePath();
		contentStream.stroke();

		drawRowAndColoum(contentStream, x, y, height, width, rowCount);
	}

	void drawRowAndColoum(PDPageContentStream contentStream, float x, float y, float height, float width,
			int rowCount) {

		float yAxis = y + 30;
		try {
			contentStream.setNonStrokingColor(Color.black);
			for (int i = 0; i < rowCount; i++) {
				drawShape.drawLine(x, yAxis, x + width, yAxis);
				yAxis += 30;
			}
			drawShape.drawLine(x + 120, y + (rowCount + 1) * 30, x + 120, y);
		} catch (Exception e) {

		}

	}

	public void drawText(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
		contentStream.setNonStrokingColor(Color.BLACK); // Set fill color
		contentStream.setStrokingColor(Color.BLACK);
		contentStream.beginText();
		PDType0Font font = PDType0Font.load(document, new File("E:\\fonts\\roboto_medium.ttf"));
		contentStream.setFont(font, 12); // Set font and size
		contentStream.newLineAtOffset(x, y); // Set position
		contentStream.showText(text); // Display text
		contentStream.endText();
	}

	public void drawText(PDPageContentStream contentStream, String text, float x, float y, int fontSize)
			throws IOException {
		contentStream.setNonStrokingColor(Color.BLACK); // Set fill color
		contentStream.setStrokingColor(Color.BLACK);
		contentStream.beginText();
		PDType0Font font = PDType0Font.load(document, new File("E:\\fonts\\roboto_medium.ttf"));
		contentStream.setFont(font, fontSize); // Set font and size
		contentStream.newLineAtOffset(x, y); // Set position
		contentStream.showText(text); // Display text
		contentStream.endText();
	}

	void initHoro() {
		desktopHoro = new DesktopHoro();
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

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	String[] getPanchangData() {
		String[] arr = new String[9];
		arr[0] = desktopHoro.getPakshaName();
		arr[1] = desktopHoro.getTithiName();
		arr[2] = desktopHoro.getNakshatraName();
		arr[3] = desktopHoro.getHinduWeekdayName();
		arr[4] = desktopHoro.getHinduWeekdayName();
		arr[5] = desktopHoro.getYoganame();
		arr[6] = desktopHoro.getKaranName();
		arr[7] = desktopHoro.getSunRiseTimeHms();
		arr[8] = desktopHoro.getSunSetTimeHms();

		return arr;
	}

	String[] getAvakahadaDetail() {
		String[] arr = new String[20];
		arr[0] = desktopHoro.getPayaName();
		arr[1] = desktopHoro.getVarnaName();
		arr[2] = desktopHoro.getYoniName();
		arr[3] = desktopHoro.getGanaName();
		arr[4] = desktopHoro.getVasyaName();
		arr[5] = desktopHoro.getNadiName();
		arr[6] = desktopHoro.getBalanceOfDasaString();
		arr[7] = String.valueOf(desktopHoro.getLagna());
		arr[8] = desktopHoro.getLagnaLordName();
		arr[9] = desktopHoro.getRasiName();
		arr[10] = desktopHoro.getRasiLordName();
		arr[11] = desktopHoro.getNakshatraName();
		arr[12] = desktopHoro.getNakshatraLordName();
		arr[13] = desktopHoro.getJulianDayValue();
		arr[14] = desktopHoro.getSunSignName();
		arr[15] = desktopHoro.getSunSignName();
		arr[16] = desktopHoro.getAyanamsaDms();
		arr[17] = desktopHoro.getAyanamsaDms();
		arr[18] = desktopHoro.getAscendentDms();
		arr[19] = desktopHoro.getAscendentDms();
		return arr;
	}

	/*
	 * void populatePlanetPosition(PDPageContentStream contentStream, float x, float
	 * y, float height, float width) throws IOException { ArrayList<PlanetDataModel>
	 * arrayList = getPlanetData(); int h = 400; for (int i = 0; i < 13; i++) {
	 * 
	 * if (i == 0) { drawText(contentStream, "ग्रह", x + 5, y + h);
	 * drawText(contentStream, "ग्रह", x + 35, y + h); drawText(contentStream,
	 * "ग्रह", x + 95, y + h); drawText(contentStream, "ग्रह", x + 145, y + h);
	 * drawText(contentStream, "ग्रह", x + 225, y + h);
	 * 
	 * } else {
	 * 
	 * PlanetDataModel planetDataModel = arrayList.get(i); drawText(contentStream,
	 * planetDataModel.getPlaName(), x + 5, y + h); drawText(contentStream,
	 * planetDataModel.getSign(), x + 35, y + h); drawText(contentStream,
	 * planetDataModel.getDegree(), x + 95, y + h); drawText(contentStream,
	 * planetDataModel.getNaks(), x + 145, y + h); drawText(contentStream,
	 * planetDataModel.getPlaCharan(), x + 225, y + h);
	 * 
	 * drawText(contentStream, "ग्रह", x + 5, y + h); drawText(contentStream,
	 * "ग्रह", x + 35, y + h); drawText(contentStream, "ग्रह", x + 95, y + h);
	 * drawText(contentStream, "ग्रह", x + 145, y + h); drawText(contentStream,
	 * "ग्रह", x + 225, y + h); }
	 * 
	 * String[] values = { "ग्रह", "ग्रह", "ग्रह", "ग्रह", "ग्रह" };
	 * printValue(drawShape, 150, height - 260, values); h = h - 30; }
	 * 
	 * }
	 */

	ArrayList<PlanetDataModel> getPlanetData() {
		double[] plaDeg = desktopHoro.getPlanetaryDegree();
		String[] planetNames = constantHindi.planetNameWithLagna;
		String[] rashiNames = constantHindi.rasiShortNames;
		String[] nakshNames = constantHindi.naksShortNames;
		ArrayList<PlanetDataModel> arrayList = new ArrayList<PlanetDataModel>();
		PlanetDataModel planetDataModel;
		for (int i = 0; i < 13; i++) {
			planetDataModel = new PlanetDataModel();
			planetDataModel.setPlaName(planetNames[i]);
			planetDataModel.setSign(rashiNames[getRasiNumber(plaDeg[i])]);
			planetDataModel.setNaks(nakshNames[getNakshatraNumber(plaDeg[i])]);
			planetDataModel.setPlaCharan(String.valueOf((getPlntCharan(plaDeg[i]))));
			planetDataModel.setDegree(String.valueOf(plaDeg[i]));
			arrayList.add(planetDataModel);
		}
		return arrayList;
	}

	int getRasiNumber(double deg) {
		return (int) (deg / 30);
	}

	private int getNakshatraNumber(double deg) {
		return (int) (deg * 0.075);
	}

	private int getPlntCharan(double pl) {

		double aa = pl;

		return (int) (4 * fract(aa * 3.0 / 40)) + 1;
	}

	private double fract(double x) {
		double y;
		long i = (long) x;
		y = x - i;
		return y;
	}

	void drawAshtakvargaTable(PDPageContentStream contentStream, float x, float y, float height, float width)
			throws IOException {
		float k = 0.552284749831f;
		int radius = 6;
		drawShape.drawTableHeader(x, y + 240, 30, width);
		contentStream.setLineWidth(1f);
		contentStream.setStrokingColor(Color.black);
		contentStream.setNonStrokingColor(Color.WHITE);
		// Start from top-left
		contentStream.moveTo(x, y + height - 30);
		// Top line
		contentStream.lineTo(x + width, y + height - 30);
		// Right line
		contentStream.lineTo(x + width, y + radius);
		// Bottom-right corner
		contentStream.curveTo(x + width, y + radius - radius * k, x + width - radius + radius * k, y,
				x + width - radius, y);
		// Bottom line
		contentStream.lineTo(x + radius, y);
		// Bottom-left corner
		contentStream.curveTo(x + radius - radius * k, y, x, y + radius - radius * k, x, y + radius);
		// Left line
		contentStream.lineTo(x, y + height - 30);
		contentStream.closePath();
		contentStream.stroke();

		drawRow(contentStream, x, y, height, width, 7);
		drawShape.drawLine(x + width - 25, y, x + width - 25, y + 270);
		drawShape.drawLine(x + width - 25 * 2, y, x + width - 25 * 2, y + 270);
		drawShape.drawLine(x + width - 25 * 3, y, x + width - 25 * 3, y + 270);
		drawShape.drawLine(x + width - 25 * 4, y, x + width - 25 * 4, y + 270);
		drawShape.drawLine(x + width - 25 * 5, y, x + width - 25 * 5, y + 270);
		drawShape.drawLine(x + width - 25 * 6, y, x + width - 25 * 6, y + 270);
		drawShape.drawLine(x + width - 25 * 7, y, x + width - 25 * 7, y + 270);
		drawShape.drawLine(x + width - 25 * 8, y, x + width - 25 * 8, y + 270);
		drawShape.drawLine(x + width - 25 * 9, y, x + width - 25 * 9, y + 270);
		drawShape.drawLine(x + width - 25 * 10, y, x + width - 25 * 10, y + 270);
		drawShape.drawLine(x + width - 25 * 11, y, x + width - 25 * 11, y + 270);
		drawShape.drawLine(x + width - 25 * 12, y, x + width - 25 * 12, y + 270);

	}

	void drawChalitTable(PDPageContentStream contentStream, float x, float y, float height, float width)
			throws IOException {
		float k = 0.552284749831f;
		int radius = 6;
		drawShape.drawTableHeader(x, y + 360, 30, width);
		contentStream.setLineWidth(1f);
		contentStream.setStrokingColor(Color.black);
		contentStream.setNonStrokingColor(Color.WHITE);
		// Start from top-left
		contentStream.moveTo(x, y + height - 30);
		// Top line
		contentStream.lineTo(x + width, y + height - 30);
		// Right line
		contentStream.lineTo(x + width, y + radius);
		// Bottom-right corner
		contentStream.curveTo(x + width, y + radius - radius * k, x + width - radius + radius * k, y,
				x + width - radius, y);
		// Bottom line
		contentStream.lineTo(x + radius, y);
		// Bottom-left corner
		contentStream.curveTo(x + radius - radius * k, y, x, y + radius - radius * k, x, y + radius);
		// Left line
		contentStream.lineTo(x, y + height - 30);
		contentStream.closePath();
		contentStream.stroke();
		drawRow(contentStream, x, y, height, width, 11);
		drawShape.drawLine(x + 30, y, x + 30, y + 390);
		drawShape.drawLine(x + 90, y, x + 90, y + 390);
		drawShape.drawLine(x + 140, y, x + 140, y + 390);
		drawShape.drawLine(x + 220, y, x + 220, y + 390);
	}

	void drawRow(PDPageContentStream contentStream, float x, float y, float height, float width, int rowCount) {

		float yAxis = y + 30;
		try {
			contentStream.setLineWidth(1f);
			contentStream.setNonStrokingColor(Color.black);
			for (int i = 0; i < rowCount; i++) {
				drawShape.drawLine(x, yAxis, x + width, yAxis);
				yAxis += 30;
			}
		} catch (Exception e) {

		}

	}

	@SuppressWarnings({ "deprecation", "unused" })
	private void drawRoundedRectangle(PDPageContentStream contentStream, float x, float y, float width, float height,
			float radius) throws IOException {
		float k = 0.552284749831f; // Control point distance for approximating a quarter circle with a cubic Bezier
									// curve

		contentStream.setLineWidth(1.5f);
		contentStream.setStrokingColor(0, 0, 0);

		// Start point
		contentStream.moveTo(x + radius, y);

		// Bottom line
		contentStream.lineTo(x + width - radius, y);
		// Bottom-right corner
		contentStream.curveTo(x + width - radius + radius * k, y, x + width, y + radius - radius * k, x + width,
				y + radius);

		// Right line
		contentStream.lineTo(x + width, y + height - radius);
		// Top-right corner
		contentStream.curveTo(x + width, y + height - radius + radius * k, x + width - radius + radius * k, y + height,
				x + width - radius, y + height);

		// Top line
		contentStream.lineTo(x + radius, y + height);
		// Top-left corner
		contentStream.curveTo(x + radius - radius * k, y + height, x, y + height - radius + radius * k, x,
				y + height - radius);

		// Left line
		contentStream.lineTo(x, y + radius);
		// Bottom-left corner
		contentStream.curveTo(x, y + radius - radius * k, x + radius - radius * k, y, x + radius, y);

		contentStream.closePath();
		contentStream.stroke();
	}

	private void drawRashiInBhav(float[] x_axis, float[] y_axis, int lagna) throws IOException {
		int lagna1 = lagna;
		for (int i = 0; i < 12; i++) {
			if (lagna1 > 12)
				lagna1 = 1;
			drawShape.drawText(x_axis[i], y_axis[i], String.valueOf(lagna1), 10);
			lagna1++;
		}
	}

	private void drawRashiInBhavForChalit(float[] x_axis, float[] y_axis, double[] midDegreeArray) throws IOException {
		int iRasi;

		for (int i = 0; i < 12; i++) {
			iRasi = (int) (midDegreeArray[i] / 30.0) + 1;
			drawShape.drawText(x_axis[i], y_axis[i], String.valueOf(iRasi), 10);
			/*
			 * @SuppressLint("UseCompatLoadingForDrawables") Drawable d =
			 * getResources().getDrawable(iconArray[iRasi - 1], null);
			 * d.setBounds(x_axis[i], y_axis[i], x_axis[i] +
			 * Utility.INSTANCE.convertDpToPx(resources, 20), y_axis[i] +
			 * Utility.INSTANCE.convertDpToPx(resources, 20)); d.draw(canvas);
			 */
		}
	}

	private void printPlanetsInHouse(float x, float y, int[] planetArray, int lagnaRashi) throws IOException {
		float rh = 170;
		float rw = 170;
		y = y + rh;

		/* 3,5,9 and 11 */
		float[] housePositionsX3 = { x + 2, x + 12, x + 2, x + 12, x + 3, x + 22 };
		float[] housePositionsY3 = { y - 24, y - 34, y - 44, y - 54, y - 66, y - 44 };
		float[] housePositionsX5 = { x + 2, x + 12, x + 2, x + 12, x + 3, x + 22 };
		float[] housePositionsY5 = { y - rh / 2 - 23, y - rh / 2 - 35, y - rh / 2 - 46, y - rh / 2 - 57,
				y - rh / 2 - 69, y - rh / 2 - 46 };
		float[] housePositionsX9 = { x + rw - 14, x + rw - 26, x + rw - 14, x + rw - 26, x + rw - 14, x + rw - 38 };
		float[] housePositionsY9 = { y - rh / 2 - 23, y - rh / 2 - 35, y - rh / 2 - 46, y - rh / 2 - 57,
				y - rh / 2 - 69, y - rh / 2 - 46 };
		float[] housePositionsX11 = { x + rw - 14, x + rw - 26, x + rw - 14, x + rw - 26, x + rw - 14, x + rw - 38 };
		float[] housePositionsY11 = { y - 24, y - 34, y - 44, y - 54, y - 66, y - 44 };
		/* 2,6,8 and 12 house */
		float[] housePositionsX12 = { x + rw / 2 + 12, x + rw / 2 + 24, x + rw / 2 + 36, x + rw / 2 + 48,
				x + rw / 2 + 60, x + rw / 2 + 36 };
		float[] housePositionsY12 = { y - 8, y - 16, y - 8, y - 16, y - 8, y - 24 };
		float[] housePositionsX2 = { x + 12, x + 24, x + 36, x + 48, x + 60, x + 36 };
		float[] housePositionsY2 = { y - 8, y - 16, y - 8, y - 16, y - 8, y - 24 };
		float[] housePositionsX6 = { x + 12, x + 24, x + 36, x + 48, x + 60, x + 36 };
		float[] housePositionsY6 = { y - rh + 4, y - rh + 12, y - rh + 4, y - rh + 12, y - rh + 4, y - rh + 20 };
		float[] housePositionsX8 = { x + rw / 2 + 12, x + rw / 2 + 24, x + rw / 2 + 36, x + rw / 2 + 48,
				x + rw / 2 + 60, x + rw / 2 + 36 };
		float[] housePositionsY8 = { y - rh + 4, y - rh + 12, y - rh + 4, y - rh + 12, y - rh + 4, y - rh + 20 };
		/* 1,7,4 and 10 house */
		float[] housePositionsX1 = { x + rw / 4 + 18, x + rw / 4 + 36, x + rw / 4 + 54, x + rw / 4 + 27,
				x + rw / 4 + 45, x + rw / 4 + 36 };
		float[] housePositionsY1 = { y - rh / 4, y - rh / 4, y - rh / 4, y - rh / 4 + 15, y - rh / 4 + 15,
				y - rh / 4 - 15 };
		float[] housePositionsX7 = { x + rw / 4 + 18, x + rw / 4 + 36, x + rw / 4 + 54, x + rw / 4 + 27,
				x + rw / 4 + 45, x + rw / 4 + 36 };
		float[] housePositionsY7 = { y - 3 * rh / 4, y - 3 * rh / 4, y - 3 * rh / 4, y - 3 * rh / 4 - 15,
				y - 3 * rh / 4 - 15, y - 3 * rh / 4 + 15 };
		float[] housePositionsX10 = { x + 3 * rw / 4, x + 3 * rw / 4, x + 3 * rw / 4, x + 3 * rw / 4 - 20,
				x + 3 * rw / 4 - 20, x + 3 * rw / 4 + 20 };
		float[] housePositionsY10 = { y - rh / 4 - 24, y - rh / 4 - 44, y - rh / 4 - 64, y - rh / 4 - 34,
				y - rh / 4 - 54, y - rh / 4 - 44 };
		float[] housePositionsX4 = { x + rw / 4 - 5, x + rw / 4 - 5, x + rw / 4 - 5, x + rw / 4 - 25, x + rw / 4 - 25,
				x + rw / 4 + 15 };
		float[] housePositionsY4 = { y - rh / 4 - 24, y - rh / 4 - 44, y - rh / 4 - 64, y - rh / 4 - 34,
				y - rh / 4 - 54, y - rh / 4 - 44 };

		ArrayList<ArrayList<String>> arrayLists = new ArrayList<>();
		ArrayList<float[]> xArrayLists = new ArrayList<>();
		ArrayList<float[]> yArrayLists = new ArrayList<>();

		xArrayLists.add(housePositionsX1);
		xArrayLists.add(housePositionsX2);
		xArrayLists.add(housePositionsX3);
		xArrayLists.add(housePositionsX4);
		xArrayLists.add(housePositionsX5);
		xArrayLists.add(housePositionsX6);
		xArrayLists.add(housePositionsX7);
		xArrayLists.add(housePositionsX8);
		xArrayLists.add(housePositionsX9);
		xArrayLists.add(housePositionsX10);
		xArrayLists.add(housePositionsX11);
		xArrayLists.add(housePositionsX12);

		yArrayLists.add(housePositionsY1);
		yArrayLists.add(housePositionsY2);
		yArrayLists.add(housePositionsY3);
		yArrayLists.add(housePositionsY4);
		yArrayLists.add(housePositionsY5);
		yArrayLists.add(housePositionsY6);
		yArrayLists.add(housePositionsY7);
		yArrayLists.add(housePositionsY8);
		yArrayLists.add(housePositionsY9);
		yArrayLists.add(housePositionsY10);
		yArrayLists.add(housePositionsY11);
		yArrayLists.add(housePositionsY12);
		String[] plaName = new ConstantHindi().plaShortName;
		for (int i = 0; i < 12; i++) {
			arrayLists.add(new ArrayList<>());
		}
		for (int i = 0; i < planetArray.length; i++) {
			int planetBhav = getBhavOfPlant(lagnaRashi, planetArray[i]);
			planetBhav--;
			if (planetBhav >= 12) {
				planetBhav = 0;
			}

			arrayLists.get(planetBhav).add(plaName[i]);
			// arrayLists.get(0).add("Mo");
			/*
			 * String spanable = planetsName[i]; if (combustArr != null && combustArr.length
			 * > 0) { if (combustArr[i].equals("1")) { spanable = spanable +
			 * resources.getString(R.string.combust); } } if (i < 12 && retrograteArr !=
			 * null && retrograteArr.length > 0) { if (retrograteArr[i + 1].equals("1")) {
			 * spanable = spanable + resources.getString(R.string.retrograde); } }
			 * arrayLists.get(planetBhav).add(new PlanetData(spanable, paint));
			 */
		}
		ArrayList<String> innerList;
		float[] x_axis;
		float[] y_axis;
		int coordinateIndex;
		for (int i = 0; i < arrayLists.size(); i++) {
			innerList = arrayLists.get(i);
			coordinateIndex = 0;
			if (innerList.size() > 0) {
				x_axis = xArrayLists.get(i);
				y_axis = yArrayLists.get(i);
				for (int j = 0; j < innerList.size(); j++) {
					if (coordinateIndex > 5) {
						coordinateIndex = 0;
					}

					/*
					 * if (combustArr != null && combustArr[j].equals("1")) {
					 * setSpanableString(canvas, innerList.get(j).getPlanetName(),
					 * resources.getString(R.string.combust), x_axis[coordinateIndex],
					 * y_axis[coordinateIndex]); } else {
					 */

					drawShape.drawKrutiDevText(x_axis[coordinateIndex], y_axis[coordinateIndex], innerList.get(j), 12);
					// }
					coordinateIndex++;
				}

			}
		}
	}

	private int getBhavOfPlant(int lagnaRashi, int plntRashi) {
		int bhavNumber;
		bhavNumber = plntRashi - lagnaRashi;
		if (bhavNumber < 0) {
			bhavNumber += 12;
		}
		bhavNumber += 1;
		return bhavNumber;
	}

	private int[] getIntArray(int[] planetPosition, int lagnaPos) {

		int[] intArray = new int[13];
		int lagna = planetPosition[lagnaPos];
		for (int i = 0; i < planetPosition.length - 1; i++) {
			intArray[i] = planetPosition[i + 1];
		}
		intArray[planetPosition.length - 1] = lagna;
		return intArray;
	}
}
