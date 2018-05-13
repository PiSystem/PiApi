package tech.teslex.pi.dependencies;


import com.google.gson.annotations.SerializedName;

public enum Type {
	@SerializedName("plugin")
	PLUGIN,
	@SerializedName("lib")
	LIBRARY
}