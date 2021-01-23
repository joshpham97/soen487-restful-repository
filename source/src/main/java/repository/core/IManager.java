package repository.core;

import java.util.ArrayList;

public interface IManager<T> {
    ArrayList<T> list();
    T get(String nickname);
    boolean add(T artist);
    boolean update(T artist);
    boolean delete(String nickname);
}
