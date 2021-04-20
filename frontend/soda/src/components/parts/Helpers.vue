<template>
  <div class="helpers">
    <div>
      <div class="hover:bg-gray-200 cursor-pointer">
        <a href="">
          <div class="flex space-x-5 p-2 items-center">
            <div class="ml-4 mt-1">
              <box-icon name="chalkboard" color="#3F3F46"></box-icon>
            </div>
            <div>
              <div>Whiteboard</div>
              <div class="text-sm">Google whiteboard</div>
            </div>
          </div>
        </a>
      </div>
      <div class="hover:bg-gray-200 cursor-pointer">
        <a href="">
          <div class="flex space-x-5 p-2 items-center">
            <div class="ml-4 mt-1">
              <box-icon name="layout" color="#3F3F46"></box-icon>
            </div>
            <div>
              <div>Change layout</div>
            </div>
          </div>
        </a>
      </div>
      <div class="hover:bg-gray-200 cursor-pointer">
        <a href="">
          <div class="flex space-x-5 p-2 items-center">
            <div class="ml-4 mt-1">
              <box-icon name="expand-alt" color="#3F3F46"></box-icon>
            </div>
            <div>
              <div>Full screen</div>
            </div>
          </div>
        </a>
      </div>
      <div class="hover:bg-gray-200 cursor-pointer">
        <a href="">
          <div class="flex space-x-5 p-2 items-center">
            <div class="ml-4 mt-1">
              <box-icon name="mouse-alt" color="#3F3F46"></box-icon>
            </div>
            <div>
              <div>Change background</div>
            </div>
          </div>
        </a>
      </div>
      <div class="hover:bg-gray-200 cursor-pointer">
        <a href="">
          <div class="flex space-x-5 p-2 items-center">
            <div class="ml-4 mt-1">
              <box-icon name="captions" color="#3F3F46"></box-icon>
            </div>
            <div>
              <div>Turn on captions</div>
            </div>
          </div>
        </a>
      </div>
      <div class="hover:bg-gray-200 cursor-pointer">
        <a @click="opendialog">
          <div class="flex space-x-5 p-2 items-center">
            <div class="ml-4 mt-1">
              <box-icon name="paperclip" color="#3F3F46"></box-icon>
            </div>
            <div>
              <div>Add Attachment</div>
            </div>
          </div>
        </a>
      </div>
      <hr />
      <div class="hover:bg-gray-200 cursor-pointer">
        <a href="">
          <div class="flex space-x-5 p-2 items-center">
            <div class="ml-4 mt-1">
              <box-icon name="error-alt" color="#3F3F46"></box-icon>
            </div>
            <div>
              <div>Report problem</div>
            </div>
          </div>
        </a>
      </div>
      <div class="hover:bg-gray-200 cursor-pointer">
        <a href="">
          <div class="flex space-x-5 p-2 items-center">
            <div class="ml-4 mt-1">
              <box-icon name="help-circle" color="#3F3F46"></box-icon>
            </div>
            <div>
              <div>Help & Troubleshooting</div>
            </div>
          </div>
        </a>
      </div>
      <div class="hover:bg-gray-200 cursor-pointer">
        <a href="">
          <div class="flex space-x-5 p-2 items-center">
            <div class="ml-4 mt-1">
              <box-icon name="cog" color="#3F3F46"></box-icon>
            </div>
            <div>
              <div>Settings</div>
            </div>
          </div>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //
    };
  },
  methods: {
    // open dialog
    opendialog() {
      this.$emit("opendialog");
    }
  }
};
</script>
