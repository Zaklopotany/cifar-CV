package cifar;

public class Image {
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

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

}
