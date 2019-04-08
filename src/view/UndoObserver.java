package view;

/**
 * Interface for undo action communication
 * @author Nekuin
 *
 */
public interface UndoObserver {
	/**
	 * Notify a listener that the user has selected undo
	 */
	public void notifyUndo();
}
