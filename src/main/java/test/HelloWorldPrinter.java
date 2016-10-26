package test;

import javafx.scene.control.Dialog;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

/**
 * Created by sereo_000 on 26.10.2016.
 */
public class HelloWorldPrinter implements Printable, ActionListener {


    public int print(Graphics g, PageFormat pf, int page) throws
            PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Now we perform our rendering */
        g.drawString("Hello world!", 100, 100);

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(this);

            boolean ok = job.printDialog();
            if (ok) {
                try {
                    job.print();
                } catch (PrinterException ex) {
                    javafx.scene.control.Dialog errDialog = new Dialog();
                    errDialog.setContentText(ex.getMessage());
                    errDialog.showAndWait();
              /* The job did not successfully complete */
                }
            }
        /*File lol = new File("C:/srv/bbb-1477307762270.pdf");
        PrinterJob job1 = PrinterJob.getPrinterJob();
        try {
            PDDocument document = PDDocument.load(lol);
            job1.setPageable(new PDFPageable(document));
            job1.print();
        } catch (IOException e1) {
            javafx.scene.control.Dialog errDialog = new Dialog();
            errDialog.setContentText(e1.getMessage());
            errDialog.showAndWait();
            e1.printStackTrace();
        } catch (PrinterException e1) {
            e1.printStackTrace();
            javafx.scene.control.Dialog errDialog = new Dialog();
            errDialog.setContentText(e1.getMessage());
            errDialog.showAndWait();
        }*/
        } catch (Throwable e1){
            e1.printStackTrace();
            javafx.scene.control.Dialog errDialog = new Dialog();
            errDialog.setContentText(e1.getMessage());
            errDialog.showAndWait();
        } finally {
            javafx.scene.control.Dialog errDialog = new Dialog();
            errDialog.setContentText("I dinirly a here");
            errDialog.showAndWait();
        }
    }

    public static void main(String args[]) {

        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame f = new JFrame("Hello World Printer");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JButton printButton = new JButton("Print Hello World");
        printButton.addActionListener(new HelloWorldPrinter());
        f.add("Center", printButton);
        f.pack();
        f.setVisible(true);
    }
}
