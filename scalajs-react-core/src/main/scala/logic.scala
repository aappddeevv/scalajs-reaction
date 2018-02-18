// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

/*
/**
 * Logic for the react proxy component that can be separated
 * out to make entry points more flexible and the logic more pure.
 */
object componentlogic {
  type MakeState[S, RP, A, SLF, State <: TotalState[S, RP, A, SLF]] = (Option[S], Int, Int, Seq[SLF => Unit]) => State

  def transitionNextTotalState[S, RP, A, SLF, State <: TotalState[S, RP, A, SLF]](
      curTotalState: State,
      scalaStateUpdate: StateUpdate[S, SLF],
      mkState: MakeState[S, RP, A, SLF, State]): State = {
    scalaStateUpdate match {
      case NoUpdate() => curTotalState
      case Update(nextScalaState) =>
        mkState(nextScalaState, curTotalState.scalaStateVersion + 1, curTotalState.scalaStateVersionUsedToComputeSubelements, curTotalState.sideEffects)
      case SilentUpdate(nextScalaState) =>
        mkState(
          nextScalaState,
          curTotalState.scalaStateVersion + 1,
          curTotalState.scalaStateVersionUsedToComputeSubelements + 1,
          curTotalState.sideEffects
        )
      case SideEffects(effect) =>
        mkState(
          curTotalState.scalaState,
          curTotalState.scalaStateVersion + 1,
          curTotalState.scalaStateVersionUsedToComputeSubelements + 1,
          Seq(effect) ++ curTotalState.sideEffects
        )
      case UpdateWithSideEffects(nextScalaState, effect) =>
        mkState(
          nextScalaState,
          curTotalState.scalaStateVersion + 1,
          curTotalState.scalaStateVersionUsedToComputeSubelements,
          Seq(effect) ++ curTotalState.sideEffects
        )
      case SilentUpdateWithSideEffects(nextScalaState, effect) =>
        mkState(
          nextScalaState,
          curTotalState.scalaStateVersion + 1,
          curTotalState.scalaStateVersionUsedToComputeSubelements + 1,
          Seq(effect) ++ curTotalState.sideEffects
        )
      case _ => curTotalState
    }
  }

}
 */
