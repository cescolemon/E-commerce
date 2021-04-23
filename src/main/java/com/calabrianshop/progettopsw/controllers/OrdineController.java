package com.calabrianshop.progettopsw.controllers;

import com.calabrianshop.progettopsw.entities.Bolla;
import com.calabrianshop.progettopsw.entities.Ordine;
import com.calabrianshop.progettopsw.entities.OrdineProdotto;
import com.calabrianshop.progettopsw.reporsitories.OrdineProdottoRepository;
import com.calabrianshop.progettopsw.services.BollaService;
import com.calabrianshop.progettopsw.services.OrdineService;
import com.calabrianshop.progettopsw.support.HeaderFooterPageEvent;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrdineController {
    @Autowired
    private OrdineService ordineService;
    @Autowired
    private BollaService bollaService;

    @Autowired
    private OrdineProdottoRepository ordineProdottoRepository;

    private static Bolla bolla;

    @GetMapping
    @ResponseBody
    public List<Ordine> getOrdiniUtente(){
        return ordineService.getOrdiniUtente();
    }

    @PostMapping("/ordered")
    @ResponseBody
    public List<OrdineProdotto> getProdottiOrdinati( @RequestBody Ordine ordine){
        List<OrdineProdotto> l=ordineService.getProdottiOrdinati(ordine);
        for(OrdineProdotto op : l){
            System.out.println(op.getProdotto().getNome()+" "+op.getProdotto().getQuantita());
        }
        return l;
    }

    @PostMapping("/data")
    public String getData(@RequestBody Ordine ordine){
        return ordineService.getData(ordine);
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> bollaToPDF() {
        List<OrdineProdotto> prodotti = (List<OrdineProdotto>) bolla.getOrdine().getOrdineProdottoCol();
        ByteArrayInputStream pd = PdfReporter(prodotti);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","inline; filename=bolla.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity(new InputStreamResource(pd), headers,HttpStatus.OK);


    }

    @PostMapping("/bolla")
    @ResponseBody
    public Bolla getBolla(@RequestBody Ordine ordine){
        this.bolla=bollaService.generaBolla(ordine);
        return this.bolla;
    }


    public static ByteArrayInputStream PdfReporter(List<OrdineProdotto> prod){
        Document document = new Document(PageSize.A4, 20, 20, 50, 25);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {


            PdfWriter writer = PdfWriter.getInstance(document, out);
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);
            document.open();

            com.itextpdf.text.Font font= FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph("Bolla ordine", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);
            com.itextpdf.text.Font fot= FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
            Paragraph par = new Paragraph("ID Bolla: "+bolla.getId(),fot);
            document.add(par);
            Paragraph par2 = new Paragraph("Indirizzo spedizione: "+bolla.getOrdine().getIndirizzo(),fot);
            document.add(par2);
            Paragraph par3 = new Paragraph("Destinatario: "+bolla.getUtente().getNome(),fot);
            document.add(par3);
            document.add(Chunk.NEWLINE);
            PdfPTable table= new PdfPTable(3);

            Stream.of("Prodotto","Quantita'","Venditore").forEach(headerTitle ->{
                PdfPCell header = new PdfPCell();
                com.itextpdf.text.Font headFont= FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);
            });


            for(OrdineProdotto p : prod){
                PdfPCell idCell = new PdfPCell(new Phrase(""+p.getProdotto().getId()+" "+p.getProdotto().getNome()));
                idCell.setPaddingLeft(1);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(idCell);

                PdfPCell qtCell = new PdfPCell(new Phrase(""+p.getQuantita()+" x "+p.getProdotto().getPrezzo()+" €"));
                qtCell.setPaddingLeft(1);
                qtCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                qtCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(qtCell);

                PdfPCell vdCell = new PdfPCell(new Phrase(""+p.getProdotto().getVenditore()));
                vdCell.setPaddingLeft(1);
                vdCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                vdCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(vdCell);
            }

            document.add(table);
            document.add(Chunk.NEWLINE);
            Paragraph par4 = new Paragraph("Totale ordine: "+bolla.getOrdine().getTotale() +" €",fot);
            document.add(par4);
            document.close();

        }catch(DocumentException e){
            e.printStackTrace();

        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}
