
package com.kpc.eos.model.statistic;


import lombok.Data;
import com.kpc.eos.model.common.DefaultSModel;

@Data
public class StockSearchSModel extends DefaultSModel {
	
	private static final long serialVersionUID = 5110546420068900605L;
	
	private String storeId;
	private String storeName;
	private String propertyField;
	private String propertyFieldValue;
	
	public StockSearchSModel() {
		
	}
}