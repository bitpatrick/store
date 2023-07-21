package it.bitprojects.store.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {
	/**
	 * si deve far attenzione ai permessi che si hanno sulle varie directory il
	 * codice legge i file nella directory specificata e anche nelle sottodirectory
	 * 
	 * @param folderPath
	 * @return
	 */
	public static Set<Path> listAllFiles(String folderPath) {
		Set<Path> p = null;
		try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
			p = paths.filter(Files::isRegularFile).collect(Collectors.toSet());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
}
