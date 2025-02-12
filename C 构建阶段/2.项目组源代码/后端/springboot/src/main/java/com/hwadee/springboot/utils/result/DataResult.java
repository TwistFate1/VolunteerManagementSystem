package com.hwadee.springboot.utils.result;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hwadee.springboot.utils.VerifyUtil;
import com.hwadee.springboot.utils.result.code.Code;
import com.hwadee.springboot.utils.result.code.Errs;

/**
 * 数据返回对象
 * @author xiaohongliang
 * 用户处理逻辑返回数据使用
 */
public class DataResult<E> implements Code {

	private boolean 	stat = false;
	private String 		message = Errs.err(ERROR);
	private E 			datas = null;
	private Integer		code = ERROR;
	private Long		total;




	public void setcode(Integer code) {
		this.code = code;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public DataResult() {
	}

	public DataResult(boolean stat) {
		this.setStat(stat);
	}

	public DataResult(boolean stat, String message) {
		this.setStat(stat).setMessage(message);
	}

	public DataResult(boolean stat, String message, E datas) {
		this.setStat(stat).setMessage(message).setDatas(datas);
	}

	/**
	 * 是否成功
	 * @return
	 */
	public boolean isStat() {
		return stat;
	}

	/**
	 * 是否不成功
	 * @return
	 */
	public boolean isStatErr() {
		return !stat;
	}

	/**
	 * 是否成功
	 * @return
	 */
	public DataResult<E> setStat(boolean stat) {
		this.stat = stat;
		if (stat) {
			this.code = SUCCESS;
		}
		return this;
	}

	/**
	 * 返回消息
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 返回消息
	 * @param message
	 */
	public DataResult<E> setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * 返回数据(通常在成功时返回数据)
	 * @return
	 */
	public E getDatas() {
		return datas;
	}

	/**
	 * 返回数据(通常在成功时返回数据)
	 * @return
	 */
	public DataResult<E> setDatas(E datas) {
		this.datas = datas;
		return this;
	}

	/**
	 * 返回错误码
	 * @return
	 */
	public Integer getcode() {
		return code;
	}

	/**
	 * 设置错误码
	 * @param code 错误码
	 * @return
	 */
	public DataResult<E> error(Integer code) {
		this.stat = false;
		this.code = code;
		this.message = Errs.err(code);
		return this;
	}

	/**
	 * 设置错误
	 * @param code 错误码
	 * @param message 内容
	 * @return
	 */
	public DataResult<E> error(Integer code, String message) {
		this.setStat(false).code = code;
		this.setMessage(message);
		return this;
	}
	/**
	 * 设置
	 * @param message 错误信息
	 * @return
	 */
	public DataResult<E> error(String message) {
		return this.error(ERROR).setStat(false).setMessage(message).setDatas(null);
	}

	/**
	 * 创建一个错误的实例
	 * @param
	 * @return
	 */
	public static <T> DataResult<T> err() {
		return new DataResult<T>().error(ERROR);
	}

	/**
	 * 从错误码创建实例
	 * @param code
	 * @return
	 */
	public static <T> DataResult<T> errByErrCode(int code) {
		return new DataResult<T>().error(code);
	}

	/**
	 * 一个正确的实例
	 * @param
	 * @return
	 */
	public static <T> DataResult<T> succ() {
		return new DataResult<T>().success();
	}

	/**
	 * 设置正确
	 * @param message 内容
	 * @param datas 内容
	 * @return
	 */
	public DataResult<E> success(String message, E datas) {
		if (VerifyUtil.isNull(message)) {
			message = Errs.err(SUCCESS);
		}
		return this.error(SUCCESS).setStat(true).setMessage(message).setDatas(datas);
	}

	/**
	 * 设置正确
	 * @param
	 * @return
	 */
	public DataResult<E> success() {
		return this.error(SUCCESS).setStat(true).setMessage(Errs.err(SUCCESS));
	}


	/**
	 * 从内容提示创建一个正确的实例并包含内容
	 * @param message
	 * @param datas
	 * @return
	 */
	public static <T> DataResult<T> successByMessage(String message, T datas) {
		return new DataResult<T>().success(message, datas);
	}

	/**
	 * 设置正确并带上返回数据(使用默认正确内容)
	 * @param datas
	 * @return
	 */
	public static <E> DataResult<E> successByDatas(E datas) {
		return successByMessage(Errs.err(SUCCESS), datas);
	}


	/**
	 *
	 * @Title: successByData
	 * @Description: 返回结果集
	 * @author Xiaohongliang
	 * @date 2021年5月19日
	 * @param @param data
	 * @return DataResult<JSONObject>    返回类型
	 * @throws
	 */
	public static DataResult<JSONObject> successByData(Object data) {
		String jsonString = JSONObject.toJSONStringWithDateFormat(data, "yyyy-MM-dd hh:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
		return successByMessage(Errs.err(SUCCESS), JSONObject.parseObject(jsonString));
	}
	/**
	 *
	 * @Title: successByData
	 * @Description: 返回结果集
	 * @author Xiaohongliang
	 * @date 2021年5月19日
	 * @param @param data
	 * @return DataResult<JSONObject>    返回类型
	 * @throws
	 */
	public static DataResult<JSONArray> successByDataArray(Object data) {
		String jsonString = JSONObject.toJSONStringWithDateFormat(data, "yyyy-MM-dd hh:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
		return successByMessage(Errs.err(SUCCESS), JSONArray.parseArray(jsonString));
	}

	/**
	 *
	 * @Title: successByTotalData
	 * @Description: 分页返回
	 * @author Xiaohongliang
	 * @date 2021年5月29日
	 * @param @param data
	 * @param @param total
	 * @param @return    参数
	 * @return DataResult<JSONArray>    返回类型
	 * @throws
	 */
	public static DataResult<JSONArray> successByTotalData(Object data,Long total) {
		String jsonString = JSONObject.toJSONStringWithDateFormat(data, "yyyy-MM-dd hh:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
		DataResult<JSONArray> dataResult = new DataResult<JSONArray>();
		dataResult.setDatas(JSONArray.parseArray(jsonString));
		dataResult.setTotal(total);
		dataResult.setMessage("成功");
		dataResult.setcode(Code.SUCCESS);
		return dataResult;
	}
}
