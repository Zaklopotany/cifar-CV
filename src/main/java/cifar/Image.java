package cifar;

import clasifier.ImageGeneric;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import util.Util;

public class Image implements ImageGeneric {
    private byte label;
    private byte[] data;

    public Image() {
    }

    public Image(byte label, byte[] data) {
        super();
        this.label = label;
        this.data = data;
    }

    @Override
    public byte getLabel() {
        return label;
    }


    public INDArray getImgDataNd4jMatrix() {
        return Nd4j.create(Util.convertToDoubleArrayBiasTrick(data));
    }

    @Override
    public int getDataLength() {
        return this.data.length;
    }


}
