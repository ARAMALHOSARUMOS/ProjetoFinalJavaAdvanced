package com.rumos.views;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import com.rumos.model.Produto;

public class LazyProdutoSorter implements Comparator<Produto>  {
	
	   private String sortField;
	     
	    private SortOrder sortOrder;
	     
	    public LazyProdutoSorter(String sortField, SortOrder sortOrder) {
	        this.sortField = sortField;
	        this.sortOrder = sortOrder;
	    }
	 
	    public int compare(Produto Produto1, Produto Produto2) {
	        try {
	            Object value1 = Produto.class.getField(this.sortField).get(Produto1);
	            Object value2 = Produto.class.getField(this.sortField).get(Produto2);
	 
	            int value = ((Comparable)value1).compareTo(value2);
	             
	            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
	        }
	        catch(Exception e) {
	            throw new RuntimeException();
	        }
	    }

}
