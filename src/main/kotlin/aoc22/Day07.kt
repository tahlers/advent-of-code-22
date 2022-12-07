package aoc22

import io.vavr.collection.HashSet
import io.vavr.collection.List
import io.vavr.collection.Set
import io.vavr.kotlin.toVavrList

sealed interface DirStructure {
    val name: String
}
data class File(override val name: String, val size: Int): DirStructure
data class Dir(override val name: String, val content: Set<DirStructure> = HashSet.empty()): DirStructure {
    fun allDirs(): List<Dir> = List.of(this).appendAll(content.toList()
        .filter { it is Dir }
        .flatMap { (it as Dir).allDirs() }
    )
}

object Day07 {

    fun calcDirSizeUpTo(input: String, size: Int = 100000): Int {
        val lines = input.trimEnd().lines().toVavrList()
        val dirStructure = parseIntoDir(lines)
        val filteredDirs = dirStructure.allDirs().map { sizeOfDir(it) }
            .filter { it <= size }
        return filteredDirs.sum().toInt()
    }

    fun calcDirSizeOfSmallest(input: String): Int {
        val lines = input.trimEnd().lines().toVavrList()
        val dirStructure = parseIntoDir(lines)
        val rootSize = sizeOfDir(dirStructure)
        val missing = 30000000 - (70000000 - rootSize)
        val filteredDirs = dirStructure.allDirs().map { sizeOfDir(it) }
            .filter { it >= missing }
        return filteredDirs.sorted().first()
    }

    private fun sizeOfDir(dir: Dir): Int {
        return dir.content.map { dirStructure ->
            when (dirStructure) {
                is File -> dirStructure.size
                is Dir -> sizeOfDir(dirStructure)
            }
        }.sum().toInt()
    }

    private tailrec fun parseIntoDir(
        input: List<String>,
        parentDirs: List<Dir> = List.empty()
    ): Dir {
        if (input.isEmpty) return collapseAll(parentDirs)
        val (head, tail) = input.head() to input.tail()
        return when {
            head == "\$ cd .." -> {
                parseIntoDir(tail, collapseDirs(parentDirs))
            }
            head == "\$ ls" -> parseIntoDir(tail, parentDirs)
            head.startsWith("\$ cd ") -> {
                val name = head.substringAfter("cd ")
                parseIntoDir(tail, parentDirs.append(Dir(name)))
            }
            head.startsWith("dir ") -> {
                val name = head.substringAfter("dir ")
                val newDir = Dir(name)
                val currentDir = parentDirs.last()
                val newCurrent = currentDir.copy(content = currentDir.content.add(newDir))
                parseIntoDir(tail, parentDirs.init().append(newCurrent))
            }
            else -> {
                val (size, name) = head.split(" ")
                val currentDir = parentDirs.last()
                val newCurrent = currentDir.copy(content = currentDir.content.add(File(name, size.toInt())))
                parseIntoDir(tail, parentDirs.init().append(newCurrent))
            }
        }
    }

    private fun collapseDirs(parentDirs: List<Dir>): List<Dir> {
        if (parentDirs.size() < 2) return parentDirs
        val currentDir = parentDirs.last()
        val parentDir = parentDirs.init().last()
        val dirInParentDir = parentDir.content.find { it.name == currentDir.name }.get() as Dir
        val newContent = parentDir.content.replace(dirInParentDir, currentDir)
        val updatedParent = parentDir.copy(content = newContent)
        return parentDirs.init().init().append(updatedParent)
    }

    private tailrec fun collapseAll(parentDirs: List<Dir>): Dir {
        if (parentDirs.size() == 1) return parentDirs[0]
        return collapseAll(collapseDirs(parentDirs))
    }
}