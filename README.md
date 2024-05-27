# SnakeGame
A classic Snake game with a graphical user interface in Java. This project includes features such as resizable gamepad, game restart function, and score tracking.
Özellikler
Klasik Yılan oyunu mekaniği
Akıcı grafikler ve animasyon
Yeniden boyutlandırılabilir oyun paneli
Oyunu yeniden oynatmak için yeniden başlat düğmesi
Skor takibi ve oyun bittikten sonra önceki skoru gösterme
Özelleştirilebilir oyun hızı ve boyutu
Kurulum
Bu uygulamayı klonlamak ve çalıştırmak için bilgisayarınızda Git ve Java JDK kurulu olmalıdır. Komut satırınızdan:

bash
Kodu kopyala
# Bu depoyu klonlayın
$ git clone https://github.com/your-username/snake-game.git

# Depoya gidin
$ cd snake-game

# Java dosyalarını derleyin
$ javac -d bin src/snakegame/*.java

# Oyunu çalıştırın
$ java -cp bin snakegame.Game
Nasıl Oynanır
Yılanın yönünü kontrol etmek için ok tuşlarını kullanın.
Amacınız mümkün olduğunca çok elma yemektir. Yılan her elma yediğinde, boyu uzar.
Yılan duvarlara veya kendisine çarptığında oyun sona erer.
Oyun bittiğinde, skorunuzu görebilir ve "Yeniden Başlat" düğmesine tıklayarak oyunu yeniden başlatabilirsiniz.
Kod Yapısı
src/snakegame/Game.java: Oyun penceresini kuran ve oyunu başlatan ana sınıf.
src/snakegame/GamePanel.java: Tüm oyun mekaniğini, render işlemlerini ve kullanıcı girişlerini yöneten oyun paneli sınıfı.
src/snakegame/background.jpg: Oyunda kullanılan arka plan resmi.
Katkıda Bulunma
Fork it (https://github.com/your-username/snake-game/fork)
Özellik dalınızı oluşturun (git checkout -b feature/fooBar)
Değişikliklerinizi kaydedin (git commit -am 'Add some fooBar')
Dala gönderin (git push origin feature/fooBar)
Yeni bir Çekme İsteği (Pull Request) oluşturun
Lisans
MIT Lisansı altında dağıtılmıştır. Daha fazla bilgi için LICENSE dosyasına bakın.
