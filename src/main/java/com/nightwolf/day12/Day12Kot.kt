package com.nightwolf.day12

import com.nightwolf.day12.Day12Kot.Element.Companion.e
import com.nightwolf.day12.Day12Kot.ElementDistance.Companion.ed
import java.util.*

class Day12Kot : Day12 {
    override fun answerOne(): Int {
        val grid = input().map { obj: String -> obj.toCharArray() }.toArray { s -> Array(s) { CharArray(0) } }
        var start = e(0, 0)
        var end = e(0, 0)
        for ((r, row) in grid.withIndex()) {
            for ((c, item) in row.withIndex()) {
                if (item == 'S') {
                    start = e(r, c)
                    grid[r][c] = 'a'
                } else if (item == 'E') {
                    end = e(r, c)
                    grid[r][c] = 'z'
                }
            }
        }
        val q = ArrayDeque<ElementDistance>().apply { add(ed(0, start.r, start.c)) }
        val vis = mutableSetOf(start)

        while (!q.isEmpty()) {
            val (d, r, c) = q.pollFirst()
            for ((nr, nc) in listOf(e(r + 1, c), e(r - 1, c), e(r, c + 1), e(r, c - 1))) {
                if (nr < 0 || nc < 0 || nr >= grid.size || nc >= grid[0].size) continue
                if (vis.contains(e(nr, nc))) continue
                if (grid[nr][nc].code - grid[r][c].code > 1) continue

                if (e(nr, nc) == end)
                    return d + 1

                vis.add(e(nr, nc))
                q.add(ed(d + 1, nr, nc))
            }
        }
        return -1
    }

    override fun answerTwo(): Int {
        val grid = input().map { obj: String -> obj.toCharArray() }.toArray { s -> Array(s) { CharArray(0) } }
        var end = Element(0, 0)
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == 'S') {
                    grid[i][j] = 'a'
                } else if (grid[i][j] == 'E') {
                    end = e(i, j)
                    grid[i][j] = 'z'
                }
            }
        }
        val q = ArrayDeque<ElementDistance>()
        q.add(ed(0, end.r, end.c))
        val vis = HashSet<Element>()
        vis.add(end)
        while (!q.isEmpty()) {
            val (d, r, c) = q.pollFirst()
            for (ne in listOf(e(r + 1, c), e(r - 1, c), e(r, c + 1), e(r, c - 1))) {
                if (ne.r < 0 || ne.c < 0 || ne.r >= grid.size || ne.c >= grid[0].size) continue
                if (vis.contains(ne)) continue
                if (grid[ne.r][ne.c].code - grid[r][c].code < -1) continue
                if (grid[ne.r][ne.c] == 'a') {
                    return d + 1
                }
                vis.add(e(ne.r, ne.c))
                q.add(ed(d + 1, ne.r, ne.c))
            }
        }
        return -1
    }

    @JvmRecord
    data class Element(val r: Int, val c: Int) {
        companion object {
            fun e(r: Int, c: Int): Element {
                return Element(r, c)
            }
        }
    }

    @JvmRecord
    data class ElementDistance(val d: Int, val r: Int, val c: Int) {
        companion object {
            fun ed(d: Int, r: Int, c: Int): ElementDistance {
                return ElementDistance(d, r, c)
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day12Kot()
            println("Answer 1: " + day.answerOne())
            println("Answer 2: " + day.answerTwo())
        }
    }
}