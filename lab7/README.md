# http://openweathermap.org/api -г ашиглан өгсөн хотын цаг агаарын мэдээг дараах байдлаар харуул

a. Тухайн өдрөөс хойш 5 хоногийн цаг агаарын мэдээллийг /xml parsing/ хийж list ашиглан харуулна.

b. Одоогийн цаг агаарын таван хотын мэдээг үзүүл /json parsing/ хийнэ.
   Хотуудын цаг агаарын мэдээллийг нэг нэгээр нь list -рүү нэмнэ. (Бүгдийг нь нэг хийвэл тооцохгүй)

c. Тухайн өдрийн цаг агаарын мэдээг 3 цагийн интервалтай үзүүлнэ /json parsing/ хийнэ.

Example (API): 
* https://samples.openweathermap.org/data/2.5/forecast?id=524901&appid=b1b15e88fa797225412429c1c50c122a1

* https://openweathermap.org/forecast5

* https://openweathermap.org/appid

* 599d244ed45b1e570b5633224950ca89 forecast
* d2d4cc4d7ed3d6940c856916acce29c9 default

curl "https://api.openweathermap.org/data/2.5/weather?q=Ulaanbaatar&appid=d2d4cc4d7ed3d6940c856916acce29c9&units=metric"
curl "https://api.openweathermap.org/data/2.5/weather?q=Darhan&appid=d2d4cc4d7ed3d6940c856916acce29c9&units=metric"

https://api.openweathermap.org/data/2.5/
