package cifar;

import java.util.List;

/**
 * Created by mzukowski on 27/04/2018.
 */
public interface ImgBinder<T> {
    List<T> listOfAllImages();

    List<T> listOfTestImages();
}
