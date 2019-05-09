package ui;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;


import controller.Controller;
import controller.IController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.Strings;
import view.LandingView;
import view.ViewModule;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.util.Locale;

import org.assertj.core.api.Assertions;


@ExtendWith(ApplicationExtension.class)
public class LandingTest {

	private ViewModule landing;
	private IController controller;
	private Strings strings;
	
	
	
	
	@Start
	private void start(Stage stage) {
		strings = Strings.getInstance();
		strings.changeBundle(new Locale("fi", "FI"));
		BorderPane root = new BorderPane();
		controller = new Controller(null);
		landing = new LandingView(controller, null);
		
		root.setCenter(landing.getView());
		Scene scene = new Scene(root, 400, 400);
		stage.setScene(scene);
		stage.show();
	}
	
	@Test
	void should_contain_button(FxRobot robot) {
		FxAssert.verifyThat(".button", LabeledMatchers.hasText("Kirjaudu sisään"));
		
	}
	
	@Test
	void should_contain_textField(FxRobot robot) {
		Assertions.assertThat(robot.lookup("#login_field").queryAs(TextField.class));
	}
	
	@Test
	void login_test(FxRobot robot) {
		Button btn = robot.lookup("Kirjaudu sisään").queryAs(Button.class);
		TextField idField = robot.lookup("#login_field").queryAs(TextField.class);
		robot.clickOn(idField);
		robot.write("2");
		robot.clickOn(btn);
	}
	
	
	
	
	

	
}
