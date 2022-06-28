package com.kuda.app.dto.response;


public class SettingInfo {

	private Long id;
	private String key;
	private String tag;
	private String value;

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getKey() {

		return key;
	}

	public void setKey(String key) {

		this.key = key;
	}

	public String getTag() {

		return tag;
	}

	public void setTag(String tag) {

		this.tag = tag;
	}

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}

	@Override
	public String toString() {

		return "SettingInfo [id=" + id + ", key=" + key + ", tag=" + tag + ", value=" + value + "]";
	}
}
