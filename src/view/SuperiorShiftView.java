package view;

import java.io.IOException;

import controller.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import model.DrivingShift;
import util.Strings;

public class SuperiorShiftView implements ViewModule {
	
	private BorderPane bpane;
	private IController controller;
	private Strings strings;
	
    @FXML
    private Button add_shift_button;

    @FXML
    private Button delete_shift_button;

    @FXML
    private Button update_shift_button;

    @FXML
    private ListView<DrivingShift> shiftList;
	
	public SuperiorShiftView(IController controller) {
		this.controller = controller;
		this.strings = Strings.getInstance();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Superior_Employee_View.fxml"), strings.getBundle());
		loader.setController(this);
		try {
			loader.load();
			bpane = loader.getRoot();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void initialize() {
		add_shift_button.setOnAction(e -> {
			System.out.println("add shift");
		});
	}

	@Override
	public void setNavBar(NavBar navBar) {
		bpane.setTop(navBar.getNavBar());
	}

	@Override
	public BorderPane getView() {
		return bpane;
	}
	
}
