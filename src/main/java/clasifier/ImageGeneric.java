package clasifier;

import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * Created by mzukowski on 26/04/2018.
 */
public interface ImageGeneric {
    INDArray getImgDataNd4jMatrix();
    int getDataLength();
    byte getLabel();
}
