Дараах даалгавруудыг хийж гүйцэтгэ.

1. Үндсэн activity нь Хоёр fragment-ийг харуулна. 
Эхний fragment нь:
* Үндсэн мэдээллийг оруулах fragment: овог, нэр, нас, хүйс, мэргэжил(Багш, оюутан гэсэн 2 сонголтоос тогтоно. 
  Хэрэв багш сонгосон үед багшийн мэдээллийг оруулах 2 дахь fragment гарч ирнэ. 
  Харин оюутанг сонгосн үед оюутны нэмэлт мэдээллийг оруулах fragment гарч ирдэг байна.)

Хоёр дахь fragment:
* Багшийн нэмэлт мэдээлэлийг оруулах fragment: үндсэн мэдээлэл эхний fragment-д оруулсан мэдээллийг бүгдийг нь 
  нэг ТеxtView-д харуулна. мөн зэрэг цол, заадаг хичээлүүд гэсэн нэмэлт мэдээлийг оруулах боломжтой байна.

* Оюутны нэмэлт мэдээлэлийг оруулах fragment: үндсэн мэдээлэл эхний fragment-д оруулсан мэдээллийг бүгдийг нь
  нэг ТеxtView-д харуулна. мөн Сургууль, анги, курс, голч дүн гэсэн нэмэлт мэдээллүүдийг оруулах боломтой байна.

2. Төхөөрөмжийг босгоход хоёр fragment-ийг үндсэн мэдээлэл нь дээрээ, нэмэлт мэдээллэл нь доор нь байна, харин төхөөрөмжийг 
   хэвтгээр болгоход үндсэн мэдээлэл баруун талд нь 40 хувьд нь нэмэлт мэдээлэл зүүн талд нь 60 хувьд нь байна.