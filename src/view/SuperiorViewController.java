package view;

import java.io.IOException;

import controller.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.DrivingShift;
import model.Employee;
import model.Vehicle;
import util.Strings;
import javafx.scene.control.TableView;

/**
 * Split SuperiorView xml file into different tabs
 * @author Nekuin
 *
 */
public class SuperiorViewController implements ViewModule {

	@FXML
    private TableView<DrivingShift> shiftTableView;

    @FXML
    private Text shiftClientText;

    @FXML
    private Text shiftIdText;

    @FXML
    private Text shiftCargoText;

    @FXML
    private Text shiftDeadlineText;

    @FXML
    private Button shiftAddButton;

    @FXML
    private Button shiftUpdateButton;

    @FXML
    private Button shiftDeleteButton;

    @FXML
    private TableView<Employee> empTableView;

    @FXML
    private Text empNameText;

    @FXML
    private Text empIdText;

    @FXML
    private HBox empLicenseLabelHolder;

    @FXML
    private Text empLicenseText;

    @FXML
    private Button addEmpButton;

    @FXML
    private Button updateEmpButton;

    @FXML
    private Button deleteEmpButton;

    @FXML
    private TableView<Vehicle> vehicleTableView;

    @FXML
    private Text brandText;

    @FXML
    private Text modelText;

    @FXML
    private Text regPlateText;
    
    @FXML
    private Text drivenText;

    @FXML
    private Text cargoText;

    @FXML
    private Text maintainedText;
    
    @FXML
    private Button addVehicleButton;

    @FXML
    private Button updateVehicleButton;

    @FXML
    private Button deleteVehicleButton;
    
    private IController controller;
    private Strings strings;
    private BorderPane root;
    
    public SuperiorViewController(IController controller) {
    	this.controller = controller;
    	strings = Strings.getInstance();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/SuperiorView.fxml"), strings.getBundle());
    	loader.setController(this);
    	try {
    		loader.load();
    		root = loader.getRoot();
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	
    }
    
    @FXML
    private void initialize() {
    	//create the different tabs here
    	//pass all employee related elements to the Employee tab handler
    	new SuperiorEmployeeTab(controller, empNameText, empIdText,
    			empLicenseLabelHolder, empLicenseText, addEmpButton,
    			updateEmpButton, deleteEmpButton, empTableView);
    	//pass all Vehicle related elements to the Vehicle tab handler
    	new SuperiorVehicleTab(controller, brandText, modelText, regPlateText,
    			drivenText, cargoText, maintainedText, addVehicleButton,
    			updateVehicleButton, deleteVehicleButton, vehicleTableView);
    	//pass all DrivingShift related elements to the DrivingShift tab handler
    	new SuperiorDrivingShiftTab(controller, shiftClientText, shiftIdText,
    			shiftCargoText, shiftDeadlineText, shiftAddButton,
    			shiftUpdateButton, shiftDeleteButton, shiftTableView);

    }

	@Override
	public BorderPane getView() {
		return root;
	}

}
