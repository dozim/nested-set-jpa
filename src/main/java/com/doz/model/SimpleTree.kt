package com.doz.model

import javax.persistence.*

@Entity
@Table(name = "TREE")
class SimpleTree {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "simpletree_gen")
    @SequenceGenerator(name = "simpletree_gen", sequenceName = "simpletree_seq")
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "treeId", orphanRemoval = true)
    private val nodeEntities: MutableSet<NodeEntity> = mutableSetOf()

    fun addNodes(otherNodeEntities: List<NodeEntity>) {
        nodeEntities.addAll(otherNodeEntities)
    }
}
