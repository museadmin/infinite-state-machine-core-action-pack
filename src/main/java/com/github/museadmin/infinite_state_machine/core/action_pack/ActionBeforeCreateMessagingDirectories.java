package com.github.museadmin.infinite_state_machine.core.action_pack;


import com.github.museadmin.infinite_state_machine.common.action.Action;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Check if any new messages have arrived in the in directory
 */
public class ActionBeforeCreateMessagingDirectories extends Action {

  /**
   * Create the messaging directories and then deactivate.
   * All *Before* actions must complete before ConfirmReadyToRun gives the go ahead
   */
  public void execute() {
    if (active()) {
      // Create the messaging directories as defined in the properties table
      String msg_root = queryProperty("msg_root");
      updateProperty("msg_root", createRunDirectory(msg_root));

      Arrays.asList(
        "msg_in",
        "msg_in_processed",
        "msg_out",
        "msg_out_processed",
        "msg_rejected"
      ).forEach(dir -> updateProperty(
          dir,
          createRunDirectory(
            Paths.get(msg_root, queryProperty(dir)).toString()
          )
        )
      );
      deactivate();
    }
  }
}
