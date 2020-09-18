package ru.itis.rabbitmq.consumers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.layout.property.Property;
import ru.itis.rabbitmq.dto.UserDto;

import java.io.IOException;

public class ExclusionPdf {
    public static void generate(UserDto user) {
        try {
            PdfDocument pdf = new PdfDocument(new PdfWriter("exclusion.pdf"));
            Document document = new Document(pdf);

            final FontSet set = new FontSet();
            set.addFont("C:\\Windows\\Fonts\\arial.ttf");
            document.setFontProvider(new FontProvider(set));
            document.setProperty(Property.FONT, new String[]{"MyFontFamilyName"});

            Paragraph p = new Paragraph("Директору");
            p.setFixedPosition(400, 800, 100);
            document.add(p);

            Paragraph p2 = new Paragraph("КФУ ВШ ИТИС");
            p2.setFixedPosition(400, 780, 100);
            document.add(p2);

            Paragraph p3 = new Paragraph("М. Абрамскому");
            p3.setFixedPosition(400, 760, 100);
            document.add(p3);

            Paragraph p4 = new Paragraph("от " + user.getFirst_name() + " " + user.getLast_name());
            p4.setFixedPosition(400, 740, 100);
            document.add(p4);

            Paragraph p5 = new Paragraph("Заявление");
            p5.setFixedPosition(250, 680, 100);
            document.add(p5);

            Paragraph p6 = new Paragraph("Я, " + user.getFirst_name() + " " + user.getLast_name() + ", " + user.getNumberOfpassport() + ", " + user.getAge() + ", " + "прошу Вас отчислить из университета.");
            p6.setFixedPosition(100, 650, 600);
            document.add(p6);

            Paragraph p7 = new Paragraph("Дата: " + user.getDate());
            p7.setFixedPosition(100, 600, 100);
            document.add(p7);

            Paragraph p8 = new Paragraph("Подпись _________");
            p8.setFixedPosition(400, 600, 300);
            document.add(p8);

            document.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
