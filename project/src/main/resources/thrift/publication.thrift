namespace java models

typedef i32 int // We can use typedef to get pretty names for the types we are using

struct TPublication {
  1: required int stationId;
  2: required string city;
  3: required int temp;
  4: required double rain;
  5: required int wind;
  6: required string direction;
  7: required string date;
}