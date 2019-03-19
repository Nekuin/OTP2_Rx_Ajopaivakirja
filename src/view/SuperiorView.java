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
import javafx.scene.control.CheckBox;
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
import model.Vehicle;

/**
 * 
 * @author tuoma
 *
 */
public class SuperiorView {

	private IController controller;
	private BorderPane borderPane;
	private ListView<Vehicle> vehicleList;
	private ObservableList<Vehicle> vehicles;
	private Vehicle clicked;

	public SuperiorView(IController controller) {
		this.controller = controller;
		this.borderPane = new BorderPane();
		setup();
	}

	public void setup() {
		HBox viewBox = new HBox();
		viewBox.setPadding(new Insets(30, 30, 30, 30));
		viewBox.getChildren().addAll(addButtons(), getCarList());
		viewBox.setAlignment(Pos.TOP_CENTER);
		this.borderPane.setCenter(viewBox);
	}

	public void setNavBar(NavBar navBar) {
		this.borderPane.setTop(navBar.getNavBar());
	}

	private ListView<Vehicle> getCarList() {
		this.vehicles = FXCollections.observableArrayList();
		this.vehicles.addAll(controller.readAllVehicles());
		this.vehicleList = new ListView<>();
		this.vehicleList.setMinSize(200, 300);
		this.vehicleList.setItems(vehicles);
		return this.vehicleList;
	}

	private void updateCarList() {
		this.vehicles.clear();
		this.vehicles.addAll(controller.readAllVehicles());
	}

	private GridPane addButtons() {

		GridPane buttonPane = new GridPane();
		

		Button addCarBtn = new Button("Add new car");
		addCarBtn.setOnAction(e -> {
			Stage stage = new Stage();
			stage.setScene(new Scene(handleAddCarModal()));
			stage.setTitle("Add a new car");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(((Node) e.getSource()).getScene().getWindow());
			stage.show();
		});

		Button deleteCarBtn = new Button("Delete car");
		deleteCarBtn.setOnMouseClicked( e -> {
	            clicked = vehicleList.getSelectionModel().getSelectedItem();
	            
	            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	            alert.setDialogPane(new FixedOrderButtonDialog());
	            alert.getButtonTypes().setAll(ButtonType.CANCEL, ButtonType.YES);
	            alert.setTitle("Are you sure?");
	            alert.setHeaderText(null);
	            alert.setContentText("Are you sure you want to delete this car:\n\nBrand & Model: " +
	            	clicked.getBrand() + " " + clicked.getModel() + "\nRegister number: " + clicked.getRegNr() + "?\n\n");
	            System.out.println(clicked);
	            Optional<ButtonType> option = alert.showAndWait();
	            if (option.get() == ButtonType.YES) {
            		controller.deleteVehicle(clicked);
            		updateCarList();
	             } else {
	                alert.close();
	             }	                  
	            });
	    

		Button updateCarBtn = new Button("Update car");
		updateCarBtn.setOnAction(e -> {
			clicked = vehicleList.getSelectionModel().getSelectedItem();
			
			Stage stage = new Stage();
			stage.setScene(new Scene(handleUpdateCarModal(clicked)));
			stage.setTitle("Update car information");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(((Node) e.getSource()).getScene().getWindow());
			stage.show();
		});

		VBox buttonBox = new VBox();
		buttonBox.setPadding(new Insets(20, 20, 20, 20));
		buttonBox.setSpacing(20);
		buttonBox.getChildren().addAll(addCarBtn, deleteCarBtn, updateCarBtn);
		buttonPane.add(buttonBox, 0, 0);
		return buttonPane;

	}
	
