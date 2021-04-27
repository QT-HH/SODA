/*
 * (C) Copyright 2017-2020 OpenVidu (https://openvidu.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.openvidu.server.cdr;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.kurento.client.GenericMediaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.openvidu.java.client.Recording.Status;
import io.openvidu.server.core.EndReason;
import io.openvidu.server.core.MediaOptions;
import io.openvidu.server.core.Participant;
import io.openvidu.server.core.Session;
import io.openvidu.server.core.SessionManager;
import io.openvidu.server.kurento.endpoint.KmsEvent;
import io.openvidu.server.recording.Recording;
import io.openvidu.server.recording.service.RecordingManager;
import io.openvidu.server.summary.SessionSummary;
import io.openvidu.server.webhook.CDRLoggerWebhook;

/**
 * CDR logger to register all information of a Session.
 * Enabled by property 'OPENVIDU_CDR=true'
 * 
 * - 'sessionCreated':				{sessionId, timestamp}
 * - 'sessionDestroyed':			{sessionId, timestamp, startTime, duration, reason}
 * - 'participantJoined':			{sessionId, timestamp, participantId, location, platform}
 * - 'participantLeft':				{sessionId, timestamp, participantId, startTime, duration, reason}
 * - 'webrtcConnectionCreated'		{sessionId, timestamp, participantId, connection, [receivingFrom], audioEnabled, videoEnabled, [videoSource], [videoFramerate]}
 * - 'webrtcConnectionDestroyed'	{sessionId, timestamp, participantId, startTime, duration, connection, [receivingFrom], audioEnabled, videoEnabled, [videoSource], [videoFramerate], reason}
 * - 'recordingStarted'				{sessionId, timestamp, id, name, hasAudio, hasVideo, resolution, recordingLayout, size}
 * - 'recordingStopped'				{sessionId, timestamp, id, name, hasAudio, hasVideo, resolution, recordingLayout, size}
 * - 'recordingStatusChanged'		{sessionId, timestamp, id, name, hasAudio, hasVideo, resolution, recordingLayout, size, status}
 * - 'filterEventDispatched'		{sessionId, timestamp, participantId, streamId, filterType, eventType, data}
 * 
 * PROPERTIES VALUES:
 * 
 * - sessionId:			string
 * - timestamp:			number
 * - startTime:			number
 * - duration:			number
 * - participantId:		string
 * - connection: 		"INBOUND", "OUTBOUND"
 * - receivingFrom: 	string
 * - audioEnabled: 		boolean
 * - videoEnabled: 		boolean
 * - videoSource: 		"CAMERA", "SCREEN", "CUSTOM", "IPCAM"
 * - videoFramerate:	number
 * - videoDimensions:	string
 * - id:				string
 * - name:				string
 * - hasAudio:			boolean
 * - hasVideo:			boolean
 * - resolution			string
 * - recordingLayout:	string
 * - size: 				number
 * - status:            string
 * - webrtcConnectionDestroyed.reason: 	"unsubscribe", "unpublish", "disconnect", "networkDisconnect", "mediaServerDisconnect", "openviduServerStopped"
 * - participantLeft.reason: 			"unsubscribe", "unpublish", "disconnect", "networkDisconnect", "mediaServerDisconnect", "openviduServerStopped"
 * - sessionDestroyed.reason: 			"lastParticipantLeft", "mediaServerDisconnect", "openviduServerStopped"
 * - recordingStopped.reason:			"recordingStoppedByServer", "lastParticipantLeft", "sessionClosedByServer", "automaticStop", "mediaServerDisconnect", "openviduServerStopped"
 * 
 * [OPTIONAL_PROPERTIES]:
 * - receivingFrom:		only if connection = "INBOUND"
 * - videoSource:		only if videoEnabled = true
 * - videoFramerate: 	only if videoEnabled = true
 * - videoDimensions: 	only if videoEnabled = true
 * 
 * @author Pablo Fuente (pablofuenteperez@gmail.com)
 */
public class CallDetailRecord {

	private static final Logger log = LoggerFactory.getLogger(CallDetailRecord.class);

	@Autowired
	private SessionManager sessionManager;

	private Collection<CDRLogger> loggers;

	private Map<String, CDREventSession> sessions = new ConcurrentHashMap<>();
	private Map<String, CDREventParticipant> participants = new ConcurrentHashMap<>();
	private Map<String, CDREventWebrtcConnection> publications = new ConcurrentHashMap<>();
	private Map<String, Set<CDREventWebrtcConnection>> subscriptions = new ConcurrentHashMap<>();
	private Map<String, CDREventRecording> recordings = new ConcurrentHashMap<>();

	public CallDetailRecord(Collection<CDRLogger> loggers) {
		this.loggers = loggers;
	}

	public Collection<CDRLogger> getLoggers() {
		return this.loggers;
	}

	public void recordSessionCreated(Session session) {
		CDREventSession e = new CDREventSession(session);
		this.sessions.put(session.getSessionId(), e);
		this.log(e);
	}

	public void recordSessionDestroyed(String sessionId, EndReason reason) {
		CDREventSession e = this.sessions.remove(sessionId);
		if (e != null) {
			CDREventSession eventSessionEnd = new CDREventSession(e, RecordingManager.finalReason(reason),
					System.currentTimeMillis());
			this.log(eventSessionEnd);

			// Summary: log closed session
			this.log(new SessionSummary(eventSessionEnd, sessionManager.removeFinalUsers(sessionId),
					sessionManager.removeAccumulatedRecordings(sessionId)));
		}
	}

