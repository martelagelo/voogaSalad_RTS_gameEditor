package game_engine.gameRepresentation;

import java.util.List;

public class Game implements Describable {

	private String name;
	private String description;
	private List<Campaign> campaigns;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
