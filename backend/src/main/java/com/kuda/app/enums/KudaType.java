package com.kuda.app.enums;


public enum KudaType {

						THANKS("thanks"),
						FEEDBACKS("feedbacks"),

	;

	private KudaType(String name) {

		this.name = name;
	}

	private String name;

	public static KudaType fromName(String name) {

		for (KudaType item : KudaType.values()){
			if (item.getName().equalsIgnoreCase(name)){
				return item;
			}
		}
		return null;
	}

	public String getName() {

		return name;
	}
}