	public void recordParticipantJoined(Participant participant, String sessionId) {
		CDREventParticipant e = new CDREventParticipant(participant);
		this.participants.put(participant.getParticipantPublicId(), e);
		this.log(e);
	}

	public void recordParticipantLeft(Participant participant, String sessionId, EndReason reason) {
		CDREventParticipant e = this.participants.remove(participant.getParticipantPublicId());
		CDREventParticipant eventParticipantEnd = new CDREventParticipant(e, reason, System.currentTimeMillis());
		this.log(eventParticipantEnd);

		// Summary: update final user ended connection
		sessionManager.getFinalUsers(sessionId).get(participant.getFinalUserId()).setConnection(eventParticipantEnd);
	}

	public void recordNewPublisher(Participant participant, String sessionId, String streamId,
			MediaOptions mediaOptions, Long timestamp) {
		CDREventWebrtcConnection publisher = new CDREventWebrtcConnection(sessionId, streamId, participant,
				mediaOptions, null, timestamp);
		this.publications.put(participant.getParticipantPublicId(), publisher);
		this.log(publisher);
	}

	public void stopPublisher(String participantPublicId, String streamId, EndReason reason) {
		CDREventWebrtcConnection eventPublisherEnd = this.publications.remove(participantPublicId);
		if (eventPublisherEnd != null) {
			eventPublisherEnd = new CDREventWebrtcConnection(eventPublisherEnd, reason, System.currentTimeMillis());
			this.log(eventPublisherEnd);

			// Summary: update final user ended publisher
			sessionManager.getFinalUsers(eventPublisherEnd.getSessionId())
					.get(eventPublisherEnd.getParticipant().getFinalUserId()).getConnections().get(participantPublicId)
					.addPublisherClosed(streamId, eventPublisherEnd);
		}
	}

	public void recordNewSubscriber(Participant participant, String sessionId, String streamId, String senderPublicId,
			Long timestamp) {
		CDREventWebrtcConnection publisher = this.publications.get(senderPublicId);
		CDREventWebrtcConnection subscriber = new CDREventWebrtcConnection(sessionId, streamId, participant,
				publisher.mediaOptions, senderPublicId, timestamp);
		this.subscriptions.putIfAbsent(participant.getParticipantPublicId(), new ConcurrentSkipListSet<>());
		this.subscriptions.get(participant.getParticipantPublicId()).add(subscriber);
		this.log(subscriber);
	}

	public void stopSubscriber(String participantPublicId, String senderPublicId, String streamId, EndReason reason) {
		Set<CDREventWebrtcConnection> participantSubscriptions = this.subscriptions.get(participantPublicId);
		if (participantSubscriptions != null) {
			CDREventWebrtcConnection eventSubscriberEnd;
			for (Iterator<CDREventWebrtcConnection> it = participantSubscriptions.iterator(); it.hasNext();) {
				eventSubscriberEnd = it.next();
				if (senderPublicId.equals(eventSubscriberEnd.receivingFrom)) {
					it.remove();
					eventSubscriberEnd = new CDREventWebrtcConnection(eventSubscriberEnd, reason,
							System.currentTimeMillis());
					this.log(eventSubscriberEnd);

					// Summary: update final user ended subscriber
					sessionManager.getFinalUsers(eventSubscriberEnd.getSessionId())
							.get(eventSubscriberEnd.getParticipant().getFinalUserId()).getConnections()
							.get(participantPublicId).addSubscriberClosed(streamId, eventSubscriberEnd);
				}
			}
		} else {
			log.error("No subscriptions map in CDR for session participant {}", participantPublicId);
		}
	}

	public void recordRecordingStarted(Recording recording) {
		CDREventRecording recordingStartedEvent = new CDREventRecording(recording);
		this.recordings.putIfAbsent(recording.getId(), recordingStartedEvent);
		this.log(recordingStartedEvent);
	}

	public void recordRecordingStopped(Recording recording, EndReason reason, long timestamp) {
		CDREventRecording recordingStartedEvent = this.recordings.remove(recording.getId());
		CDREventRecording recordingStoppedEvent = new CDREventRecording(recordingStartedEvent, recording,
				RecordingManager.finalReason(reason), timestamp);
		this.log(recordingStoppedEvent);

		// Summary: update ended recording
		sessionManager.getAccumulatedRecordings(recording.getSessionId()).add(recordingStoppedEvent);
	}

	public void recordRecordingStatusChanged(Recording recording, EndReason finalReason, long timestamp,
			Status status) {
		this.log(new CDREventRecordingStatus(recording, recording.getCreatedAt(), finalReason, timestamp, status));
	}

	public void recordFilterEventDispatched(String sessionId, String participantId, String streamId, String filterType,
			GenericMediaEvent event) {
		this.log(new CDREventFilterEvent(sessionId, participantId, streamId, filterType, event));
	}

	protected void log(CDREvent event) {
		this.loggers.forEach(logger -> {

			// TEMP FIX: AVOID SENDING recordingStarted AND recordingStopped EVENTS TO
			// WEBHOOK. ONLY recordingStatusChanged
			if (!(logger instanceof CDRLoggerWebhook && (CDREventName.recordingStarted.equals(event.getEventName())
					|| CDREventName.recordingStopped.equals(event.getEventName())))) {
				logger.log(event);
			}

		});
	}

	public void log(KmsEvent event) {
		this.loggers.forEach(logger -> {
			logger.log(event);
		});
	}

	public void log(SessionSummary sessionSummary) {
		this.loggers.forEach(logger -> {
			logger.log(sessionSummary);
		});
	}

}
