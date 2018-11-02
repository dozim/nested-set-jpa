package com.doz

import com.doz.collection.GraphCreator
import com.doz.model.Node
import com.doz.model.SimpleTree
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Transactional
@Service
open class NodeService(@Autowired private val nodeRepository: NodeRepository,
                       @Autowired private val nodeHeaderRepository: NodeHeaderRepository) {


    @PostConstruct
    fun init() {
        val tree = SimpleTree()

        nodeHeaderRepository.save(tree)

        val rNode = Node(tree.id)
        val cNode1 = Node(tree.id)
        val cNode2 = Node(tree.id)
        val ccNode1 = Node(tree.id)
        val ccccNode1 = Node(tree.id)

        val graph = GraphCreator()
        graph.addRootNode(rNode)
        graph.addChildren(rNode, cNode1)
        graph.addChildren(rNode, cNode2)
        graph.addChildren(cNode1, ccNode1)
        graph.addChildren(ccNode1, ccccNode1)

        tree.addNodes(graph.nodesAsNestedSet)
        nodeHeaderRepository.save(tree)
    }

}
