package tech.teslex.pi;

import tech.teslex.pi.dependencies.PiDependency;
import tech.teslex.pi.utils.CommandsUtils;

import java.util.ArrayList;
import java.util.List;

public class PiApi {

	public static final String VERSION = "0.0.1";

	private static List<PiDependency> dependencies = new ArrayList<>();

	public static void registerCommands(Class clazz) {
		CommandsUtils.init(clazz);
	}

	public static List<PiDependency> getDependencies() {
		return dependencies;
	}

	static void setDependencies(List<PiDependency> dependencies) {
		PiApi.dependencies = dependencies;
	}
}
