package sampleGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sampleGUI.models.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerStudentDetails implements Initializable {    //transaction data
    @FXML
    public Label lblAgeStudentDetailDemo,lblEmailStudentDetailDemo,lblNameStudentDetailsDemo,lblIdStudentDetailDemo;
    @FXML
    public Button btnBackMainDemo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addEvent();
    }

    public void detailStudent(Student student){
        lblIdStudentDetailDemo.setText(String.valueOf(student.getStudentId()));
        lblNameStudentDetailsDemo.setText(student.getStudentName());
        lblEmailStudentDetailDemo.setText(student.getStudentEmail());
        lblAgeStudentDetailDemo.setText(String.valueOf(student.getStudentAge()));
    }

    private void addEvent() {
        btnBackMainDemo.setOnAction(event -> {
            FXMLLoader loader=new FXMLLoader();     //FXMLLoader để load scene sampleGUI.fxml
            loader.setLocation(getClass().getResource("sampleGUI.fxml"));    //load màn hình đó lên
            Parent parentSample= null;              //lấy parent để tạo ra scene, zống như trong class MainGUI.java
            try {
                parentSample = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene sceneSample=new Scene(parentSample,MainGUI.WIDTH_OF_WINDOW,MainGUI.HEIGHT_OF_WINDOW); //tạo ra scene, để đưa parent vào,
            ((Stage)((Node) event.getSource()).getScene().getWindow()).setScene(sceneSample);           //đưa scene này vòa 1 cái stage zống như bên MainGUI.java
        });
    }
}
