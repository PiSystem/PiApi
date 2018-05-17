## How to use:

#### Register commands by annotation:

```java
import cn.nukkit.command.CommandSender;
import tech.teslex.pi.annotations.PiCommand;

class Commands { 
	
	@PiCommand(command = "test", description = "test command", usage = "usage test", aliases = {"text"}, permissions = {"testplugin.test.use"})
        public void test(CommandSender commandSender, String s, String[] args) {
            commandSender.sendMessage("test response");
        }
        
}
```
And register the class:
```java
import tech.teslex.pi.PiApi;

PiApi.registerCommands(Commands.class);
```


#### Require dependencies:
```java
import tech.teslex.pi.PiApi;
import tech.teslex.pi.dependencies.PiDependency;
import tech.teslex.pi.dependencies.PiDType;

PiDependency jwt = new PiDependency("JWT", "java-jwt-{VERSION}.jar", PiDType.LIBRARY, "3.3.0", "http://central.maven.org/maven2/com/auth0/java-jwt/{VERSION}/java-jwt-{VERSION}.jar"); 

PiApi.requireDependency(jwt);
```


#### Use Groovy:
```groovy
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.plugin.PluginBase
import tech.teslex.pi.PiApi
import tech.teslex.pi.annotations.PiCommand

class MyGroovyPlugin extends PluginBase {

	@Override
	void onEnable() {
		PiApi.registerCommands(this.class)
	}

	@PiCommand(command = "echo")
	void echo(CommandSender sender, String s, String[] args) {
		sender.sendMessage("Echo: ${args.join(" ")}")
	}
}
```