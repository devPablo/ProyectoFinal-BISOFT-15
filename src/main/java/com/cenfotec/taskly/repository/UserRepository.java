package com.cenfotec.taskly.repository;

import com.cenfotec.taskly.domain.User;
import com.microsoft.azure.spring.data.cosmosdb.repository.CosmosRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CosmosRepository<User, String>  {
    public List<User> findByUsername(String username);
}