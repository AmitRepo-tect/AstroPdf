package com.astroganit.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public interface PDFGenerateServiceNew {
	byte[] generatePDF() throws IOException;
}
