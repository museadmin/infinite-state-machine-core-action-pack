package com.github.museadmin.infinite_state_machine.core.action_pack;

import com.github.museadmin.infinite_state_machine.data.access.action.Action;
import com.github.museadmin.infinite_state_machine.data.access.action.IAction;

import java.util.ArrayList;

/**
 * Action that verifies the state of the ISM and confirms
 * we have booted up correctly and can now run
 */
public class ActionConfirmReadyToRun extends Action implements IAction {

  /**
   * Confirm we've got through the bootstrapping process ok.
   * All BEFORE_ actions completed
   */
  public void execute() {

    if (actionIsNotActive()) {return;}

    ArrayList <String> results = dataAccessLayer.executeSqlQuery(
      "select id from actions where action like = 'BEFORE_%'" +
        " and active = '" + SQLTRUE + "';"
    );

    if (results == null || results.size() == 0) {return;}

    dataAccessLayer.executeSqlStatement(
      "update states set state = '" + SQLTRUE + "' where " +
        "state_flag = 'READY_TO_RUN';"
    );

    deactivate("CONFIRM_READY_TO_RUN");
  }

}
