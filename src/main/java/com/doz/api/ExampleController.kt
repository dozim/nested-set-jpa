package com.doz.api

import com.doz.NodeService
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController(private val nodeService: NodeService) {


    @GetMapping("example")
    fun runExample() {
        nodeService.example()
        nodeService.exampleSubTree()
    }

    @GetMapping("example/subtree")
    fun printSubTree(
        @Param("left") left: Int,
        @Param("right") right: Int,
        @Param("treeId") treeId: Long,
    ) {
        nodeService.printSubTree(left, right, treeId)
    }

    @GetMapping("example/leafs")
    fun printLeafs(
        @Param("left") left: Int,
        @Param("right") right: Int,
        @Param("treeId") treeId: Long,
    ) {
        nodeService.printLeafs(left, right, treeId)
    }

    @GetMapping("example/random")
    fun randomTree() {
        nodeService.createRandomTree()
    }
}