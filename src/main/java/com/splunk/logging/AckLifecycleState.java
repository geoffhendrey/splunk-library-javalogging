/*
 * Copyright 2017 Splunk, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.splunk.logging;

/**
 *
 * @author ghendrey
 */
public class AckLifecycleState {

  public enum State {
	// States tied to an EventBatch object
    PRE_EVENT_POST,
    EVENT_POST_OK,
    EVENT_POST_NOT_OK,
    EVENT_POST_FAILURE,
    PRE_ACK_POLL,
    ACK_POLL_OK,
    ACK_POLL_NOT_OK,
    ACK_POLL_FAILURE,

    // States without an EventBatch object
    HEALTH_POLL_OK,
    HEALTH_POLL_NOT_OK,
    HEALTH_POLL_FAILED
  };

  private final State currentState;
  private EventBatch events = null;
  private final HttpEventCollectorSender sender;

  public AckLifecycleState(State currentState
		  , EventBatch events
		  , HttpEventCollectorSender sender) throws Exception {
	if (currentState.compareTo(State.HEALTH_POLL_OK) < 0
		&& events == null) {
		throw new IllegalStateException("Provided state requires an EventBatch object");
	}
    this.currentState = currentState;
    this.sender = sender;

    // ignore events for State values not an needing EventBatch object
	if (currentState.compareTo(State.HEALTH_POLL_OK) < 0) {
	  this.events = events;
	}
  }

  public AckLifecycleState(State currentState, HttpEventCollectorSender sender) throws Exception {
	if (currentState.compareTo(State.HEALTH_POLL_OK) < 0) {
		throw new IllegalStateException("Provided state requires an EventBatch object");
	}
	this.currentState = currentState;
	this.sender = sender;
  }

  /**
   * @return the currentState
   */
  public State getCurrentState() {
    return currentState;
  }

  /**
   * @return the events
   */
  public EventBatch getEvents() {
    return events;
  }

  /**
   * @return the sender
   */
  public HttpEventCollectorSender getSender() {
    return sender;
  }
}
