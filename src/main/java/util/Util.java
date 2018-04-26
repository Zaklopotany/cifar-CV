package util;

/**
 * Created by mzukowski on 26/04/2018.
 */
public class Util {

    // support method to create INDArray from byte array
    //and add one additional dimension bias trick
    public static double[] convertToDoubleArrayBiasTrick(byte[] input) {
        double[] ret = new double[input.length + 1];
        for (int i = 0; i < input.length - 1; i++) {
            ret[i] = input[i] & 0xff; // Range 0 to 255, not -128 to 127
        }
        return ret;
    }
}
