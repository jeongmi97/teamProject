package com.itbank.dao;

import java.util.HashMap;

import com.itbank.vo.CustomerVO;

public interface CustomerDAO {

	CustomerVO login(CustomerVO vo);

	String emailcheck(String email);

	int insertCustomer(CustomerVO vo);

	CustomerVO searchEmail(CustomerVO vo);
	
	String searchPw(CustomerVO vo);

	void updatePw(HashMap<String, String> map);

	CustomerVO checkPassword(CustomerVO vo);

	void updateC(CustomerVO vo);

}
