package sampleGUI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.converter.NumberStringConverter;
import sampleGUI.hibernate.DAOCustomer;
import sampleGUI.hibernate.DAOCustomerGroup;
import sampleGUI.models.*;
import sampleGUI.services.SQLCustomerGroupServices;
import sampleGUI.services.SQLCustomerServices;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

//tạo class để thực hiện đa tiến trình trong progressBar và progressIndicator
class TaskProgressBarDemo_progressIndicator_Demo extends Task<Void>{   //truyền vào hàm call của task này là hàm void, rồi implement nó
    @Override
    protected Void call() throws Exception {    //đưa task này vào progressBar và progressIndicator, progressBar và progressIndicator sẽ lưu đc zá trị của task này
        for (int i=0;i<10;i++){
            if (isCancelled()){                 //nếu task bị hoãn thì cancel và cập nhật lại cái message, sau đó break để bỏ vòng lặp này đi
                updateMessage("Cancelled");
                break;
            }
            updateProgress(i+1,10);//cập nhật lại xem tiến trình này thực hiện đc bao nhiêu %
            updateMessage("Loading...");          //cập nhật xong cái progress thì cập nhật cái message là đang loading
            Thread.sleep(1000);             //cho tiến trình nghỉ trong vòng 1s, tức là nghỉ 1s sau rồi quay lại vòng lặp này
        }
        updateMessage("Finished!");             //hết vòng lặp thì update lại message là finished
        return null;
    }

}
public class ControllerGUI implements Initializable {
                    /*interface Initializable để tạo ra phương thức initialize, phương thức này sẽ chạy đầu tiên khi chương trình khởi chạy*/
    Scene scNewWindow;
    Button btnGoBackNorth;
    Dialog<Pair<String,String>/* Pair là trả về 1 cặp data */> dialogLogin;
    ButtonType btntypeLogin;
    TextField txtUsername, txtPassword;
    Node btnNodeLogin;
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    @FXML   //checkbox
    public CheckBox chkJava, chkCsharp, chkPython;
    @FXML
    public Button btnCheckDemo;

    //menuBar, contextMenu
    @FXML
    public MenuItem mnuCloseDemo;
    @FXML
    MenuItem mnuItemContext1,mnuItemContext2;
    @FXML
    Button btnContextMenuDemo1,btnContextMenuDemo2;

    @FXML   //treeview
    public TreeView<String> tvDemo;
    @FXML   //comboBox
    public ComboBox<String>cboDemo; /*ObservableList là 1 collection, là 1 cái list nhưng nó có thể bắt đc sự kiện thay đổi*/
    ObservableList<String>obListDemo= FXCollections.observableArrayList("Java","C#","Python"); /*ObservableList ko thể khởi tạo đc, phải gọi thư viện khác để khởi tạo nó*/

    //progressBar and progressIndicator, xử lý bằng đa tiến trình
    TaskProgressBarDemo_progressIndicator_Demo taskProgressDemo;
    @FXML
    public Label lblComboboxDemo;
    @FXML
    public ProgressBar prgrbarDemo;
    @FXML
    public ProgressIndicator prgridcDemo;
    @FXML
    public Label lblReadyDemo;
    @FXML
    public Button btnStart,btnCancel;

    static final double INIT_VALUE =50; //slider
    @FXML
    public Slider sldDemo;
    @FXML
    public TextField txtSliderDemo;

    @FXML   //imageView, imageButton, FileChooser
    public ImageView imgDemo;
    @FXML
    public Button btnImgDemo;
    @FXML
    public BorderPane loMain;   //layout chứa scene Main, ko cần dùng public stage trong MainGUI.java nữa. Xử lý cho FileChooser

    //webview
    @FXML
    public Button btnPreviousWebviewDemo,btnNextWebviewDemo;
    @FXML
    public TextField txtAddressBarDemo;
    @FXML
    public WebView webviewDemo;
    private WebEngine webEngineDemo;    //webview chỉ hiển thị ra, còn webengine để load đến trang web để nhận html về, đưa html cho webview đẻ hiển thị ra.
    //pie chart
    @FXML
    public TextField txtCsharpPiechartDemo, txtJavaPiechartDemo, txtPythonPiechartDemo;
    @FXML
    public Button btnLoadPiechartDemo;
    @FXML
    public PieChart pieChartDemo;
    //line chart
    @FXML
    public LineChart<String,Number> lineChartDemo;  //cột X kiểu String, cột Y kiểu Number

    //tableView và truyền data sang scene khác
    @FXML
    public TableView<Student> tblvStudentDemo;
    @FXML
    private TableColumn<Student, Integer> columnIdStudentDemo, columnAgeStudentDemo;
    @FXML
    private TableColumn<Student, String> columnNameStudentDemo, columnEmailStudentDemo;
    private ObservableList<Student> studentsDemo;   //đưa data vào list này, ObservableList sẽ ràng buộc table này, data trên table thay đổi làm ObservableList thay đổi theo.
    @FXML
    public TextField txtIdStudentDemo, txtNameStudentDemo, txtEmailStudentDemo, txtAgeStudentDemo;
    @FXML
    public Button btnAddStudentDemo, btnDeleteStudentDemo;
    @FXML
    public Button btnDetailStudentDemo; //truyền data qua scece khác

    //Employee Management
    @FXML
    ListView<Employee> lvEmployees;
    @FXML
    ComboBox<Department> cboDepartments;
    @FXML
    TextField txtCodeEmployee, txtNameEmployee, txtDayStartWorkingEmployee, txtDayOfBirthEmployee;
    @FXML
    Button btnSaveEmployee,btnDeleteEmployee,btnExitEmployee;
    public ObservableList<Department>departments;
    Employee employeeSelected=null;
    Department departmentSelected=null;

