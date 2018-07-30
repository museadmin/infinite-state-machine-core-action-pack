package com.github.museadmin.infinite_state_machine.core.action_pack;


import com.github.museadmin.infinite_state_machine.data.access.action.Action;

/**
 * New messages found in the in directory. Write them into the
 * messages table in the database.
 */
public class ActionPickUpInboundMessages extends Action {

  /**
   * The principal method for execution of the action
   */
  public void execute() {
    if (active()) {
      System.out.println("In ActionPickUpInboundMessages");
    }
  }
}
