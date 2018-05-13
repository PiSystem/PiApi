package tech.teslex.pi;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import tech.teslex.pi.utils.DependenciesUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main extends PluginBase {

	public static Main it;
	public static PluginLogger log;

	@Override
	public void onEnable() {
		it = this;
		log = it.getLogger();

		saveResource("dependencies.json");

		try {
			File file = new File(getServer().getPluginPath() + File.separator + "PiApi" + File.separator + "dependencies.json");
			PiApi.dependencies = DependenciesUtils.loadFromFile(file);
			DependenciesUtils.initAll();
		} catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
