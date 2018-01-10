package org.bitbucket.jsdev88.projethaddadsaussier.utils;

import java.util.Objects;

public class Pair<K,V> {
	private K key;
	private V value;
	
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.getKey().toString() + " Orientation :" + this.getValue().toString();
	}
	
	 @Override
	    public boolean equals(final Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (!(o instanceof Pair)) {
	            return false;
	        }

	        final Pair<?, ?> pair = (Pair<?, ?>) o;

	        if (key!= pair.key) {
	            return false;
	        }
	        if (value != pair.value) {
	            return false;
	        }

	        return true;
	    }

	    @Override
	    public int hashCode() {
	    	return Objects.hash(key, value);
	    }
	
}
