package com.doz.model

interface BasicNode {
    var leftLink: Int
    var rightLink: Int
    var depth: Int
    var name: String

    fun getChildren(): List<BasicNode> =
        when (this) {
            is Node -> childNodes
            is NodeEntity -> childNodes
            else -> listOf()
        }

    fun getParent(): BasicNode? =
        when (this) {
            is Node -> parentNode
            is NodeEntity -> parentNode
            else -> null
        }
}