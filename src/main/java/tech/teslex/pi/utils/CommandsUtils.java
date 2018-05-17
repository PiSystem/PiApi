package tech.teslex.pi.utils;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import tech.teslex.pi.Main;
import tech.teslex.pi.PiApi;
import tech.teslex.pi.annotations.PiCommand;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CommandsUtils {

	public static void initAll() {
		List<Class> cmds = PiApi.getCommands();

		for (Class cmd : cmds) {
			init(cmd);
		}
	}

	public static void init(Class clazz) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(PiCommand.class)) {
				PiCommand command = method.getAnnotation(PiCommand.class);

				Command command1 = new Command(command.command(), command.description(), command.usage(), command.aliases()) {
					@Override
					public boolean execute(CommandSender commandSender, String s, String[] strings) {

						for (String permission : command.permissions())
							this.setPermission(permission);

						if (!this.testPermission(commandSender))
							return true;

						try {
							method.invoke(clazz.newInstance(), commandSender, s, strings);
						} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
							e.printStackTrace();
						}
						return false;
					}
				};

				Main.it.getServer().getCommandMap().register(command.command(), command1);
			}
		}
	}
}
