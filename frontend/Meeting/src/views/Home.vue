<template>
  <div class="home">
    <!--Home component-->
    <div
      class="min-h-screen bg-gray-100 py-6 flex flex-col justify-center sm:py-12"
    >
      <div class="relative py-3 sm:max-w-xl sm:mx-auto">
        <div
          class="absolute inset-0 bg-gradient-to-r from-cyan-400 to-lightBlue-500 shadow-lg transform -skew-y-6 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl"
        ></div>
        <div
          class="relative px-4 py-10 bg-white shadow-lg sm:rounded-3xl sm:p-20"
        >
          <div class="max-w-md mx-auto">
            <div class="flex items-center">
              <img
                src="https://res.cloudinary.com/dk5ch7wqm/image/upload/v1612296046/IMG-20200910-WA0020_ppnudi-removebg-preview_vqozxz.png"
                class="h-7 sm:h-12"
              />
              <h2 class="ml-3 text-2xl font-mono font-semibold">
                Meeting Application
              </h2>
            </div>
            <div class="divide-y divide-gray-200">
              <div
                class="py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7"
              >
                <p>
                  Start or schedule your meeting no account needed!.
                </p>
                <ul class="list-disc space-y-2">
                  <li class="flex items-start">
                    <span class="h-6 flex items-center sm:h-7">
                      <svg
                        class="flex-shrink-0 h-5 w-5 text-cyan-500"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                      >
                        <path
                          fill-rule="evenodd"
                          d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                          clip-rule="evenodd"
                        />
                      </svg>
                    </span>
                    <p class="ml-2">
                      Create or schedule a new
                      <code class="text-sm font-bold text-gray-900"
                        >meeting</code
                      >
                    </p>
                  </li>
                  <li class="flex items-start">
                    <span class="h-6 flex items-center sm:h-7">
                      <svg
                        class="flex-shrink-0 h-5 w-5 text-cyan-500"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                      >
                        <path
                          fill-rule="evenodd"
                          d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                          clip-rule="evenodd"
                        />
                      </svg>
                    </span>
                    <p class="ml-2">
                      Get a meeting
                      <code class="text-sm font-bold text-gray-900">code</code>
                      or
                      <code class="text-sm font-bold text-gray-900">url</code>
                    </p>
                  </li>
                  <li class="flex items-start">
                    <span class="h-6 flex items-center sm:h-7">
                      <svg
                        class="flex-shrink-0 h-5 w-5 text-cyan-500"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                      >
                        <path
                          fill-rule="evenodd"
                          d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                          clip-rule="evenodd"
                        />
                      </svg>
                    </span>
                    <p class="ml-2">
                      Share with others
                      <code class="text-sm font-bold text-gray-900">join</code>
                    </p>
                  </li>
                </ul>
                <div class="col-span-6">
                  <input
                    type="text"
                    v-model="name"
                    name="meeting_name"
                    id="meeting_name"
                    placeholder="Meeting name"
                    class="p-3 focus:ring-lightBlue-500 focus:border-lightBlue-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"
                  />
                </div>
                <div class="flex justify-end">
                  <button
                    @click="createnewmeeting"
                    type="button"
                    class="w-full cursor-pointer inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 p-3 bg-lightBlue-500 text-sm font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:w-auto"
                  >
                    <Spinner v-if="loading" />
                    <span class="ml-2">Create a meeting</span>
                  </button>
                </div>
              </div>
              <div
                class="pt-6 text-base leading-6 font-bold sm:text-lg sm:leading-7"
              >
                <p>Already have a meeting code / url?</p>
                <p>
                  <a
                    @click="joinnewmeeting"
                    class="text-cyan-600 cursor-pointer hover:text-cyan-700"
                  >
                    Join a meeting &rarr;
                  </a>
                </p>
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
import { encryptinformation } from "../components/script";
import Spinner from "../components/parts/Spinner";
export default {
  name: "Home",
  components: { Spinner },
  data() {
    return {
      name: null,
      loading: false
    };
  },
  methods: {
    // create a new meeting
    async createnewmeeting() {
      this.loading = true;
      let meeting_name = encryptinformation(this.name); // encrypt meeting name
      this.name = ""; // clear
      let application_url = window.location.href; // get the current href
      let meeting_url = `${application_url}session/${meeting_name}`; // create a url
      this.$store.commit("savemeetingurl", meeting_url); // save meeting to vuex
      setTimeout(() => {
        this.loading = false;
        this.$router.push(`/session/${meeting_name}`);
      }, 1000);
    },
    // join a meeting
    joinnewmeeting() {
      let item = encryptinformation("Join meeting");
      let route = `/session/${item}`;
      this.$router.push(route);
    }
  }
};
</script>
