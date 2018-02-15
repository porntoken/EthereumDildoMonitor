package com.porntoken.dao;

/**
 * @author Christopher Gu
 * @product PornToken $PTWO Live Dildo Crowdfund
 * Copyright 2018
 */

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.porntoken.model.ProcessedEthTx;

@Repository
public interface ProcessedEthTxDao extends CrudRepository<ProcessedEthTx, Integer> {

}
