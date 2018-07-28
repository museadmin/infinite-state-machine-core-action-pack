package com.github.museadmin.infinite_state_machine.core.action_pack;


import com.github.museadmin.infinite_state_machine.data.access.action.Action;
import com.github.museadmin.infinite_state_machine.data.access.action.IAction;

/**
 * Process a normal shutdown of the state machine
 */
public class ActionNormalShutdown extends Action implements IAction {

  /**
   * The principal method for execution of the action
   */
  public void execute() {
    System.out.println("In ActionNormalShutdown");
  }

}
