package cifar;

import clasifier.ImageGeneric;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import util.Util;

/**
 * Created by mzukowski on 26/04/2018.
 */
public class CifarImage implements ImageGeneric {
    INDArray data;
    byte label;

    public CifarImage(INDArray data, byte label) {
        this.data = data;
        this.label = label;
    }


    @Override
    public INDArray getImgDataNd4jMatrix() {
        return data;
    }

    @Override
    public int getDataLength() {
        return this.data.length();
    }
    @Override
    public byte getLabel() {
        return label;
    }
}
