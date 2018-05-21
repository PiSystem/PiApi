package tech.teslex.pi.utils;

import cn.nukkit.plugin.Plugin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tech.teslex.mpes.ll.LibLoader;
import tech.teslex.pi.PiApi;
import tech.teslex.pi.dependencies.PiDType;
import tech.teslex.pi.dependencies.PiDependency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class DependenciesUtils {

	public static List<PiDependency> loadJsonFromFile(File file) throws IOException {
		Gson gson = new Gson();
		BufferedReader bf = new BufferedReader(new FileReader(file));
		return gson.fromJson(bf, new TypeToken<List<PiDependency>>() {
		}.getType());
	}

	public static void initOne(PiDependency dependency) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		if (dependency.getType() == PiDType.PLUGIN && PiApi.it.getServer().getPluginManager().getPlugin(dependency.getName()) != null)
			return;
		else if (dependency.getType() == PiDType.LIBRARY) {
			String fileName = dependency.getFileName() != null ? dependency.getFileName() : dependency.getUrl().substring(dependency.getUrl().lastIndexOf('/') + 1, dependency.getUrl().length());

			File x = new File(new File(PiApi.it.getServer().getDataPath() + File.separator + "libraries").getAbsoluteFile() + File.separator + fileName);

			if (x.exists())
				return;
		}

		if (dependency.getType() == PiDType.PLUGIN) {
			File pluginFile = new File(PiApi.it.getServer().getPluginPath() + File.separator);

			PiApi.log.notice("Downloading " + dependency.getName() + " [Plugin]");
			pluginFile = dependency.getFileName() != null ?
					HTTPUtils.download(dependency.getUrl(), pluginFile.toPath(), dependency.getFileName()) :
					HTTPUtils.download(dependency.getUrl(), pluginFile.toPath());

			Plugin loadedPlugin = PiApi.it.getServer().getPluginManager().loadPlugin(pluginFile);
			PiApi.it.getServer().getPluginManager().enablePlugin(loadedPlugin);
		} else {
			File libFile = new File(PiApi.it.getServer().getDataPath() + File.separator + "libraries");

			PiApi.log.notice("Downloading " + dependency.getName() + " [Library]");
			libFile = dependency.getFileName() != null ?
					HTTPUtils.download(dependency.getUrl(), libFile.toPath(), dependency.getFileName()) :
					HTTPUtils.download(dependency.getUrl(), libFile.toPath());

			File finalLibFile = libFile;
			LibLoader.loadLib(() -> finalLibFile);
		}

	}

	public static void initAll() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
		for (PiDependency dependency : PiApi.getDependencies()) {
			initOne(dependency);
		}
	}
}
