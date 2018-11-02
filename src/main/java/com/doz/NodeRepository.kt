package com.doz

import com.doz.model.Node
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param


const val query = ""


interface NodeRepository : PagingAndSortingRepository<Node, Long> {

    @Query("SELECT n FROM Node n " +
            "WHERE n.treeId = :treeId " +
            "AND n.leftLink BETWEEN :left AND :right " +
            "ORDER BY n.leftLink")
    fun findSubTree(@Param("left") leftNum: Long,
                    @Param("right") rightNum: Long,
                    @Param("treeId") treeId: Long): List<Node>

    @Query("SELECT n FROM Node n " +
            "WHERE n.treeId = :treeId " +
            "AND n.leftLink BETWEEN :left AND :right " +
            "AND n.rightLink = n.leftLink + 1 " +
            "ORDER BY n.leftLink ")
    fun findLeafNodes(@Param("left") leftNum: Long,
                      @Param("right") rightNum: Long,
                      @Param("treeId") treeId: Long): List<Node>

    @Modifying
    @Query("UPDATE Node " +
            "SET rightLink = rightLink + 2 " +
            "WHERE treeId = :treeId " +
            "AND rightLink > :right ")
    fun updateAllNodesToRight(@Param("right") rightNum: Long,
                              @Param("treeId") treeId: Long)

    @Query("SELECT n FROM Node n " +
            "WHERE n.treeId = :treeId " +
            "AND n.rightLink > :right AND n.leftLink < :left " +
            "ORDER BY n.leftLink DESC")//TODO sort via pageable?
    fun findRootNode(@Param("left") leftNum: Long,
                     @Param("right") rightNum: Long,
                     @Param("treeId") treeId: Long,
                     pageable: Pageable) : Page<Node>

    @Query("SELECT n FROM Node n " +
            "WHERE n.treeId = :treeId " +
            "AND n.rightLink > :right AND n.leftLink < :left " +
            "ORDER BY n.leftLink ASC")//TODO sort via pageable?
    fun findDirectParent(@Param("left") leftNum: Long,
                         @Param("right") rightNum: Long,
                         @Param("treeId") treeId: Long,
                         pageable: Pageable) : Page<Node>
}
