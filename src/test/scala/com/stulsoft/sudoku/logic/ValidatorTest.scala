/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sudoku.logic

import com.stulsoft.sudoku.model.{Square, Table}
import org.scalatest.flatspec.AnyFlatSpec

class ValidatorTest extends AnyFlatSpec:
  "Validator" should "validate a square" in {
    val square = Square()
    assert(Validator.isValidSquare(square))

    square.cell(2, 1).value = Some(2)
    assert(Validator.isValidSquare(square))

    square.cell(1, 3).value = Some(2)
    assert(!Validator.isValidSquare(square))
  }

  it should "validate a value in a square" in {
    val square = Square()
    assert(Validator.isValidInSquare(square, None))
    assert(Validator.isValidInSquare(square, Some(9)))

    square.cell(2, 1).value = Some(2)
    assert(!Validator.isValidInSquare(square, Some(2)))
  }

  it should "validate a row" in {
    val table = Table()
    assert(Validator.isValidRow(table, 1))

    table.square(1,1).cell(1,2).value = Some(1)
    assert(Validator.isValidRow(table, 1))

    table.square(3,1).cell(1,2).value = Some(1)
    table.square(3,2).cell(1,2).value = Some(1)
    assert(!Validator.isValidRow(table, 7))
  }

  it should "validate a value in a row" in {
    val table = Table()
    assert(Validator.isValidInRow(table, 3, None))
    assert(Validator.isValidInRow(table, 3, Some(9)))

    table.square(2, 2).cell(2, 2).value = Some(4)
    assert(!Validator.isValidInRow(table, 5, Some(4)))

    assert(Validator.isValidInRow(table, 5, Some(5)))

    table.square(3, 3).cell(3, 3).value = Some(3)
    assert(!Validator.isValidInRow(table, 9, Some(3)))
  }

  it should "validate a column" in {
    val table = Table()
    assert(Validator.isValidColumn(table, 1))

    table.square(1, 1).cell(2, 1).value = Some(1)
    assert(Validator.isValidColumn(table, 1))

    table.square(2, 2).cell(1, 2).value = Some(1)
    table.square(3, 2).cell(2, 2).value = Some(1)
    assert(!Validator.isValidColumn(table, 5))
  }

  it should "validate a value in a column" in {
      val table = Table()

      assert(Validator.isValidInColumn(table, 3, None))
      assert(Validator.isValidInColumn(table, 3, Some(9)))

      table.square(2, 2).cell(2, 2).value = Some(4)
      assert(!Validator.isValidInColumn(table, 5, Some(4)))

      assert(Validator.isValidInColumn(table, 5, Some(5)))

      table.square(3, 3).cell(3, 3).value = Some(3)
      assert(!Validator.isValidInColumn(table, 9, Some(3)))
    }