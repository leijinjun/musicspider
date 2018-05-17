package cn.person.musicspider.enums;

import cn.person.musicspider.core.EnumType;

public enum RelationType implements EnumType{

	Song(1,"歌曲"),
	Singer(2,"歌手"),
	Alubum(3,"专辑");
	
	RelationType(int code,String text) {
		this.code=code;
		this.text= text;
	}
	
	private int code;
	private String text;
	
	@Override
	public int getCode() {
		return code;
	}



	@Override
	public String getText() {
		return text;
	}
	
	public RelationType getEnum(int code) {
		RelationType[] relationTypes = RelationType.values();
		for (RelationType rt : relationTypes) {
			if(code==rt.getCode()){
				return rt;
			}
		}
		throw new IllegalArgumentException("Cannot convert " + code + " to RelationType");
	}

}
