package com

class TestController {

	def list() {
		log.info("Entered in list")
		render view: "list"
	}

}
