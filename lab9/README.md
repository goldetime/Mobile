a) TextMessage илгээх болон хүлээн авдаг програм бич.

b) TelephonyManager -ийг ашиглан дараах төлөвүүдийн өөрчлөлтийг тухайн
   өөрчлөлтийн мэдээллийг хугацаатай нь phonestate.txt гэсэн файлд бич.
   
   * LISTEN_CALL_FORWARDING_INDICATOR
   * LISTEN_CALL_STATE
   * LISTEN_CELL_INFO 
   * LISTEN_CELL_LOCATION
   * LISTEN_DATA_ACTIVITY
   * LISTEN_SERVICE_STATE
   
c) SIM -тэй холбоотой дараах мэдээллийн SIMInfo.txt файлд бич.

   * Phone Number
   * Country ISO
   * Operator code
   * Operator name
   * Sim serial
   
d) Төхөөрөмжийн төлөвийн дараах өөрчлөлтүүдийг deviceInfo.txt файлд бич.

   * Системийн цагны минут 5-д хуваагдах үед тухайн цагийн мэдээллийг дараах байдлаар 
       /Time: тухайн цагийн мэдээлэл/
   * Системийн цаг өөрчлөгдөхөд /TimeChanged: тухайн цагийн мэдээлэл/
   * Төхөөрөмж асаж дуусахад: /BootCompleted тухайн цагийн мэдээлэл/
   * Төхөөрөмжийг цэнэглэж эхлэхэд /PowerConnected: тухайн цагийн мэдээлэл/
   * Төхөөрөмжийг цэнэглэгчээс салгахад /Powerdisconnected: Тухайн цагийн мэдээлэл, 
       хэдэн минут цэнэглэсэн талаарх мэдээлэл, хэдэн хувьтай цэнэглэгдсэнийг тус бүр log (l.txt) файлд бич./

   * Баттерейны хувь өөрчлөгдөхөд /Battery_Changed: тухайн цагийн мэдээлэл, боломжит мэдээлэл/
