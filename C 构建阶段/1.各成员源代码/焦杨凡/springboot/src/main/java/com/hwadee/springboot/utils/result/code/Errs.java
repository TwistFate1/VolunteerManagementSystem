package com.hwadee.springboot.utils.result.code;

import java.util.HashMap;
import java.util.Map;

public class Errs {
	public final static Map<Integer, String> errorIndex = new HashMap<Integer, String>();

	/**
	 * 添加错误码
	 * @param errorCode
	 * @param errorMessage
	 * @return
	 */
	public static int put(Integer errorCode, String errorMessage) {
		errorIndex.put(errorCode, errorMessage);
		return errorCode;
	}

	/**
	 * 获取错误信息
	 * @param errorCode
	 * @return
	 */
	public static String err(Integer errorCode) {
		return errorIndex.get(errorCode);
	}
}
