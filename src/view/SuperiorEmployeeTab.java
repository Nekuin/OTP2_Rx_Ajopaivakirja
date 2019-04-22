package view;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Driver;
import model.Employee;
import util.Strings;

/**
 * Handles SuperiorView Employee Tab
 * @author Nekuin
 *
 */
public class SuperiorEmployeeTab implements UndoObserver, SubmitObserver {
	
	private IController controller;
	private Text[] empInfoTexts;
	private HBox empLicenseHolder;
	private TableView<Employee> empTableView;
	private ObservableList<Employee> employees;

	/**
	 * Constructor
	 * @param controller
	 * @param empNameText
	 * @param empIdText
	 * @param empLicenseLabelHolder
	 * @param empLicenseText
	 * @param addEmpButton
	 * @param updateEmpButton
	 * @param deleteEmpButton
	 * @param empTableView
	 */
	public SuperiorEmployeeTab(IController controller, Text empNameText, Text empIdText, HBox empLicenseLabelHolder,
			Text empLicenseText, Button addEmpButton, Button updateEmpButton, Button deleteEmpButton, TableView<Employee> empTableView) {
		
		this.controller = controller;
		//put all info texts into an array
		empInfoTexts = new Text[] {empNameText, empIdText, empLicenseText};
		this.empLicenseHolder = empLicenseLabelHolder;
		this.empTableView = empTableView;
		//create listeners for buttons
		createButtonListeners(addEmpButton, updateEmpButton, deleteEmpButton);
		//setup table columns
		setupColumns();
		//clear PH text from columns
		updateInfoTexts("", "");
	}
	
	/**
	 * Create listeners for add update and delete buttons
	 * @param addEmpButton Button
	 * @param updateEmpButton Button
	 * @param deleteEmpButton Button
	 */
	private void createButtonListeners(Button addEmpButton, Button updateEmpButton, Button deleteEmpButton) {
		addEmpButton.setOnAction(e -> {
			addEmployee();
		});
		
		updateEmpButton.setOnAction(e -> {
			updateEmployee();
		});
		
		deleteEmpButton.setOnAction(e -> {
			deleteEmployee();
		});
	}
	
	/**
	 * Add button action
	 */
	private void addEmployee() {
		Stage stage = new Stage();
		stage.setScene(new Scene(EmployeeModal.createAddEmployeeModal(controller, this).getView()));
		stage.setTitle(Strings.getInstance().getString("add_employee_title"));
		stage.show();
	}
	
	/**
	 * Update button action
	 */
	private void updateEmployee() {
		Employee clicked = empTableView.getSelectionModel().getSelectedItem();
		if(clicked != null) {
			Stage stage = new Stage();
			stage.setScene(new Scene(EmployeeModal.createEditEmployeeModal(controller, clicked, this).getView()));
			stage.setTitle(Strings.getInstance().getString("add_employee_title"));
			stage.show();
		}
	}
	
	/**
	 * Delete button action
	 */
	private void deleteEmployee() {
		Employee clicked = empTableView.getSelectionModel().getSelectedItem();
		if(clicked != null) {
			System.out.println("remove: " + clicked);
			employees.remove(clicked);
			//create and show Undo popup for the user
			new UndoPopup(controller, clicked, this).showMessage();
		}
	}
	
	/**
	 * Setup and populate TableView columns
	 * also sets onClick listener for the items
	 */
	private void setupColumns() {
		TableColumn<Employee, ?> nameColumn = empTableView.getColumns().get(0);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Employee, ?> idColumn = empTableView.getColumns().get(1);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
		
		employees = FXCollections.observableArrayList();
		employees.addAll(controller.readAllEmployees());
		empTableView.setItems(employees);
		empTableView.setOnMouseClicked(e -> {
			Employee clicked = empTableView.getSelectionModel().getSelectedItem();
			if(clicked != null) {
				if(clicked instanceof Driver) {
					updateInfoTexts(clicked.getName(), "" + clicked.getEmployeeID(), ((Driver)clicked).getDriversLicense());
				} else {
					updateInfoTexts(clicked.getName(), "" + clicked.getEmployeeID());
				}
				
			}
		});
	}
	
	/**
	 * Updates Employee info panel texts
	 * String in index 0 will update empNameText, index 1 empIdText
	 * and index 2 empLicenseText
	 * also sets opacity for empLicenseHolder based on how many Strings were passed.
	 * @param strings
	 */
	private void updateInfoTexts(String...strings) {
		int i = 0;
		for(String s : strings) {
			empInfoTexts[i].setText(s);
			i++;
		}
		if(strings.length == empInfoTexts.length) {
			empLicenseHolder.setOpacity(1);
		} else {
			empLicenseHolder.setOpacity(0);
		}
	}
	
	/**
	 * Updates the TableView list
	 */
	private void updateEmployeeList() {
		employees.clear();
		employees.addAll(controller.readAllEmployees());
	}

	@Override
	public void notifyUndo() {
		updateEmployeeList();
	}

	@Override
	public void notifyListener() {
		updateEmployeeList();
		updateInfoTexts("", "", "");
		
	}
}
