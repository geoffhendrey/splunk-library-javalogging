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
public interface AckLifecycle {

  void preEventsPost(EventBatch events);

  void eventPostOK(EventBatch events);

  void eventPostNotOK(int code, String msg, EventBatch events);

  void eventPostFailure(Exception ex);

  void preAckPoll();

  void ackPollOK(EventBatch events);

  void ackPollNotOK(int statusCode, String reply);

  void ackPollFailed(Exception ex);

  void healthPollFailed(Exception ex);

  void healthPollOK();

  void healthPollNotOK(int code, String msg);
}

