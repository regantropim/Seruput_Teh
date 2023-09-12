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


## Feature Substitute

Di project ini ada beberapa batasan - batasan fitur yang dapat digunakan, hal ini terjadi karena fitur
tersebut tidak diajarkan pada mata kuliah ini. Berikut adalah daftar fitur yang tidak dapat digunakan:

### Enum

Enum merupakan sebuah tipe data yang dapat menyimpan beberapa nilai yang telah ditentukan sebelumnya.

#### Contoh

```java
public enum Gender {
    MALE, FEMALE
}
```

```java
public class Person {
    
    private final String name;
    private final Gender gender;
    
    private boolean isMale() {
        return gender == Gender.MALE;
    }
    
}
```

Di Java, enum dapat digunakan untuk membuat sebuah class yang hanya memiliki beberapa nilai yang telah ditentukan
sebelumnya. Enum dapat digunakan untuk menggantikan penggunaan `static final` variable.

Tetapi karena enum tidak diajarkan pada mata kuliah ini, maka saya tidak dapat menggunakan enum untuk menggantikan
penggunaan `static final` variable.

### Enum Substitute

Untuk menggantikan penggunaan enum, saya menggunakan `static final` variable.

#### Contoh

```java
public class Gender {

    public static final Gender MALE = new Gender("Male");
    public static final Gender FEMALE = new Gender("Female");
    
    private final String name;

    public Gender(String name) {
        this.name = name;
    }
    
    private String name() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
```

#### Apa itu `toString()`?

`toString()` merupakan sebuah method yang terdapat pada class `Object`. Method ini digunakan untuk mengubah
bentuk dari sebuah object menjadi sebuah string. Method ini biasanya digunakan untuk menampilkan object ke layar.

```java
public class Main {

    public static void main(String[] args) {
        Gender gender = Gender.FEMALE;
        System.out.println(gender);
    }
    
}
```

**Output**

```shell
$ java Main

Female
```

## Lambda

Lambda merupakan sebuah fitur yang memungkinkan kita untuk membuat sebuah fungsi tanpa harus membuat sebuah class.
Dan jika anda merupakan seorang pemula, anda mungkin masih asing dengan penggunaan lambda. Oleh karena itu berikut
adalah contoh bagaimana cara menggunakan lambda.

#### Contoh

```java
public class Main {

    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("Hello World");
        runnable.run();
    }
    
}
```

Pada contoh diatas, kita membuat sebuah fungsi tanpa harus membuat sebuah class. Fungsi tersebut akan mencetak
`Hello World` ke layar.

### Lambda Substitute

Untuk menggantikan penggunaan lambda, saya menggunakan anonymous class.

#### Contoh

```java
public class Main {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World");
            }
        };
        runnable.run();
    }
    
}
```

Jika diperhatikan, kode di atas memiliki bentuk yang sama dengan kode lambda. Tetapi kode diatas menggunakan anonymous
class, sedangkan kode lambda tidak menggunakan anonymous class. Dengan menggunakan _lambda_ kita akan melihat bahwa
kode yang kita tulis akan lebih pendek dan lebih mudah dibaca, tetapi sayangnya karena kita tidak diajarkan tentang
lambda, maka kita tidak dapat menggunakan lambda.