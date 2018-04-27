//package cifar;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import cifar.NearestNeighbor.Neighbor;
//
//public class Main {
//
//	public static void main(String[] args) {
//		System.out.println("start");
//		List<Image> images = ImageBinder.listOfAllImages();
//		List<Image> testImages  = ImageBinder.listOfTestImages();
//		System.out.println(images.size());
//		System.out.println(testImages.size());
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		List<Neighbor> lista = new LinkedList<>();
//		lista = NearestNeighbor.absolute(testImages.get(1),10, images);
//		System.out.println("zakonczono");
//		System.out.println(lista.toString());
//
//
//	}
//}
