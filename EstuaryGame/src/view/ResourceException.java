package view;

import java.nio.file.Path;

public class ResourceException extends Exception {

	public Path errorPath;
	
	public ResourceException(Path p) {
		errorPath = p;
	}
	
}
