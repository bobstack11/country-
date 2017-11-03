package com.egouos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egouos.dao.BaseDAO;
import com.egouos.pojo.OrderId;
import com.egouos.service.OrderIdService;

@Service
public class OrderIdServiceImpl implements OrderIdService{
	@Autowired
	BaseDAO baseDAO;

	@Override
	public String addOxid() {
		final OrderId oid = new OrderId(System.currentTimeMillis());
		baseDAO.saveOrUpdate(oid);
		return oid.getOxid();
	}
	
}
