package utilz;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;
public class LoadSave {
	//Load an image from the specified data name
	public static BufferedImage GetImg(String dataName) {
		BufferedImage image = null;
		InputStream input = LoadSave.class.getResourceAsStream("/" + dataName);
		try {
			image = ImageIO.read(input);
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				input.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return image;
	}
	//Load all the levels from the lvls directory
	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/lvls");
		File data = null;
		try {
			data = new File(url.toURI());
		} 
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		File[] datas = data.listFiles();
		File[] datasSorted = new File[datas.length];
		//Sort the files in ascending order based on their names
		for (int i = 0; i < datasSorted.length; i++) {
			for (int j = 0; j < datas.length; j++) {
				if (datas[j].getName().equals((i + 1) + ".png")) {
					datasSorted[i] = datas[j];
				}
			}
		}
		BufferedImage[] images = new BufferedImage[datasSorted.length];
		//Read and load each image file into the array
		for (int i = 0; i < images.length; i++) {
			try {
				images[i] = ImageIO.read(datasSorted[i]);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return images;
	}
}
