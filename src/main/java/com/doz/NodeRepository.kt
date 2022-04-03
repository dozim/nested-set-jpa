package com.doz

import com.doz.model.NodeEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param


interface NodeRepository : PagingAndSortingRepository<NodeEntity, Long> {

    @Query(
        "SELECT n FROM NodeEntity n " +
                "WHERE n.treeId = :treeId " +
                "AND n.leftLink BETWEEN :left AND :right " +
                "ORDER BY n.leftLink"
    )
    fun findSubTree(
        @Param("left") leftNum: Int,
        @Param("right") rightNum: Int,
        @Param("treeId") treeId: Long
    ): List<NodeEntity>

    @Query(
        "SELECT n FROM NodeEntity n " +
                "WHERE n.treeId = :treeId " +
                "AND n.leftLink BETWEEN :left AND :right " +
                "AND n.rightLink = n.leftLink + 1 " +
                "ORDER BY n.leftLink "
    )
    fun findLeafNodes(
        @Param("left") leftNum: Int,
        @Param("right") rightNum: Int,
        @Param("treeId") treeId: Long
    ): List<NodeEntity>

    @Modifying
    @Query(
        "UPDATE NodeEntity " +
                "SET rightLink = rightLink + 2 " +
                "WHERE treeId = :treeId " +
                "AND rightLink > :right "
    )
    fun updateAllNodesToRight(
        @Param("right") rightNum: Int,
        @Param("treeId") treeId: Long
    )

    @Query(
        "SELECT n FROM NodeEntity n " +
                "WHERE n.treeId = :treeId " +
                "AND n.leftLink = 1 "
    )
    fun findRootNode(
        @Param("treeId") treeId: Long,
        pageable: Pageable
    ): Page<NodeEntity>

    @Query(
        "SELECT n FROM NodeEntity n " +
                "WHERE n.treeId = :treeId " +
                "AND n.rightLink > :right AND n.leftLink < :left " +
                "ORDER BY n.leftLink ASC"
    )
    fun findParentNode(
        @Param("left") leftNum: Int,
        @Param("right") rightNum: Int,
        @Param("treeId") treeId: Long,
        pageable: Pageable
    ): Page<NodeEntity>
}
