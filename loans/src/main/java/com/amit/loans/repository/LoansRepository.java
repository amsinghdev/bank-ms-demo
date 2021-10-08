package com.amit.loans.repository;

import com.amit.loans.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoansRepository extends CrudRepository<Loans,Integer> {
    List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}
