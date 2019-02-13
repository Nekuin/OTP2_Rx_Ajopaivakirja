package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class HRView implements IHRView {
	
	private BorderPane bpane;
	
	public HRView() {
		this.bpane = new BorderPane();
		
		//for testing
		this.bpane.setCenter(new Text("ph"));
	}
	

	@Override
	public BorderPane getHRView() {
		return this.bpane;
	}
	
}
