package tech.teslex.pi;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import tech.teslex.pi.dependencies.PiDependency;
import tech.teslex.pi.utils.CommandsUtils;
import tech.teslex.pi.utils.DependenciesUtils;
import tech.teslex.pi.utils.ScriptsUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class PiApi extends PluginBase {

	public static final String VERSION = "0.0.2";
	public static PiApi it;
	public static PluginLogger log;
	private static List<PiDependency> dependencies = new ArrayList<>();

	public static void registerCommands(Class clazz) {
		CommandsUtils.init(clazz);
	}

	public static List<PiDependency> getDependencies() {
		return dependencies;
	}

	public static void requireDependency(PiDependency dependency) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
		dependencies.add(dependency);
		DependenciesUtils.initOne(dependency);
	}

	@Override
	public void onLoad() {
		it = this;
		log = it.getLogger();

		saveResource("dependencies.json");
		saveResource("config.yml");
	}

	@Override
	public void onEnable() {

		try {
			File file = new File(getServer().getPluginPath() + File.separator + "PiApi" + File.separator + "dependencies.json");
			PiApi.dependencies = (DependenciesUtils.loadJsonFromFile(file));
			DependenciesUtils.initAll();
		} catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		try {
			File scriptsDir = new File(getServer().getDataPath() + (File.separator + "scripts"));
			scriptsDir.mkdirs();
			ScriptsUtils.evalAllInDir(scriptsDir);
		} catch (Exception e) {
			e.printStackTrace();
		}

		registerCommands(ScriptsUtils.class);
	}
}
