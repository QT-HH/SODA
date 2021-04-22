<template>
  <div class="home">
    <!--Dialog-->
    <div v-if="dialog" class="fixed z-10 inset-0 overflow-y-auto">
      <div
        class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0"
      >
        <div class="fixed inset-0 transition-opacity">
          <div class="absolute inset-0 bg-gray-500 opacity-75"></div>
        </div>
        <span
          class="hidden sm:inline-block sm:align-middle sm:h-screen"
          aria-hidden="true"
          >&#8203;</span
        >
        <div
          class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full"
          role="dialog"
          aria-modal="true"
          aria-labelledby="modal-headline"
        >
          <Dialog @closedialog="closedialog" />
        </div>
      </div>
    </div>
    <!--/end-->

    <!--Meeting-->
    <div v-if="meeting" class="bg-gray-800 h-screen relative overflow-hidden">
      <!--Video content-->
      <div style="pointer-events: none">
        <video
          class="h-screen object-cover overflow-hidden"
          width="100%"
          id="local_view"
          autoplay
          playsinline
        ></video>
      </div>
      <!--/end-->

      <!--Alert content-->
      <div
        v-if="show_alert"
        style="width: 22rem"
        class="absolute right-5 px-6 py-6 top-2 shadow-md bg-white rounded-lg"
      >
        <div class="flex justify-between mb-3">
          <span class="text-lg font-base">Your meeting is ready</span>
          <button @click="show_alert = false" class="focus:outline-none">
            <box-icon size="30px" color="black" name="x"></box-icon>
          </button>
        </div>
        <Alert :meeting_code="meeting_code" />
      </div>
      <!--/end-->

      <!--Navigation Drawer-->
      <div
        class="absolute px-2 overflow-y-auto w-44 sm:w-48 inset-y-0 left-0 max-w-full"
      >
        <div v-for="(item, index) in sessions" :key="item.id ? item.id : index">
          <div class="w-full bg-gray-900 rounded-lg mt-2 h-24 relative">
            <video
              width="100%"
              muted
              controls
              class="meeting-streams h-24 w-full object-cover overflow-hidden"
              autoplay
              playsinline
            ></video>
          </div>
        </div>
      </div>
      <!--/end-->

      <div
        v-if="drawer"
        class="absolute inset-y-0 right-0 right-0 w-96 bg-white"
      >
        <div class="relative h-screen">
          <div class="px-8 py-4">
            <div class="flex justify-between mb-3">
              <span class="text-lg font-lg">Meeting details</span>
              <button @click="drawer = false" class="focus:outline-none">
                <box-icon size="30px" color="black" name="x"></box-icon>
              </button>
            </div>
            <div class="grid grid-cols-2">
              <div>
                <a @click="show_chat = false" class="flex cursor-pointer">
                  <box-icon type="solid" name="group"></box-icon>
                  <span class="ml-2">People ({{ sessions.length }})</span>
                </a>
                <div
                  v-if="show_chat === false"
                  class="w-32 border-2 mt-3 border-solid border-blue-500"
                ></div>
              </div>

              <div>
                <a @click="show_chat = true" class="flex cursor-pointer">
                  <box-icon name="message-square-detail"></box-icon>
                  <span class="ml-2">Chat</span>
                </a>
                <div
                  v-if="show_chat === true"
                  class="w-32 border-2 mt-3 border-solid border-blue-500"
                ></div>
              </div>
            </div>
          </div>
          <div class="pb-2 px-4">
            <a
              v-if="show_chat === false"
              href="mailto:webuser@example.com"
              class="w-full mb-2 inline-flex justify-center rounded-md border border-transparent shadow-sm px-3 py-2 text-base font-medium text-blue-600 hover:bg-blue-100 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 sm:w-auto"
            >
              <box-icon
                name="user-plus"
                type="solid"
                color="#3B82F6"
              ></box-icon>
              <span class="ml-2">Add people</span>
            </a>
            <hr />
          </div>
          <div class="px-4 h-2/3 overflow-y-auto">
            <Chat :messages="messages" v-if="show_chat" />
            <People :sessions="sessions" v-else />
          </div>
          <div
            v-if="show_chat === true"
            class="absolute inset-x-0 bottom-24 bg-gray-100 shadow-xl h-16"
          >
            <div class="flex items-center py-2 px-4">
              <input
                id="message"
                name="message"
                type="text"
                placeholder="Write a message"
                v-model="messageContent.message"
                class="shadow-sm focus:ring-indigo-500 focus:border-indigo-500 mt-1 block w-full sm:text-sm border-gray-300 rounded-md"
              />
              <button @click="sendmessage" class="focus:outline-none">
                <box-icon name="send" class="ml-2 mt-2"></box-icon>
              </button>
            </div>
          </div>
        </div>
      </div>

      <!--Bottom menu content-->
      <div class="absolute flex items-center left-1 bottom-28">
        <box-icon name="dots-horizontal" color="#2563EB"></box-icon>
        <div class="text-gray-600 text-xs font-medium ml-2">Ongoing</div>
      </div>
      <div
        v-if="modal"
        data-aos="fade-up-right"
        class="absolute rounded-lg w-96 shadow-md left-5 bottom-28 bg-white"
      >
        <Menu :meeting_code="meeting_code" />
      </div>
      <div
        v-if="expand"
        data-aos="fade-up-left"
        class="absolute rounded-lg bg-gray-100 shadow-md right-5 bottom-28 w-72 bg-white"
      >
        <div class="py-4">
          <Helpers @opendialog="opendialog" />
        </div>
      </div>
      <!--/end-->

      <!--Bottom section-->
      <div class="absolute inset-x-0 bottom-0 bg-white">
        <div class="grid grid-cols-2 md:grid-cols-3 h-24">
          <div class="flex hidden md:block items-center">
            <button
              @click="modal = !modal"
              class="flex items-center focus:outline-none hover:bg-gray-200 h-full cursor-pointer"
            >
              <span class="text-xl mr-3 ml-4">Meeting details</span>
              <box-icon
                name="chevron-down"
                class="mr-4"
                type="solid"
              ></box-icon>
            </button>
          </div>
          <div class="flex md:justify-center items-center">
            <div>
              <button
                @click="muteandunmuteaudio"
                class="transition duration-500 ease-in-out transform hover:-translate-y-1 hover:shadow-xl border border-solid border-4 border-indigo-500 py-4 px-4 rounded-full text-indigo-600 hover:text-white focus:outline-none"
              >
                <box-icon
                  v-if="audio_track"
                  color="#52525B"
                  name="volume-mute"
                ></box-icon>
                <box-icon v-else color="#52525B" name="volume"></box-icon>
              </button>
            </div>
            <div>
              <button
                @click="exitsession"
                class="transition duration-500 ease-in-out transform hover:-translate-y-1 hover:shadow-xl border border-solid border-4 border-indigo-500 py-4 px-4 ml-3 rounded-full text-indigo-600 hover:text-white focus:outline-none"
              >
                <box-icon type="solid" color="red" name="phone"></box-icon>
              </button>
            </div>
            <div>
              <button
                @click="stopandstartvideo"
                class="transition duration-500 ease-in-out transform hover:-translate-y-1 hover:shadow-xl border border-solid border-4 border-indigo-500 py-4 px-4 ml-3 rounded-full text-indigo-600 hover:text-white focus:outline-none"
              >
                <box-icon
                  v-if="video_stream"
                  color="#52525B"
                  name="video"
                ></box-icon>
                <box-icon v-else color="#52525B" name="video-off"></box-icon>
              </button>
            </div>
          </div>
          <div class="justify-end flex items-center">
            <button
              class="hover:bg-gray-100 hidden lg:block focus:outline-none h-full w-32 cursor-pointer"
            >
              <box-icon color="#52525B" name="captions"></box-icon>
              <div>Turn on captions</div>
            </button>
            <button
              @click="drawer = !drawer"
              class="hover:bg-gray-100 hidden lg:block focus:outline-none h-full w-32 cursor-pointer"
            >
              <box-icon color="#52525B" name="home-alt"></box-icon>
              <div>Dashboard</div>
            </button>
            <button
              @click="expand = !expand"
              class="hover:bg-gray-100 focus:outline-none h-full w-16 cursor-pointer"
            >
              <box-icon name="dots-vertical"></box-icon>
            </button>
          </div>
        </div>
      </div>
      <!--/end-->
    </div>
    <!--/end-->

    <!--Loader-->
    <div v-else-if="loading">
      <Loader :status="status" />
    </div>
    <!--/end-->

    <!--Join meeting-->
    <div v-else>
      <div
        class="min-h-screen bg-gray-100 py-6 flex flex-col justify-center sm:py-12"
      >
        <div class="relative py-3 sm:max-w-xl sm:mx-auto">
          <div
            class="absolute inset-0 bg-gradient-to-r from-violet-400 to-purple-600 shadow-lg transform -skew-y-6 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl"
          ></div>
          <div
            class="relative px-4 py-10 bg-white shadow-lg sm:rounded-3xl sm:p-20"
          >
            <div class="max-w-md mx-auto">
              <div class="flex items-center">
                <div class="text-2xl font-mono font-semibold">Join Meeting</div>
              </div>
              <div class="divide-y divide-gray-200">
                <ValidationObserver v-slot="{ invalid }">
                  <div
                    class="py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7"
                  >
                    <validation-provider
                      :rules="{ regex: /https?:\/\/(www\.)?/ }"
                      v-slot="{ errors }"
                    >
                      <div class="col-span-6">
                        <div
                          class="flex justify-between items-center bg-purple-50 py-2 rounded-lg"
                        >
                          <!-- Target -->
                          <input
                            type="text"
                            name="Meeting code"
                            id="meeting_name"
                            placeholder="Meeting code"
                            class="p-2 focus:outline-none bg-purple-50 rounded-md block w-full border-transparent"
                            v-model="meeting_code"
                          />

                          <!-- Trigger -->
                          <button
                            class="btn ml-2 mr-2 focus:outline-none"
                            data-clipboard-target="#meeting_name"
                          >
                            <box-icon
                              type="regular"
                              class="mt-2"
                              color="purple"
                              name="clipboard"
                            ></box-icon>
                          </button>
                        </div>
                        <span class="text-red-500 text-xs">{{
                          errors[0]
                        }}</span>
                      </div>
                    </validation-provider>
                    <validation-provider rules="required" v-slot="{ errors }">
                      <div class="col-span-6 mt-4">
                        <input
                          type="text"
                          name="user_name"
                          id="user_name"
                          v-model="item.user_name"
                          placeholder="Please enter your name here"
                          class="p-3 focus:ring-purple-500 focus:border-purple-500 block w-full shadow-sm text-base border-gray-300 rounded-md"
                        />
                        <div>
                          <span class="text-red-500 text-xs">{{
                            errors[0]
                          }}</span>
                        </div>
                      </div>
                    </validation-provider>
                    <div class="grid grid-cols-1">
                      <button
                        :disabled="invalid"
                        @click="joinmeeting"
                        type="button"
                        class="w-full cursor-pointer inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 p-3 bg-purple-600 text-base font-medium text-white hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 sm:w-auto"
                      >
                        <Spinner v-if="saving" />
                        <span class="ml-2">Join meeting</span>
                      </button>
                    </div>
                  </div>
                </ValidationObserver>
                <div
                  class="pt-6 text-base leading-6 font-bold sm:text-lg sm:leading-7"
                >
                  <p>Schedule your meeting on Google calender later?</p>
                  <p>
                    <a
                      class="text-purple-600 mt-2 cursor-pointer hover:text-purple-700"
                    >
                      <div class="flex items-center">
                        <box-icon name="calendar"></box-icon>
                        <span class="ml-2 font-base"
                          >Schedule meeting &rarr;</span
                        >
                      </div>
                    </a>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--/end-->
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import adapter from "webrtc-adapter";
import { ValidationProvider, ValidationObserver, extend } from "vee-validate";
import { required, regex } from "vee-validate/dist/rules";
extend("regex", regex);
extend("required", {
  ...required,
  message: "This field is required"
});
var io = require("socket.io-client");
import Loader from "../components/parts/Loading";
import Dialog from "../components/parts/Dialog";
import Chat from "../components/parts/Chat";
import People from "../components/parts/People";
import Menu from "../components/parts/Menu";
import Helpers from "../components/parts/Helpers";
import Alert from "../components/parts/Alert";
import Spinner from "../components/parts/Spinner";
import ClipboardJS from "clipboard";
import "boxicons";
import {
  fetchallsessions,
  fetchallmeetings,
  fetchonemeeting,
  saveonesession,
  saveonemeeting,
  decryptinformation,
  encryptinformation,
  fetchonesession,
  deleteonemeeting,
  deleteallsession,
  deleteonesession
} from "../components/script";
export default {
  name: "Session",
  components: {
    Spinner,
    Helpers,
    Alert,
    Menu,
    Chat,
    People,
    Dialog,
    Loader,
    ValidationProvider,
    ValidationObserver
  },
  data() {
    return {
      // video constraints
      constraints: {
        audio: { echoCancellation: true },
        video: {
          width: {
            min: 640,
            max: 1024
          },
          height: {
            min: 480,
            max: 768
          }
        }
      },
      //streams: [], Incase increasing the number to more than 2
      // end
      time: new Date().toLocaleTimeString(),
      show_chat: false,
      // menu controls
      expand: false,
      modal: false,
      show_alert: true,
      // drawer
      drawer: false,
      drawer1: true,
      // dialogs
      dialog: false,
      // end
      // logged || guest user
      user: {
        signalingClient: null, // user signaling client
        remoteStream: null, // local meeting streams
        localStream: null, // local stream
        sessionid: null, // local session id
        peerConnection: null // local peer connection
      },
      // loading || meeting
      loading: false,
      meeting: false,
      status: "Processing",
      // end
      item: {
        user_name: null,
        meeting_url: null
      },
      // sessions
      sessions: [],
      // message content
      messageContent: {
        message: null,
        attachment: null
      },
      // messages
      messages: [],
      // video control
      video_stream: true,
      audio_track: true,
      //end
      user_user: null,
      error: null,
      saving: false
    };
  },
  // * computed
  computed: {
    meeting_code: {
      get() {
        return this.$store.state.meeting_url;
      },
      set(value) {
        this.$store.commit("savemeetingurl", value);
      }
    }
  },
  // * before destroy
  beforeDestroy() {
    // ? exit and reset
    this.exitsession();
  },
  // * on page mount
  async mounted() {
    new ClipboardJS(".btn");
    let application_url = window.location.href; // get current url
    // *On page mount check if the meeting name is available
    const split_meeting_url = application_url.split("/"); // decrypt meeting name
    const meeting_id = split_meeting_url.pop();
    const meeting_name = decryptinformation(meeting_id);
    if (meeting_name === "Join meeting") {
      return;
    } else {
      this.$store.commit("savemeetingurl", application_url); // save meeting to vuex
    }
  },
  // * methods
  methods: {
    // * initalize meeting
    initializemeeting(id, item) {
      let meeting_views = document.querySelectorAll(".meeting-streams");
      this.sessions.forEach((e, stream_index) => {
        meeting_views.forEach((remoteview, video_index) => {
          if (e.sessionid === id && stream_index === video_index && item) {
            if (remoteview.srcObject) {
              return;
            }
            remoteview.srcObject = item;
          }
        });
      });
    },
    // * join meeting
    joinmeeting() {
      // ? make connection only when username is available
      this.loading = true;
      // * get the meeting meeting
      let split_meeting_url = this.meeting_code.split("/");
      let meeting_name = split_meeting_url.pop();

      this.user.signalingClient = io(
        //"http://localhost:5000/"
        "https://webrtc-app-backend-vue.herokuapp.com/"
      );
      // * join a meeting
      this.user.signalingClient.on("connect", async () => {
        let value = {
          user_name: encryptinformation(this.item.user_name), // session user name (to be encrypted)
          meeting_url: meeting_name, // meeting id
          socket_id: this.user.signalingClient.id // socket id
        };
        console.log(value);
        if (value.user_name !== null) {
          await saveonemeeting(value); // persist session in db
          this.item.user_name = null;
        }

        // * Joining meeting
        this.status = "Joining";
        let path = `/session/${meeting_name}`;
        if (this.$route.path !== path) this.$router.replace(path);

        // * Fetch all that have joined the meeting
        const sessions_response = await fetchallmeetings(`${meeting_name}`);
        this.sessions = [...sessions_response];
        // * Fetch all messages and attachments
        const contents_response = await fetchallsessions(`${meeting_name}`);
        contents_response.forEach(async e => {
          const user_name = await fetchonemeeting(e.sessionid);
          e.sessionid = decryptinformation(user_name.name);
          this.messages.push(e);
        });

        // * join meeting
        this.loading = false;
        this.meeting = true;
        this.status = "Processing";

        // * notify joining meeting
        this.user.signalingClient.emit(
          "joined",
          JSON.stringify({
            meetingid: `${meeting_name}`,
            sessionid: `${this.user.signalingClient.id}`
          })
        );

        // * initialize the camera
        this.$nextTick(async () => {
          let localView = document.getElementById("local_view");
          //console.log(localView);

          this.user.localStream = await navigator.mediaDevices.getUserMedia(
            this.constraints
          );
          localView.srcObject = this.user.localStream;
          this.initializemeeting(
            this.user.signalingClient.id,
            this.user.localStream
          );
          // !end

          // * Get the video and audio tracks streams
          const audioTracks = localView.srcObject.getAudioTracks();
          const videoTracks = localView.srcObject.getVideoTracks();
          if (audioTracks.length > 0) {
            console.log(`Using audio device: ${audioTracks[0].label}`);
          }
          if (videoTracks.length > 0) {
            console.log(`Using video device: ${videoTracks[0].label}`);
          }
        });
        //!end
        this.user.signalingClient.connect();
      });
      // !end

      // * listen to users joining create an offer and send
      this.user.signalingClient.on("joined", async data => {
        if (data !== this.user.signalingClient.id) {
          // * fetch user details
          const joined_user = await fetchonemeeting(data);
          this.sessions.push(joined_user);

          // * create rtc session
          const configuration = {
            iceServers: [{ urls: "stun:stun.l.google.com:19302" }]
          };
          this.user.peerConnection = new RTCPeerConnection(configuration);
          let localView = document.getElementById("local_view");
          //console.log("[user]", localView, localView.srcObject);
          localView.srcObject
            .getTracks()
            .forEach(track =>
              this.user.peerConnection.addTrack(track, localView.srcObject)
            );
          // ! remote tracks
          this.user.peerConnection.ontrack = event => {
            this.initializemeeting(data, event.streams[0]);
          };
          //!end

          // ? ICE GATHERING WITHOUT TRICKLE
          // this.user.peerConnection.addEventListener(
          //   "icegatheringstatechange",
          //   event => {
          //     if (event.target.iceGatheringState === "complete") {
          //       this.user.signalingClient.emit(
          //         "offer_message",
          //         JSON.stringify({
          //           desc: this.user.peerConnection.localDescription,
          //           offerfrom: `${this.user.signalingClient.id}`,
          //           offerto: `${data}` // ? send offer to
          //         })
          //       );
          //     }
          //   }
          // );

          // ? WITH TRICLE ENABLED

          // * create an offer and send
          await this.user.peerConnection.setLocalDescription(
            await this.user.peerConnection.createOffer({
              offerToReceiveAudio: true,
              offerToReceiveVideo: true
            })
          );

          this.user.peerConnection.onicecandidate = ({ candidate }) =>
            this.user.signalingClient.emit(
              "offer_message",
              JSON.stringify({
                desc: {
                  offer: this.user.peerConnection.localDescription,
                  icecandidate: { candidate }
                },
                offerfrom: `${this.user.signalingClient.id}`,
                offerto: `${data}` // ? send offer to
              })
            );
        }
        this.user.signalingClient.connect();
      });
      //!end

      // * listen to users offers and create an answer
      this.user.signalingClient.on("offer_message", async data => {
        const response = JSON.parse(data);
        this.$nextTick(async () => {
          // * Get the video and audio tracks streams
          let localView = document.getElementById("local_view");
          this.user.localStream = await navigator.mediaDevices.getUserMedia(
            this.constraints
          );
          localView.srcObject = this.user.localStream;
          const audioTracks = localView.srcObject.getAudioTracks();
          const videoTracks = localView.srcObject.getVideoTracks();
          const peerTracks = localView.srcObject.getTracks();
          if (audioTracks.length > 0) {
            //console.log(`Using audio device: ${audioTracks[0].label}`);
          }
          if (videoTracks.length > 0) {
            //console.log(`Using video device: ${videoTracks[0].label}`);
          }
          //!end

          // * create rtc connection
          const configuration = {
            iceServers: [{ urls: "stun:stun.l.google.com:19302" }]
          };
          this.user.peerConnection = new RTCPeerConnection(configuration);
          // ! remote tracks
          this.user.peerConnection.ontrack = event => {
            this.initializemeeting(response.offerfrom, event.streams[0]);
          };
          //!end

          if (response.desc) {
            //console.log("[user] Offer", response);
            // * set offer to remote

            if (response.desc.offer) {
              await this.user.peerConnection
                .setRemoteDescription(response.desc.offer)
                .catch(error => {
                  if (error) return;
                });
            }

            peerTracks.forEach(track =>
              this.user.peerConnection.addTrack(track, localView.srcObject)
            );
            // * create an answer set to local description and send
            await this.user.peerConnection.setLocalDescription(
              await this.user.peerConnection.createAnswer({
                offerToReceiveAudio: true,
                offerToReceiveVideo: true
              })
            );

            // * send a answer and candidate
            this.user.peerConnection.onicecandidate = ({ candidate }) =>
              this.user.signalingClient.emit(
                "answer_message",
                JSON.stringify({
                  desc: {
                    answer: this.user.peerConnection.localDescription,
                    icecandidate: { candidate }
                  },
                  offerfrom: `${this.user.signalingClient.id}`,
                  offerto: `${response.offerfrom}` // ? send answer to
                })
              );

            // * add ice candidates
            if (this.user.peerConnection.canTrickleIceCandidates === true) {
              //console.log("[user] Candidate", response.desc.icecandidate);
              await this.user.peerConnection
                .addIceCandidate(response.desc.icecandidate)
                .catch(error => {
                  if (error) return;
                });
            }

            // ? ICE GATHERING WITHOUT TRICKLE
            //   this.user.peerConnection.addEventListener(
            //     "icegatheringstatechange",
            //     event => {
            //       if (event.target.iceGatheringState === "complete") {
            //         this.user.signalingClient.emit(
            //           "answer_message",
            //           JSON.stringify({
            //             desc: this.user.peerConnection.localDescription,
            //             offerfrom: `${this.user.signalingClient.id}`,
            //             offerto: `${response.offerfrom}` // ? send answer to
            //           })
            //         );
            //       }
            //     }
            //   );
            //   console.log("[user] peer connection", this.user.peerConnection);
          }
        });
        this.user.signalingClient.connect();
      });

      // * listen to answers and set to remote description
      this.user.signalingClient.on("answer_message", async data => {
        const response = JSON.parse(data);

        if (response.desc) {
          // * set remote description and ice
          //console.log("[user] Answer", response);
          if (response.desc.answer) {
            await this.user.peerConnection
              .setRemoteDescription(response.desc.answer)
              .catch(error => {
                if (error) return;
              });
          }

          if (this.user.peerConnection.canTrickleIceCandidates === true) {
            //console.log("[user] Candidate", response.desc.icecandidate);
            await this.user.peerConnection
              .addIceCandidate(response.desc.icecandidate)
              .catch(error => {
                if (error) return;
              });
          }
        }

        this.user.signalingClient.connect();
      });

      // * await for new messages
      this.user.signalingClient.on("sendmessage", async data => {
        if (data) {
          let new_message = await fetchonesession(data);
          let user_name = await fetchonemeeting(new_message.sessionid);
          new_message.sessionid = decryptinformation(user_name.name);
          this.messages.push(new_message);
        }
        this.user.signalingClient.connect();
      });
      // !end
    },
    // * send message
    async sendmessage() {
      // * retrive session details
      let split_meeting_url = window.location.href.split("/");
      let value = {
        message: encryptinformation(this.messageContent.message),
        sessionid: this.user.signalingClient.id, // user session id
        meetingid: split_meeting_url.pop() // meeting id
      };
      const session_response = await saveonesession(value);
      let user_name = await fetchonemeeting(session_response.sessionid);
      session_response.sessionid = decryptinformation(user_name.name);
      this.messages.push(session_response);
      this.messageContent = {
        message: null,
        attachment: null
      };
      // * send a message
      this.user.signalingClient.emit(
        "send",
        JSON.stringify({
          sessionid: session_response._id,
          meetingid: value.meetingid // meeting id
        })
      );

      this.user.signalingClient.connect();
    },
    // * delete one session
    async deletecontent(item) {
      await deleteonesession(item._id);
    },
    // * exit meetings
    async exitsession() {
      // * disconnect media
      await this.disconnectmedia();
      // * delete all contents with session id
      if (this.user.signalingClient) {
        await deleteonemeeting(this.user.signalingClient.id);
        await deleteallsession(this.user.signalingClient.id);
        // * disconnect from meeting
        this.user.signalingClient.on("disconnect");
        this.user = {
          signalingClient: null, // user signaling client
          remoteStream: null, // local meeting streams
          localStream: null, // local stream
          sessionid: null, // local session id
          peerConnection: null // local peer connection
        };
      }
      let path = "/";
      if (this.$route.path !== path) this.$router.push(path);
    },
    // * disconnect media
    disconnectmedia() {
      let localView = document.getElementById("local_view");
      if (localView !== null) {
        if (localView.srcObject) {
          localView.srcObject.getTracks().forEach(track => track.stop());
        }
      }
      let meeting_views = document.querySelectorAll(".meeting-streams");
      meeting_views.forEach(e => {
        if (e.srcObject) {
          e.srcObject.getTracks().forEach(track => track.stop());
        }
      });
      this.sessions = [];
    },
    // ! end
    // * open dialog
    opendialog() {
      this.dialog = true;
    },
    // * close dialog
    closedialog() {
      this.dialog = false;
    },
    //! end

    // ?  Video Controls
    // * mute and unmute video
    muteandunmuteaudio() {
      let localView = document.getElementById("local_view");
      localView.srcObject
        .getAudioTracks()
        .forEach(track => (track.enabled = !track.enabled));
      this.audio_track = !this.audio_track;
      // end
    },
    // * start and stop video
    stopandstartvideo() {
      let localView = document.getElementById("local_view");
      // getTracks()
      localView.srcObject
        .getVideoTracks()
        .forEach(track => (track.enabled = !track.enabled));
      this.video_stream = !this.video_stream;
      // end
    }
    // ! end
  }
};
</script>
