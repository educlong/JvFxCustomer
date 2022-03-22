package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Optional;

public class Main extends Application {
    final int WIDTH_OF_WINDOW=400/*1100*/,  HEIGHT_OF_WINDOW=350/*850*/;
    final int HEIGHT_OF_SOUTH=50/*200*/, HEIGHT_OF_NORTH=200/*200*/;
    final int WIDTH_OF_WEST=50/*350*/, WIDTH_OF_EAST=50/*350*/;
    final int WIDTH_OF_LOGIN=250/*350*/, HEIGHT_OF_LOGIN=100/*350*/;

    Stage window;
    Scene scMain;
    @Override
    public void start(Stage primaryStage) throws Exception{             //javafx sẽ thực hiện zao diện như 1 sân khấu
        window=primaryStage;
        BorderPane loMain=new BorderPane();		                        //layout để bố trí các control cho 1 scene
        window.setTitle("Hello World");                                 //set title cho màn hình này
        scMain =new Scene(loMain, WIDTH_OF_WINDOW, HEIGHT_OF_WINDOW);   //đưa layout vào màn hình scene,
        window.setScene(scMain);  //đưa scene vào stage
        window.show();            //và show stage ra
        addControls(loMain);
        addEvent();
    }
    public static void main(String[] args) {
        launch(args);
    }

