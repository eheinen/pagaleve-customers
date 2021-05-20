package com.pagaleve.eheinen.api.customer

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@EnableScan
@Repository
interface CustomerRepository : CrudRepository<Customer, String>