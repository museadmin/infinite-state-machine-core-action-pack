package com.github.museadmin.infinite_state_machine.core.action_pack;


import com.github.museadmin.infinite_state_machine.action.Action;

/**
 * Process a normal shutdown of the state machine
 */
public class ActionNormalShutdown extends Action {

  /**
   * The principal method for execution of the action
   */
  public void execute() {
    if (active()) {
      updateRunPhase("NORMAL_SHUTDOWN");
      if (afterActionsComplete()) {
        updateRunPhase("STOPPED");
      }
    }
  }
}
