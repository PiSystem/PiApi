package tech.teslex.pi.utils;

import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.EventExecutor;
import cn.nukkit.utils.EventException;
import tech.teslex.pi.PiApi;
import tech.teslex.pi.annotations.PiOn;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventsUtils {

	@EventHandler
	public static void init(Class clazz) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(PiOn.class) && method.getParameterTypes().length == 1) {
				PiOn eventOn = method.getAnnotation(PiOn.class);

				PiApi.it.getServer().getPluginManager().registerEvent(
						(Class<? extends Event>) method.getParameterTypes()[0],
						new Listener() {
						}, eventOn.priority(),
						new CustomMethodEventExecutor(method, clazz),
						PiApi.it,
						eventOn.ignoreCancelled()
				);

			}
		}
	}
}

class CustomMethodEventExecutor implements EventExecutor {
	private final Method method;
	private final Class aClass;

	CustomMethodEventExecutor(Method method, Class aClass) {
		this.method = method;
		this.aClass = aClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Listener listener, Event event) throws EventException {
		try {
			Class<Event>[] params = (Class<Event>[]) method.getParameterTypes();
			for (Class<Event> param : params) {
				if (param.isAssignableFrom(event.getClass())) {
					method.invoke(aClass.newInstance(), event);
					break;
				}
			}
		} catch (InvocationTargetException ex) {
			throw new EventException(ex.getCause());
		} catch (ClassCastException ex) {
			// We are going to ignore ClassCastException because EntityDamageEvent can't be cast to EntityDamageByEntityEvent
		} catch (Throwable t) {
			throw new EventException(t);
		}
	}

	public Method getMethod() {
		return method;
	}
}
