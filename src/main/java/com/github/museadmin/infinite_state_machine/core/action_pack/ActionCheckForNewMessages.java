package com.github.museadmin.infinite_state_machine.core.action_pack;


import com.github.museadmin.infinite_state_machine.data.access.action.Action;

/**
 * Check if any new messages have arrived in the in directory
 */
public class ActionCheckForNewMessages extends Action {

  /**
   * The principal method for execution of the action
   */
  public void execute() {

    if (notActive()) {return;}

    System.out.println("In ActionCheckForNewMessages");
  }

}
