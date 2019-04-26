package view;

import java.io.IOException;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Driver;
import model.Employee;
import model.HrManager;
import model.Superior;
import util.ErrorTooltip;
import util.Strings;

/**
 * Modal for adding or editing an Employee
 * @author Nekuin
 *
 */
public class EmployeeModal implements ViewModule {
	
	@FXML
    private Text titleText;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private VBox licenseHolder;

    @FXML
    private Label licenseLabel;

    @FXML
    private TextField licenseTextField;

    @FXML
    private CheckBox hazardousCheckBox;
    
    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;
    
    private Strings strings;
    private BorderPane root;
    private Employee employee;
    private IController controller;
    private SubmitObserver observer;
    private final String DRIVER = "Driver", HR = "HR", SUPERIOR = "Superior";
	
    /**
     * Add constructor
     * @param controller
     */
	private EmployeeModal(IController controller, SubmitObserver observer) {
		strings = Strings.getInstance();
		this.controller = controller;
		this.observer = observer;
		loadXML();
		setTitle(strings.getString("add_employee_title"));
		//hide driver related stuff by default
		licenseHolder.setOpacity(0);
	}
	
	/**
	 * Edit constructor
	 * @param controller
	 * @param employee
	 */
	private EmployeeModal(IController controller, Employee employee, SubmitObserver observer) {
		strings = Strings.getInstance();
		this.controller = controller;
		this.employee = employee;
		this.observer = observer;
		loadXML();
		setTitle(strings.getString("edit_employee_title"));
		prefillFields(employee);
		// disabled roleComboBox, you cannot change roles
		roleComboBox.setDisable(true);
	}
	
	/**
	 * Loads EmployeeModal FXML file
	 */
	private void loadXML() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/EmployeeModal.fxml"), strings.getBundle());
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
		//create listeners for Buttons
		submitButton.setOnAction(e -> {
			submitAction(e);
		});
		
		cancelButton.setOnAction(e -> {
			((Node)e.getSource()).getScene().getWindow().hide();
		});
		populateRoleBox();
		submitButton.setDefaultButton(true);
	}
	
	/**
	 * SubmitButton onAction
	 * @param e SubmitButton ActionEvent
	 */
	private void submitAction(ActionEvent e) {
		//validate
		if(validateFields()) {
			// if employee is null we're in the Add modal
			if(employee == null) {
				String selectedRole = roleComboBox.getSelectionModel().getSelectedItem();
				if(selectedRole.equals(DRIVER)) {
					controller.createDriver(new Driver(nameTextField.getText(), licenseTextField.getText(), hazardousCheckBox.isSelected()));
				} else if(selectedRole.equals(HR)) {
					controller.createHrManager(new HrManager(nameTextField.getText()));
				} else if(selectedRole.equals(SUPERIOR)) {
					controller.createSuperior(new Superior(nameTextField.getText()));
				}
				
				
			} else {
				employee.setName(nameTextField.getText());
				if(employee instanceof Driver) {
					((Driver)employee).setDriversLicense(licenseTextField.getText());
					((Driver)employee).setCanDriveHazardous(hazardousCheckBox.isSelected());
				}
				controller.updateEmployee(employee);
			}
			//notify observer
			observer.notifyListener();
			//close modal
			((Node)e.getSource()).getScene().getWindow().hide();
		}
	}
	
	/**
	 * Validate that the TextFields actually have some text in them
	 * @param e ActionEvent
	 * @return boolean
	 */
	private boolean validateFields() {
		String name = nameTextField.getText();
		if(name.length() == 0) {
			ErrorTooltip.showErrorTooltip(submitButton, strings.getString("sup_emp_noname_err"));
			return false;
		}
		if(roleComboBox.getSelectionModel().getSelectedItem().equals(DRIVER)) {
			String license = licenseTextField.getText();
			if(license.length() == 0) {
				ErrorTooltip.showErrorTooltip(submitButton, strings.getString("sup_emp_nolicense_err"));
				return false;
			}
		}
		//return true if all tests passed
		return true;
	}
	
	/**
	 * Populates role box with Driver, HR and Superior roles
	 */
	private void populateRoleBox() {
		//put roles into combo box -  Driver, HR and Superior
		ObservableList<String> roles = FXCollections.observableArrayList();
		roles.addAll(DRIVER, HR, SUPERIOR);
		roleComboBox.setItems(roles);
		//hide or show Driver related options based on the selection
		roleComboBox.setOnAction(e -> {
			if(roleComboBox.getSelectionModel().getSelectedItem().equals(DRIVER)) {
				licenseHolder.setOpacity(1);
			} else {
				licenseHolder.setOpacity(0);
			}
		});
	}
	
	/**
	 * Fill out the fields with the Employees information if this is Edit modal
	 * @param emp
	 */
	private void prefillFields(Employee emp) {
		nameTextField.setText(emp.getName());
		if(emp instanceof Driver) {
			licenseTextField.setText(((Driver)emp).getDriversLicense());
			hazardousCheckBox.selectedProperty().set(((Driver)emp).canDriveHazardous());
			roleComboBox.getSelectionModel().select(0);
		} else if(emp instanceof HrManager) {
			// hide driver related options
			licenseHolder.setOpacity(0);
			roleComboBox.getSelectionModel().select(1);
		} else if(emp instanceof Superior) {
			// hide driver related options
			licenseHolder.setOpacity(0);
			roleComboBox.getSelectionModel().select(2);
		}
		
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
	 * @return
	 */
	public static EmployeeModal createAddEmployeeModal(IController controller, SubmitObserver observer) {
		return new EmployeeModal(controller, observer);
	}
	
	/**
	 * Returns the Edit version of this modal
	 * @param controller
	 * @param employee
	 * @return
	 */
	public static EmployeeModal createEditEmployeeModal(IController controller, Employee employee, SubmitObserver observer) {
		return new EmployeeModal(controller, employee, observer);
	}

	@Override
	public BorderPane getView() {
		return root;
	}
}
