package com.github.museadmin.infinite_state_machine.core.action_pack;


import com.github.museadmin.infinite_state_machine.data.access.action.Action;

import java.io.File;

/**
 * Check if any new messages have arrived in the in directory.
 * Each message file should have a semaphore file of the same name
 * but with a .smp extension, that is created after the message
 * file write is complete.
 */
public class ActionCheckForNewMessages extends Action {

  /**
   * The principal method for execution of the action
   */
  public void execute() {
    if (active()) {
      File in = new File(queryProperty("msg_in"));
      File[] list = in.listFiles(
        (dir, name) -> name.toLowerCase().endsWith(".smp")
      );

      // We have at least one new message
      activate("ActionNormalShutdown");

      if (list == null || list.length == 0) {
        return;
      }

      // TODO Create a unit test that writes the msg file
      // Read the file into a message object
      // Check if action target is active -> skip
      // Call the message obj method to write to DB
      // Activate the target action
      // Move the message file to processed

    }
  }
}
