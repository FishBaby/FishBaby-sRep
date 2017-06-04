package com.fishbaby.common.exception;

public class BeanParseException extends Exception{
	
	/**
	 * 用于BeanParse解析异常
	 * @param Msg
	 */
	public BeanParseException(String msg) {
		super(msg);
	}
	/**
	 * 用于BeanParse解析异常
	 * @param Msg
	 */
	public BeanParseException(String msg,Throwable supere) {
		super(msg);
		super.setStackTrace(supere.getStackTrace());
	}
	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
