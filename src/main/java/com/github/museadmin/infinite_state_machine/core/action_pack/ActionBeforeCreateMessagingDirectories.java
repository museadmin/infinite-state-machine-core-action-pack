package com.github.museadmin.infinite_state_machine.core.action_pack;


import com.github.museadmin.infinite_state_machine.data.access.action.Action;

import java.io.File;

/**
 * Check if any new messages have arrived in the in directory
 */
public class ActionBeforeCreateMessagingDirectories extends Action {

  /**
   * The principal method for execution of the action
   */
  public void execute() {
    if (active()) {
      // Create the messaging directories as defined in the properties table
      String root = queryProperty("msg_root");
      createRunDirectory(root);

      String dir = queryProperty("msg_in");
      createRunDirectory(root + File.separator + dir);
      insertProperty("msg_in", root + File.separator + dir);

      dir = queryProperty("msg_in_processed");
      createRunDirectory(root + File.separator + dir);
      insertProperty("msg_in_processed", root + File.separator + dir);

      dir = queryProperty("msg_out");
      createRunDirectory(root + File.separator + dir);
      insertProperty("msg_out", root + File.separator + dir);

      dir = queryProperty("msg_out_processed");
      createRunDirectory(root + File.separator + dir);
      insertProperty("msg_out_processed", root + File.separator + dir);

      // Deactivate this action
      deactivate();
    }
  }
}
