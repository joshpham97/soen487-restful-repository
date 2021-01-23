package repository.core;

public interface IManagerFactory<T> {
    IManager<T> loadManager();
}
