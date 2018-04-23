package clasifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import cifar.Image;
import util.Pair;

public class LinearClasifier {
	private static final Random rand = new Random();
	private Image[] data;
	private double step;
	private INDArray weights;
	private int classNum; // number of distinfguish categories
	private double delta; // to calculate margin
	private static final Logger logger = Logger.getLogger(LinearClasifier.class.getName());

	private LinearClasifier(Image[] data, double step, int classNum, double delta) {
		super();
		this.classNum = classNum;
		this.step = step;
		this.data = data;
		this.delta = delta;
	}

	// numClass - number of distinguish image categories
	public static LinearClasifier getClasifierWithBiasTrick(Image[] data, double step, int classNum, double delta) {

		LinearClasifier linearClass = new LinearClasifier(data, step, classNum, delta);
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

	public INDArray getWeights() {
		return weights;
	}

	private INDArray setInitialWeights(int rows, int cols, double step) {
		return Nd4j.rand(cols, rows).muli(step);
	}

	// support method to create INDArray from byte array
	public static int[] convertToIntArray(byte[] input) {
		int[] ret = new int[input.length];
		for (int i = 0; i < input.length; i++) {
			ret[i] = input[i] & 0xff; // Range 0 to 255, not -128 to 127
		}
		return ret;
	}

	// get random subset of data: subset size - imgNUm
	public Pair<INDArray, INDArray> getBatch(int size) throws Exception {
		if (size <= 0) {
			throw new Exception("Incorrect values");
		}

		INDArray data = Nd4j.create(size, weights.columns() + 1);
		INDArray label = Nd4j.create(size);

		for (int i = 0; i < size; i++) {
			Image tempImg = this.data[rand.nextInt(this.data.length)];
			data.put(i, Nd4j.create(LinearClasifier.convertToIntArray(tempImg.getImageData())));
			label.putScalar(i, tempImg.getLabel());
		}
		Pair<INDArray,INDArray> tempPair = new Pair(data, label);
		logger.info(" NEW BATCH ");
		return tempPair;
	}

	public INDArray calculateMarginSVM(Pair<INDArray, INDArray>  batchData, INDArray weights) {
		
//		INDArray margin = Nd4j.create(classNum, batchData.getKey().rows());
		INDArray scores = batchData.getKey().mul(weights);
		INDArray correctScores = Nd4j.create(batchData.getKey().rows());
		
		for(int i = 0; i<batchData.getKey().rows(); i++) {
//			scores
			batchData.getKey().getDouble(i, batchData.getValue().getInt(i));
//			correctScores.put(i, b)
		}

		logger.info(" MARGIN CALCULATED");

		// Wxi
		// Wyi

		//

		// Transforms.max
	}

	// batch
	// calculate margins
	// calculate binarry array
	// calculate gradient

}