    //customer management
    @FXML
    TreeView<Object> tvCustomerGroup;
    TreeItem<Object>tvCustomerRoot;
    @FXML
    TableView<CustomerDb> tblCustomers;
    @FXML
    TableColumn<CustomerDb,String> columnEmailCustomer, columnPhoneCustomer, columnNameCustomer, columnCodeCustomer;
    @FXML
    TextField txtCodeCustomer, txtNameCustomer, txtPhoneCustomer, txtEmailCustomer, txtNameCustomerGroup, txtCodeCustomerGroup;
    @FXML
    Button btnSaveCustomer, btnDeleteCustomer,btnLastCustomer, btnFirstCustomer,btnPreviousCustomer,btnNextCustomer, btnSaveCustomerGroup, btnDeleteCustomerGroup;
    ObservableList<CustomerGroupDb> customerGroups;   //xử lý cho sql
    ObservableList<CustomerDb> customers;             //xử lý cho sql
    CustomerGroupDb customerGroupSelected=null;
    CustomerDb customerSelected=null;
    SQLCustomerGroupServices customerGroupServices=new SQLCustomerGroupServices();
    SQLCustomerServices customerServices=new SQLCustomerServices();

    //customer management hibernate
    @FXML
    ListView<CustomerGroup> lvCustomerGroupHibernate;
    @FXML
    TableView<Customer> tblCustomersHibernate;
    @FXML
    TableColumn<Customer,String> columnEmailCustomerHibernate, columnPhoneCustomerHibernate, columnNameCustomerHibernate, columnCodeCustomerHibernate;
    @FXML
    TextField txtCodeCustomerHibernate, txtNameCustomerHibernate, txtPhoneCustomerHibernate;
    @FXML
    TextField txtEmailCustomerHibernate, txtNameCustomerGroupHibernate, txtCodeCustomerGroupHibernate;
    @FXML
    Button btnSaveCustomerHibernate, btnDeleteCustomerHibernate,btnLastCustomerHibernate, btnFirstCustomerHibernate;
    @FXML
    Button btnPreviousCustomerHibernate,btnNextCustomerHibernate, btnSaveCustomerGroupHibernate, btnDeleteCustomerGroupHibernate;
    ObservableList<CustomerGroup> customerGroupsHibernate;   //xử lý cho sql
    ObservableList<Customer> customersHibernate;             //xử lý cho sql
    CustomerGroup customerGroupHibernateSelected=null;
    Customer customerHibernateSelected=null;
    DAOCustomerGroup daoCustomerGroup=null;
    DAOCustomer daoCustomer=null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addControls();
        addEvents();
    }

    public void btnNewWindow (ActionEvent event){
        StackPane loNewWindow=new StackPane();
        btnGoBackNorth=new Button("Go Back");
        loNewWindow.getChildren().add(btnGoBackNorth);
        scNewWindow=new Scene(loNewWindow,50,50);
        MainGUI.window.setScene(scNewWindow);
        btnGoBackNorth.setOnAction(event1 -> {
            MainGUI.window.setScene(MainGUI.scMain);
        });
    }
    public void btnClose(ActionEvent event){
        Optional<ButtonType> result= AnnounceFactory.alertYesNoCancel("Confirmation to close","Information to close?",
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
        AnnounceFactory.announcementAlert(Alert.AlertType.INFORMATION,"Information","Notification",result.get().getText()).show();
    }
    public void btnLogin(ActionEvent event){
        controlLogin();
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
            MainGUI.window.setScene(MainGUI.scMain);                                        //rồi tắt dialog này đi, show scene scMain lên
        });
    }
    private void controlLogin() {
        //code layout for Dialog login
        GridPane loGridLogin=new GridPane();
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
        dialogLogin=AnnounceFactory.dialogTwoButtonWithCancel("Login Dialog","Sign Up",btntypeLogin);   //tạo dialog 2 button vs 1 button là cancel
        btnNodeLogin= dialogLogin.getDialogPane().lookupButton(btntypeLogin);       //lấy button chính ra trả về 1 node
        btnNodeLogin.setDisable(true);  //ẩn button login đi

        dialogLogin.getDialogPane().setContent(loGridLogin);                        //đưa layout grid này vào dialog
    }

    private void addControls() {
        addControlTreeViewDemo();
        addControlComboBoxDemo();
        addControlContextMenu();
        addControlSlider();
        addControlWebview();
        addControlLineChart();
        addControlTableView();

        addControlCustomerManagement();

        addControlCustomerManagementHibernate();
    }

    private void addEvents() {
        addEventCheckBox();
        addEventTreeViewDemo();
        addEventComboBoxDemo();
        addEvent_progressBarDemo_progressIndicator_Demo();
        addEventForMenu();
        addEventChangePictureForImageView();
        addEventWebview();
        addEventPieChart();
        addEventTableView();

        addEventEmployeeManagement();
        fakeDataEmployeeManagement();

        addEventCustomerManagement();
        fakeDataCustomerManagement();

        addEventCustomerManagementHibernate();
    }

    private void addControlComboBoxDemo() {
        obListDemo.add("Android");
        cboDemo.setItems(obListDemo);
    }
    private void addControlContextMenu() {
        btnContextMenuDemo1.setContextMenu(new EventContextMenuButton(btnContextMenuDemo1));    //gọi class EventContextMenuButton kế thừa từ ContextMenu
        btnContextMenuDemo2.setContextMenu(new EventContextMenuButton(btnContextMenuDemo2));
    }
    private void addControlTreeViewDemo() {
        TreeItem<String>tvDemoRoot=new TreeItem<>();
        TreeItem<String>tvDemoBranch1=new TreeItem<>("Java");
        TreeItem<String>tvDemoBranch2=new TreeItem<>("C#");
        TreeItem<String>tvDemoBranch1Leaf1=new TreeItem<>("Java Swing");
        TreeItem<String>tvDemoBranch1Leaf2=new TreeItem<>("Java FX");
        tvDemoBranch1.getChildren().addAll(tvDemoBranch1Leaf1,tvDemoBranch1Leaf2);
        tvDemoBranch1.setExpanded(true);
        tvDemoRoot.getChildren().addAll(tvDemoBranch1,tvDemoBranch2);
        tvDemoRoot.setExpanded(true);
        tvDemo.setRoot(tvDemoRoot);
        tvDemo.setShowRoot(false);
    }
    private void addControlWebview() {
        webEngineDemo=webviewDemo.getEngine();  //lấy webengine từ trong webview, và dùng cái engine này để load trang web, và bắt sự kiện cho addressbar
    }
    private void addControlTableView() {
        studentsDemo=FXCollections.observableArrayList(new Student(1,"trump","trumpusa@gmail.com",74),      //khai báo 2 students
                new Student(2,"putin","putinrussia@gmail.com",70)); //đưa vào list
        columnIdStudentDemo.setCellValueFactory(new PropertyValueFactory<Student,Integer>("studentId"));        //set hiển thị cho từng cột
        columnNameStudentDemo.setCellValueFactory(new PropertyValueFactory<Student,String>("studentName"));
        columnEmailStudentDemo.setCellValueFactory(new PropertyValueFactory<Student,String>("studentEmail"));
        columnAgeStudentDemo.setCellValueFactory(new PropertyValueFactory<Student,Integer>("studentAge"));
        tblvStudentDemo.setItems(studentsDemo);
    }
