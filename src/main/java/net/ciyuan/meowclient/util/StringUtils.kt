package net.ciyuan.meowclient.util

fun String.clearColors(): String
{
    return this.replace(Regex("\u00a7."), "")
}