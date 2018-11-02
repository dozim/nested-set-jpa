package com.doz.model

import java.util.ArrayList
import java.util.HashSet
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "TREE")
class SimpleTree {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "treeId", orphanRemoval = true)
    private val nodes: MutableSet<Node> = HashSet()

    val nodesImmutable: List<Node> get() = ArrayList(nodes)

    fun addNode(node: Node): Boolean = nodes.add(node)

    fun addNodes(otherNodes: Collection<Node>) {
        nodes.addAll(otherNodes)
    }
}
