package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class StartProgram extends Application {
    public static Stage primaryFinalStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryFinalStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../values/fxml/sample.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED); //Скрыть кнопкdf
        primaryStage.setScene(new Scene(root)); //Вызов новый сцены
        primaryStage.centerOnScreen(); //Центрировать
        primaryStage.setTitle("Search");
        primaryStage.show(); //Показать

    }

    public static void main(String[] args) {
        launch(args);
    }

}
