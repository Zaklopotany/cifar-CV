package cifar;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		System.out.println("start");
		List<Image> images = images = ImageBinder.listOfAllImages();
		System.out.println(images.size());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("zakonczono");
		
		
	}
}
