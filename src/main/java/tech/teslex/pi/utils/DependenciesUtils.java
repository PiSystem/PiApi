package tech.teslex.pi.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tech.teslex.mpes.ll.LibLoader;
import tech.teslex.pi.Main;
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

	public static List<PiDependency> loadFromFile(File file) throws IOException {
		Gson gson = new Gson();
		BufferedReader bf = new BufferedReader(new FileReader(file));
		return gson.fromJson(bf, new TypeToken<List<PiDependency>>() {
		}.getType());
	}

	public static void initOne(PiDependency dependency) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (dependency.getType() == PiDType.PLUGIN) {
			File pluginFile = new File(Main.it.getServer().getPluginPath() + File.separator);

			Main.log.notice("Downloading " + dependency.getName() + " [Plugin]");
			pluginFile = dependency.getFileName() != null ?
					HTTPUtils.download(dependency.getUrl(), pluginFile.toPath(), dependency.getFileName()) :
					HTTPUtils.download(dependency.getUrl(), pluginFile.toPath());

			Main.it.getServer().getPluginManager().loadPlugin(pluginFile);

		} else {
			File libFile = new File(Main.it.getServer().getDataPath() + File.separator + "libraries");

			Main.log.notice("Downloading " + dependency.getName() + " [Library]");
			libFile = dependency.getFileName() != null ?
					HTTPUtils.download(dependency.getUrl(), libFile.toPath(), dependency.getFileName()) :
					HTTPUtils.download(dependency.getUrl(), libFile.toPath());

			File finalLibFile = libFile;
			LibLoader.loadLib(() -> finalLibFile);
		}

	}

	public static void initAll() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
		for (PiDependency dependency : PiApi.getDependencies()) {

			if (dependency.getType() == PiDType.PLUGIN && Main.it.getServer().getPluginManager().getPlugin(dependency.getName()) != null)
				continue;
			else if (dependency.getType() == PiDType.LIBRARY) {
				String fileName = dependency.getFileName() != null ? dependency.getFileName() : dependency.getUrl().substring(dependency.getUrl().lastIndexOf('/') + 1, dependency.getUrl().length());

				Main.log.info(dependency.getFileName());

				File x = new File(new File(Main.it.getServer().getDataPath() + File.separator + "libraries").getAbsoluteFile() + File.separator + fileName);

				if (x.exists())
					continue;
			}

			initOne(dependency);
		}
	}
}
