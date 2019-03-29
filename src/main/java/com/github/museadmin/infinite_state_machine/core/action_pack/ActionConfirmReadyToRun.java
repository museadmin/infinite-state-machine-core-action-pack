package com.github.museadmin.infinite_state_machine.core.action_pack;

import com.github.museadmin.infinite_state_machine.common.action.Action;

/**
 * Action that verifies the state of the ISM and confirms
 * we have booted up correctly and can now run
 */
public class ActionConfirmReadyToRun extends Action {

  /**
   * Confirm we've got through the bootstrapping process ok.
   * e.g. All *Before* actions completed
   */
  public void execute() {
    if (active()) {
      if (beforeActionsComplete()) {
        setState("READY_TO_RUN");
        updateRunPhase("RUNNING");
        deactivate();
      }
    }
  }
}
