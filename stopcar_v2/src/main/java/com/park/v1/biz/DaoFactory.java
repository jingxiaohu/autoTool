package com.park.v1.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.service.CoreService;
@Service
public class DaoFactory {
	@Autowired
	private CoreService coreService;

	/**
	 * @return the coreService
	 */
	public CoreService getCoreService() {
		return coreService;
	}
	
	
	
	
	
	

}
