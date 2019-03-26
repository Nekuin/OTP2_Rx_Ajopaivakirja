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

	private Button button;
	private ViewModule landing;
	
	@Start
	private void start(Stage stage) {
		BorderPane root = new BorderPane();
		Locale.setDefault(new Locale("fi", "FI"));
		Main.b = ResourceBundle.getBundle("Strings");
		landing = new LandingView(null);
		
		root.setCenter(landing.getView());
		Scene scene = new Scene(root, 400, 400);
		stage.setScene(scene);
		stage.show();
	}
	
	@Test
	void should_contain_button(FxRobot robot) {
		Assertions.assertThat(robot.lookup("#login-button").queryAs(Button.class));
		//Assertions.assertThat(robot.lookup("#landing").queryAs(BorderPane.class));
		//verifyThat(".login-button", hasText("Kirjaudu sis‰‰n"));
	}
	

	
}
