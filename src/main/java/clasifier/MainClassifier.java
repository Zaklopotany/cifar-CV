package clasifier;

import java.util.List;


import cifar.CifarImageBinder;
import cifar.Image;
import cifar.ImageBinder;
import org.apache.log4j.Logger;


public class MainClassifier {

    public static final Logger logger = Logger.getLogger(MainClassifier.class);

    public static void main(String[] args) throws Exception {
        //		ImageBinder.initializeImageBinder("/home/zaklopotany/Pulpit/images"); - linux
        CifarImageBinder cifImgBin = new CifarImageBinder("C:\\Users\\mzukowski\\Desktop\\cifar10");
        List<ImageGeneric> imagesCif = cifImgBin.listOfAllImages();

        ImageBinder imgBin = new ImageBinder("C:\\Users\\mzukowski\\Desktop\\cifar10");
        List<ImageGeneric> images = imgBin.listOfAllImages();

        LinearClassifier linc = LinearClassifier.getClasifierWithBiasTrick(images, 0.0001, 10, 1, 0.00000001);


        linc.trainLinearClasifier(10000, 256);

        Thread.sleep(1000);

    }
}
