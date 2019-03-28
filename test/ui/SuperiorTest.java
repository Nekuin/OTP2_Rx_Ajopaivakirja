package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Driver;
import model.Employee;
import view.SuperiorEmployeeView;
import view.ViewModule;

@ExtendWith(ApplicationExtension.class)
public class SuperiorTest {
	
	private ViewModule sup;
	private static IController controller;
	
	
	@BeforeAll
	public static void beforeAll() {
		controller = new Controller(null);
		controller.createDriver(new Driver("Soini2", "A"));
	}
	
	@Start
	public void start(Stage stage) {
		Locale.setDefault(new Locale("fi", "FI"));
		Main.b = ResourceBundle.getBundle("Strings");
		
		BorderPane root = new BorderPane();
		
		
		
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
		
		//click on ok
		robot.type(KeyCode.ENTER);
		
		//count all employees(expected 1 since we haven't created any test drivers yet!)
		int n = controller.readAllEmployees().size();
		System.out.println("n: " + n);
		
		assertEquals(1, n, "Employee was not created, expected 1 only counted 0!");
	}
	
	@Test
	public void removeDriver(FxRobot robot) {
		
		ListView<Employee> listView = robot.lookup("#emp-list").queryAs(ListView.class);
		listView.getSelectionModel().select(0);
		Button remEmpBtn = robot.lookup("#delete-employee").queryAs(Button.class);
		robot.clickOn(remEmpBtn);
		robot.type(KeyCode.TAB);
		robot.type(KeyCode.ENTER);
		int n = controller.readAllEmployees().size();
		System.out.println("remove n: " + n);
	}
	
	@Test
	public void checkDriver() {
		Collection<Driver> drivers = controller.readAllDrivers();
		Optional<Driver> driver = drivers.stream().filter(d -> d.getName().equals("Timo Soini")).findFirst();
		String name = "Timo Soini";
		if(driver.isPresent()) {
			System.out.println("soini found");
			System.out.println(driver.get());
			assertEquals(name, driver.get().getName());
		} else {
			assertTrue(false, "Timo was not found");
		}
		
	}
	
	
}
