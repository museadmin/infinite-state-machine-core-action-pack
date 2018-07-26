package com.github.museadmin.infinite_state_machine.core.action_pack;

import com.github.museadmin.infinite_state_machine.common.action.IAction;

/**
 * New messages found in the in directory. Write them into the
 * messages table in the database.
 */
public class ActionPickUpInboundMessages implements IAction {

  /**
   * The principal method for execution of the action
   */
  public void execute() {
    System.out.println("In ActionPickUpInboundMessages");
  }


}
