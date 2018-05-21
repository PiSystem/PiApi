package tech.teslex.pi.utils;

import cn.nukkit.command.CommandSender;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.metaclass.MissingPropertyExceptionNoStack;
import tech.teslex.pi.PiApi;
import tech.teslex.pi.annotations.PiCommand;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class ScriptsUtils {

	private static Object eval(File fileScript) throws IOException {
		GroovyShell groovyShell = new GroovyShell(PiApi.class.getClassLoader());

		Script script = groovyShell.parse(fileScript);

		boolean isAutostart = false;

		try {
			isAutostart = (Boolean.valueOf(script.getProperty("autostart").toString()) ||
					script.getProperty("autostart") == null);
		} catch (MissingPropertyExceptionNoStack exceptionNoStack) {
			isAutostart = true;
		}

		if (isAutostart) {
			PiApi.log.warning("Executing script: " + fileScript.getName());
			return groovyShell.evaluate(fileScript);
		}

		return false;
	}

	private static Object eval(URI uriScript) throws IOException {
		GroovyShell groovyShell = new GroovyShell(PiApi.class.getClassLoader());

		Script script = groovyShell.parse(uriScript);

		boolean isAutostart = false;

		try {
			isAutostart = (Boolean.valueOf(script.getProperty("autostart").toString()) ||
					script.getProperty("autostart") == null);
		} catch (MissingPropertyExceptionNoStack exceptionNoStack) {
			isAutostart = true;
		}

		if (isAutostart) {
			PiApi.log.warning("Executing script: " + uriScript.getPath());
			return groovyShell.evaluate(uriScript);
		}

		return false;
	}

	private static Object eval(String textScript) {
		GroovyShell groovyShell = new GroovyShell(PiApi.class.getClassLoader());

		Script script = groovyShell.parse(textScript);

		boolean isAutostart = false;

		try {
			isAutostart = (Boolean.valueOf(script.getProperty("autostart").toString()) ||
					script.getProperty("autostart") == null);
		} catch (MissingPropertyExceptionNoStack exceptionNoStack) {
			isAutostart = true;
		}

		if (isAutostart) {
			PiApi.log.warning("Executing script from text");
			return groovyShell.evaluate(textScript);
		}

		return false;
	}

	public static void evalAllInDir(File dir) throws IOException {
		for (File script : Objects.requireNonNull(dir.listFiles(file -> file.getName().endsWith(".groovy")))) {
			eval(script);
		}
	}

	@PiCommand(command = "piexec", description = "Execute any groovy script", usage = "/piexec <path or url>", permissions = {"pi.api.exec"})
	public void piexec(CommandSender sender, String s, String[] args) throws URISyntaxException, IOException {
		if (args.length == 0) {
			sender.sendMessage(PiApi.it.getServer().getCommandMap().getCommand("piexec").getUsage());
			return;
		}

		if (args[0].equals("code")) {
			StringBuilder script = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				script.append(args[i]).append(" ");
			}
			Object response = eval(script.toString());
			if (response != null)
				sender.sendMessage("Result: " + response.toString());
		} else if (args[0].startsWith("http://") || args[0].startsWith("https://")) {
			Object response = eval(new URI(args[0]));
			if (response != null)
				sender.sendMessage("Result: " + response.toString());
		} else {
			Object response = eval(new File(args[0]));
			if (response != null)
				sender.sendMessage("Result: " + response.toString());
		}
	}
}
