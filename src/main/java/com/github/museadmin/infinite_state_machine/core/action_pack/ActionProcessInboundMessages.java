package com.github.museadmin.infinite_state_machine.core.action_pack;

import com.github.museadmin.infinite_state_machine.data.access.action.Action;

/**
 * Unprocessed messages are in the messages table.
 */
public class ActionProcessInboundMessages extends Action {

  /**
   * The principal method for execution of the action
   */
  public void execute() {
    if (active()) {
      System.out.println("In ActionProcessInboundMessages");
    }
  }
}