	public BorderPane handleUpdateCarModal(Vehicle clicked) {
		
		BorderPane modalPane = new BorderPane();
		
		VBox labelVBox = new VBox();
		labelVBox.setSpacing(30);
		labelVBox.setPadding(new Insets(30, 20, 20, 20));
		Text label = new Text("Fill in the information below:");
		label.setStyle("-fx-font: 20 arial;");
		Text mandatoryText = new Text("All the fields are mandatory");
		mandatoryText.setStyle("-fx-font: 17 arial;");
		Text infoText = new Text("Change the necessary fields below and save the changes");
		infoText.setStyle("-fx-font: 16 arial;");
		labelVBox.getChildren().addAll(label, mandatoryText, infoText);

		// Car brand hbox
		HBox brandBox = new HBox();
		brandBox.setSpacing(20);
		brandBox.setPadding(new Insets(20, 20, 20, 20));
		Text askBrandText = new Text("Brand of the car: ");
		TextField brandInput = new TextField(clicked.getBrand());
		brandBox.getChildren().addAll(askBrandText, brandInput);

		// Car model hbox
		HBox modelBox = new HBox();
		modelBox.setSpacing(20);
		modelBox.setPadding(new Insets(20, 20, 20, 20));
		Text askModelText = new Text("Model of the car: ");
		TextField modelInput = new TextField(clicked.getModel());
		modelBox.getChildren().addAll(askModelText, modelInput);

		// Reg num hbox
		HBox regNumlBox = new HBox();
		regNumlBox.setSpacing(20);
		regNumlBox.setPadding(new Insets(20, 20, 20, 20));
		Text regNumText = new Text("Register number: ");
		TextField regNumInput = new TextField(clicked.getRegNr());
		regNumlBox.getChildren().addAll(regNumText, regNumInput);

		// driver distance hbox
		HBox dDistBox = new HBox();
		dDistBox.setSpacing(20);
		dDistBox.setPadding(new Insets(20, 20, 20, 20));
		Text dDistText = new Text("Driven distance: ");
		Text kmText = new Text("km");
		TextField dDistInput = new TextField(Double.toString(clicked.getDrivenDistance()));
		dDistBox.getChildren().addAll(dDistText, dDistInput, kmText);

		// cargo hbox
		HBox cargoBox = new HBox();
		cargoBox.setSpacing(20);
		cargoBox.setPadding(new Insets(20, 20, 20, 20));
		Text cargoText = new Text("Max cargo load: ");
		TextField cargoInput = new TextField(Integer.toString(clicked.getMaxCargoLoad()));
		Text weightText = new Text("Kg");
		cargoBox.getChildren().addAll(cargoText, cargoInput, weightText);

		// maintained/new
		HBox maintainedBox = new HBox();
		maintainedBox.setSpacing(20);
		maintainedBox.setPadding(new Insets(20, 20, 20, 20));
		Text maintainedText = new Text("Is this a new/maintained car: ");
		CheckBox maintainedCheckBox = new CheckBox();
		if(clicked.getMaintained()) {
			maintainedCheckBox.setSelected(true);
		}
		maintainedBox.getChildren().addAll(maintainedText, maintainedCheckBox);

		// Buttons
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(30);
		buttonBox.setPadding(new Insets(30, 30, 30, 30));
		Button submitButton = new Button("Submit the changes");
		submitButton.setOnAction(e -> {
			
			clicked.setBrand(brandInput.getText());
			clicked.setModel(modelInput.getText());
			clicked.setRegNr(regNumInput.getText());
			boolean everythingOK = true;
			try {
				Double.parseDouble(dDistInput.getText());
			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Check the driven distance field");
				alert.setHeaderText(null);
				alert.setContentText("Please enter only numbers in driven distance field.");
				alert.showAndWait();
				everythingOK = false;
			}
			clicked.setDrivenDistance(Double.parseDouble(dDistInput.getText()));

			try {
				Integer.parseInt(cargoInput.getText());
			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Check the cargo load field");
				alert.setHeaderText(null);
				alert.setContentText("Please enter only numbers in max cargo load field.");
				alert.showAndWait();
				everythingOK = false;
			}
			clicked.setMaxCargoLoad(Integer.parseInt(cargoInput.getText()));

			if (maintainedCheckBox.isSelected()) {
				clicked.setMaintained(true);
			} else {
				clicked.setMaintained(false);
			}

			if (everythingOK) {
				controller.updateVehicle(clicked);
				Alert confirmationMessage = new Alert(AlertType.INFORMATION);
				confirmationMessage.setTitle("The car successfully updated");
				confirmationMessage.setHeaderText(null);
				confirmationMessage.setContentText("You have successfully the following car's information: \n\n" + "Brand & Model: "
						+ clicked.getBrand() + " " + clicked.getModel() + "\nRegister number: " + clicked.getRegNr()
						+ "\nDriven distance: " + clicked.getDrivenDistance() + " km");
				confirmationMessage.showAndWait();
				((Node) e.getSource()).getScene().getWindow().hide();
				updateCarList();
			}
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e -> {
			((Node) e.getSource()).getScene().getWindow().hide();
		});
		buttonBox.getChildren().addAll(cancelButton, submitButton);

		GridPane pane = new GridPane();
		pane.add(labelVBox, 0, 0);
		pane.add(brandBox, 0, 1);
		pane.add(modelBox, 0, 2);
		pane.add(regNumlBox, 0, 3);
		pane.add(dDistBox, 0, 4);
		pane.add(cargoBox, 0, 5);
		pane.add(maintainedBox, 0, 6);
		pane.add(buttonBox, 0, 7);
		modalPane.setBottom(pane);
		
		return modalPane;
	}

	public BorderPane handleAddCarModal() {

		BorderPane modalPane = new BorderPane();

		VBox labelVBox = new VBox();
		labelVBox.setSpacing(30);
		labelVBox.setPadding(new Insets(30, 20, 20, 20));
		Text label = new Text("Fill in the information below:");
		label.setStyle("-fx-font: 20 arial;");
		Text mandatoryText = new Text("All the fields are mandatory");
		mandatoryText.setStyle("-fx-font: 17 arial;");
		labelVBox.getChildren().addAll(label, mandatoryText);

		// Car brand hbox
		HBox brandBox = new HBox();
		brandBox.setSpacing(20);
		brandBox.setPadding(new Insets(20, 20, 20, 20));
		Text askBrandText = new Text("Brand of the car: ");
		TextField brandInput = new TextField();
		brandBox.getChildren().addAll(askBrandText, brandInput);

		// Car model hbox
		HBox modelBox = new HBox();
		modelBox.setSpacing(20);
		modelBox.setPadding(new Insets(20, 20, 20, 20));
		Text askModelText = new Text("Model of the car: ");
		TextField modelInput = new TextField();
		modelBox.getChildren().addAll(askModelText, modelInput);

		// Reg num hbox
		HBox regNumlBox = new HBox();
		regNumlBox.setSpacing(20);
		regNumlBox.setPadding(new Insets(20, 20, 20, 20));
		Text regNumText = new Text("Register number: ");
		TextField regNumInput = new TextField();
		regNumlBox.getChildren().addAll(regNumText, regNumInput);

		// driver distance hbox
		HBox dDistBox = new HBox();
		dDistBox.setSpacing(20);
		dDistBox.setPadding(new Insets(20, 20, 20, 20));
		Text dDistText = new Text("Driven distance: ");
		Text kmText = new Text("km");
		TextField dDistInput = new TextField();
		dDistBox.getChildren().addAll(dDistText, dDistInput, kmText);

		// cargo hbox
		HBox cargoBox = new HBox();
		cargoBox.setSpacing(20);
		cargoBox.setPadding(new Insets(20, 20, 20, 20));
		Text cargoText = new Text("Max cargo load: ");
		TextField cargoInput = new TextField();
		Text weightText = new Text("Kg");
		cargoBox.getChildren().addAll(cargoText, cargoInput, weightText);

		// maintained/new
		HBox maintainedBox = new HBox();
		maintainedBox.setSpacing(20);
		maintainedBox.setPadding(new Insets(20, 20, 20, 20));
		Text maintainedText = new Text("Is this a new/maintained car: ");
		CheckBox maintainedCheckBox = new CheckBox();
		maintainedBox.getChildren().addAll(maintainedText, maintainedCheckBox);

		// Buttons
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(30);
		buttonBox.setPadding(new Insets(30, 30, 30, 30));
		Button submitButton = new Button("Submit the car");
		submitButton.setOnAction(e -> {
			Vehicle vehicle = new Vehicle();
			vehicle.setBrand(brandInput.getText());
			vehicle.setModel(modelInput.getText());
			vehicle.setRegNr(regNumInput.getText());
			boolean everythingOK = true;
			try {
				Double.parseDouble(dDistInput.getText());
			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Check the driven distance field");
				alert.setHeaderText(null);
				alert.setContentText("Please enter only numbers in driven distance field.");
				alert.showAndWait();
				everythingOK = false;
			}
			vehicle.setDrivenDistance(Double.parseDouble(dDistInput.getText()));

			try {
				Integer.parseInt(cargoInput.getText());
			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Check the cargo load field");
				alert.setHeaderText(null);
				alert.setContentText("Please enter only numbers in max cargo load field.");
				alert.showAndWait();
				everythingOK = false;
			}
			vehicle.setMaxCargoLoad(Integer.parseInt(cargoInput.getText()));

			if (maintainedCheckBox.isSelected()) {
				vehicle.setMaintained(true);
			} else {
				vehicle.setMaintained(false);
			}

			if (everythingOK) {
				controller.createVehicle(vehicle);
				Alert confirmationMessage = new Alert(AlertType.INFORMATION);
				confirmationMessage.setTitle("New car successfully added");
				confirmationMessage.setHeaderText(null);
				confirmationMessage.setContentText("You have successfully added a new car: \n\n" + "Brand & Model: "
						+ vehicle.getBrand() + " " + vehicle.getModel() + "\nRegister number: " + vehicle.getRegNr()
						+ "\nDriven distance: " + vehicle.getDrivenDistance() + " km");
				confirmationMessage.showAndWait();
				((Node) e.getSource()).getScene().getWindow().hide();
				updateCarList();
			}
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e -> {
			((Node) e.getSource()).getScene().getWindow().hide();
		});
		buttonBox.getChildren().addAll(cancelButton, submitButton);

		GridPane pane = new GridPane();
		pane.add(labelVBox, 0, 0);
		pane.add(brandBox, 0, 1);
		pane.add(modelBox, 0, 2);
		pane.add(regNumlBox, 0, 3);
		pane.add(dDistBox, 0, 4);
		pane.add(cargoBox, 0, 5);
		pane.add(maintainedBox, 0, 6);
		pane.add(buttonBox, 0, 7);
		modalPane.setBottom(pane);

		return modalPane;
	}

	public BorderPane getSuperiorView() {
		return this.borderPane;
	}
	
	private static class FixedOrderButtonDialog extends DialogPane {
	    @Override
	    protected Node createButtonBar() {
	        ButtonBar node = (ButtonBar) super.createButtonBar();
	        node.setButtonOrder(ButtonBar.BUTTON_ORDER_NONE);
	        return node;
	    }
	}
}


