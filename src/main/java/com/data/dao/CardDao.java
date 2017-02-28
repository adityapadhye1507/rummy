package com.data.dao;

import java.util.List;


public interface CardDao {
	
	public List<String> getCards() throws Exception;

	public String toCsv(List<String> list);
	
	public List<String> toList(String string);
	
}
