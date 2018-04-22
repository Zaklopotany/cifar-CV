package cifar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ImageBinder {
	public static final String MAIN_PATH = "/home/zaklopotany/Pulpit/images";
	public static List<Image> testImages = new ArrayList<>();
	public static List<Image> allImages = new ArrayList<>();
	private static FileInputStream fis;

	public static List<Image> listOfAllImages() {

		String path1 = "/data_batch_1.bin";
		String path2 = "/data_batch_2.bin";
		String path3 = "/data_batch_3.bin";
		String path4 = "/data_batch_4.bin";
		String path5 = "/data_batch_5.bin";
		String[] paths = {path1,path2,path3,path4,path5};
		File[] imageFiles = new File[5];
		// load All files
		for (int i = 0; i < imageFiles.length; i++) {
			imageFiles[i] = new File(MAIN_PATH + paths[i]);
		}
		// Create Images- bind data from binary files
		for (File image : imageFiles) {
			try {
				fis = new FileInputStream(image);
				for (int i = 0; i < 10000; i++) {
					byte labelByte;
					byte[] dataByteArr = new byte[3072]; 
					labelByte = (byte) fis.read();
					fis.read(dataByteArr, 0, 3072);
					allImages.add(new Image(labelByte, dataByteArr));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}

		return allImages;
	}

	public static List<Image> listOfTestImages() {

		String test_path = "/test_batch.bin";
		File file = new File(MAIN_PATH + test_path);

		// Create Images- bind data from binary files

		try {
			FileInputStream fis = new FileInputStream(file);
			for (int i = 0; i < 10000; i++) {
				byte labelByte;
				byte[] dataByteArr = new byte[3072];
				labelByte = (byte) fis.read();
				fis.read(dataByteArr, 0, 3072);
				testImages.add(new Image(labelByte, dataByteArr));

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return testImages;
	}

}
