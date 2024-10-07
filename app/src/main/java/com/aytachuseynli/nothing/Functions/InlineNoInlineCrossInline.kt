package com.aytachuseynli.nothing.Functions



fun main() {
    selamVer() // Burada "Merhaba, nasılsın?" hemen yazılır.
    selamVer() // Tekrar "Merhaba, nasılsın?" yazılır.

    oyunOyna {
        println("Oyun oynanıyor!")
    }

    oyunYap {
        println("Oyun oynanıyor!")
        // return // Bunu yapamazsın, çünkü oyun bitmeden çıkış yok!
    }
}


/*
1. inline Nedir?
Düşün ki, arkadaşlarınla bir oyun oynuyorsun ve her seferinde "ben buradayım" dediğinde,
bunu kısaca "ben buradayım!" diye bağırarak yapıyorsun. Yani, her seferinde söyleniyor.
İşte inline de bu gibi bir şey.
Bir fonksiyonu her çağırdığında, onu sanki hemen orada yazıyormuşsun gibi yapar.
 */

inline fun selamVer() {
    println("Merhaba, nasılsın?")
}

/*
2. noinline Nedir?
Şimdi, düşün ki arkadaşlarınla bir oyun oynarken, bazen "sadece beni dinle" demek istiyorsun.
Ama bu sefer, sadece senin söylediklerini kaydetmek istiyorsun; başka kimseye söyleme!
İşte noinline burada devreye giriyor.
Yani, bir şey söylemek istiyorsun ama onu hemen orada yazmak istemiyorsun.
 */

inline fun oyunOyna(noinline eylem: () -> Unit) {
    println("Oyun başlıyor!")
    eylem() // Burada "sadece beni dinle" yazılır ama hemen yerleştirilmez.
    println("Oyun bitiyor!")
}

/*
3. crossinline Nedir?
Şimdi düşün ki, bir şey söylemek istiyorsun ama başka bir yerde oyun oynuyorsun ve oyun bitmeden çıkmak istemiyorsun.
İşte crossinline bu durumda senin yanında!
Oyun bittikten sonra "ben gidiyorum" demek istiyorsun ama bunu yapmana izin vermiyor.
 */

inline fun oyunYap(crossinline eylem: () -> Unit) {
    println("Oyun başlıyor!")

    val yeniOyun = Runnable {
        eylem() // Burada hemen çıkmak yok!
    }

    Thread(yeniOyun).start() // Yeni bir yerde oyun başlatıyoruz.

    println("Oyun bitiyor!")
}

/*
Özet
inline: Her seferinde hemen orada yazılmış gibi çalışır. Hızlıdır.
noinline: Hemen orada yazılmasını istemediğimiz durumlar için kullanılır.
crossinline: Başka bir yere gidebilmek için oyunun bitmesini beklememizi sağlar.
 */