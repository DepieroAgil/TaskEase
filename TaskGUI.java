import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TaskGUI extends JFrame implements Observer {
    private JTextField titleInput; // Input untuk judul tugas
    private JTextArea taskInput; // Input untuk deskripsi tugas
    private JButton addButton;
    private DefaultListModel<Task> taskListModel; // Simpan Task, bukan String
    private JList<Task> taskList; // JList menggunakan Task
    private JButton markDoneButton, deleteButton, editButton;
    private TaskManager taskManager;

    public TaskGUI() {
        taskManager = TaskManager.getInstance();
        taskManager.addObserver(this);

        setTitle("TaskEase - Task Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 600); // Sesuaikan ukuran untuk tampilan vertikal

        // Inisialisasi taskListModel
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setCellRenderer(new TaskRenderer()); // Gunakan custom renderer

        // Input fields
        titleInput = new JTextField(20);

        // Ganti JTextField dengan JTextArea untuk deskripsi
        taskInput = new JTextArea(5, 20); // Tinggi 5 baris, lebar 20 karakter
        taskInput.setLineWrap(true); // Aktifkan pemotongan otomatis
        taskInput.setWrapStyleWord(true); // Potong pada kata, bukan huruf

        // Tambahkan JScrollPane untuk JTextArea
        JScrollPane descriptionScrollPane = new JScrollPane(taskInput);

        addButton = new JButton("Tambah Tugas");
        markDoneButton = new JButton("Tandai Selesai");
        deleteButton = new JButton("Hapus Tugas");
        editButton = new JButton("Edit Tugas");

        // Atur warna tombol
        styleButton(addButton, new Color(0, 123, 255), Color.WHITE); // Biru
        styleButton(markDoneButton, new Color(40, 167, 69), Color.WHITE); // Hijau
        styleButton(deleteButton, new Color(220, 53, 69), Color.WHITE); // Merah
        styleButton(editButton, new Color(255, 193, 7), Color.BLACK); // Kuning

        // Buat panel input dengan BoxLayout (Vertikal)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Tambahkan margin (kiri, atas, kanan, bawah)

        // Tambahkan komponen secara vertikal
        inputPanel.add(new JLabel("Judul:"));
        inputPanel.add(titleInput);
        inputPanel.add(Box.createVerticalStrut(10)); // Jarak antar elemen
        inputPanel.add(new JLabel("Deskripsi:"));
        inputPanel.add(descriptionScrollPane);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(addButton);

        // Buat panel tombol di bawah
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(markDoneButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        // Tambahkan ke layout utama
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listener untuk Tambah Tugas
        addButton.addActionListener(e -> {
            String title = titleInput.getText();
            String description = taskInput.getText();
            if (!title.isEmpty() && !description.isEmpty()) {
                taskManager.addTask(title, description);
                titleInput.setText("");
                taskInput.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Judul dan Deskripsi tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Listener untuk "Hapus Tugas"
        deleteButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex(); // Ambil indeks tugas yang dipilih
            if (selectedIndex != -1) {
                Task selectedTask = taskListModel.getElementAt(selectedIndex); // Ambil tugas dari model
                taskManager.deleteTask(selectedTask.getId()); // Hapus berdasarkan ID
            } else {
                JOptionPane.showMessageDialog(this, "Pilih tugas untuk dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Listener untuk "Tandai Selesai"
        markDoneButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex(); // Ambil indeks tugas yang dipilih
            if (selectedIndex != -1) {
                Task selectedTask = taskListModel.getElementAt(selectedIndex); // Ambil tugas dari model
                taskManager.markTaskDone(selectedTask.getId()); // Tandai selesai berdasarkan ID
            } else {
                JOptionPane.showMessageDialog(this, "Pilih tugas untuk ditandai selesai!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Listener untuk "Edit Tugas"
editButton.addActionListener(e -> {
    int selectedIndex = taskList.getSelectedIndex(); // Ambil indeks tugas yang dipilih
    if (selectedIndex != -1) {
        Task selectedTask = taskListModel.getElementAt(selectedIndex); // Ambil tugas dari model
        
        // Input baru untuk judul
        String newTitle = JOptionPane.showInputDialog(this, "Edit Judul:", selectedTask.getTitle());

        // Input baru untuk deskripsi dengan JTextArea
        JTextArea descriptionArea = new JTextArea(5, 20); // Kotak teks besar
        descriptionArea.setText(selectedTask.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        int result = JOptionPane.showConfirmDialog(
                this,
                scrollPane,
                "Edit Deskripsi:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Jika pengguna menekan OK, ambil data baru
        if (result == JOptionPane.OK_OPTION) {
            String newDescription = descriptionArea.getText();

            // Validasi input baru
            if (newTitle != null && !newTitle.isEmpty() && newDescription != null && !newDescription.isEmpty()) {
                taskManager.editTask(selectedTask.getId(), newTitle, newDescription); // Edit tugas berdasarkan ID
            } else {
                JOptionPane.showMessageDialog(this, "Judul dan Deskripsi tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih tugas untuk diedit!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        refreshTaskList(); // Panggil refresh untuk menampilkan data awal
    }

    // Metode untuk memberikan gaya pada tombol
    private void styleButton(JButton button, Color background, Color foreground) {
        button.setBackground(background); // Warna latar
        button.setForeground(foreground); // Warna teks
        button.setOpaque(true); // Pastikan warna background terlihat
        button.setBorderPainted(false); // Hilangkan border
    }

    @Override
    public void update(Task task) {
        refreshTaskList();
    }

    private void refreshTaskList() {
        taskListModel.clear(); 
        for (Task task : taskManager.getTasks()) {
            taskListModel.addElement(task); 
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskGUI gui = new TaskGUI();
            gui.setVisible(true);
        });
    }

    // Custom Renderer untuk JList
    private static class TaskRenderer extends JPanel implements ListCellRenderer<Task> {
        private JLabel titleLabel;
        private JLabel descriptionLabel;
        private JLabel statusLabel;

        public TaskRenderer() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(10, 10, 10, 10)); // Margin untuk setiap item

            titleLabel = new JLabel();
            titleLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Gaya teks untuk judul

            statusLabel = new JLabel();
            statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            descriptionLabel = new JLabel();
            descriptionLabel.setFont(new Font("Arial", Font.ITALIC, 12));

            add(titleLabel);
            add(statusLabel);
            add(descriptionLabel);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Task> list, Task task, int index, boolean isSelected, boolean cellHasFocus) {
            titleLabel.setText(task.getTitle());
            statusLabel.setText("Status: " + (task.isDone() ? "Selesai" : "Belum"));
            descriptionLabel.setText(task.getDescription());

            // Ganti warna latar saat dipilih
            if (isSelected) {
                setBackground(new Color(220, 230, 241)); // Warna saat dipilih
            } else {
                setBackground(Color.WHITE); // Warna default
            }
            setOpaque(true);
            return this;
        }
    }
}