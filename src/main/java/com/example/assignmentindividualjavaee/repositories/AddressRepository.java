package com.example.assignmentindividualjavaee.repositories;

import com.example.assignmentindividualjavaee.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
