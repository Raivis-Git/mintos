package com.mintos.task.controller;

import com.mintos.task.controller.model.ClientCreateRequest;
import com.mintos.task.model.Client;
import com.mintos.task.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/client")
public class ClientController {

    @Autowired
    public ClientService clientService;

    @GetMapping("/ids")
    public ResponseEntity<?> getClients() {
        return ResponseEntity.ok(clientService.fetchAllClientIds());
    }

    @PostMapping()
    public ResponseEntity<?> addClient(@RequestBody ClientCreateRequest clientCreateRequest) {
        Client client = clientService.addClient(clientCreateRequest.getFullName(), clientCreateRequest.getEmail(), clientCreateRequest.getPhoneNumber());
        return ResponseEntity.ok(client);
    }

}
