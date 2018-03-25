package cifar;

import java.util.List;

public class NearestNeighbor {
	// compare algorithm

	public static void absolute(Image testImage, List<Image> compareImages, int imagesNumber) {
		int compare = 0;

		byte[] testImageDate = testImage.getImageData();

		for (int i = 0; i < testImage.getImageData().length; i++) {
			;
		}
	}

	public class Neighbor implements Comparable<Neighbor> {
		private Image img;
		private int compareValue;

		public Image getImg() {
			return img;
		}

		public void setImg(Image img) {
			this.img = img;
		}

		public int getCompareValue() {
			return compareValue;
		}

		public void setCompareValue(int compareValue) {
			this.compareValue = compareValue;
		}

		@Override
		public int compareTo(Neighbor o) {
			// TODO Auto-generated method stub
			return 0;
		}

	}
}