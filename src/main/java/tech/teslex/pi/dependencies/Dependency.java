package tech.teslex.pi.dependencies;

public class Dependency {

	private String name;

	private Type type;

	private String version;

	private String url;

	public Dependency(String name, Type type, String version, String url) {
		this.name = name;
		this.type = type;
		this.version = version;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url.replace("{VERSION}", getVersion()).replace("{FILE_NAME}", getName());
	}

	public void setUrl(String url) {
		this.url = url;
	}

}