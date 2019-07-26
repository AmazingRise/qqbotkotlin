package main

import util.Prompt

fun main(args: Array<String>) {
    network.Server().start(8080)
    Prompt.run()
}