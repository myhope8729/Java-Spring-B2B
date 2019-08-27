package com.kpc.eos.core.model;

/**
 * 
 * PagingModel
 * =================
 * Description : 
 * Methods :
 */
public class PagingModel extends BaseModel {

    private static final long serialVersionUID = 4605322480703993991L;

    private Integer page;
    
    private Integer total;
    
    private Integer rows;

    private Integer records;

    private String sidx;
    
    private String sord;

    private final Integer pagePerGroup = 10;
    
    private Integer startRow;
    
    private Integer endRow;
    
    public PagingModel() {
        this.page = 1;
        this.total = 0;
        this.rows = 10;
        this.records = 0;
        this.sidx = null;
        this.sord = "ASC";
    }

    public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
    
	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

    public Integer getStartRow() {
    	if (this.page == null || this.rows == null) {
    		this.startRow = 0;
    		this.page = 1;
    	} else {
    		int sRow = (this.page.intValue()-1) * this.rows.intValue(); 
    		if (sRow >= records)
    		{
    			sRow = 0;
    			page = 1;
    		}
    		this.startRow = Integer.valueOf(sRow);
    	}
    	
    	return this.startRow;
    }

    public Integer getEndRow() {
		if (this.page == null || this.rows == null) {
			this.endRow = this.rows;
		} else {
			this.endRow = this.page * rows;
		}
		
		return this.endRow;
    }

	public Integer getTotal() {
		if(this.records == null) return 0;
		this.total = this.records / this.rows + ((this.records % this.rows > 0) ? 1 : 0);
		return this.total;
	}

    public void setTotal(Integer total) {
    	this.total = total;
    }
    
    public Integer getFromPageNo() {
    	int pageNo = this.page < 1 ? 1 : this.page;
    	return ((pageNo - 1) / pagePerGroup) * pagePerGroup + 1;
    }

    public Integer getToPageNo() {
        int tmpToPage = getFromPageNo() + pagePerGroup;
        
        if(tmpToPage > getTotal())
            tmpToPage = getTotal() + 1;
        if(tmpToPage > 1)
            tmpToPage--;
        
        return tmpToPage;
    }
    
    public Integer getPrevPageNo() {
    	int pageNo = this.page < 1 ? 1 : this.page;
    	return pageNo - pagePerGroup >= 1 ? getFromPageNo()-1 : 0;
    }
    
    public Integer getNextPageNo() {
    	return getToPageNo() < getTotal() ? getToPageNo()+1 : 0;
    }
    
    public Integer getNextPageNoWithNoGroup() 
    {
    	int nextPage = getPage() + 1;
    	if (nextPage > getTotal())
    	{
    		nextPage = 0;
    	}
    	return nextPage;
    }
    
    public String getBackward() {
    	if ("ASC".equalsIgnoreCase(getSord()))
    		return "DESC";
    	else
    		return "ASC";
    }
}
