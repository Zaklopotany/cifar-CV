package clasifier;

import java.util.List;


import cifar.Image;
import cifar.ImageBinder;
import org.apache.log4j.Logger;


public class MainClassifier {
	
	public static final Logger logger = Logger.getLogger(MainClassifier.class);
	
	public static void main(String[] args) throws Exception {
		logger.info("start");
//		ImageBinder.initializeImageBinder("/home/zaklopotany/Pulpit/images"); - linux
		ImageBinder.initializeImageBinder("C:\\Users\\mzukowski\\Desktop\\cifar10"); //- WIN 10 werk

		List<Image> images = ImageBinder.listOfAllImages();
		
		logger.info("images loaded, size  " + images.size());

		LinearClasifier linc = LinearClasifier.getClasifierWithBiasTrick(images, 0.00001, 10, 1);

		logger.info("LinearClassifier initialized");

		linc.calculateMarginSVM(linc.getBatch(128));

		Thread.sleep(1000);
		
	}
}
