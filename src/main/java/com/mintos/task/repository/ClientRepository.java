package com.mintos.task.repository;

import com.mintos.task.model.Account;
import com.mintos.task.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Account> findAccountById(Long id);
}
