package net.ciyuan.meowclient.`interface`

interface IChat : IModule
{
    val filter: Regex
    val skipRegexCheck: Boolean
    fun onMessage(message: String)
}