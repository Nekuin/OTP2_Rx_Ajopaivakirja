package view;

import javafx.scene.layout.BorderPane;

import java.io.IOException;
import controller.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Vehicle;
import util.ErrorTooltip;
import util.Strings;

/**
 * Modal for adding or editing Vehicles
 * @author Nekuin
 *
 */
public class VehicleModal implements ViewModule {

	@FXML
    private Text titleText;

    @FXML
    private Label brandLabel;

    @FXML
    private TextField brandTextField;

    @FXML
    private Label modelLabel;

    @FXML
    private TextField modelTextField;

    @FXML
    private Label regLabel;

    @FXML
    private TextField regTextField;

    @FXML
    private Label cargoLabel;

    @FXML
    private TextField cargoTextField;

    @FXML
    private CheckBox maintainedCheckBox;
    
    @FXML
    private Label distanceLabel;

    @FXML
    private TextField distanceTextField;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;
    
    private IController controller;
    private Strings strings;
    private SubmitObserver observer;
    private BorderPane root;
    private Vehicle vehicle;
    
    /**
     * Constructor for Add version
     * @param controller
     * @param observer
     */
    private VehicleModal(IController controller, SubmitObserver observer) {
    	this.controller = controller;
    	strings = Strings.getInstance();
    	this.observer = observer;
    	loadXML();
    	setTitle(strings.getString("add_vehicle_text"));
    }
    
    /**
     * Constructor for Edit version
     * @param controller
     * @param observer
     * @param vehicle
     */
    private VehicleModal(IController controller, SubmitObserver observer, Vehicle vehicle) {
    	this.controller = controller;
    	strings = Strings.getInstance();
    	this.observer = observer;
    	this.vehicle = vehicle;
    	loadXML();
    	prefillFields(vehicle);
    	setTitle(strings.getString("upd_vehicle_text"));
    }
    
    private void loadXML() {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/VehicleModal.fxml"), strings.getBundle());
    	loader.setController(this);
    	try {
			loader.load();
			root = loader.getRoot();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void initialize() {
    	submitButton.setOnAction(e -> {
    		submitAction(e);
    	});
    	
    	cancelButton.setOnAction(e -> {
    		((Node)e.getSource()).getScene().getWindow().hide();
    	});
    	
    	submitButton.setDefaultButton(true);
    }
    
    private void submitAction(ActionEvent e) {
    	String reg = regTextField.getText();
    	String model = modelTextField.getText();
    	String brand = brandTextField.getText();
    	String distance = distanceTextField.getText();
    	String cargo = cargoTextField.getText();
    	if(validateFields(reg, model, brand, distance, cargo)) {
			double drivenDistance = Double.parseDouble(distanceTextField.getText());
			int maxCargoLoad = Integer.parseInt(cargoTextField.getText());
			
    		//if vehicle is null we're in the Add modal
    		if(vehicle == null) {
    			controller.createVehicle(new Vehicle(reg, drivenDistance,
    					maxCargoLoad, model, brand, maintainedCheckBox.isSelected()));
    		} else {
    			vehicle.setRegNr(reg);
    			vehicle.setDrivenDistance(drivenDistance);
    			vehicle.setMaxCargoLoad(maxCargoLoad);
    			vehicle.setModel(model);
    			vehicle.setBrand(brand);
    			vehicle.setMaintained(maintainedCheckBox.isSelected());
    			controller.updateVehicle(vehicle);
    		}
    		
    		//notify observer
    		observer.notifyListener();
    		//close modal
    		((Node)e.getSource()).getScene().getWindow().hide();
    	}
    }
    
    private boolean parseDistanceField() {
    	try {
    		Double.parseDouble(distanceTextField.getText());
    	} catch (NumberFormatException ex) {
    		return false;
    	}
    	return true;
    }
    
    private boolean parseCargoField() {
    	try {
    		Integer.parseInt(cargoTextField.getText());
    	} catch(NumberFormatException ex) {
    		return false;
    	}
    	
    	return true;
    }
    
    private boolean validateFields(String reg, String model, String brand, String distance, String cargo) {
    	//TODO: replace [ph] texts
    	if(brand.length() == 0) {
    		ErrorTooltip.showErrorTooltip(brandTextField, strings.getString("sup_veh_brand_err"));
    		return false;
    	}
    	if(model.length() == 0) {
    		ErrorTooltip.showErrorTooltip(modelTextField, strings.getString("sup_veh_model_err"));
    		return false;
    	}
    	if(reg.length() == 0) {
    		ErrorTooltip.showErrorTooltip(regTextField, strings.getString("sup_veh_reg_err"));
    		return false;
    	}
    	if(distance.length() == 0) {
    		ErrorTooltip.showErrorTooltip(distanceTextField, strings.getString("sup_veh_distance_err"));
    		return false;
    	}
    	if(cargo.length() == 0) {
    		ErrorTooltip.showErrorTooltip(cargoTextField, strings.getString("sup_veh_cargo_err"));
    		return false;
    	}
    	boolean cargoParse = parseCargoField();
		if(cargoParse == false) {
			ErrorTooltip.showErrorTooltip(cargoTextField, strings.getString("sup_veh_number_err"));
			return false;
		}
    	boolean drivenParse = parseDistanceField();
    	if(drivenParse == false) {
    		ErrorTooltip.showErrorTooltip(distanceTextField, strings.getString("sup_veh_number_err"));
    		return false;
    	}
		
    	return true;
    }
    
    /**
     * Fill out fields with the Vehicle information if this is Edit modal
     * @param vehicle
     */
    private void prefillFields(Vehicle vehicle) {
    	brandTextField.setText(vehicle.getBrand());
    	modelTextField.setText(vehicle.getModel());
    	cargoTextField.setText("" + vehicle.getMaxCargoLoad());
    	regTextField.setText(vehicle.getRegNr());
    	distanceTextField.setText("" + vehicle.getDrivenDistance());
    	maintainedCheckBox.selectedProperty().set(vehicle.getMaintained());
    }
    
    /**
     * Set title text
     * @param title
     */
    private void setTitle(String title) {
    	titleText.setText(title);
    }
    
    /**
     * Returns the Add version of this modal
     * @param controller
     * @param observer
     * @return
     */
    public static VehicleModal createAddVehicleModal(IController controller, SubmitObserver observer) {
    	return new VehicleModal(controller, observer);
    }
    
    /**
     * Returns the Edit version of this modal
     * @param controller
     * @param observer
     * @param vehicle
     * @return
     */
    public static VehicleModal createEditVehicleModal(IController controller, SubmitObserver observer, Vehicle vehicle) {
    	return new VehicleModal(controller, observer, vehicle);
    }
	
	@Override
	public BorderPane getView() {
		return root;
	}

}
