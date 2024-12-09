public interface Publisher {
    void addObserver(Observer observer); // Menambahkan observer
    void removeObserver(Observer observer); // Menghapus observer
    void notifyObservers(Task task); // Memberi tahu observer
}