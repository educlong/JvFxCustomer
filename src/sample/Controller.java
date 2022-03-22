package sample;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;

public class Controller {
    public static Optional<ButtonType> alertYesNoCancel(String title, String header, String content, String buttonType1,
            String buttonType2, String buttonType3, ButtonBar.ButtonData btnData1, ButtonBar.ButtonData btnData2, ButtonBar.ButtonData btnData3) {
        Alert alert=announcementAlert(Alert.AlertType.CONFIRMATION,title,header, content);
        ButtonType btntpAlert1=new ButtonType(buttonType1, btnData1);
        ButtonType btntpAlert2=new ButtonType(buttonType2, btnData2);
        ButtonType btntpAlert3=new ButtonType(buttonType3, btnData3);
        alert.getButtonTypes().setAll(btntpAlert1,btntpAlert2,btntpAlert3);
        return alert.showAndWait();
    }
    public static Optional<ButtonType> alertYesNo(String title, String header, String content, String buttonType1, ButtonBar.ButtonData btnData1,
                                                 String buttonType2, ButtonBar.ButtonData btnData2) {
        Alert alert=announcementAlert(Alert.AlertType.CONFIRMATION,title,header, content);
        ButtonType btntpAlert1=new ButtonType(buttonType1, btnData1);
        ButtonType btntpAlert2=new ButtonType(buttonType2, btnData2);
        alert.getButtonTypes().setAll(btntpAlert1,btntpAlert2);
        return alert.showAndWait();
    }
    public static Alert announcementAlert(Alert.AlertType altype, String title, String header, String messages){
        Alert alert=new Alert(altype);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(messages);
        return alert;
    }
    //thường dùng cho login
    public static Dialog<Pair<String,String>> dialogTwoButtonWithCancel (String title, String header, ButtonType btntypeOption){
        Dialog<Pair<String,String>> dialogOption=new Dialog<>();
        dialogOption.setTitle(title);
        dialogOption.setHeaderText(header);
        dialogOption.getDialogPane().getButtonTypes().addAll(btntypeOption,ButtonType.CANCEL);
        return  dialogOption;
    }
}
