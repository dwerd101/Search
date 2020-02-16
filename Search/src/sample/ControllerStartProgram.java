package sample;

import java.io.*;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControllerStartProgram {
    private static ArrayList<String> list = new ArrayList<>();
    private final FileChooser fileChooser = new FileChooser();
    private File file;

    @FXML // fx:id="open"
    private Button open;

    @FXML // fx:id="close"
    private Button close;


    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        close.setOnAction(actionEvent -> {
            Platform.exit();
        });
        open.setOnAction(actionEvent -> {
            openButton();
        });

    }


    private void readFileAndConvertToString(File file) {
        try {
            String text = "";
            String read = "";

            try (BufferedReader buf = new BufferedReader(new FileReader(file))) {
                while ((read = buf.readLine()) != null) {
                    list.add(read);

                }
            }
        } catch (IOException e) {

        }

    }


    protected static ArrayList<String> getList() {
        return list;
    }

    private void openButton() {
        fileChooser.getExtensionFilters().addAll(//
                new FileChooser.ExtensionFilter("TXT", "*.txt"));

        file = fileChooser.showOpenDialog(StartProgram.primaryFinalStage);
        if (file != null) {

            readFileAndConvertToString(file);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = FXMLLoader.load(getClass().getResource("../values/fxml/main.fxml"));

                open.getScene().getWindow().hide();                                 //скрыть текущее окно
                Scene scene = new Scene(root);
                Stage stage = new Stage(StageStyle.DECORATED);
                StartProgram.primaryFinalStage = stage;
                stage.setScene(scene);
                stage.show();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
