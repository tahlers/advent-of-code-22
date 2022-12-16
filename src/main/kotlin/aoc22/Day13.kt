package aoc22

object Day13 {

    sealed interface Node : Comparable<Node> {

        fun toListNode(): ListNode

        data class IntNode(val int: Int) : Node {
            override fun toListNode(): ListNode = ListNode(listOf(this))
            override fun compareTo(other: Node): Int {
                if (other is IntNode) return compare(other).compareValue
                val right = other.toListNode()
                return if (right.list.isEmpty()) Order.HIGHER.compareValue
                else {
                    val left = this.toListNode()
                    val firstChildLeft = left.list[0]
                    val firstChildRight = right.list[0]
                    val compared = firstChildLeft.compareTo(firstChildRight)
                    if (compared == 0 && right.list.size > 1) Order.LOWER.compareValue else compared
                }
            }

            override fun toString(): String {
                return int.toString()
            }

            fun compare(other: IntNode): Order {
                return if (int == other.int) Order.EQUAL
                else if (int < other.int) Order.LOWER
                else Order.HIGHER
            }
        }

        data class ListNode(val list: List<Node> = emptyList()) : Node {
            operator fun plus(other: Node) = ListNode(list + other)
            override fun toListNode() = this
            override fun compareTo(other: Node): Int {
                val right = other.toListNode()
                return if (list.isEmpty() && right.list.isEmpty()) Order.EQUAL.compareValue
                else if (list.isEmpty()) Order.LOWER.compareValue
                else if (right.list.isEmpty()) Order.HIGHER.compareValue
                else {
                    val firstChildLeft = list[0]
                    val firstChildRight = right.list[0]
                    val compared = firstChildLeft.compareTo(firstChildRight)
                    if (compared == 0) {
                        ListNode(list.drop(1)).compareTo(ListNode(right.list.drop(1)))
                    } else compared
                }
            }

            override fun toString(): String {
                return list.joinToString(separator = ",", prefix = "[", postfix = "]")
            }
        }
    }

    enum class Order(val compareValue: Int) { LOWER(-1), EQUAL(0), HIGHER(1) }

    fun calcSumOfRightOrderPairs(input: String): Int {
        val pairs = parsePairs(input.trimEnd())
        val filtered = pairs.withIndex().filter { it.value.first.compareTo(it.value.second) == -1 }
        return filtered.map { it.index + 1 }.sum()
    }

    fun calcDecoderKey(input: String): Int {
        val divider1 = Node.ListNode(listOf(Node.ListNode(listOf(Node.IntNode(2)))))
        val divider2 = Node.ListNode(listOf(Node.ListNode(listOf(Node.IntNode(6)))))
        val pairs = parsePairs(input.trimEnd())
        val nodes = pairs.flatMap { listOf(it.first, it.second) } + divider1 + divider2
        val sorted = nodes.sorted()
        val divider1Index = sorted.indexOf(divider1) + 1
        val divider2Index = sorted.indexOf(divider2) + 1
        return divider1Index * divider2Index
    }

    private fun parsePairs(input: String): List<Pair<Node, Node>> {
        val pairSplit = input.split("\n\n")
        val pairLines = pairSplit.map { it.split('\n').toPair() }
        return pairLines.map { pair ->
            val first = parseToNode(Node.ListNode(), pair.first.drop(1)).first
            val second = parseToNode(Node.ListNode(), pair.second.drop(1)).first
            first to second
        }
    }

    private fun parseToNode(current: Node.ListNode, remaining: String): Pair<Node, String> {
        if (remaining.isEmpty()) return current to ""
        val nextChar = remaining.first()
        val tail = remaining.drop(1)
        return when {
            nextChar.isDigit() -> {
                if (remaining.startsWith("10")) {
                    val intNode = Node.IntNode(10)
                    val newCurrent = current + intNode
                    parseToNode(newCurrent, remaining.drop(2))
                } else {
                    val intNode = Node.IntNode(nextChar.digitToInt())
                    val newCurrent = current + intNode
                    parseToNode(newCurrent, tail)
                }
            }

            nextChar == ']' -> {
                current to tail
            }

            nextChar == '[' -> {
                val (newNode, newRemaining) = parseToNode(Node.ListNode(), tail)
                parseToNode(current + newNode, newRemaining)
            }

            else -> {
                parseToNode(current, tail)
            }
        }
    }

    private fun <T> List<T>.toPair(): Pair<T, T> {
        if (this.size < 2) throw IllegalStateException()
        return this[0] to this[1]
    }
}