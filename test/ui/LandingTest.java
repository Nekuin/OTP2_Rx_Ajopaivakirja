package ui;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.LandingView;
import view.ViewModule;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.util.Locale;
import java.util.ResourceBundle;

import org.assertj.core.api.Assertions;


@ExtendWith(ApplicationExtension.class)
public class LandingTest {

	private ViewModule landing;
	private IController controller;
	
	@Start
	private void start(Stage stage) {
		BorderPane root = new BorderPane();
		Locale.setDefault(new Locale("fi", "FI"));
		Main.b = ResourceBundle.getBundle("Strings");
		controller = new Controller(null);
		landing = new LandingView(controller);
		
		root.setCenter(landing.getView());
		Scene scene = new Scene(root, 400, 400);
		stage.setScene(scene);
		stage.show();
	}
	
	@Test
	void should_contain_button(FxRobot robot) {
		Assertions.assertThat(robot.lookup("#login-button").queryAs(Button.class));
		
	}
	
	@Test
	void should_contain_textField(FxRobot robot) {
		Assertions.assertThat(robot.lookup("#id-field").queryAs(TextField.class));
	}
	
	@Test
	void login_test(FxRobot robot) {
		Button b = robot.lookup("#login-button").queryAs(Button.class);
		TextField idField = robot.lookup("#id-field").queryAs(TextField.class);
		robot.clickOn(idField);
		robot.write("1");
		robot.clickOn(b);
	}
	

	
}