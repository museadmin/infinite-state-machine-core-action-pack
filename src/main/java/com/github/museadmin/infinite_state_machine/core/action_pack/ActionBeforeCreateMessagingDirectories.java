package com.github.museadmin.infinite_state_machine.core.action_pack;


import com.github.museadmin.infinite_state_machine.data.access.action.IAction;

/**
 * Check if any new messages have arrived in the in directory
 */
public class ActionBeforeCreateMessagingDirectories implements IAction {

  /**
   * The principal method for execution of the action
   */
  public void execute() {
    System.out.println("In ActionBeforeCreateMessagingDirectories");


  }

}
