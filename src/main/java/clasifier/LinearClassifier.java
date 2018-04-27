package clasifier;

import java.util.List;
import java.util.Random;

import cifar.CifarImage;
import cifar.Image;
import org.apache.log4j.Logger;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;
import util.Pair;

public class LinearClassifier {
    private final List<ImageGeneric> data;
    private double step;
    private double learningRate;
    private INDArray weights;
    private int classNum; // number of distinguish categories
    private double delta; // to calculate margin
    private double reg = 50000; //reularization hyperparameter

    private static final Random rand = new Random();
    private static final Logger logger = Logger.getLogger(LinearClassifier.class);


    private LinearClassifier(List<ImageGeneric> data, double step, int classNum, double delta, double learningRate) {
        super();
        this.classNum = classNum;
        this.step = step;
        this.data = data;
        this.delta = delta;
        this.learningRate = learningRate;
    }

    // numClass - number of distinguish image categories
    public static LinearClassifier getClasifierWithBiasTrick(List<ImageGeneric> data, double step, int classNum
            , double delta, double learningRate) {

        LinearClassifier linearClass = new LinearClassifier(data, step, classNum, delta, learningRate);
        linearClass.setInitialWeights(classNum, data.get(0).getDataLength(), step);
        logger.info("LinearClassifier initialized");
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

    public List<ImageGeneric> getData() {
        return data;
    }

    private void setInitialWeights(int rows, int cols, double step) {
        this.weights = Nd4j.randn(cols, rows).mul(step);
    }

    // get random subset of data: subset size - imgNUm
    private Pair getBatch(int size) throws Exception {
        if (size <= 0 && size > getData().size()) {
            throw new Exception("Incorrect values");
        }
        INDArray data = Nd4j.create(size, getWeights().rows());
        INDArray label = Nd4j.create(1, size);

        for (int i = 0; i < size; i++) {
            // get random image from data set
            ImageGeneric tempImg = getData().get(rand.nextInt(getData().size()));
            data.putRow(i, tempImg.getImgDataNd4jMatrix());
            label.putScalar(0, i, tempImg.getLabel());
        }
//        logger.info(" NEW BATCH CREATE");
        return new Pair<>(data, label);
    }

    private Pair<INDArray, INDArray> getTestData(List<ImageGeneric> testData) {

        INDArray data = Nd4j.create(testData.size(), getWeights().rows());
        INDArray label = Nd4j.create(1, testData.size());

        for (int i = 0; i < testData.size(); i++) {
            data.putRow(i,testData.get(i).getImgDataNd4jMatrix());
            label.putScalar(i, testData.get(i).getLabel());
        }
        return new Pair<>(data,label);
    }

    private INDArray calculateGradient(Pair<INDArray, INDArray> batchData) {
        INDArray correctScores = Nd4j.create(batchData.getKey().rows());
        INDArray scores = batchData.getKey().mmul(getWeights()); // Sj - scores for each class
//        logger.info(" BATCH SCORES CALCULATED");
        for (int i = 0; i < scores.rows(); i++) {
            // Syi - correct scores
            correctScores.putScalar(i, scores.getDouble(i, batchData.getValue().getInt(0, i)));
        }
        calculatePercentAcc(scores, batchData.getValue());
        //calculate margin and max(0,array)
        INDArray margin = Transforms.max(scores.subColumnVector(correctScores.transpose()).add(delta), 0);


        //delete correct classes
        for (int i = 0; i < margin.rows(); i++) {
            margin.put(i, batchData.getValue().getInt(i), 0);
        }
        //calculate loss with regularization
//        calculateLoss(margin, batchData.getKey().rows());
        // transform to binary array
        margin = margin.gt(0);
        //get number of incorrect scores gt than Sj - Syi + delta for explict class
        INDArray sumArray = margin.sum(1);
        //merge binary array with sum of incorrect classes
        for (int i = 0; i < margin.rows(); i++) {
            margin.put(i, batchData.getValue().getInt(i), -sumArray.getDouble(i));
        }
        //calculate and return gradient
        return margin.transpose().mmul(batchData.getKey()).div(batchData.getKey().rows());

    }

    //calculate percent accuracy
    private void calculatePercentAcc(INDArray margin, INDArray batchLabels) {
        INDArray correctValue = margin.argMax(1);
        double mean = correctValue.eq(batchLabels).meanNumber().doubleValue();
        logger.info("Percent accuracy per dataSet: " + mean);

    }

    //calculate Loss
    private void calculateLoss(INDArray margin, int batchSize) {
        double loss = margin.sumNumber().doubleValue() / batchSize + Transforms.pow(getWeights(), 2).sumNumber().doubleValue() * reg;
        logger.info("New Loss: " + loss);
    }

    //calculate new weights
    private void updateWeights(INDArray gradient) {
        this.weights = getWeights().add(gradient.mul(-learningRate).transpose());
    }

    //find W matrix
    public void trainLinearClasifier(int loop, int batchSize) throws Exception {
        for (int i = 0; i < loop; i++) {
            logger.info("Training classifier, loop no: " + i);
            updateWeights(calculateGradient(getBatch(batchSize)));
        }
    }

    public void testTrainedClassifier(List<ImageGeneric> testData) {
        Pair<INDArray, INDArray> testDataPair = getTestData(testData);
        INDArray scores = testDataPair.getKey().mmul(getWeights()); // Sj - scores for each class
        calculatePercentAcc(scores, testDataPair.getValue());

    }
}
