<template>
  <div class="menu">
    <div class="flex py-3 px-4 border border-b-gray-500 rounded-lg">
      <box-icon
        color="#15803D"
        type="solid"
        class="mt-1"
        name="info-circle"
      ></box-icon>
      <div class="ml-3">
        <div class="text-lg font-semibold">Details</div>
        <div class="text-xs text-gray-400">{{ date }}</div>
      </div>
    </div>
    <div class="px-5 py-4">
      <div class="text-base mb-2 text-black font-semibold">Joining info</div>
      <textarea
        id="meeting_details"
        name="meeting_details"
        type="text"
        rows="2"
        v-model="meeting_code"
        class="border-transparent w-full p-0 focus:outline-none"
      ></textarea>
      <button
        class="btn flex mt-3 focus:outline-none"
        data-clipboard-target="#meeting_details"
      >
        <box-icon color="#3F3F46" class="mt-1" name="copy"></box-icon>
        <div class="ml-3 font-semibold text-black">Copy joining info</div>
      </button>
    </div>
  </div>
</template>

<script>
import ClipboardJS from "clipboard";
export default {
  name: "Menu",
  props: ["meeting_code"],
  data() {
    return {
      date: new Date()
    };
  },
  // mounted
  mounted() {
    new ClipboardJS(".btn");
  }
};
</script>
