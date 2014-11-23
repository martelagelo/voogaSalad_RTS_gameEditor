package game_engine.gameRepresentation.conditions;
/**
 * A messy class designed to f
 * @author zach
 *
 */
public class TypeFinder {
	public static Class<?> UnEraseJavaGenerics(Class<?> translatingClass){
		if(translatingClass.equals(Double.class)){
			System.out.println("Double");
			return Double.class;
		}
		return null;
		
		
	}
}
