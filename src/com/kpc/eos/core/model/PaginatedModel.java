package com.kpc.eos.core.model;

import lombok.Data;

@Data
public class PaginatedModel extends BaseModel {
	protected PagingModel page = new PagingModel();

}
