
String RED      = "\u001B[31m"
String BLUE     = "\u001B[34m"
String PURPLE   = "\u001B[35m"
String RESET    = "\u001B[0m"

List<CardReader> readers = []

CardReader reader
String scanDateString
Date scanDate
String scanReader
CardState cardState
String cardAtr

def command = 'pcsc_scan -n'
def proc = command.execute()

boolean initialized = false
boolean loadedReaders = false

def resetReaderDate() {
	scanDateString = null
	scanDate = new Date()
	readerId = null
	cardState = null
	cardAtr = null
	reader = null
}

class CardState {
	String name
	String toString() { name }
}

class CardReader {

	String RESET    = "\u001B[0m"
	String BLUE     = "\u001B[34m"
	String PURPLE   = "\u001B[35m"

	Integer id
	String name
	String serial
	String address

	CardReader(String sourceString) {
		String readerString = sourceString.replace("${BLUE}", '')
		readerString = readerString.replace("${PURPLE}", '')
		readerString = readerString.replace("${RESET}", '')
		def readerInfo = readerString.split(/[:)(]/)
		if (readerInfo.size() == 4) {
			this.id = Integer.parseInt(readerInfo[0])
			this.name = readerInfo[1].trim()
			this.serial = readerInfo[2].trim()
			this.address = readerInfo[3].trim()
		}
	}

	String toString() {
		"${name}"
	}
}


proc.in.eachLine{ line -> 
	if (!initialized) {
 		if (line == "${RED}Scanning present readers...${RESET}" ) {
			initialized = true
		}
	} else if (!loadedReaders) {
		if (line == "") {
			loadedReaders = true
		} else {
			def r = new CardReader(line)
			readers.add(r)
			println "Listening on ${r}.${RESET}"
		}
	} else {
		if (line == "") {
			resetReaderDate()
		} else {
			if (line[0..6] == 'Reader ') {
				String readerData = line.replaceFirst('Reader ', '')
				reader = new CardReader(readerData)
			} else if (line[0..1]  == '  ') {
				// scan details
				if (line =~ /  Card state:/) {
					String cardStateString = line
						.replace(RED,'')
						.replace(RESET,'')
						.replace(/  Card state: Card /, '')
						.replace(', ','')
					cardState = new CardState(name: cardStateString)
					if (cardState.name == 'removed') {
						resetReaderDate()
					} else if (cardState.name != 'inserted') {
						println "STATE?: '${cardState}'"
					}
				} else if (line =~ /  ATR:/) {
					String atrCode = line.replace(/  ATR:/, '')
					cardAtr = atrCode.replace(PURPLE, '').replace(RESET, '')
					println "Card ${cardAtr} inserted to ${reader}."
				} else {
					println "WTF?: ${line}"
				}
			} else {
				scanDate = Date.parse('EEE MMM dd HH:mm:ss yyyy', line) ?: new Date()
			}
		}
	}
}
