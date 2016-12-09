package view;

import java.nio.file.Path;

/**
 * If an Image fails to load because the specified filepath doesn't exist,
 * this is thrown.
 * 
 * @author Ian
 * @version 1.1
 * @since 12/9/16
 */
public class ResourceException extends Exception {

	public Path errorPath;
	
	/**
	 * Creates the Exception
	 * @param p
	 */
	public ResourceException(Path p) {
		errorPath = p;
	}
	
}
