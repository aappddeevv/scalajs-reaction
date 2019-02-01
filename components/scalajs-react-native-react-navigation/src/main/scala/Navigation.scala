// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package native
package navigation

import scala.scalajs.js
import js.annotation._
import js.|

import native.styling._

@js.native
trait NavigationActions extends js.Object {
  def navigate[T <: js.Object](params: NavigateParams[T]): Action = js.native
}


@js.native
trait NavigationJS extends js.Object {
  def createStackNavigator[T <: js.Object](config: RouteConfigs,
    options: StackNavigatorConfig[T]): Navigator = js.native
  def createAppContainer(c: Navigator): ReactJsComponent = js.native
  val NavigationActions: NavigationActions = js.native
}

trait NavigatorProps[T] extends js.Object {
  var screenProps: js.UndefOr[T] = js.undefined
}

// this component gets passed NavigatorProps[T]
@js.native
trait Navigator extends ReactJsComponent

@js.native
sealed trait GestureDirection extends js.Any
object GestureDirection {
  val horizontal = "horizontal".asInstanceOf[GestureDirection]
  val vertical = "vertical".asInstanceOf[GestureDirection]
}

trait NavigationOptionsRec[T <: js.Object] extends js.Object {
  var title: js.UndefOr[String] = js.undefined
  @JSName("header")
    // takes HeaderProps???
  val headerFunc: js.UndefOr[js.Function1[js.Object, ReactJsComponent]] = js.undefined
  var header: js.UndefOr[ReactType|Null] = js.undefined // setting to null hides header
  var headerTitle: js.UndefOr[ReactType] = js.undefined
  var headerTitleAllowFontScaling: js.UndefOr[Boolean] = js.undefined
  var headerBackImage: js.UndefOr[ReactType] = js.undefined
  var headerBackTitle: js.UndefOr[String] = js.undefined
  var headerTruncatedBackTitle: js.UndefOr[String] = js.undefined
  var headerRight: js.UndefOr[ReactType] = js.undefined
  var headerLeft: js.UndefOr[ReactType] = js.undefined
  var headerStyle: js.UndefOr[StyleProp[ViewStyle]] = js.undefined
  var headerForceInset: js.UndefOr[Double] = js.undefined
  var headerTitleStyle: js.UndefOr[TextStyle] = js.undefined
  var headerLeftContainerStyle: js.UndefOr[StyleProp[ViewStyle]] = js.undefined
  var headerRightContainerStyle: js.UndefOr[StyleProp[ViewStyle]] = js.undefined
  var headerTitleContainerStyle: js.UndefOr[StyleProp[ViewStyle]] = js.undefined
  var headerTintColor: js.UndefOr[String] = js.undefined
  var headerPressColorAndroid: js.UndefOr[String] = js.undefined
  var headerTransparent: js.UndefOr[Boolean] = js.undefined
  var headerBackground: js.UndefOr[String] = js.undefined
  // make enum
  var headerBackgroundTransitionPreset: js.UndefOr[String] = js.undefined
  var gesturesEnable: js.UndefOr[Boolean] = js.undefined
  var gestureDirection: js.UndefOr[GestureDirection] = js.undefined
  var params: js.UndefOr[T] = js.undefined
}

trait StackNavigatorConfig[T <: js.Object] extends js.Object {
  var initialRouteName: js.UndefOr[String] = js.undefined
  // initialRouteParams
  var initialRouteKey: js.UndefOr[String] = js.undefined
  var defaultNavigationOptions: js.UndefOr[NavigationOptions[T]] = js.undefined
  //var paths
  var mode: js.UndefOr[String] = js.undefined
  var headerMode: js.UndefOr[String] = js.undefined
  var heaederBackTitleVisible: js.UndefOr[Boolean] = js.undefined
  var headerTransitionPreset: js.UndefOr[String] = js.undefined
  var headerLayoutPreset: js.UndefOr[String] = js.undefined
  var cardStyle: js.UndefOr[ViewStyle] = js.undefined
  var cardShadowEnabled: js.UndefOr[Boolean] = js.undefined
  var cardOverlayEnabled: js.UndefOr[Boolean] = js.undefined
  var transitionConfig: js.UndefOr[js.Object] = js.undefined
  var onTransitionStart: js.UndefOr[js.Function0[Unit]] = js.undefined
  var onTransitionEnd: js.UndefOr[js.Function0[Unit]] = js.undefined  
  var transparentCard: js.UndefOr[js.Object] = js.undefined
}

trait RouteConfig[T <: js.Object] extends js.Object {
  val screen: ReactJsComponent
  var path: js.UndefOr[String] = js.undefined
  var navigationOptions: js.UndefOr[NavigationOptions[T]] = js.undefined
}

@js.native
trait State[T <: js.Object] extends js.Object {
  val routeName: String = js.native
  val key: String = js.native
  // null of no params provided, or undefined?
  val params: T|Null = js.native
}

@js.native
trait StackNavController extends js.Object {
  def dismiss(): Unit = js.native
  def pop(n: Int): Unit = js.native
  def popToTop(): Unit = js.native // top of stack
  def push[T <: js.Object](route: String, params: js.UndefOr[T] = js.undefined): Unit = js.native
  def reset(): Unit = js.native  
}

@js.native
trait DrawerNavController extends js.Object {
  def openDrawer(): Unit = js.native
  def closeDrawer(): Unit = js.native
  def toggleDrawer(): Unit = js.native
}

@js.native
sealed trait NavEventName extends js.Any
object NavEventName {
  val willFocus = "willFocus".asInstanceOf[NavEventName]
  val didFocus = "didFocus".asInstanceOf[NavEventName]
  val willBlur = "willBlr".asInstanceOf[NavEventName]
  val didBlur = "didBlr".asInstanceOf[NavEventName]
}

@js.native
trait Listener extends js.Object {
  def remove(): Unit = js.native
}

@js.native
trait ListenerEvent extends js.Object {
  // action
  // keyboard
  // lastState
  // state
  val `type`: NavEventName = js.native
}

@js.native
trait Action extends js.Object {
}

@js.native
trait NavController extends js.Object {
  def addListener(eventName: NavEventName, cb: js.Function1[ListenerEvent, Unit]): Listener = js.native
  def dangerouslyGetParent(): Navigator = js.native
  // must use NavigationActions to create this param
  def dispatch(action: Action): Unit = js.native
  def getParam[T](key: String, defaultValue: T): T = js.native
  def goBack(key: js.UndefOr[String|Null] = js.undefined): Unit = js.native
  def isFocused(): Boolean = js.native
  // add actions param
  def navigate[T <: js.Object](to: String, params: js.UndefOr[T] = js.undefined): Unit = js.native
  @JSName("navigate")
  def navigateWith[T <: js.Object](params: NavigateParams[T]): Unit = js.native
  def state[T <: js.Object]: State[T] = js.native
}

// alternate params for NavController.navigate
trait NavigateParams[T] extends js.Object {
  var routeName: js.UndefOr[String] = js.undefined
  var params: js.UndefOr[T] = js.undefined
  //var action ...
  var key: js.UndefOr[String] = js.undefined
}

@js.native
trait StackNavigatorNavProps extends js.Object {
  def screenProps[T <: js.Object]: T = js.native
}

/** Passed to screens that navigator routes to. */
@js.native // ?
trait NavProps extends js.Object {
  val navigation: NavController = js.native
}

@js.native
@JSImport("react-navigation", JSImport.Namespace)
object ReactNavigation extends NavigationJS
