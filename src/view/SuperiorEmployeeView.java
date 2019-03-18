package view;

import controller.IController;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class SuperiorEmployeeView {
	
	private IController controller;
	private BorderPane borderPane;
	
	public SuperiorEmployeeView(IController controller) {
		this.controller = controller;
		this.borderPane = new BorderPane();
		setup();
	}
	
	private void setup() {
		this.borderPane.setCenter(new Text("placeholder"));
	}
	
	public BorderPane getView() {
		return this.borderPane;
	}
	
	public void setNavBar(NavBar navBar) {
		this.borderPane.setTop(navBar.getNavBar());
	}
}
