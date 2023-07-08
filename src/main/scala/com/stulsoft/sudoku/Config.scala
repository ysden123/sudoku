/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sudoku

import java.io.{File, FileInputStream, FileReader, FileWriter, OutputStream, OutputStreamWriter}
import java.util.Properties
import scala.util.{Failure, Success, Using}

object Config:
  private val configPath = s"""${System.getenv("APPDATA")}\\sudoku\\application.properties"""
  private val properties: Properties = new Properties()

  private val KEY_LEVEL = "level"
  private val DEFAULT_LEVEL = 2

  load()

  def level(): Int =
    if properties.containsKey(KEY_LEVEL) then
      properties.getProperty(KEY_LEVEL).toInt
    else
      DEFAULT_LEVEL

  def level_=(aLevel: Int): Unit =
    properties.put(KEY_LEVEL, aLevel.toString)

  private def load(): Unit =
    try
      val file = new File(configPath)
      file.getParentFile.mkdirs
      file.createNewFile

      Using(new FileInputStream(file)) { stream => properties.load(stream) }
      match
        case Success(_) =>
        case Failure(exception) =>
          exception.printStackTrace()
    catch
      case exception: Exception =>
        exception.printStackTrace()

  def save(): Unit =
    try
      val file = new File(configPath)
      Using(new FileWriter(file)) { writer => properties.store(writer, null) }
      match
        case Success(_) =>
        case Failure(exception) =>
          exception.printStackTrace()
    catch
      case exception: Exception =>
        exception.printStackTrace()