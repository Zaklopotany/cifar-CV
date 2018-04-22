package cifar;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NearestNeighbor {
	// compare algorithm

	public static List<Neighbor> absolute(Image testImage, int size, List<Image> compareImages) {
		List<Neighbor> list = new LinkedList<>();

		byte[] testImageData = testImage.getImageData();

		Iterator<Image> iterList = compareImages.iterator();

		while (iterList.hasNext()) {
			Neighbor tempNeighbor = new Neighbor(iterList.next(), 0);
			byte[] compareImageData = tempNeighbor.getImg().getImageData();
			int sum = 0;
			for (int i = 0; i < testImage.getImageData().length; i++) {
				sum += Math.abs(testImageData[i] - compareImageData[i]);
			}

			tempNeighbor.setCompareValue(sum);
			storeInList(tempNeighbor, size, list);
		}
		return list;
	}

	public static class Neighbor implements Comparable<Neighbor> {
		private Image img;
		private int compareValue;

		public Neighbor(Image image, int compareValue) {
			this.img = image;
			this.compareValue = compareValue;
		}

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
			if (this.getCompareValue() > o.getCompareValue()) {
				return -1;
			} else if (this.getCompareValue() < o.getCompareValue()) {
				return 1;
			} else {
				return 0;
			}
		}
		@Override
		public String toString() {
			return this.compareValue + "";
		}

	}

	public static void storeInList(Neighbor neighbor, int size, List<Neighbor> list) {
		boolean condition = true;

		if (list.isEmpty()) {
			list.add(neighbor);
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			if (neighbor.compareTo(list.get(i)) > 0 && i < size - 1) {
				list.add(i, neighbor);
				condition = false;
				break;
			}

		}

		if (condition && list.size() < size) {
			list.add(neighbor);
		}

		if (list.size() == size + 1) {
			list.remove(size);
		}

		return;

	}

}