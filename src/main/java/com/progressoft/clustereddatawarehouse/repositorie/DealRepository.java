package com.progressoft.clustereddatawarehouse.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progressoft.clustereddatawarehouse.model.entitie.Deal;



@Repository
public interface DealRepository extends JpaRepository<Deal, String> {}