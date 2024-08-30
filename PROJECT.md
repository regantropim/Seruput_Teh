# Project

Setelah selesai membaca membaca [README.md](README.md), saya selanjutkan akan menjelaskan tentang project ini.

## Project Structure

```
id.seruput
├── app
├── core
```

Singkatnya project ini terdiri dari dua package utama yaitu `app` dan `core`. 
Package `app` berisi kode-kode yang berhubungan dengan aplikasi GUI, sedangkan package `core` berisi 
kode-kode yang berhubungan dengan logika aplikasi.

## Getter & Setter

Di project ini, saya menggunakan getter dan setter untuk mengakses field yang bersifat private. Tapi di project ini
kita tidak menggunakan `get...()` dan `set...(... .)` untuk mengakses field yang bersifat private, melainkan kita menggunakan method
seperti:

```java
public class Person {
    
    private String name;
    
    public String name() {
        return name;
    }
    
    public void name(String name) {
        this.name = name;
    }
    
}
```