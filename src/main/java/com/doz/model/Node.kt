package com.doz.model

class Node(override var name: String) : BasicNode {
    var childNodes: MutableList<Node> = mutableListOf()
    var parentNode: Node? = null
    override var leftLink: Int = 0
    override var rightLink: Int = 0
    override var depth: Int = 0
}