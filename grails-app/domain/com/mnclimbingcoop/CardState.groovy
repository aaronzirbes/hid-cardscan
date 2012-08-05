package com.mnclimbingcoop

class CardState {

	String name

	String toString() { name }

	static CardState fromString(String state) {
		CardState.findByName(state)
	}
}
