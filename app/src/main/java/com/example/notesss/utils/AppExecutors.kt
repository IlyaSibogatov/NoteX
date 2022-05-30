package com.example.notesss.utils

import java.util.concurrent.Executors

object AppExecutor {
    val ioExecutor = Executors.newFixedThreadPool(4)
}