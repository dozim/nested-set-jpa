package com.doz.collection

class NodeCollector<T> {

    fun getAllNodesFromRootNode(rootNode: T, getChildren: (node: T) -> List<T>): List<T> {
        return mutableListOf<T>()
            .apply {
                add(rootNode)
                addAll(getAllChildNodes(rootNode, getChildren))
            }
    }

    private fun getAllChildNodes(parentNode: T, getChildren: (node: T) -> List<T>): Collection<T> {
        val children = getChildren(parentNode)

        if (children.isEmpty()) {
            return mutableListOf()
        }

        val list = mutableListOf<T>()
        list.addAll(children)

        val recursiveNodes = children.flatMap { getAllChildNodes(it, getChildren) }

        list.addAll(recursiveNodes)

        return list
    }
}