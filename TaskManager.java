import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private static TaskManager instance;
    private List<Task> tasks = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();
    private final String FILE_NAME = "tasks.txt";

    private TaskManager() {
        loadTasksFromFile();
    }

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public void addTask(String title, String description) {
        int id = tasks.isEmpty() ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
        Task task = new Task(id, title, description, false);
        tasks.add(task);
        saveTasksToFile();
        notifyObservers(task);
    }

    public void deleteTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
        saveTasksToFile();
        notifyObservers(null);
    }

    public void markTaskDone(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setDone(true);
                break;
            }
        }
        saveTasksToFile();
        notifyObservers(null);
    }

    public void editTask(int id, String newTitle, String newDescription) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setTitle(newTitle);
                task.setDescription(newDescription);
                break;
            }
        }
        saveTasksToFile();
        notifyObservers(null);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(Task task) {
        for (Observer observer : observers) {
            observer.update(task);
        }
    }

    private void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(task.getId() + ";" + task.getTitle() + ";" + task.getDescription() + ";" + task.isDone());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasksFromFile() {
        tasks.clear();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("File tidak ditemukan. Mulai dengan daftar tugas kosong.");
            return;
        }
    
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) { // Abaikan baris kosong
                    continue;
                }
    
                String[] parts = line.split(";"); // Pemisah ';'
                
                // Validasi format data (harus ada 4 elemen: ID, judul, deskripsi, status)
                if (parts.length != 4) {
                    System.err.println("Baris data tidak valid: " + line);
                    continue; // Abaikan baris yang tidak valid
                }
    
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    String description = parts[2].trim();
                    boolean isDone = Boolean.parseBoolean(parts[3].trim());
                    tasks.add(new Task(id, title, description, isDone));
                } catch (NumberFormatException e) {
                    System.err.println("Kesalahan format angka pada baris: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}