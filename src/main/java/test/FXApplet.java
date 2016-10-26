package test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.print.Printer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.swing.*;
import java.io.File;
import java.util.Optional;




/**
 * Created by Валерий on 03.10.2016.
 */
public class FXApplet extends JApplet{
    protected Scene scene;
    protected Group root;

    @Override
    public final void init() { // This method is invoked when applet is loaded
        SwingUtilities.invokeLater(new Runnable() {
            //@Override
            public void run() {
                initSwing();
            }
        });
    }

    private void initSwing() { // This method is invoked on Swing thread
        final JFXPanel fxPanel = new JFXPanel();
        add(fxPanel);

        Platform.runLater(new Runnable() {
            //@Override
            public void run() {
                try {
                    initFX(fxPanel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                initApplet();
            }
        });
    }

    private void initFX(JFXPanel fxPanel) throws Exception { // This method is invoked on JavaFX thread
        root = new Group();
        scene = new Scene(root);
        //Сюда передавать путь из выбранного файла
        File pdfFile = new File("C:/Users/sereo_000/Downloads/magistratura_1_kurs_4_f-t.pdf");
        Node node = null;
        final VBox root = new VBox(5);
        scene.setUserData(pdfFile);

        String param = this.getParameter("pathToFile");

        String name = print(node);

        try {
            //HelloWorldPrinter.main(null);
            Testing.main(name/*,param*/);

        } catch (Exception e) {
            e.printStackTrace();
            /*javafx.scene.control.Dialog errDialog = new Dialog();
            errDialog.setContentText(e.getMessage());
            errDialog.showAndWait();*/
        }
        //print(root);

    }

    public void initApplet() {
        // Add custom initialization code here

    }

    public String print(Node node) {
        ChoiceDialog dialog = new ChoiceDialog(Printer.getDefaultPrinter(), Printer.getAllPrinters());
        dialog.setHeaderText("Choose the printer!");
        dialog.setContentText("Choose a printer from available printers");
        dialog.setTitle("Printer Choice");
        boolean justForWhile = true;
        while (justForWhile){
            Optional<Printer> opt = dialog.showAndWait();
            if (opt.isPresent()) {
                Printer printer = opt.get();
                String nameOfPrinter = printer.getName();
                if (nameOfPrinter.contains("PDF") || nameOfPrinter.contains("OneNote") || nameOfPrinter.contains("Fax") || nameOfPrinter.contains("Pdf") || nameOfPrinter.contains("pdf")) {
                    Dialog errorDialog = new Dialog();
                    errorDialog.setHeaderText("This printer is not available!");
                    errorDialog.setTitle("Error choosing printer");
                    errorDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                    errorDialog.showAndWait();

                } else {
                    return nameOfPrinter;

                }

            }
        }
        return null;
    }

}