    private void addControls(BorderPane loMain) {
        HBox loNorth=new HBox();            //Các thành phần được sắp xếp theo thứ tự chiều ngang
        VBox loWest=new VBox();             //Các thành phần được sắp xếp theo chiều dọc.
        FlowPane loSouth=new FlowPane();    //zống flowPanel
        GridPane loEast=new GridPane();     //bao gồm các cột và các dòng tạo thành nhiều ô
        AnchorPane loCenter=new AnchorPane();//AnchorPane cho phép bạn thiết lập vị trí các node theo ý muốn
        loMain.setTop(loNorth);
        loMain.setLeft(loWest);
        loMain.setBottom(loSouth);
        loMain.setRight(loEast);
        loMain.setCenter(loCenter);
        loNorth.setPrefSize(0,HEIGHT_OF_NORTH);
        loSouth.setPrefSize(0,HEIGHT_OF_SOUTH);
        loEast.setPrefSize(WIDTH_OF_EAST,0);
        loWest.setPrefSize(WIDTH_OF_WEST,0);
        loNorth.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,null,null)));
        loSouth.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,null,null)));
        loEast.setBackground(new Background(new BackgroundFill(Color.GRAY,null,null)));
        loWest.setBackground(new Background(new BackgroundFill(Color.GRAY,null,null)));
        loCenter.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        processNorth(loNorth);
        processSouth(loSouth);
        processWest(loWest);
        processEast(loEast);
        processCenters(loCenter);
        processInformation();
    }

    private void addEvent() {
        eventNorth();
        eventSouth();
        eventWest();
        eventEast();
        eventCenter();
        eventInformation();
    }
    //new a scene for information
    private void processInformation() {

    }
    private void eventInformation() {

    }


    //east
    private void processEast(GridPane loEast) {

    }
    private void eventEast() {

    }
    //west
    private void processWest(VBox loWest) {

    }
    private void eventWest() {

    }
    //south
    private void processSouth(FlowPane loSouth) {

    }
    private void eventSouth() {

    }
    //north
    Button btnClose,btnNewWindow, btnLogin,btnGoBackNorth;
    Scene scNorthNew;
    CheckBox chkJava, chkCsharp, chkPython;
    private void processNorth(HBox loNorth) {   //checkbox
        VBox loNorth1=new VBox();
        loNorth.getChildren().add(loNorth1);
        btnNewWindow=new Button("New");
        btnClose=new Button("Close");
        btnLogin=new Button("Login");

        StackPane loNorthNew=new StackPane();
        btnGoBackNorth=new Button("Go Back");
        loNorthNew.getChildren().add(btnGoBackNorth);
        scNorthNew=new Scene(loNorthNew,50,50);

        loNorth1.getChildren().add(btnClose);
        loNorth1.getChildren().add(btnNewWindow);
        loNorth1.getChildren().add(btnLogin);

        Label lblLanguages=new Label("Languages");
        chkJava=new CheckBox("Java");
        chkCsharp=new CheckBox("C#");
        chkPython=new CheckBox("Python");
        chkJava.setSelected(true);
        loNorth1.getChildren().add(chkJava);
        loNorth1.getChildren().add(chkCsharp);
        loNorth1.getChildren().add(chkPython);
    }
    private void eventNorth() {
        btnNewWindow.setOnAction(event -> {
            window.setScene(scNorthNew);
        });
        btnGoBackNorth.setOnAction(event -> {
            window.setScene(scMain);
        });
        btnClose.setOnAction(event -> {   /* viết tắt của EventHandler<ActionEvent>() trong Java8 (Lambda Expression)*/
            Optional<ButtonType> result=Controller.alertYesNoCancel("Confirmation to close","Information to close?",
                    "Do u want to close? (Choose ur option)","YES", "NO","CANCEL",
                            ButtonBar.ButtonData.YES, ButtonBar.ButtonData.NO,  ButtonBar.ButtonData.CANCEL_CLOSE);
            if(result.get().getButtonData()== ButtonBar.ButtonData.YES){
                System.out.println("Code for yes");
            }
            else if(result.get().getButtonData()== ButtonBar.ButtonData.NO){
                System.out.println("Code for no");
            }
            else if(result.get().getButtonData()== ButtonBar.ButtonData.CANCEL_CLOSE){
                System.out.println("Code for cancel");
            }
            Controller.announcementAlert(Alert.AlertType.INFORMATION,"Information","Notification",result.get().getText()).show();
        });
        btnLogin.setOnAction(event -> { //nhấn vào button Login trên layout north => thực hiện show Dialog Login ra
            processLogin();
        });
    }
    //login
    Dialog<Pair<String,String>/* Pair là trả về 1 cặp data */> dialogLogin;
    ButtonType btntypeLogin;
    TextField txtUsername, txtPassword;
    Node btnNodeLogin;
    private void processLogin() {   //for scene scLogin
        //code layout for Dialog login
        GridPane loGridLogin=new GridPane();
        Scene scLogin=new Scene(loGridLogin,WIDTH_OF_LOGIN,HEIGHT_OF_LOGIN);
        loGridLogin.setHgap(2); //set cac control cach nhau theo chieu ngang la 2
        loGridLogin.setVgap(5); //set cac control cach nhau theo chieu doc la 5
        loGridLogin.setPadding(new Insets(2,2,2,2));    //pading cho grid
        txtUsername=new TextField();
        txtUsername.setPromptText("Username");
        txtPassword=new PasswordField();
        txtPassword.setPromptText("Password");
        loGridLogin.add(new Label("Username: "),0,0);
        loGridLogin.add(new Label("Password: "),0,1);
        loGridLogin.add(txtUsername,1,0);
        loGridLogin.add(txtPassword,1,1);
        //xử lý cho login
        btntypeLogin=new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);    //tạo 1 button Login có kiểu ButtonType
        dialogLogin=Controller.dialogTwoButtonWithCancel("Login Dialog","Sign Up",btntypeLogin);   //tạo dialog 2 button vs 1 button là cancel
        btnNodeLogin= dialogLogin.getDialogPane().lookupButton(btntypeLogin);       //lấy button chính ra trả về 1 node
        btnNodeLogin.setDisable(true);  //ẩn button login đi

        dialogLogin.getDialogPane().setContent(loGridLogin);                        //đưa layout grid này vào dialog
        eventLogin();
    }
    private void eventLogin() {
        txtUsername.textProperty().addListener((observable, oldValue, newValue) -> {    //nếu có nhập data trong username thì mới đc nhấn login
            //observable là 1 interface để gói data của username, kiểm tra sự thay đổi của username, trả về 2 zá trị là oldValue và newValue
            btnNodeLogin.setDisable(newValue.trim().isEmpty());//kiểm tra newValue, nếu nó khác null thì cho cái btnNodeLogin hiện ra
        });
        dialogLogin.setResultConverter(dialogButton->{ //dialog Option trả về kiểu data <Pair<String,String>> => viết hàm để trả về kiểu <Pair<String,String>> này
            if (dialogButton ==btntypeLogin){           //khi nào dialog button  nhấn vào login button thì mới trả về data
                return new Pair<>(txtUsername.getText(),txtPassword.getText()); //thì sẽ trả về zá trị của username và password
            }
            return null;    //còn nhấn vào cancel thì trả về null
        });
        Optional<Pair<String,String>> result =dialogLogin.showAndWait();   //sau đó lấy cái kết quả đó để trả về username và password
        result.ifPresent(usernamePassword->{                                //với key là username và value là password
            System.out.println("Username="+usernamePassword.getKey()+", Password="+usernamePassword.getValue());
            window.setScene(scMain);                                        //rồi tắt dialog này đi, show scene scMain lên
        });
    }
    //center
    private void processCenters(AnchorPane loCenter) {

    }
    private void eventCenter() {

    }
}
