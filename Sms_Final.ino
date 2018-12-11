#define DEBUG_ARRAY(a) {for (int index = 0; index < sizeof(a) / sizeof(a[0]); index++)    {Serial.print(a[index]); Serial.print('\t');} Serial.println();};
#include "SIM900.h"
#include <SoftwareSerial.h>
#include "sms.h"
#include <string.h>
SMSGSM sms;


boolean p = false;
boolean s = false;
boolean ss = false;
char *numero;
char *texto;
String str ;
String strs ;
int freeRam()
{
  extern int __heap_start, *__brkval;
  int v;
  return (int) &v - (__brkval == 0 ? (int) &__heap_start : (int) __brkval);
}


void setup()
{
  Serial.begin(9600);
  Serial.println("GSM Shield testing.");
  if (gsm.begin(4800)) {
    Serial.println("\nstatus=READY");
    Serial.print("  SRAM Libre: "); Serial.println(freeRam());
    Serial.println("Send");
  }
}


void loop()
{


}

void serialEvent()
{
  if (Serial.available())
  {



    if (str == NULL) {
      str = Serial.readStringUntil('#');
      Serial.println("Numero " + str);
      p = true;
      s = true;
    }
    if (p == true) {
      strs = Serial.readStringUntil('\n');
      Serial.println("Texto " + strs);
      ss = true;
    }

    if (s == true and ss == true)
    {
      numero = strdup(str.c_str());
      texto = strdup(strs.c_str());
      if (sms.SendSMS(numero , texto )) {
        Serial.println("\nSMS sent OK");
        Serial.print("SRAM Libre: ");
        Serial.println(freeRam());
        Serial.println("nextnum");
        str="";
        strs="";
      }



    }

  }
}
