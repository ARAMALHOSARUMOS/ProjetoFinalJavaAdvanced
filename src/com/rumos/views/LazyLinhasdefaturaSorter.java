package com.rumos.views;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import com.rumos.model.Linhasdefatura;
import com.rumos.model.Produto;

public class LazyLinhasdefaturaSorter implements Comparator<Linhasdefatura>  {
	
	   private String sortField;
	     
	    private SortOrder sortOrder;
	     
	    public LazyLinhasdefaturaSorter(String sortField, SortOrder sortOrder) {
	        this.sortField = sortField;
	        this.sortOrder = sortOrder;
	    }
	 
	    public int compare(Linhasdefatura Produto1, Linhasdefatura Produto2) {
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
