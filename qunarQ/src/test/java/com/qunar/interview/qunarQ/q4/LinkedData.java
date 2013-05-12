package com.qunar.interview.qunarQ.q4;



public class LinkedData<K,V> {
	private K k;
	private V v;
	private LinkedData<K,V> nextLinkedData=null;
	private int hash;

	
	
	public LinkedData<K, V> getNextLinkedData() {
		return nextLinkedData;
	}
	public void setNextLinkedData(LinkedData<K, V> nextLinkedData) {
		this.nextLinkedData = nextLinkedData;
	}
	public int getHash() {
		return hash;
	}
	public void setHash(int hash) {
		this.hash = hash;
	}
	public K getK() {
		return k;
	}
	public void setK(K k) {
		this.k = k;
	}
	public V getV() {
		return v;
	}
	public void setV(V v) {
		this.v = v;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hash;
		result = prime * result + ((k == null) ? 0 : k.hashCode());
		result = prime * result
				+ ((nextLinkedData == null) ? 0 : nextLinkedData.hashCode());
		result = prime * result + ((v == null) ? 0 : v.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkedData other = (LinkedData) obj;
		
		if (k.equals(other.k)&&v.equals(other.v)){
			return true;
			
		}
		if (hash != other.hash)
			return false;
		if (k == null) {
			if (other.k != null)
				return false;
		} else if (!k.equals(other.k))
			return false;
		if (nextLinkedData == null) {
			if (other.nextLinkedData != null)
				return false;
		} else if (!nextLinkedData.equals(other.nextLinkedData))
			return false;
		if (v == null) {
			if (other.v != null)
				return false;
		} else if (!v.equals(other.v))
			return false;
		return true;
	}
	

	

}
