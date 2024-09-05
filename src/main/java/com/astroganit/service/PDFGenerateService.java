package com.astroganit.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.astroganit.model.BirthDetailModel;

@Service
public interface PDFGenerateService {
	byte[] generatePDF(BirthDetailModel birthDetailModel) throws IOException;
}
