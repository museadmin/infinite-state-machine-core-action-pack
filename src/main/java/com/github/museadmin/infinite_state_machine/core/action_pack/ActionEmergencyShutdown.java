package com.github.museadmin.infinite_state_machine.core.action_pack;


import com.github.museadmin.infinite_state_machine.common.action.Action;

public class ActionEmergencyShutdown extends Action {

  /**
   * Perform an immediate stop. Ideally, this is only triggered when a bug in the framework
   * is encountered and it can no longer be trusted to perform a graceful stop.
   */
  public void execute() {
    if (active()) {
      updateRunPhase("STOPPED");
      System.out.println("In ActionEmergencyShutdown");
    }
  }
}
