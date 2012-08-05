def command = 'pcsc_scan -n'
def proc = command.execute()
boolean initialized = false

proc.in.eachLine{ line -> 
	if (!initialized && line == 'Scanning present readers...') {
		initialized = true
	}
	println "\t${line}"
}
