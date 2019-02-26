package model;

import java.util.List;

public interface IDao<T> {
	public T get(int id);
	public List<T> getAll();
	public void create(T t);
	public void update(T t);
	public void delete(T t);
}
