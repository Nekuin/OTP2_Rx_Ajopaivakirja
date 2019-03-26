package view;

import java.util.Optional;

import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Driver;
import model.Employee;
import model.HrManager;
import model.Superior;

/**
 * 
 * @author tuoma
 *
 */
public class SuperiorEmployeeView implements ViewModule {

	private IController controller;
	private BorderPane borderPane;
	private ListView<Employee> employeeList;
	private ObservableList<Employee> employees;
	private Employee clicked;
	private String[] role;
	private ObservableList<String> roles;
	private ComboBox<String> roleDropDown;
	private int chosenRole;

	public SuperiorEmployeeView(IController controller) {
		this.controller = controller;
		this.borderPane = new BorderPane();
		setup();
	}

	private void setup() {
		HBox viewBox = new HBox();
		viewBox.setPadding(new Insets(30, 30, 30, 30));
		viewBox.getChildren().addAll(addButtons(), getEmployeeList());
		viewBox.setAlignment(Pos.TOP_CENTER);
		this.borderPane.setCenter(viewBox);
	}

	
	@Override
	public BorderPane getView() {
		return this.borderPane;
	}
	
	@Override
	public void setNavBar(NavBar navBar) {
		this.borderPane.setTop(navBar.getNavBar());
	}

	private ListView<Employee> getEmployeeList() {
		this.employees = FXCollections.observableArrayList();
		this.employees.addAll(controller.readAllEmployees());
		this.employeeList = new ListView<>();
		this.employeeList.setMinSize(400, 300);
		this.employeeList.setItems(employees);
		return this.employeeList;
	}

	private void updateEmployeeList() {
		this.employees.clear();
		this.employees.addAll(controller.readAllEmployees());
	}

	private GridPane addButtons() {

		GridPane buttonPane = new GridPane();

		Button addEmpBtn = new Button("Add new employee");
		addEmpBtn.setOnAction(e -> {
			Stage stage = new Stage();
			stage.setScene(new Scene(handleAddEmployeeModal()));
			stage.setTitle("Add a new employee");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(((Node) e.getSource()).getScene().getWindow());
			stage.show();
		});
		addEmpBtn.setId("add-employee");

		Button deleteEmpBtn = new Button("Delete employee");
		deleteEmpBtn.setOnMouseClicked(e -> {
			clicked = employeeList.getSelectionModel().getSelectedItem();
			this.role = clicked.getClass().getName().split("[.]");
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setDialogPane(new FixedOrderButtonDialog());
			alert.getButtonTypes().setAll(ButtonType.CANCEL, ButtonType.YES);
			alert.setTitle("Are you sure?");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete this employee?\n\nName: " + clicked.getName()
					+ "\nEmployee ID: " + clicked.getEmployeeID() + "\nRole: " + this.role[1] + "\n\n");
			System.out.println(clicked);
			Optional<ButtonType> option = alert.showAndWait();
			if (option.get() == ButtonType.YES) {
				controller.deleteEmployee(clicked);
				updateEmployeeList();
			} else {
				alert.close();
			}
		});

		Button updateEmpBtn = new Button("Update employee");
		updateEmpBtn.setOnAction(e -> {
			clicked = employeeList.getSelectionModel().getSelectedItem();

			Stage stage = new Stage();
			stage.setScene(new Scene(handleUpdateEmployeeModal(clicked)));
			stage.setTitle("Update employee information");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(((Node) e.getSource()).getScene().getWindow());
			stage.show();
		});

