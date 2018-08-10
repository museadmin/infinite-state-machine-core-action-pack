package com.github.museadmin.infinite_state_machine.core.action_pack;

import com.github.museadmin.infinite_state_machine.action.Action;

/**
 * After new messages are inserted into the database they need to be
 * processed. e.g. If the target action is not currently active, then
 * set it to active and set its payload form the message.
 */
public class ActionProcessInboundMessages extends Action {

  /**
   * Look in the messages table for any messages marked as not processed
   * If the target action isn't already active with another payload then
   * write the payload from the message into the action's payload
   * Then activate the action
   */
  public void execute() {
    if (active()) {
      getUnprocessedMessages().forEach(
        row -> {
          String actionName = row.getString("action");
          if (! active(actionName)) {
            updatePayload(actionName, row.getString("payload"));
            activate(actionName);
            markMessageProcessed(row.getInt("id"));
          }
        }
      );
    }
  }
}
