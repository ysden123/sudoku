/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sudoku.logic

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration

object Util:
  /**
   * Calculates a number of a square (1,2 or 3) vertically or horizontally.
   *
   * @param index a row or a column (1 -9)
   * @return the number of a square (1,2 or 3).
   */
  def squareIndex(index: Int): Int =
    Math.ceil(index.toDouble / 3.0).toInt

  /**
   * Calculates a number of a cell (1,2 or 3) vertically or horizontally.
   *
   * @param index a row or a column (1 -9)
   * @return the number of cell (1,2 or 3)
   */
  def indexInSquare(index: Int): Int =
    var anIndex = index % 3
    if anIndex == 0 then anIndex = 3
    anIndex

  def durationToString(start: Long, finish: Long): String =
    val duration = Duration(finish - start, TimeUnit.MILLISECONDS)
    f"${duration.toHours}%02d:${duration.toMinutes}%02d:${duration.toSeconds}%02d:${duration.toMillis}%02d"
