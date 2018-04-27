package cifar;

import clasifier.ImageGeneric;
import com.sun.org.apache.xml.internal.security.encryption.CipherData;
import org.apache.log4j.Logger;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzukowski on 27/04/2018.
 * God bless young Java Dev : *
 */
public class CifarImageBinder implements ImgBinder<ImageGeneric> {

    private  String MAIN_PATH;// win10 work = "C:\\Users\\mzukowski\\Desktop\\cifar-100-binary"; // "/home/zaklopotany/Pulpit/images"
    private List<ImageGeneric> testImages;// = new ArrayList<>();
    private List<ImageGeneric> allImages;// = new ArrayList<>();
    private FileInputStream fis;
    private final int imgNum = 10000; //number of images per data batch in cifar 10 - 10000
    private static final Logger logger = Logger.getLogger(CifarImageBinder.class);

    public CifarImageBinder(String MAIN_PATH) {
        this.MAIN_PATH = MAIN_PATH;
        this.testImages = new ArrayList<>();
        this.allImages = new ArrayList<>();
        logger.info("CifarImage binder Initialized...");


    }

    @Override
    public List<ImageGeneric> listOfAllImages() {
        String path1 = "/data_batch_1.bin";
        String path2 = "/data_batch_2.bin";
        String path3 = "/data_batch_3.bin";
        String path4 = "/data_batch_4.bin";
        String path5 = "/data_batch_5.bin";
        String[] paths = {path1, path2, path3, path4, path5};
        File[] imageFiles = new File[5]; // should be 5
//		 load All files
        for (int i = 0; i < imageFiles.length; i++) {
            imageFiles[i] = new File(MAIN_PATH + paths[i]);
        }

        // Create Images- bind data from binary files
        for (File image : imageFiles) {
            try {
                fis = new FileInputStream(image);
                for (int i = 0; i < imgNum; i++) {
                    byte labelByte = (byte) fis.read();
                    byte[] dataByteArr = new byte[3072];
                    fis.read(dataByteArr, 0, 3072);
                    INDArray tempData = Nd4j.create(Util.convertToDoubleArrayBiasTrick(dataByteArr));
                    allImages.add(new CifarImage(tempData, labelByte));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        logger.info("images loaded, size  " + allImages.size());

        return allImages;

    }

    @Override
    public List<ImageGeneric> listOfTestImages() {
        String test_path = "/test_batch.bin";
        File file = new File(MAIN_PATH + test_path);
        // Create Images- bind data from binary files
        try {
            fis = new FileInputStream(file);
            for (int i = 0; i < imgNum; i++) {
                byte labelByte = (byte) fis.read();
                byte[] dataByteArr = new byte[3072];
                fis.read(dataByteArr, 0, 3072);
                INDArray tempData = Nd4j.create(Util.convertToDoubleArrayBiasTrick(dataByteArr));
                testImages.add(new CifarImage(tempData, labelByte));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        logger.info("Test images loaded, size  " + testImages.size());
        return testImages;
    }
}
