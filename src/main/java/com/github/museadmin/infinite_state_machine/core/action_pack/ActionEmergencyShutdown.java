package com.github.museadmin.infinite_state_machine.core.action_pack;


import com.github.museadmin.infinite_state_machine.action.Action;

public class ActionEmergencyShutdown extends Action {

  /**
   * The principal method for execution of the action
   */
  public void execute() {
    if (active()) {
      System.out.println("In ActionEmergencyShutdown");
    }
  }
}
