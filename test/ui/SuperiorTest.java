package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import application.Main;
import controller.Controller;
import controller.IController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Driver;
import view.SuperiorEmployeeView;
import view.ViewModule;

@ExtendWith(ApplicationExtension.class)
public class SuperiorTest {
	
	private ViewModule sup;
	private IController controller;
	
	@Start
	public void start(Stage stage) {
		Locale.setDefault(new Locale("fi", "FI"));
		Main.b = ResourceBundle.getBundle("Strings");
		
		BorderPane root = new BorderPane();
		
		controller = new Controller(null);
		sup = new SuperiorEmployeeView(controller);
		
		root.setCenter(sup.getView());
		Scene scene = new Scene(root, 400, 400);
		stage.setScene(scene);
		stage.show();
	}
	
	@Test
	public void addDriver(FxRobot robot) {
		Button addEmpBtn = robot.lookup("#add-employee").queryAs(Button.class);
		robot.clickOn(addEmpBtn);
		
		ComboBox<String> rolet = robot.lookup("#role-dropdown").queryAs(ComboBox.class);
		robot.clickOn(rolet);
		
		//choose second item from the list (Driver)
		robot.type(KeyCode.DOWN);
		robot.type(KeyCode.DOWN);
		robot.type(KeyCode.ENTER);

		//tab into the name field
		robot.type(KeyCode.TAB);
		robot.write("Timo Soini");
		
		//tab into the drivers license field
		robot.type(KeyCode.TAB);
		robot.write("A");
		
		//click on submit
		Button submitButton = robot.lookup("#submit-button").queryAs(Button.class);
		robot.clickOn(submitButton);
		
		//count all employees(expected 1 since we haven't created any test drivers yet!)
		int n = controller.readAllEmployees().size();
		assertEquals(1, n, "Employee was not created, expected 1 only counted 0!");
	}
}
