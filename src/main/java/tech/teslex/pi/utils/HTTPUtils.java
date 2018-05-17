package tech.teslex.pi.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class HTTPUtils {

	public static File download(String url, Path to) throws IOException {
		URL xUrl = new URL(url);
		try (InputStream in = xUrl.openStream()) {
			String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
			File x = new File(to.toAbsolutePath().toFile().getAbsoluteFile() + File.separator + fileName);

			Files.copy(in, x.toPath(), StandardCopyOption.REPLACE_EXISTING);

			return x;
		}
	}

	public static File download(String url, Path to, String fileName) throws IOException {
		URL xUrl = new URL(url);
		try (InputStream in = xUrl.openStream()) {
			File x = new File(to.toAbsolutePath().toFile().getAbsoluteFile() + File.separator + fileName);

			Files.copy(in, x.toPath(), StandardCopyOption.REPLACE_EXISTING);

			return x;
		}
	}

}
