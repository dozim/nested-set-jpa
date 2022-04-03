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

        val rootNode = Node(tree.id)
        val childNode1 = Node(tree.id)
        val childNode2 = Node(tree.id)
        val childNode3 = Node(tree.id)
        val childNode4 = Node(tree.id)

        val graph = GraphCreator()
        graph.addRootNode(rootNode)
        graph.addChildren(rootNode, childNode1)
        graph.addChildren(rootNode, childNode2)
        graph.addChildren(childNode1, childNode3)
        graph.addChildren(childNode3, childNode4)

        tree.addNodes(graph.nodesAsNestedSet)
        nodeHeaderRepository.save(tree)
    }

}
