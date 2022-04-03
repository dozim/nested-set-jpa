package com.doz

import com.doz.collection.GraphCreatorForNode
import com.doz.collection.NodeCollector
import com.doz.model.Node
import com.doz.model.NodeEntity
import com.doz.model.SimpleTree
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Transactional
@Service
open class NodeService(
    @Autowired private val nodeRepository: NodeRepository,
    @Autowired private val simpleTreeRepository: SimpleTreeRepository
) {

    @PostConstruct
    fun example() {

        val graph = GraphCreatorForNode()
        val rootNode = graph.createRootNode()

        val childNode1 = Node("childNode1")
        val childNode2 = Node("childNode2")
        val childNode3 = Node("childNode3")
        val childNode4 = Node("childNode4")

        graph.addChildren(rootNode, childNode1)
        graph.addChildren(rootNode, childNode2)
        graph.addChildren(childNode1, childNode3)
        graph.addChildren(childNode3, childNode4)

        val nodesAsNestedSet = graph.nodesAsNestedSet().sortedBy { it.leftLink }

        nodesAsNestedSet.forEach {
            println("name: ${it.name}")
            println("parentNode: ${if (it != rootNode) it.parentNode.name else ""}")
            println("childNodes: ${it.childNodes.joinToString { iter -> iter.name }}")
            println("left:  ${it.leftLink}")
            println("right: ${it.rightLink}")
            println("depth: ${it.depth}")
            println()
        }

        saveNodes(rootNode)
    }

    fun saveNodes(rootNode: Node) {
        val tree = SimpleTree()
        simpleTreeRepository.save(tree)

        val rootNodeEntity = transformNode(null, rootNode, tree)
        val allNodes = NodeCollector<NodeEntity>().getAllNodesFromRootNode(rootNodeEntity, NodeEntity::childNodes)

        tree.addNodes(allNodes)
        simpleTreeRepository.save(tree)
    }

    private fun transformNode(parent: NodeEntity?, node: Node, tree: SimpleTree): NodeEntity {
        return NodeEntity().apply {
            leftLink = node.leftLink
            rightLink = node.rightLink
            depth = node.depth
            treeId = tree.id
            parentNode = parent
            childNodes.addAll(node.childNodes.map { transformNode(this, it, tree) })
        }
    }

}
