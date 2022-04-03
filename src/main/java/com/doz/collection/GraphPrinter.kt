package com.doz.collection

import com.doz.model.BasicNode
import com.doz.model.SimpleTree
import hu.webarticum.treeprinter.SimpleTreeNode
import hu.webarticum.treeprinter.decorator.BorderTreeNodeDecorator
import hu.webarticum.treeprinter.printer.traditional.TraditionalTreePrinter
import org.springframework.stereotype.Component

@Component
class GraphPrinter {

    fun printAsText(nodesAsNestedSet: List<BasicNode>) {
        nodesAsNestedSet
            .sortedBy { it.leftLink }
            .forEach {
                println()
                println("name: ${it.name}")
                println("parentNode: ${ it.getParent()?.name ?: ""}")
                println("childNodes: ${it.getChildren().joinToString { iter -> iter.name }}")
                println("left:  ${it.leftLink}")
                println("right: ${it.rightLink}")
                println("depth: ${it.depth}")
                println()
            }
    }

    fun printAsGraph(rootNode: BasicNode) {
        val rootNodeAsPrintNode = simpleTreeNode(rootNode)
        collectAsSimplePrintTree(rootNode, rootNodeAsPrintNode)
        TraditionalTreePrinter().print(BorderTreeNodeDecorator(rootNodeAsPrintNode))
    }

    private fun collectAsSimplePrintTree(
        node: BasicNode,
        simpleTreeNode: SimpleTreeNode
    ) {
        if (node.getChildren().isEmpty()) {
            return
        }
        node.getChildren().map {
            val childSimpleTreeNode = simpleTreeNode(it)
            simpleTreeNode.addChild(childSimpleTreeNode)
            Pair(it, childSimpleTreeNode)
        }.forEach { (node, simpleTreeNode) -> collectAsSimplePrintTree(node, simpleTreeNode) }

    }

    private fun simpleTreeNode(node: BasicNode) =
        SimpleTreeNode("${node.name} Left: ${node.leftLink} Right: ${node.rightLink} Depth: ${node.depth}")

}