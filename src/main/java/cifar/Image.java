package cifar;

import clasifier.ImageGeneric;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import util.Util;

public class Image implements ImageGeneric{
	private byte label;
	private byte[] imageData;

	public Image() {
	}

	public Image(byte label, byte[] imageData) {
		super();
		this.label = label;
		this.imageData = imageData;
	}

	// getters setters
	public byte getLabel() {
		return label;
	}

	public void setLabel(byte label) {
		this.label = label;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public INDArray getImgDataNd4jMatrix() {
		return Nd4j.create(Util.convertToDoubleArrayBiasTrick(getImageData()));
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

}
