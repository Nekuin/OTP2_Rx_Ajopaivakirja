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

/**
 * Popup style module that handles the undo action
 * @author Nekuin
 *
 */
public class UndoPopup implements ViewModule {
	
	private IController controller;
	
    private BorderPane root;
    
    @FXML
    private Button undo_button;
    
    @FXML
    private Button dismiss_button;
    
    private boolean userDismissed;
    private Object removedObject;
    private UndoObserver observer;
	/**
	 * Constructor for Undo Popup
	 * @param IController controller
	 * @param Object removedObject
	 * @param UndoObserver caller
	 */
	public UndoPopup(IController controller, Object removedObject, UndoObserver caller) {
		this.controller = controller;
		userDismissed = false;
		this.removedObject = removedObject;
		observer = caller;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/undo_msg.fxml"));
		loader.setController(this);
		try {
			loader.load();
			root = loader.getRoot();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		setup("Removed " + removedObject.toString() + ", Undo?");
		startHideUndoTimer();
	}
	/**
	 * Initializes the buttons in the popup
	 */
	@FXML
	private void initialize() {
		undo_button.setOnAction(e -> {
			System.out.println("clicked undo");
			//determine if the object is an Employee or something else
			if(removedObject instanceof Employee) {
				System.out.println("employee detected");
				//its possible we could just call controller.updateEmployee on all subclasses
				//of Employee - not tested
			} else if(removedObject instanceof DrivingShift) {
				System.out.println("shift detected");
				//you have to merge (re-attach), not create a new Entity
				//thats why you call update and not create
				controller.updateDrivingShift((DrivingShift)removedObject);
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
	
	/**
	 * Starts the timer that determines when the popup will be disabled
	 */
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
	/**
	 * Removes the object from database
	 */
	private void removeFromDatabase() {
		//determine type of the object
		if(removedObject instanceof Employee) {
			controller.deleteEmployee((Employee)removedObject);
		} else if(removedObject instanceof DrivingShift) {
			controller.deleteShift((DrivingShift)removedObject);
		}
	}
	/**
	 * Sets up the button 
	 * @param message
	 */
	private void setup(String message) {
		undo_button.setText(message);
	}
	
	/**
	 * Show the popup message
	 */
	public void showMessage() {
		controller.showUndoMessage(getView());
	}
	
	@Override
	public BorderPane getView() {
		return root;
	}
	
	
}
