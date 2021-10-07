package com.amit.accounts.repository;

import com.amit.accounts.model.Accounts;
import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<Accounts,Integer> {
    public Accounts getAccountByCustomerId(Integer customerId);
}
