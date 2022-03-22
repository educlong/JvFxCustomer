package sampleGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    public static Stage window;
    public static Scene scMain;
    public static int WIDTH_OF_WINDOW=1100;
    public static int HEIGHT_OF_WINDOW=700;
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            window = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("sampleGUI.fxml"));    //thêm file xml vào
            window.setTitle("Java FX Basic");
            scMain = new Scene(root, WIDTH_OF_WINDOW, HEIGHT_OF_WINDOW);
            scMain.getStylesheets().add(getClass().getResource("applicationcss.css").toExternalForm());
            window.setScene(scMain);
            window.setResizable(false);     //cố định screen, ko mở rộng screen ra đc.
            window.show();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
