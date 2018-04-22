package clasifier;

import java.util.Random;

import org.nd4j.linalg.cpu.nativecpu.NDArray;
import org.nd4j.linalg.factory.Nd4j;

import cifar.Image;

public class LinearClasifier {
	private static final Random rand = new Random();
	private Image[] data;
	private double step;
	private NDArray weights;
	private int classNum;

	private LinearClasifier(Image[] data, double step, int classNum) {
		super();
		this.classNum = classNum;
		this.step = step;
		this.data = data;
	}

	public static LinearClasifier getClasifierWithBiasTrick(Image[] data, double step, int classNum) {
		
		LinearClasifier linearClass = new LinearClasifier(data,step,classNum);
		try {
			linearClass.setInitialWeights(classNum, data[0].getImageData().length + 1, step);
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		return linearClass;
	}

	/**
	 * get classifier without bias trick
	 * 
	 */
	private static void getClasifier() {
		// TODO to be implemented
	}

	private NDArray setInitialWeights(int rows, int cols, double step) {
		return (NDArray) Nd4j.rand(rows, cols).muli(step);
	}
	
	//get subgroup of data size - imgNUm
	public Image[] getBatch(int size) throws Exception {
		if (size <= 0) {
			throw new Exception("Incorrect values");
		}
		Image[] tempBatch = new Image[size];

		for (int i = 0; i < size; i++) {
			tempBatch[i] = data[rand.nextInt(data.length)];
		}
		return tempBatch;
	}

	public NDArray getWeights() {
		return weights;
	}

	public void setWeights(NDArray weights) {
		this.weights = weights;
	}

	
	// batch
	// calculate margins
	// calculate binarry array
	// calculate gradient

}
