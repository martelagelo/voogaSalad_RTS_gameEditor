package model.state;

import java.util.Set;

import model.state.gameelement.Attribute;
import model.state.gameelement.AttributeContainer;

public class HighScore {
	
	private AttributeContainer myAttributes;
	
	public HighScore (AttributeContainer ac) {
		myAttributes = ac;
	}
	
	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder();
		for(Attribute<Number> a : myAttributes.getNumericalAttributes()){
			sb.append(a);
		}
		for(Attribute<String> a : myAttributes.getTextualAttributes()){
			sb.append(a);
		}
		return sb.toString();
	}

}