/**/private void addControlSlider() {
    sldDemo.setValue(INIT_VALUE);
    txtSliderDemo.setText(String.valueOf(INIT_VALUE));
    sldDemo.setMax(600);
    txtSliderDemo.textProperty().bindBidirectional(sldDemo.valueProperty(), new NumberStringConverter());
    /* Giải thích:
     * textProperty để set ràng buộc vs sldDemo. bindBidirectional là tạo ràng buộc 2 chiều, truyền chiều còn lại vào
     * khi đưa sldDemo vào nó sẽ báo lỗi vì format của valueProperty khác vs format của textProperty.
     * Do đó cần phải có 1 class để set format cho nó là NumberStringConverter để chuyển từ number sang string*/
}
    private void addControlLineChart() {
        XYChart.Series<String,Number> seriesDemo = new XYChart.Series<>();  //tạo tập data của line chart, lưu vào series
        XYChart.Data<String,Number> january = new XYChart.Data<>("T1",300);
        XYChart.Data<String,Number> february = new XYChart.Data<>("T2",400);
        XYChart.Data<String,Number> march = new XYChart.Data<>("T3",600);
        XYChart.Data<String,Number> april = new XYChart.Data<>("T4",100);
        seriesDemo.getData().addAll(january,february,march,april);          //đưa data vào line chart
        seriesDemo.setName("Thu nhap theo hang thang");
        lineChartDemo.getData().add(seriesDemo);        //add series vào linechart cho 1 người, muốn add nhiều người thì tạo thêm 1 series nữa rồi add vào linechart.
    }

    private void addEventComboBoxDemo() {
        cboDemo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (cboDemo.getSelectionModel().getSelectedIndex()==-1) return; //tức là k chọn zì cả thì k làm zì cả
            lblComboboxDemo.setText(cboDemo.getValue());
        });
    }
    private class EventContextMenuButton extends ContextMenu {
        public EventContextMenuButton(Button button) {
            mnuItemContext1 = new MenuItem("Tô màu Đỏ");
            mnuItemContext2 = new MenuItem("Tô màu Xanh");
            mnuItemContext1.setOnAction(event -> {                      //context menu 1 làm zì
                button.setStyle("-fx-background-color: palevioletred"); //setbackground for button
            });
            mnuItemContext2.setOnAction(event ->{                       //context menu 2 làm zì
                button.setStyle("-fx-background-color: skyblue");       //setbackground for button
            });
            getItems().addAll(mnuItemContext1,mnuItemContext2);
        }
    }
    private void addEventTreeViewDemo() {
        tvDemo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue.getValue());    //newvalue chính là các TreeViewItem
        });
    }
    private void addEventWebview() {
        txtAddressBarDemo.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {//kiểm tra xem cái key nhập vào có phải phím enter k?, nếu là phím enter thì lấy nội dung mà người dùng nhập vào
                String addressBar= txtAddressBarDemo.getText();
                webEngineDemo.load("https://www."+addressBar);
            }
        });
        webEngineDemo.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> { //bắt sự kiện của stateProperty của engine, khi có thay đổi của engine
            if (newValue== Worker.State.SUCCEEDED){ //khi có thay đổi (tức là load sang trang web khác), load thành công =>thay đổi địa chỉ addressbar =địa chỉ mới của engine
                txtAddressBarDemo.setText(webEngineDemo.getLocation());
            }
        });
        btnPreviousWebviewDemo.setOnAction(event -> {
            if (webEngineDemo.getHistory().getCurrentIndex()>0){    //khi nhấn nút quay trở lại thì kiểm tra lịch sử hiện tại của nó có lờn hơn 0 hay k?
                webEngineDemo.getHistory().go(-1);            //go=-1 tức là lùi về 1 bước
            }
        });
        btnNextWebviewDemo.setOnAction(event -> {                   //getHistory().getCurrentIndex()+1 là kiểm tra xem nếu nó k có trang cuối cùng
            if (webEngineDemo.getHistory().getCurrentIndex()+1 < webEngineDemo.getHistory().getEntries().size()) { //kiểm tra xem nó có bằng kích thước của history ko?
                webEngineDemo.getHistory().go(1);            //getHistory().getEntries().size() là tổng các trang web trong lịch sử. Nếu mà < thì mới nhảy sang trang kế
            }
        });
    }
    private void addEventTableView() {
        tblvStudentDemo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int row=tblvStudentDemo.getSelectionModel().getSelectedIndex();
            if (row==-1) return;
            Student student=tblvStudentDemo.getSelectionModel().getSelectedItem();
            txtIdStudentDemo.setText(student.getStudentId()+"");
            txtNameStudentDemo.setText(student.getStudentName());
            txtEmailStudentDemo.setText(student.getStudentEmail());
            txtAgeStudentDemo.setText(student.getStudentAge()+"");
        });
        btnAddStudentDemo.setOnAction(event -> {
            studentsDemo.add(new Student(Integer.parseInt(txtIdStudentDemo.getText()),txtNameStudentDemo.getText(),     //add newStudent nhập từ các textfield vào list
                            txtEmailStudentDemo.getText(),Integer.parseInt(txtAgeStudentDemo.getText())));  //vì dùng ObservableList nên có ràng buộc, k cần add vào list nữa
        });
        btnDeleteStudentDemo.setOnAction(event -> {
            if (tblvStudentDemo.getSelectionModel().getSelectedIndex()==-1) return;
            studentsDemo.remove(tblvStudentDemo.getSelectionModel().getSelectedItem());
        });
        btnDetailStudentDemo.setOnAction(event -> {     //truyền data sang màn hình khác
            if (tblvStudentDemo.getSelectionModel().getSelectedIndex()==-1) return;                     //nếu k chọn dòng nào trên table thì return
            FXMLLoader loader=new FXMLLoader();     //FXMLLoader để load scene sampleStudentDetails.fxml
            loader.setLocation(getClass().getResource("sampleStudentDetails.fxml"));    //load màn hình đó lên
            Parent parentStudentView= null;         //lấy parent để tạo ra scene, zống như trong class MainGUI.java
            try {
                parentStudentView = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene sceneStudentDetail=new Scene(parentStudentView);                                  //tạo ra scene, để đưa parent vào,
            ((Stage)((Node) event.getSource()).getScene().getWindow()).setScene(sceneStudentDetail);//đưa scene này vòa 1 cái stage zống như bên MainGUI.java
            ControllerStudentDetails controllerStudentDetails=loader.getController();               //chuyển scene sang nhưng chưa chuyển đc data->gọi controller này ra
            Student studentSelected=tblvStudentDemo.getSelectionModel().getSelectedItem();          //lấy đc controller detail thì gọi đc hàm detailStudent
            controllerStudentDetails.detailStudent(studentSelected);                                //và truyền student đc chọn trên table vào.
        });
    }
