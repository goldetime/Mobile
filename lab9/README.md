a) TextMessage илгээх болон хүлээн авдаг програм бич.

   * Илгээх            [!!]           [OK]
   * Хүлээн авах                      [OK]

b) TelephonyManager -ийг ашиглан дараах төлөвүүдийн өөрчлөлтийг тухайн
   өөрчлөлтийн мэдээллийг хугацаатай нь phonestate.txt гэсэн файлд бич.
   
   * LISTEN_CALL_FORWARDING_INDICATOR [OK]
   * LISTEN_CALL_STATE                [OK]
   * LISTEN_CELL_INFO                 [!!] Requires API 17
   * LISTEN_CELL_LOCATION             [!!] Cell area
   * LISTEN_DATA_ACTIVITY             [OK]
   * LISTEN_SERVICE_STATE             [OK]
   
c) SIM -тэй холбоотой дараах мэдээллийн SIMInfo.txt файлд бич.

   * Phone Number                     [!!]
   * Country ISO                      [OK]
   * Operator code                    [OK]
   * Operator name                    [OK]
   * Sim serial                       [OK]
   
d) Төхөөрөмжийн төлөвийн дараах өөрчлөлтүүдийг deviceInfo.txt файлд бич.

   * Системийн цагны минут 5-д хуваагдах үед тухайн цагийн мэдээллийг /Time: тухайн цагийн мэдээлэл/             [??]
   * Системийн цаг өөрчлөгдөхөд /TimeChanged: тухайн цагийн мэдээлэл/                                            [OK]
   * Төхөөрөмж асаж дуусахад: /BootCompleted: тухайн цагийн мэдээлэл/                                            [!!]
   * Төхөөрөмжийг цэнэглэж эхлэхэд /PowerConnected: тухайн цагийн мэдээлэл/                                      [OK]
   
   * Төхөөрөмжийг цэнэглэгчээс салгахад /Powerdisconnected: Тухайн цагийн мэдээлэл,                              [OK]
     хэдэн минут цэнэглэсэн талаарх мэдээлэл,                                                  [??]
     хэдэн хувьтай цэнэглэгдсэнийг тус бүр log (battery_disconnected.txt) файлд бич./          [??]
   
   * Баттерейны хувь өөрчлөгдөхөд /Battery_Changed: тухайн цагийн мэдээлэл, боломжит мэдээлэл/                   [!!]
