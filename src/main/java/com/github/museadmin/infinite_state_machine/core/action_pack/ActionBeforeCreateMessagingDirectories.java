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
      String msg_root = queryProperty("msg_root");
      createRunDirectory(msg_root);

      updateProperty(
        "msg_in",
        createRunDirectory(
          msg_root +
            File.separator +
            queryProperty("msg_in")
        )
      );

      updateProperty(
        "msg_in_processed",
        createRunDirectory(
          msg_root +
            File.separator +
            queryProperty("msg_in_processed")
        )
      );

      updateProperty(
        "msg_out",
        createRunDirectory(
          msg_root +
            File.separator +
            queryProperty("msg_out")
        )
      );

      updateProperty(
        "msg_out_processed",
        createRunDirectory(
          msg_root +
            File.separator +
            queryProperty("msg_out_processed")
        )
      );

      // Deactivate this action
      deactivate();
    }
  }
}
