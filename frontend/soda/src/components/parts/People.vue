<template>
  <div class="people">
    <div
      class="flex py-1 items-center justify-between"
      v-for="(item, index) in sessions"
      :key="item.id ? item.id : index"
    >
      <div class="flex items-center">
        <div class="mt-2">
          <box-icon
            type="solid"
            color="#14B8A6"
            size="40px"
            name="user-circle"
          ></box-icon>
        </div>
        <div class="ml-2">
          {{ decryptname(item.name) }}
        </div>
      </div>
      <div class="flex items-center space-x-1">
        <box-icon color="#3B82F6" name='dots-horizontal-rounded' ></box-icon>
        <box-icon name='pin' type='solid' ></box-icon>
      </div>
    </div>
  </div>
</template>

<script>
import { decryptinformation } from "../script";
export default {
  name: "People",
  props: ["sessions"],
  data() {
    return {
      //
    };
  },
  methods: {
    // decrypt name
    decryptname(item) {
      const response = decryptinformation(item);
      return response;
    }
  }
};
</script>
