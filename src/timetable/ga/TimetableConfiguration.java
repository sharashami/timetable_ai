package timetable.ga;

import org.jgap.impl.DefaultConfiguration;

public class TimetableConfiguration extends DefaultConfiguration {

	private Enviroment enviroment;
	public TimetableConfiguration(Enviroment enviroment) {
		super();
		this.enviroment = enviroment;
		
	}
	public Enviroment getEnviroment() {
		return enviroment;
	}
}
