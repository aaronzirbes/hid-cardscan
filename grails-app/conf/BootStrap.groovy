import com.mnclimbingcoop.CardState

class BootStrap {

	def executorService
	def pcscScanService

    def init = { servletContext ->
		def inserted = CardState.findOrCreateByName('inserted')
		def removed = CardState.findOrCreateByName('removed')

		runAsync {
			pcscScanService.doWatchReader()
		}
    }
}
