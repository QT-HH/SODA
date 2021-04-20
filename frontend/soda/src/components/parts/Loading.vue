<template>
  <div class="loading">
    <div
      class="h-screen bg-gray-900 text-white items-center justify-center flex flex-wrap content-center"
    >
      <Spinner />
      <div class="text-2xl ml-2 text-teal-400">{{ status }}...</div>
    </div>
  </div>
</template>

<script>
import Spinner from "./Spinner";
export default {
  name: "Loading",
  components: { Spinner },
  props: ["status"],
  data() {
    return {
      //
    };
  }
};
</script>
