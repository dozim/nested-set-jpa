package com.doz.collection

import com.doz.model.Node
import com.doz.model.NodeEntity
import com.doz.model.RootNode
import java.util.*

class GraphCreatorForNode {
    private lateinit var rootNode: RootNode

    fun createRootNode(): Node {
        rootNode = RootNode("RootNode")
        return rootNode
    }

    fun addChildren(parent: Node, vararg newChildren: Node) {
        if (parent !== rootNode && parentNodeNotInGraph(rootNode)) {
            throw UnsupportedOperationException("Parent node does not exist in graph")
        } else {
            val childNodes = parent.childNodes
            Arrays.stream(newChildren)
                .forEach { node: Node ->
                    childNodes.add(node)
                    node.parentNode = parent
                    node.depth = parent.depth + 1
                }
        }
    }

    private fun parentNodeNotInGraph(node: Node?): Boolean {
        return if (node!!.childNodes.contains(node)) {
            false
        } else node.childNodes
            .stream()
            .anyMatch { node: Node? -> parentNodeNotInGraph(node) }
    }

    fun removeNode(nodeEntity: NodeEntity?) {}

    private fun applyNestedSetAlgorithm(node: Node?, count: Int): Int {
        var count = count
        node!!.leftLink = count++
        for (childNode in node.childNodes) {
            count = applyNestedSetAlgorithm(childNode, count)
        }
        node.rightLink = count++
        return count
    }

    fun nodesAsNestedSet(): List<Node> {
        applyNestedSetAlgorithm(rootNode, 0)
        return NodeCollector<Node>().getAllNodesFromRootNode(rootNode, Node::childNodes)
    }


}