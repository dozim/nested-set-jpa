package com.doz

import com.doz.model.SimpleTree
import org.springframework.data.repository.PagingAndSortingRepository

interface SimpleTreeRepository : PagingAndSortingRepository<SimpleTree, Long> {

}
