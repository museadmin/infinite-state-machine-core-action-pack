package com.github.museadmin.infinite_state_machine.core.action_pack;

import com.github.museadmin.infinite_state_machine.action.Action;

/**
 * After new messages are inserted into the database they need to be
 * processed. e.g. If the target action is not currently active, then
 * set it to active and set its payload form the message.
 */
public class ActionProcessInboundMessages extends Action {

  public void execute() {

    if (active()) {
      // Get any unprocessed messages in the DB
      getUnprocessedMessages().forEach(
        row -> {
          String actionName = row.getString("action");
          if (! active(actionName)) {
            // Set the action payload
            updatePayload(actionName, row.getString("payload"));

            // activate the action
            activate(actionName);

            // Mark the message as processed
            markMessageProcessed(row.getInt("id"));
          }
        }
      );

    }


  }
}
