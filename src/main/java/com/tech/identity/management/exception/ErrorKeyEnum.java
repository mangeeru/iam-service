package com.tech.identity.management.exception;


public enum ErrorKeyEnum {
	BAD_REQUEST("Bad.Request"),
	DATABASE_ERROR("DB.Error"),
	INTERNAL_ERROR("Internal.Server.error"),
	INVALID_INPUT("invalid.input"),
	SERVICE_CALL_ERROR("serviceCall.error"),
	FIELD_MISSING_ERROR("Missing.Fields"),
	USER_NOT_FOUND("User Not Found"),
	USER_NOT_ACTIVE("User Not Active"),
	USER_DISABLED("User_Disabled");
	
	private String key;
	
	private ErrorKeyEnum(String key) {
		this.key=key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public static ErrorKeyEnum fromValue(String value) {
		for(ErrorKeyEnum errorKey:ErrorKeyEnum.values()) {
			if(errorKey.getKey().equals(value)) {
				return errorKey;
			}
		}
		return null;
	}

}
