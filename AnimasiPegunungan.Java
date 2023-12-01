import processing.core.PApplet;

public class Mount extends PApplet {

    public static void main(String[] args) {
        Mount zahqil = new Mount();
        PApplet.runSketch(new String[]{"Gunung dan Danau"}, zahqil);
    }

    // Varibel-variabel kapal
    float angle = 0; // Sudut
    float amplitude = 30; // Jarak maksimum pergerakan dari titik pusat
    float shipX, shipY; // Posisi horizontal dan vertikal kapal
    float time = 0; // Perubahan seiring waktu

    // Variabel-variabel untuk awan
    float cloud1X, cloud1Y; // Variabel horizontal dan vertikal awan 1
    float cloud2X, cloud2Y; // Variabel horizontal dan vertikal awan 2

    float roadWidth = 200; // Lebar jalan
    float roadHeight = 35; // Tinggi jalan
    float roadSpeed = 2; // Kecepatan pergerakan jalan
    float roadX = 0; // Posisi awal jalan

    Car[] cars; // Array untuk menyimpan mobil

    public void setup(){
        size(800, 600); // Mengatur ukuran canvas menjadi 800 piksel lebar dan 600 piksel tinggi
        background(126, 186, 246); // Mengatur warna latar belakang canvas
        noStroke(); // Menonaktifkan garis tepi (stroke)

        // Inisialisasi posisi awan
        // Menetapkan posisi awan 1
        cloud1X = 325;
        cloud1Y = 100;
        // Menetapkan posisi awan 2
        cloud2X = 545;
        cloud2Y = 150;

        // Inisialisasi array mobil
        cars = new Car[3]; // Array dari objek Car dengan panjang 3
        // Menginisialisasi setiap elemen array dengan objek Car dan posisi awal yang berbeda
        cars[0] = new Car(10, height - roadHeight - 10);
        cars[1] = new Car(300, height - roadHeight - 20);
        cars[2] = new Car(600, height - roadHeight - 15);
    }

    public void settings() {
        size(800, 600);
    }

    public void draw() {
        drawSky(); //menggambar latar belakang langit

        shipX = width / 2 + amplitude * sin(angle); // Menghitung posisi horizontal kapal menggunakan gerakan sinusoidal
        angle += 0.02; // Menambahkan nilai sudut untuk animasi gerakan kapal

        // Update waktu (range dari 0 pagi hingga 1 malam)
        time = (sin(angle) + 1) / 2;

        // Menghitung posisi kapal berdasarkan koordinat lingkaran
        float radius = 150;
        shipX = width / 2 + radius * cos(angle);
        shipY = height / 2 + radius * sin(angle);

        // Menggerakkan awan
        moveClouds();

        // Memanggil fungsi-fungsi untuk menggambar elemen-elemen dalam pemandangan
        drawLake();
        drawSun();
        drawMountains();
        drawShip();
        drawTrees();
        drawShadow();
        drawCloud();

        // Iterasi / perulangan melalui setiap objek Car dalam array cars
        for (Car car : cars) {
            car.move(); // Memanggil metode move() untuk menggerakkan mobil
            car.display(); // Memanggil metode display() untuk menampilkan mobil
        }

        shipX = width / 2 + amplitude * sin(angle); // Menghitung posisi horizontal kapal
        angle += 0.0001; // Menambahkan nilai sudut untuk animasi gerakan kapal

        angle += 0.0001; // Menambahkan nilai sudut untuk menghitung waktu
        time = (sin(angle) + 1) / 2;
    }

    // Metode untuk menggambar dan menampilkan elemen-elemen dataran
    public void drawLake() {
        // Rumput
        fill(98, 148, 96); // Warna hijau
        rect(0, height / 2, width, height / 2);

        // Jalan
        fill(64); // Warna abu-abu tua
        triangle(width / 2, height / 2, 500, height, width, height);

        // Air danau
        fill(65, 105, 225); // Warna biru muda
        ellipse(width / 2, height / 2, 500, 300);

        // Jalan tambahan berbentuk persegi panjang
        fill(64); // Warna abu-abu tua
        rect(0, height - roadHeight, width, roadHeight);
    }

    // Metode untuk menggambar dan menampilkan matahari
    public void drawSun() {
        if (time < 0.1 || time > 0.9) {
            fill(255, 255, 0); // Warna kuning
            ellipse(130, 90, 55, 55);
        }
    }

    // Metode untuk menggambar dan menampilkan pegunungan
    public void drawMountains() {
        // Gunung pertama
        fill(64); // Warna abu-abu tua
        triangle(100, height / 2, 400, height / 2 - 300, 700, height / 2);

        // Gunung kedua
        fill(96); // Warna abu-abu
        triangle(300, height / 2, 600, height / 2 - 400, 900, height / 2);
    }

    // Metode untuk menggambar dan menampilkan perahu
    public void drawShip(){
        // Bayangan perahu
        fill(52, 82, 179); // Warna biru tua
        ellipse(shipX + 15, 393, 42, 20);

        // Cerobong perahu
        fill(255, 0, 0); // Warna merah muda
        rect(shipX, 360, 30, 20);
        rect(shipX + 5, 350, 10, 10);

        // Perahu
        fill(255); // Warna putih
        rect(shipX, 370, 30, 20);
        triangle(shipX, 370, shipX - 12, 370, shipX + 1, 390);
        triangle(shipX + 30, 370, shipX + 42, 370, shipX + 30, 390);
    }

