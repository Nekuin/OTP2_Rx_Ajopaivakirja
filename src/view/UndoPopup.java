package view;

import java.io.IOException;

import controller.IController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import model.DrivingShift;
import model.Employee;

public class UndoPopup {
	
	private IController controller;
	
    private BorderPane root;
    
    @FXML
    private Button undo_button;
    
    @FXML
    private Button dismiss_button;
    
    private boolean userDismissed;
    private Object o;
    private UndoObserver observer;
	
	public UndoPopup(IController controller, Object o, UndoObserver caller) {
		this.controller = controller;
		userDismissed = false;
		this.o = o;
		observer = caller;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("undo_msg.fxml"));
		loader.setController(this);
		try {
			loader.load();
			root = loader.getRoot();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		setup("Removed " + o.toString() + ", Undo?");
		startHideUndoTimer();
	}
	
	@FXML
	private void initialize() {
		undo_button.setOnAction(e -> {
			System.out.println("clicked undo");
			//determine if the object is an Employee or something else
			if(o instanceof Employee) {
				System.out.println("employee detected");
				//its possible we could just call controller.updateEmployee on all subclasses
				//of Employee - not tested
			} else if(o instanceof DrivingShift) {
				System.out.println("shift detected");
				//you have to merge (re-attach), not create a new Entity
				//thats why you call update and not create
				controller.updateDrivingShift((DrivingShift)o);
			}
			//clicking undo counts as "dismissing" the message
			userDismissed = true;
			observer.notifyUndo();
			//run on UI thread
			Platform.runLater(() -> {
				controller.resetRootBottom();
			});
		});
		
		undo_button.getStyleClass().add("undoButton");
		
		dismiss_button.setOnAction(e -> {
			userDismissed = true;
			removeFromDatabase();
		});
		
		dismiss_button.getStyleClass().add("dismissButton");
	}
	
	private void startHideUndoTimer() {
		new Thread(() -> {
			long startTime = System.currentTimeMillis();
			//loop for 5 seconds
			while((startTime+10000) > System.currentTimeMillis() && !userDismissed) {
				Thread.yield();
			}
			//permanently remove the object if the user didn't undo the action
			if(!userDismissed) {
				System.out.println("user didnt undo");
				removeFromDatabase();
			}
			//run on UI thread
			Platform.runLater(() -> {
				controller.resetRootBottom();
			});
		}).start();
	}
	
	private void removeFromDatabase() {
		//determine type of the object
		if(o instanceof Employee) {
			controller.deleteEmployee((Employee)o);
		} else if(o instanceof DrivingShift) {
			controller.deleteShift((DrivingShift)o);
		}
	}
	
	private void setup(String message) {
		undo_button.setText(message);
		
	}
	
	public BorderPane getView() {
		return root;
	}
	
	
}
