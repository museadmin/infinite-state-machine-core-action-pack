package com.github.museadmin.infinite_state_machine.core.action_pack;

import com.github.museadmin.infinite_state_machine.common.action.Action;
import com.github.museadmin.infinite_state_machine.common.action.IAction;

public class ActionConfirmReadyToRun extends Action implements IAction {

  /**
   * The principal method for execution of the action
   */
  public void execute() {
    System.out.println("In ActionConfirmReadyToRun");
  }

}
