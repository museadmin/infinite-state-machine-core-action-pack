package com.github.museadmin.infinite_state_machine.core.action_pack;

import com.github.museadmin.infinite_state_machine.common.action.Action;
import com.github.museadmin.infinite_state_machine.common.utils.ZipDirectory;

import java.io.IOException;

/**
 * Archive the messaging directories and run log
 */
public class ActionAfterArchiveMessages extends Action {

  public void execute() {
    if(active()) {
      String source = queryProperty("msg_root");
      String target = "/tmp/msgs.zip";
      try {
        ZipDirectory.zipDirectory(source, target);
      } catch (IOException e) {
        LOGGER.error(String.format("Failed to create zip archive of messaging directory (%s);", target));
      }
      deactivate();
    }
  }
}
