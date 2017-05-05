package com.erp.trans.common.adapter;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class CustomDateTransfer extends PropertyEditorSupport{
	
	private final String format;
	
	private boolean allowEmpty;
	
	public CustomDateTransfer(String format, boolean allowEmpty) {
		this.format = format;
		this.allowEmpty = allowEmpty;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		}
		else{
			try {
				if(text.length() != format.length() && text.length() < 11){
					text = text + " 00:00:00";
				}
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				setValue(sdf.parse(text));
			}
			catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
	}
	
	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return (value != null ? sdf.format(value) : "");
	}
}
