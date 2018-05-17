package tech.teslex.pi.dependencies;


import com.google.gson.annotations.SerializedName;

public enum PiDType {
	@SerializedName("plugin")
	PLUGIN,
	@SerializedName("lib")
	LIBRARY
}