package model;

import java.util.List;

/**
 * 
 * @author tuoma
 * Interface for DAO
 * @param <T> Type of the object
 */
public interface IDao<T> {
	
	/**
	 * Returns object based on the id parameter
	 * @param id id of the object
	 * @return object of type T
	 */
	public T get(int id);
	
	/**
	 * Returns all the objects in the database
	 * @return List of objects type T
	 */
	public List<T> getAll();
	
	
	/**
	 * Inserts an object of type T into the database
	 * @param object of type T
	 */
	public void create(T t);
	
	/**
	 * Updates object of type T in the database
	 * @param Object of type T
	 */
	public void update(T t);
	
	/**
	 * Deletes object of type T from the database
	 * @param Object of type T
	 */
	public void delete(T t);
}
