namespace java models

typedef i32 int // We can use typedef to get pretty names for the types we are using

struct TPublicationAVG {
  1: required string city;
  2: required double temp_avg;
  3: required double rain_avg;
  4: required double wind_avg;
  5: required int count;
}