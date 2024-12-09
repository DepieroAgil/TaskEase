# TaskEase

## Deskripsi
TaskEase adalah aplikasi manajemen tugas berbasis **Java** yang dirancang untuk membantu pengguna mengelola daftar tugas mereka dengan mudah. Aplikasi ini mendukung fitur-fitur seperti menambah tugas, mengedit tugas, menandai tugas sebagai selesai, dan menghapus tugas. Data tugas juga disimpan secara permanen sehingga dapat diakses kembali saat aplikasi dijalankan.

---

## Fitur Utama
- **Tambah Tugas**: Tambahkan tugas baru dengan judul dan deskripsi.
- **Edit Tugas**: Ubah judul dan deskripsi tugas yang sudah dibuat.
- **Tandai Selesai**: Tandai tugas yang telah selesai untuk mencatat progres.
- **Hapus Tugas**: Hapus tugas yang tidak diperlukan dari daftar.
- **Deskripsi Multi-Baris**: Tampilan deskripsi yang lebih terorganisir untuk tugas yang panjang.
- **Penyimpanan Permanen**: Data tugas disimpan secara lokal dan dimuat ulang saat aplikasi dijalankan.

---

## Teknologi yang Digunakan
- **Bahasa Pemrograman**: Java
- **Framework GUI**: Swing
- **Design Pattern**:
  - **Singleton**: Digunakan untuk mengelola instance `TaskManager` yang bertanggung jawab atas data tugas.
  - **Observer**: Digunakan untuk menjaga sinkronisasi antara data tugas dan antarmuka pengguna (`TaskGUI`).

---

## Cara Menjalankan
1. Pastikan Anda memiliki **JDK (Java Development Kit)** yang terinstal di komputer Anda.
2. Clone repositori ini:
   ```bash
   git clone https://github.com/DepieroAgil/TaskEase.git
3. Masuk ke direktori proyek :
   ```bash
   cd TaskEase

4. Kompilasi semua file :
   ```bash
   javac -encoding UTF-8 *.java

6. Jalankan aplikasi :
  ```bash
  java SplashScreen