/**/private void addEvent_progressBarDemo_progressIndicator_Demo() {
        btnStart.setOnAction(event -> {
            taskProgressDemo=new TaskProgressBarDemo_progressIndicator_Demo();      //task này sẽ ràng buộc 3 đối tượng là lblComboboxDemo, prgrbarDemo và prgridcDemo vs taskDemo
            prgrbarDemo.progressProperty().bind(taskProgressDemo.progressProperty());//.progressProperty để mô tả tiến trình của prgrbarDemo này, .bind để ràng buộc vs taskDemo
            prgridcDemo.progressProperty().bind(taskProgressDemo.progressProperty());//taskProgressDemo.progressProperty chính là nó cập nhật tại updateProgress trong class task..
            lblReadyDemo.textProperty().bind(taskProgressDemo.messageProperty());   //còn label này sẽ bị ràng buộc vs mesage trong updateMessage của class task...
            new Thread(taskProgressDemo).start();   //sau khi xác định sự ràng buộc xong thì cho chạy task
        });
        btnCancel.setOnAction(event -> {
            if (taskProgressDemo==null) return;     //nếu chưa nhấn start thì k làm zì cả
            taskProgressDemo.cancel();              //cancel cái task này
            prgrbarDemo.progressProperty().unbind();//rồi tắt ràng buộc zữa prgrbarDemo, prgridcDemo với taskProgressDemo
            prgrbarDemo.setProgress(0);             //cho quay lại về 0%
            prgridcDemo.progressProperty().unbind();
            prgridcDemo.setProgress(0);
            lblReadyDemo.textProperty().unbind();   //tương tự cũng tắt ràng buộc của cái label này vs message trong task
            lblReadyDemo.setText("Ready");          //set label này về ready như cũ
        });
    }
    private void addEventCheckBox() {
        btnCheckDemo.setOnAction(event -> {
            String msg="Your language: ";
            if (chkJava.isSelected())
                msg+=chkJava.getText()+". ";
            if (chkCsharp.isSelected())
                msg+=chkCsharp.getText()+". ";
            if (chkPython.isSelected())
                msg+=chkPython.getText()+". ";
            ButtonType btntypeCheckOk=new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            Dialog<Pair<String,String>> dialogCheckOk=AnnounceFactory.dialogTwoButtonWithCancel("Check Language",msg,btntypeCheckOk);
            Node btnNodeCheckOk=dialogCheckOk.getDialogPane().lookupButton(btntypeCheckOk);
            if (chkJava.isSelected()==false && chkCsharp.isSelected()==false && chkPython.isSelected()==false)
                btnNodeCheckOk.setDisable(true);
            else
                btnNodeCheckOk.setDisable(false);
            FlowPane loCheckbox=new FlowPane();
            loCheckbox.setPrefSize(0,20);
            dialogCheckOk.getDialogPane().setContent(loCheckbox);
            String finalMsg = msg;
            dialogCheckOk.setResultConverter(dialogButton -> {
                if (dialogButton==btntypeCheckOk){
                    return  new Pair<>(null,finalMsg);
                }
                return null;
            });
            Optional<Pair<String,String>> result=dialogCheckOk.showAndWait();
            result.ifPresent(message->{
                System.out.println(finalMsg);
                MainGUI.window.setScene(MainGUI.scMain);
            });
//            JOptionPane.showMessageDialog(null,msg);
        });
    }
    private void addEventForMenu() {
        mnuCloseDemo.setOnAction(event -> {
            if (AnnounceFactory.alertYesNo("Close Program","Quit?","Do you want to close?","YES","NO",
                    ButtonBar.ButtonData.YES, ButtonBar.ButtonData.NO).get().getButtonData()== ButtonBar.ButtonData.YES) {
                Platform.exit();/*tắt cái plastform của java application này, tức là tắt hệ thống của java, sau đó chọn:*/
                System.exit(0);
            }
        });
    }
    private void addEventChangePictureForImageView() {
        btnImgDemo.setOnAction(event ->{
            Stage stgDemo= (Stage) loMain.getScene().getWindow();   //lấy stage chính của chương trình là border layout
            FileChooser fcChoosePictureDemo=new FileChooser();      //tạo file chooser rồi set title cũng như description và phần mở rộng cho nó
            fcChoosePictureDemo.setTitle("Choose Image");
            FileChooser.ExtensionFilter imageFilterDemo=new FileChooser.ExtensionFilter("File hinh anh","*jpg","*png"); //chỉ lấy file jpg và png
            fcChoosePictureDemo.getExtensionFilters().add(imageFilterDemo); //đưa file đc chọn vào file chooser.
            File filefcDemo=fcChoosePictureDemo.showOpenDialog(stgDemo);    //đưa stage chính của chương trình vào dialog để mỗi lần chỉ đc show 1 file chooser thôi
            if (filefcDemo==null) return;                                   //nếu k có file nào đc chọn thì returen
            Image imgDemo=new Image(filefcDemo.toURI().toString(),61,53,false,true);//false là k chỉnh tỷ lệ, true là làm mịn
            this.imgDemo.setImage(imgDemo);                                 //câu lệnh trên trả về 1 đường dẫn, sau đó đưa đường dẫn vào imageView imgeDemo
        });
    }
    private void addEventPieChart() {
        btnLoadPiechartDemo.setOnAction(event -> {
            try {   //đưa 3 zá trị của c#, java và python vào piechartData rồi mới đưa vào piechart
                PieChart.Data cSharp = new PieChart.Data("C#",Integer.parseInt(txtCsharpPiechartDemo.getText()));
                PieChart.Data java = new PieChart.Data("Java",Integer.parseInt(txtJavaPiechartDemo.getText()));
                PieChart.Data python = new PieChart.Data("Python",Integer.parseInt(txtPythonPiechartDemo.getText()));
                pieChartDemo.getData().clear();                     //nếu piechart có dữ liệu trước thì xóa nó đi, rồi mới đưa 3 data này vào piechart
                pieChartDemo.getData().addAll(cSharp,java,python);
                for (PieChart.Data data : pieChartDemo.getData()){  //bát sự kiện cho mỗi phân vùng của piechart, sự kiện này đc bắt trong btnload, chạy lần lượt 3piechart này
                    data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,e -> {     //bắt sự kiện MouseEvent, và sự kiện của nó là nhấn chuột MOUSE_CLICK -> show alert lên
                        AnnounceFactory.announcementAlert(Alert.AlertType.INFORMATION,"Data",null,(data.getName()+": "+data.getPieValue())).showAndWait();
                    });
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void fakeDataEmployeeManagement(){
        Department pHR=new Department("pHR","Human Resource Dep");
        Department pBu=new Department("pBu","Business Dep");
        Department pTech=new Department("pTech","Technology Dep");
        pHR.addEmployee(new Employee("nv1","Donald Trump",
                new Date(2000-1900, Calendar.DECEMBER,30),new Date(1999-1900,Calendar.NOVEMBER,29)));
        pHR.addEmployee(new Employee("nv2","Putin",
                new Date(2001-1900,Calendar.JANUARY,19),new Date(1998-1900,Calendar.OCTOBER,28)));
        pTech.addEmployee(new Employee("nv3","Kim Jong Un",
                new Date(2002-1900,Calendar.FEBRUARY,20),new Date(1997-1900,Calendar.SEPTEMBER,27)));
        pTech.addEmployee(new Employee("nv4","Tap Can Binh",
                new Date(2003-1900,Calendar.MARCH,21),new Date(1996-1900,Calendar.AUGUST,26)));
        pBu.addEmployee(new Employee("nv5","Obama",
                new Date(2004-1900,Calendar.APRIL,22),new Date(1995-1900,Calendar.JULY,25)));
        pBu.addEmployee(new Employee("nv6","Bush",
                new Date(2005-1900,Calendar.MAY,23),new Date(1994-1900,Calendar.JUNE,24)));
        departments=FXCollections.observableArrayList(pHR,pBu,pTech);
        cboDepartments.setItems(departments);
    }
    private void addEventEmployeeManagement() {
        cboDepartments.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (cboDepartments.getSelectionModel().getSelectedIndex()==-1) return;
            departmentSelected = cboDepartments.getSelectionModel().getSelectedItem();
            lvEmployees.setItems(departmentSelected.getEmployees());    //đưa data từ combobox lên listview
        });
        lvEmployees.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (lvEmployees.getSelectionModel().getSelectedIndex()==-1) return;
            employeeSelected =lvEmployees.getSelectionModel().getSelectedItem();
            txtCodeEmployee.setText(employeeSelected.getCodeEmployee());
            txtNameEmployee.setText(employeeSelected.getNameEmployee());
            txtDayStartWorkingEmployee.setText(sdf.format(employeeSelected.getDateStartWorking()));
            txtDayOfBirthEmployee.setText(sdf.format(employeeSelected.getDateOfBirth()));
        });
        btnSaveEmployee.setOnAction(event -> {
            for (Department department : departments){
                for(Employee employee : department.getEmployees()){
                    if (employee.getCodeEmployee().equalsIgnoreCase(txtCodeEmployee.getText()))
                        return; //check mã xem có trùng vs nhân viên khác hay k, nếu trùng thì return
                }
            }
            try {   //try-catch vì có ngày tháng năm thì có thể người ta nhập sai
                if (departmentSelected==null) return;
                departmentSelected.addEmployee(new Employee(txtCodeEmployee.getText(), txtNameEmployee.getText(),
                                                    sdf.parse(txtDayStartWorkingEmployee.getText()),sdf.parse(txtDayOfBirthEmployee.getText())));
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,ex.toString());
            }
        });
        btnDeleteEmployee.setOnAction(event -> {
            if(employeeSelected==null) return;
            departmentSelected.getEmployees().remove(employeeSelected);
            employeeSelected=null;
        });
    }

    private void addControlCustomerManagement() {
        columnCodeCustomer.setCellValueFactory(new PropertyValueFactory<CustomerDb,String>("codeCustomer"));
        columnNameCustomer.setCellValueFactory(new PropertyValueFactory<CustomerDb,String>("nameCustomer"));
        columnPhoneCustomer.setCellValueFactory(new PropertyValueFactory<CustomerDb,String>("phoneCustomer"));
        columnEmailCustomer.setCellValueFactory(new PropertyValueFactory<CustomerDb,String>("emailCustomer"));
    }
    private void addEventCustomerManagement() {
        tvCustomerGroup.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (tvCustomerGroup.getSelectionModel().getSelectedIndex()==-1) return; //nếu chưa chọn node nào trên tree thì return
            if(newValue.getParent().getValue()==null){                              //lấy nhánh customergroup (1 lần getparent)
                customerSelected=null;
                customerGroupSelected = (CustomerGroupDb) newValue.getValue();                                    //lấy nhánh cha thì đó chính là customerGroup
                if (customerGroupServices.checkCodeCustomerGroup(customerGroupSelected.getCodeCustomerGroup())) {
                    txtCodeCustomerGroup.setText(customerGroupSelected.getCodeCustomerGroup());
                    txtNameCustomerGroup.setText(customerGroupSelected.getNameCustomerGroup());
                }
                customers=customerServices.readAllCustomerInGroup(customerGroupSelected.getCodeCustomerGroup());//đồng thời đọc từ sql các customer từ customerGroup này.
                tblCustomers.setItems(customers);                                                               //đọc xong thì đưa hết lên tableview
            }
            else if(newValue.getParent().getParent().getValue()==null){             //lấy nhánh customer (2 lần getparent)
                customerSelected= (CustomerDb) newValue.getValue();                   //lấy nhánh con thì đó chính là customer
                customerGroupSelected=customerSelected.getGroupCustomer();          //lấy đc customer thì sẽ lấy đc customerGroup vì customer này thuộc 1 group nào đó
                customers=customerServices.readAllCustomerInGroup(customerGroupSelected.getCodeCustomerGroup());//đồng thời đọc từ sql các customer từ customerGroup này.
                tblCustomers.setItems(customers);                                                               //đọc xong thì đưa hết lên tableview
                if (customerServices.checkCodeCustomer(customerSelected.getCodeCustomer())) {   //nếu customer lấy trên treeview có trong database -> thì hiển thị lên
                    txtCodeCustomer.setText(customerSelected.getCodeCustomer());                //và settext cho từng textfield tương ứng
                    txtNameCustomer.setText(customerSelected.getNameCustomer());
                    txtPhoneCustomer.setText(customerSelected.getPhoneCustomer());
                    txtEmailCustomer.setText(customerSelected.getEmailCustomer());
                }
                if (customerGroupServices.checkCodeCustomerGroup(customerGroupSelected.getCodeCustomerGroup())) {
                    txtCodeCustomerGroup.setText(customerGroupSelected.getCodeCustomerGroup());
                    txtNameCustomerGroup.setText(customerGroupSelected.getNameCustomerGroup());
                }
            }
        });
        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int row=tblCustomers.getSelectionModel().getSelectedIndex();        //nếu chưa chọn vào tableview thì return, ko làm zì cả
            if (row==-1) return;
            customerSelected=tblCustomers.getSelectionModel().getSelectedItem();//nếu chọn rồi thì lấy customer đó ra, và show details lên textfield
            customerGroupSelected=customerSelected.getGroupCustomer();          //lấy đc customer thì sẽ lấy đc customerGroup
            if (customerServices.checkCodeCustomer(customerSelected.getCodeCustomer())) {   //nếu customer lấy trên table có trong database -> thì hiển thị lên
                txtCodeCustomer.setText(customerSelected.getCodeCustomer());
                txtNameCustomer.setText(customerSelected.getNameCustomer());
                txtPhoneCustomer.setText(customerSelected.getPhoneCustomer());
                txtEmailCustomer.setText(customerSelected.getEmailCustomer());
            }
        });

        btnSaveCustomer.setOnAction(event -> {
            if (customerGroupSelected==null) return;                                                           //nếu chưa có group nào đc chọn thì return
//            for(CustomerGroup customerGroup : customerGroups){
//                for (Customer customer : customerGroup.getCustomers()){     //nếu trùng mã hoặc gõ toàn ký tự khoảng trắng thì k thêm
//                    if(customer.getCodeCustomer().trim().equalsIgnoreCase("")) {
//                        JOptionPane.showMessageDialog(null, "Saving failed!");    //thông báo nếu save failed
//                        return;
//                    }
//                    else if(customer.getCodeCustomer().equalsIgnoreCase(txtCodeCustomer.getText())){        //nếu mã trùng thì sửa data
//                        JOptionPane.showMessageDialog(null, "Update success!");   //thông báo nếu update thành công
//                    }
//                }
//            }
            if (txtCodeCustomer.getText().trim().indexOf("cus")!=0 || txtCodeCustomer.getText().trim().length()==0) return; //ký tự đầu phải là cus và k đc gõ toàn khoảng trắng
            CustomerDb newCustomer = new CustomerDb(txtCodeCustomer.getText().trim(),txtNameCustomer.getText(),txtPhoneCustomer.getText(),txtEmailCustomer.getText());
            if(customerServices.saveCustomer(newCustomer,customerGroupSelected)>0) {
                customerGroupSelected.addCustomer(newCustomer);                                                //đưa customer đc tạo mới vào customerGroup đang chọn
                JOptionPane.showMessageDialog(null, "saving success!");              //thông báo nếu insert thành công
            }
            else {
                newCustomer=null;
                JOptionPane.showMessageDialog(null, "Saving failed!");               //thông báo nếu insert failed
            }
            customers=customerServices.readAllCustomerInGroup(customerGroupSelected.getCodeCustomerGroup());//đồng thời đọc từ sql các customer từ customerGroup này.
            tblCustomers.setItems(customers);                                                               //đưa nhóm customergroup vào table
            displayTreeViewCustomerGroup();                                                                 //đưa vào table rồi thì reset lại treevies
        });
        btnDeleteCustomer.setOnAction(event -> {
            if (txtCodeCustomer.getText().trim().length()==0) return;
            Optional<ButtonType> ret= AnnounceFactory.alertYesNo("Confirm to delete","Delete a Customer",
                    "Do you want to delete "+txtNameCustomer.getText(),"YES","NO", ButtonBar.ButtonData.YES, ButtonBar.ButtonData.NO);
            if(ret.get().getButtonData()==ButtonBar.ButtonData.NO) return;
            if (customerServices.deleteCustomer(txtCodeCustomer.getText())>0)
                JOptionPane.showMessageDialog(null, "delete success!");           //thông báo nếu delete thành công
            else
                JOptionPane.showMessageDialog(null, "delete fail!");              //thông báo nếu delete fail
            customers=customerServices.readAllCustomerInGroup(customerGroupSelected.getCodeCustomerGroup());//đồng thời đọc từ sql các customer từ customerGroup này.
            tblCustomers.setItems(customers);                                                               //đọc xong thì đưa hết lên tableview
            displayTreeViewCustomerGroup();                                                                 //đưa vào table rồi thì reset lại treevies
        });

        btnSaveCustomerGroup.setOnAction(event -> {
            if (txtCodeCustomerGroup.getText().trim().length()==0) return; //ký tự k đc gõ toàn khoảng trắng
            CustomerGroupDb newCustomerGroup=new CustomerGroupDb(txtCodeCustomerGroup.getText().trim(),txtNameCustomerGroup.getText());
            if (customerGroupServices.saveCustomerGroup(newCustomerGroup)>0){
                customerGroups.add(newCustomerGroup);
                JOptionPane.showMessageDialog(null, "saving success!");              //thông báo nếu insert thành công
            }
            else {
                newCustomerGroup=null;
                JOptionPane.showMessageDialog(null, "Saving failed!");               //thông báo nếu insert failed
            }
//            customers=customerServices.readAllCustomerInGroup(customerGroupSelected.getCodeCustomerGroup());//đồng thời đọc từ sql các customer từ customerGroup này.
//            tblCustomers.setItems(customers);                                                               //đưa nhóm customergroup vào table
            displayTreeViewCustomerGroup();                                                                 //đưa vào table rồi thì reset lại treevies
        });
        btnDeleteCustomerGroup.setOnAction(event -> {
            if (txtCodeCustomerGroup.getText().trim().length()==0) return;
            Optional<ButtonType> ret= AnnounceFactory.alertYesNo("Confirm to delete","Delete a Customer Group (You will delete all customer in this group)",
                    "Do you want to delete "+txtNameCustomerGroup.getText(),"YES","NO", ButtonBar.ButtonData.YES, ButtonBar.ButtonData.NO);
            if(ret.get().getButtonData()==ButtonBar.ButtonData.NO) return;
            if (customerGroupServices.deleteCustomerGroup(txtCodeCustomerGroup.getText())>0)
                JOptionPane.showMessageDialog(null, "delete success!");           //thông báo nếu delete thành công
            else
                JOptionPane.showMessageDialog(null, "delete fail!");              //thông báo nếu delete fail
            displayTreeViewCustomerGroup();                                                                 //đưa vào table rồi thì reset lại treevies
        });

        btnLastCustomer.setOnAction(event -> {
            try {
                SQLCustomerServices.results.last();
                txtCodeCustomer.setText(SQLCustomerServices.results.getString(1));
                txtNameCustomer.setText(SQLCustomerServices.results.getString(2));
                txtPhoneCustomer.setText(SQLCustomerServices.results.getString(3));
                txtEmailCustomer.setText(SQLCustomerServices.results.getString(4));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });     //đi về cuối list trong table, cho phép di chuyển vì ResultSet có TYPE_SCROLL_INSENSITIVE (xem SQLCustomerServices)
        btnFirstCustomer.setOnAction(event -> {
            try {
                SQLCustomerServices.results.first();
                txtCodeCustomer.setText(SQLCustomerServices.results.getString(1));
                txtNameCustomer.setText(SQLCustomerServices.results.getString(2));
                txtPhoneCustomer.setText(SQLCustomerServices.results.getString(3));
                txtEmailCustomer.setText(SQLCustomerServices.results.getString(4));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });    //đi về đầu list trong table
        btnNextCustomer.setOnAction(event -> {
            try {
                if(SQLCustomerServices.results.next()==false)       //nếu đang là mục cuối cùng trên table mà nhấn vào next thì sẽ nhảy đến mục đầu tiên (chạy vòng)
                    SQLCustomerServices.results.first();
                txtCodeCustomer.setText(SQLCustomerServices.results.getString(1));
                txtNameCustomer.setText(SQLCustomerServices.results.getString(2));
                txtPhoneCustomer.setText(SQLCustomerServices.results.getString(3));
                txtEmailCustomer.setText(SQLCustomerServices.results.getString(4));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });     //đi đến customer kế trong table
        btnPreviousCustomer.setOnAction(event -> {
            try {
                if(SQLCustomerServices.results.previous()==false)   //nếu đang là mục đầu tiên trên table mà nhấn vào previous thì sẽ nhảy đến mục cuối cùng (chạy vòng)
                    SQLCustomerServices.results.last();
                txtCodeCustomer.setText(SQLCustomerServices.results.getString(1));
                txtNameCustomer.setText(SQLCustomerServices.results.getString(2));
                txtPhoneCustomer.setText(SQLCustomerServices.results.getString(3));
                txtEmailCustomer.setText(SQLCustomerServices.results.getString(4));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }); //đi đến customer trước đó trong table

    }
    private void fakeDataCustomerManagement() {
//        CustomerGroup vip = new CustomerGroup("vip","VIP Customer");
//        CustomerGroup potential=new CustomerGroup("pot", "Potential Customer");
//        CustomerGroup grumpy=new CustomerGroup("gru","Grumpy Customer");
//        vip.addCustomer(new Customer("cus01","Donald Trump","099888999","trumpusa@gmail.com"));
//        vip.addCustomer(new Customer("cus02","Putin","098777888","putinrussia@gmail.com"));
//        grumpy.addCustomer(new Customer("cus05","Tap Can Binh","097666777","binhchina@gmail.com"));
//        grumpy.addCustomer(new Customer("cus06","Kim Jong Un","095444555","kimnorthkorea@gmail.com"));
//        potential.addCustomer(new Customer("cus04","Obama","092333444","obamausa@gmail.com"));
//        potential.addCustomer(new Customer("cus03","Joe Biden","095666666","joebidenusa@gmail.com"));
//        customerGroups=FXCollections.observableArrayList(vip,potential,grumpy);
        displayTreeViewCustomerGroup();
    }
    private void displayTreeViewCustomerGroup() {
        customerGroups=customerGroupServices.readAllCustomerGroup();
        tvCustomerRoot=new TreeItem<>();
        for (CustomerGroupDb customerGroup : customerGroups){
            TreeItem<Object> nodeGroup = new TreeItem<>(customerGroup);
            customers=customerServices.readAllCustomerInGroup(customerGroup.getCodeCustomerGroup());   //đọc hết customer trong 1 group từ sql lên
            for (CustomerDb customer : customers){        //sau đó đưa lên tree và add từng customer vào customerGroup
                customerGroup.addCustomer(customer);    //add vào customerGroup
                nodeGroup.getChildren().add((new TreeItem<>(customer)));    //đưa từng nhánh customer lên tree
            }
            nodeGroup.setExpanded(true);            //xổ ra hết các nhánh của tree
            tvCustomerRoot.getChildren().add(nodeGroup);//đưa từng nhánh customerGroup lên tree
        }
        tvCustomerRoot.setExpanded(true);           //xổ ra hết các nhánh của tree
        tvCustomerGroup.setRoot(tvCustomerRoot);    //đưa root vào tree
        tvCustomerGroup.setShowRoot(false);         //ko hiển thị root lên tree
    }

    private void addControlCustomerManagementHibernate() {
        daoCustomerGroup=new DAOCustomerGroup();
        customerGroupsHibernate=daoCustomerGroup.getAll();
        lvCustomerGroupHibernate.setItems(customerGroupsHibernate);
//        DAOCustomer daoCustomer=new DAOCustomer();
//        customersHibernate=daoCustomer.getAll();
//        tblCustomersHibernate.setItems(customersHibernate);
        columnCodeCustomerHibernate = (TableColumn<Customer, String>) tblCustomersHibernate.getColumns().get(0);
        columnNameCustomerHibernate = (TableColumn<Customer, String>) tblCustomersHibernate.getColumns().get(1);
        columnPhoneCustomerHibernate = (TableColumn<Customer, String>) tblCustomersHibernate.getColumns().get(2);
        columnEmailCustomerHibernate = (TableColumn<Customer, String>) tblCustomersHibernate.getColumns().get(3);

        columnCodeCustomerHibernate.setCellValueFactory(new PropertyValueFactory<Customer,String>("codeCustomer"));
        columnNameCustomerHibernate.setCellValueFactory(new PropertyValueFactory<Customer,String>("nameCustomer"));
        columnPhoneCustomerHibernate.setCellValueFactory(new PropertyValueFactory<Customer,String>("phoneCustomer"));
        columnEmailCustomerHibernate.setCellValueFactory(new PropertyValueFactory<Customer,String>("emailCustomer"));
    }
    private void addEventCustomerManagementHibernate() {
        lvCustomerGroupHibernate.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newvalue) -> {
            if (lvCustomerGroupHibernate.getSelectionModel().getSelectedIndex()==-1) return;
            customerGroupHibernateSelected=lvCustomerGroupHibernate.getSelectionModel().getSelectedItem();
            customerHibernateSelected=null;
            txtCodeCustomerGroupHibernate.setText(customerGroupHibernateSelected.getCodeGroup());
            txtNameCustomerGroupHibernate.setText(customerGroupHibernateSelected.getNameGroup());
            refreshViewCustomerManagementHibernate(customerGroupHibernateSelected);
        });
        tblCustomersHibernate.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int row=tblCustomersHibernate.getSelectionModel().getSelectedIndex();                   //nếu chưa chọn vào tableview thì return, ko làm zì cả
            if (row==-1) return;
            customerHibernateSelected=tblCustomersHibernate.getSelectionModel().getSelectedItem();  //nếu chọn rồi thì lấy customer đó ra, và show details lên textfield
            customerGroupHibernateSelected=customerHibernateSelected.getCustomerGroupByCodeGroup(); //lấy đc customer thì sẽ lấy đc customerGroup
            txtCodeCustomerHibernate.setText(customerHibernateSelected.getCodeCustomer());
            txtNameCustomerHibernate.setText(customerHibernateSelected.getNameCustomer());
            txtPhoneCustomerHibernate.setText(customerHibernateSelected.getPhoneCustomer());
            txtEmailCustomerHibernate.setText(customerHibernateSelected.getEmailCustomer());
        });
        btnSaveCustomerHibernate.setOnAction(actionEvent -> {
            if (customerGroupHibernateSelected==null) return;
            if (txtCodeCustomerHibernate.getText().trim().indexOf("cus")!=0 || txtCodeCustomerHibernate.getText().trim().length()==0) return; //tương tự như k có hibernate
            daoCustomer=new DAOCustomer();
            Customer newCustomer=new Customer(txtCodeCustomerHibernate.getText().trim(),txtNameCustomerHibernate.getText().trim(),txtPhoneCustomerHibernate.getText().trim(),
                                                txtEmailCustomerHibernate.getText().trim(),0,customerGroupHibernateSelected);
            if(daoCustomer.saveData(newCustomer)==0)
                JOptionPane.showMessageDialog(null, "saving success");
            else
                JOptionPane.showMessageDialog(null, "saving fail");
        });
        btnDeleteCustomerHibernate.setOnAction(event -> {
            if (customerHibernateSelected==null) return;
            Optional<ButtonType> ret= AnnounceFactory.alertYesNo("Confirm to delete","Delete a Customer",
                    "Do you want to delete "+customerHibernateSelected.getNameCustomer(),"YES",
                        "NO", ButtonBar.ButtonData.YES, ButtonBar.ButtonData.NO);
            if(ret.get().getButtonData()==ButtonBar.ButtonData.NO) return;
            daoCustomer.deleteData(customerHibernateSelected);
        });
    }
    private void refreshViewCustomerManagementHibernate(CustomerGroup customerGroup){
//        daoCustomerGroup=new DAOCustomerGroup();
        customerGroupsHibernate=daoCustomerGroup.getAll();
        lvCustomerGroupHibernate.setItems(customerGroupsHibernate);

        daoCustomer=new DAOCustomer();
        customersHibernate=daoCustomer.getAll(customerGroup);
        tblCustomersHibernate.setItems(customersHibernate);

        tblCustomersHibernate.refresh();
        lvCustomerGroupHibernate.refresh();
    }
}