package com.astroganit.serviceimpl;

import com.astroganit.service.PDFGenerateServiceNew;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.fop.fonts.Base14Font;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class PDFGenerateServiceImplNew implements PDFGenerateServiceNew {
	public byte[] generatePDF() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			generatePdfWithRectangle(outputStream);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return outputStream.toByteArray();
	}

	public void generatePdfWithRectangle(ByteArrayOutputStream outputStream) throws IOException {

		PdfWriter writer = new PdfWriter(outputStream);
		PdfDocument pdfDoc = new PdfDocument(writer);
		Document document = new Document(pdfDoc);
		String krutiDevText = "LFkku"/*"vki dqN yk, nqfu;k¡"*/;
		try (InputStream fontStream = getClass().getClassLoader()
				.getResourceAsStream("fonts/Kruti-Dev-Regular.ttf")) {
			if (fontStream == null) {
				throw new IOException("Font file not found");
			}

			PdfFont font = PdfFontFactory.createFont(fontStream.readAllBytes(),
					PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

			Paragraph paragraph = new Paragraph(krutiDevText).setFont(font).setFontSize(12)
					.setFontColor(ColorConstants.BLACK);
			document.add(paragraph);
		}

		document.close();

	}
}
