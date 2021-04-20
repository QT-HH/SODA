<template>
  <div class="chat overflow-y-auto">
    <div v-for="item in messages" :key="item.id">
      <div class="flex items-center justify-between">
        <div class="text-gray-900 font-medium antialiased">
          {{ item.sessionid }}
        </div>
        <div class="text-xs text-gray-400 antialiased">
          {{ formatdate(item.createdAt) }}
        </div>
      </div>
      <div class="text-gray-700 text-sm antialiased">
        {{ decryptmessage(item.message) }}
      </div>
      <div v-if="item.attachment" class="flex items-center justify-end">
        <a href="">
          <box-icon name="paperclip"></box-icon>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import { decryptinformation } from "../script";
export default {
  name: "Chats",
  props: ["messages"],
  data() {
    return {
      items: []
    };
  },
  methods: {
    // decrypt message
    decryptmessage(item) {
      const response = decryptinformation(item);
      return response;
    },
    //
    getuserdetails(item) {
      return item;
    },
    formatdate(item) {
      return moment(item).fromNow();
    }
  }
};
</script>
