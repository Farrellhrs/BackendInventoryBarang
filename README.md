# GudangKu Backend

GudangKu adalah aplikasi backend untuk mengelola data gudang. Aplikasi ini menggunakan **Spark Java** sebagai framework REST API, dengan struktur proyek modular yang memisahkan logika bisnis, rute, dan lapisan koneksi database.

---

## **Fitur Utama**
- REST API untuk autentikasi, manajemen produk, pengguna, dan pencatatan keluar/masuk barang.
- Struktur berbasis controller, service, dan repository.
- Koneksi database menggunakan JDBC.
- Modular dan mudah dikembangkan.

---

## **Persyaratan Sistem**
1. **Java Development Kit (JDK)** versi 11 atau lebih tinggi.
2. **Maven** untuk manajemen dependensi.
3. Database seperti **MySQL** (atau lainnya sesuai kebutuhan).

---

## **Struktur Direktori**

Berikut adalah struktur direktori proyek:

```
GudangKu/
├── src/
│   ├── main/
│   │   ├── java/com/pbo/warehouse/api/
│   │   │   ├── controllers/                  # Controller untuk endpoint API
│   │   │   ├── dto/                          # Data Transfer Objects (request/response)
│   │   │   │   ├── request/
│   │   │   │   ├── response/
│   │   │   │   └── ResponseBodyDto.java
│   │   │   ├── exceptions/                   # Custom exception handler
│   │   │   ├── jdbc/                         # Koneksi database
│   │   │   │   └── DatabaseConnection.java
│   │   │   ├── middleware/                   # Middleware (contoh: autentikasi)
│   │   │   ├── models/                       # Model data
│   │   │   ├── repositories/                 # Repository untuk query database
│   │   │   ├── routes/                       # Definisi rute API
│   │   │   ├── services/                     # Logika bisnis
│   │   │   ├── utils/                        # Fungsi utilitas
│   │   │   └── Main.java                     # Entry point aplikasi
│   │   └── resources/                        # Konfigurasi tambahan (opsional)
├── test/                                     # Unit test
├── target/                                   # File hasil build
├── .gitignore                                # File untuk mengabaikan direktori/file tertentu
├── pom.xml                                   # Konfigurasi Maven
└── README.md                                 # Dokumentasi proyek
```

---

## **Konfigurasi Database**

File koneksi database ada di:  
`src/main/java/com/pbo/warehouse/api/jdbc/DatabaseConnection.java`

## **Menjalankan Proyek**

### 1. **Install Dependensi**
Jalankan perintah berikut untuk mengunduh dan menginstal dependensi Maven:
```bash
mvn install
```

### 2. **Menjalankan Aplikasi**
Jalankan aplikasi menggunakan Maven:
```bash
mvn exec:java
```

Server akan berjalan di **http://localhost:8090**.

---

## **Membangun Proyek**
Untuk membuat file JAR:
```bash
mvn clean package
```

File JAR akan disimpan di direktori `target/`.

---

## **Menjalankan Build Produksi**
Jalankan file JAR hasil build:
```bash
java -jar target/nama-file.jar
```

---

## **Dependensi Utama**
Proyek ini menggunakan dependensi berikut:
- **Spark Java**: Framework minimalis untuk REST API.
- **JDBC**: Untuk koneksi ke database.
- **MySQL Connector**: Driver JDBC untuk MySQL.

---
