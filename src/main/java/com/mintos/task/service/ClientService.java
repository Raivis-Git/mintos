package com.mintos.task.service;

import com.mintos.task.model.Client;
import com.mintos.task.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Long> fetchAllClientIds() {
        return clientRepository.findAll().stream()
                .map(Client::getId)
                .collect(Collectors.toList());
    }

    public Client addClient(String fullName, String email, String phoneNumber) {
        return clientRepository.save(new Client(fullName, email, phoneNumber));
    }

    public Optional<Client> fetchClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

}
