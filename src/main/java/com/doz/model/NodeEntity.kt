package com.doz.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "Node", indexes = [
    Index(name = "idx_nodeentity_depth", columnList = "depth"),
   // Index(name = "idx_nodeentity_tree_id_left_unq", columnList = "TREE_ID, LEFT_LINK", unique = true),
   // Index(name = "idx_nodeentity_tree_id_right_unq", columnList = "TREE_ID, RIGHT_LINK", unique = true)
])
open class NodeEntity: BasicNode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nodeentity_gen")
    @SequenceGenerator(name = "nodeentity_gen", sequenceName = "nodeentity_seq")
    open var id: Long = 0

    @Column(name = "LEFT_LINK", nullable = false)
    override var leftLink: Int = 0

    @Column(name = "RIGHT_LINK", nullable = false)
    override var rightLink: Int = 0

    @Column(name = "depth", nullable = false)
    override var depth: Int = 0

    @Column(name = "name", nullable = false)
    override var name: String = ""

    @JoinColumn(name = "TREE_ID", nullable = false, table = "TREE", referencedColumnName = "ID")
    open var treeId: Long = 0

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "node_id")
    open var childNodes: MutableList<NodeEntity> = mutableListOf()

    @OneToOne(fetch = FetchType.LAZY)
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