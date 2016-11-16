package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

public class ResourcesTesting {

	public static void main(String[] args) {
		//Current folders in resources
		String[] allpaths = new String[] {
				"player_idle",
				"background",
		};
		
		
		for (String path: allpaths) {
			String srcpath = "resources\\";
			srcpath = srcpath + path;
			System.out.println(srcpath);
			
			try {
				//List all the paths in the source path
				Stream<Path> paths = Files.list(Paths.get(srcpath));
				//Create an iterator for the paths
				Iterator<Path> itpath = paths.iterator();
				
				Path next = itpath.next();
				if (Files.isDirectory(next)) {
					System.out.println(next.toString());
					Stream<Path> subpaths = Files.list(Paths.get(next.toString()));
					System.out.println(subpaths.count());
				}
				else if (Files.isReadable(next)) {
					System.out.println(next.toString());
					BufferedImage test = ImageIO.read(next.toFile());
					System.out.println(test.getHeight());
				}
				
				paths.close();
			}
			catch (IOException e) {
				System.out.println("IOException from finding directories");
			}
		}
		
		
		/*
		try {
			Stream<Path> paths = Files.list(Paths.get(srcpath));
			Iterator<Path> itpath = paths.iterator();
			
			if (Files.isDirectory(itpath.next())) {
				System.out.println("hell yeah");
			}
			
			int directs = (int) Files.list(Paths.get(srcpath)).count();
			System.out.println(directs + " directories");
		}
		catch (IOException e) {
			System.out.println("IOException from finding directories");
		}
		*/
	}
	
}