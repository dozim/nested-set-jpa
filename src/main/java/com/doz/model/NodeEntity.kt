package com.doz.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "Node", indexes = [
    Index(name = "idx_nodeentity_depth", columnList = "depth"),
   // Index(name = "idx_nodeentity_tree_id_left_unq", columnList = "TREE_ID, LEFT_LINK", unique = true),
   // Index(name = "idx_nodeentity_tree_id_right_unq", columnList = "TREE_ID, RIGHT_LINK", unique = true)
])
open class NodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long = 0

    @Column(name = "LEFT_LINK", nullable = false)
    open var leftLink: Int = 0

    @Column(name = "RIGHT_LINK", nullable = false)
    open var rightLink: Int = 0

    @Column(name = "depth", nullable = false)
    open var depth: Int? = null

    @JoinColumn(name = "TREE_ID", nullable = false, table = "TREE", referencedColumnName = "ID")
    open var treeId: Long = 0

    @OneToMany
    @JoinColumn(name = "node_id")
    open var childNodes: MutableList<NodeEntity> = mutableListOf()

    @OneToOne
    @JoinColumn(name = "parent_node_id")
    open var parentNode: NodeEntity? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as NodeEntity

        return id == other.id && id > 0
    }

    override fun hashCode(): Int = javaClass.hashCode()

}