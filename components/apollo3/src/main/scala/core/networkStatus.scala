package apollo.client3
package core
package networkStatus_module

@js.native
abstract trait NetworkStatus extends js.Any
object NetworkStatus {
    val loading = 1.asInstanceOf[NetworkStatus]
    val setVariables = 2.asInstanceOf[NetworkStatus]
    val fetchMore = 3.asInstanceOf[NetworkStatus]
    val refetch = 4.asInstanceOf[NetworkStatus]
    val poll = 6.asInstanceOf[NetworkStatus]
    val ready = 7.asInstanceOf[NetworkStatus]
    val error = 8.asInstanceOf[NetworkStatus]
}