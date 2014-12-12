package model.state;

import java.io.Serializable;

import util.JSONable;

public class LevelIdentifier implements Serializable, JSONable{

	private static final long serialVersionUID = -4397687057831738116L;
	public String levelName;
	public String campaignName;

	public LevelIdentifier(String levelName, String campaignName) {
		this.levelName = levelName;
		this.campaignName = campaignName;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof LevelIdentifier)) {
			return false;
		} else {
			LevelIdentifier li = (LevelIdentifier) o;
			return (this.levelName.equals(li.levelName)) && (this.campaignName.equals(li.campaignName));
		}
	}

}
