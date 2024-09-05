package com.astroganit.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.astroganit.model.BirthDetailModel;
import com.astroganit.model.DateTimeModel;
import com.astroganit.model.PlaceDetailModel;
import com.astroganit.service.PDFGenerateService;
import com.astroganit.service.PDFGenerateServiceNew;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class PDFController {

	@Autowired(required = true)
	PDFGenerateService pdfGenerateService;
	@Autowired(required = true)
	PDFGenerateServiceNew pdfGenerateServiceNew;
	/*
	 * @GetMapping({ "/generatepdf" }) void generatePDF() { try {
	 * System.out.print("generatePdf"); pdfGenerateService.generatePDF(); } catch
	 * (IOException e) { }
	 * 
	 * }
	 */

	@GetMapping("/generatepdf")
	public void generatePdf(HttpServletResponse response) throws IOException {
		System.out.print("generatePdf");
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"drawing.pdf\"");
		try {
			// byte[] baos=padGenerator.generatePdf();
			byte[] baos = pdfGenerateService.generatePDF(getBirthDetailModel());
			response.getOutputStream().write(baos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/generate")
	public void generatePdfNew(HttpServletResponse response) throws IOException {
		System.out.print("generate");
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"drawing.pdf\"");
		try {
			// byte[] baos=padGenerator.generatePdf();
			byte[] baos = pdfGenerateServiceNew.generatePDF();
			response.getOutputStream().write(baos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	BirthDetailModel getBirthDetailModel() {
		BirthDetailModel birthDetailModel = new BirthDetailModel();
		PlaceDetailModel placeDetailModel = new PlaceDetailModel();
		placeDetailModel.setPlace("New Delhi");
		placeDetailModel.setCountry("India");
		placeDetailModel.setState("New Delhi");
		placeDetailModel.setLatDeg("28");
		placeDetailModel.setLatMin("38");
		placeDetailModel.setLatNS("N");
		placeDetailModel.setLongDeg("77");
		placeDetailModel.setLongMin("13");
		placeDetailModel.setLongEW("E");
		placeDetailModel.setTimezoneStr("5.5");
		placeDetailModel.setTimezone("5.5");
		DateTimeModel dateTimeModel = new DateTimeModel();
		dateTimeModel.setHrs("4");
		dateTimeModel.setMin("00");
		dateTimeModel.setSec("48");
		dateTimeModel.setDay("12");
		dateTimeModel.setMonth("1");
		dateTimeModel.setYear("1985");
		birthDetailModel.setName("Amit");
		birthDetailModel.setSex("M");
		birthDetailModel.setDst("0");
		birthDetailModel.setKphn("0");
		birthDetailModel.setAyanamsa("0");
		birthDetailModel.setLanguageCode("0");
		birthDetailModel.setDateTimeModel(dateTimeModel);
		birthDetailModel.setPlaceDetailModel(placeDetailModel);
		return birthDetailModel;
	}
}
