package org.rest.rapa.resource;

public class ResourceImpl implements Resource {
	private int id;

	public ResourceImpl() {
	}

	public ResourceImpl(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
