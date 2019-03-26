package ui;

import java.util.Locale;
import java.util.ResourceBundle;

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
import view.SuperiorEmployeeView;
import view.ViewModule;

@ExtendWith(ApplicationExtension.class)
public class SuperiorTest {
	
	private ViewModule sup;

	@Start
	public void start(Stage stage) {
		Locale.setDefault(new Locale("fi", "FI"));
		Main.b = ResourceBundle.getBundle("Strings");
		
		BorderPane root = new BorderPane();
		
		IController controller = new Controller(null);
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
		robot.type(KeyCode.DOWN);
		robot.type(KeyCode.DOWN);
		robot.type(KeyCode.ENTER);
		/*
		TextField nameField = robot.lookup("#name-field").queryAs(TextField.class);
		robot.clickOn(nameField);
		*/
		robot.type(KeyCode.TAB);
		robot.write("Timo Soini");
		
		robot.type(KeyCode.TAB);
		robot.write("A");
		
		//TODO: cant find buten
		Button submitButton = robot.lookup("#submit-button").queryAs(Button.class);
		robot.clickOn(submitButton);
	}
}
