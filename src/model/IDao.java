package model;

import java.util.List;

public interface IDao<T> {
	
	/**
	 * Returns object based on the id parameter
	 * @param id id of the object
	 * @return
	 */
	public T get(int id);
	
	/**
	 * Returns all the objects in the database
	 * @return
	 */
	public List<T> getAll();
	
	
	/**
	 * Creates object in the database 
	 * @param t object thats added to the database
	 */
	public void create(T t);
	
	/**
	 * Updates object in the database
	 * @param t object that is being updated
	 */
	public void update(T t);
	
	/**
	 * Deletes object from the database
	 * @param t object that is being deleted from database
	 */
	public void delete(T t);
}
