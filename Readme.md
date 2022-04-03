#  Preorder Tree Traversal also called Nested Set Model

This is an example how to implement the Nested Set Algorithm and how to persist it with JPA.
This is work in progress.

Call: GET localhost:8080/example to see

```

                                  ┌───────────────────────────────────┐
                                  │RootNode Left: 0 Right: 13 Depth: 0│
                                  └───────────────────────────────────┘
                 ┌──────────────────────────────────┴─────────────────┐
                 │                                                    │
 ┌────────────────────────────────┐                   ┌─────────────────────────────────┐
 │Child1 Left: 1 Right: 6 Depth: 1│                   │Child2 Left: 7 Right: 12 Depth: 1│
 └────────────────────────────────┘                   └─────────────────────────────────┘
 │                                  ┌─────────────────┴─────────────────┐
 │                                  │                                   │
 ┌────────────────────────────────┐ ┌────────────────────────────────┐ ┌──────────────────────────────────┐
 │Child3 Left: 2 Right: 5 Depth: 2│ │Child5 Left: 8 Right: 9 Depth: 2│ │Child6 Left: 10 Right: 11 Depth: 2│
 └────────────────────────────────┘ └────────────────────────────────┘ └──────────────────────────────────┘
 │
 │
 ┌────────────────────────────────┐
 │Child4 Left: 3 Right: 4 Depth: 3│
```


### Print a SubTree from an existing Tree

GET localhost:8080/example/subtree?left=7&right=12&treeId=1


### Print all leaves from an existing Tree

GET localhost:8080/example/leafs?left=0&right=13&treeId=1

### Generate a Random Tree

GET localhost:8080/example/random


Links: 
- https://en.wikipedia.org/wiki/Nested_set_model
- https://www.sitepoint.com/hierarchical-data-database-2/

Thanks to: hu.webarticum: tree-printer