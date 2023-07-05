/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sudoku.model

import com.stulsoft.sudoku.model.Table
import org.scalatest.flatspec.AnyFlatSpec

class TableTest extends AnyFlatSpec:
  "Table" should "update a cell value" in {
    val table = Table()

    table.updateCell(5, 5, Some(5))
    var value = table.square(2, 2).cell(2, 2).value
    assert(value.isDefined && (5 == value.get))

    table.updateCell(6, 6, Some(6))
    value = table.square(2, 2).cell(3, 3).value
    assert(value.isDefined && (6 == value.get))

    table.updateCell(4, 7, Some(7))
    value = table.square(2, 3).cell(1, 1).value
    assert(value.isDefined && (7 == value.get))
  }

  it should "clear a table" in {
    val table = Table()

    table.updateCell(7, 6, Some(7))
    assert(table.square(3, 2).cell(1, 3).value.isDefined
      && table.square(3, 2).cell(1, 3).value.get == 7)

    table.clear()
    assert(table.square(3, 2).cell(1, 3).value.isEmpty)
  }

  it should "initialize a table" in {
    val table = Table()
    table.initialize()
  }