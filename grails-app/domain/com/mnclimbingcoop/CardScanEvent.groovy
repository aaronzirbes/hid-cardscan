package com.mnclimbingcoop

class CardScanEvent {

	/* 

Place Card Event
================
Sat Aug  4 21:29:22 2012
Reader 0: OMNIKEY CardMan 5x21 CLi (OKCM0072605121102420745768209684) 00 00
  Card state: Card inserted, 
  ATR: 3B 8F 80 01 80 4F 0C A0 00 00 03 06 0A 00 18 00 00 00 00 7A

Remove Card Event
=================
Sat Aug  4 21:29:26 2012
Reader 0: OMNIKEY CardMan 5x21 CLi (OKCM0072605121102420745768209684) 00 00
  Card state: Card removed, 

	*/

	Date eventDate = new Date()
	Integer readerId
	String readerDescription
	String readerSerial
	String readerAddress
	CardState cardState
	String cardAtr

	String toString() {
		"${cardState} [${cardAtr}] on ${eventDate}"
	}
}
