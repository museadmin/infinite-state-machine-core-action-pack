package com.github.museadmin.infinite_state_machine.core.action_pack;

import com.github.museadmin.infinite_state_machine.common.action.IAction;

/**
 * Unprocessed messages are in the messages table.
 */
public class ActionProcessInboundMessages implements IAction {

  /**
   * The principal method for execution of the action
   */
  public void execute() {
    System.out.println("In ActionProcessInboundMessages");
  }

}