    // Metode untuk menggambar dan menampilkan beberapa pohon
    public void drawTrees() {
        // Posisi masing-masing pohon
        drawTree(150, height / 2 + 50);
        drawTree(250, height / 2 + 100);
        drawTree(350, height / 2 + 80);
        drawTree(640, height / 2 + 30);
    }

    // Metode untuk menggambar dan menampilkan pohon
    public void drawTree(float x, float y) {
        //batang pohon
        fill(139, 69, 19); // Warna coklat
        rect(x, y, 20, 100);

        // Daun
        fill(75, 166, 72); // Warna hijau tua
        int numLeaves = 30;
        float leafSize = 30;

        // Menggerakkan daun
        for (int i = 0; i < numLeaves; i++) {
            float offsetX = random(-20, 20);
            float offsetY = random(-50, 0);

            float leafX = x + 10 + offsetX; // Posisi X dedaunan
            float leafY = y + offsetY; // Posisi Y dedaunan

            ellipse(leafX, leafY, leafSize, leafSize);
        }
    }

    // Metode untuk menggambar dan menampilkan bayangan
    public void drawShadow(){
        // Bayangan pohon
        fill(75, 114, 74);
        triangle(180, 520, 150, 450, 170, 450);
        triangle(280, 570, 250, 500, 270, 500);
        triangle(380, 550, 350, 480, 370, 480);
        triangle(670, 500, 640, 430, 660, 430);
    }

    // Menggerakkan posisi awan secara horizontal
    public void moveClouds() {
        double cloudSpeed = 0.5;  // Kecepatan awan
        cloud1X += cloudSpeed; // Menggerakkan posisi awan pertama ke kanan sejauh cloudSpeed
        cloud2X += cloudSpeed; // Menggerakkan posisi awan kedua ke kanan sejauh cloudSpeed

        // Kembali awan ke kiri setelah mencapai batas kanan layar
        if (cloud1X > width + 75) { // Memeriksa apakah posisi awan pertama (cloud1X) melebihi batas kanan layar
            cloud1X = -75; // Jika iya, atur kembali posisi awan pertama ke posisi awal di luar batas kiri layar
        }
        if (cloud2X > width + 75) { // Memeriksa apakah posisi awan kedua (cloud2X) melebihi batas kanan layar
            cloud2X = -75; // Jika iya, atur kembali posisi awan kedua ke posisi awal di luar batas kiri layar
        }
    }

    // Menampilkan awan
    public void drawCloud(){
        // Awan 1
        fill(255);
        ellipse(cloud1X, cloud1Y, 150, 75);
        ellipse(cloud1X - 23, cloud1Y - 13, 72, 72);
        ellipse(cloud1X + 25, cloud1Y - 13, 91, 80);
        ellipse(cloud1X - 5, cloud1Y + 3, 87, 87);

        // Awan 2
        ellipse(cloud2X, cloud2Y, 150, 75);
        ellipse(cloud2X - 23, cloud2Y - 13, 72, 72);
        ellipse(cloud2X + 25, cloud2Y - 13, 91, 80);
        ellipse(cloud2X - 5, cloud2Y + 3, 87, 87);
    }

    // Menampilkan langit
    public void drawSky() {
        // Interpolasi warna langit berdasarkan waktu
        int morningColor = color(126, 186, 246); // Warna pagi
        int afternoonColor = color(135, 206, 250); // Warna siang
        int nightColor = color(25, 25, 112); // Warna malam

        int currentColor; // Variabel untuk menyimpan nilai warna saat ini

        // Memeriksa apakah waktu (time) berada di luar rentang siang hari (0.1 hingga 0.9)
        if (time < 0.1 || time > 0.9) { // Jika iya, mencampur warna dari pagi hingga siang hari
            currentColor = lerpColor(morningColor, afternoonColor, time);
        } else {
            currentColor = nightColor; // Jika tidak, menggunakan warna malam sebagai warna latar belakang
        }

        background(currentColor);
        // Mengatur warna latar belakang canvas berdasarkan hasil pengecekan waktu
    }

    // Kelas yang merepresentasikan objek mobil
    class Car {
        float carX; // Posisi horizontal mobil
        float carY; // Posisi vertikal mobil
        float carWidth = 40; // Lebar mobil
        float carHeight = 20; // Tinggi mobil
        float carSpeed = 1; // Kecepatan pergerakan mobil

        // Inisialisasi nilai posisi awal mobil
        Car(float x, float y) {
            carX = x;
            carY = y;
        }

        // Metode untuk menggerakkan mobil
        void move() {
            carX += carSpeed; // Menambahkan nilai posisi horizontal mobil dengan kecepatan pergerakan
            if (carX > width) {  // Memeriksa apakah mobil telah melewati batas kanan layar
                carX = -carWidth; // Jika iya, atur kembali posisi mobil ke luar batas kiri layar
            }
        }

        // Metode untuk menampilkan gambaran visual dari mobil
        void display() {
            // Badan mobil
            fill(255, 0, 0); // Warna merah
            rect(carX, carY, carWidth, carHeight); // Persegi panjang sebagai badan mobil

            // Roda mobil
            fill(0); // Warna hitam
            ellipse(carX + 10, carY + carHeight + 5, 10, 10); // Roda kiri
            ellipse(carX + carWidth - 10, carY + carHeight + 5, 10, 10); // Roda kanan
        }
    }
}
