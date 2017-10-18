package org.kiev.cinema;

import org.krysalis.barcode4j.impl.datamatrix.DataMatrixBean;
import org.krysalis.barcode4j.impl.datamatrix.SymbolShapeHint;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.LinkedList;

@Component
public class TicketImageCreator {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 280;
    private static final int X_START = 20;
    private static final int Y_START = 40;
    private static final int STEP = 24;

    public Path generate(LinkedList<String> printableList, Timestamp soldAtTime, Long ticketId) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        g2d.setColor(Color.PINK);
        g2d.fillRect(10, 10, WIDTH -160, HEIGHT -20);

        g2d.setColor(Color.BLACK);
        Font font = new Font("Monospaced", Font.PLAIN, 15);
        g2d.setFont(font);

        int count = 0;
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(String str : printableList) {
            g2d.drawString(str, X_START, Y_START +count* STEP);
            count++;
        }
        g2d.setColor(Color.GRAY);
        g2d.drawString(String.format("Sold at: %1$tF %1$tT", soldAtTime), 100, 278);

        BufferedImage barcode = generateDataMatrixBarcode(ticketId);
        g2d.drawImage(barcode, 500, 40, null);
        g2d.dispose();

        File file = new File( CinemaConstants.TICKETS_PATH + File.separator + String.format("ticket_%d.png", ticketId));
        ImageIO.write(bufferedImage, "png", file);
        return Paths.get(file.toURI());
    }

    private BufferedImage generateDataMatrixBarcode(Long ticketId) throws IOException {
        DataMatrixBean bean = new DataMatrixBean();
        bean.setShape(SymbolShapeHint.FORCE_RECTANGLE);
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(300, BufferedImage.TYPE_BYTE_GRAY, true, 90);
        bean.generateBarcode(canvas, String.format("%010d", ticketId));
        canvas.finish();
        BufferedImage barcodeImage = canvas.getBufferedImage();
        return barcodeImage;
    }

}
