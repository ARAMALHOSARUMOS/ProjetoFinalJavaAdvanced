package com.rumos.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.rumos.model.Linhasdefatura;

public class LazyLinhasFaturaDataModel extends LazyDataModel<Linhasdefatura> {
	
	private static final long serialVersionUID = 1L;
	
	private List<Linhasdefatura> datasource;

	public LazyLinhasFaturaDataModel(List<Linhasdefatura> datasource) {
		this.datasource = datasource;
	}

	@Override
	public Linhasdefatura getRowData(String rowKey) {
		for (Linhasdefatura Linhasdefatura : datasource) {
			if (Linhasdefatura.getProduto().getNome().equals(rowKey))
				return Linhasdefatura;
		}

		return null;
	}

	@Override
	public Object getRowKey(Linhasdefatura Linhasdefatura) {
		
		String s = Linhasdefatura.getProduto().getNome();
		
		return s;
	}

	@Override
	public List<Linhasdefatura> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		List<Linhasdefatura> data = new ArrayList<Linhasdefatura>();

		// filter
		for (Linhasdefatura Linhasdefatura : datasource) {
			boolean match = true;

			if (filters != null) {
				for (Iterator<String> it = filters.keySet().iterator(); it
						.hasNext();) {
					try {
						String filterProperty = it.next();
						Object filterValue = filters.get(filterProperty);
						String fieldValue = String.valueOf(Linhasdefatura.getClass()
								.getField(filterProperty).get(Linhasdefatura));

						if (filterValue == null
								|| fieldValue
										.startsWith(filterValue.toString())) {
							match = true;
						} else {
							match = false;
							break;
						}
					} catch (Exception e) {
						match = false;
					}
				}
			}

			if (match) {
				data.add(Linhasdefatura);
			}
		}

		// sort
		if (sortField != null) {
			Collections.sort(data, new LazyLinhasdefaturaSorter(sortField, sortOrder));
		}

		// rowCount
		int dataSize = data.size();
		this.setRowCount(dataSize);

		// paginate
		if (dataSize > pageSize) {
			try {
				return data.subList(first, first + pageSize);
			} catch (IndexOutOfBoundsException e) {
				return data.subList(first, first + (dataSize % pageSize));
			}
		} else {
			return data;
		}
	}
}
