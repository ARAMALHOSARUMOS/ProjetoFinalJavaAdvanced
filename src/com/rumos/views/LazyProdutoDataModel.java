package com.rumos.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.rumos.model.Produto;

public class LazyProdutoDataModel extends LazyDataModel<Produto> {
	
	private static final long serialVersionUID = 1L;
	
	private List<Produto> datasource;

	public LazyProdutoDataModel(List<Produto> datasource) {
		this.datasource = datasource;
	}

	@Override
	public Produto getRowData(String rowKey) {
		for (Produto Produto : datasource) {
			if (Produto.getNome().equals(rowKey))
				return Produto;
		}

		return null;
	}

	@Override
	public Object getRowKey(Produto Produto) {
		return Produto.getNome();
	}

	@Override
	public List<Produto> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		List<Produto> data = new ArrayList<Produto>();

		// filter
		for (Produto produto : datasource) {
			boolean match = true;

			if (filters != null) {
				for (Iterator<String> it = filters.keySet().iterator(); it
						.hasNext();) {
					try {
						String filterProperty = it.next();
						Object filterValue = filters.get(filterProperty);
						String fieldValue = String.valueOf(produto.getClass()
								.getField(filterProperty).get(produto));

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
				data.add(produto);
			}
		}

		// sort
		if (sortField != null) {
			Collections.sort(data, new LazyProdutoSorter(sortField, sortOrder));
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
