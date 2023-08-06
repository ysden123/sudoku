/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sudoku

import com.typesafe.scalalogging.StrictLogging

import java.io.{ByteArrayInputStream, InputStream}
import javax.sound.sampled.AudioSystem
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object Sound extends StrictLogging:
  given ExecutionContext = ExecutionContext.global

  private def readBinaryDataFromResource(resourceName: String): Try[ByteArrayInputStream] =
    Try {
      var inputStream: InputStream = null
      try {
        inputStream = getClass.getClassLoader.getResourceAsStream(resourceName)
        val buffer = Array.ofDim[Byte](1024 * 10) // You can adjust the buffer size as needed
        var bytesRead = 0
        val byteArrayStream = new java.io.ByteArrayOutputStream()

        while ( {
          bytesRead = inputStream.read(buffer)
          bytesRead
        } != -1) {
          byteArrayStream.write(buffer, 0, bytesRead)
        }
        new ByteArrayInputStream(byteArrayStream.toByteArray)
      }
      catch
        case exception: Exception =>
          logger.error(exception.getMessage, exception)
          throw exception
      finally {
        if (inputStream != null) {
          inputStream.close()
        }
      }
    }

  def playFanfare(): Unit =
    Future {
      readBinaryDataFromResource("sounds/fanfare.wav") match
        case Success(inputStream) =>
          try
            val clip = AudioSystem.getClip()
            clip.open(AudioSystem.getAudioInputStream(inputStream))
            clip.start()
          catch
            case exception: Exception =>
              logger.error(exception.getMessage, exception)
        case Failure(exception) =>
          logger.error("Data was not read")
    }