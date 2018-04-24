package clasifier;

import java.util.List;
import java.util.logging.Logger;

import cifar.Image;
import cifar.ImageBinder;

public class MainClassifier {
	
	public static final Logger logger = Logger.getLogger(MainClassifier.class.getName());
	
	public static void main(String[] args) throws InterruptedException {
		logger.info("start");
		
		List<Image> images = ImageBinder.listOfAllImages();
		
		logger.info("images loaded, size  " + images.size());
		
		LinearClasifier linc = LinearClasifier.getClasifierWithBiasTrick(images, 0.00001, 10, 1);
		
		
		Thread.sleep(1000);
		
	}
}
