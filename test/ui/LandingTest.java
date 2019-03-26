package ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class LandingTest {

	private Button button;
	
	@Start
	private void start(Stage stage) {
		button = new Button("nappi");
		button.setId("nappi");
		button.setOnAction(e -> {
			button.setText("clicked");
		});
		stage.setScene(new Scene(new StackPane(button), 100, 100));
		stage.show();
	}
	
	@Test
	void should_contain_button() {
		verifyThat(".button", hasText("nappi"));
	}
	
	@Test
	void shoud_click_on_button(FxRobot robot) {
		robot.clickOn(".button");
		
		verifyThat(".button", hasText("clicked"));
	}
	
}