		VBox buttonBox = new VBox();
		buttonBox.setPadding(new Insets(20, 20, 20, 20));
		buttonBox.setSpacing(20);
		buttonBox.getChildren().addAll(addEmpBtn, deleteEmpBtn, updateEmpBtn);
		buttonPane.add(buttonBox, 0, 0);
		return buttonPane;

	}

	public BorderPane handleUpdateEmployeeModal(Employee clicked) {

		BorderPane modalPane = new BorderPane();

		VBox labelVBox = new VBox();
		labelVBox.setSpacing(30);
		labelVBox.setPadding(new Insets(30, 20, 20, 20));
		Text label = new Text("Fill in the information below:");
		label.setStyle("-fx-font: 20 arial;");
		Text mandatoryText = new Text("All the fields with * are mandatory");
		mandatoryText.setStyle("-fx-font: 17 arial;");
		Text infoText = new Text("Change the necessary fields below and save the changes");
		infoText.setStyle("-fx-font: 16 arial;");
		labelVBox.getChildren().addAll(label, mandatoryText, infoText);

		// Employee name hbox
		HBox nameBox = new HBox();
		nameBox.setSpacing(20);
		nameBox.setPadding(new Insets(20, 20, 20, 20));
		Text askNameText = new Text("* Name: ");
		TextField nameInput = new TextField(clicked.getName());
		nameInput.setId("name-field");
		nameBox.getChildren().addAll(askNameText, nameInput);

	
		// Employee id hbox
		HBox empiIDBox = new HBox();
		empiIDBox.setSpacing(5);
		empiIDBox.setPadding(new Insets(20, 20, 20, 20));
		Text empIDText = new Text("Employee ID: ");
		Text empID = new Text(Integer.toString(clicked.getEmployeeID()));
		empiIDBox.getChildren().addAll(empIDText, empID);
		
		// Employee role hbox
		HBox roleBox = new HBox();
		roleBox.setSpacing(5);
		roleBox.setPadding(new Insets(20, 20, 20, 20));
		Text askRoleText = new Text("Employee role: ");
		this.role = clicked.getClass().getName().split("[.]");
		Text roleText = new Text(this.role[1]);
		roleBox.getChildren().addAll(askRoleText, roleText);
		
		// Driverlicense hbox
		HBox licenceBox = new HBox();
		licenceBox.setSpacing(20);
		licenceBox.setPadding(new Insets(20, 20, 20, 20));
		Text askLicenceText = new Text("Licence: ");
		TextField licenceInput = new TextField(((Driver)clicked).getDriversLicense());
		licenceBox.getChildren().addAll(askLicenceText, licenceInput);
		
		// Buttons
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(30);
		buttonBox.setPadding(new Insets(30, 30, 30, 30));
		Button submitButton = new Button("Submit the changes");
		submitButton.setId("submit-button");

		submitButton.setOnAction(e -> {
			boolean textfieldsOK = false;

			if (nameInput.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Empty textfield");
				alert.setHeaderText(null);
				alert.setContentText("Please make sure you have input in name field.");
				alert.showAndWait();

			} else {
				clicked.setName(nameInput.getText());
				textfieldsOK = true;
			}
			
			if(licenceInput != null) {
				((Driver)clicked).setDriversLicense(licenceInput.getText());
			}

			if (textfieldsOK) {
				controller.updateEmployee(clicked);
				Alert confirmationMessage = new Alert(AlertType.INFORMATION);
				confirmationMessage.setTitle("The employee successfully updated");
				confirmationMessage.setHeaderText(null);
				confirmationMessage.setContentText("You have successfully the following employee's information: \n\n"
						+ "Name: " + clicked.getName() + "\nEmployee ID: "
						+ clicked.getEmployeeID() + "\nRole: " + this.role[1]);
				confirmationMessage.showAndWait();
				((Node) e.getSource()).getScene().getWindow().hide();
				updateEmployeeList();
			}
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e1 -> {
			((Node) e1.getSource()).getScene().getWindow().hide();
		});
		buttonBox.getChildren().addAll(cancelButton, submitButton);

		GridPane pane = new GridPane();
		pane.add(labelVBox, 0, 0);
		pane.add(nameBox, 0, 1);
		pane.add(empiIDBox, 0, 2);
		pane.add(roleBox, 0, 3);
		pane.add(licenceBox, 0, 4);
		pane.add(buttonBox, 0, 5);
		modalPane.setBottom(pane);

		if(!clicked.getClass().getName().contains("Driver")){
			System.out.println(clicked.getClass().getName());
			licenceBox.setVisible(false);
		}
		
		return modalPane;
	}

	public BorderPane handleAddEmployeeModal() {

		BorderPane modalPane = new BorderPane();

		VBox labelVBox = new VBox();
		labelVBox.setSpacing(30);
		labelVBox.setPadding(new Insets(30, 20, 20, 20));
		Text label = new Text("Fill in the information below:");
		label.setStyle("-fx-font: 20 arial;");
		Text mandatoryText = new Text("Fields marked with * are mandatory");
		mandatoryText.setStyle("-fx-font: 17 arial;");
		labelVBox.getChildren().addAll(label, mandatoryText);

		// Employee name hbox
		HBox nameBox = new HBox();
		nameBox.setSpacing(20);
		nameBox.setPadding(new Insets(20, 20, 20, 20));
		Text askNameText = new Text("* Name: ");
		TextField nameInput = new TextField();
		nameBox.getChildren().addAll(askNameText, nameInput);

		// Employee role hbox
		HBox roleBox = new HBox();
		roleBox.setSpacing(20);
		roleBox.setPadding(new Insets(20, 20, 20, 20));
		Text askRoleText = new Text("* Role: ");
		roleBox.getChildren().addAll(askRoleText, getRoleComboBox());
		
		// Driverlicense hbox
		HBox licenceBox = new HBox();
		licenceBox.setSpacing(20);
		licenceBox.setPadding(new Insets(20, 20, 20, 20));
		Text askLicenceText = new Text("Licence: ");
		TextField licenceInput = new TextField();
		licenceBox.getChildren().addAll(askLicenceText, licenceInput); // TEE TÄNNE ACTION LISTENER MÄÄRITTELEMÄÄN NÄKYYKÖ KENTTÄ VAI EI 

		// Buttons
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(30);
		buttonBox.setPadding(new Insets(30, 30, 30, 30));
		Button submitButton = new Button("Submit");
		//id for tests
		submitButton.setId("submit-button");
		submitButton.setOnAction(e -> {

			boolean textfieldOK = false;
			
			if (nameInput.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Empty textfield");
				alert.setHeaderText(null);
				alert.setContentText("Please make sure you have inputs in name field.");
				alert.showAndWait();
			} else {
				textfieldOK = true;		
			}
				
			boolean roleOK = false;
			if(chosenRole <0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("No role selected");
				alert.setHeaderText(null);
				alert.setContentText("Please make sure you have selected a role for the new employee.");
				alert.showAndWait();
			}else {
				roleOK = true;
			}
			
			if (textfieldOK && roleOK) {
				
				if(chosenRole == 0) {
					HrManager employee = new HrManager(nameInput.getText());
					controller.createHrManager(employee);
				}
				if(chosenRole == 1)
				{	
					Driver driver = new Driver(nameInput.getText(), licenceInput.getText());
					controller.createDriver(driver);
				}
				if(chosenRole == 2)
				{
					Superior employee = new Superior(nameInput.getText());
					controller.createSuperior(employee);
				}
				
				Alert confirmationMessage = new Alert(AlertType.INFORMATION);
				confirmationMessage.setTitle("New employee successfully added");
				confirmationMessage.setHeaderText(null);
				confirmationMessage.setContentText("You have successfully added a new employee!");
				confirmationMessage.showAndWait();
				((Node) e.getSource()).getScene().getWindow().hide();
				updateEmployeeList();
			}
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e -> {
			((Node) e.getSource()).getScene().getWindow().hide();
		});
		buttonBox.getChildren().addAll(cancelButton, submitButton);

		GridPane pane = new GridPane();
		pane.add(labelVBox, 0, 0);
		pane.add(roleBox, 0, 1);
		pane.add(nameBox, 0, 2);	
		pane.add(licenceBox, 0, 3);
		pane.add(buttonBox, 0, 4);
		modalPane.setBottom(pane);
		
		return modalPane;
	}

	public BorderPane getSuperiorView() {
		return this.borderPane;
	}
	
	private ComboBox<String> getRoleComboBox(){
	
		roles = FXCollections.observableArrayList();
		roles.addAll("Hr Manager", "Driver", "Superior");
		roleDropDown = new ComboBox<>(roles);
		roleDropDown.setPrefWidth(175);
		roleDropDown.setId("role-dropdown");
		return roleDropDown;
	}

	private class FixedOrderButtonDialog extends DialogPane {
		@Override
		protected Node createButtonBar() {
			ButtonBar node = (ButtonBar) super.createButtonBar();
			node.setButtonOrder(ButtonBar.BUTTON_ORDER_NONE);
			return node;
		}
	}

}
