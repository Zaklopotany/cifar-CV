package util;

public class Pair <V,W> {

	private V key;
	private W value;
	
	
	
	public Pair(V key, W value) {
		super();
		this.key = key;
		this.value = value;
	}
	public V getKey() {
		return key;
	}
	public void setKey(V key) {
		this.key = key;
	}
	public W getValue() {
		return value;
	}
	public void setValue(W value) {
		this.value = value;
	}
	
	
	
}
