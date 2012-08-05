package com.mnclimbingcoop

class PcscScanService {

	/** Watch card scanner for events */
	def doWatchReader() {
		// This will hold each line of output
		String line
		// this is the unix process to watch the reader
		def command = 'pcsc_scan -n'
		// run the command and fork it to the background
		println "Running process: ${proc.class}"
		def proc = command.execute()
		println "Process forked: ${proc.class}"

		// consume the output for the process
		def stdout = new StringBuffer()
		def stderr = new StringBuffer()
		proc.consumeProcessOutput(stdout, stderr)

		// wait for the process to run
		proc

		// read the input from the process
		while ( ( line = stdout.readLine() ) != null ) {
			println "read: ${line}"
		}
	}
}
