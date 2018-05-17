package tech.teslex.pi.dependencies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PiDependency {

	private String name;

	@SerializedName("file_name")
	@Expose
	private String fileName;

	private PiDType type;

	private String version;

	private String url;

	public PiDependency(String name, PiDType type, String version, String url) {
		this.name = name;
		this.type = type;
		this.version = version;
		this.url = url;
	}

	public PiDependency(String name, String fileName, PiDType type, String version, String url) {
		this.name = name;
		this.fileName = fileName;
		this.type = type;
		this.version = version;
		this.url = url;
	}

	public String getFileName() {
		return fileName.replace("{VERSION}", getVersion()).replace("{NAME}", getName());
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PiDType getType() {
		return type;
	}

	public void setType(PiDType type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url.replace("{VERSION}", getVersion()).replace("{NAME}", getName());
	}

	public void setUrl(String url) {
		this.url = url;
	}

}