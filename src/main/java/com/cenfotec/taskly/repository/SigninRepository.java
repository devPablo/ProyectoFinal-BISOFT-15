package com.cenfotec.taskly.repository;

import com.cenfotec.taskly.domain.User;
import com.microsoft.azure.spring.data.cosmosdb.repository.CosmosRepository;

public interface SigninRepository extends CosmosRepository<User, String> {
}
