package com.doz.model

open class Node(val name: String) {
    var childNodes: MutableList<Node> = mutableListOf()
    open lateinit var parentNode: Node
    var leftLink: Int = 0
    var rightLink: Int = 0
    var depth: Int = 0
}

class RootNode(name: String) : Node(name) {
    override var parentNode: Node
        get() = throw UnsupportedOperationException("RootNode has no parent")
        set(value) {}
}