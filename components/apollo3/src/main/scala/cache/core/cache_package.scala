package apollo
package client3
package cache_module
package core_module

package object cache_module {
    import cache_module._
    type Transaction[T] = js.Function1[ApolloCache[T], Unit]
}