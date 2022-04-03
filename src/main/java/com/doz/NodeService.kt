package com.doz

import com.doz.collection.GraphCreator
import com.doz.collection.GraphPrinter
import com.doz.collection.NodeCollector
import com.doz.model.BasicNode
import com.doz.model.Node
import com.doz.model.NodeEntity
import com.doz.model.SimpleTree
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Collectors
import java.util.stream.IntStream
import kotlin.random.Random

@Transactional
@Service
open class NodeService(
    private val nodeRepository: NodeRepository,
    private val simpleTreeRepository: SimpleTreeRepository,
    private val graphPrinter: GraphPrinter
) {

    fun example() {
        val graph = GraphCreator()
        val rootNode = graph.createRootNode()

        createExample(graph, rootNode)

        val nodesAsNestedSet = graph.nodesAsNestedSet()

        graphPrinter.printAsText(nodesAsNestedSet)
        graphPrinter.printAsGraph(rootNode as BasicNode)

        saveNodes(rootNode)
    }

    fun exampleSubTree() {
        val findSubTree = nodeRepository.findSubTree(7, 12, 1)
        val subTreeRoot = findSubTree.minByOrNull { it.leftLink }!!
        graphPrinter.printAsText(findSubTree)
        graphPrinter.printAsGraph(subTreeRoot)
    }

    private fun createExample(graph: GraphCreator, rootNode: Node) {
        val child1 = Node("Child1")
        val child2 = Node("Child2")
        val child3 = Node("Child3")
        val child4 = Node("Child4")
        val child5 = Node("Child5")
        val child6 = Node("Child6")

        graph.addChildren(rootNode, child1)
        graph.addChildren(rootNode, child2)
        graph.addChildren(child1, child3)
        graph.addChildren(child3, child4)
        graph.addChildren(child2, child5, child6)
    }


    fun saveNodes(rootNode: Node) {
        val tree = SimpleTree()
        simpleTreeRepository.save(tree)

        val rootNodeEntity = transformNode(null, rootNode, tree)
        val allNodes = NodeCollector<NodeEntity>().getAllNodesFromRootNode(rootNodeEntity, NodeEntity::childNodes)

        tree.addNodes(allNodes)
        simpleTreeRepository.save(tree)
        println("Tree was generated with id: ${tree.id} and has ${allNodes.size} Nodes")
    }

    private fun transformNode(parent: NodeEntity?, node: Node, tree: SimpleTree): NodeEntity {
        return NodeEntity().apply {
            leftLink = node.leftLink
            rightLink = node.rightLink
            depth = node.depth
            name = node.name
            treeId = tree.id
            parentNode = parent
            childNodes.addAll(node.childNodes.map { transformNode(this, it, tree) })
        }
    }

    fun printSubTree(left: Int, right: Int, treeId: Long) {
        val subTree = this.nodeRepository.findSubTree(
            leftNum = left,
            rightNum = right,
            treeId = treeId
        )

        subTree
            .sortedBy { it.leftLink }
            .first()
            .also { graphPrinter.printAsGraph(it) }

    }

    fun printLeafs(left: Int, right: Int, treeId: Long) {
        val leafNodes = this.nodeRepository.findLeafNodes(
            leftNum = left,
            rightNum = right,
            treeId = treeId
        )

        graphPrinter.printAsText(leafNodes)
    }

    fun createRandomTree() {
        val maxNodes = Random.nextInt(1, 30)
        val graph = GraphCreator()
        val rootNode = graph.createRootNode()

        createRandomTree(AtomicInteger(1), maxNodes, graph, rootNode)

        val nodesAsNestedSet = graph.nodesAsNestedSet()

        graphPrinter.printAsText(nodesAsNestedSet)
        graphPrinter.printAsGraph(rootNode as BasicNode)

        saveNodes(rootNode)
    }

    fun createRandomTree(alreadyCreatedNodes: AtomicInteger, maxNodes: Int, graph: GraphCreator, parenNode: Node) {
        if (alreadyCreatedNodes.get() > maxNodes) return

        val childNodes = IntStream.range(1, Random.nextInt(2, 7)).mapToObj {
            val node = Node("Node${alreadyCreatedNodes.getAndIncrement()}")
            graph.addChildren(parenNode, node)
            node
        }.collect(Collectors.toList())

        if (alreadyCreatedNodes.get() > maxNodes) return

        childNodes.forEach { createRandomTree(alreadyCreatedNodes, maxNodes, graph, it) }
    }

}
