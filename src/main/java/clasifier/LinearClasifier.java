package clasifier;

import java.util.List;
import java.util.Random;

import cifar.Image;
import org.apache.log4j.Logger;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;
import util.Pair;

public class LinearClasifier {
    private static final Random rand = new Random();
    private List<Image> data;
    private double step;
    private INDArray weights;
//    private static final Logger logger = Logger.getLogger(LinearClasifier.class.getName());
//    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LinearClasifier.class);
    private static final Logger logger = Logger.getLogger(LinearClasifier.class);
    private int classNum; // number of distinguish categories
    private double delta; // to calculate margin

    private LinearClasifier(List<Image> data, double step, int classNum, double delta) {
        super();
        this.classNum = classNum;
        this.step = step;
        this.data = data;
        this.delta = delta;
    }

    // numClass - number of distinguish image categories
    public static LinearClasifier getClasifierWithBiasTrick(List<Image> data, double step, int classNum, double delta) {

        LinearClasifier linearClass = new LinearClasifier(data, step, classNum, delta);

        linearClass.setInitialWeights(classNum, data.get(0).getImageData().length + 1, step);

        return linearClass;
    }

    /**
     * get classifier without bias trick
     */
    private static void getClasifier() {
        // TODO to be implemented
    }

    public INDArray getWeights() {
        return weights;
    }

    public List<Image> getData() {
        return this.data;
    }

    private void setInitialWeights(int rows, int cols, double step) {
        this.weights = Nd4j.randn(cols, rows).mul(step);
    }

    // support method to create INDArray from byte array
    //and add one additional dimension bias trick
    public static double[] convertToIntArray(byte[] input) {
        double[] ret = new double[input.length + 1];
        for (int i = 0; i < input.length - 1; i++) {
            ret[i] = input[i] & 0xff; // Range 0 to 255, not -128 to 127
        }
        return ret;
    }

    // get random subset of data: subset size - imgNUm
    public Pair getBatch(int size) throws Exception {
        if (size <= 0) {
            throw new Exception("Incorrect values");
        }
        INDArray data = Nd4j.create(size, getWeights().rows());
        INDArray label = Nd4j.create(1, size);

        for (int i = 0; i < size; i++) {
            // get random image from data set
            Image tempImg = getData().get(rand.nextInt(getData().size()));
            data.putRow(i, Nd4j.create(LinearClasifier.convertToIntArray(tempImg.getImageData())));
            label.putScalar(0, i, tempImg.getLabel());
        }
        logger.info(" NEW BATCH CREATE");
        return new Pair(data, label);
    }

    public INDArray calculateMarginSVM(Pair<INDArray, INDArray> batchData) {
        INDArray correctScores = Nd4j.create(batchData.getKey().rows());
        INDArray scores = batchData.getKey().mmul(getWeights()); // Sj - scores for each class
        logger.info(" BATCH SCORES CALCULATED");
        for (int i = 0; i < scores.rows(); i++) {
            // Syi - correct scores
            correctScores.putScalar(i, batchData.getKey().getDouble(i, batchData.getValue().getInt(0, i)));
        }
        logger.info(" BATCH CORRECTSCORES CALCULATED");
        //calculate margin and max(0,array)
        INDArray margin = scores.subColumnVector(correctScores.transpose());
                margin = Transforms.max(margin, 0);
        // transform to binary array
        margin = margin.gt(0);
        //delete correct classes
        for (int i = 0; i < margin.rows(); i++) {
            margin.put(i, batchData.getValue().getInt(i), 0);
        }
        //get number of incorrect scores gt than Sj - Syi + delta for explict class
        INDArray sumArray = margin.sum(1);
        //merge binary a
        for (int i = 0; i < margin.rows(); i++) {
            margin.put(i, batchData.getValue().getInt(i), sumArray.getInt(i));
        }

        INDArray ind = Nd4j.create(123, 123);//= new NDArray();
        return ind;
        // delete Syi margin

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
