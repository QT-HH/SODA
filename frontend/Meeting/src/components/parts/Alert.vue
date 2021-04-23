<template>
  <div class="alert">
    <!--Alert-->
    <div>
      <a
        href="mailto:webmaster@example.com"
        class="w-full mb-2 inline-flex justify-center rounded-md border border-transparent shadow-sm px-3 py-2 bg-blue-600 text-base font-medium text-white hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 sm:w-auto"
      >
        <box-icon name="user-plus" type="solid" color="white"></box-icon>
        <span class="ml-2">Add others</span>
      </a>
      <div class="mb-2 text-gray-700 font-medium antialiased">
        Share this meeting link with others you want in the meeting
      </div>
      <div class="col-span-6 mt-3">
        <div
          class="flex justify-between items-center bg-lightBlue-50 rounded-lg"
        >
          <!-- Target -->
          <input
            name="meeting_alert"
            id="meeting_alert"
            placeholder="Meeting code"
            class="p-2 bg-lightBlue-50 block w-full border-transparent"
            v-model="meeting_code"
          />

          <!-- Trigger -->
          <button
            class="btn ml-2 mr-2 focus:outline-none"
            data-clipboard-target="#meeting_alert"
          >
            <box-icon
              type="regular"
              class="mt-2"
              color="blue"
              name="clipboard"
            ></box-icon>
          </button>
        </div>
      </div>
      <div class="flex justify-center mt-7 align-center">
        <box-icon name="check-shield" size="24px" color="red"></box-icon>
        <span class="ml-2 text-sm text-gray-700 font-medium antialiased"
          >All content for this meeting are encrypted end to end.</span
        >
      </div>
      <div class="mt-3 text-sm text-gray-500 font-medium antialiased">
        Joined as {{ user_info }}
      </div>
    </div>
  </div>
</template>

<script>
import ClipboardJS from "clipboard";
export default {
  props: ["meeting_code", "user_info"], // props
  name: "Alert",
  data() {
    return {
      //
    };
  },
  // mounted
  mounted() {
    new ClipboardJS(".btn");
  }
};
</script>
