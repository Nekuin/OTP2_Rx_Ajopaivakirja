package model;

import java.util.List;

public interface IDao<T> {
	public T get(int id);
	public List<T> getAll();
	public void create(T driver);
	public void update(T driver);
	public void delete(T driver);
}
