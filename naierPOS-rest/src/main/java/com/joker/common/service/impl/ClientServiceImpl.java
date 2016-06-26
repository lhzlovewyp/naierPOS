package com.joker.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.ClientMapper;
import com.joker.common.model.Client;
import com.joker.common.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
    ClientMapper mapper;
	
	@Override
	public Client getClientById(String clientId) {
		return mapper.getClientById(clientId);
	}

	@Override
	public Client getClientByCode(String code) {
		return mapper.getClientByCode(code);
	}

	@Override
	public void deleteClientByCode(String code) {
		mapper.deleteClientByCode(code);
	}

	@Override
	public void deleteClientById(String id) {
		mapper.deleteClientById(id);
	}

	@Override
	public void insertClient(Client client) {
		mapper.insertClient(client);
	}

	@Override
	public void updateClient(Client client) {
		mapper.updateClient(client);
	}

}
