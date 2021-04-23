package com.calabrianshop.progettopsw.support;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.IOException;

public class HeaderFooterPageEvent extends PdfPageEventHelper {



    public void onStartPage(PdfWriter writer, Document document) {
        String img = "C:"+File.separator+"Users"+File.separator+"Cesco2"+File.separator+"Desktop"+File.separator+"Unical"+File.separator+"progettopsw"+File.separator+"src"+File.separator+"main"+File.separator +"resources"+File.separator+"static"+File.separator+"logo.jpg";
        Image image;
        try {
            image = Image.getInstance(img);
            image.setAlignment(Element.ALIGN_RIGHT);
            image.setAbsolutePosition(135, 780);
            image.scalePercent(40f, 40f);
            writer.getDirectContent().addImage(image, true);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(""), 200, 800, 0);

    }

    public void onEndPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(" CalabrianShop Spa, Sede: via pietro bucci"), 110, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);
    }

}