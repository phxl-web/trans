package com.erp.trans.web.dto;

import java.util.List;

public class SeriesDto {
    private String id;

    private String name;

    private String firstletter;
    
    private String seriesorder;
    
    private List<SeriesDto> seriesitems;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstletter() {
		return firstletter;
	}

	public void setFirstletter(String firstletter) {
		this.firstletter = firstletter;
	}

	public String getSeriesorder() {
		return seriesorder;
	}

	public void setSeriesorder(String seriesorder) {
		this.seriesorder = seriesorder;
	}

	public List<SeriesDto> getSeriesitems() {
		return seriesitems;
	}

	public void setSeriesitems(List<SeriesDto> seriesitems) {
		this.seriesitems = seriesitems;
	}
	
	

    
}