# Binary Search Tree (BST) - Symbol Table

## Project Overview
This project consists of a custom implementation of a **Symbol Table** using a **Binary Search Tree (BST)** as the underlying data structure. It was developed for the **Algorithms and Data Structures** course during the 1st year of my BSc in Computer Engineering at **Iscte**.

A Symbol Table associates unique keys with specific values (e.g., a URL to an IP address), providing efficient mechanisms for data insertion and retrieval.

<p align="center">
  <img src="https://github.com/user-attachments/assets/c6335773-52aa-494f-bbb3-78e1daf09a57" alt="Binary Search Tree Structure" width="400"/>
</p>

## Technical Features
* **BST Invariant**: Ensures that for every node, all keys in the left subtree are smaller and all keys in the right subtree are larger.
* **Recursive Operations**: Implementation of core methods like `put(Key key, Value val)` and `get(Key key)` using recursive logic to maintain tree integrity.
* **Dynamic Node Management**: Uses an internal `Node` class to manage references (`root`, `left`, `right`) and store generic Key-Value pairs.
* **Generic Types**: Designed to work with generic types `<Key, Value>`, ensuring flexibility for different data formats.

## Complexity Analysis
The efficiency of this implementation is directly proportional to the **height of the tree** ($h$).
* **Search (get)**: Average case $O(\log n)$, worst case $O(n)$ if the tree becomes a linked list.
* **Insertion (put)**: Average case $O(\log n)$, worst case $O(n)$.

## How to Run
1.  Ensure you have **Java JDK** installed.
2.  Compile the source file:
    ```bash
    javac ST.java
    ```
3.  Include this class in your project to use it as a standard Symbol Table.

## Academic Context
* **University**: Iscte - Instituto Universitário de Lisboa
* **Course**: BSc in Computer Engineering (LEI)
* **Subject**: Algorithms and Data Structures - 1st Year, 2nd Semester
