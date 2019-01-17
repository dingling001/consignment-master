package com.bootdo.system.domain;

import java.io.Serializable;



/**
 * 
 * 
 * @author Mr.liang
 * @email 21670230@qq.com
 * @date 2018-10-11 16:07:08
 */
public class CityDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer cityId;
	//
	private String cityName;
	//城市编号
	private String cityNumber;
	//是否是省  0 是  1不是
	private String cityProvince;
	//是否是 市  0是 1不是
	private String cityCountry;
	//是否是区
	private String cityArea;
	//上级编号
	private String citySuper;

	/**
	 * 设置：
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	/**
	 * 获取：
	 */
	public Integer getCityId() {
		return cityId;
	}
	/**
	 * 设置：
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * 获取：
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * 设置：城市编号
	 */
	public void setCityNumber(String cityNumber) {
		this.cityNumber = cityNumber;
	}
	/**
	 * 获取：城市编号
	 */
	public String getCityNumber() {
		return cityNumber;
	}
	/**
	 * 设置：是否是省  0 是  1不是
	 */
	public void setCityProvince(String cityProvince) {
		this.cityProvince = cityProvince;
	}
	/**
	 * 获取：是否是省  0 是  1不是
	 */
	public String getCityProvince() {
		return cityProvince;
	}
	/**
	 * 设置：是否是 市  0是 1不是
	 */
	public void setCityCountry(String cityCountry) {
		this.cityCountry = cityCountry;
	}
	/**
	 * 获取：是否是 市  0是 1不是
	 */
	public String getCityCountry() {
		return cityCountry;
	}
	/**
	 * 设置：是否是区
	 */
	public void setCityArea(String cityArea) {
		this.cityArea = cityArea;
	}
	/**
	 * 获取：是否是区
	 */
	public String getCityArea() {
		return cityArea;
	}
	/**
	 * 设置：上级编号
	 */
	public void setCitySuper(String citySuper) {
		this.citySuper = citySuper;
	}
	/**
	 * 获取：上级编号
	 */
	public String getCitySuper() {
		return citySuper;
	}
}
